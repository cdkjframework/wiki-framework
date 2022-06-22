package com.cdkjframework.redis.realize;

import com.redislabs.lettusearch.*;
import io.lettuce.core.*;
import io.lettuce.core.output.*;
import io.lettuce.core.protocol.CommandArgs;
import io.lettuce.core.protocol.CommandType;
import io.lettuce.core.protocol.ProtocolKeyword;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.redis.realize
 * @ClassName: SearchCommands
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Date: 2022/6/21 15:29
 * @Version: 1.0
 */
public class SearchCommands implements RediSearchCommands {
    @Override
    public void setTimeout(Duration duration) {

    }

    /**
     * @param l
     * @param timeUnit
     * @deprecated
     */
    @Override
    public void setTimeout(long l, TimeUnit timeUnit) {

    }

    @Override
    public String auth(String s) {
        return null;
    }

    @Override
    public String clusterBumpepoch() {
        return null;
    }

    @Override
    public String clusterMeet(String s, int i) {
        return null;
    }

    @Override
    public String clusterForget(String s) {
        return null;
    }

    @Override
    public String clusterAddSlots(int... ints) {
        return null;
    }

    @Override
    public String clusterDelSlots(int... ints) {
        return null;
    }

    @Override
    public String clusterSetSlotNode(int i, String s) {
        return null;
    }

    @Override
    public String clusterSetSlotStable(int i) {
        return null;
    }

    @Override
    public String clusterSetSlotMigrating(int i, String s) {
        return null;
    }

    @Override
    public String clusterSetSlotImporting(int i, String s) {
        return null;
    }

    @Override
    public String clusterInfo() {
        return null;
    }

    @Override
    public String clusterMyId() {
        return null;
    }

    @Override
    public String clusterNodes() {
        return null;
    }

    @Override
    public List<String> clusterSlaves(String s) {
        return null;
    }

    @Override
    public List clusterGetKeysInSlot(int i, int i1) {
        return null;
    }

    @Override
    public Long clusterCountKeysInSlot(int i) {
        return null;
    }

    @Override
    public Long clusterCountFailureReports(String s) {
        return null;
    }

    @Override
    public Long clusterKeyslot(Object o) {
        return null;
    }

    @Override
    public String clusterSaveconfig() {
        return null;
    }

    @Override
    public String clusterSetConfigEpoch(long l) {
        return null;
    }

    @Override
    public List<Object> clusterSlots() {
        return null;
    }

    @Override
    public String asking() {
        return null;
    }

    @Override
    public String clusterReplicate(String s) {
        return null;
    }

    @Override
    public String clusterFailover(boolean b) {
        return null;
    }

    @Override
    public String clusterReset(boolean b) {
        return null;
    }

    @Override
    public String clusterFlushslots() {
        return null;
    }

    @Override
    public String select(int i) {
        return null;
    }

    @Override
    public String swapdb(int i, int i1) {
        return null;
    }

    @Override
    public StatefulRediSearchConnection getStatefulConnection() {
        return null;
    }

    @Override
    public AggregateResults aggregate(Object index, Object query) {
        return null;
    }

    @Override
    public AggregateResults aggregate(Object index, Object query, AggregateOptions options) {
        return null;
    }

    @Override
    public AggregateWithCursorResults aggregate(Object index, Object query, Cursor cursor) {
        return null;
    }

    @Override
    public AggregateWithCursorResults aggregate(Object index, Object query, Cursor cursor, AggregateOptions options) {
        return null;
    }

    @Override
    public AggregateWithCursorResults cursorRead(Object index, long cursor) {
        return null;
    }

    @Override
    public AggregateWithCursorResults cursorRead(Object index, long cursor, long count) {
        return null;
    }

    @Override
    public String cursorDelete(Object index, long cursor) {
        return null;
    }

    @Override
    public String create(Object index, Field[] fields) {
        return null;
    }

    @Override
    public String create(Object index, CreateOptions options, Field[] fields) {
        return null;
    }

    @Override
    public String dropIndex(Object index) {
        return null;
    }

    @Override
    public String dropIndex(Object index, boolean deleteDocs) {
        return null;
    }

    @Override
    public String alter(Object index, Field field) {
        return null;
    }

    @Override
    public List<Object> ftInfo(Object index) {
        return null;
    }

    @Override
    public String aliasAdd(Object name, Object index) {
        return null;
    }

    @Override
    public String aliasUpdate(Object name, Object index) {
        return null;
    }

    @Override
    public String aliasDel(Object name) {
        return null;
    }

    @Override
    public List list() {
        return null;
    }

    @Override
    public SearchResults search(Object index, Object query) {
        return null;
    }

    @Override
    public SearchResults search(Object index, Object query, SearchOptions options) {
        return null;
    }

    @Override
    public Long sugadd(Object key, Suggestion suggestion) {
        return null;
    }

    @Override
    public Long sugadd(Object key, Suggestion suggestion, boolean increment) {
        return null;
    }

    @Override
    public List<Suggestion> sugget(Object key, Object prefix) {
        return null;
    }

    @Override
    public List<Suggestion> sugget(Object key, Object prefix, SuggetOptions options) {
        return null;
    }

    @Override
    public Boolean sugdel(Object key, Object string) {
        return null;
    }

    @Override
    public Long suglen(Object key) {
        return null;
    }

    @Override
    public Long publish(Object o, Object o2) {
        return null;
    }

    @Override
    public List pubsubChannels() {
        return null;
    }

    @Override
    public List pubsubChannels(Object o) {
        return null;
    }

    @Override
    public Map pubsubNumsub(Object[] objects) {
        return null;
    }

    @Override
    public Long pubsubNumpat() {
        return null;
    }

    @Override
    public Object echo(Object o) {
        return null;
    }

    @Override
    public List<Object> role() {
        return null;
    }

    @Override
    public String ping() {
        return null;
    }

    @Override
    public String readOnly() {
        return null;
    }

    @Override
    public String readWrite() {
        return null;
    }

    @Override
    public String quit() {
        return null;
    }

    @Override
    public Long waitForReplication(int i, long l) {
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
    public Object dispatch(ProtocolKeyword protocolKeyword, CommandOutput commandOutput, CommandArgs commandArgs) {
        return null;
    }

    @Override
    public Object dispatch(ProtocolKeyword protocolKeyword, CommandOutput commandOutput) {
        return null;
    }

    @Override
    public Long geoadd(Object o, double v, double v1, Object v2) {
        return null;
    }

    @Override
    public Long geoadd(Object o, Object... objects) {
        return null;
    }

    @Override
    public List<Value<String>> geohash(Object o, Object[] objects) {
        return null;
    }

    @Override
    public Set georadius(Object o, double v, double v1, double v2, GeoArgs.Unit unit) {
        return null;
    }

    @Override
    public List<GeoWithin> georadius(Object o, double v, double v1, double v2, GeoArgs.Unit unit, GeoArgs geoArgs) {
        return null;
    }

    @Override
    public Long georadius(Object o, double v, double v1, double v2, GeoArgs.Unit unit, GeoRadiusStoreArgs geoRadiusStoreArgs) {
        return null;
    }

    @Override
    public Set georadiusbymember(Object o, Object o2, double v1, GeoArgs.Unit unit) {
        return null;
    }

    @Override
    public List<GeoWithin> georadiusbymember(Object o, Object o2, double v1, GeoArgs.Unit unit, GeoArgs geoArgs) {
        return null;
    }

    @Override
    public Long georadiusbymember(Object o, Object o2, double v1, GeoArgs.Unit unit, GeoRadiusStoreArgs geoRadiusStoreArgs) {
        return null;
    }

    @Override
    public List<GeoCoordinates> geopos(Object o, Object[] objects) {
        return null;
    }

    @Override
    public Double geodist(Object o, Object o2, Object v1, GeoArgs.Unit unit) {
        return null;
    }

    @Override
    public Long pfadd(Object o, Object[] objects) {
        return null;
    }

    @Override
    public String pfmerge(Object o, Object[] objects) {
        return null;
    }

    @Override
    public Long pfcount(Object[] objects) {
        return null;
    }

    @Override
    public Long hdel(Object o, Object[] objects) {
        return null;
    }

    @Override
    public Boolean hexists(Object o, Object k1) {
        return null;
    }

    @Override
    public Object hget(Object o, Object k1) {
        return null;
    }

    @Override
    public Long hincrby(Object o, Object k1, long l) {
        return null;
    }

    @Override
    public Double hincrbyfloat(Object o, Object k1, double v) {
        return null;
    }

    @Override
    public Map hgetall(Object o) {
        return null;
    }

    @Override
    public Long hgetall(KeyValueStreamingChannel keyValueStreamingChannel, Object o) {
        return null;
    }

    @Override
    public List hkeys(Object o) {
        return null;
    }

    @Override
    public Long hkeys(KeyStreamingChannel keyStreamingChannel, Object o) {
        return null;
    }

    @Override
    public Long hlen(Object o) {
        return null;
    }

    @Override
    public List<KeyValue> hmget(Object o, Object[] objects) {
        return null;
    }

    @Override
    public Long hmget(KeyValueStreamingChannel keyValueStreamingChannel, Object o, Object[] objects) {
        return null;
    }

    @Override
    public String hmset(Object o, Map map) {
        return null;
    }

    @Override
    public MapScanCursor hscan(Object o) {
        return null;
    }

    @Override
    public MapScanCursor hscan(Object o, ScanArgs scanArgs) {
        return null;
    }

    @Override
    public MapScanCursor hscan(Object o, ScanCursor scanCursor, ScanArgs scanArgs) {
        return null;
    }

    @Override
    public MapScanCursor hscan(Object o, ScanCursor scanCursor) {
        return null;
    }

    @Override
    public StreamScanCursor hscan(KeyValueStreamingChannel keyValueStreamingChannel, Object o) {
        return null;
    }

    @Override
    public StreamScanCursor hscan(KeyValueStreamingChannel keyValueStreamingChannel, Object o, ScanArgs scanArgs) {
        return null;
    }

    @Override
    public StreamScanCursor hscan(KeyValueStreamingChannel keyValueStreamingChannel, Object o, ScanCursor scanCursor, ScanArgs scanArgs) {
        return null;
    }

    @Override
    public StreamScanCursor hscan(KeyValueStreamingChannel keyValueStreamingChannel, Object o, ScanCursor scanCursor) {
        return null;
    }

    @Override
    public Boolean hset(Object o, Object k1, Object o2) {
        return null;
    }

    @Override
    public Long hset(Object o, Map map) {
        return null;
    }

    @Override
    public Boolean hsetnx(Object o, Object k1, Object o2) {
        return null;
    }

    @Override
    public Long hstrlen(Object o, Object k1) {
        return null;
    }

    @Override
    public List hvals(Object o) {
        return null;
    }

    @Override
    public Long hvals(ValueStreamingChannel valueStreamingChannel, Object o) {
        return null;
    }

    @Override
    public Long del(Object[] objects) {
        return null;
    }

    @Override
    public Long unlink(Object[] objects) {
        return null;
    }

    @Override
    public byte[] dump(Object o) {
        return new byte[0];
    }

    @Override
    public Long exists(Object[] objects) {
        return null;
    }

    @Override
    public Boolean expire(Object o, long l) {
        return null;
    }

    @Override
    public Boolean expireat(Object o, Date date) {
        return null;
    }

    @Override
    public Boolean expireat(Object o, long l) {
        return null;
    }

    @Override
    public List keys(Object o) {
        return null;
    }

    @Override
    public Long keys(KeyStreamingChannel keyStreamingChannel, Object o) {
        return null;
    }

    @Override
    public String migrate(String s, int i, Object o, int i1, long l) {
        return null;
    }

    @Override
    public String migrate(String s, int i, int i1, long l, MigrateArgs migrateArgs) {
        return null;
    }

    @Override
    public Boolean move(Object o, int i) {
        return null;
    }

    @Override
    public String objectEncoding(Object o) {
        return null;
    }

    @Override
    public Long objectIdletime(Object o) {
        return null;
    }

    @Override
    public Long objectRefcount(Object o) {
        return null;
    }

    @Override
    public Boolean persist(Object o) {
        return null;
    }

    @Override
    public Boolean pexpire(Object o, long l) {
        return null;
    }

    @Override
    public Boolean pexpireat(Object o, Date date) {
        return null;
    }

    @Override
    public Boolean pexpireat(Object o, long l) {
        return null;
    }

    @Override
    public Long pttl(Object o) {
        return null;
    }

    @Override
    public Object randomkey() {
        return null;
    }

    @Override
    public String rename(Object o, Object k1) {
        return null;
    }

    @Override
    public Boolean renamenx(Object o, Object k1) {
        return null;
    }

    @Override
    public String restore(Object o, long l, byte[] bytes) {
        return null;
    }

    @Override
    public String restore(Object o, byte[] bytes, RestoreArgs restoreArgs) {
        return null;
    }

    @Override
    public List sort(Object o) {
        return null;
    }

    @Override
    public Long sort(ValueStreamingChannel valueStreamingChannel, Object o) {
        return null;
    }

    @Override
    public List sort(Object o, SortArgs sortArgs) {
        return null;
    }

    @Override
    public Long sort(ValueStreamingChannel valueStreamingChannel, Object o, SortArgs sortArgs) {
        return null;
    }

    @Override
    public Long sortStore(Object o, SortArgs sortArgs, Object k1) {
        return null;
    }

    @Override
    public Long touch(Object[] objects) {
        return null;
    }

    @Override
    public Long ttl(Object o) {
        return null;
    }

    @Override
    public String type(Object o) {
        return null;
    }

    @Override
    public KeyScanCursor scan() {
        return null;
    }

    @Override
    public KeyScanCursor scan(ScanArgs scanArgs) {
        return null;
    }

    @Override
    public KeyScanCursor scan(ScanCursor scanCursor, ScanArgs scanArgs) {
        return null;
    }

    @Override
    public KeyScanCursor scan(ScanCursor scanCursor) {
        return null;
    }

    @Override
    public StreamScanCursor scan(KeyStreamingChannel keyStreamingChannel) {
        return null;
    }

    @Override
    public StreamScanCursor scan(KeyStreamingChannel keyStreamingChannel, ScanArgs scanArgs) {
        return null;
    }

    @Override
    public StreamScanCursor scan(KeyStreamingChannel keyStreamingChannel, ScanCursor scanCursor, ScanArgs scanArgs) {
        return null;
    }

    @Override
    public StreamScanCursor scan(KeyStreamingChannel keyStreamingChannel, ScanCursor scanCursor) {
        return null;
    }

    @Override
    public KeyValue blpop(long l, Object[] objects) {
        return null;
    }

    @Override
    public KeyValue brpop(long l, Object[] objects) {
        return null;
    }

    @Override
    public Object brpoplpush(long l, Object o, Object k1) {
        return null;
    }

    @Override
    public Object lindex(Object o, long l) {
        return null;
    }

    @Override
    public Long linsert(Object o, boolean b, Object o2, Object v1) {
        return null;
    }

    @Override
    public Long llen(Object o) {
        return null;
    }

    @Override
    public Object lpop(Object o) {
        return null;
    }

    @Override
    public Long lpos(Object o, Object o2) {
        return null;
    }

    @Override
    public Long lpos(Object o, Object o2, LPosArgs lPosArgs) {
        return null;
    }

    @Override
    public List<Long> lpos(Object o, Object o2, int i) {
        return null;
    }

    @Override
    public List<Long> lpos(Object o, Object o2, int i, LPosArgs lPosArgs) {
        return null;
    }

    @Override
    public Long lpush(Object o, Object[] objects) {
        return null;
    }

    @Override
    public Long lpushx(Object o, Object[] objects) {
        return null;
    }

    @Override
    public List lrange(Object o, long l, long l1) {
        return null;
    }

    @Override
    public Long lrange(ValueStreamingChannel valueStreamingChannel, Object o, long l, long l1) {
        return null;
    }

    @Override
    public Long lrem(Object o, long l, Object o2) {
        return null;
    }

    @Override
    public String lset(Object o, long l, Object o2) {
        return null;
    }

    @Override
    public String ltrim(Object o, long l, long l1) {
        return null;
    }

    @Override
    public Object rpop(Object o) {
        return null;
    }

    @Override
    public Object rpoplpush(Object o, Object k1) {
        return null;
    }

    @Override
    public Long rpush(Object o, Object[] objects) {
        return null;
    }

    @Override
    public Long rpushx(Object o, Object[] objects) {
        return null;
    }

    @Override
    public Object eval(String s, ScriptOutputType scriptOutputType, Object[] objects) {
        return null;
    }

    @Override
    public Object eval(String s, ScriptOutputType scriptOutputType, Object[] objects, Object[] objects2) {
        return null;
    }

    @Override
    public Object evalsha(String s, ScriptOutputType scriptOutputType, Object[] objects) {
        return null;
    }

    @Override
    public Object evalsha(String s, ScriptOutputType scriptOutputType, Object[] objects, Object[] objects2) {
        return null;
    }

    @Override
    public List<Boolean> scriptExists(String... strings) {
        return null;
    }

    @Override
    public String scriptFlush() {
        return null;
    }

    @Override
    public String scriptKill() {
        return null;
    }

    @Override
    public String scriptLoad(Object o) {
        return null;
    }

    @Override
    public String digest(Object o) {
        return null;
    }

    @Override
    public String bgrewriteaof() {
        return null;
    }

    @Override
    public String bgsave() {
        return null;
    }

    @Override
    public Object clientGetname() {
        return null;
    }

    @Override
    public String clientSetname(Object o) {
        return null;
    }

    @Override
    public String clientKill(String s) {
        return null;
    }

    @Override
    public Long clientKill(KillArgs killArgs) {
        return null;
    }

    @Override
    public Long clientUnblock(long l, UnblockType unblockType) {
        return null;
    }

    @Override
    public String clientPause(long l) {
        return null;
    }

    @Override
    public String clientList() {
        return null;
    }

    @Override
    public Long clientId() {
        return null;
    }

    @Override
    public List<Object> command() {
        return null;
    }

    @Override
    public List<Object> commandInfo(String... strings) {
        return null;
    }

    @Override
    public List<Object> commandInfo(CommandType... commandTypes) {
        return null;
    }

    @Override
    public Long commandCount() {
        return null;
    }

    @Override
    public Map<String, String> configGet(String s) {
        return null;
    }

    @Override
    public String configResetstat() {
        return null;
    }

    @Override
    public String configRewrite() {
        return null;
    }

    @Override
    public String configSet(String s, String s1) {
        return null;
    }

    @Override
    public Long dbsize() {
        return null;
    }

    @Override
    public String debugCrashAndRecover(Long aLong) {
        return null;
    }

    @Override
    public String debugHtstats(int i) {
        return null;
    }

    @Override
    public String debugObject(Object o) {
        return null;
    }

    @Override
    public void debugOom() {

    }

    @Override
    public void debugSegfault() {

    }

    @Override
    public String debugReload() {
        return null;
    }

    @Override
    public String debugRestart(Long aLong) {
        return null;
    }

    @Override
    public String debugSdslen(Object o) {
        return null;
    }

    @Override
    public String flushall() {
        return null;
    }

    @Override
    public String flushallAsync() {
        return null;
    }

    @Override
    public String flushdb() {
        return null;
    }

    @Override
    public String flushdbAsync() {
        return null;
    }

    @Override
    public String info() {
        return null;
    }

    @Override
    public String info(String s) {
        return null;
    }

    @Override
    public Date lastsave() {
        return null;
    }

    @Override
    public Long memoryUsage(Object o) {
        return null;
    }

    @Override
    public String save() {
        return null;
    }

    @Override
    public void shutdown(boolean b) {

    }

    @Override
    public String slaveof(String s, int i) {
        return null;
    }

    @Override
    public String slaveofNoOne() {
        return null;
    }

    @Override
    public List<Object> slowlogGet() {
        return null;
    }

    @Override
    public List<Object> slowlogGet(int i) {
        return null;
    }

    @Override
    public Long slowlogLen() {
        return null;
    }

    @Override
    public String slowlogReset() {
        return null;
    }

    @Override
    public List time() {
        return null;
    }

    @Override
    public Long sadd(Object o, Object[] objects) {
        return null;
    }

    @Override
    public Long scard(Object o) {
        return null;
    }

    @Override
    public Set sdiff(Object[] objects) {
        return null;
    }

    @Override
    public Long sdiff(ValueStreamingChannel valueStreamingChannel, Object[] objects) {
        return null;
    }

    @Override
    public Long sdiffstore(Object o, Object[] objects) {
        return null;
    }

    @Override
    public Set sinter(Object[] objects) {
        return null;
    }

    @Override
    public Long sinter(ValueStreamingChannel valueStreamingChannel, Object[] objects) {
        return null;
    }

    @Override
    public Long sinterstore(Object o, Object[] objects) {
        return null;
    }

    @Override
    public Boolean sismember(Object o, Object o2) {
        return null;
    }

    @Override
    public Boolean smove(Object o, Object k1, Object o2) {
        return null;
    }

    @Override
    public Set smembers(Object o) {
        return null;
    }

    @Override
    public Long smembers(ValueStreamingChannel valueStreamingChannel, Object o) {
        return null;
    }

    @Override
    public Object spop(Object o) {
        return null;
    }

    @Override
    public Set spop(Object o, long l) {
        return null;
    }

    @Override
    public Object srandmember(Object o) {
        return null;
    }

    @Override
    public List srandmember(Object o, long l) {
        return null;
    }

    @Override
    public Long srandmember(ValueStreamingChannel valueStreamingChannel, Object o, long l) {
        return null;
    }

    @Override
    public Long srem(Object o, Object[] objects) {
        return null;
    }

    @Override
    public Set sunion(Object[] objects) {
        return null;
    }

    @Override
    public Long sunion(ValueStreamingChannel valueStreamingChannel, Object[] objects) {
        return null;
    }

    @Override
    public Long sunionstore(Object o, Object[] objects) {
        return null;
    }

    @Override
    public ValueScanCursor sscan(Object o) {
        return null;
    }

    @Override
    public ValueScanCursor sscan(Object o, ScanArgs scanArgs) {
        return null;
    }

    @Override
    public ValueScanCursor sscan(Object o, ScanCursor scanCursor, ScanArgs scanArgs) {
        return null;
    }

    @Override
    public ValueScanCursor sscan(Object o, ScanCursor scanCursor) {
        return null;
    }

    @Override
    public StreamScanCursor sscan(ValueStreamingChannel valueStreamingChannel, Object o) {
        return null;
    }

    @Override
    public StreamScanCursor sscan(ValueStreamingChannel valueStreamingChannel, Object o, ScanArgs scanArgs) {
        return null;
    }

    @Override
    public StreamScanCursor sscan(ValueStreamingChannel valueStreamingChannel, Object o, ScanCursor scanCursor, ScanArgs scanArgs) {
        return null;
    }

    @Override
    public StreamScanCursor sscan(ValueStreamingChannel valueStreamingChannel, Object o, ScanCursor scanCursor) {
        return null;
    }

    @Override
    public KeyValue bzpopmin(long l, Object[] objects) {
        return null;
    }

    @Override
    public KeyValue bzpopmax(long l, Object[] objects) {
        return null;
    }

    @Override
    public Long zadd(Object o, double v, Object v1) {
        return null;
    }

    @Override
    public Long zadd(Object o, Object... objects) {
        return null;
    }

    @Override
    public Long zadd(Object o, ScoredValue[] scoredValues) {
        return null;
    }

    @Override
    public Long zadd(Object o, ZAddArgs zAddArgs, double v, Object v1) {
        return null;
    }

    @Override
    public Long zadd(Object o, ZAddArgs zAddArgs, Object... objects) {
        return null;
    }

    @Override
    public Long zadd(Object o, ZAddArgs zAddArgs, ScoredValue[] scoredValues) {
        return null;
    }

    @Override
    public Double zaddincr(Object o, double v, Object v1) {
        return null;
    }

    @Override
    public Double zaddincr(Object o, ZAddArgs zAddArgs, double v, Object v1) {
        return null;
    }

    @Override
    public Long zcard(Object o) {
        return null;
    }

    /**
     * @param o
     * @param v
     * @param v1
     * @deprecated
     */
    @Override
    public Long zcount(Object o, double v, double v1) {
        return null;
    }

    /**
     * @param o
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    public Long zcount(Object o, String s, String s1) {
        return null;
    }

    @Override
    public Double zincrby(Object o, double v, Object v1) {
        return null;
    }

    @Override
    public Long zinterstore(Object o, Object[] objects) {
        return null;
    }

    @Override
    public Long zinterstore(Object o, ZStoreArgs zStoreArgs, Object[] objects) {
        return null;
    }

    /**
     * @param o
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    public Long zlexcount(Object o, String s, String s1) {
        return null;
    }

    @Override
    public Long zlexcount(Object o, Range range) {
        return null;
    }

    @Override
    public ScoredValue zpopmin(Object o) {
        return null;
    }

    @Override
    public List<ScoredValue> zpopmin(Object o, long l) {
        return null;
    }

    @Override
    public ScoredValue zpopmax(Object o) {
        return null;
    }

    @Override
    public List<ScoredValue> zpopmax(Object o, long l) {
        return null;
    }

    @Override
    public List zrange(Object o, long l, long l1) {
        return null;
    }

    @Override
    public Long zrange(ValueStreamingChannel valueStreamingChannel, Object o, long l, long l1) {
        return null;
    }

    @Override
    public List<ScoredValue> zrangeWithScores(Object o, long l, long l1) {
        return null;
    }

    @Override
    public Long zrangeWithScores(ScoredValueStreamingChannel scoredValueStreamingChannel, Object o, long l, long l1) {
        return null;
    }

    /**
     * @param o
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    public List zrangebylex(Object o, String s, String s1) {
        return null;
    }

    @Override
    public List zrangebylex(Object o, Range range) {
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
    public List zrangebylex(Object o, String s, String s1, long l, long l1) {
        return null;
    }

    @Override
    public List zrangebylex(Object o, Range range, Limit limit) {
        return null;
    }

    /**
     * @param o
     * @param v
     * @param v1
     * @deprecated
     */
    @Override
    public List zrangebyscore(Object o, double v, double v1) {
        return null;
    }

    /**
     * @param o
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    public List zrangebyscore(Object o, String s, String s1) {
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
    public List zrangebyscore(Object o, double v, double v1, long l, long l1) {
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
    public List zrangebyscore(Object o, String s, String s1, long l, long l1) {
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
    public Long zrangebyscore(ValueStreamingChannel valueStreamingChannel, Object o, double v, double v1) {
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
    public Long zrangebyscore(ValueStreamingChannel valueStreamingChannel, Object o, String s, String s1) {
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
    public Long zrangebyscore(ValueStreamingChannel valueStreamingChannel, Object o, double v, double v1, long l, long l1) {
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
    public Long zrangebyscore(ValueStreamingChannel valueStreamingChannel, Object o, String s, String s1, long l, long l1) {
        return null;
    }

    /**
     * @param o
     * @param v
     * @param v1
     * @deprecated
     */
    @Override
    public List<ScoredValue> zrangebyscoreWithScores(Object o, double v, double v1) {
        return null;
    }

    /**
     * @param o
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    public List<ScoredValue> zrangebyscoreWithScores(Object o, String s, String s1) {
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
    public List<ScoredValue> zrangebyscoreWithScores(Object o, double v, double v1, long l, long l1) {
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
    public List<ScoredValue> zrangebyscoreWithScores(Object o, String s, String s1, long l, long l1) {
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
    public Long zrangebyscoreWithScores(ScoredValueStreamingChannel scoredValueStreamingChannel, Object o, double v, double v1) {
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
    public Long zrangebyscoreWithScores(ScoredValueStreamingChannel scoredValueStreamingChannel, Object o, String s, String s1) {
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
    public Long zrangebyscoreWithScores(ScoredValueStreamingChannel scoredValueStreamingChannel, Object o, double v, double v1, long l, long l1) {
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
    public Long zrangebyscoreWithScores(ScoredValueStreamingChannel scoredValueStreamingChannel, Object o, String s, String s1, long l, long l1) {
        return null;
    }

    @Override
    public Long zrank(Object o, Object o2) {
        return null;
    }

    @Override
    public Long zrem(Object o, Object[] objects) {
        return null;
    }

    /**
     * @param o
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    public Long zremrangebylex(Object o, String s, String s1) {
        return null;
    }

    @Override
    public Long zremrangebylex(Object o, Range range) {
        return null;
    }

    @Override
    public Long zremrangebyrank(Object o, long l, long l1) {
        return null;
    }

    /**
     * @param o
     * @param v
     * @param v1
     * @deprecated
     */
    @Override
    public Long zremrangebyscore(Object o, double v, double v1) {
        return null;
    }

    /**
     * @param o
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    public Long zremrangebyscore(Object o, String s, String s1) {
        return null;
    }

    @Override
    public List zrevrange(Object o, long l, long l1) {
        return null;
    }

    @Override
    public Long zrevrange(ValueStreamingChannel valueStreamingChannel, Object o, long l, long l1) {
        return null;
    }

    @Override
    public List<ScoredValue> zrevrangeWithScores(Object o, long l, long l1) {
        return null;
    }

    @Override
    public Long zrevrangeWithScores(ScoredValueStreamingChannel scoredValueStreamingChannel, Object o, long l, long l1) {
        return null;
    }

    @Override
    public List zrevrangebylex(Object o, Range range) {
        return null;
    }

    @Override
    public List zrevrangebylex(Object o, Range range, Limit limit) {
        return null;
    }

    /**
     * @param o
     * @param v
     * @param v1
     * @deprecated
     */
    @Override
    public List zrevrangebyscore(Object o, double v, double v1) {
        return null;
    }

    /**
     * @param o
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    public List zrevrangebyscore(Object o, String s, String s1) {
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
    public List zrevrangebyscore(Object o, double v, double v1, long l, long l1) {
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
    public List zrevrangebyscore(Object o, String s, String s1, long l, long l1) {
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
    public Long zrevrangebyscore(ValueStreamingChannel valueStreamingChannel, Object o, double v, double v1) {
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
    public Long zrevrangebyscore(ValueStreamingChannel valueStreamingChannel, Object o, String s, String s1) {
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
    public Long zrevrangebyscore(ValueStreamingChannel valueStreamingChannel, Object o, double v, double v1, long l, long l1) {
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
    public Long zrevrangebyscore(ValueStreamingChannel valueStreamingChannel, Object o, String s, String s1, long l, long l1) {
        return null;
    }

    /**
     * @param o
     * @param v
     * @param v1
     * @deprecated
     */
    @Override
    public List<ScoredValue> zrevrangebyscoreWithScores(Object o, double v, double v1) {
        return null;
    }

    /**
     * @param o
     * @param s
     * @param s1
     * @deprecated
     */
    @Override
    public List<ScoredValue> zrevrangebyscoreWithScores(Object o, String s, String s1) {
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
    public List<ScoredValue> zrevrangebyscoreWithScores(Object o, double v, double v1, long l, long l1) {
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
    public List<ScoredValue> zrevrangebyscoreWithScores(Object o, String s, String s1, long l, long l1) {
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
    public Long zrevrangebyscoreWithScores(ScoredValueStreamingChannel scoredValueStreamingChannel, Object o, double v, double v1) {
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
    public Long zrevrangebyscoreWithScores(ScoredValueStreamingChannel scoredValueStreamingChannel, Object o, String s, String s1) {
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
    public Long zrevrangebyscoreWithScores(ScoredValueStreamingChannel scoredValueStreamingChannel, Object o, double v, double v1, long l, long l1) {
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
    public Long zrevrangebyscoreWithScores(ScoredValueStreamingChannel scoredValueStreamingChannel, Object o, String s, String s1, long l, long l1) {
        return null;
    }

    @Override
    public Long zrevrank(Object o, Object o2) {
        return null;
    }

    @Override
    public ScoredValueScanCursor zscan(Object o) {
        return null;
    }

    @Override
    public ScoredValueScanCursor zscan(Object o, ScanArgs scanArgs) {
        return null;
    }

    @Override
    public ScoredValueScanCursor zscan(Object o, ScanCursor scanCursor, ScanArgs scanArgs) {
        return null;
    }

    @Override
    public ScoredValueScanCursor zscan(Object o, ScanCursor scanCursor) {
        return null;
    }

    @Override
    public StreamScanCursor zscan(ScoredValueStreamingChannel scoredValueStreamingChannel, Object o) {
        return null;
    }

    @Override
    public StreamScanCursor zscan(ScoredValueStreamingChannel scoredValueStreamingChannel, Object o, ScanArgs scanArgs) {
        return null;
    }

    @Override
    public StreamScanCursor zscan(ScoredValueStreamingChannel scoredValueStreamingChannel, Object o, ScanCursor scanCursor, ScanArgs scanArgs) {
        return null;
    }

    @Override
    public StreamScanCursor zscan(ScoredValueStreamingChannel scoredValueStreamingChannel, Object o, ScanCursor scanCursor) {
        return null;
    }

    @Override
    public Double zscore(Object o, Object o2) {
        return null;
    }

    @Override
    public Long zunionstore(Object o, Object[] objects) {
        return null;
    }

    @Override
    public Long zunionstore(Object o, ZStoreArgs zStoreArgs, Object[] objects) {
        return null;
    }

    @Override
    public Long zrevrangebyscoreWithScores(ScoredValueStreamingChannel scoredValueStreamingChannel, Object o, Range range, Limit limit) {
        return null;
    }

    @Override
    public Long zrevrangebyscoreWithScores(ScoredValueStreamingChannel scoredValueStreamingChannel, Object o, Range range) {
        return null;
    }

    @Override
    public List<ScoredValue> zrevrangebyscoreWithScores(Object o, Range range, Limit limit) {
        return null;
    }

    @Override
    public List<ScoredValue> zrevrangebyscoreWithScores(Object o, Range range) {
        return null;
    }

    @Override
    public Long zrevrangebyscore(ValueStreamingChannel valueStreamingChannel, Object o, Range range, Limit limit) {
        return null;
    }

    @Override
    public Long zrevrangebyscore(ValueStreamingChannel valueStreamingChannel, Object o, Range range) {
        return null;
    }

    @Override
    public List zrevrangebyscore(Object o, Range range, Limit limit) {
        return null;
    }

    @Override
    public List zrevrangebyscore(Object o, Range range) {
        return null;
    }

    @Override
    public Long zremrangebyscore(Object o, Range range) {
        return null;
    }

    @Override
    public Long zrangebyscoreWithScores(ScoredValueStreamingChannel scoredValueStreamingChannel, Object o, Range range, Limit limit) {
        return null;
    }

    @Override
    public Long zrangebyscoreWithScores(ScoredValueStreamingChannel scoredValueStreamingChannel, Object o, Range range) {
        return null;
    }

    @Override
    public List<ScoredValue> zrangebyscoreWithScores(Object o, Range range, Limit limit) {
        return null;
    }

    @Override
    public List<ScoredValue> zrangebyscoreWithScores(Object o, Range range) {
        return null;
    }

    @Override
    public Long zrangebyscore(ValueStreamingChannel valueStreamingChannel, Object o, Range range, Limit limit) {
        return null;
    }

    @Override
    public Long zrangebyscore(ValueStreamingChannel valueStreamingChannel, Object o, Range range) {
        return null;
    }

    @Override
    public List zrangebyscore(Object o, Range range, Limit limit) {
        return null;
    }

    @Override
    public List zrangebyscore(Object o, Range range) {
        return null;
    }

    @Override
    public Long zcount(Object o, Range range) {
        return null;
    }

    @Override
    public Long xack(Object o, Object k1, String... strings) {
        return null;
    }

    @Override
    public String xadd(Object o, Map map) {
        return null;
    }

    @Override
    public String xadd(Object o, XAddArgs xAddArgs, Map map) {
        return null;
    }

    @Override
    public String xadd(Object o, Object... objects) {
        return null;
    }

    @Override
    public String xadd(Object o, XAddArgs xAddArgs, Object... objects) {
        return null;
    }

    @Override
    public List<StreamMessage> xclaim(Object o, Consumer consumer, long l, String... strings) {
        return null;
    }

    @Override
    public List<StreamMessage> xclaim(Object o, Consumer consumer, XClaimArgs xClaimArgs, String... strings) {
        return null;
    }

    @Override
    public Long xdel(Object o, String... strings) {
        return null;
    }

    @Override
    public String xgroupCreate(XReadArgs.StreamOffset streamOffset, Object o) {
        return null;
    }

    @Override
    public String xgroupCreate(XReadArgs.StreamOffset streamOffset, Object o, XGroupCreateArgs xGroupCreateArgs) {
        return null;
    }

    @Override
    public Boolean xgroupDelconsumer(Object o, Consumer consumer) {
        return null;
    }

    @Override
    public Boolean xgroupDestroy(Object o, Object k1) {
        return null;
    }

    @Override
    public String xgroupSetid(XReadArgs.StreamOffset streamOffset, Object o) {
        return null;
    }

    @Override
    public List<Object> xinfoStream(Object o) {
        return null;
    }

    @Override
    public List<Object> xinfoGroups(Object o) {
        return null;
    }

    @Override
    public List<Object> xinfoConsumers(Object o, Object k1) {
        return null;
    }

    @Override
    public Long xlen(Object o) {
        return null;
    }

    @Override
    public List<Object> xpending(Object o, Object k1) {
        return null;
    }

    @Override
    public List<StreamMessage> xread(XReadArgs.StreamOffset[] streamOffsets) {
        return null;
    }

    @Override
    public List<StreamMessage> xread(XReadArgs xReadArgs, XReadArgs.StreamOffset[] streamOffsets) {
        return null;
    }

    @Override
    public List<StreamMessage> xreadgroup(Consumer consumer, XReadArgs.StreamOffset[] streamOffsets) {
        return null;
    }

    @Override
    public List<StreamMessage> xreadgroup(Consumer consumer, XReadArgs xReadArgs, XReadArgs.StreamOffset[] streamOffsets) {
        return null;
    }

    @Override
    public Long xtrim(Object o, long l) {
        return null;
    }

    @Override
    public Long xtrim(Object o, boolean b, long l) {
        return null;
    }

    @Override
    public List<StreamMessage> xrevrange(Object o, Range range, Limit limit) {
        return null;
    }

    @Override
    public List<StreamMessage> xrevrange(Object o, Range range) {
        return null;
    }

    @Override
    public List<StreamMessage> xrange(Object o, Range range, Limit limit) {
        return null;
    }

    @Override
    public List<StreamMessage> xrange(Object o, Range range) {
        return null;
    }

    @Override
    public List<Object> xpending(Object o, Consumer consumer, Range range, Limit limit) {
        return null;
    }

    @Override
    public List<Object> xpending(Object o, Object k1, Range range, Limit limit) {
        return null;
    }

    @Override
    public Long append(Object o, Object o2) {
        return null;
    }

    @Override
    public Long bitcount(Object o) {
        return null;
    }

    @Override
    public Long bitcount(Object o, long l, long l1) {
        return null;
    }

    @Override
    public List<Long> bitfield(Object o, BitFieldArgs bitFieldArgs) {
        return null;
    }

    @Override
    public Long bitpos(Object o, boolean b) {
        return null;
    }

    @Override
    public Long bitpos(Object o, boolean b, long l) {
        return null;
    }

    @Override
    public Long bitpos(Object o, boolean b, long l, long l1) {
        return null;
    }

    @Override
    public Long bitopAnd(Object o, Object[] objects) {
        return null;
    }

    @Override
    public Long bitopNot(Object o, Object k1) {
        return null;
    }

    @Override
    public Long bitopOr(Object o, Object[] objects) {
        return null;
    }

    @Override
    public Long bitopXor(Object o, Object[] objects) {
        return null;
    }

    @Override
    public Long decr(Object o) {
        return null;
    }

    @Override
    public Long decrby(Object o, long l) {
        return null;
    }

    @Override
    public Object get(Object o) {
        return null;
    }

    @Override
    public Long getbit(Object o, long l) {
        return null;
    }

    @Override
    public Object getrange(Object o, long l, long l1) {
        return null;
    }

    @Override
    public Object getset(Object o, Object o2) {
        return null;
    }

    @Override
    public Long incr(Object o) {
        return null;
    }

    @Override
    public Long incrby(Object o, long l) {
        return null;
    }

    @Override
    public Double incrbyfloat(Object o, double v) {
        return null;
    }

    @Override
    public List<KeyValue> mget(Object[] objects) {
        return null;
    }

    @Override
    public Long mget(KeyValueStreamingChannel keyValueStreamingChannel, Object[] objects) {
        return null;
    }

    @Override
    public String mset(Map map) {
        return null;
    }

    @Override
    public Boolean msetnx(Map map) {
        return null;
    }

    @Override
    public String set(Object o, Object o2) {
        return null;
    }

    @Override
    public String set(Object o, Object o2, SetArgs setArgs) {
        return null;
    }

    @Override
    public Long setbit(Object o, long l, int i) {
        return null;
    }

    @Override
    public String setex(Object o, long l, Object o2) {
        return null;
    }

    @Override
    public String psetex(Object o, long l, Object o2) {
        return null;
    }

    @Override
    public Boolean setnx(Object o, Object o2) {
        return null;
    }

    @Override
    public Long setrange(Object o, long l, Object o2) {
        return null;
    }

    @Override
    public StringMatchResult stralgoLcs(StrAlgoArgs strAlgoArgs) {
        return null;
    }

    @Override
    public Long strlen(Object o) {
        return null;
    }

    @Override
    public String discard() {
        return null;
    }

    @Override
    public TransactionResult exec() {
        return null;
    }

    @Override
    public String multi() {
        return null;
    }

    @Override
    public String watch(Object[] objects) {
        return null;
    }

    @Override
    public String unwatch() {
        return null;
    }
}
