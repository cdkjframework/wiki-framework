package com.cdkjframework.util.make;

import com.cdkjframework.util.tool.ByteUtils;
import lombok.Data;

import java.net.InetAddress;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.util.make
 * @ClassName: AbstractUUIDGenerator
 * @Description: 抽象UUID生成
 * @Author: xiaLin
 * @Version: 1.0
 */
public abstract class AbstractUUIDGenerator {

    /**
     * IP地址
     */
    private static final int IP;

    /**
     * 静态变量
     */
    static {
        int ipAdd;
        try {
            ipAdd = ByteUtils.toInt(InetAddress.getLocalHost().getAddress());
        } catch (Exception e) {
            ipAdd = 0;
        }
        IP = ipAdd;
    }

    /**
     * 柜台
     */
    private static short counter = (short) 0;

    /**
     * JVM
     */
    private static final int JVM = (int) (System.currentTimeMillis() >>> 8);

    protected static int getJVM() {
        return JVM;
    }

    protected static short getCount() {
        synchronized (AbstractUUIDGenerator.class) {
            if (counter < 0) {
                counter = 0;
            }
            return counter++;
        }
    }

    protected static int getIP() {
        return IP;
    }

    protected static short getHiTime() {
        return (short) (System.currentTimeMillis() >>> 32);
    }

    protected static int getLoTime() {
        return (int) System.currentTimeMillis();
    }

}
