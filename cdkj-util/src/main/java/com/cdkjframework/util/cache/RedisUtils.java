package com.cdkjframework.util.cache;

import com.alibaba.fastjson.JSONArray;
import com.cdkjframework.config.RedisConfig;
import com.cdkjframework.util.date.DateUtils;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.AssertUtils;
import com.cdkjframework.util.tool.JsonUtils;
import com.cdkjframework.util.tool.StringUtils;
import io.lettuce.core.*;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.async.RedisAdvancedClusterAsyncCommands;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import io.lettuce.core.support.ConnectionPoolSupport;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.util.cache
 * @ClassName: RedisUtils
 * @Description: Redis 缓存工具
 * @Author: xiaLin
 * @Version: .0
 */
@Component
public class RedisUtils implements ApplicationRunner {

    /**
     * 日志
     */
    private static LogUtils logUtils = LogUtils.getLogger(RedisUtils.class);

    /**
     * 配置
     */
    @Autowired
    private RedisConfig redisConfig;

    /**
     * 异步 redis 访问
     */
    private static RedisAdvancedClusterAsyncCommands<String, String> commands;

    /**
     * 同步 redis 访问
     */
    private static RedisAdvancedClusterCommands<String, String> clusterCommands;

    /**
     * 单
     */
    private static RedisAsyncCommands<String, String> redisAsyncCommands;

    /**
     * 初始化
     *
     * @param args 参数
     * @throws Exception 异常信息
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        AssertUtils.isListEmpty(redisConfig.getHost(), "redis 没有配置连接地址");
        int port = redisConfig.getPort();
        // redis 集群连接
        if (redisConfig.getHost().size() > 1) {
            logUtils.info("Redis 集群配置开始：" + DateUtils.format(new Date(), DateUtils.DATE_HH_MM_SS));
            redisClusterClient(port);
            return;
        } else {
            logUtils.info("Redis 配置开始：" + DateUtils.format(new Date(), DateUtils.DATE_HH_MM_SS));
            // redis 连接
            redisClient(port);
        }
        logUtils.info("Redis 配置结束" + DateUtils.format(new Date(), DateUtils.DATE_HH_MM_SS));
    }

    /**
     * redis 连接
     */
    private void redisClient(int port) {
        String url = redisConfig.getHost().get(0);
        // 创建地址
        RedisClient redisClient = RedisClient.create(createRedisUrl(url, port));
        redisClient.setOptions(clientOptions());
        redisClient.setDefaultTimeout(Duration.ofSeconds(redisConfig.getTimeout()));

        GenericObjectPool<StatefulRedisConnection<String, String>> pool;
        GenericObjectPoolConfig<StatefulRedisConnection<String, String>> poolConfig =
                genericObjectPoolConfig();

        // 创建连接
        pool = ConnectionPoolSupport.createGenericObjectPool(() -> {
            logUtils.info("Requesting new StatefulRedisConnection " + System.currentTimeMillis());
            return redisClient.connect();
        }, poolConfig);

        StatefulRedisConnection<String, String> connection = null;
        try {
            connection = pool.borrowObject();
            connection.setAutoFlushCommands(true);

            redisAsyncCommands = connection.async();
        } catch (Exception ex) {
            logUtils.error(ex.getCause(), ex.getMessage());
        }
    }

    /**
     * redis 集群连接
     */
    private void redisClusterClient(int port) {
        List<RedisURI> redisURIList = new ArrayList<>();

        for (String key :
                redisConfig.getHost()) {
            redisURIList.add(createRedisUrl(key, port));
        }

        // redis 集群
        RedisClusterClient clusterClient = RedisClusterClient.create(redisURIList);
        clusterClient.setOptions(clusterClientOptions());
        clusterClient.setDefaultTimeout(Duration.ofSeconds(redisConfig.getTimeout()));

        // 配置
        GenericObjectPool<StatefulRedisClusterConnection<String, String>> pool;
        GenericObjectPoolConfig<StatefulRedisClusterConnection<String, String>> poolConfig =
                genericObjectPoolConfig();

        // 创建连接
        pool = ConnectionPoolSupport.createGenericObjectPool(() -> {
            logUtils.info("Requesting new StatefulRedisClusterConnection " + System.currentTimeMillis());
            return clusterClient.connect();
        }, poolConfig);

        StatefulRedisClusterConnection<String, String> connection = null;
        try {
            connection = pool.borrowObject();
            connection.setReadFrom(ReadFrom.MASTER_PREFERRED);

            commands = connection.async();
            clusterCommands = connection.sync();
        } catch (Exception ex) {
            logUtils.error(ex.getCause(), ex.getMessage());
        }
    }

    /**
     * 创建连接
     *
     * @param redisUrl 连接地址
     * @param port     端口
     * @return 返回结果
     */
    private RedisURI createRedisUrl(String redisUrl, int port) {
        RedisURI redisURI;
        if (port == 0) {
            redisURI = RedisURI.create("redis://" + redisUrl);
        } else {
            redisURI = RedisURI.create(redisUrl, port);
        }
        redisURI.setDatabase(redisConfig.getDatabase());
        if (StringUtils.isNotNullAndEmpty(redisConfig.getPassword())) {
            redisURI.setPassword(redisConfig.getPassword());
        }
        // 返回地址
        return redisURI;
    }

    /**
     * 设置配置
     *
     * @return 返回配置结果
     */
    private GenericObjectPoolConfig genericObjectPoolConfig() {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxIdle(redisConfig.getMaxIdle());
        poolConfig.setMinIdle(redisConfig.getMinIdle());
        poolConfig.setMaxTotal(redisConfig.getMaxTotal());
        poolConfig.setMaxWaitMillis(redisConfig.getMaxWaitMillis());
        poolConfig.setMinEvictableIdleTimeMillis(redisConfig.getMinEvictableIdleTimeMillis());
        poolConfig.setSoftMinEvictableIdleTimeMillis(redisConfig.getSoftMinEvictableIdleTimeMillis());

        return poolConfig;
    }

    /**
     * 配置集群选项,自动重连,最多重定型1次
     *
     * @return 返回结果
     */
    private ClusterClientOptions clusterClientOptions() {
        return ClusterClientOptions.builder().autoReconnect(true).maxRedirects(1).build();
    }

    /**
     * 自动重连
     *
     * @return 返回结果
     */
    private ClientOptions clientOptions() {
        return ClientOptions.builder().autoReconnect(true).build();
    }

    // =============================common============================

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return 0
     */
    public static boolean syncExpire(String key, long time) {
        try {
            if (time < 0) {
                time = 30 * 60;
            }
            RedisFuture<Boolean> redisFuture = redisAsyncCommands == null ?
                    commands.expire(key, time) :
                    redisAsyncCommands.expire(key, time);
            return redisFuture.get();
        } catch (Exception e) {
            logUtils.error(e.getCause(), e.getMessage());
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为nullogUtils* @return 时间(秒) 返回0代表为永久有效
     */
    public static boolean syncExists(String... key) {
        RedisFuture<Long> redisFuture = redisAsyncCommands == null ? commands.exists(key) :
                redisAsyncCommands.exists(key);
        try {
            if (redisFuture.get() == key.length) {
                return true;
            } else {
                return false;
            }
        } catch (InterruptedException e) {
            logUtils.error(e.getCause(), e.getMessage());
        } catch (ExecutionException e) {
            logUtils.error(e.getCause(), e.getMessage());
        }

        return false;
    }


    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */

    @SuppressWarnings("unchecked")
    public static void syncDel(String... key) {
        if (redisAsyncCommands == null) {
            commands.del(key);
        } else {
            redisAsyncCommands.del(key);
        }
    }

    // ============================String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public static String syncGet(String key) {
        if (syncExists(key)) {
            return null;
        }
        RedisFuture<String> redisFuture = redisAsyncCommands == null ? commands.get(key) :
                redisAsyncCommands.get(key);
        try {
            return redisFuture.get();
        } catch (InterruptedException e) {
            logUtils.error(e.getCause(), e.getMessage());
        } catch (ExecutionException e) {
            logUtils.error(e.getCause(), e.getMessage());
        }
        return null;
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public static boolean syncSet(String key, String value) {
        try {
            if (redisAsyncCommands == null) {
                commands.set(key, value);
            } else {
                redisAsyncCommands.set(key, value);
            }
            return true;
        } catch (Exception e) {
            logUtils.error(e.getCause(), e.getMessage());
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public static boolean syncSet(String key, String value, long time) {
        try {
            if (time > 0) {
                if (redisAsyncCommands == null) {
                    commands.setex(key, time, value);
                } else {
                    redisAsyncCommands.setex(key, time, value);
                }
            } else {
                syncSet(key, value);
            }
            return true;
        } catch (Exception e) {
            logUtils.error(e.getCause(), e.getMessage());
            return false;
        }
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return 返回增加值
     */
    public static long syncIncr(String key, long delta) {
        RedisFuture<Long> redisFuture;
        if (delta <= 0) {
            redisFuture = redisAsyncCommands == null ? commands.incr(key) : redisAsyncCommands.incr(key);
        } else {
            redisFuture = redisAsyncCommands == null ? commands.incrby(key, delta) : redisAsyncCommands.incrby(key, delta);
        }
        // 返回结果
        try {
            return redisFuture.get();
        } catch (InterruptedException e) {
            logUtils.error(e.getCause(), e.getMessage());
        } catch (ExecutionException e) {
            logUtils.error(e.getCause(), e.getMessage());
        }
        return 0L;
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    public static long syncDecr(String key, long delta) {
        RedisFuture<Long> redisFuture;
        if (delta <= 0) {
            redisFuture = redisAsyncCommands == null ? commands.decr(key) : redisAsyncCommands.decr(key);
        } else {
            redisFuture = redisAsyncCommands == null ? commands.decrby(key, delta) : redisAsyncCommands.decrby(key, delta);
        }
        // 返回结果
        try {
            return redisFuture.get();
        } catch (InterruptedException e) {
            logUtils.error(e.getCause(), e.getMessage());
        } catch (ExecutionException e) {
            logUtils.error(e.getCause(), e.getMessage());
        }
        return 0L;
    }

    // ================================ Map =================================

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     * 0
     */
    public static Map<String, Object> syncHashGet(String key) {
        String value = syncGet(key);
        return JsonUtils.jsonStringToMap(value);
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     * 0
     */
    public static boolean syncHashSet(String key, Map<String, Object> map) {
        try {
            String value = JsonUtils.objectToJsonString(map);
            syncSet(key, value);
            return true;
        } catch (Exception e) {
            logUtils.error(e.getCause(), e.getMessage());
            return false;
        }
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public static boolean syncHashSet(String key, Map<String, Object> map, long time) {
        try {
            String value = JsonUtils.objectToJsonString(map);
            syncSet(key, value);
            if (time > 0) {
                syncExpire(key, time);
            }
            return true;
        } catch (Exception e) {
            logUtils.error(e.getCause(), e.getMessage());
            return false;
        }
    }


    // ================================ Entity =================================

    /**
     * 获取实体
     *
     * @param key   主键
     * @param clazz 实体
     * @param <T>
     * @return 返回实体
     */
    public static <T> T syncGetEntity(String key, Class<T> clazz) {
        String value = syncGet(key);
        return JsonUtils.jsonStringToBean(value, clazz);
    }

    /**
     * 实体写入缓存
     *
     * @param key 主键
     * @param t   实体
     * @param <T> 实体类型
     * @return 返回执行结果
     */
    public static <T> boolean syncEntitySet(String key, T t) {
        String value = JsonUtils.objectToJsonString(t);
        return syncSet(key, value);
    }

    /**
     * 实体写入缓存
     *
     * @param key 主键
     * @param t   实体
     * @param <T> 实体类型
     * @return 返回执行结果
     */
    public static <T> boolean syncEntitySet(String key, T t, long time) {
        String value = JsonUtils.objectToJsonString(t);
        return syncSet(key, value, time);
    }

    // ================================ List =================================

    /**
     * 获取实体
     *
     * @param key   主键
     * @param clazz 实体
     * @param <T>
     * @return 返回实体
     */
    public static <T> List<T> syncGetList(String key, Class<T> clazz) {
        String value = syncGet(key);
        return JsonUtils.jsonStringToList(value, clazz);
    }

    /**
     * 实体写入缓存
     *
     * @param key  主键
     * @param list 实体
     * @param <T>  实体类型
     * @return 返回执行结果
     */
    public static <T> boolean syncListSet(String key, List<T> list) {
        JSONArray value = JsonUtils.listToJsonArray(list);
        if (value == null) {
            return false;
        }
        return syncSet(key, value.toJSONString());
    }

    /**
     * 实体写入缓存
     *
     * @param key  主键
     * @param list 实体
     * @param <T>  实体类型
     * @return 返回执行结果
     */
    public static <T> boolean syncListSet(String key, List<T> list, long time) {
        JSONArray value = JsonUtils.listToJsonArray(list);
        if (value == null) {
            return false;
        }
        return syncSet(key, value.toJSONString(), time);
    }
}