package com.cdkjframework.redis.realize;

import io.lettuce.core.*;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import io.lettuce.core.models.stream.ClaimedMessages;
import io.lettuce.core.models.stream.PendingMessage;
import io.lettuce.core.models.stream.PendingMessages;
import io.lettuce.core.output.*;
import io.lettuce.core.protocol.CommandArgs;
import io.lettuce.core.protocol.CommandType;
import io.lettuce.core.protocol.ProtocolKeyword;
import io.lettuce.core.protocol.RedisCommand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.redis.realize
 * @ClassName: ReactiveCommands
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */

public class ReactiveCommands implements RedisReactiveCommands {


    /**
     * @param duration
     * @deprecated
     */
    @Override
    @Deprecated
    public void setTimeout(Duration duration) {

    }

    @Override
    public Mono<String> asking() {
        return null;
    }

    @Override
    public Mono<String> auth(CharSequence charSequence) {
        return null;
    }

    @Override
    public Mono<String> auth(String s, CharSequence charSequence) {
        return null;
    }

    @Override
    public Mono<String> clusterAddSlots(int... ints) {
        return null;
    }

    @Override
    public Mono<String> clusterBumpepoch() {
        return null;
    }

    @Override
    public Mono<Long> clusterCountFailureReports(String s) {
        return null;
    }

    @Override
    public Mono<Long> clusterCountKeysInSlot(int i) {
        return null;
    }

    @Override
    public Mono<String> clusterDelSlots(int... ints) {
        return null;
    }

    @Override
    public Mono<String> clusterFailover(boolean b) {
        return null;
    }

    @Override
    public Mono<String> clusterFailover(boolean b, boolean b1) {
        return null;
    }

    @Override
    public Mono<String> clusterFlushslots() {
        return null;
    }

    @Override
    public Mono<String> clusterForget(String s) {
        return null;
    }

    @Override
    public Flux clusterGetKeysInSlot(int i, int i1) {
        return null;
    }

    @Override
    public Mono<String> clusterInfo() {
        return null;
    }

    @Override
    public Mono<Long> clusterKeyslot(Object o) {
        return null;
    }

    @Override
    public Mono<String> clusterMeet(String s, int i) {
        return null;
    }

    @Override
    public Mono<String> clusterMyId() {
        return null;
    }

    @Override
    public Mono<String> clusterNodes() {
        return null;
    }

    @Override
    public Mono<String> clusterReplicate(String s) {
        return null;
    }

    @Override
    public Flux<String> clusterReplicas(String s) {
        return null;
    }

    @Override
    public Mono<String> clusterReset(boolean b) {
        return null;
    }

    @Override
    public Mono<String> clusterSaveconfig() {
        return null;
    }

    @Override
    public Mono<String> clusterSetConfigEpoch(long l) {
        return null;
    }

    @Override
    public Mono<String> clusterSetSlotImporting(int i, String s) {
        return null;
    }

    @Override
    public Mono<String> clusterSetSlotMigrating(int i, String s) {
        return null;
    }

    @Override
    public Mono<String> clusterSetSlotNode(int i, String s) {
        return null;
    }

    @Override
    public Mono<String> clusterSetSlotStable(int i) {
        return null;
    }

    @Override
    public Mono<List<Object>> clusterShards() {
        return null;
    }

    /**
     * @param s
     * @deprecated
     */
    @Override
    @Deprecated
    public Flux<String> clusterSlaves(String s) {
        return null;
    }

    @Override
    public Flux<Object> clusterSlots() {
        return null;
    }

    @Override
    public Mono<String> clusterDelSlotsRange(Range[] ranges) {
        return null;
    }

    @Override
    public Mono<String> clusterAddSlotsRange(Range[] ranges) {
        return null;
    }

    @Override
    public Mono<String> select(int i) {
        return null;
    }

    @Override
    public Mono<String> swapdb(int i, int i1) {
        return null;
    }

    /**
     * @deprecated
     */
    @Override
    @Deprecated
    public StatefulRedisConnection getStatefulConnection() {
        return null;
    }

    @Override
    public Mono<Long> publish(Object o, Object o2) {
        return null;
    }

    @Override
    public Flux pubsubChannels() {
        return null;
    }

    @Override
    public Flux pubsubChannels(Object o) {
        return null;
    }

    @Override
    public Mono<Map> pubsubNumsub(Object[] objects) {
        return null;
    }

    @Override
    public Mono<Long> pubsubNumpat() {
        return null;
    }

    @Override
    public Mono echo(Object o) {
        return null;
    }

    @Override
    public Flux<Object> role() {
        return null;
    }

    @Override
    public Mono<String> ping() {
        return null;
    }

    @Override
    public Mono<String> readOnly() {
        return null;
    }

    @Override
    public Mono<String> readWrite() {
        return null;
    }

    @Override
    public Mono<String> quit() {
        return null;
    }

    @Override
    public Mono<Long> waitForReplication(int i, long l) {
        return null;
    }

    /**
     * @deprecated
     */
    @Override
    @Deprecated
    public boolean isOpen() {
        return false;
    }

    /**
     * @deprecated
     */
    @Override
    @Deprecated
    public void reset() {

    }

    /**
     * @param b
     * @deprecated
     */
    @Override
    @Deprecated
    public void setAutoFlushCommands(boolean b) {

    }

    /**
     * @deprecated
     */
    @Override
    @Deprecated
    public void flushCommands() {

    }

    @Override
    public Flux dispatch(ProtocolKeyword protocolKeyword, CommandOutput commandOutput, CommandArgs commandArgs) {
        return null;
    }

    @Override
    public Flux dispatch(ProtocolKeyword protocolKeyword, CommandOutput commandOutput) {
        return null;
    }

    @Override
    public Mono<Set<AclCategory>> aclCat() {
        return null;
    }

    @Override
    public Mono<Set<CommandType>> aclCat(AclCategory aclCategory) {
        return null;
    }

    @Override
    public Mono<Long> aclDeluser(String... strings) {
        return null;
    }

    @Override
    public Mono<String> aclDryRun(String s, String s1, String... strings) {
        return null;
    }

    @Override
    public Mono<String> aclGenpass() {
        return null;
    }

    @Override
    public Mono<String> aclGenpass(int i) {
        return null;
    }

    @Override
    public Mono<List<Object>> aclGetuser(String s) {
        return null;
    }

    @Override
    public Flux<String> aclList() {
        return null;
    }

    @Override
    public Mono<String> aclLoad() {
        return null;
    }

    @Override
    public Flux<Map<String, Object>> aclLog() {
        return null;
    }

    @Override
    public Flux<Map<String, Object>> aclLog(int i) {
        return null;
    }

    @Override
    public Mono<String> aclLogReset() {
        return null;
    }

    @Override
    public Mono<String> aclSave() {
        return null;
    }

    @Override
    public Mono<String> aclSetuser(String s, AclSetuserArgs aclSetuserArgs) {
        return null;
    }

    @Override
    public Flux<String> aclUsers() {
        return null;
    }

    @Override
    public Mono<String> aclWhoami() {
        return null;
    }

    @Override
    public Mono<String> aclDryRun(String s, RedisCommand redisCommand) {
        return null;
    }

    @Override
    public Flux fcall(String s, ScriptOutputType scriptOutputType, Object[] objects, Object[] objects2) {
        return null;
    }

    @Override
    public Flux fcallReadOnly(String s, ScriptOutputType scriptOutputType, Object[] objects) {
        return null;
    }

    @Override
    public Flux fcallReadOnly(String s, ScriptOutputType scriptOutputType, Object[] objects, Object[] objects2) {
        return null;
    }

    @Override
    public Flux fcall(String s, ScriptOutputType scriptOutputType, Object[] objects) {
        return null;
    }

    @Override
    public Mono<String> functionLoad(String s) {
        return null;
    }

    @Override
    public Mono<String> functionLoad(String s, boolean b) {
        return null;
    }

    @Override
    public Mono<byte[]> functionDump() {
        return null;
    }

    @Override
    public Mono<String> functionRestore(byte[] bytes) {
        return null;
    }

    @Override
    public Mono<String> functionRestore(byte[] bytes, FunctionRestoreMode functionRestoreMode) {
        return null;
    }

    @Override
    public Mono<String> functionFlush(FlushMode flushMode) {
        return null;
    }

    @Override
    public Mono<String> functionKill() {
        return null;
    }

    @Override
    public Flux<Map<String, Object>> functionList() {
        return null;
    }

    @Override
    public Flux<Map<String, Object>> functionList(String s) {
        return null;
    }

    @Override
    public Mono<Long> geoadd(Object o, double v, double v1, Object v2) {
        return null;
    }

    @Override
    public Mono<Long> geoadd(Object o, double v, double v1, Object v2, GeoAddArgs geoAddArgs) {
        return null;
    }

    @Override
    public Mono<Long> geoadd(Object o, Object... objects) {
        return null;
    }

    @Override
    public Mono<Long> geoadd(Object o, GeoValue[] geoValues) {
        return null;
    }

    @Override
    public Mono<Long> geoadd(Object o, GeoAddArgs geoAddArgs, Object... objects) {
        return null;
    }

    @Override
    public Mono<Long> geoadd(Object o, GeoAddArgs geoAddArgs, GeoValue[] geoValues) {
        return null;
    }

    @Override
    public Mono<Double> geodist(Object o, Object o2, Object v1, GeoArgs.Unit unit) {
        return null;
    }

    @Override
    public Flux<Value<String>> geohash(Object o, Object[] objects) {
        return null;
    }

    @Override
    public Flux<Value<GeoCoordinates>> geopos(Object o, Object[] objects) {
        return null;
    }

    @Override
    public Flux georadius(Object o, double v, double v1, double v2, GeoArgs.Unit unit) {
        return null;
    }

    @Override
    public Flux<GeoWithin> georadius(Object o, double v, double v1, double v2, GeoArgs.Unit unit, GeoArgs geoArgs) {
        return null;
    }

    @Override
    public Mono<Long> georadius(Object o, double v, double v1, double v2, GeoArgs.Unit unit, GeoRadiusStoreArgs geoRadiusStoreArgs) {
        return null;
    }

    @Override
    public Flux georadiusbymember(Object o, Object o2, double v1, GeoArgs.Unit unit) {
        return null;
    }

    @Override
    public Flux<GeoWithin> georadiusbymember(Object o, Object o2, double v1, GeoArgs.Unit unit, GeoArgs geoArgs) {
        return null;
    }

    @Override
    public Mono<Long> georadiusbymember(Object o, Object o2, double v1, GeoArgs.Unit unit, GeoRadiusStoreArgs geoRadiusStoreArgs) {
        return null;
    }

    @Override
    public Flux geosearch(Object o, GeoSearch.GeoRef geoRef, GeoSearch.GeoPredicate geoPredicate) {
        return null;
    }

    @Override
    public Flux<GeoWithin> geosearch(Object o, GeoSearch.GeoRef geoRef, GeoSearch.GeoPredicate geoPredicate, GeoArgs geoArgs) {
        return null;
    }

    @Override
    public Mono<Long> geosearchstore(Object o, Object k1, GeoSearch.GeoRef geoRef, GeoSearch.GeoPredicate geoPredicate, GeoArgs geoArgs, boolean b) {
        return null;
    }

    @Override
    public Mono<Long> pfadd(Object o, Object[] objects) {
        return null;
    }

    @Override
    public Mono<String> pfmerge(Object o, Object[] objects) {
        return null;
    }

    @Override
    public Mono<Long> pfcount(Object[] objects) {
        return null;
    }

    @Override
    public Mono<Long> hdel(Object o, Object[] objects) {
        return null;
    }

    @Override
    public Mono<Boolean> hexists(Object o, Object k1) {
        return null;
    }

    @Override
    public Mono hget(Object o, Object k1) {
        return null;
    }

    @Override
    public Mono<Long> hincrby(Object o, Object k1, long l) {
        return null;
    }

    @Override
    public Mono<Double> hincrbyfloat(Object o, Object k1, double v) {
        return null;
    }

    @Override
    public Flux<KeyValue> hgetall(Object o) {
        return null;
    }

    /**
     * @param keyValueStreamingChannel
     * @param o
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> hgetall(KeyValueStreamingChannel keyValueStreamingChannel, Object o) {
        return null;
    }

    @Override
    public Flux hkeys(Object o) {
        return null;
    }

    /**
     * @param keyStreamingChannel
     * @param o
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> hkeys(KeyStreamingChannel keyStreamingChannel, Object o) {
        return null;
    }

    @Override
    public Mono<Long> hlen(Object o) {
        return null;
    }

    @Override
    public Flux<KeyValue> hmget(Object o, Object[] objects) {
        return null;
    }

    /**
     * @param keyValueStreamingChannel
     * @param o
     * @param objects
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> hmget(KeyValueStreamingChannel keyValueStreamingChannel, Object o, Object[] objects) {
        return null;
    }

    @Override
    public Mono<String> hmset(Object o, Map map) {
        return null;
    }

    @Override
    public Mono hrandfield(Object o) {
        return null;
    }

    @Override
    public Flux hrandfield(Object o, long l) {
        return null;
    }

    @Override
    public Mono<KeyValue> hrandfieldWithvalues(Object o) {
        return null;
    }

    @Override
    public Flux<KeyValue> hrandfieldWithvalues(Object o, long l) {
        return null;
    }

    @Override
    public Mono<MapScanCursor> hscan(Object o) {
        return null;
    }

    @Override
    public Mono<MapScanCursor> hscan(Object o, ScanArgs scanArgs) {
        return null;
    }

    @Override
    public Mono<MapScanCursor> hscan(Object o, ScanCursor scanCursor, ScanArgs scanArgs) {
        return null;
    }

    @Override
    public Mono<MapScanCursor> hscan(Object o, ScanCursor scanCursor) {
        return null;
    }

    /**
     * @param keyValueStreamingChannel
     * @param o
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<StreamScanCursor> hscan(KeyValueStreamingChannel keyValueStreamingChannel, Object o) {
        return null;
    }

    /**
     * @param keyValueStreamingChannel
     * @param o
     * @param scanArgs
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<StreamScanCursor> hscan(KeyValueStreamingChannel keyValueStreamingChannel, Object o, ScanArgs scanArgs) {
        return null;
    }

    /**
     * @param keyValueStreamingChannel
     * @param o
     * @param scanCursor
     * @param scanArgs
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<StreamScanCursor> hscan(KeyValueStreamingChannel keyValueStreamingChannel, Object o, ScanCursor scanCursor, ScanArgs scanArgs) {
        return null;
    }

    /**
     * @param keyValueStreamingChannel
     * @param o
     * @param scanCursor
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<StreamScanCursor> hscan(KeyValueStreamingChannel keyValueStreamingChannel, Object o, ScanCursor scanCursor) {
        return null;
    }

    @Override
    public Mono<Boolean> hset(Object o, Object k1, Object o2) {
        return null;
    }

    @Override
    public Mono<Long> hset(Object o, Map map) {
        return null;
    }

    @Override
    public Mono<Boolean> hsetnx(Object o, Object k1, Object o2) {
        return null;
    }

    @Override
    public Mono<Long> hstrlen(Object o, Object k1) {
        return null;
    }

    @Override
    public Flux hvals(Object o) {
        return null;
    }

    /**
     * @param valueStreamingChannel
     * @param o
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> hvals(ValueStreamingChannel valueStreamingChannel, Object o) {
        return null;
    }

    @Override
    public Mono<Boolean> copy(Object o, Object k1) {
        return null;
    }

    @Override
    public Mono<Boolean> copy(Object o, Object k1, CopyArgs copyArgs) {
        return null;
    }

    @Override
    public Mono<Long> del(Object[] objects) {
        return null;
    }

    @Override
    public Mono<Long> unlink(Object[] objects) {
        return null;
    }

    @Override
    public Mono<byte[]> dump(Object o) {
        return null;
    }

    @Override
    public Mono<Long> exists(Object[] objects) {
        return null;
    }

    @Override
    public Mono<Boolean> expire(Object o, long l) {
        return null;
    }

    @Override
    public Mono<Boolean> expire(Object o, long l, ExpireArgs expireArgs) {
        return null;
    }

    @Override
    public Mono<Boolean> expire(Object o, Duration duration) {
        return null;
    }

    @Override
    public Mono<Boolean> expire(Object o, Duration duration, ExpireArgs expireArgs) {
        return null;
    }

    @Override
    public Mono<Boolean> expireat(Object o, long l) {
        return null;
    }

    @Override
    public Mono<Boolean> expireat(Object o, long l, ExpireArgs expireArgs) {
        return null;
    }

    @Override
    public Mono<Boolean> expireat(Object o, Date date) {
        return null;
    }

    @Override
    public Mono<Boolean> expireat(Object o, Date date, ExpireArgs expireArgs) {
        return null;
    }

    @Override
    public Mono<Boolean> expireat(Object o, Instant instant) {
        return null;
    }

    @Override
    public Mono<Boolean> expireat(Object o, Instant instant, ExpireArgs expireArgs) {
        return null;
    }

    @Override
    public Mono<Long> expiretime(Object o) {
        return null;
    }

    @Override
    public Flux keys(Object o) {
        return null;
    }

    /**
     * @param keyStreamingChannel
     * @param o
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> keys(KeyStreamingChannel keyStreamingChannel, Object o) {
        return null;
    }

    @Override
    public Mono<String> migrate(String s, int i, Object o, int i1, long l) {
        return null;
    }

    @Override
    public Mono<String> migrate(String s, int i, int i1, long l, MigrateArgs migrateArgs) {
        return null;
    }

    @Override
    public Mono<Boolean> move(Object o, int i) {
        return null;
    }

    @Override
    public Mono<String> objectEncoding(Object o) {
        return null;
    }

    @Override
    public Mono<Long> objectFreq(Object o) {
        return null;
    }

    @Override
    public Mono<Long> objectIdletime(Object o) {
        return null;
    }

    @Override
    public Mono<Long> objectRefcount(Object o) {
        return null;
    }

    @Override
    public Mono<Boolean> persist(Object o) {
        return null;
    }

    @Override
    public Mono<Boolean> pexpire(Object o, long l) {
        return null;
    }

    @Override
    public Mono<Boolean> pexpire(Object o, long l, ExpireArgs expireArgs) {
        return null;
    }

    @Override
    public Mono<Boolean> pexpire(Object o, Duration duration) {
        return null;
    }

    @Override
    public Mono<Boolean> pexpire(Object o, Duration duration, ExpireArgs expireArgs) {
        return null;
    }

    @Override
    public Mono<Boolean> pexpireat(Object o, long l) {
        return null;
    }

    @Override
    public Mono<Boolean> pexpireat(Object o, long l, ExpireArgs expireArgs) {
        return null;
    }

    @Override
    public Mono<Boolean> pexpireat(Object o, Date date) {
        return null;
    }

    @Override
    public Mono<Boolean> pexpireat(Object o, Date date, ExpireArgs expireArgs) {
        return null;
    }

    @Override
    public Mono<Boolean> pexpireat(Object o, Instant instant) {
        return null;
    }

    @Override
    public Mono<Boolean> pexpireat(Object o, Instant instant, ExpireArgs expireArgs) {
        return null;
    }

    @Override
    public Mono<Long> pexpiretime(Object o) {
        return null;
    }

    @Override
    public Mono<Long> pttl(Object o) {
        return null;
    }

    @Override
    public Mono randomkey() {
        return null;
    }

    @Override
    public Mono<String> rename(Object o, Object k1) {
        return null;
    }

    @Override
    public Mono<Boolean> renamenx(Object o, Object k1) {
        return null;
    }

    @Override
    public Mono<String> restore(Object o, long l, byte[] bytes) {
        return null;
    }

    @Override
    public Mono<String> restore(Object o, byte[] bytes, RestoreArgs restoreArgs) {
        return null;
    }

    @Override
    public Flux sort(Object o) {
        return null;
    }

    /**
     * @param valueStreamingChannel
     * @param o
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> sort(ValueStreamingChannel valueStreamingChannel, Object o) {
        return null;
    }

    @Override
    public Flux sort(Object o, SortArgs sortArgs) {
        return null;
    }

    /**
     * @param valueStreamingChannel
     * @param o
     * @param sortArgs
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> sort(ValueStreamingChannel valueStreamingChannel, Object o, SortArgs sortArgs) {
        return null;
    }

    @Override
    public Flux sortReadOnly(Object o) {
        return null;
    }

    /**
     * @param valueStreamingChannel
     * @param o
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> sortReadOnly(ValueStreamingChannel valueStreamingChannel, Object o) {
        return null;
    }

    @Override
    public Flux sortReadOnly(Object o, SortArgs sortArgs) {
        return null;
    }

    /**
     * @param valueStreamingChannel
     * @param o
     * @param sortArgs
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> sortReadOnly(ValueStreamingChannel valueStreamingChannel, Object o, SortArgs sortArgs) {
        return null;
    }

    @Override
    public Mono<Long> sortStore(Object o, SortArgs sortArgs, Object k1) {
        return null;
    }

    @Override
    public Mono<Long> touch(Object[] objects) {
        return null;
    }

    @Override
    public Mono<Long> ttl(Object o) {
        return null;
    }

    @Override
    public Mono<String> type(Object o) {
        return null;
    }

    @Override
    public Mono<KeyScanCursor> scan() {
        return null;
    }

    @Override
    public Mono<KeyScanCursor> scan(ScanArgs scanArgs) {
        return null;
    }

    @Override
    public Mono<KeyScanCursor> scan(ScanCursor scanCursor, ScanArgs scanArgs) {
        return null;
    }

    @Override
    public Mono<KeyScanCursor> scan(ScanCursor scanCursor) {
        return null;
    }

    /**
     * @param keyStreamingChannel
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<StreamScanCursor> scan(KeyStreamingChannel keyStreamingChannel) {
        return null;
    }

    /**
     * @param keyStreamingChannel
     * @param scanArgs
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<StreamScanCursor> scan(KeyStreamingChannel keyStreamingChannel, ScanArgs scanArgs) {
        return null;
    }

    /**
     * @param keyStreamingChannel
     * @param scanCursor
     * @param scanArgs
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<StreamScanCursor> scan(KeyStreamingChannel keyStreamingChannel, ScanCursor scanCursor, ScanArgs scanArgs) {
        return null;
    }

    /**
     * @param keyStreamingChannel
     * @param scanCursor
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<StreamScanCursor> scan(KeyStreamingChannel keyStreamingChannel, ScanCursor scanCursor) {
        return null;
    }

    @Override
    public Mono blmove(Object o, Object k1, LMoveArgs lMoveArgs, long l) {
        return null;
    }

    @Override
    public Mono blmove(Object o, Object k1, LMoveArgs lMoveArgs, double v) {
        return null;
    }

    @Override
    public Mono<KeyValue> blmpop(long l, LMPopArgs lmPopArgs, Object[] objects) {
        return null;
    }

    @Override
    public Mono<KeyValue> blmpop(double v, LMPopArgs lmPopArgs, Object[] objects) {
        return null;
    }

    @Override
    public Mono<KeyValue> blpop(long l, Object[] objects) {
        return null;
    }

    @Override
    public Mono<KeyValue> blpop(double v, Object[] objects) {
        return null;
    }

    @Override
    public Mono<KeyValue> brpop(long l, Object[] objects) {
        return null;
    }

    @Override
    public Mono<KeyValue> brpop(double v, Object[] objects) {
        return null;
    }

    @Override
    public Mono brpoplpush(long l, Object o, Object k1) {
        return null;
    }

    @Override
    public Mono brpoplpush(double v, Object o, Object k1) {
        return null;
    }

    @Override
    public Mono lindex(Object o, long l) {
        return null;
    }

    @Override
    public Mono<Long> linsert(Object o, boolean b, Object o2, Object v1) {
        return null;
    }

    @Override
    public Mono<Long> llen(Object o) {
        return null;
    }

    @Override
    public Mono lmove(Object o, Object k1, LMoveArgs lMoveArgs) {
        return null;
    }

    @Override
    public Mono<KeyValue> lmpop(LMPopArgs lmPopArgs, Object[] objects) {
        return null;
    }

    @Override
    public Mono lpop(Object o) {
        return null;
    }

    @Override
    public Flux lpop(Object o, long l) {
        return null;
    }

    @Override
    public Mono<Long> lpos(Object o, Object o2) {
        return null;
    }

    @Override
    public Mono<Long> lpos(Object o, Object o2, LPosArgs lPosArgs) {
        return null;
    }

    @Override
    public Flux<Long> lpos(Object o, Object o2, int i) {
        return null;
    }

    @Override
    public Flux<Long> lpos(Object o, Object o2, int i, LPosArgs lPosArgs) {
        return null;
    }

    @Override
    public Mono<Long> lpush(Object o, Object[] objects) {
        return null;
    }

    @Override
    public Mono<Long> lpushx(Object o, Object[] objects) {
        return null;
    }

    @Override
    public Flux lrange(Object o, long l, long l1) {
        return null;
    }

    /**
     * @param valueStreamingChannel
     * @param o
     * @param l
     * @param l1
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> lrange(ValueStreamingChannel valueStreamingChannel, Object o, long l, long l1) {
        return null;
    }

    @Override
    public Mono<Long> lrem(Object o, long l, Object o2) {
        return null;
    }

    @Override
    public Mono<String> lset(Object o, long l, Object o2) {
        return null;
    }

    @Override
    public Mono<String> ltrim(Object o, long l, long l1) {
        return null;
    }

    @Override
    public Mono rpop(Object o) {
        return null;
    }

    @Override
    public Flux rpop(Object o, long l) {
        return null;
    }

    @Override
    public Mono rpoplpush(Object o, Object k1) {
        return null;
    }

    @Override
    public Mono<Long> rpush(Object o, Object[] objects) {
        return null;
    }

    @Override
    public Mono<Long> rpushx(Object o, Object[] objects) {
        return null;
    }

    @Override
    public Flux eval(String s, ScriptOutputType scriptOutputType, Object[] objects) {
        return null;
    }

    @Override
    public Flux eval(byte[] bytes, ScriptOutputType scriptOutputType, Object[] objects) {
        return null;
    }

    @Override
    public Flux eval(String s, ScriptOutputType scriptOutputType, Object[] objects, Object[] objects2) {
        return null;
    }

    @Override
    public Flux eval(byte[] bytes, ScriptOutputType scriptOutputType, Object[] objects, Object[] objects2) {
        return null;
    }

    @Override
    public Flux evalReadOnly(byte[] bytes, ScriptOutputType scriptOutputType, Object[] objects, Object[] objects2) {
        return null;
    }

    @Override
    public Flux evalsha(String s, ScriptOutputType scriptOutputType, Object[] objects) {
        return null;
    }

    @Override
    public Flux evalsha(String s, ScriptOutputType scriptOutputType, Object[] objects, Object[] objects2) {
        return null;
    }

    @Override
    public Flux evalshaReadOnly(String s, ScriptOutputType scriptOutputType, Object[] objects, Object[] objects2) {
        return null;
    }

    @Override
    public Flux<Boolean> scriptExists(String... strings) {
        return null;
    }

    @Override
    public Mono<String> scriptFlush() {
        return null;
    }

    @Override
    public Mono<String> scriptFlush(FlushMode flushMode) {
        return null;
    }

    @Override
    public Mono<String> scriptKill() {
        return null;
    }

    @Override
    public Mono<String> scriptLoad(String s) {
        return null;
    }

    @Override
    public Mono<String> scriptLoad(byte[] bytes) {
        return null;
    }

    @Override
    public String digest(String s) {
        return null;
    }

    @Override
    public String digest(byte[] bytes) {
        return null;
    }

    @Override
    public Mono<String> bgrewriteaof() {
        return null;
    }

    @Override
    public Mono<String> bgsave() {
        return null;
    }

    @Override
    public Mono<String> clientCaching(boolean b) {
        return null;
    }

    @Override
    public Mono clientGetname() {
        return null;
    }

    @Override
    public Mono<Long> clientGetredir() {
        return null;
    }

    @Override
    public Mono<Long> clientId() {
        return null;
    }

    @Override
    public Mono<String> clientKill(String s) {
        return null;
    }

    @Override
    public Mono<Long> clientKill(KillArgs killArgs) {
        return null;
    }

    @Override
    public Mono<String> clientList() {
        return null;
    }

    @Override
    public Mono<String> clientList(ClientListArgs clientListArgs) {
        return null;
    }

    @Override
    public Mono<String> clientInfo() {
        return null;
    }

    @Override
    public Mono<String> clientNoEvict(boolean b) {
        return null;
    }

    @Override
    public Mono<String> clientPause(long l) {
        return null;
    }

    @Override
    public Mono<String> clientSetname(Object o) {
        return null;
    }

    @Override
    public Mono<String> clientSetinfo(String s, String s1) {
        return null;
    }

    @Override
    public Mono<String> clientTracking(TrackingArgs trackingArgs) {
        return null;
    }

    @Override
    public Mono<Long> clientUnblock(long l, UnblockType unblockType) {
        return null;
    }

    @Override
    public Flux<Object> command() {
        return null;
    }

    @Override
    public Mono<Long> commandCount() {
        return null;
    }

    @Override
    public Flux<Object> commandInfo(String... strings) {
        return null;
    }

    @Override
    public Flux<Object> commandInfo(CommandType... commandTypes) {
        return null;
    }

    @Override
    public Mono<Map<String, String>> configGet(String s) {
        return null;
    }

    @Override
    public Mono<Map<String, String>> configGet(String... strings) {
        return null;
    }

    @Override
    public Mono<String> configResetstat() {
        return null;
    }

    @Override
    public Mono<String> configRewrite() {
        return null;
    }

    @Override
    public Mono<String> configSet(String s, String s1) {
        return null;
    }

    @Override
    public Mono<Long> dbsize() {
        return null;
    }

    @Override
    public Mono<String> debugCrashAndRecover(Long aLong) {
        return null;
    }

    @Override
    public Mono<String> debugHtstats(int i) {
        return null;
    }

    @Override
    public Mono<String> debugObject(Object o) {
        return null;
    }

    @Override
    public Mono<Void> debugOom() {
        return null;
    }

    @Override
    public Mono<String> debugReload() {
        return null;
    }

    @Override
    public Mono<String> debugRestart(Long aLong) {
        return null;
    }

    @Override
    public Mono<String> debugSdslen(Object o) {
        return null;
    }

    @Override
    public Mono<Void> debugSegfault() {
        return null;
    }

    @Override
    public Mono<String> flushall() {
        return null;
    }

    @Override
    public Mono<String> flushall(FlushMode flushMode) {
        return null;
    }

    /**
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<String> flushallAsync() {
        return null;
    }

    @Override
    public Mono<String> flushdb() {
        return null;
    }

    @Override
    public Mono<String> flushdb(FlushMode flushMode) {
        return null;
    }

    /**
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<String> flushdbAsync() {
        return null;
    }

    @Override
    public Mono<String> info() {
        return null;
    }

    @Override
    public Mono<String> info(String s) {
        return null;
    }

    @Override
    public Mono<Date> lastsave() {
        return null;
    }

    @Override
    public Mono<Long> memoryUsage(Object o) {
        return null;
    }

    @Override
    public Mono<String> replicaof(String s, int i) {
        return null;
    }

    @Override
    public Mono<String> replicaofNoOne() {
        return null;
    }

    @Override
    public Mono<String> save() {
        return null;
    }

    @Override
    public Mono<Void> shutdown(boolean b) {
        return null;
    }

    @Override
    public Mono<Void> shutdown(ShutdownArgs shutdownArgs) {
        return null;
    }

    /**
     * @param s
     * @param i
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<String> slaveof(String s, int i) {
        return null;
    }

    /**
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<String> slaveofNoOne() {
        return null;
    }

    @Override
    public Flux<Object> slowlogGet() {
        return null;
    }

    @Override
    public Flux<Object> slowlogGet(int i) {
        return null;
    }

    @Override
    public Mono<Long> slowlogLen() {
        return null;
    }

    @Override
    public Mono<String> slowlogReset() {
        return null;
    }

    @Override
    public Flux time() {
        return null;
    }

    @Override
    public Mono<String> configSet(Map map) {
        return null;
    }

    @Override
    public Mono<Long> sadd(Object o, Object[] objects) {
        return null;
    }

    @Override
    public Mono<Long> scard(Object o) {
        return null;
    }

    @Override
    public Flux sdiff(Object[] objects) {
        return null;
    }

    /**
     * @param valueStreamingChannel
     * @param objects
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> sdiff(ValueStreamingChannel valueStreamingChannel, Object[] objects) {
        return null;
    }

    @Override
    public Mono<Long> sdiffstore(Object o, Object[] objects) {
        return null;
    }

    @Override
    public Flux sinter(Object[] objects) {
        return null;
    }

    /**
     * @param valueStreamingChannel
     * @param objects
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> sinter(ValueStreamingChannel valueStreamingChannel, Object[] objects) {
        return null;
    }

    @Override
    public Mono<Long> sintercard(Object[] objects) {
        return null;
    }

    @Override
    public Mono<Long> sintercard(long l, Object[] objects) {
        return null;
    }

    @Override
    public Mono<Long> sinterstore(Object o, Object[] objects) {
        return null;
    }

    @Override
    public Mono<Boolean> sismember(Object o, Object o2) {
        return null;
    }

    @Override
    public Flux smembers(Object o) {
        return null;
    }

    /**
     * @param valueStreamingChannel
     * @param o
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> smembers(ValueStreamingChannel valueStreamingChannel, Object o) {
        return null;
    }

    @Override
    public Flux<Boolean> smismember(Object o, Object[] objects) {
        return null;
    }

    @Override
    public Mono<Boolean> smove(Object o, Object k1, Object o2) {
        return null;
    }

    @Override
    public Mono spop(Object o) {
        return null;
    }

    @Override
    public Flux spop(Object o, long l) {
        return null;
    }

    @Override
    public Mono srandmember(Object o) {
        return null;
    }

    @Override
    public Flux srandmember(Object o, long l) {
        return null;
    }

    /**
     * @param valueStreamingChannel
     * @param o
     * @param l
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> srandmember(ValueStreamingChannel valueStreamingChannel, Object o, long l) {
        return null;
    }

    @Override
    public Mono<Long> srem(Object o, Object[] objects) {
        return null;
    }

    @Override
    public Flux sunion(Object[] objects) {
        return null;
    }

    /**
     * @param valueStreamingChannel
     * @param objects
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> sunion(ValueStreamingChannel valueStreamingChannel, Object[] objects) {
        return null;
    }

    @Override
    public Mono<Long> sunionstore(Object o, Object[] objects) {
        return null;
    }

    @Override
    public Mono<ValueScanCursor> sscan(Object o) {
        return null;
    }

    @Override
    public Mono<ValueScanCursor> sscan(Object o, ScanArgs scanArgs) {
        return null;
    }

    @Override
    public Mono<ValueScanCursor> sscan(Object o, ScanCursor scanCursor, ScanArgs scanArgs) {
        return null;
    }

    @Override
    public Mono<ValueScanCursor> sscan(Object o, ScanCursor scanCursor) {
        return null;
    }

    /**
     * @param valueStreamingChannel
     * @param o
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<StreamScanCursor> sscan(ValueStreamingChannel valueStreamingChannel, Object o) {
        return null;
    }

    /**
     * @param valueStreamingChannel
     * @param o
     * @param scanArgs
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<StreamScanCursor> sscan(ValueStreamingChannel valueStreamingChannel, Object o, ScanArgs scanArgs) {
        return null;
    }

    /**
     * @param valueStreamingChannel
     * @param o
     * @param scanCursor
     * @param scanArgs
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<StreamScanCursor> sscan(ValueStreamingChannel valueStreamingChannel, Object o, ScanCursor scanCursor, ScanArgs scanArgs) {
        return null;
    }

    /**
     * @param valueStreamingChannel
     * @param o
     * @param scanCursor
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<StreamScanCursor> sscan(ValueStreamingChannel valueStreamingChannel, Object o, ScanCursor scanCursor) {
        return null;
    }

    @Override
    public Mono<KeyValue> bzmpop(long l, ZPopArgs zPopArgs, Object[] objects) {
        return null;
    }

    @Override
    public Mono<KeyValue> bzmpop(long l, long l1, ZPopArgs zPopArgs, Object[] objects) {
        return null;
    }

    @Override
    public Mono<KeyValue> bzmpop(double v, ZPopArgs zPopArgs, Object[] objects) {
        return null;
    }

    @Override
    public Mono<KeyValue> bzmpop(double v, int i, ZPopArgs zPopArgs, Object[] objects) {
        return null;
    }

    @Override
    public Mono<KeyValue> bzpopmin(long l, Object[] objects) {
        return null;
    }

    @Override
    public Mono<KeyValue> bzpopmin(double v, Object[] objects) {
        return null;
    }

    @Override
    public Mono<KeyValue> bzpopmax(long l, Object[] objects) {
        return null;
    }

    @Override
    public Mono<KeyValue> bzpopmax(double v, Object[] objects) {
        return null;
    }

    @Override
    public Mono<Long> zadd(Object o, double v, Object v1) {
        return null;
    }

    @Override
    public Mono<Long> zadd(Object o, Object... objects) {
        return null;
    }

    @Override
    public Mono<Long> zadd(Object o, ScoredValue[] scoredValues) {
        return null;
    }

    @Override
    public Mono<Long> zadd(Object o, ZAddArgs zAddArgs, double v, Object v1) {
        return null;
    }

    @Override
    public Mono<Long> zadd(Object o, ZAddArgs zAddArgs, Object... objects) {
        return null;
    }

    @Override
    public Mono<Long> zadd(Object o, ZAddArgs zAddArgs, ScoredValue[] scoredValues) {
        return null;
    }

    @Override
    public Mono<Double> zaddincr(Object o, double v, Object v1) {
        return null;
    }

    @Override
    public Mono<Double> zaddincr(Object o, ZAddArgs zAddArgs, double v, Object v1) {
        return null;
    }

    @Override
    public Mono<Long> zcard(Object o) {
        return null;
    }

    /**
     * @param o
     * @param v
     * @param v1
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> zcount(Object o, double v, double v1) {
        return null;
    }

    /**
     * @param o
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> zcount(Object o, String s, String s1) {
        return null;
    }

    @Override
    public Flux zdiff(Object[] objects) {
        return null;
    }

    @Override
    public Mono<Long> zdiffstore(Object o, Object[] objects) {
        return null;
    }

    @Override
    public Flux<ScoredValue> zdiffWithScores(Object[] objects) {
        return null;
    }

    @Override
    public Mono<Double> zincrby(Object o, double v, Object v1) {
        return null;
    }

    @Override
    public Flux zinter(Object[] objects) {
        return null;
    }

    @Override
    public Flux zinter(ZAggregateArgs zAggregateArgs, Object[] objects) {
        return null;
    }

    @Override
    public Mono<Long> zintercard(Object[] objects) {
        return null;
    }

    @Override
    public Mono<Long> zintercard(long l, Object[] objects) {
        return null;
    }

    @Override
    public Flux<ScoredValue> zinterWithScores(ZAggregateArgs zAggregateArgs, Object[] objects) {
        return null;
    }

    @Override
    public Flux<ScoredValue> zinterWithScores(Object[] objects) {
        return null;
    }

    @Override
    public Mono<Long> zinterstore(Object o, Object[] objects) {
        return null;
    }

    @Override
    public Mono<Long> zinterstore(Object o, ZStoreArgs zStoreArgs, Object[] objects) {
        return null;
    }

    /**
     * @param o
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> zlexcount(Object o, String s, String s1) {
        return null;
    }

    @Override
    public Mono<Long> zlexcount(Object o, Range range) {
        return null;
    }

    @Override
    public Mono<List<Double>> zmscore(Object o, Object[] objects) {
        return null;
    }

    @Override
    public Mono<KeyValue> zmpop(ZPopArgs zPopArgs, Object[] objects) {
        return null;
    }

    @Override
    public Mono<KeyValue> zmpop(int i, ZPopArgs zPopArgs, Object[] objects) {
        return null;
    }

    @Override
    public Mono<ScoredValue> zpopmin(Object o) {
        return null;
    }

    @Override
    public Flux<ScoredValue> zpopmin(Object o, long l) {
        return null;
    }

    @Override
    public Mono<ScoredValue> zpopmax(Object o) {
        return null;
    }

    @Override
    public Flux<ScoredValue> zpopmax(Object o, long l) {
        return null;
    }

    @Override
    public Mono zrandmember(Object o) {
        return null;
    }

    @Override
    public Flux zrandmember(Object o, long l) {
        return null;
    }

    @Override
    public Mono<ScoredValue> zrandmemberWithScores(Object o) {
        return null;
    }

    @Override
    public Flux<ScoredValue> zrandmemberWithScores(Object o, long l) {
        return null;
    }

    @Override
    public Flux zrange(Object o, long l, long l1) {
        return null;
    }

    /**
     * @param valueStreamingChannel
     * @param o
     * @param l
     * @param l1
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> zrange(ValueStreamingChannel valueStreamingChannel, Object o, long l, long l1) {
        return null;
    }

    @Override
    public Flux<ScoredValue> zrangeWithScores(Object o, long l, long l1) {
        return null;
    }

    /**
     * @param scoredValueStreamingChannel
     * @param o
     * @param l
     * @param l1
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> zrangeWithScores(ScoredValueStreamingChannel scoredValueStreamingChannel, Object o, long l, long l1) {
        return null;
    }

    /**
     * @param o
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    @Deprecated
    public Flux zrangebylex(Object o, String s, String s1) {
        return null;
    }

    @Override
    public Flux zrangebylex(Object o, Range range) {
        return null;
    }

    /**
     * @param o
     * @param s
     * @param s1
     * @param l
     * @param l1
     * @deprecated
     */
    @Override
    @Deprecated
    public Flux zrangebylex(Object o, String s, String s1, long l, long l1) {
        return null;
    }

    @Override
    public Flux zrangebylex(Object o, Range range, Limit limit) {
        return null;
    }

    /**
     * @param o
     * @param v
     * @param v1
     * @deprecated
     */
    @Override
    @Deprecated
    public Flux zrangebyscore(Object o, double v, double v1) {
        return null;
    }

    /**
     * @param o
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    @Deprecated
    public Flux zrangebyscore(Object o, String s, String s1) {
        return null;
    }

    /**
     * @param o
     * @param v
     * @param v1
     * @param l
     * @param l1
     * @deprecated
     */
    @Override
    @Deprecated
    public Flux zrangebyscore(Object o, double v, double v1, long l, long l1) {
        return null;
    }

    /**
     * @param o
     * @param s
     * @param s1
     * @param l
     * @param l1
     * @deprecated
     */
    @Override
    @Deprecated
    public Flux zrangebyscore(Object o, String s, String s1, long l, long l1) {
        return null;
    }

    /**
     * @param valueStreamingChannel
     * @param o
     * @param v
     * @param v1
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> zrangebyscore(ValueStreamingChannel valueStreamingChannel, Object o, double v, double v1) {
        return null;
    }

    /**
     * @param valueStreamingChannel
     * @param o
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> zrangebyscore(ValueStreamingChannel valueStreamingChannel, Object o, String s, String s1) {
        return null;
    }

    /**
     * @param valueStreamingChannel
     * @param o
     * @param v
     * @param v1
     * @param l
     * @param l1
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> zrangebyscore(ValueStreamingChannel valueStreamingChannel, Object o, double v, double v1, long l, long l1) {
        return null;
    }

    /**
     * @param valueStreamingChannel
     * @param o
     * @param s
     * @param s1
     * @param l
     * @param l1
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> zrangebyscore(ValueStreamingChannel valueStreamingChannel, Object o, String s, String s1, long l, long l1) {
        return null;
    }

    /**
     * @param o
     * @param v
     * @param v1
     * @deprecated
     */
    @Override
    @Deprecated
    public Flux<ScoredValue> zrangebyscoreWithScores(Object o, double v, double v1) {
        return null;
    }

    /**
     * @param o
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    @Deprecated
    public Flux<ScoredValue> zrangebyscoreWithScores(Object o, String s, String s1) {
        return null;
    }

    /**
     * @param o
     * @param v
     * @param v1
     * @param l
     * @param l1
     * @deprecated
     */
    @Override
    @Deprecated
    public Flux<ScoredValue> zrangebyscoreWithScores(Object o, double v, double v1, long l, long l1) {
        return null;
    }

    /**
     * @param o
     * @param s
     * @param s1
     * @param l
     * @param l1
     * @deprecated
     */
    @Override
    @Deprecated
    public Flux<ScoredValue> zrangebyscoreWithScores(Object o, String s, String s1, long l, long l1) {
        return null;
    }

    /**
     * @param scoredValueStreamingChannel
     * @param o
     * @param v
     * @param v1
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> zrangebyscoreWithScores(ScoredValueStreamingChannel scoredValueStreamingChannel, Object o, double v, double v1) {
        return null;
    }

    /**
     * @param scoredValueStreamingChannel
     * @param o
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> zrangebyscoreWithScores(ScoredValueStreamingChannel scoredValueStreamingChannel, Object o, String s, String s1) {
        return null;
    }

    /**
     * @param scoredValueStreamingChannel
     * @param o
     * @param v
     * @param v1
     * @param l
     * @param l1
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> zrangebyscoreWithScores(ScoredValueStreamingChannel scoredValueStreamingChannel, Object o, double v, double v1, long l, long l1) {
        return null;
    }

    /**
     * @param scoredValueStreamingChannel
     * @param o
     * @param s
     * @param s1
     * @param l
     * @param l1
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> zrangebyscoreWithScores(ScoredValueStreamingChannel scoredValueStreamingChannel, Object o, String s, String s1, long l, long l1) {
        return null;
    }

    @Override
    public Mono<Long> zrangestorebylex(Object o, Object k1, Range range, Limit limit) {
        return null;
    }

    @Override
    public Mono<Long> zrank(Object o, Object o2) {
        return null;
    }

    @Override
    public Mono<ScoredValue<Long>> zrankWithScore(Object o, Object o2) {
        return null;
    }

    @Override
    public Mono<Long> zrem(Object o, Object[] objects) {
        return null;
    }

    /**
     * @param o
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> zremrangebylex(Object o, String s, String s1) {
        return null;
    }

    @Override
    public Mono<Long> zremrangebylex(Object o, Range range) {
        return null;
    }

    @Override
    public Mono<Long> zremrangebyrank(Object o, long l, long l1) {
        return null;
    }

    /**
     * @param o
     * @param v
     * @param v1
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> zremrangebyscore(Object o, double v, double v1) {
        return null;
    }

    /**
     * @param o
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> zremrangebyscore(Object o, String s, String s1) {
        return null;
    }

    @Override
    public Flux zrevrange(Object o, long l, long l1) {
        return null;
    }

    /**
     * @param valueStreamingChannel
     * @param o
     * @param l
     * @param l1
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> zrevrange(ValueStreamingChannel valueStreamingChannel, Object o, long l, long l1) {
        return null;
    }

    @Override
    public Flux<ScoredValue> zrevrangeWithScores(Object o, long l, long l1) {
        return null;
    }

    /**
     * @param scoredValueStreamingChannel
     * @param o
     * @param l
     * @param l1
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> zrevrangeWithScores(ScoredValueStreamingChannel scoredValueStreamingChannel, Object o, long l, long l1) {
        return null;
    }

    @Override
    public Flux zrevrangebylex(Object o, Range range) {
        return null;
    }

    @Override
    @Deprecated
    public Flux zrevrangebylex(Object o, Range range, Limit limit) {
        return null;
    }

    /**
     * @param o
     * @param v
     * @param v1
     * @deprecated
     */
    @Override
    @Deprecated
    public Flux zrevrangebyscore(Object o, double v, double v1) {
        return null;
    }

    /**
     * @param o
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    @Deprecated
    public Flux zrevrangebyscore(Object o, String s, String s1) {
        return null;
    }

    /**
     * @param o
     * @param v
     * @param v1
     * @param l
     * @param l1
     * @deprecated
     */
    @Override
    @Deprecated
    public Flux zrevrangebyscore(Object o, double v, double v1, long l, long l1) {
        return null;
    }

    /**
     * @param o
     * @param s
     * @param s1
     * @param l
     * @param l1
     * @deprecated
     */
    @Override
    @Deprecated
    public Flux zrevrangebyscore(Object o, String s, String s1, long l, long l1) {
        return null;
    }

    /**
     * @param valueStreamingChannel
     * @param o
     * @param v
     * @param v1
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> zrevrangebyscore(ValueStreamingChannel valueStreamingChannel, Object o, double v, double v1) {
        return null;
    }

    /**
     * @param valueStreamingChannel
     * @param o
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> zrevrangebyscore(ValueStreamingChannel valueStreamingChannel, Object o, String s, String s1) {
        return null;
    }

    /**
     * @param valueStreamingChannel
     * @param o
     * @param v
     * @param v1
     * @param l
     * @param l1
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> zrevrangebyscore(ValueStreamingChannel valueStreamingChannel, Object o, double v, double v1, long l, long l1) {
        return null;
    }

    /**
     * @param valueStreamingChannel
     * @param o
     * @param s
     * @param s1
     * @param l
     * @param l1
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> zrevrangebyscore(ValueStreamingChannel valueStreamingChannel, Object o, String s, String s1, long l, long l1) {
        return null;
    }

    /**
     * @param o
     * @param v
     * @param v1
     * @deprecated
     */
    @Override
    @Deprecated
    public Flux<ScoredValue> zrevrangebyscoreWithScores(Object o, double v, double v1) {
        return null;
    }

    /**
     * @param o
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    @Deprecated
    public Flux<ScoredValue> zrevrangebyscoreWithScores(Object o, String s, String s1) {
        return null;
    }

    /**
     * @param o
     * @param v
     * @param v1
     * @param l
     * @param l1
     * @deprecated
     */
    @Override
    @Deprecated
    public Flux<ScoredValue> zrevrangebyscoreWithScores(Object o, double v, double v1, long l, long l1) {
        return null;
    }

    /**
     * @param o
     * @param s
     * @param s1
     * @param l
     * @param l1
     * @deprecated
     */
    @Override
    @Deprecated
    public Flux<ScoredValue> zrevrangebyscoreWithScores(Object o, String s, String s1, long l, long l1) {
        return null;
    }

    /**
     * @param scoredValueStreamingChannel
     * @param o
     * @param v
     * @param v1
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> zrevrangebyscoreWithScores(ScoredValueStreamingChannel scoredValueStreamingChannel, Object o, double v, double v1) {
        return null;
    }

    /**
     * @param scoredValueStreamingChannel
     * @param o
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> zrevrangebyscoreWithScores(ScoredValueStreamingChannel scoredValueStreamingChannel, Object o, String s, String s1) {
        return null;
    }

    /**
     * @param scoredValueStreamingChannel
     * @param o
     * @param v
     * @param v1
     * @param l
     * @param l1
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> zrevrangebyscoreWithScores(ScoredValueStreamingChannel scoredValueStreamingChannel, Object o, double v, double v1, long l, long l1) {
        return null;
    }

    /**
     * @param scoredValueStreamingChannel
     * @param o
     * @param s
     * @param s1
     * @param l
     * @param l1
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> zrevrangebyscoreWithScores(ScoredValueStreamingChannel scoredValueStreamingChannel, Object o, String s, String s1, long l, long l1) {
        return null;
    }

    @Override
    public Mono<Long> zrevrangestorebylex(Object o, Object k1, Range range, Limit limit) {
        return null;
    }

    @Override
    public Mono<Long> zrevrank(Object o, Object o2) {
        return null;
    }

    @Override
    public Mono<ScoredValue<Long>> zrevrankWithScore(Object o, Object o2) {
        return null;
    }

    @Override
    public Mono<ScoredValueScanCursor> zscan(Object o) {
        return null;
    }

    @Override
    public Mono<ScoredValueScanCursor> zscan(Object o, ScanArgs scanArgs) {
        return null;
    }

    @Override
    public Mono<ScoredValueScanCursor> zscan(Object o, ScanCursor scanCursor, ScanArgs scanArgs) {
        return null;
    }

    @Override
    public Mono<ScoredValueScanCursor> zscan(Object o, ScanCursor scanCursor) {
        return null;
    }

    /**
     * @param scoredValueStreamingChannel
     * @param o
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<StreamScanCursor> zscan(ScoredValueStreamingChannel scoredValueStreamingChannel, Object o) {
        return null;
    }

    /**
     * @param scoredValueStreamingChannel
     * @param o
     * @param scanArgs
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<StreamScanCursor> zscan(ScoredValueStreamingChannel scoredValueStreamingChannel, Object o, ScanArgs scanArgs) {
        return null;
    }

    /**
     * @param scoredValueStreamingChannel
     * @param o
     * @param scanCursor
     * @param scanArgs
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<StreamScanCursor> zscan(ScoredValueStreamingChannel scoredValueStreamingChannel, Object o, ScanCursor scanCursor, ScanArgs scanArgs) {
        return null;
    }

    /**
     * @param scoredValueStreamingChannel
     * @param o
     * @param scanCursor
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<StreamScanCursor> zscan(ScoredValueStreamingChannel scoredValueStreamingChannel, Object o, ScanCursor scanCursor) {
        return null;
    }

    @Override
    public Mono<Double> zscore(Object o, Object o2) {
        return null;
    }

    @Override
    public Flux zunion(Object[] objects) {
        return null;
    }

    @Override
    public Flux zunion(ZAggregateArgs zAggregateArgs, Object[] objects) {
        return null;
    }

    @Override
    public Flux<ScoredValue> zunionWithScores(ZAggregateArgs zAggregateArgs, Object[] objects) {
        return null;
    }

    @Override
    public Flux<ScoredValue> zunionWithScores(Object[] objects) {
        return null;
    }

    @Override
    public Mono<Long> zunionstore(Object o, Object[] objects) {
        return null;
    }

    @Override
    public Mono<Long> zunionstore(Object o, ZStoreArgs zStoreArgs, Object[] objects) {
        return null;
    }

    @Override
    public Mono<Long> zrevrangestorebyscore(Object o, Object k1, Range range, Limit limit) {
        return null;
    }

    @Override
    public Mono<Long> zrevrangestore(Object o, Object k1, Range range) {
        return null;
    }

    /**
     * @param scoredValueStreamingChannel
     * @param o
     * @param range
     * @param limit
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> zrevrangebyscoreWithScores(ScoredValueStreamingChannel scoredValueStreamingChannel, Object o, Range range, Limit limit) {
        return null;
    }

    /**
     * @param scoredValueStreamingChannel
     * @param o
     * @param range
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> zrevrangebyscoreWithScores(ScoredValueStreamingChannel scoredValueStreamingChannel, Object o, Range range) {
        return null;
    }

    @Override
    public Flux<ScoredValue> zrevrangebyscoreWithScores(Object o, Range range, Limit limit) {
        return null;
    }

    @Override
    public Flux<ScoredValue> zrevrangebyscoreWithScores(Object o, Range range) {
        return null;
    }

    /**
     * @param valueStreamingChannel
     * @param o
     * @param range
     * @param limit
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> zrevrangebyscore(ValueStreamingChannel valueStreamingChannel, Object o, Range range, Limit limit) {
        return null;
    }

    /**
     * @param valueStreamingChannel
     * @param o
     * @param range
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> zrevrangebyscore(ValueStreamingChannel valueStreamingChannel, Object o, Range range) {
        return null;
    }

    @Override
    public Flux zrevrangebyscore(Object o, Range range, Limit limit) {
        return null;
    }

    @Override
    public Flux zrevrangebyscore(Object o, Range range) {
        return null;
    }

    @Override
    public Mono<Long> zremrangebyscore(Object o, Range range) {
        return null;
    }

    @Override
    public Mono<Long> zrangestorebyscore(Object o, Object k1, Range range, Limit limit) {
        return null;
    }

    @Override
    public Mono<Long> zrangestore(Object o, Object k1, Range range) {
        return null;
    }

    /**
     * @param scoredValueStreamingChannel
     * @param o
     * @param range
     * @param limit
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> zrangebyscoreWithScores(ScoredValueStreamingChannel scoredValueStreamingChannel, Object o, Range range, Limit limit) {
        return null;
    }

    /**
     * @param scoredValueStreamingChannel
     * @param o
     * @param range
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> zrangebyscoreWithScores(ScoredValueStreamingChannel scoredValueStreamingChannel, Object o, Range range) {
        return null;
    }

    @Override
    public Flux<ScoredValue> zrangebyscoreWithScores(Object o, Range range, Limit limit) {
        return null;
    }

    @Override
    public Flux<ScoredValue> zrangebyscoreWithScores(Object o, Range range) {
        return null;
    }

    /**
     * @param valueStreamingChannel
     * @param o
     * @param range
     * @param limit
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> zrangebyscore(ValueStreamingChannel valueStreamingChannel, Object o, Range range, Limit limit) {
        return null;
    }

    /**
     * @param valueStreamingChannel
     * @param o
     * @param range
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> zrangebyscore(ValueStreamingChannel valueStreamingChannel, Object o, Range range) {
        return null;
    }

    @Override
    public Flux zrangebyscore(Object o, Range range, Limit limit) {
        return null;
    }

    @Override
    public Flux zrangebyscore(Object o, Range range) {
        return null;
    }

    @Override
    public Mono<Long> zcount(Object o, Range range) {
        return null;
    }

    @Override
    public Mono<Long> xack(Object o, Object k1, String... strings) {
        return null;
    }

    @Override
    public Mono<String> xadd(Object o, Map map) {
        return null;
    }

    @Override
    public Mono<String> xadd(Object o, XAddArgs xAddArgs, Map map) {
        return null;
    }

    @Override
    public Mono<String> xadd(Object o, Object... objects) {
        return null;
    }

    @Override
    public Mono<String> xadd(Object o, XAddArgs xAddArgs, Object... objects) {
        return null;
    }

    @Override
    public Mono<ClaimedMessages> xautoclaim(Object o, XAutoClaimArgs xAutoClaimArgs) {
        return null;
    }

    @Override
    public Flux<StreamMessage> xclaim(Object o, Consumer consumer, long l, String... strings) {
        return null;
    }

    @Override
    public Flux<StreamMessage> xclaim(Object o, Consumer consumer, XClaimArgs xClaimArgs, String... strings) {
        return null;
    }

    @Override
    public Mono<Long> xdel(Object o, String... strings) {
        return null;
    }

    @Override
    public Mono<String> xgroupCreate(XReadArgs.StreamOffset streamOffset, Object o) {
        return null;
    }

    @Override
    public Mono<String> xgroupCreate(XReadArgs.StreamOffset streamOffset, Object o, XGroupCreateArgs xGroupCreateArgs) {
        return null;
    }

    @Override
    public Mono<Boolean> xgroupCreateconsumer(Object o, Consumer consumer) {
        return null;
    }

    @Override
    public Mono<Long> xgroupDelconsumer(Object o, Consumer consumer) {
        return null;
    }

    @Override
    public Mono<Boolean> xgroupDestroy(Object o, Object k1) {
        return null;
    }

    @Override
    public Mono<String> xgroupSetid(XReadArgs.StreamOffset streamOffset, Object o) {
        return null;
    }

    @Override
    public Flux<Object> xinfoStream(Object o) {
        return null;
    }

    @Override
    public Flux<Object> xinfoGroups(Object o) {
        return null;
    }

    @Override
    public Flux<Object> xinfoConsumers(Object o, Object k1) {
        return null;
    }

    @Override
    public Mono<Long> xlen(Object o) {
        return null;
    }

    @Override
    public Mono<PendingMessages> xpending(Object o, Object k1) {
        return null;
    }

    @Override
    public Flux<PendingMessage> xpending(Object o, XPendingArgs xPendingArgs) {
        return null;
    }

    @Override
    public Flux<StreamMessage> xread(XReadArgs.StreamOffset[] streamOffsets) {
        return null;
    }

    @Override
    public Flux<StreamMessage> xread(XReadArgs xReadArgs, XReadArgs.StreamOffset[] streamOffsets) {
        return null;
    }

    @Override
    public Flux<StreamMessage> xreadgroup(Consumer consumer, XReadArgs.StreamOffset[] streamOffsets) {
        return null;
    }

    @Override
    public Flux<StreamMessage> xreadgroup(Consumer consumer, XReadArgs xReadArgs, XReadArgs.StreamOffset[] streamOffsets) {
        return null;
    }

    @Override
    public Mono<Long> xtrim(Object o, long l) {
        return null;
    }

    @Override
    public Mono<Long> xtrim(Object o, boolean b, long l) {
        return null;
    }

    @Override
    public Mono<Long> xtrim(Object o, XTrimArgs xTrimArgs) {
        return null;
    }

    @Override
    public Flux<StreamMessage> xrevrange(Object o, Range range, Limit limit) {
        return null;
    }

    @Override
    public Flux<StreamMessage> xrevrange(Object o, Range range) {
        return null;
    }

    @Override
    public Flux<StreamMessage> xrange(Object o, Range range, Limit limit) {
        return null;
    }

    @Override
    public Flux<StreamMessage> xrange(Object o, Range range) {
        return null;
    }

    @Override
    public Flux<PendingMessage> xpending(Object o, Consumer consumer, Range range, Limit limit) {
        return null;
    }

    @Override
    public Flux<PendingMessage> xpending(Object o, Object k1, Range range, Limit limit) {
        return null;
    }

    @Override
    public Mono<Long> append(Object o, Object o2) {
        return null;
    }

    @Override
    public Mono<Long> bitcount(Object o) {
        return null;
    }

    @Override
    public Mono<Long> bitcount(Object o, long l, long l1) {
        return null;
    }

    @Override
    public Flux<Value<Long>> bitfield(Object o, BitFieldArgs bitFieldArgs) {
        return null;
    }

    @Override
    public Mono<Long> bitpos(Object o, boolean b) {
        return null;
    }

    @Override
    public Mono<Long> bitpos(Object o, boolean b, long l) {
        return null;
    }

    @Override
    public Mono<Long> bitpos(Object o, boolean b, long l, long l1) {
        return null;
    }

    @Override
    public Mono<Long> bitopAnd(Object o, Object[] objects) {
        return null;
    }

    @Override
    public Mono<Long> bitopNot(Object o, Object k1) {
        return null;
    }

    @Override
    public Mono<Long> bitopOr(Object o, Object[] objects) {
        return null;
    }

    @Override
    public Mono<Long> bitopXor(Object o, Object[] objects) {
        return null;
    }

    @Override
    public Mono<Long> decr(Object o) {
        return null;
    }

    @Override
    public Mono<Long> decrby(Object o, long l) {
        return null;
    }

    @Override
    public Mono get(Object o) {
        return null;
    }

    @Override
    public Mono<Long> getbit(Object o, long l) {
        return null;
    }

    @Override
    public Mono getdel(Object o) {
        return null;
    }

    @Override
    public Mono getex(Object o, GetExArgs getExArgs) {
        return null;
    }

    @Override
    public Mono getrange(Object o, long l, long l1) {
        return null;
    }

    @Override
    public Mono getset(Object o, Object o2) {
        return null;
    }

    @Override
    public Mono<Long> incr(Object o) {
        return null;
    }

    @Override
    public Mono<Long> incrby(Object o, long l) {
        return null;
    }

    @Override
    public Mono<Double> incrbyfloat(Object o, double v) {
        return null;
    }

    @Override
    public Flux<KeyValue> mget(Object[] objects) {
        return null;
    }

    /**
     * @param keyValueStreamingChannel
     * @param objects
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> mget(KeyValueStreamingChannel keyValueStreamingChannel, Object[] objects) {
        return null;
    }

    @Override
    public Mono<String> mset(Map map) {
        return null;
    }

    @Override
    public Mono<Boolean> msetnx(Map map) {
        return null;
    }

    @Override
    public Mono<String> set(Object o, Object o2) {
        return null;
    }

    @Override
    public Mono<String> set(Object o, Object o2, SetArgs setArgs) {
        return null;
    }

    @Override
    public Mono setGet(Object o, Object o2) {
        return null;
    }

    @Override
    public Mono setGet(Object o, Object o2, SetArgs setArgs) {
        return null;
    }

    @Override
    public Mono<Long> setbit(Object o, long l, int i) {
        return null;
    }

    @Override
    public Mono<String> setex(Object o, long l, Object o2) {
        return null;
    }

    @Override
    public Mono<String> psetex(Object o, long l, Object o2) {
        return null;
    }

    @Override
    public Mono<Boolean> setnx(Object o, Object o2) {
        return null;
    }

    @Override
    public Mono<Long> setrange(Object o, long l, Object o2) {
        return null;
    }

    @Override
    public Mono<StringMatchResult> stralgoLcs(StrAlgoArgs strAlgoArgs) {
        return null;
    }

    @Override
    public Mono<Long> strlen(Object o) {
        return null;
    }

    @Override
    public Mono<String> discard() {
        return null;
    }

    @Override
    public Mono<TransactionResult> exec() {
        return null;
    }

    @Override
    public Mono<String> multi() {
        return null;
    }

    @Override
    public Mono<String> watch(Object[] objects) {
        return null;
    }

    @Override
    public Mono<String> unwatch() {
        return null;
    }
}
