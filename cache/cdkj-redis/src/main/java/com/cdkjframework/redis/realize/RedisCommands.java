package com.cdkjframework.redis.realize;

import io.lettuce.core.*;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.models.stream.ClaimedMessages;
import io.lettuce.core.models.stream.PendingMessage;
import io.lettuce.core.models.stream.PendingMessages;
import io.lettuce.core.output.*;
import io.lettuce.core.protocol.CommandArgs;
import io.lettuce.core.protocol.CommandType;
import io.lettuce.core.protocol.ProtocolKeyword;
import io.lettuce.core.protocol.RedisCommand;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: cdkj-framework
 * @Package: com.cdkjframework.redis.realize
 * @ClassName: RedisCommands
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
public class RedisCommands implements RedisAsyncCommands<String, String> {

    @Override
    public RedisFuture<String> set(String s, String s2, SetArgs setArgs) {
        return null;
    }

    @Override
    public RedisFuture<String> setGet(String s, String s2) {
        return null;
    }

    @Override
    public RedisFuture<String> setGet(String s, String s2, SetArgs setArgs) {
        return null;
    }

    @Override
    public RedisFuture<Long> setbit(String s, long l, int i) {
        return null;
    }

    @Override
    public RedisFuture<String> setex(String s, long l, String s2) {
        return null;
    }

    @Override
    public RedisFuture<String> psetex(String s, long l, String s2) {
        return null;
    }

    @Override
    public RedisFuture<Boolean> setnx(String s, String s2) {
        return null;
    }

    @Override
    public RedisFuture<Long> setrange(String s, long l, String s2) {
        return null;
    }

    @Override
    public RedisFuture<StringMatchResult> stralgoLcs(StrAlgoArgs strAlgoArgs) {
        return null;
    }

    @Override
    public RedisFuture<Long> strlen(String s) {
        return null;
    }

    @Override
    public RedisFuture<Long> append(String s, String s2) {
        return null;
    }

    @Override
    public RedisFuture<Long> bitcount(String s) {
        return null;
    }

    @Override
    public RedisFuture<Long> bitcount(String s, long l, long l1) {
        return null;
    }

    @Override
    public RedisFuture<List<Long>> bitfield(String s, BitFieldArgs bitFieldArgs) {
        return null;
    }

    @Override
    public RedisFuture<Long> bitpos(String s, boolean b) {
        return null;
    }

    @Override
    public RedisFuture<Long> bitpos(String s, boolean b, long l) {
        return null;
    }

    @Override
    public RedisFuture<Long> bitpos(String s, boolean b, long l, long l1) {
        return null;
    }

    @Override
    public RedisFuture<Long> bitopAnd(String s, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<Long> bitopNot(String s, String k1) {
        return null;
    }

    @Override
    public RedisFuture<Long> bitopOr(String s, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<Long> bitopXor(String s, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<Long> decr(String s) {
        return null;
    }

    @Override
    public RedisFuture<Long> decrby(String s, long l) {
        return null;
    }

    @Override
    public RedisFuture<String> get(String s) {
        return null;
    }

    @Override
    public RedisFuture<Long> getbit(String s, long l) {
        return null;
    }

    @Override
    public RedisFuture<String> getdel(String s) {
        return null;
    }

    @Override
    public RedisFuture<String> getex(String s, GetExArgs getExArgs) {
        return null;
    }

    @Override
    public RedisFuture<String> getrange(String s, long l, long l1) {
        return null;
    }

    @Override
    public RedisFuture<String> getset(String s, String s2) {
        return null;
    }

    @Override
    public RedisFuture<Long> incr(String s) {
        return null;
    }

    @Override
    public RedisFuture<Long> incrby(String s, long l) {
        return null;
    }

    @Override
    public RedisFuture<Double> incrbyfloat(String s, double v) {
        return null;
    }

    @Override
    public RedisFuture<Long> mget(KeyValueStreamingChannel<String, String> keyValueStreamingChannel, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<String> set(String s, String s2) {
        return null;
    }

    @Override
    public RedisFuture<List<Object>> xinfoGroups(String s) {
        return null;
    }

    @Override
    public RedisFuture<List<Object>> xinfoConsumers(String s, String k1) {
        return null;
    }

    @Override
    public RedisFuture<Long> xlen(String s) {
        return null;
    }

    @Override
    public RedisFuture<PendingMessages> xpending(String s, String k1) {
        return null;
    }

    @Override
    public RedisFuture<List<PendingMessage>> xpending(String s, String k1, Range<String> range, Limit limit) {
        return null;
    }

    @Override
    public RedisFuture<List<PendingMessage>> xpending(String s, Consumer<String> consumer, Range<String> range, Limit limit) {
        return null;
    }

    @Override
    public RedisFuture<List<PendingMessage>> xpending(String s, XPendingArgs<String> xPendingArgs) {
        return null;
    }

    @Override
    public RedisFuture<List<StreamMessage<String, String>>> xrange(String s, Range<String> range) {
        return null;
    }

    @Override
    public RedisFuture<List<StreamMessage<String, String>>> xrange(String s, Range<String> range, Limit limit) {
        return null;
    }

    @Override
    public RedisFuture<List<StreamMessage<String, String>>> xread(XReadArgs.StreamOffset<String>... streamOffsets) {
        return null;
    }

    @Override
    public RedisFuture<List<StreamMessage<String, String>>> xread(XReadArgs xReadArgs, XReadArgs.StreamOffset<String>... streamOffsets) {
        return null;
    }

    @Override
    public RedisFuture<List<StreamMessage<String, String>>> xreadgroup(Consumer<String> consumer, XReadArgs.StreamOffset<String>... streamOffsets) {
        return null;
    }

    @Override
    public RedisFuture<List<StreamMessage<String, String>>> xreadgroup(Consumer<String> consumer, XReadArgs xReadArgs, XReadArgs.StreamOffset<String>... streamOffsets) {
        return null;
    }

    @Override
    public RedisFuture<List<StreamMessage<String, String>>> xrevrange(String s, Range<String> range) {
        return null;
    }

    @Override
    public RedisFuture<List<StreamMessage<String, String>>> xrevrange(String s, Range<String> range, Limit limit) {
        return null;
    }

    @Override
    public RedisFuture<Long> xtrim(String s, long l) {
        return null;
    }

    @Override
    public RedisFuture<Long> xtrim(String s, boolean b, long l) {
        return null;
    }

    @Override
    public RedisFuture<Long> xtrim(String s, XTrimArgs xTrimArgs) {
        return null;
    }

    @Override
    public RedisFuture<Long> xack(String s, String k1, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<String> xadd(String s, Map<String, String> map) {
        return null;
    }

    @Override
    public RedisFuture<String> xadd(String s, XAddArgs xAddArgs, Map<String, String> map) {
        return null;
    }

    @Override
    public RedisFuture<String> xadd(String s, Object... objects) {
        return null;
    }

    @Override
    public RedisFuture<String> xadd(String s, XAddArgs xAddArgs, Object... objects) {
        return null;
    }

    @Override
    public RedisFuture<ClaimedMessages<String, String>> xautoclaim(String s, XAutoClaimArgs<String> xAutoClaimArgs) {
        return null;
    }

    @Override
    public RedisFuture<List<StreamMessage<String, String>>> xclaim(String s, Consumer<String> consumer, long l, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<List<StreamMessage<String, String>>> xclaim(String s, Consumer<String> consumer, XClaimArgs xClaimArgs, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<Long> xdel(String s, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<String> xgroupCreate(XReadArgs.StreamOffset<String> streamOffset, String s) {
        return null;
    }

    @Override
    public RedisFuture<String> xgroupCreate(XReadArgs.StreamOffset<String> streamOffset, String s, XGroupCreateArgs xGroupCreateArgs) {
        return null;
    }

    @Override
    public RedisFuture<Boolean> xgroupCreateconsumer(String s, Consumer<String> consumer) {
        return null;
    }

    @Override
    public RedisFuture<Long> xgroupDelconsumer(String s, Consumer<String> consumer) {
        return null;
    }

    @Override
    public RedisFuture<Boolean> xgroupDestroy(String s, String k1) {
        return null;
    }

    @Override
    public RedisFuture<String> xgroupSetid(XReadArgs.StreamOffset<String> streamOffset, String s) {
        return null;
    }

    @Override
    public RedisFuture<List<Object>> xinfoStream(String s) {
        return null;
    }

    @Override
    public RedisFuture<String> discard() {
        return null;
    }

    @Override
    public RedisFuture<TransactionResult> exec() {
        return null;
    }

    @Override
    public RedisFuture<String> multi() {
        return null;
    }

    @Override
    public RedisFuture<String> watch(String... strings) {
        return null;
    }

    @Override
    public RedisFuture<String> unwatch() {
        return null;
    }

    @Override
    public RedisFuture<List<ScoredValue<String>>> zrevrangebyscoreWithScores(String s, Range<? extends Number> range) {
        return null;
    }

    /**
     * @param s
     * @param v
     * @param v1
     * @param l
     * @param l1
     * @deprecated
     */
    @Override
    @Deprecated
    public RedisFuture<List<ScoredValue<String>>> zrevrangebyscoreWithScores(String s, double v, double v1, long l, long l1) {
        return null;
    }

    /**
     * @param s
     * @param s2
     * @param s1
     * @param l
     * @param l1
     * @deprecated
     */
    @Override
    @Deprecated
    public RedisFuture<List<ScoredValue<String>>> zrevrangebyscoreWithScores(String s, String s2, String s1, long l, long l1) {
        return null;
    }

    @Override
    public RedisFuture<List<ScoredValue<String>>> zrevrangebyscoreWithScores(String s, Range<? extends Number> range, Limit limit) {
        return null;
    }

    /**
     * @param scoredValueStreamingChannel
     * @param s
     * @param v
     * @param v1
     * @deprecated
     */
    @Override
    @Deprecated
    public RedisFuture<Long> zrevrangebyscoreWithScores(ScoredValueStreamingChannel<String> scoredValueStreamingChannel, String s, double v, double v1) {
        return null;
    }

    /**
     * @param scoredValueStreamingChannel
     * @param s
     * @param s2
     * @param s1
     * @deprecated
     */
    @Override
    @Deprecated
    public RedisFuture<Long> zrevrangebyscoreWithScores(ScoredValueStreamingChannel<String> scoredValueStreamingChannel, String s, String s2, String s1) {
        return null;
    }

    @Override
    public RedisFuture<Long> zrevrangebyscoreWithScores(ScoredValueStreamingChannel<String> scoredValueStreamingChannel, String s, Range<? extends Number> range) {
        return null;
    }

    /**
     * @param scoredValueStreamingChannel
     * @param s
     * @param v
     * @param v1
     * @param l
     * @param l1
     * @deprecated
     */
    @Override
    @Deprecated
    public RedisFuture<Long> zrevrangebyscoreWithScores(ScoredValueStreamingChannel<String> scoredValueStreamingChannel, String s, double v, double v1, long l, long l1) {
        return null;
    }

    /**
     * @param scoredValueStreamingChannel
     * @param s
     * @param s2
     * @param s1
     * @param l
     * @param l1
     * @deprecated
     */
    @Override
    @Deprecated
    public RedisFuture<Long> zrevrangebyscoreWithScores(ScoredValueStreamingChannel<String> scoredValueStreamingChannel, String s, String s2, String s1, long l, long l1) {
        return null;
    }

    @Override
    public RedisFuture<Long> zrevrangebyscoreWithScores(ScoredValueStreamingChannel<String> scoredValueStreamingChannel, String s, Range<? extends Number> range, Limit limit) {
        return null;
    }

    @Override
    public RedisFuture<Long> zrevrangestore(String s, String k1, Range<Long> range) {
        return null;
    }

    @Override
    public RedisFuture<Long> zrevrangestorebylex(String s, String k1, Range<? extends String> range, Limit limit) {
        return null;
    }

    @Override
    public RedisFuture<Long> zrevrangestorebyscore(String s, String k1, Range<? extends Number> range, Limit limit) {
        return null;
    }

    @Override
    public RedisFuture<Long> zrevrank(String s, String s2) {
        return null;
    }

    @Override
    public RedisFuture<ScoredValue<Long>> zrevrankWithScore(String s, String s2) {
        return null;
    }

    @Override
    public RedisFuture<ScoredValueScanCursor<String>> zscan(String s) {
        return null;
    }

    @Override
    public RedisFuture<ScoredValueScanCursor<String>> zscan(String s, ScanArgs scanArgs) {
        return null;
    }

    @Override
    public RedisFuture<ScoredValueScanCursor<String>> zscan(String s, ScanCursor scanCursor, ScanArgs scanArgs) {
        return null;
    }

    @Override
    public RedisFuture<ScoredValueScanCursor<String>> zscan(String s, ScanCursor scanCursor) {
        return null;
    }

    @Override
    public RedisFuture<StreamScanCursor> zscan(ScoredValueStreamingChannel<String> scoredValueStreamingChannel, String s) {
        return null;
    }

    @Override
    public RedisFuture<StreamScanCursor> zscan(ScoredValueStreamingChannel<String> scoredValueStreamingChannel, String s, ScanArgs scanArgs) {
        return null;
    }

    @Override
    public RedisFuture<StreamScanCursor> zscan(ScoredValueStreamingChannel<String> scoredValueStreamingChannel, String s, ScanCursor scanCursor, ScanArgs scanArgs) {
        return null;
    }

    @Override
    public RedisFuture<StreamScanCursor> zscan(ScoredValueStreamingChannel<String> scoredValueStreamingChannel, String s, ScanCursor scanCursor) {
        return null;
    }

    @Override
    public RedisFuture<Double> zscore(String s, String s2) {
        return null;
    }

    @Override
    public RedisFuture<List<String>> zunion(String... strings) {
        return null;
    }

    @Override
    public RedisFuture<List<String>> zunion(ZAggregateArgs zAggregateArgs, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<List<ScoredValue<String>>> zunionWithScores(ZAggregateArgs zAggregateArgs, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<List<ScoredValue<String>>> zunionWithScores(String... strings) {
        return null;
    }

    @Override
    public RedisFuture<Long> zunionstore(String s, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<Long> zunionstore(String s, ZStoreArgs zStoreArgs, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<Long> zrangebyscoreWithScores(ScoredValueStreamingChannel<String> scoredValueStreamingChannel, String s, Range<? extends Number> range) {
        return null;
    }

    /**
     * @param scoredValueStreamingChannel
     * @param s
     * @param v
     * @param v1
     * @param l
     * @param l1
     * @deprecated
     */
    @Override
    @Deprecated
    public RedisFuture<Long> zrangebyscoreWithScores(ScoredValueStreamingChannel<String> scoredValueStreamingChannel, String s, double v, double v1, long l, long l1) {
        return null;
    }

    /**
     * @param scoredValueStreamingChannel
     * @param s
     * @param s2
     * @param s1
     * @param l
     * @param l1
     * @deprecated
     */
    @Override
    @Deprecated
    public RedisFuture<Long> zrangebyscoreWithScores(ScoredValueStreamingChannel<String> scoredValueStreamingChannel, String s, String s2, String s1, long l, long l1) {
        return null;
    }

    @Override
    public RedisFuture<Long> zrangebyscoreWithScores(ScoredValueStreamingChannel<String> scoredValueStreamingChannel, String s, Range<? extends Number> range, Limit limit) {
        return null;
    }

    @Override
    public RedisFuture<Long> zrangestore(String s, String k1, Range<Long> range) {
        return null;
    }

    @Override
    public RedisFuture<Long> zrangestorebylex(String s, String k1, Range<? extends String> range, Limit limit) {
        return null;
    }

    @Override
    public RedisFuture<Long> zrangestorebyscore(String s, String k1, Range<? extends Number> range, Limit limit) {
        return null;
    }

    @Override
    public RedisFuture<Long> zrank(String s, String s2) {
        return null;
    }

    @Override
    public RedisFuture<ScoredValue<Long>> zrankWithScore(String s, String s2) {
        return null;
    }

    @Override
    public RedisFuture<Long> zrem(String s, String... strings) {
        return null;
    }

    /**
     * @param s
     * @param s2
     * @param s1
     * @deprecated
     */
    @Override
    @Deprecated
    public RedisFuture<Long> zremrangebylex(String s, String s2, String s1) {
        return null;
    }

    @Override
    public RedisFuture<Long> zremrangebylex(String s, Range<? extends String> range) {
        return null;
    }

    @Override
    public RedisFuture<Long> zremrangebyrank(String s, long l, long l1) {
        return null;
    }

    /**
     * @param s
     * @param v
     * @param v1
     * @deprecated
     */
    @Override
    @Deprecated
    public RedisFuture<Long> zremrangebyscore(String s, double v, double v1) {
        return null;
    }

    /**
     * @param s
     * @param s2
     * @param s1
     * @deprecated
     */
    @Override
    @Deprecated
    public RedisFuture<Long> zremrangebyscore(String s, String s2, String s1) {
        return null;
    }

    @Override
    public RedisFuture<Long> zremrangebyscore(String s, Range<? extends Number> range) {
        return null;
    }

    @Override
    public RedisFuture<List<String>> zrevrange(String s, long l, long l1) {
        return null;
    }

    @Override
    public RedisFuture<Long> zrevrange(ValueStreamingChannel<String> valueStreamingChannel, String s, long l, long l1) {
        return null;
    }

    @Override
    public RedisFuture<List<ScoredValue<String>>> zrevrangeWithScores(String s, long l, long l1) {
        return null;
    }

    @Override
    public RedisFuture<Long> zrevrangeWithScores(ScoredValueStreamingChannel<String> scoredValueStreamingChannel, String s, long l, long l1) {
        return null;
    }

    @Override
    public RedisFuture<List<String>> zrevrangebylex(String s, Range<? extends String> range) {
        return null;
    }

    @Override
    public RedisFuture<List<String>> zrevrangebylex(String s, Range<? extends String> range, Limit limit) {
        return null;
    }

    /**
     * @param s
     * @param v
     * @param v1
     * @deprecated
     */
    @Override
    @Deprecated
    public RedisFuture<List<String>> zrevrangebyscore(String s, double v, double v1) {
        return null;
    }

    /**
     * @param s
     * @param s2
     * @param s1
     * @deprecated
     */
    @Override
    @Deprecated
    public RedisFuture<List<String>> zrevrangebyscore(String s, String s2, String s1) {
        return null;
    }

    @Override
    public RedisFuture<List<String>> zrevrangebyscore(String s, Range<? extends Number> range) {
        return null;
    }

    /**
     * @param s
     * @param v
     * @param v1
     * @param l
     * @param l1
     * @deprecated
     */
    @Override
    @Deprecated
    public RedisFuture<List<String>> zrevrangebyscore(String s, double v, double v1, long l, long l1) {
        return null;
    }

    /**
     * @param s
     * @param s2
     * @param s1
     * @param l
     * @param l1
     * @deprecated
     */
    @Override
    @Deprecated
    public RedisFuture<List<String>> zrevrangebyscore(String s, String s2, String s1, long l, long l1) {
        return null;
    }

    @Override
    public RedisFuture<List<String>> zrevrangebyscore(String s, Range<? extends Number> range, Limit limit) {
        return null;
    }

    /**
     * @param valueStreamingChannel
     * @param s
     * @param v
     * @param v1
     * @deprecated
     */
    @Override
    @Deprecated
    public RedisFuture<Long> zrevrangebyscore(ValueStreamingChannel<String> valueStreamingChannel, String s, double v, double v1) {
        return null;
    }

    /**
     * @param valueStreamingChannel
     * @param s
     * @param s2
     * @param s1
     * @deprecated
     */
    @Override
    @Deprecated
    public RedisFuture<Long> zrevrangebyscore(ValueStreamingChannel<String> valueStreamingChannel, String s, String s2, String s1) {
        return null;
    }

    @Override
    public RedisFuture<Long> zrevrangebyscore(ValueStreamingChannel<String> valueStreamingChannel, String s, Range<? extends Number> range) {
        return null;
    }

    /**
     * @param valueStreamingChannel
     * @param s
     * @param v
     * @param v1
     * @param l
     * @param l1
     * @deprecated
     */
    @Override
    @Deprecated
    public RedisFuture<Long> zrevrangebyscore(ValueStreamingChannel<String> valueStreamingChannel, String s, double v, double v1, long l, long l1) {
        return null;
    }

    /**
     * @param valueStreamingChannel
     * @param s
     * @param s2
     * @param s1
     * @param l
     * @param l1
     * @deprecated
     */
    @Override
    @Deprecated
    public RedisFuture<Long> zrevrangebyscore(ValueStreamingChannel<String> valueStreamingChannel, String s, String s2, String s1, long l, long l1) {
        return null;
    }

    @Override
    public RedisFuture<Long> zrevrangebyscore(ValueStreamingChannel<String> valueStreamingChannel, String s, Range<? extends Number> range, Limit limit) {
        return null;
    }

    /**
     * @param s
     * @param v
     * @param v1
     * @deprecated
     */
    @Override
    @Deprecated
    public RedisFuture<List<ScoredValue<String>>> zrevrangebyscoreWithScores(String s, double v, double v1) {
        return null;
    }

    /**
     * @param s
     * @param s2
     * @param s1
     * @deprecated
     */
    @Override
    @Deprecated
    public RedisFuture<List<ScoredValue<String>>> zrevrangebyscoreWithScores(String s, String s2, String s1) {
        return null;
    }

    @Override
    public RedisFuture<KeyValue<String, ScoredValue<String>>> bzmpop(long l, ZPopArgs zPopArgs, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<KeyValue<String, List<ScoredValue<String>>>> bzmpop(long l, long l1, ZPopArgs zPopArgs, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<KeyValue<String, ScoredValue<String>>> bzmpop(double v, ZPopArgs zPopArgs, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<KeyValue<String, List<ScoredValue<String>>>> bzmpop(double v, int i, ZPopArgs zPopArgs, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<KeyValue<String, ScoredValue<String>>> bzpopmin(long l, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<KeyValue<String, ScoredValue<String>>> bzpopmin(double v, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<KeyValue<String, ScoredValue<String>>> bzpopmax(long l, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<KeyValue<String, ScoredValue<String>>> bzpopmax(double v, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<Long> zadd(String s, double v, String v1) {
        return null;
    }

    @Override
    public RedisFuture<Long> zadd(String s, Object... objects) {
        return null;
    }

    @Override
    public RedisFuture<Long> zadd(String s, ScoredValue<String>... scoredValues) {
        return null;
    }

    @Override
    public RedisFuture<Long> zadd(String s, ZAddArgs zAddArgs, double v, String v1) {
        return null;
    }

    @Override
    public RedisFuture<Long> zadd(String s, ZAddArgs zAddArgs, Object... objects) {
        return null;
    }

    @Override
    public RedisFuture<Long> zadd(String s, ZAddArgs zAddArgs, ScoredValue<String>... scoredValues) {
        return null;
    }

    @Override
    public RedisFuture<Double> zaddincr(String s, double v, String v1) {
        return null;
    }

    @Override
    public RedisFuture<Double> zaddincr(String s, ZAddArgs zAddArgs, double v, String v1) {
        return null;
    }

    @Override
    public RedisFuture<Long> zcard(String s) {
        return null;
    }

    /**
     * @param s
     * @param v
     * @param v1
     * @deprecated
     */
    @Override
    @Deprecated
    public RedisFuture<Long> zcount(String s, double v, double v1) {
        return null;
    }

    /**
     * @param s
     * @param s2
     * @param s1
     * @deprecated
     */
    @Override
    @Deprecated
    public RedisFuture<Long> zcount(String s, String s2, String s1) {
        return null;
    }

    @Override
    public RedisFuture<Long> zcount(String s, Range<? extends Number> range) {
        return null;
    }

    @Override
    public RedisFuture<List<String>> zdiff(String... strings) {
        return null;
    }

    @Override
    public RedisFuture<Long> zdiffstore(String s, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<List<ScoredValue<String>>> zdiffWithScores(String... strings) {
        return null;
    }

    @Override
    public RedisFuture<Double> zincrby(String s, double v, String v1) {
        return null;
    }

    @Override
    public RedisFuture<List<String>> zinter(String... strings) {
        return null;
    }

    @Override
    public RedisFuture<List<String>> zinter(ZAggregateArgs zAggregateArgs, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<Long> zintercard(String... strings) {
        return null;
    }

    @Override
    public RedisFuture<Long> zintercard(long l, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<List<ScoredValue<String>>> zinterWithScores(ZAggregateArgs zAggregateArgs, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<List<ScoredValue<String>>> zinterWithScores(String... strings) {
        return null;
    }

    @Override
    public RedisFuture<Long> zinterstore(String s, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<Long> zinterstore(String s, ZStoreArgs zStoreArgs, String... strings) {
        return null;
    }

    /**
     * @param s
     * @param s2
     * @param s1
     * @deprecated
     */
    @Override
    @Deprecated
    public RedisFuture<Long> zlexcount(String s, String s2, String s1) {
        return null;
    }

    @Override
    public RedisFuture<Long> zlexcount(String s, Range<? extends String> range) {
        return null;
    }

    @Override
    public RedisFuture<List<Double>> zmscore(String s, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<KeyValue<String, ScoredValue<String>>> zmpop(ZPopArgs zPopArgs, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<KeyValue<String, List<ScoredValue<String>>>> zmpop(int i, ZPopArgs zPopArgs, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<ScoredValue<String>> zpopmin(String s) {
        return null;
    }

    @Override
    public RedisFuture<List<ScoredValue<String>>> zpopmin(String s, long l) {
        return null;
    }

    @Override
    public RedisFuture<ScoredValue<String>> zpopmax(String s) {
        return null;
    }

    @Override
    public RedisFuture<List<ScoredValue<String>>> zpopmax(String s, long l) {
        return null;
    }

    @Override
    public RedisFuture<String> zrandmember(String s) {
        return null;
    }

    @Override
    public RedisFuture<List<String>> zrandmember(String s, long l) {
        return null;
    }

    @Override
    public RedisFuture<ScoredValue<String>> zrandmemberWithScores(String s) {
        return null;
    }

    @Override
    public RedisFuture<List<ScoredValue<String>>> zrandmemberWithScores(String s, long l) {
        return null;
    }

    @Override
    public RedisFuture<List<String>> zrange(String s, long l, long l1) {
        return null;
    }

    @Override
    public RedisFuture<Long> zrange(ValueStreamingChannel<String> valueStreamingChannel, String s, long l, long l1) {
        return null;
    }

    @Override
    public RedisFuture<List<ScoredValue<String>>> zrangeWithScores(String s, long l, long l1) {
        return null;
    }

    @Override
    public RedisFuture<Long> zrangeWithScores(ScoredValueStreamingChannel<String> scoredValueStreamingChannel, String s, long l, long l1) {
        return null;
    }

    /**
     * @param s
     * @param s2
     * @param s1
     * @deprecated
     */
    @Override
    @Deprecated
    public RedisFuture<List<String>> zrangebylex(String s, String s2, String s1) {
        return null;
    }

    @Override
    public RedisFuture<List<String>> zrangebylex(String s, Range<? extends String> range) {
        return null;
    }

    /**
     * @param s
     * @param s2
     * @param s1
     * @param l
     * @param l1
     * @deprecated
     */
    @Override
    @Deprecated
    public RedisFuture<List<String>> zrangebylex(String s, String s2, String s1, long l, long l1) {
        return null;
    }

    @Override
    public RedisFuture<List<String>> zrangebylex(String s, Range<? extends String> range, Limit limit) {
        return null;
    }

    /**
     * @param s
     * @param v
     * @param v1
     * @deprecated
     */
    @Override
    @Deprecated
    public RedisFuture<List<String>> zrangebyscore(String s, double v, double v1) {
        return null;
    }

    /**
     * @param s
     * @param s2
     * @param s1
     * @deprecated
     */
    @Override
    @Deprecated
    public RedisFuture<List<String>> zrangebyscore(String s, String s2, String s1) {
        return null;
    }

    @Override
    public RedisFuture<List<String>> zrangebyscore(String s, Range<? extends Number> range) {
        return null;
    }

    /**
     * @param s
     * @param v
     * @param v1
     * @param l
     * @param l1
     * @deprecated
     */
    @Override
    @Deprecated
    public RedisFuture<List<String>> zrangebyscore(String s, double v, double v1, long l, long l1) {
        return null;
    }

    /**
     * @param s
     * @param s2
     * @param s1
     * @param l
     * @param l1
     * @deprecated
     */
    @Override
    @Deprecated
    public RedisFuture<List<String>> zrangebyscore(String s, String s2, String s1, long l, long l1) {
        return null;
    }

    @Override
    public RedisFuture<List<String>> zrangebyscore(String s, Range<? extends Number> range, Limit limit) {
        return null;
    }

    /**
     * @param valueStreamingChannel
     * @param s
     * @param v
     * @param v1
     * @deprecated
     */
    @Override
    @Deprecated
    public RedisFuture<Long> zrangebyscore(ValueStreamingChannel<String> valueStreamingChannel, String s, double v, double v1) {
        return null;
    }

    /**
     * @param valueStreamingChannel
     * @param s
     * @param s2
     * @param s1
     * @deprecated
     */
    @Override
    @Deprecated
    public RedisFuture<Long> zrangebyscore(ValueStreamingChannel<String> valueStreamingChannel, String s, String s2, String s1) {
        return null;
    }

    @Override
    public RedisFuture<Long> zrangebyscore(ValueStreamingChannel<String> valueStreamingChannel, String s, Range<? extends Number> range) {
        return null;
    }

    /**
     * @param valueStreamingChannel
     * @param s
     * @param v
     * @param v1
     * @param l
     * @param l1
     * @deprecated
     */
    @Override
    @Deprecated
    public RedisFuture<Long> zrangebyscore(ValueStreamingChannel<String> valueStreamingChannel, String s, double v, double v1, long l, long l1) {
        return null;
    }

    /**
     * @param valueStreamingChannel
     * @param s
     * @param s2
     * @param s1
     * @param l
     * @param l1
     * @deprecated
     */
    @Override
    @Deprecated
    public RedisFuture<Long> zrangebyscore(ValueStreamingChannel<String> valueStreamingChannel, String s, String s2, String s1, long l, long l1) {
        return null;
    }

    @Override
    public RedisFuture<Long> zrangebyscore(ValueStreamingChannel<String> valueStreamingChannel, String s, Range<? extends Number> range, Limit limit) {
        return null;
    }

    /**
     * @param s
     * @param v
     * @param v1
     * @deprecated
     */
    @Override
    @Deprecated
    public RedisFuture<List<ScoredValue<String>>> zrangebyscoreWithScores(String s, double v, double v1) {
        return null;
    }

    /**
     * @param s
     * @param s2
     * @param s1
     * @deprecated
     */
    @Override
    @Deprecated
    public RedisFuture<List<ScoredValue<String>>> zrangebyscoreWithScores(String s, String s2, String s1) {
        return null;
    }

    @Override
    public RedisFuture<List<ScoredValue<String>>> zrangebyscoreWithScores(String s, Range<? extends Number> range) {
        return null;
    }

    /**
     * @param s
     * @param v
     * @param v1
     * @param l
     * @param l1
     * @deprecated
     */
    @Override
    @Deprecated
    public RedisFuture<List<ScoredValue<String>>> zrangebyscoreWithScores(String s, double v, double v1, long l, long l1) {
        return null;
    }

    /**
     * @param s
     * @param s2
     * @param s1
     * @param l
     * @param l1
     * @deprecated
     */
    @Override
    @Deprecated
    public RedisFuture<List<ScoredValue<String>>> zrangebyscoreWithScores(String s, String s2, String s1, long l, long l1) {
        return null;
    }

    @Override
    public RedisFuture<List<ScoredValue<String>>> zrangebyscoreWithScores(String s, Range<? extends Number> range, Limit limit) {
        return null;
    }

    /**
     * @param scoredValueStreamingChannel
     * @param s
     * @param v
     * @param v1
     * @deprecated
     */
    @Override
    @Deprecated
    public RedisFuture<Long> zrangebyscoreWithScores(ScoredValueStreamingChannel<String> scoredValueStreamingChannel, String s, double v, double v1) {
        return null;
    }

    /**
     * @param scoredValueStreamingChannel
     * @param s
     * @param s2
     * @param s1
     * @deprecated
     */
    @Override
    @Deprecated
    public RedisFuture<Long> zrangebyscoreWithScores(ScoredValueStreamingChannel<String> scoredValueStreamingChannel, String s, String s2, String s1) {
        return null;
    }

    @Override
    public RedisFuture<Long> sadd(String s, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<Long> scard(String s) {
        return null;
    }

    @Override
    public RedisFuture<Set<String>> sdiff(String... strings) {
        return null;
    }

    @Override
    public RedisFuture<Long> sdiff(ValueStreamingChannel<String> valueStreamingChannel, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<Long> sdiffstore(String s, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<Set<String>> sinter(String... strings) {
        return null;
    }

    @Override
    public RedisFuture<Long> sinter(ValueStreamingChannel<String> valueStreamingChannel, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<Long> sintercard(String... strings) {
        return null;
    }

    @Override
    public RedisFuture<Long> sintercard(long l, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<Long> sinterstore(String s, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<Boolean> sismember(String s, String s2) {
        return null;
    }

    @Override
    public RedisFuture<Boolean> smove(String s, String k1, String s2) {
        return null;
    }

    @Override
    public RedisFuture<Set<String>> smembers(String s) {
        return null;
    }

    @Override
    public RedisFuture<Long> smembers(ValueStreamingChannel<String> valueStreamingChannel, String s) {
        return null;
    }

    @Override
    public RedisFuture<List<Boolean>> smismember(String s, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<String> spop(String s) {
        return null;
    }

    @Override
    public RedisFuture<Set<String>> spop(String s, long l) {
        return null;
    }

    @Override
    public RedisFuture<String> srandmember(String s) {
        return null;
    }

    @Override
    public RedisFuture<List<String>> srandmember(String s, long l) {
        return null;
    }

    @Override
    public RedisFuture<Long> srandmember(ValueStreamingChannel<String> valueStreamingChannel, String s, long l) {
        return null;
    }

    @Override
    public RedisFuture<Long> srem(String s, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<Set<String>> sunion(String... strings) {
        return null;
    }

    @Override
    public RedisFuture<Long> sunion(ValueStreamingChannel<String> valueStreamingChannel, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<Long> sunionstore(String s, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<ValueScanCursor<String>> sscan(String s) {
        return null;
    }

    @Override
    public RedisFuture<ValueScanCursor<String>> sscan(String s, ScanArgs scanArgs) {
        return null;
    }

    @Override
    public RedisFuture<ValueScanCursor<String>> sscan(String s, ScanCursor scanCursor, ScanArgs scanArgs) {
        return null;
    }

    @Override
    public RedisFuture<ValueScanCursor<String>> sscan(String s, ScanCursor scanCursor) {
        return null;
    }

    @Override
    public RedisFuture<StreamScanCursor> sscan(ValueStreamingChannel<String> valueStreamingChannel, String s) {
        return null;
    }

    @Override
    public RedisFuture<StreamScanCursor> sscan(ValueStreamingChannel<String> valueStreamingChannel, String s, ScanArgs scanArgs) {
        return null;
    }

    @Override
    public RedisFuture<StreamScanCursor> sscan(ValueStreamingChannel<String> valueStreamingChannel, String s, ScanCursor scanCursor, ScanArgs scanArgs) {
        return null;
    }

    @Override
    public RedisFuture<StreamScanCursor> sscan(ValueStreamingChannel<String> valueStreamingChannel, String s, ScanCursor scanCursor) {
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
    public RedisFuture<String> clientCaching(boolean b) {
        return null;
    }

    @Override
    public RedisFuture<String> clientGetname() {
        return null;
    }

    @Override
    public RedisFuture<Long> clientGetredir() {
        return null;
    }

    @Override
    public RedisFuture<String> clientSetname(String s) {
        return null;
    }

    @Override
    public RedisFuture<String> clientSetinfo(String s, String s1) {
        return null;
    }

    @Override
    public RedisFuture<String> clientTracking(TrackingArgs trackingArgs) {
        return null;
    }

    @Override
    public RedisFuture<String> clientKill(String s) {
        return null;
    }

    @Override
    public RedisFuture<Long> clientKill(KillArgs killArgs) {
        return null;
    }

    @Override
    public RedisFuture<Long> clientUnblock(long l, UnblockType unblockType) {
        return null;
    }

    @Override
    public RedisFuture<String> clientPause(long l) {
        return null;
    }

    @Override
    public RedisFuture<String> clientList() {
        return null;
    }

    @Override
    public RedisFuture<String> clientList(ClientListArgs clientListArgs) {
        return null;
    }

    @Override
    public RedisFuture<String> clientInfo() {
        return null;
    }

    @Override
    public RedisFuture<String> clientNoEvict(boolean b) {
        return null;
    }

    @Override
    public RedisFuture<Long> clientId() {
        return null;
    }

    @Override
    public RedisFuture<List<Object>> command() {
        return null;
    }

    @Override
    public RedisFuture<List<Object>> commandInfo(String... strings) {
        return null;
    }

    @Override
    public RedisFuture<List<Object>> commandInfo(CommandType... commandTypes) {
        return null;
    }

    @Override
    public RedisFuture<Long> commandCount() {
        return null;
    }

    @Override
    public RedisFuture<Map<String, String>> configGet(String s) {
        return null;
    }

    @Override
    public RedisFuture<Map<String, String>> configGet(String... strings) {
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
    public RedisFuture<String> configSet(String s, String s1) {
        return null;
    }

    @Override
    public RedisFuture<String> configSet(Map<String, String> map) {
        return null;
    }

    @Override
    public RedisFuture<Long> dbsize() {
        return null;
    }

    @Override
    public RedisFuture<String> debugCrashAndRecover(Long aLong) {
        return null;
    }

    @Override
    public RedisFuture<String> debugHtstats(int i) {
        return null;
    }

    @Override
    public RedisFuture<String> debugObject(String s) {
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
    public RedisFuture<String> debugRestart(Long aLong) {
        return null;
    }

    @Override
    public RedisFuture<String> debugSdslen(String s) {
        return null;
    }

    @Override
    public RedisFuture<String> flushall() {
        return null;
    }

    @Override
    public RedisFuture<String> flushall(FlushMode flushMode) {
        return null;
    }

    @Override
    public RedisFuture<String> flushallAsync() {
        return null;
    }

    @Override
    public RedisFuture<String> flushdb() {
        return null;
    }

    @Override
    public RedisFuture<String> flushdb(FlushMode flushMode) {
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
    public RedisFuture<String> info(String s) {
        return null;
    }

    @Override
    public RedisFuture<Date> lastsave() {
        return null;
    }

    @Override
    public RedisFuture<Long> memoryUsage(String s) {
        return null;
    }

    @Override
    public RedisFuture<String> replicaof(String s, int i) {
        return null;
    }

    @Override
    public RedisFuture<String> replicaofNoOne() {
        return null;
    }

    @Override
    public RedisFuture<String> save() {
        return null;
    }

    @Override
    public void shutdown(boolean b) {

    }

    @Override
    public void shutdown(ShutdownArgs shutdownArgs) {

    }

    @Override
    public RedisFuture<String> slaveof(String s, int i) {
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
    public RedisFuture<List<Object>> slowlogGet(int i) {
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
    public <T> RedisFuture<T> eval(String s, ScriptOutputType scriptOutputType, String... strings) {
        return null;
    }

    @Override
    public <T> RedisFuture<T> eval(byte[] bytes, ScriptOutputType scriptOutputType, String... strings) {
        return null;
    }

    @Override
    public <T> RedisFuture<T> eval(String s, ScriptOutputType scriptOutputType, String[] strings, String... strings2) {
        return null;
    }

    @Override
    public <T> RedisFuture<T> eval(byte[] bytes, ScriptOutputType scriptOutputType, String[] strings, String... strings2) {
        return null;
    }

    @Override
    public <T> RedisFuture<T> evalReadOnly(byte[] bytes, ScriptOutputType scriptOutputType, String[] strings, String... strings2) {
        return null;
    }

    @Override
    public <T> RedisFuture<T> evalsha(String s, ScriptOutputType scriptOutputType, String... strings) {
        return null;
    }

    @Override
    public <T> RedisFuture<T> evalsha(String s, ScriptOutputType scriptOutputType, String[] strings, String... strings2) {
        return null;
    }

    @Override
    public <T> RedisFuture<T> evalshaReadOnly(String s, ScriptOutputType scriptOutputType, String[] strings, String... strings2) {
        return null;
    }

    @Override
    public RedisFuture<List<Boolean>> scriptExists(String... strings) {
        return null;
    }

    @Override
    public RedisFuture<String> scriptFlush() {
        return null;
    }

    @Override
    public RedisFuture<String> scriptFlush(FlushMode flushMode) {
        return null;
    }

    @Override
    public RedisFuture<String> scriptKill() {
        return null;
    }

    @Override
    public RedisFuture<String> scriptLoad(String s) {
        return null;
    }

    @Override
    public RedisFuture<String> scriptLoad(byte[] bytes) {
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
    public RedisFuture<String> blmove(String s, String k1, LMoveArgs lMoveArgs, long l) {
        return null;
    }

    @Override
    public RedisFuture<String> blmove(String s, String k1, LMoveArgs lMoveArgs, double v) {
        return null;
    }

    @Override
    public RedisFuture<KeyValue<String, List<String>>> blmpop(long l, LMPopArgs lmPopArgs, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<KeyValue<String, List<String>>> blmpop(double v, LMPopArgs lmPopArgs, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<KeyValue<String, String>> blpop(long l, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<KeyValue<String, String>> blpop(double v, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<KeyValue<String, String>> brpop(long l, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<KeyValue<String, String>> brpop(double v, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<String> brpoplpush(long l, String s, String k1) {
        return null;
    }

    @Override
    public RedisFuture<String> brpoplpush(double v, String s, String k1) {
        return null;
    }

    @Override
    public RedisFuture<String> lindex(String s, long l) {
        return null;
    }

    @Override
    public RedisFuture<Long> linsert(String s, boolean b, String s2, String v1) {
        return null;
    }

    @Override
    public RedisFuture<Long> llen(String s) {
        return null;
    }

    @Override
    public RedisFuture<String> lmove(String s, String k1, LMoveArgs lMoveArgs) {
        return null;
    }

    @Override
    public RedisFuture<KeyValue<String, List<String>>> lmpop(LMPopArgs lmPopArgs, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<String> lpop(String s) {
        return null;
    }

    @Override
    public RedisFuture<List<String>> lpop(String s, long l) {
        return null;
    }

    @Override
    public RedisFuture<Long> lpos(String s, String s2) {
        return null;
    }

    @Override
    public RedisFuture<Long> lpos(String s, String s2, LPosArgs lPosArgs) {
        return null;
    }

    @Override
    public RedisFuture<List<Long>> lpos(String s, String s2, int i) {
        return null;
    }

    @Override
    public RedisFuture<List<Long>> lpos(String s, String s2, int i, LPosArgs lPosArgs) {
        return null;
    }

    @Override
    public RedisFuture<Long> lpush(String s, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<Long> lpushx(String s, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<List<String>> lrange(String s, long l, long l1) {
        return null;
    }

    @Override
    public RedisFuture<Long> lrange(ValueStreamingChannel<String> valueStreamingChannel, String s, long l, long l1) {
        return null;
    }

    @Override
    public RedisFuture<Long> lrem(String s, long l, String s2) {
        return null;
    }

    @Override
    public RedisFuture<String> lset(String s, long l, String s2) {
        return null;
    }

    @Override
    public RedisFuture<String> ltrim(String s, long l, long l1) {
        return null;
    }

    @Override
    public RedisFuture<String> rpop(String s) {
        return null;
    }

    @Override
    public RedisFuture<List<String>> rpop(String s, long l) {
        return null;
    }

    @Override
    public RedisFuture<String> rpoplpush(String s, String k1) {
        return null;
    }

    @Override
    public RedisFuture<Long> rpush(String s, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<Long> rpushx(String s, String... strings) {
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
    public RedisFuture<StreamScanCursor> scan(KeyStreamingChannel<String> keyStreamingChannel) {
        return null;
    }

    @Override
    public RedisFuture<StreamScanCursor> scan(KeyStreamingChannel<String> keyStreamingChannel, ScanArgs scanArgs) {
        return null;
    }

    @Override
    public RedisFuture<StreamScanCursor> scan(KeyStreamingChannel<String> keyStreamingChannel, ScanCursor scanCursor, ScanArgs scanArgs) {
        return null;
    }

    @Override
    public RedisFuture<StreamScanCursor> scan(KeyStreamingChannel<String> keyStreamingChannel, ScanCursor scanCursor) {
        return null;
    }

    @Override
    public RedisFuture<Long> unlink(String... strings) {
        return null;
    }

    @Override
    public RedisFuture<byte[]> dump(String s) {
        return null;
    }

    @Override
    public RedisFuture<Long> exists(String... strings) {
        return null;
    }

    @Override
    public RedisFuture<Boolean> expire(String s, long l) {
        return null;
    }

    @Override
    public RedisFuture<Boolean> expire(String s, long l, ExpireArgs expireArgs) {
        return null;
    }

    @Override
    public RedisFuture<Boolean> expire(String s, Duration duration) {
        return null;
    }

    @Override
    public RedisFuture<Boolean> expire(String s, Duration duration, ExpireArgs expireArgs) {
        return null;
    }

    @Override
    public RedisFuture<Boolean> expireat(String s, Date date) {
        return null;
    }

    @Override
    public RedisFuture<Boolean> expireat(String s, Date date, ExpireArgs expireArgs) {
        return null;
    }

    @Override
    public RedisFuture<Boolean> expireat(String s, Instant instant) {
        return null;
    }

    @Override
    public RedisFuture<Boolean> expireat(String s, Instant instant, ExpireArgs expireArgs) {
        return null;
    }

    @Override
    public RedisFuture<Long> expiretime(String s) {
        return null;
    }

    @Override
    public RedisFuture<Boolean> expireat(String s, long l) {
        return null;
    }

    @Override
    public RedisFuture<Boolean> expireat(String s, long l, ExpireArgs expireArgs) {
        return null;
    }

    @Override
    public RedisFuture<List<String>> keys(String s) {
        return null;
    }

    @Override
    public RedisFuture<Long> keys(KeyStreamingChannel<String> keyStreamingChannel, String s) {
        return null;
    }

    @Override
    public RedisFuture<String> migrate(String s, int i, String s2, int i1, long l) {
        return null;
    }

    @Override
    public RedisFuture<String> migrate(String s, int i, int i1, long l, MigrateArgs<String> migrateArgs) {
        return null;
    }

    @Override
    public RedisFuture<Boolean> move(String s, int i) {
        return null;
    }

    @Override
    public RedisFuture<String> objectEncoding(String s) {
        return null;
    }

    @Override
    public RedisFuture<Long> objectFreq(String s) {
        return null;
    }

    @Override
    public RedisFuture<Long> objectIdletime(String s) {
        return null;
    }

    @Override
    public RedisFuture<Long> objectRefcount(String s) {
        return null;
    }

    @Override
    public RedisFuture<Boolean> persist(String s) {
        return null;
    }

    @Override
    public RedisFuture<Boolean> pexpire(String s, long l) {
        return null;
    }

    @Override
    public RedisFuture<Boolean> pexpire(String s, long l, ExpireArgs expireArgs) {
        return null;
    }

    @Override
    public RedisFuture<Boolean> pexpire(String s, Duration duration) {
        return null;
    }

    @Override
    public RedisFuture<Boolean> pexpire(String s, Duration duration, ExpireArgs expireArgs) {
        return null;
    }

    @Override
    public RedisFuture<Boolean> pexpireat(String s, Date date) {
        return null;
    }

    @Override
    public RedisFuture<Boolean> pexpireat(String s, Date date, ExpireArgs expireArgs) {
        return null;
    }

    @Override
    public RedisFuture<Boolean> pexpireat(String s, Instant instant) {
        return null;
    }

    @Override
    public RedisFuture<Boolean> pexpireat(String s, Instant instant, ExpireArgs expireArgs) {
        return null;
    }

    @Override
    public RedisFuture<Long> pexpiretime(String s) {
        return null;
    }

    @Override
    public RedisFuture<Boolean> pexpireat(String s, long l) {
        return null;
    }

    @Override
    public RedisFuture<Boolean> pexpireat(String s, long l, ExpireArgs expireArgs) {
        return null;
    }

    @Override
    public RedisFuture<Long> pttl(String s) {
        return null;
    }

    @Override
    public RedisFuture<String> randomkey() {
        return null;
    }

    @Override
    public RedisFuture<String> rename(String s, String k1) {
        return null;
    }

    @Override
    public RedisFuture<Boolean> renamenx(String s, String k1) {
        return null;
    }

    @Override
    public RedisFuture<String> restore(String s, long l, byte[] bytes) {
        return null;
    }

    @Override
    public RedisFuture<String> restore(String s, byte[] bytes, RestoreArgs restoreArgs) {
        return null;
    }

    @Override
    public RedisFuture<List<String>> sort(String s) {
        return null;
    }

    @Override
    public RedisFuture<Long> sort(ValueStreamingChannel<String> valueStreamingChannel, String s) {
        return null;
    }

    @Override
    public RedisFuture<List<String>> sort(String s, SortArgs sortArgs) {
        return null;
    }

    @Override
    public RedisFuture<Long> sort(ValueStreamingChannel<String> valueStreamingChannel, String s, SortArgs sortArgs) {
        return null;
    }

    @Override
    public RedisFuture<List<String>> sortReadOnly(String s) {
        return null;
    }

    @Override
    public RedisFuture<Long> sortReadOnly(ValueStreamingChannel<String> valueStreamingChannel, String s) {
        return null;
    }

    @Override
    public RedisFuture<List<String>> sortReadOnly(String s, SortArgs sortArgs) {
        return null;
    }

    @Override
    public RedisFuture<Long> sortReadOnly(ValueStreamingChannel<String> valueStreamingChannel, String s, SortArgs sortArgs) {
        return null;
    }

    @Override
    public RedisFuture<Long> sortStore(String s, SortArgs sortArgs, String k1) {
        return null;
    }

    @Override
    public RedisFuture<Long> touch(String... strings) {
        return null;
    }

    @Override
    public RedisFuture<Long> ttl(String s) {
        return null;
    }

    @Override
    public RedisFuture<String> type(String s) {
        return null;
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
    public RedisFuture<Long> hdel(String s, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<Boolean> hexists(String s, String k1) {
        return null;
    }

    @Override
    public RedisFuture<String> hget(String s, String k1) {
        return null;
    }

    @Override
    public RedisFuture<Long> hincrby(String s, String k1, long l) {
        return null;
    }

    @Override
    public RedisFuture<Double> hincrbyfloat(String s, String k1, double v) {
        return null;
    }

    @Override
    public RedisFuture<Map<String, String>> hgetall(String s) {
        return null;
    }

    @Override
    public RedisFuture<Long> hgetall(KeyValueStreamingChannel<String, String> keyValueStreamingChannel, String s) {
        return null;
    }

    @Override
    public RedisFuture<List<String>> hkeys(String s) {
        return null;
    }

    @Override
    public RedisFuture<Long> hkeys(KeyStreamingChannel<String> keyStreamingChannel, String s) {
        return null;
    }

    @Override
    public RedisFuture<Long> hlen(String s) {
        return null;
    }

    @Override
    public RedisFuture<List<KeyValue<String, String>>> hmget(String s, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<Long> hmget(KeyValueStreamingChannel<String, String> keyValueStreamingChannel, String s, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<String> hmset(String s, Map<String, String> map) {
        return null;
    }

    @Override
    public RedisFuture<String> hrandfield(String s) {
        return null;
    }

    @Override
    public RedisFuture<List<String>> hrandfield(String s, long l) {
        return null;
    }

    @Override
    public RedisFuture<KeyValue<String, String>> hrandfieldWithvalues(String s) {
        return null;
    }

    @Override
    public RedisFuture<List<KeyValue<String, String>>> hrandfieldWithvalues(String s, long l) {
        return null;
    }

    @Override
    public RedisFuture<MapScanCursor<String, String>> hscan(String s) {
        return null;
    }

    @Override
    public RedisFuture<MapScanCursor<String, String>> hscan(String s, ScanArgs scanArgs) {
        return null;
    }

    @Override
    public RedisFuture<MapScanCursor<String, String>> hscan(String s, ScanCursor scanCursor, ScanArgs scanArgs) {
        return null;
    }

    @Override
    public RedisFuture<MapScanCursor<String, String>> hscan(String s, ScanCursor scanCursor) {
        return null;
    }

    @Override
    public RedisFuture<StreamScanCursor> hscan(KeyValueStreamingChannel<String, String> keyValueStreamingChannel, String s) {
        return null;
    }

    @Override
    public RedisFuture<StreamScanCursor> hscan(KeyValueStreamingChannel<String, String> keyValueStreamingChannel, String s, ScanArgs scanArgs) {
        return null;
    }

    @Override
    public RedisFuture<StreamScanCursor> hscan(KeyValueStreamingChannel<String, String> keyValueStreamingChannel, String s, ScanCursor scanCursor, ScanArgs scanArgs) {
        return null;
    }

    @Override
    public RedisFuture<StreamScanCursor> hscan(KeyValueStreamingChannel<String, String> keyValueStreamingChannel, String s, ScanCursor scanCursor) {
        return null;
    }

    @Override
    public RedisFuture<Boolean> hset(String s, String k1, String s2) {
        return null;
    }

    @Override
    public RedisFuture<Long> hset(String s, Map<String, String> map) {
        return null;
    }

    @Override
    public RedisFuture<Boolean> hsetnx(String s, String k1, String s2) {
        return null;
    }

    @Override
    public RedisFuture<Long> hstrlen(String s, String k1) {
        return null;
    }

    @Override
    public RedisFuture<List<String>> hvals(String s) {
        return null;
    }

    @Override
    public RedisFuture<Long> hvals(ValueStreamingChannel<String> valueStreamingChannel, String s) {
        return null;
    }

    @Override
    public RedisFuture<Long> pfadd(String s, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<String> pfmerge(String s, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<Long> pfcount(String... strings) {
        return null;
    }

    @Override
    public RedisFuture<Long> geoadd(String s, double v, double v1, String v2) {
        return null;
    }

    @Override
    public RedisFuture<Long> geoadd(String s, double v, double v1, String v2, GeoAddArgs geoAddArgs) {
        return null;
    }

    @Override
    public RedisFuture<Long> geoadd(String s, Object... objects) {
        return null;
    }

    @Override
    public RedisFuture<Long> geoadd(String s, GeoValue<String>... geoValues) {
        return null;
    }

    @Override
    public RedisFuture<Long> geoadd(String s, GeoAddArgs geoAddArgs, Object... objects) {
        return null;
    }

    @Override
    public RedisFuture<Long> geoadd(String s, GeoAddArgs geoAddArgs, GeoValue<String>... geoValues) {
        return null;
    }

    @Override
    public RedisFuture<List<Value<String>>> geohash(String s, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<Set<String>> georadius(String s, double v, double v1, double v2, GeoArgs.Unit unit) {
        return null;
    }

    @Override
    public RedisFuture<List<GeoWithin<String>>> georadius(String s, double v, double v1, double v2, GeoArgs.Unit unit, GeoArgs geoArgs) {
        return null;
    }

    @Override
    public RedisFuture<Long> georadius(String s, double v, double v1, double v2, GeoArgs.Unit unit, GeoRadiusStoreArgs<String> geoRadiusStoreArgs) {
        return null;
    }

    @Override
    public RedisFuture<Set<String>> georadiusbymember(String s, String s2, double v1, GeoArgs.Unit unit) {
        return null;
    }

    @Override
    public RedisFuture<List<GeoWithin<String>>> georadiusbymember(String s, String s2, double v1, GeoArgs.Unit unit, GeoArgs geoArgs) {
        return null;
    }

    @Override
    public RedisFuture<Long> georadiusbymember(String s, String s2, double v1, GeoArgs.Unit unit, GeoRadiusStoreArgs<String> geoRadiusStoreArgs) {
        return null;
    }

    @Override
    public RedisFuture<Set<String>> geosearch(String s, GeoSearch.GeoRef<String> geoRef, GeoSearch.GeoPredicate geoPredicate) {
        return null;
    }

    @Override
    public RedisFuture<List<GeoWithin<String>>> geosearch(String s, GeoSearch.GeoRef<String> geoRef, GeoSearch.GeoPredicate geoPredicate, GeoArgs geoArgs) {
        return null;
    }

    @Override
    public RedisFuture<Long> geosearchstore(String s, String k1, GeoSearch.GeoRef<String> geoRef, GeoSearch.GeoPredicate geoPredicate, GeoArgs geoArgs, boolean b) {
        return null;
    }

    @Override
    public RedisFuture<List<GeoCoordinates>> geopos(String s, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<Double> geodist(String s, String s2, String v1, GeoArgs.Unit unit) {
        return null;
    }

    @Override
    public RedisFuture<Long> publish(String s, String s2) {
        return null;
    }

    @Override
    public RedisFuture<List<String>> pubsubChannels() {
        return null;
    }

    @Override
    public RedisFuture<List<String>> pubsubChannels(String s) {
        return null;
    }

    @Override
    public RedisFuture<Map<String, Long>> pubsubNumsub(String... strings) {
        return null;
    }

    @Override
    public RedisFuture<Long> pubsubNumpat() {
        return null;
    }

    @Override
    public RedisFuture<String> echo(String s) {
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
    public RedisFuture<String> readOnly() {
        return null;
    }

    @Override
    public RedisFuture<String> readWrite() {
        return null;
    }

    @Override
    public RedisFuture<String> quit() {
        return null;
    }

    @Override
    public RedisFuture<Long> waitForReplication(int i, long l) {
        return null;
    }

    @Override
    public <T> RedisFuture<T> dispatch(ProtocolKeyword protocolKeyword, CommandOutput<String, String, T> commandOutput) {
        return null;
    }

    @Override
    public <T> RedisFuture<T> dispatch(ProtocolKeyword protocolKeyword, CommandOutput<String, String, T> commandOutput, CommandArgs<String, String> commandArgs) {
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
    public void setAutoFlushCommands(boolean b) {

    }

    @Override
    public void flushCommands() {

    }

    @Override
    public RedisFuture<String> auth(CharSequence charSequence) {
        return null;
    }

    @Override
    public RedisFuture<String> auth(String s, CharSequence charSequence) {
        return null;
    }

    @Override
    public RedisFuture<String> select(int i) {
        return null;
    }

    @Override
    public RedisFuture<String> swapdb(int i, int i1) {
        return null;
    }

    @Override
    public StatefulRedisConnection<String, String> getStatefulConnection() {
        return null;
    }

    @Override
    public void setTimeout(Duration duration) {

    }

    @Override
    public RedisFuture<String> clusterBumpepoch() {
        return null;
    }

    @Override
    public RedisFuture<String> clusterMeet(String s, int i) {
        return null;
    }

    @Override
    public RedisFuture<String> clusterForget(String s) {
        return null;
    }

    @Override
    public RedisFuture<String> clusterAddSlots(int... ints) {
        return null;
    }

    @Override
    public RedisFuture<String> clusterDelSlots(int... ints) {
        return null;
    }

    @Override
    public RedisFuture<String> clusterDelSlotsRange(Range<Integer>... ranges) {
        return null;
    }

    @Override
    public RedisFuture<String> clusterSetSlotNode(int i, String s) {
        return null;
    }

    @Override
    public RedisFuture<String> clusterSetSlotStable(int i) {
        return null;
    }

    @Override
    public RedisFuture<List<Object>> clusterShards() {
        return null;
    }

    @Override
    public RedisFuture<String> clusterSetSlotMigrating(int i, String s) {
        return null;
    }

    @Override
    public RedisFuture<String> clusterSetSlotImporting(int i, String s) {
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
    public RedisFuture<List<String>> clusterSlaves(String s) {
        return null;
    }

    @Override
    public RedisFuture<List<String>> clusterGetKeysInSlot(int i, int i1) {
        return null;
    }

    @Override
    public RedisFuture<Long> clusterCountKeysInSlot(int i) {
        return null;
    }

    @Override
    public RedisFuture<String> clusterAddSlotsRange(Range<Integer>... ranges) {
        return null;
    }

    @Override
    public RedisFuture<Long> clusterCountFailureReports(String s) {
        return null;
    }

    @Override
    public RedisFuture<Long> clusterKeyslot(String s) {
        return null;
    }

    @Override
    public RedisFuture<String> clusterSaveconfig() {
        return null;
    }

    @Override
    public RedisFuture<String> clusterSetConfigEpoch(long l) {
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
    public RedisFuture<String> clusterReplicate(String s) {
        return null;
    }

    @Override
    public RedisFuture<List<String>> clusterReplicas(String s) {
        return null;
    }

    @Override
    public RedisFuture<String> clusterFailover(boolean b) {
        return null;
    }

    @Override
    public RedisFuture<String> clusterFailover(boolean b, boolean b1) {
        return null;
    }

    @Override
    public RedisFuture<String> clusterReset(boolean b) {
        return null;
    }

    @Override
    public RedisFuture<String> clusterFlushslots() {
        return null;
    }

    @Override
    public RedisFuture<Boolean> copy(String s, String k1) {
        return null;
    }

    @Override
    public RedisFuture<Boolean> copy(String s, String k1, CopyArgs copyArgs) {
        return null;
    }

    @Override
    public RedisFuture<Long> del(String... strings) {
        return null;
    }

    @Override
    public RedisFuture<List<KeyValue<String, String>>> mget(String... strings) {
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
    public RedisFuture<Set<AclCategory>> aclCat() {
        return null;
    }

    @Override
    public RedisFuture<Set<CommandType>> aclCat(AclCategory aclCategory) {
        return null;
    }

    @Override
    public RedisFuture<Long> aclDeluser(String... strings) {
        return null;
    }

    @Override
    public RedisFuture<String> aclDryRun(String s, String s1, String... strings) {
        return null;
    }

    @Override
    public RedisFuture<String> aclDryRun(String s, RedisCommand<String, String, ?> redisCommand) {
        return null;
    }

    @Override
    public RedisFuture<String> aclGenpass() {
        return null;
    }

    @Override
    public RedisFuture<String> aclGenpass(int i) {
        return null;
    }

    @Override
    public RedisFuture<List<Object>> aclGetuser(String s) {
        return null;
    }

    @Override
    public RedisFuture<List<String>> aclList() {
        return null;
    }

    @Override
    public RedisFuture<String> aclLoad() {
        return null;
    }

    @Override
    public RedisFuture<List<Map<String, Object>>> aclLog() {
        return null;
    }

    @Override
    public RedisFuture<List<Map<String, Object>>> aclLog(int i) {
        return null;
    }

    @Override
    public RedisFuture<String> aclLogReset() {
        return null;
    }

    @Override
    public RedisFuture<String> aclSave() {
        return null;
    }

    @Override
    public RedisFuture<String> aclSetuser(String s, AclSetuserArgs aclSetuserArgs) {
        return null;
    }

    @Override
    public RedisFuture<List<String>> aclUsers() {
        return null;
    }

    @Override
    public RedisFuture<String> aclWhoami() {
        return null;
    }

    @Override
    public <T> RedisFuture<T> fcall(String s, ScriptOutputType scriptOutputType, String... strings) {
        return null;
    }

    @Override
    public <T> RedisFuture<T> fcall(String s, ScriptOutputType scriptOutputType, String[] strings, String... strings2) {
        return null;
    }

    @Override
    public <T> RedisFuture<T> fcallReadOnly(String s, ScriptOutputType scriptOutputType, String... strings) {
        return null;
    }

    @Override
    public <T> RedisFuture<T> fcallReadOnly(String s, ScriptOutputType scriptOutputType, String[] strings, String... strings2) {
        return null;
    }

    @Override
    public RedisFuture<String> functionLoad(String s) {
        return null;
    }

    @Override
    public RedisFuture<String> functionLoad(String s, boolean b) {
        return null;
    }

    @Override
    public RedisFuture<byte[]> functionDump() {
        return null;
    }

    @Override
    public RedisFuture<String> functionRestore(byte[] bytes) {
        return null;
    }

    @Override
    public RedisFuture<String> functionRestore(byte[] bytes, FunctionRestoreMode functionRestoreMode) {
        return null;
    }

    @Override
    public RedisFuture<String> functionFlush(FlushMode flushMode) {
        return null;
    }

    @Override
    public RedisFuture<String> functionKill() {
        return null;
    }

    @Override
    public RedisFuture<List<Map<String, Object>>> functionList() {
        return null;
    }

    @Override
    public RedisFuture<List<Map<String, Object>>> functionList(String s) {
        return null;
    }
}
