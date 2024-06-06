package com.cdkjframework.util.make;

import com.cdkjframework.exceptions.GlobalRuntimeException;

/**
 * @ProjectName: com.lesmarthome.interface
 * @Package: com.lesmarthome.interfaces.util
 * @ClassName: SnowflakeUtils
 * @Description: 雪花算法 Twitter_Snowflake<br>
 * * SnowFlake的结构如下(每部分用-分开):<br>
 * * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 0000 - 0000 - 0000 - 0000000000 <br>
 * * 1位标识，由于long基本类型在Java中是带符号的，最高位是符号位，正数是0，负数是1，所以id一般是正数，最高位是0<br>
 * * 41位时间截(毫秒级)，注意，41位时间截不是存储当前时间的时间截，而是存储时间截的差值（当前时间截 - 开始时间截)，
 * * 这里的的开始时间截，一般是我们的id生成器开始使用的时间，由我们程序来指定的（如下下面程序IdWorker类的startTime属性）。41位的时间截，可以使用69年，年T = (1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69<br>
 * * 4位预留，用于业务设计
 * * 8位的数据机器位，可以部署在256个节点，包括4位datacenterId和4位workerId<br>
 * * 10位序列，毫秒内的计数，10位的计数顺序号支持每个节点每毫秒(同一机器，同一时间截)产生1024个ID序号<br>
 * * 加起来刚好64位，为一个Long型。<br>
 * * SnowFlake的优点是，整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞(由数据中心ID和机器ID作区分)，并且效率较高，经测试，SnowFlake每秒能够产生26万ID左右。
 * *
 * @Author: xiaLin
 * @Date: 2022/9/23 14:04
 * @Version: 1.0
 */
public class SnowflakeUtils {

    /**
     * 开始时间截 (2022-09-23 14:33:53)
     */
    private final long EPOCH = 1663914833335L;

    /**
     * 机器id所占的位数
     */
    private final long WORKER_ID_BITS = 4L;

    /**
     * 数据标识id所占的位数
     */
    private final long DATACENTER_ID_BITS = 4L;

    /**
     * 业务id占位
     **/
    private final long BIZ_BITS = 4L;

    /**
     * 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
     */
    private final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);

    /**
     * 支持的最大数据标识id，结果是31
     */
    private final long MAX_DATACENTER_ID = ~(-1L << DATACENTER_ID_BITS);

    /**
     * 序列在id中占的位数
     */
    private final long SEQUENCE_BITS = 10L;

    /**
     * 机器ID向左移10位
     */
    private final long WORKER_ID_SHIFT = SEQUENCE_BITS;

    /**
     * 数据标识id向左移14位(4+10)
     */
    private final long DATACENTER_ID_SHIFT = WORKER_ID_SHIFT + WORKER_ID_BITS;

    /**
     * 业务预留标识id向左移18位(4+4+10)
     */
    private final long BIZ_SHIFT = DATACENTER_ID_SHIFT + DATACENTER_ID_BITS;

    /**
     * 时间截向左移22位(4+4+4+10)
     */
    private final long TIMESTAMP_LEFT_SHIFT = BIZ_SHIFT + BIZ_BITS;

    /**
     * 生成序列的掩码，这里为1024 (0b1111111111=0xfff=1023)
     */
    private final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);

    /**
     * 工作机器ID(0~15)
     */
    private long workerId;

    /**
     * 数据中心ID(0~15)
     */
    private long datacenterId;

    /**
     * 毫秒内序列(0~4095)
     */
    private long sequence = 0L;

    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp = -1L;

    /**
     * 构造函数
     *
     * @param workerId     工作ID (0~15)
     * @param datacenterId 数据中心ID (0~15)
     */
    private SnowflakeUtils(long workerId, long datacenterId) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new GlobalRuntimeException(String.format("工作线程Id不能大于%d或小于0", MAX_WORKER_ID));
        }
        if (datacenterId > MAX_DATACENTER_ID || datacenterId < 0) {
            throw new GlobalRuntimeException(String.format("数据中心Id不能大于%d或小于0", MAX_DATACENTER_ID));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }


    /**
     * 获取 String 类型的 id
     *
     * @return 返回结果
     */
    public String getIdString() {
        return String.valueOf(nextId());
    }

    /**
     * 获取 Long 类型的 id
     *
     * @return 返回结果
     */
    public Long getId() {
        return Long.valueOf(nextId());
    }

    /**
     * 获得下一个ID (该方法是线程安全的)
     *
     * @return SnowflakeId 雪花算法ID
     */
    public synchronized long nextId() {
        long timestamp = timeGen();
        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new GlobalRuntimeException(String.format("时钟向后移动。拒绝生成id达%d毫秒", lastTimestamp - timestamp));
        }
        //如果是同一时间生成的，则进行毫秒内序列
        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        //时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;

        //移位并通过或运算拼到一起组成64位的ID
        /** 开始时间截 (2022-09-23 14:33:53) */
//         上次生成ID的时间截 -  1561376248657L(开始时间截) << 10L + 4L + 4L + 4L
        return ((timestamp - EPOCH) << TIMESTAMP_LEFT_SHIFT)
                // 0               10L +  4L
                | (datacenterId << DATACENTER_ID_SHIFT)
                // 0               10L
                | (workerId << WORKER_ID_SHIFT)
                // 0L
                | sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }
}
