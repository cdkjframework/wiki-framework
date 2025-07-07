package com.cdkjframework.util.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoCallback;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ProjectName: HT-OMS-Project-WEB
 * @Package: com.cdkjframework.core.util.kryo
 * @ClassName: KryoUtils
 * @Description: KryoUtils
 * @Author: xiaLin
 * @Version: 1.0
 */
public class KryoUtil {

    /**
     * 序列化
     */
    private static Kryo serKryo = KryoInstance.INSTANCE.getPool().borrow();

    /**
     * 反序列化
     */
    private static Kryo dserKryo = KryoInstance.INSTANCE.getPool().borrow();

    static ReentrantLock lockSer = new ReentrantLock();

    static ReentrantLock lockDser = new ReentrantLock();
    /**
     * 序列化的缓冲池大小.默认是4096。现在设置成100000
     */
    private static final int BUFFER_SIZE = 100000;

    public static byte[] syncSerialize(final Object rpc) throws RuntimeException {

        lockSer.lock();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Output output = new Output(out, BUFFER_SIZE);
            serKryo.writeObjectOrNull(output, rpc, rpc.getClass());
            output.close();
            return out.toByteArray();
        } finally {
            lockSer.unlock();
        }
    }

    public static byte[] syncSerializeObject(final Object rpc)
            throws RuntimeException {
        lockSer.lock();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Output output = new Output(out, BUFFER_SIZE);
            serKryo.writeClassAndObject(output, rpc);
            output.close();
            return out.toByteArray();
        } finally {
            lockSer.unlock();
        }
    }

    public static <T> T syncDeserialize(final byte[] buf, final Class<T> type)
            throws RuntimeException {
        Input input = null;
        lockDser.lock();
        try {
            ByteArrayInputStream bIn = new ByteArrayInputStream(buf);
            input = new Input(bIn, BUFFER_SIZE);
            return dserKryo.readObject(input, type);
        } finally {
            if (input != null) {
                input.close();
            }
            lockDser.unlock();
        }
    }

    public static Object syncDeserializeObject(final byte[] buf)
            throws RuntimeException {
        Input input = null;
        lockDser.lock();
        try {
            ByteArrayInputStream bIn = new ByteArrayInputStream(buf);
            input = new Input(bIn, BUFFER_SIZE);
            return dserKryo.readClassAndObject(input);
        } finally {
            if (input != null) {
                input.close();
            }
            lockDser.unlock();
        }
    }

    /**
     * 类非线安全，所以需要加同步，当时也可以每次传一个新的类Kryo的实例，但需要消耗更多的性能
     *
     * @param rpc
     * @return
     * @throws RuntimeException
     */
    public static byte[] serialize(final Object rpc) throws RuntimeException {
        return KryoInstance.INSTANCE.getPool().run(new KryoCallback<byte[]>() {
            @Override
            public byte[] execute(Kryo kryo) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                Output output = new Output(out, BUFFER_SIZE);
                kryo.writeObjectOrNull(output, rpc, rpc.getClass());
                output.close();
                return out.toByteArray();
            }
        });
    }

    /**
     * 序列化
     *
     * @param obj
     * @return
     * @throws RuntimeException
     * @author : <a href="mailto:dejianliu@ebnew.com">liudejian</a> 2014-12-11
     * 下午6:04:28
     */
    public static byte[] serializeObject(final Object obj)
            throws RuntimeException {
        return KryoInstance.INSTANCE.getPool().run(new KryoCallback<byte[]>() {
            @Override
            public byte[] execute(Kryo kryo) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                Output output = new Output(out, BUFFER_SIZE);
                kryo.writeClassAndObject(output, obj);
                output.close();
                return out.toByteArray();
            }
        });
    }

    /**
     * 反序列化
     *
     * @param buf  反序列化字节数组
     * @param type 返回类型
     * @return
     * @throws RuntimeException
     */
    public static <T> T deserialize(final byte[] buf, final Class<T> type)
            throws RuntimeException {

        return KryoInstance.INSTANCE.getPool().run(new KryoCallback<T>() {
            @Override
            public T execute(Kryo kryo) {
                ByteArrayInputStream bIn = new ByteArrayInputStream(buf);
                Input input = new Input(bIn, BUFFER_SIZE);
                try {
                    return kryo.readObject(input, type);
                } finally {
                    input.close();
                }
            }
        });

    }

    public static List deserialize(final byte[] buf)
            throws RuntimeException {

        return KryoInstance.INSTANCE.getPool().run(new KryoCallback<List<Object>>() {
            @Override
            public List<Object> execute(Kryo kryo) {
                ByteArrayInputStream bIn = new ByteArrayInputStream(buf);
                Input input = new Input(bIn, BUFFER_SIZE);
                try {
                    return kryo.readObject(input, List.class);
                } finally {
                    input.close();
                }
            }
        });

    }

    /**
     * 反序列化对象
     *
     * @param buf
     * @return
     * @throws RuntimeException
     * @author : <a href="mailto:dejianliu@ebnew.com">liudejian</a> 2014-12-11
     * 下午6:03:28
     */
    public static Object deserializeObject(final byte[] buf)
            throws RuntimeException {

        return KryoInstance.INSTANCE.getPool().run(new KryoCallback<Object>() {
            @Override
            public Object execute(Kryo kryo) {
                ByteArrayInputStream bIn = new ByteArrayInputStream(buf);
                Input input = new Input(bIn, BUFFER_SIZE);
                try {
                    return kryo.readClassAndObject(input);
                } finally {
                    input.close();
                }
            }
        });

    }

}
