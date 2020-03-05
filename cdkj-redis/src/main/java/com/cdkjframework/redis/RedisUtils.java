package com.cdkjframework.redis;

import com.alibaba.fastjson.JSONArray;
import com.cdkjframework.util.log.LogUtils;
import com.cdkjframework.util.tool.JsonUtils;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.cluster.api.async.RedisAdvancedClusterAsyncCommands;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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
@Order(Integer.MIN_VALUE + 1)
public class RedisUtils implements ApplicationRunner {

    /**
     * 日志
     */
    private static LogUtils logUtils = LogUtils.getLogger(RedisUtils.class);

    /**
     * 异步 redis 访问
     */
    private static RedisAdvancedClusterAsyncCommands<String, String> commands;

    /**
     * 单
     */
    private static RedisAsyncCommands<String, String> redisAsyncCommands;

    /**
     * 异步 redis 访问
     */
    @Resource(name = "clusterAsyncCommands")
    private RedisAdvancedClusterAsyncCommands<String, String> clusterAsyncCommands;

    /**
     * 单
     */
    @Resource(name = "redisAsyncCommands")
    private RedisAsyncCommands<String, String> asyncCommands;

    /**
     * 初始化
     *
     * @param args 参数
     * @throws Exception 异常信息
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (clusterAsyncCommands.getStatefulConnection() != null) {
            commands = clusterAsyncCommands;
        }
        if (asyncCommands.getStatefulConnection() != null) {
            redisAsyncCommands = asyncCommands;
        }
    }

    // ============================= common ============================

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
