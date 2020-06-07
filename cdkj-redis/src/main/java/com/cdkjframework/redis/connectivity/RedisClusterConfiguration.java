package com.cdkjframework.redis.connectivity;

import com.cdkjframework.exceptions.GlobalException;
import com.cdkjframework.redis.config.RedisConfig;
import com.cdkjframework.util.date.DateUtils;
import com.cdkjframework.util.date.LocalDateUtils;
import com.cdkjframework.util.log.LogUtils;
import io.lettuce.core.*;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.async.AsyncNodeSelection;
import io.lettuce.core.cluster.api.async.RedisAdvancedClusterAsyncCommands;
import io.lettuce.core.cluster.api.async.RedisClusterAsyncCommands;
import io.lettuce.core.cluster.models.partitions.RedisClusterNode;
import io.lettuce.core.output.*;
import io.lettuce.core.protocol.CommandArgs;
import io.lettuce.core.protocol.CommandType;
import io.lettuce.core.protocol.ProtocolKeyword;
import io.lettuce.core.support.ConnectionPoolSupport;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.redis
 * @ClassName: RedisConfiguration
 * @Description: Redis 缓存工具配置
 * @Author: xiaLin
 * @Version: 1.0
 */
@Configuration
@Component
public class RedisClusterConfiguration extends BaseRedisConfiguration {

    /**
     * 日志
     */
    private LogUtils logUtils = LogUtils.getLogger(RedisClusterConfiguration.class);

    /**
     * 配置
     */
    @Autowired
    private RedisConfig redisConfig;

    /**
     * Redis高级集群异步命令
     *
     * @return 返回结果
     */
    @Bean(name = "clusterAsyncCommands")
    public RedisAdvancedClusterAsyncCommands<String, String> redisAdvancedClusterAsyncCommands() throws GlobalException {
        int port = redisConfig.getPort();
        RedisAdvancedClusterAsyncCommands<String, String> commands = null;
        if (!redisClusterCommands()) {
            commands = redisClusterClient();
        } else {
            commands = redisClusterClient(port);
            logUtils.info("Redis 集群配置结束：" + LocalDateUtils.dateTimeCurrentFormatter());
        }

        // 返回结果
        return commands;
    }

    /**
     * 集群默认信息
     *
     * @return
     */
    private RedisAdvancedClusterAsyncCommands<String, String> redisClusterClient() {
        return new RedisAdvancedClusterAsyncCommands<String, String>() {
            @Override
            public RedisClusterAsyncCommands<String, String> getConnection(String nodeId) {
                return null;
            }

            @Override
            public RedisClusterAsyncCommands<String, String> getConnection(String host, int port) {
                return null;
            }

            @Override
            public StatefulRedisClusterConnection<String, String> getStatefulConnection() {
                return null;
            }

            @Override
            public AsyncNodeSelection<String, String> readonly(Predicate<RedisClusterNode> predicate) {
                return null;
            }

            @Override
            public AsyncNodeSelection<String, String> nodes(Predicate<RedisClusterNode> predicate) {
                return null;
            }

            @Override
            public AsyncNodeSelection<String, String> nodes(Predicate<RedisClusterNode> predicate, boolean dynamic) {
                return null;
            }

            @Override
            public RedisFuture<Long> del(String... keys) {
                return null;
            }

            @Override
            public RedisFuture<Long> unlink(String... keys) {
                return null;
            }

            @Override
            public RedisFuture<Long> exists(String... keys) {
                return null;
            }

            @Override
            public RedisFuture<List<KeyValue<String, String>>> mget(String... keys) {
                return null;
            }

            @Override
            public RedisFuture<String> mset(Map<String, String> map) {
                return null;
            }

            @Override
            public RedisFuture<Boolean> msetnx(Map<String, String> map) {
                return null;
            }

            @Override
            public RedisFuture<String> clientSetname(String name) {
                return null;
            }

            @Override
            public RedisFuture<String> flushall() {
                return null;
            }

            @Override
            public RedisFuture<String> flushdb() {
                return null;
            }

            @Override
            public RedisFuture<Long> dbsize() {
                return null;
            }

            @Override
            public RedisFuture<List<String>> keys(String pattern) {
                return null;
            }

            @Override
            public RedisFuture<Long> keys(KeyStreamingChannel<String> channel, String pattern) {
                return null;
            }

            @Override
            public RedisFuture<String> randomkey() {
                return null;
            }

            @Override
            public RedisFuture<String> scriptFlush() {
                return null;
            }

            @Override
            public RedisFuture<String> scriptKill() {
                return null;
            }

            @Override
            public RedisFuture<String> scriptLoad(String script) {
                return null;
            }

            @Override
            public void shutdown(boolean save) {

            }

            @Override
            public RedisFuture<KeyScanCursor<String>> scan() {
                return null;
            }

            @Override
            public RedisFuture<KeyScanCursor<String>> scan(ScanArgs scanArgs) {
                return null;
            }

            @Override
            public RedisFuture<KeyScanCursor<String>> scan(ScanCursor scanCursor, ScanArgs scanArgs) {
                return null;
            }

            @Override
            public RedisFuture<KeyScanCursor<String>> scan(ScanCursor scanCursor) {
                return null;
            }

            @Override
            public RedisFuture<StreamScanCursor> scan(KeyStreamingChannel<String> channel) {
                return null;
            }

            @Override
            public RedisFuture<StreamScanCursor> scan(KeyStreamingChannel<String> channel, ScanArgs scanArgs) {
                return null;
            }

            @Override
            public RedisFuture<StreamScanCursor> scan(KeyStreamingChannel<String> channel, ScanCursor scanCursor, ScanArgs scanArgs) {
                return null;
            }

            @Override
            public RedisFuture<StreamScanCursor> scan(KeyStreamingChannel<String> channel, ScanCursor scanCursor) {
                return null;
            }

            @Override
            public RedisFuture<Long> touch(String... keys) {
                return null;
            }

            @Override
            public void setTimeout(Duration timeout) {

            }

            @Override
            public void setTimeout(long timeout, TimeUnit unit) {

            }

            @Override
            public String auth(String password) {
                return null;
            }

            @Override
            public RedisFuture<String> clusterBumpepoch() {
                return null;
            }

            @Override
            public RedisFuture<String> clusterMeet(String ip, int port) {
                return null;
            }

            @Override
            public RedisFuture<String> clusterForget(String nodeId) {
                return null;
            }

            @Override
            public RedisFuture<String> clusterAddSlots(int... slots) {
                return null;
            }

            @Override
            public RedisFuture<String> clusterDelSlots(int... slots) {
                return null;
            }

            @Override
            public RedisFuture<String> clusterSetSlotNode(int slot, String nodeId) {
                return null;
            }

            @Override
            public RedisFuture<String> clusterSetSlotStable(int slot) {
                return null;
            }

            @Override
            public RedisFuture<String> clusterSetSlotMigrating(int slot, String nodeId) {
                return null;
            }

            @Override
            public RedisFuture<String> clusterSetSlotImporting(int slot, String nodeId) {
                return null;
            }

            @Override
            public RedisFuture<String> clusterInfo() {
                return null;
            }

            @Override
            public RedisFuture<String> clusterMyId() {
                return null;
            }

            @Override
            public RedisFuture<String> clusterNodes() {
                return null;
            }

            @Override
            public RedisFuture<List<String>> clusterSlaves(String nodeId) {
                return null;
            }

            @Override
            public RedisFuture<List<String>> clusterGetKeysInSlot(int slot, int count) {
                return null;
            }

            @Override
            public RedisFuture<Long> clusterCountKeysInSlot(int slot) {
                return null;
            }

            @Override
            public RedisFuture<Long> clusterCountFailureReports(String nodeId) {
                return null;
            }

            @Override
            public RedisFuture<Long> clusterKeyslot(String key) {
                return null;
            }

            @Override
            public RedisFuture<String> clusterSaveconfig() {
                return null;
            }

            @Override
            public RedisFuture<String> clusterSetConfigEpoch(long configEpoch) {
                return null;
            }

            @Override
            public RedisFuture<List<Object>> clusterSlots() {
                return null;
            }

            @Override
            public RedisFuture<String> asking() {
                return null;
            }

            @Override
            public RedisFuture<String> clusterReplicate(String nodeId) {
                return null;
            }

            @Override
            public RedisFuture<String> clusterFailover(boolean force) {
                return null;
            }

            @Override
            public RedisFuture<String> clusterReset(boolean hard) {
                return null;
            }

            @Override
            public RedisFuture<String> clusterFlushslots() {
                return null;
            }

            @Override
            public RedisFuture<String> readOnly() {
                return null;
            }

            @Override
            public RedisFuture<String> readWrite() {
                return null;
            }

            @Override
            public RedisFuture<Long> publish(String channel, String message) {
                return null;
            }

            @Override
            public RedisFuture<List<String>> pubsubChannels() {
                return null;
            }

            @Override
            public RedisFuture<List<String>> pubsubChannels(String channel) {
                return null;
            }

            @Override
            public RedisFuture<Map<String, Long>> pubsubNumsub(String... channels) {
                return null;
            }

            @Override
            public RedisFuture<Long> pubsubNumpat() {
                return null;
            }

            @Override
            public RedisFuture<String> echo(String msg) {
                return null;
            }

            @Override
            public RedisFuture<List<Object>> role() {
                return null;
            }

            @Override
            public RedisFuture<String> ping() {
                return null;
            }

            @Override
            public RedisFuture<String> quit() {
                return null;
            }

            @Override
            public RedisFuture<Long> waitForReplication(int replicas, long timeout) {
                return null;
            }

            @Override
            public <T> RedisFuture<T> dispatch(ProtocolKeyword type, CommandOutput<String, String, T> output) {
                return null;
            }

            @Override
            public <T> RedisFuture<T> dispatch(ProtocolKeyword type, CommandOutput<String, String, T> output, CommandArgs<String, String> args) {
                return null;
            }

            @Override
            public boolean isOpen() {
                return false;
            }

            @Override
            public void reset() {

            }

            @Override
            public void setAutoFlushCommands(boolean autoFlush) {

            }

            @Override
            public void flushCommands() {

            }

            @Override
            public RedisFuture<Long> geoadd(String key, double longitude, double latitude, String member) {
                return null;
            }

            @Override
            public RedisFuture<Long> geoadd(String key, Object... lngLatMember) {
                return null;
            }

            @Override
            public RedisFuture<List<Value<String>>> geohash(String key, String... members) {
                return null;
            }

            @Override
            public RedisFuture<Set<String>> georadius(String key, double longitude, double latitude, double distance, GeoArgs.Unit unit) {
                return null;
            }

            @Override
            public RedisFuture<List<GeoWithin<String>>> georadius(String key, double longitude, double latitude, double distance, GeoArgs.Unit unit, GeoArgs geoArgs) {
                return null;
            }

            @Override
            public RedisFuture<Long> georadius(String key, double longitude, double latitude, double distance, GeoArgs.Unit unit, GeoRadiusStoreArgs<String> geoRadiusStoreArgs) {
                return null;
            }

            @Override
            public RedisFuture<Set<String>> georadiusbymember(String key, String member, double distance, GeoArgs.Unit unit) {
                return null;
            }

            @Override
            public RedisFuture<List<GeoWithin<String>>> georadiusbymember(String key, String member, double distance, GeoArgs.Unit unit, GeoArgs geoArgs) {
                return null;
            }

            @Override
            public RedisFuture<Long> georadiusbymember(String key, String member, double distance, GeoArgs.Unit unit, GeoRadiusStoreArgs<String> geoRadiusStoreArgs) {
                return null;
            }

            @Override
            public RedisFuture<List<GeoCoordinates>> geopos(String key, String... members) {
                return null;
            }

            @Override
            public RedisFuture<Double> geodist(String key, String from, String to, GeoArgs.Unit unit) {
                return null;
            }

            @Override
            public RedisFuture<Long> pfadd(String key, String... values) {
                return null;
            }

            @Override
            public RedisFuture<String> pfmerge(String destkey, String... sourcekeys) {
                return null;
            }

            @Override
            public RedisFuture<Long> pfcount(String... keys) {
                return null;
            }

            @Override
            public RedisFuture<Long> hdel(String key, String... fields) {
                return null;
            }

            @Override
            public RedisFuture<Boolean> hexists(String key, String field) {
                return null;
            }

            @Override
            public RedisFuture<String> hget(String key, String field) {
                return null;
            }

            @Override
            public RedisFuture<Long> hincrby(String key, String field, long amount) {
                return null;
            }

            @Override
            public RedisFuture<Double> hincrbyfloat(String key, String field, double amount) {
                return null;
            }

            @Override
            public RedisFuture<Map<String, String>> hgetall(String key) {
                return null;
            }

            @Override
            public RedisFuture<Long> hgetall(KeyValueStreamingChannel<String, String> channel, String key) {
                return null;
            }

            @Override
            public RedisFuture<List<String>> hkeys(String key) {
                return null;
            }

            @Override
            public RedisFuture<Long> hkeys(KeyStreamingChannel<String> channel, String key) {
                return null;
            }

            @Override
            public RedisFuture<Long> hlen(String key) {
                return null;
            }

            @Override
            public RedisFuture<List<KeyValue<String, String>>> hmget(String key, String... fields) {
                return null;
            }

            @Override
            public RedisFuture<Long> hmget(KeyValueStreamingChannel<String, String> channel, String key, String... fields) {
                return null;
            }

            @Override
            public RedisFuture<String> hmset(String key, Map<String, String> map) {
                return null;
            }

            @Override
            public RedisFuture<MapScanCursor<String, String>> hscan(String key) {
                return null;
            }

            @Override
            public RedisFuture<MapScanCursor<String, String>> hscan(String key, ScanArgs scanArgs) {
                return null;
            }

            @Override
            public RedisFuture<MapScanCursor<String, String>> hscan(String key, ScanCursor scanCursor, ScanArgs scanArgs) {
                return null;
            }

            @Override
            public RedisFuture<MapScanCursor<String, String>> hscan(String key, ScanCursor scanCursor) {
                return null;
            }

            @Override
            public RedisFuture<StreamScanCursor> hscan(KeyValueStreamingChannel<String, String> channel, String key) {
                return null;
            }

            @Override
            public RedisFuture<StreamScanCursor> hscan(KeyValueStreamingChannel<String, String> channel, String key, ScanArgs scanArgs) {
                return null;
            }

            @Override
            public RedisFuture<StreamScanCursor> hscan(KeyValueStreamingChannel<String, String> channel, String key, ScanCursor scanCursor, ScanArgs scanArgs) {
                return null;
            }

            @Override
            public RedisFuture<StreamScanCursor> hscan(KeyValueStreamingChannel<String, String> channel, String key, ScanCursor scanCursor) {
                return null;
            }

            @Override
            public RedisFuture<Boolean> hset(String key, String field, String value) {
                return null;
            }

            @Override
            public RedisFuture<Boolean> hsetnx(String key, String field, String value) {
                return null;
            }

            @Override
            public RedisFuture<Long> hstrlen(String key, String field) {
                return null;
            }

            @Override
            public RedisFuture<List<String>> hvals(String key) {
                return null;
            }

            @Override
            public RedisFuture<Long> hvals(ValueStreamingChannel<String> channel, String key) {
                return null;
            }

            @Override
            public RedisFuture<byte[]> dump(String key) {
                return null;
            }

            @Override
            public RedisFuture<Boolean> expire(String key, long seconds) {
                return null;
            }

            @Override
            public RedisFuture<Boolean> expireat(String key, Date timestamp) {
                return null;
            }

            @Override
            public RedisFuture<Boolean> expireat(String key, long timestamp) {
                return null;
            }

            @Override
            public RedisFuture<String> migrate(String host, int port, String key, int db, long timeout) {
                return null;
            }

            @Override
            public RedisFuture<String> migrate(String host, int port, int db, long timeout, MigrateArgs<String> migrateArgs) {
                return null;
            }

            @Override
            public RedisFuture<Boolean> move(String key, int db) {
                return null;
            }

            @Override
            public RedisFuture<String> objectEncoding(String key) {
                return null;
            }

            @Override
            public RedisFuture<Long> objectIdletime(String key) {
                return null;
            }

            @Override
            public RedisFuture<Long> objectRefcount(String key) {
                return null;
            }

            @Override
            public RedisFuture<Boolean> persist(String key) {
                return null;
            }

            @Override
            public RedisFuture<Boolean> pexpire(String key, long milliseconds) {
                return null;
            }

            @Override
            public RedisFuture<Boolean> pexpireat(String key, Date timestamp) {
                return null;
            }

            @Override
            public RedisFuture<Boolean> pexpireat(String key, long timestamp) {
                return null;
            }

            @Override
            public RedisFuture<Long> pttl(String key) {
                return null;
            }

            @Override
            public RedisFuture<String> rename(String key, String newKey) {
                return null;
            }

            @Override
            public RedisFuture<Boolean> renamenx(String key, String newKey) {
                return null;
            }

            @Override
            public RedisFuture<String> restore(String key, long ttl, byte[] value) {
                return null;
            }

            @Override
            public RedisFuture<String> restore(String key, byte[] value, RestoreArgs args) {
                return null;
            }

            @Override
            public RedisFuture<List<String>> sort(String key) {
                return null;
            }

            @Override
            public RedisFuture<Long> sort(ValueStreamingChannel<String> channel, String key) {
                return null;
            }

            @Override
            public RedisFuture<List<String>> sort(String key, SortArgs sortArgs) {
                return null;
            }

            @Override
            public RedisFuture<Long> sort(ValueStreamingChannel<String> channel, String key, SortArgs sortArgs) {
                return null;
            }

            @Override
            public RedisFuture<Long> sortStore(String key, SortArgs sortArgs, String destination) {
                return null;
            }

            @Override
            public RedisFuture<Long> ttl(String key) {
                return null;
            }

            @Override
            public RedisFuture<String> type(String key) {
                return null;
            }

            @Override
            public RedisFuture<KeyValue<String, String>> blpop(long timeout, String... keys) {
                return null;
            }

            @Override
            public RedisFuture<KeyValue<String, String>> brpop(long timeout, String... keys) {
                return null;
            }

            @Override
            public RedisFuture<String> brpoplpush(long timeout, String source, String destination) {
                return null;
            }

            @Override
            public RedisFuture<String> lindex(String key, long index) {
                return null;
            }

            @Override
            public RedisFuture<Long> linsert(String key, boolean before, String pivot, String value) {
                return null;
            }

            @Override
            public RedisFuture<Long> llen(String key) {
                return null;
            }

            @Override
            public RedisFuture<String> lpop(String key) {
                return null;
            }

            @Override
            public RedisFuture<Long> lpush(String key, String... values) {
                return null;
            }

            @Override
            public RedisFuture<Long> lpushx(String key, String... values) {
                return null;
            }

            @Override
            public RedisFuture<List<String>> lrange(String key, long start, long stop) {
                return null;
            }

            @Override
            public RedisFuture<Long> lrange(ValueStreamingChannel<String> channel, String key, long start, long stop) {
                return null;
            }

            @Override
            public RedisFuture<Long> lrem(String key, long count, String value) {
                return null;
            }

            @Override
            public RedisFuture<String> lset(String key, long index, String value) {
                return null;
            }

            @Override
            public RedisFuture<String> ltrim(String key, long start, long stop) {
                return null;
            }

            @Override
            public RedisFuture<String> rpop(String key) {
                return null;
            }

            @Override
            public RedisFuture<String> rpoplpush(String source, String destination) {
                return null;
            }

            @Override
            public RedisFuture<Long> rpush(String key, String... values) {
                return null;
            }

            @Override
            public RedisFuture<Long> rpushx(String key, String... values) {
                return null;
            }

            @Override
            public <T> RedisFuture<T> eval(String script, ScriptOutputType type, String... keys) {
                return null;
            }

            @Override
            public <T> RedisFuture<T> eval(String script, ScriptOutputType type, String[] keys, String... values) {
                return null;
            }

            @Override
            public <T> RedisFuture<T> evalsha(String digest, ScriptOutputType type, String... keys) {
                return null;
            }

            @Override
            public <T> RedisFuture<T> evalsha(String digest, ScriptOutputType type, String[] keys, String... values) {
                return null;
            }

            @Override
            public RedisFuture<List<Boolean>> scriptExists(String... digests) {
                return null;
            }

            @Override
            public String digest(String script) {
                return null;
            }

            @Override
            public RedisFuture<String> bgrewriteaof() {
                return null;
            }

            @Override
            public RedisFuture<String> bgsave() {
                return null;
            }

            @Override
            public RedisFuture<String> clientGetname() {
                return null;
            }

            @Override
            public RedisFuture<String> clientKill(String addr) {
                return null;
            }

            @Override
            public RedisFuture<Long> clientKill(KillArgs killArgs) {
                return null;
            }

            @Override
            public RedisFuture<Long> clientUnblock(long id, UnblockType type) {
                return null;
            }

            @Override
            public RedisFuture<String> clientPause(long timeout) {
                return null;
            }

            @Override
            public RedisFuture<String> clientList() {
                return null;
            }

            @Override
            public RedisFuture<List<Object>> command() {
                return null;
            }

            @Override
            public RedisFuture<List<Object>> commandInfo(String... commands) {
                return null;
            }

            @Override
            public RedisFuture<List<Object>> commandInfo(CommandType... commands) {
                return null;
            }

            @Override
            public RedisFuture<Long> commandCount() {
                return null;
            }

            @Override
            public RedisFuture<Map<String, String>> configGet(String parameter) {
                return null;
            }

            @Override
            public RedisFuture<String> configResetstat() {
                return null;
            }

            @Override
            public RedisFuture<String> configRewrite() {
                return null;
            }

            @Override
            public RedisFuture<String> configSet(String parameter, String value) {
                return null;
            }

            @Override
            public RedisFuture<String> debugCrashAndRecover(Long delay) {
                return null;
            }

            @Override
            public RedisFuture<String> debugHtstats(int db) {
                return null;
            }

            @Override
            public RedisFuture<String> debugObject(String key) {
                return null;
            }

            @Override
            public void debugOom() {

            }

            @Override
            public void debugSegfault() {

            }

            @Override
            public RedisFuture<String> debugReload() {
                return null;
            }

            @Override
            public RedisFuture<String> debugRestart(Long delay) {
                return null;
            }

            @Override
            public RedisFuture<String> debugSdslen(String key) {
                return null;
            }

            @Override
            public RedisFuture<String> flushallAsync() {
                return null;
            }

            @Override
            public RedisFuture<String> flushdbAsync() {
                return null;
            }

            @Override
            public RedisFuture<String> info() {
                return null;
            }

            @Override
            public RedisFuture<String> info(String section) {
                return null;
            }

            @Override
            public RedisFuture<Date> lastsave() {
                return null;
            }

            @Override
            public RedisFuture<String> save() {
                return null;
            }

            @Override
            public RedisFuture<String> slaveof(String host, int port) {
                return null;
            }

            @Override
            public RedisFuture<String> slaveofNoOne() {
                return null;
            }

            @Override
            public RedisFuture<List<Object>> slowlogGet() {
                return null;
            }

            @Override
            public RedisFuture<List<Object>> slowlogGet(int count) {
                return null;
            }

            @Override
            public RedisFuture<Long> slowlogLen() {
                return null;
            }

            @Override
            public RedisFuture<String> slowlogReset() {
                return null;
            }

            @Override
            public RedisFuture<List<String>> time() {
                return null;
            }

            @Override
            public RedisFuture<Long> sadd(String key, String... members) {
                return null;
            }

            @Override
            public RedisFuture<Long> scard(String key) {
                return null;
            }

            @Override
            public RedisFuture<Set<String>> sdiff(String... keys) {
                return null;
            }

            @Override
            public RedisFuture<Long> sdiff(ValueStreamingChannel<String> channel, String... keys) {
                return null;
            }

            @Override
            public RedisFuture<Long> sdiffstore(String destination, String... keys) {
                return null;
            }

            @Override
            public RedisFuture<Set<String>> sinter(String... keys) {
                return null;
            }

            @Override
            public RedisFuture<Long> sinter(ValueStreamingChannel<String> channel, String... keys) {
                return null;
            }

            @Override
            public RedisFuture<Long> sinterstore(String destination, String... keys) {
                return null;
            }

            @Override
            public RedisFuture<Boolean> sismember(String key, String member) {
                return null;
            }

            @Override
            public RedisFuture<Boolean> smove(String source, String destination, String member) {
                return null;
            }

            @Override
            public RedisFuture<Set<String>> smembers(String key) {
                return null;
            }

            @Override
            public RedisFuture<Long> smembers(ValueStreamingChannel<String> channel, String key) {
                return null;
            }

            @Override
            public RedisFuture<String> spop(String key) {
                return null;
            }

            @Override
            public RedisFuture<Set<String>> spop(String key, long count) {
                return null;
            }

            @Override
            public RedisFuture<String> srandmember(String key) {
                return null;
            }

            @Override
            public RedisFuture<List<String>> srandmember(String key, long count) {
                return null;
            }

            @Override
            public RedisFuture<Long> srandmember(ValueStreamingChannel<String> channel, String key, long count) {
                return null;
            }

            @Override
            public RedisFuture<Long> srem(String key, String... members) {
                return null;
            }

            @Override
            public RedisFuture<Set<String>> sunion(String... keys) {
                return null;
            }

            @Override
            public RedisFuture<Long> sunion(ValueStreamingChannel<String> channel, String... keys) {
                return null;
            }

            @Override
            public RedisFuture<Long> sunionstore(String destination, String... keys) {
                return null;
            }

            @Override
            public RedisFuture<ValueScanCursor<String>> sscan(String key) {
                return null;
            }

            @Override
            public RedisFuture<ValueScanCursor<String>> sscan(String key, ScanArgs scanArgs) {
                return null;
            }

            @Override
            public RedisFuture<ValueScanCursor<String>> sscan(String key, ScanCursor scanCursor, ScanArgs scanArgs) {
                return null;
            }

            @Override
            public RedisFuture<ValueScanCursor<String>> sscan(String key, ScanCursor scanCursor) {
                return null;
            }

            @Override
            public RedisFuture<StreamScanCursor> sscan(ValueStreamingChannel<String> channel, String key) {
                return null;
            }

            @Override
            public RedisFuture<StreamScanCursor> sscan(ValueStreamingChannel<String> channel, String key, ScanArgs scanArgs) {
                return null;
            }

            @Override
            public RedisFuture<StreamScanCursor> sscan(ValueStreamingChannel<String> channel, String key, ScanCursor scanCursor, ScanArgs scanArgs) {
                return null;
            }

            @Override
            public RedisFuture<StreamScanCursor> sscan(ValueStreamingChannel<String> channel, String key, ScanCursor scanCursor) {
                return null;
            }

            @Override
            public RedisFuture<KeyValue<String, ScoredValue<String>>> bzpopmin(long timeout, String... keys) {
                return null;
            }

            @Override
            public RedisFuture<KeyValue<String, ScoredValue<String>>> bzpopmax(long timeout, String... keys) {
                return null;
            }

            @Override
            public RedisFuture<Long> zadd(String key, double score, String member) {
                return null;
            }

            @Override
            public RedisFuture<Long> zadd(String key, Object... scoresAndValues) {
                return null;
            }

            @Override
            public RedisFuture<Long> zadd(String key, ScoredValue<String>... scoredValues) {
                return null;
            }

            @Override
            public RedisFuture<Long> zadd(String key, ZAddArgs zAddArgs, double score, String member) {
                return null;
            }

            @Override
            public RedisFuture<Long> zadd(String key, ZAddArgs zAddArgs, Object... scoresAndValues) {
                return null;
            }

            @Override
            public RedisFuture<Long> zadd(String key, ZAddArgs zAddArgs, ScoredValue<String>... scoredValues) {
                return null;
            }

            @Override
            public RedisFuture<Double> zaddincr(String key, double score, String member) {
                return null;
            }

            @Override
            public RedisFuture<Double> zaddincr(String key, ZAddArgs zAddArgs, double score, String member) {
                return null;
            }

            @Override
            public RedisFuture<Long> zcard(String key) {
                return null;
            }

            @Override
            public RedisFuture<Long> zcount(String key, double min, double max) {
                return null;
            }

            @Override
            public RedisFuture<Long> zcount(String key, String min, String max) {
                return null;
            }

            @Override
            public RedisFuture<Long> zcount(String key, Range<? extends Number> range) {
                return null;
            }

            @Override
            public RedisFuture<Double> zincrby(String key, double amount, String member) {
                return null;
            }

            @Override
            public RedisFuture<Long> zinterstore(String destination, String... keys) {
                return null;
            }

            @Override
            public RedisFuture<Long> zinterstore(String destination, ZStoreArgs storeArgs, String... keys) {
                return null;
            }

            @Override
            public RedisFuture<Long> zlexcount(String key, String min, String max) {
                return null;
            }

            @Override
            public RedisFuture<Long> zlexcount(String key, Range<? extends String> range) {
                return null;
            }

            @Override
            public RedisFuture<ScoredValue<String>> zpopmin(String key) {
                return null;
            }

            @Override
            public RedisFuture<List<ScoredValue<String>>> zpopmin(String key, long count) {
                return null;
            }

            @Override
            public RedisFuture<ScoredValue<String>> zpopmax(String key) {
                return null;
            }

            @Override
            public RedisFuture<List<ScoredValue<String>>> zpopmax(String key, long count) {
                return null;
            }

            @Override
            public RedisFuture<List<String>> zrange(String key, long start, long stop) {
                return null;
            }

            @Override
            public RedisFuture<Long> zrange(ValueStreamingChannel<String> channel, String key, long start, long stop) {
                return null;
            }

            @Override
            public RedisFuture<List<ScoredValue<String>>> zrangeWithScores(String key, long start, long stop) {
                return null;
            }

            @Override
            public RedisFuture<Long> zrangeWithScores(ScoredValueStreamingChannel<String> channel, String key, long start, long stop) {
                return null;
            }

            @Override
            public RedisFuture<List<String>> zrangebylex(String key, String min, String max) {
                return null;
            }

            @Override
            public RedisFuture<List<String>> zrangebylex(String key, Range<? extends String> range) {
                return null;
            }

            @Override
            public RedisFuture<List<String>> zrangebylex(String key, String min, String max, long offset, long count) {
                return null;
            }

            @Override
            public RedisFuture<List<String>> zrangebylex(String key, Range<? extends String> range, Limit limit) {
                return null;
            }

            @Override
            public RedisFuture<List<String>> zrangebyscore(String key, double min, double max) {
                return null;
            }

            @Override
            public RedisFuture<List<String>> zrangebyscore(String key, String min, String max) {
                return null;
            }

            @Override
            public RedisFuture<List<String>> zrangebyscore(String key, Range<? extends Number> range) {
                return null;
            }

            @Override
            public RedisFuture<List<String>> zrangebyscore(String key, double min, double max, long offset, long count) {
                return null;
            }

            @Override
            public RedisFuture<List<String>> zrangebyscore(String key, String min, String max, long offset, long count) {
                return null;
            }

            @Override
            public RedisFuture<List<String>> zrangebyscore(String key, Range<? extends Number> range, Limit limit) {
                return null;
            }

            @Override
            public RedisFuture<Long> zrangebyscore(ValueStreamingChannel<String> channel, String key, double min, double max) {
                return null;
            }

            @Override
            public RedisFuture<Long> zrangebyscore(ValueStreamingChannel<String> channel, String key, String min, String max) {
                return null;
            }

            @Override
            public RedisFuture<Long> zrangebyscore(ValueStreamingChannel<String> channel, String key, Range<? extends Number> range) {
                return null;
            }

            @Override
            public RedisFuture<Long> zrangebyscore(ValueStreamingChannel<String> channel, String key, double min, double max, long offset, long count) {
                return null;
            }

            @Override
            public RedisFuture<Long> zrangebyscore(ValueStreamingChannel<String> channel, String key, String min, String max, long offset, long count) {
                return null;
            }

            @Override
            public RedisFuture<Long> zrangebyscore(ValueStreamingChannel<String> channel, String key, Range<? extends Number> range, Limit limit) {
                return null;
            }

            @Override
            public RedisFuture<List<ScoredValue<String>>> zrangebyscoreWithScores(String key, double min, double max) {
                return null;
            }

            @Override
            public RedisFuture<List<ScoredValue<String>>> zrangebyscoreWithScores(String key, String min, String max) {
                return null;
            }

            @Override
            public RedisFuture<List<ScoredValue<String>>> zrangebyscoreWithScores(String key, Range<? extends Number> range) {
                return null;
            }

            @Override
            public RedisFuture<List<ScoredValue<String>>> zrangebyscoreWithScores(String key, double min, double max, long offset, long count) {
                return null;
            }

            @Override
            public RedisFuture<List<ScoredValue<String>>> zrangebyscoreWithScores(String key, String min, String max, long offset, long count) {
                return null;
            }

            @Override
            public RedisFuture<List<ScoredValue<String>>> zrangebyscoreWithScores(String key, Range<? extends Number> range, Limit limit) {
                return null;
            }

            @Override
            public RedisFuture<Long> zrangebyscoreWithScores(ScoredValueStreamingChannel<String> channel, String key, double min, double max) {
                return null;
            }

            @Override
            public RedisFuture<Long> zrangebyscoreWithScores(ScoredValueStreamingChannel<String> channel, String key, String min, String max) {
                return null;
            }

            @Override
            public RedisFuture<Long> zrangebyscoreWithScores(ScoredValueStreamingChannel<String> channel, String key, Range<? extends Number> range) {
                return null;
            }

            @Override
            public RedisFuture<Long> zrangebyscoreWithScores(ScoredValueStreamingChannel<String> channel, String key, double min, double max, long offset, long count) {
                return null;
            }

            @Override
            public RedisFuture<Long> zrangebyscoreWithScores(ScoredValueStreamingChannel<String> channel, String key, String min, String max, long offset, long count) {
                return null;
            }

            @Override
            public RedisFuture<Long> zrangebyscoreWithScores(ScoredValueStreamingChannel<String> channel, String key, Range<? extends Number> range, Limit limit) {
                return null;
            }

            @Override
            public RedisFuture<Long> zrank(String key, String member) {
                return null;
            }

            @Override
            public RedisFuture<Long> zrem(String key, String... members) {
                return null;
            }

            @Override
            public RedisFuture<Long> zremrangebylex(String key, String min, String max) {
                return null;
            }

            @Override
            public RedisFuture<Long> zremrangebylex(String key, Range<? extends String> range) {
                return null;
            }

            @Override
            public RedisFuture<Long> zremrangebyrank(String key, long start, long stop) {
                return null;
            }

            @Override
            public RedisFuture<Long> zremrangebyscore(String key, double min, double max) {
                return null;
            }

            @Override
            public RedisFuture<Long> zremrangebyscore(String key, String min, String max) {
                return null;
            }

            @Override
            public RedisFuture<Long> zremrangebyscore(String key, Range<? extends Number> range) {
                return null;
            }

            @Override
            public RedisFuture<List<String>> zrevrange(String key, long start, long stop) {
                return null;
            }

            @Override
            public RedisFuture<Long> zrevrange(ValueStreamingChannel<String> channel, String key, long start, long stop) {
                return null;
            }

            @Override
            public RedisFuture<List<ScoredValue<String>>> zrevrangeWithScores(String key, long start, long stop) {
                return null;
            }

            @Override
            public RedisFuture<Long> zrevrangeWithScores(ScoredValueStreamingChannel<String> channel, String key, long start, long stop) {
                return null;
            }

            @Override
            public RedisFuture<List<String>> zrevrangebylex(String key, Range<? extends String> range) {
                return null;
            }

            @Override
            public RedisFuture<List<String>> zrevrangebylex(String key, Range<? extends String> range, Limit limit) {
                return null;
            }

            @Override
            public RedisFuture<List<String>> zrevrangebyscore(String key, double max, double min) {
                return null;
            }

            @Override
            public RedisFuture<List<String>> zrevrangebyscore(String key, String max, String min) {
                return null;
            }

            @Override
            public RedisFuture<List<String>> zrevrangebyscore(String key, Range<? extends Number> range) {
                return null;
            }

            @Override
            public RedisFuture<List<String>> zrevrangebyscore(String key, double max, double min, long offset, long count) {
                return null;
            }

            @Override
            public RedisFuture<List<String>> zrevrangebyscore(String key, String max, String min, long offset, long count) {
                return null;
            }

            @Override
            public RedisFuture<List<String>> zrevrangebyscore(String key, Range<? extends Number> range, Limit limit) {
                return null;
            }

            @Override
            public RedisFuture<Long> zrevrangebyscore(ValueStreamingChannel<String> channel, String key, double max, double min) {
                return null;
            }

            @Override
            public RedisFuture<Long> zrevrangebyscore(ValueStreamingChannel<String> channel, String key, String max, String min) {
                return null;
            }

            @Override
            public RedisFuture<Long> zrevrangebyscore(ValueStreamingChannel<String> channel, String key, Range<? extends Number> range) {
                return null;
            }

            @Override
            public RedisFuture<Long> zrevrangebyscore(ValueStreamingChannel<String> channel, String key, double max, double min, long offset, long count) {
                return null;
            }

            @Override
            public RedisFuture<Long> zrevrangebyscore(ValueStreamingChannel<String> channel, String key, String max, String min, long offset, long count) {
                return null;
            }

            @Override
            public RedisFuture<Long> zrevrangebyscore(ValueStreamingChannel<String> channel, String key, Range<? extends Number> range, Limit limit) {
                return null;
            }

            @Override
            public RedisFuture<List<ScoredValue<String>>> zrevrangebyscoreWithScores(String key, double max, double min) {
                return null;
            }

            @Override
            public RedisFuture<List<ScoredValue<String>>> zrevrangebyscoreWithScores(String key, String max, String min) {
                return null;
            }

            @Override
            public RedisFuture<List<ScoredValue<String>>> zrevrangebyscoreWithScores(String key, Range<? extends Number> range) {
                return null;
            }

            @Override
            public RedisFuture<List<ScoredValue<String>>> zrevrangebyscoreWithScores(String key, double max, double min, long offset, long count) {
                return null;
            }

            @Override
            public RedisFuture<List<ScoredValue<String>>> zrevrangebyscoreWithScores(String key, String max, String min, long offset, long count) {
                return null;
            }

            @Override
            public RedisFuture<List<ScoredValue<String>>> zrevrangebyscoreWithScores(String key, Range<? extends Number> range, Limit limit) {
                return null;
            }

            @Override
            public RedisFuture<Long> zrevrangebyscoreWithScores(ScoredValueStreamingChannel<String> channel, String key, double max, double min) {
                return null;
            }

            @Override
            public RedisFuture<Long> zrevrangebyscoreWithScores(ScoredValueStreamingChannel<String> channel, String key, String max, String min) {
                return null;
            }

            @Override
            public RedisFuture<Long> zrevrangebyscoreWithScores(ScoredValueStreamingChannel<String> channel, String key, Range<? extends Number> range) {
                return null;
            }

            @Override
            public RedisFuture<Long> zrevrangebyscoreWithScores(ScoredValueStreamingChannel<String> channel, String key, double max, double min, long offset, long count) {
                return null;
            }

            @Override
            public RedisFuture<Long> zrevrangebyscoreWithScores(ScoredValueStreamingChannel<String> channel, String key, String max, String min, long offset, long count) {
                return null;
            }

            @Override
            public RedisFuture<Long> zrevrangebyscoreWithScores(ScoredValueStreamingChannel<String> channel, String key, Range<? extends Number> range, Limit limit) {
                return null;
            }

            @Override
            public RedisFuture<Long> zrevrank(String key, String member) {
                return null;
            }

            @Override
            public RedisFuture<ScoredValueScanCursor<String>> zscan(String key) {
                return null;
            }

            @Override
            public RedisFuture<ScoredValueScanCursor<String>> zscan(String key, ScanArgs scanArgs) {
                return null;
            }

            @Override
            public RedisFuture<ScoredValueScanCursor<String>> zscan(String key, ScanCursor scanCursor, ScanArgs scanArgs) {
                return null;
            }

            @Override
            public RedisFuture<ScoredValueScanCursor<String>> zscan(String key, ScanCursor scanCursor) {
                return null;
            }

            @Override
            public RedisFuture<StreamScanCursor> zscan(ScoredValueStreamingChannel<String> channel, String key) {
                return null;
            }

            @Override
            public RedisFuture<StreamScanCursor> zscan(ScoredValueStreamingChannel<String> channel, String key, ScanArgs scanArgs) {
                return null;
            }

            @Override
            public RedisFuture<StreamScanCursor> zscan(ScoredValueStreamingChannel<String> channel, String key, ScanCursor scanCursor, ScanArgs scanArgs) {
                return null;
            }

            @Override
            public RedisFuture<StreamScanCursor> zscan(ScoredValueStreamingChannel<String> channel, String key, ScanCursor scanCursor) {
                return null;
            }

            @Override
            public RedisFuture<Double> zscore(String key, String member) {
                return null;
            }

            @Override
            public RedisFuture<Long> zunionstore(String destination, String... keys) {
                return null;
            }

            @Override
            public RedisFuture<Long> zunionstore(String destination, ZStoreArgs storeArgs, String... keys) {
                return null;
            }

            @Override
            public RedisFuture<Long> xack(String key, String group, String... messageIds) {
                return null;
            }

            @Override
            public RedisFuture<String> xadd(String key, Map<String, String> body) {
                return null;
            }

            @Override
            public RedisFuture<String> xadd(String key, XAddArgs args, Map<String, String> body) {
                return null;
            }

            @Override
            public RedisFuture<String> xadd(String key, Object... keysAndValues) {
                return null;
            }

            @Override
            public RedisFuture<String> xadd(String key, XAddArgs args, Object... keysAndValues) {
                return null;
            }

            @Override
            public RedisFuture<List<StreamMessage<String, String>>> xclaim(String key, Consumer<String> consumer, long minIdleTime, String... messageIds) {
                return null;
            }

            @Override
            public RedisFuture<List<StreamMessage<String, String>>> xclaim(String key, Consumer<String> consumer, XClaimArgs args, String... messageIds) {
                return null;
            }

            @Override
            public RedisFuture<Long> xdel(String key, String... messageIds) {
                return null;
            }

            @Override
            public RedisFuture<String> xgroupCreate(XReadArgs.StreamOffset<String> streamOffset, String group) {
                return null;
            }
            @Override
            public RedisFuture<Boolean> xgroupDelconsumer(String key, Consumer<String> consumer) {
                return null;
            }

            @Override
            public RedisFuture<Boolean> xgroupDestroy(String key, String group) {
                return null;
            }

            @Override
            public RedisFuture<String> xgroupSetid(XReadArgs.StreamOffset<String> streamOffset, String group) {
                return null;
            }

            @Override
            public RedisFuture<Long> xlen(String key) {
                return null;
            }

            @Override
            public RedisFuture<List<Object>> xpending(String key, String group) {
                return null;
            }

            @Override
            public RedisFuture<List<Object>> xpending(String key, String group, Range<String> range, Limit limit) {
                return null;
            }

            @Override
            public RedisFuture<List<Object>> xpending(String key, Consumer<String> consumer, Range<String> range, Limit limit) {
                return null;
            }

            @Override
            public RedisFuture<List<StreamMessage<String, String>>> xrange(String key, Range<String> range) {
                return null;
            }

            @Override
            public RedisFuture<List<StreamMessage<String, String>>> xrange(String key, Range<String> range, Limit limit) {
                return null;
            }

            @Override
            public RedisFuture<List<StreamMessage<String, String>>> xread(XReadArgs.StreamOffset<String>... streams) {
                return null;
            }

            @Override
            public RedisFuture<List<StreamMessage<String, String>>> xread(XReadArgs args, XReadArgs.StreamOffset<String>... streams) {
                return null;
            }

            @Override
            public RedisFuture<List<StreamMessage<String, String>>> xreadgroup(Consumer<String> consumer, XReadArgs.StreamOffset<String>... streams) {
                return null;
            }

            @Override
            public RedisFuture<List<StreamMessage<String, String>>> xreadgroup(Consumer<String> consumer, XReadArgs args, XReadArgs.StreamOffset<String>... streams) {
                return null;
            }

            @Override
            public RedisFuture<List<StreamMessage<String, String>>> xrevrange(String key, Range<String> range) {
                return null;
            }

            @Override
            public RedisFuture<List<StreamMessage<String, String>>> xrevrange(String key, Range<String> range, Limit limit) {
                return null;
            }

            @Override
            public RedisFuture<Long> xtrim(String key, long count) {
                return null;
            }

            @Override
            public RedisFuture<Long> xtrim(String key, boolean approximateTrimming, long count) {
                return null;
            }

            @Override
            public RedisFuture<Long> append(String key, String value) {
                return null;
            }

            @Override
            public RedisFuture<Long> bitcount(String key) {
                return null;
            }

            @Override
            public RedisFuture<Long> bitcount(String key, long start, long end) {
                return null;
            }

            @Override
            public RedisFuture<List<Long>> bitfield(String key, BitFieldArgs bitFieldArgs) {
                return null;
            }

            @Override
            public RedisFuture<Long> bitpos(String key, boolean state) {
                return null;
            }

            @Override
            public RedisFuture<Long> bitpos(String key, boolean state, long start) {
                return null;
            }

            @Override
            public RedisFuture<Long> bitpos(String key, boolean state, long start, long end) {
                return null;
            }

            @Override
            public RedisFuture<Long> bitopAnd(String destination, String... keys) {
                return null;
            }

            @Override
            public RedisFuture<Long> bitopNot(String destination, String source) {
                return null;
            }

            @Override
            public RedisFuture<Long> bitopOr(String destination, String... keys) {
                return null;
            }

            @Override
            public RedisFuture<Long> bitopXor(String destination, String... keys) {
                return null;
            }

            @Override
            public RedisFuture<Long> decr(String key) {
                return null;
            }

            @Override
            public RedisFuture<Long> decrby(String key, long amount) {
                return null;
            }

            @Override
            public RedisFuture<String> get(String key) {
                return null;
            }

            @Override
            public RedisFuture<Long> getbit(String key, long offset) {
                return null;
            }

            @Override
            public RedisFuture<String> getrange(String key, long start, long end) {
                return null;
            }

            @Override
            public RedisFuture<String> getset(String key, String value) {
                return null;
            }

            @Override
            public RedisFuture<Long> incr(String key) {
                return null;
            }

            @Override
            public RedisFuture<Long> incrby(String key, long amount) {
                return null;
            }

            @Override
            public RedisFuture<Double> incrbyfloat(String key, double amount) {
                return null;
            }

            @Override
            public RedisFuture<Long> mget(KeyValueStreamingChannel<String, String> channel, String... keys) {
                return null;
            }

            @Override
            public RedisFuture<String> set(String key, String value) {
                return null;
            }

            @Override
            public RedisFuture<String> set(String key, String value, SetArgs setArgs) {
                return null;
            }

            @Override
            public RedisFuture<Long> setbit(String key, long offset, int value) {
                return null;
            }

            @Override
            public RedisFuture<String> setex(String key, long seconds, String value) {
                return null;
            }

            @Override
            public RedisFuture<String> psetex(String key, long milliseconds, String value) {
                return null;
            }

            @Override
            public RedisFuture<Boolean> setnx(String key, String value) {
                return null;
            }

            @Override
            public RedisFuture<Long> setrange(String key, long offset, String value) {
                return null;
            }

            @Override
            public RedisFuture<Long> strlen(String key) {
                return null;
            }
        };
    }

    /**
     * redis 集群连接
     */
    private RedisAdvancedClusterAsyncCommands<String, String> redisClusterClient(int port) {
        List<RedisURI> redisUrlList = new ArrayList<>();

        for (String key :
                redisConfig.getHost()) {
            redisUrlList.add(createRedisUrl(key, port));
        }

        // redis 集群
        RedisClusterClient clusterClient = RedisClusterClient.create(redisUrlList);
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

            return connection.async();
        } catch (Exception ex) {
            logUtils.error(ex.getCause(), ex.getMessage());
        }

        return null;
    }
}
