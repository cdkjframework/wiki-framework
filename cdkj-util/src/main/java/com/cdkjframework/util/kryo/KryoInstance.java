package com.cdkjframework.util.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;
import com.esotericsoftware.kryo.serializers.DefaultSerializers;
import de.javakaffee.kryoserializers.*;

import java.lang.reflect.InvocationHandler;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * @ProjectName: HT-OMS-Project-WEB
 * @Package: com.cdkjframework.core.util.kryo
 * @ClassName: KryoInstance
 * @Description: KryoInstance
 * @Author: xiaLin
 * @Version: 1.0
 */
public enum KryoInstance {
    //单例模式
    INSTANCE;

    private static final Set<Class<?>> REGISTERS = new LinkedHashSet<Class<?>>();
    private final KryoPool pool;

  public void registerClass(Class<?> clazz) {
        REGISTERS.add(clazz);
    }

    private KryoInstance() {
      KryoFactory factory = new KryoFactory() {
        @Override
        public Kryo create() {
          ExpKryo kryo = new ExpKryo();
          kryo.setRegistrationRequired(false);
          kryo.register(Arrays.asList("").getClass(),
                  new ArraysAsListSerializer());
          kryo.register(GregorianCalendar.class,
                  new GregorianCalendarSerializer());
          kryo.register(InvocationHandler.class, new JdkProxySerializer());


          kryo.register(BigDecimal.class,
                  new DefaultSerializers.BigDecimalSerializer());
          kryo.register(BigInteger.class,
                  new DefaultSerializers.BigIntegerSerializer());
          kryo.register(Pattern.class, new RegexSerializer());
          kryo.register(BitSet.class, new BitSetSerializer());
          kryo.register(URI.class, new URISerializer());
          kryo.register(UUID.class, new UUIDSerializer());
          UnmodifiableCollectionsSerializer.registerSerializers(kryo);
          SynchronizedCollectionsSerializer.registerSerializers(kryo);

          kryo.register(HashMap.class);
          kryo.register(ArrayList.class);
          kryo.register(LinkedList.class);
          kryo.register(HashSet.class);
          kryo.register(TreeSet.class);
          kryo.register(Hashtable.class);
          kryo.register(Date.class);
          kryo.register(Calendar.class);
          kryo.register(ConcurrentHashMap.class);
          kryo.register(SimpleDateFormat.class);
          kryo.register(GregorianCalendar.class);
          kryo.register(Vector.class);
          kryo.register(BitSet.class);
          kryo.register(StringBuffer.class);
          kryo.register(StringBuilder.class);
          kryo.register(Object.class);
          kryo.register(Object[].class);
          kryo.register(String[].class);
          kryo.register(byte[].class);
          kryo.register(char[].class);
          kryo.register(int[].class);
          kryo.register(float[].class);
          kryo.register(double[].class);

          for (Class<?> clazz : REGISTERS) {
            kryo.register(clazz);
          }
          return kryo;
        }
      };
      pool = new KryoPool.Builder(factory).build();
    }

    public KryoPool getPool() {
        return pool;
    }



}
