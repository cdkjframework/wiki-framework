package com.cdkj.framework.util.cache;

import com.alibaba.fastjson.JSONArray;
import com.cdkj.framework.enums.datasource.ApolloRedisEnum;
import com.cdkj.framework.util.kryo.KryoUtil;
import com.cdkj.framework.util.log.LogUtil;
import com.cdkj.framework.util.tool.JsonUtil;
import com.cdkj.framework.util.tool.StringUtil;
import com.cdkj.framework.util.tool.mapper.MapperUtil;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.*;

/**
 * @ProjectName: com.cdkj.framework.core
 * @Package: com.cdkj.framework.core.util.cache
 * @ClassName: JedisPoolUtil
 * @Description: JedisPool Redis 操作
 * @Author: xiaLin
 * @Version: 1.0
 */

@Component
@EnableCaching
@Order(1)
public class JedisPoolUtil implements ApplicationRunner {

    /**
     * 日志
     */
    private static LogUtil logUtil = LogUtil.getLogger(JedisPoolUtil.class);

    /**
     * 缓存读取类
     */
    private static JedisPool jedisPool;

    /**
     * redis 配置信息
     */
    @Autowired
    private RedisConfig redisConfig;

    /**
     * 读取 redis 配置
     */
    @ApolloConfig(value = "cdkj.redis")
    private Config apolloConfig;

    /**
     * 初始化连接
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (apolloConfig == null && StringUtil.isNullAndSpaceOrEmpty(redisConfig.getHost())) {
            return;
        }
        if (apolloConfig != null && apolloConfig.getPropertyNames().size() > 0) {
            setConfiguration();
        }
        logUtil.info("进入 Redis 配置：" + new Date());
        logUtil.info(JsonUtil.objectToJsonString(redisConfig));
        //配置信息
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(redisConfig.getMaxActive());
        jedisPoolConfig.setMaxIdle(redisConfig.getMaxIdle());
        jedisPoolConfig.setMaxWaitMillis(redisConfig.getTimeOut());
        try {
            //设置信息
            if (StringUtil.isNullAndSpaceOrEmpty(redisConfig.getPassword())) {
                jedisPool = new JedisPool(jedisPoolConfig, redisConfig.getHost(), redisConfig.getPort());
            } else {
                jedisPool = new JedisPool(jedisPoolConfig, redisConfig.getHost(), redisConfig.getPort(), redisConfig.getTimeOut(), redisConfig.getPassword());
            }
        } catch (Exception ex) {
            logUtil.info(ex.getMessage());
        }
        logUtil.info("进入 Redis 配置完成：" + new Date());
    }

    /**
     * 写入数据集
     *
     * @param key  主键
     * @param list 数据集
     * @return 返回是否成功
     */
    public static boolean setList(String key, List list) {
        //过期时间
        final Integer expire = 0;
        //写入数据
        return setList(key, list, expire);
    }

    /**
     * 写入数据集
     *
     * @param key    主键
     * @param list   数据集
     * @param expire 过期时间
     * @return 返回是否成功
     */
    public static boolean setList(String key, List list, Integer expire) {
        Jedis jedis = null;
        JSONArray jsonArray = null;
        try {

            byte[] bytes = KryoUtil.serialize(list);
            //读取资源
            jedis = jedisPool.getResource();
            if (expire > 0) {
                jedis.setex(key.getBytes(), expire, bytes);
            } else {
                jedis.set(key.getBytes(), bytes);
            }

            //返回结果
            return true;
        } catch (Exception ex) {
            logUtil.error("写入缓存异常：" + ex.getMessage());
            logUtil.error("keyValue：" + key + "：" + jsonArray == null ? "" : jsonArray.toString());
            return false;
        } finally {
            jedis.close();
        }
    }

    /**
     * 写入实体数据
     *
     * @param key 主键
     * @param t   数据
     * @param <T> 实体
     * @return 返回结果
     */
    public static <T> boolean setEntity(String key, T t) {
        //过期时间
        final Integer expire = 0;
        //写入数据
        return setEntity(key, t, expire);
    }

    /**
     * 写入实体数据
     *
     * @param key    主键
     * @param t      数据
     * @param expire 过期时间
     * @param <T>    实体
     * @return 返回结果
     */
    public static <T> boolean setEntity(String key, T t, Integer expire) {
//        JSONObject jsonObject = null;
        Jedis jedis = null;
        try {
            String json = JsonUtil.objectToJsonString(t);
            //读取资源
            jedis = jedisPool.getResource();
            if (expire > 0) {
                jedis.setex(key.getBytes(), expire, json.getBytes());
            } else {
                jedis.setnx(key.getBytes(), json.getBytes());
            }
            //返回结果
            return true;
        } catch (Exception ex) {
            logUtil.error("写入缓存异常：" + ex.getMessage());
            return false;
        } finally {
            jedis.close();
        }
    }

    /**
     * 写入字段串
     *
     * @param key  主键
     * @param data 数据
     * @return 返回结果
     */
    public static boolean setString(String key, String data) {
        //过期时间
        final Integer expire = 0;
        //写入数据
        return setString(key, data, expire);
    }

    /**
     * 写入字段串
     *
     * @param key    主键
     * @param data   数据
     * @param expire 过期时间
     * @return 返回结果
     */
    public static boolean setString(String key, String data, Integer expire) {
        Jedis jedis = null;
        try {
            //读取资源
            jedis = jedisPool.getResource();
            if (expire > 0) {
                jedis.setex(key.getBytes(), expire, data.getBytes());
            } else {
                jedis.set(key.getBytes(), data.getBytes());
            }

            //返回结果
            return true;
        } catch (Exception ex) {
            logUtil.error("写入缓存异常：" + ex.getMessage());
            logUtil.error("keyValue：" + key + "：" + data);
            return false;
        } finally {
            jedis.close();
        }
    }

    /**
     * 获取列表
     *
     * @param key   主键
     * @param clazz 实体
     * @return 返回结果 list
     */
    public static <T> List getList(String key, Class<T> clazz) {
        List list = Arrays.asList();
        try {
            //读取资源

            byte[] byteList = getByteList(key);
            //验证是否有数据
            if (byteList.length > 0) {
                list = KryoUtil.deserialize(byteList, ArrayList.class);
            }
        } catch (Exception ex) {
            logUtil.error("读取缓存异常：" + ex.getMessage());
            logUtil.error("key：" + key);
        }

        //返回结果
        return list;
    }

    /**
     * 获取列表
     *
     * @param key   主键
     * @param clazz 实体
     * @return 返回结果 list
     */
    public static <T> T getEntity(String key, Class<T> clazz) {
        T t = null;
        try {
            //读取资源
            String data = getString(key);
            //验证是否有数据
            if (!StringUtil.isNullAndSpaceOrEmpty(data)) {
                //创建实例
                t = JsonUtil.jsonStringToBean(data, clazz);
            }
        } catch (Exception ex) {
            t = null;
            logUtil.error("读取缓存异常：" + ex.getMessage());
            logUtil.error("key：" + key);
        }

        //返回结果
        return t;
    }

    /**
     * 获取 byte 数据
     *
     * @param key 主键
     * @return 返回结果
     */
    public static byte[] getByteList(String key) {
        Jedis jedis = null;
        byte[] byteList;
        try {
            //读取资源
            jedis = jedisPool.getResource();
            byteList = jedis.get(key.getBytes());
        } catch (Exception ex) {
            logUtil.error("读取缓存异常：" + ex.getMessage());
            logUtil.error("key：" + key);
            byteList = new byte[0];
        } finally {
            jedis.close();
        }

        return byteList;
    }

    /**
     * 读取指定键数据
     *
     * @param key 主键
     * @return 返回结果
     */
    public static String getString(String key) {
        String data = "";
        try {
            byte[] byteList = getByteList(key);

            data = new String(byteList);
        } catch (Exception ex) {
            logUtil.error("读取缓存异常：" + ex.getMessage());
            logUtil.error("key：" + key);
        } finally {
        }

        //返回结果
        return data;
    }

    /**
     * 验证是否存在
     *
     * @param key 主键
     * @return 返回结果
     */
    public static boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(key);
        } catch (Exception ex) {
            logUtil.error("验证缓存异常：" + ex.getMessage());
            logUtil.error("key：" + key);
            return false;
        } finally {
            jedis.close();
        }
    }

    /**
     * 移除（删除）缓存
     *
     * @param key 主键
     * @return 返回执行结果
     */
    public static boolean remove(String... key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //执行删除
            return jedis.del(key) > 0;
        } catch (Exception ex) {
            logUtil.error("验证缓存异常：" + ex.getMessage());
            logUtil.error("key：" + key);
            return false;
        } finally {
            jedis.close();
        }
    }

    /**
     * 通过key获取set<String>
     *
     * @param pattern
     * @return
     */
    public Set<String> getKeys(String pattern) {
        if (StringUtil.isNullAndSpaceOrEmpty(pattern)) {
            logUtil.info("Key can not be empty!");
            return null;
        } else {
            Set<String> sets = new HashSet();
            Long begin = System.currentTimeMillis();
            Jedis jedis = null;

            try {
                jedis = jedisPool.getResource();

                try {
                    Set<String> res = jedis.keys(pattern);
                    if (res != null) {
                        sets.addAll(res);
                    }
                } finally {
                    if (jedis != null) {
                        jedis.close();
                    }
                }
            } catch (Exception var15) {
                logUtil.error(var15.toString());
            } finally {
                logUtil.debug("Redis sort (" + pattern + ") took " + (System.currentTimeMillis() - begin) + " ms");
            }

            return sets;
        }
    }

    /**
     * 通过key删除
     *
     * @param key
     * @return
     */
    public Long del(String key) {
        if (StringUtil.isNullAndSpaceOrEmpty(key)) {
            logUtil.error("Key can not be empty!");
            return 0L;
        } else {
            Long begin = System.currentTimeMillis();
            Jedis jedis = null;

            try {
                jedis = jedisPool.getResource();
                Long var4 = jedis.del(key.getBytes());
                return var4;
            } catch (JedisConnectionException var9) {
                logUtil.error(var9.getMessage());
            } catch (Exception var10) {
                logUtil.error(var10.getMessage());
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
                logUtil.debug("Redis del (" + key + ") took " + (System.currentTimeMillis() - begin) + " ms");
            }

            return 0L;
        }
    }

    /**
     * 设置key过期时间
     *
     * @param key
     * @param seconds
     * @return
     */
    public Long expire(String key, int seconds) {
        if (StringUtil.isNullAndSpaceOrEmpty(key)) {
            logUtil.error("Key can not be empty!");
            return 0L;
        } else {
            Long begin = System.currentTimeMillis();
            Jedis jedis = null;

            try {
                jedis = jedisPool.getResource();
                Long var5 = jedis.expire(key.getBytes(), seconds);
                return var5;
            } catch (JedisConnectionException var10) {
                logUtil.error(var10.getMessage());
            } catch (Exception var11) {
                logUtil.error(var11.getMessage());
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
                logUtil.debug("Redis expire (" + key + ") took " + (System.currentTimeMillis() - begin) + " ms");
            }

            return 0L;
        }
    }

    /**
     * 设置配置
     */
    private void setConfiguration() {
        try {
            redisConfig = MapperUtil.apolloToEntity(apolloConfig, ApolloRedisEnum.values(), RedisConfig.class);
        } catch (IllegalAccessException e) {
            logUtil.error(e.getMessage());
            logUtil.error(e.getStackTrace());
        } catch (InstantiationException e) {
            logUtil.error(e.getMessage());
            logUtil.error(e.getStackTrace());
        }
    }
}
