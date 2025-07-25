package com.cdkjframework.redis.realize;

import io.lettuce.core.*;
import io.lettuce.core.api.StatefulConnection;
import io.lettuce.core.cluster.SlotHash;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.reactive.RedisAdvancedClusterReactiveCommands;
import io.lettuce.core.cluster.api.reactive.RedisClusterReactiveCommands;
import io.lettuce.core.cluster.models.partitions.ClusterPartitionParser;
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
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: wiki-framework
 * @Package: com.cdkjframework.redis.realize
 * @ClassName: RedisClusterCommands
 * @Description: java类作用描述
 * @Author: xiaLin
 * @Version: 1.0
 */
public class ClusterReactiveCommands implements RedisAdvancedClusterReactiveCommands<String, String> {
    /**
     * Retrieve a connection to the specified cluster node using the nodeId. Host and port are looked up in the node list. In
     * contrast to the {@link RedisAdvancedClusterReactiveCommands}, node-connections do not route commands to other cluster
     * nodes
     *
     * @param nodeId the node Id.
     * @return a connection to the requested cluster node.
     */
    @Override
    public RedisClusterReactiveCommands<String, String> getConnection(String nodeId) {
        return null;
    }

    /**
     * Retrieve a connection to the specified cluster node using host and port. In contrast to the
     * {@link RedisAdvancedClusterReactiveCommands}, node-connections do not route commands to other cluster nodes. Host and
     * port connections are verified by default for cluster membership, see
     * {@link ClusterClientOptions#isValidateClusterNodeMembership()}.
     *
     * @param host the host.
     * @param port the port.
     * @return a connection to the requested cluster node.
     */
    @Override
    public RedisClusterReactiveCommands<String, String> getConnection(String host, int port) {
        return null;
    }

    /**
     * @return the underlying connection.
     */
    @Override
    @Deprecated
    public StatefulRedisClusterConnection<String, String> getStatefulConnection() {
        return null;
    }

    /**
     * Set the default timeout for operations. A zero timeout value indicates to not time out.
     *
     * @param timeout the timeout value.
     */
    @Override
    @Deprecated
    public void setTimeout(Duration timeout) {

    }

    /**
     * Generate a new config epoch, incrementing the current epoch, assign the new epoch to this node, WITHOUT any consensus and
     * persist the configuration on disk before sending packets with the new configuration.
     *
     * @return String simple-string-reply If the new config epoch is generated and assigned either BUMPED (epoch) or STILL
     * (epoch) are returned.
     */
    @Override
    public Mono<String> clusterBumpepoch() {
        return null;
    }

    /**
     * Meet another cluster node to include the node into the cluster. The command starts the cluster handshake and returns with
     * {@literal OK} when the node was added to the cluster.
     *
     * @param ip   IP address of the host.
     * @param port port number.
     * @return String simple-string-reply.
     */
    @Override
    public Mono<String> clusterMeet(String ip, int port) {
        return null;
    }

    /**
     * Blacklist and remove the cluster node from the cluster.
     *
     * @param nodeId the node Id.
     * @return String simple-string-reply.
     */
    @Override
    public Mono<String> clusterForget(String nodeId) {
        return null;
    }

    /**
     * Adds slots to the cluster node. The current node will become the master for the specified slots.
     *
     * @param slots one or more slots from {@literal 0} to {@literal 16384}.
     * @return String simple-string-reply.
     */
    @Override
    public Mono<String> clusterAddSlots(int... slots) {
        return null;
    }

    /**
     * Removes slots from the cluster node.
     *
     * @param slots one or more slots from {@literal 0} to {@literal 16384}.
     * @return String simple-string-reply.
     */
    @Override
    public Mono<String> clusterDelSlots(int... slots) {
        return null;
    }

    @Override
    public Mono<String> clusterDelSlotsRange(Range<Integer>... ranges) {
        return null;
    }

    /**
     * Assign a slot to a node. The command migrates the specified slot from the current node to the specified node in
     * {@code nodeId}
     *
     * @param slot   the slot.
     * @param nodeId the id of the node that will become the master for the slot.
     * @return String simple-string-reply.
     */
    @Override
    public Mono<String> clusterSetSlotNode(int slot, String nodeId) {
        return null;
    }

    /**
     * Clears migrating / importing state from the slot.
     *
     * @param slot the slot.
     * @return String simple-string-reply.
     */
    @Override
    public Mono<String> clusterSetSlotStable(int slot) {
        return null;
    }

    @Override
    public Mono<List<Object>> clusterShards() {
        return null;
    }

    /**
     * Flag a slot as {@literal MIGRATING} (outgoing) towards the node specified in {@code nodeId}. The slot must be handled by
     * the current node in order to be migrated.
     *
     * @param slot   the slot.
     * @param nodeId the id of the node is targeted to become the master for the slot.
     * @return String simple-string-reply.
     */
    @Override
    public Mono<String> clusterSetSlotMigrating(int slot, String nodeId) {
        return null;
    }

    /**
     * Flag a slot as {@literal IMPORTING} (incoming) from the node specified in {@code nodeId}.
     *
     * @param slot   the slot.
     * @param nodeId the id of the node is the master of the slot.
     * @return String simple-string-reply.
     */
    @Override
    public Mono<String> clusterSetSlotImporting(int slot, String nodeId) {
        return null;
    }

    /**
     * Get information and statistics about the cluster viewed by the current node.
     *
     * @return String bulk-string-reply as a collection of text lines.
     */
    @Override
    public Mono<String> clusterInfo() {
        return null;
    }

    /**
     * Obtain the nodeId for the currently connected node.
     *
     * @return String simple-string-reply.
     */
    @Override
    public Mono<String> clusterMyId() {
        return null;
    }

    /**
     * Obtain details about all cluster nodes. Can be parsed using
     * {@link ClusterPartitionParser#parse}
     *
     * @return String bulk-string-reply as a collection of text lines.
     */
    @Override
    public Mono<String> clusterNodes() {
        return null;
    }

    /**
     * List replicas for a certain node identified by its {@code nodeId}. Can be parsed using
     * {@link ClusterPartitionParser#parse}
     *
     * @param nodeId node id of the master node.
     * @return List&lt;String&gt; array-reply list of replicas. The command returns data in the same format as
     * {@link #clusterNodes()} but one line per replica.
     */
    @Override
    @Deprecated
    public Flux<String> clusterSlaves(String nodeId) {
        return null;
    }

    /**
     * Retrieve the list of keys within the {@code slot}.
     *
     * @param slot  the slot.
     * @param count maximal number of keys.
     * @return List&lt;K&gt; array-reply list of keys.
     */
    @Override
    public Flux<String> clusterGetKeysInSlot(int slot, int count) {
        return null;
    }

    /**
     * Returns the number of keys in the specified Redis Cluster hash {@code slot}.
     *
     * @param slot the slot.
     * @return Integer reply: The number of keys in the specified hash slot, or an error if the hash slot is invalid.
     */
    @Override
    public Mono<Long> clusterCountKeysInSlot(int slot) {
        return null;
    }

    @Override
    public Mono<String> clusterAddSlotsRange(Range<Integer>... ranges) {
        return null;
    }

    /**
     * Returns the number of failure reports for the specified node. Failure reports are the way Redis Cluster uses in order to
     * promote a {@literal PFAIL} state, that means a node is not reachable, to a {@literal FAIL} state, that means that the
     * majority of masters in the cluster agreed within a window of time that the node is not reachable.
     *
     * @param nodeId the node id.
     * @return Integer reply: The number of active failure reports for the node.
     */
    @Override
    public Mono<Long> clusterCountFailureReports(String nodeId) {
        return null;
    }

    /**
     * Returns an integer identifying the hash slot the specified key hashes to. This command is mainly useful for debugging and
     * testing, since it exposes via an API the underlying Redis implementation of the hashing algorithm. Basically the same as
     * {@link SlotHash#getSlot(byte[])}. If not, call Houston and report that we've got a problem.
     *
     * @param key the key.
     * @return Integer reply: The hash slot number.
     */
    @Override
    public Mono<Long> clusterKeyslot(String key) {
        return null;
    }

    /**
     * Forces a node to save the nodes.conf configuration on disk.
     *
     * @return String simple-string-reply: {@code OK} or an error if the operation fails.
     */
    @Override
    public Mono<String> clusterSaveconfig() {
        return null;
    }

    /**
     * This command sets a specific config epoch in a fresh node. It only works when:
     * <ul>
     * <li>The nodes table of the node is empty.</li>
     * <li>The node current config epoch is zero.</li>
     * </ul>
     *
     * @param configEpoch the config epoch.
     * @return String simple-string-reply: {@code OK} or an error if the operation fails.
     */
    @Override
    public Mono<String> clusterSetConfigEpoch(long configEpoch) {
        return null;
    }

    /**
     * Get array of cluster slots to node mappings.
     *
     * @return List&lt;Object&gt; array-reply nested list of slot ranges with IP/Port mappings.
     */
    @Override
    public Flux<Object> clusterSlots() {
        return null;
    }

    /**
     * The asking command is required after a {@code -ASK} redirection. The client should issue {@code ASKING} before to
     * actually send the command to the target instance. See the Redis Cluster specification for more information.
     *
     * @return String simple-string-reply.
     */
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

    /**
     * Turn this node into a replica of the node with the id {@code nodeId}.
     *
     * @param nodeId master node id.
     * @return String simple-string-reply.
     */
    @Override
    public Mono<String> clusterReplicate(String nodeId) {
        return null;
    }

    @Override
    public Flux<String> clusterReplicas(String s) {
        return null;
    }

    /**
     * Failover a cluster node. Turns the currently connected node into a master and the master into its replica.
     *
     * @param force do not coordinate with master if {@code true}.
     * @return String simple-string-reply.
     */
    @Override
    public Mono<String> clusterFailover(boolean force) {
        return null;
    }

    @Override
    public Mono<String> clusterFailover(boolean b, boolean b1) {
        return null;
    }

    /**
     * Reset a node performing a soft or hard reset:
     * <ul>
     * <li>All other nodes are forgotten</li>
     * <li>All the assigned / open slots are released</li>
     * <li>If the node is a replica, it turns into a master</li>
     * <li>Only for hard reset: a new Node ID is generated</li>
     * <li>Only for hard reset: currentEpoch and configEpoch are set to 0</li>
     * <li>The new configuration is saved and the cluster state updated</li>
     * <li>If the node was a replica, the whole data set is flushed away</li>
     * </ul>
     *
     * @param hard {@code true} for hard reset. Generates a new nodeId and currentEpoch/configEpoch are set to 0.
     * @return String simple-string-reply.
     */
    @Override
    public Mono<String> clusterReset(boolean hard) {
        return null;
    }

    /**
     * Delete all the slots associated with the specified node. The number of deleted slots is returned.
     *
     * @return String simple-string-reply.
     */
    @Override
    public Mono<String> clusterFlushslots() {
        return null;
    }

    /**
     * Post a message to a channel.
     *
     * @param channel the channel type: key.
     * @param message the message type: value.
     * @return Long integer-reply the number of clients that received the message.
     */
    @Override
    public Mono<Long> publish(String channel, String message) {
        return null;
    }

    /**
     * Lists the currently *active channels*.
     *
     * @return K array-reply a list of active channels, optionally matching the specified pattern.
     */
    @Override
    public Flux<String> pubsubChannels() {
        return null;
    }

    /**
     * Lists the currently *active channels*.
     *
     * @param channel the key.
     * @return K array-reply a list of active channels, optionally matching the specified pattern.
     */
    @Override
    public Flux<String> pubsubChannels(String channel) {
        return null;
    }

    /**
     * Returns the number of subscribers (not counting clients subscribed to patterns) for the specified channels.
     *
     * @param channels channel keys.
     * @return array-reply a list of channels and number of subscribers for every channel.
     */
    @Override
    public Mono<Map<String, Long>> pubsubNumsub(String... channels) {
        return null;
    }

    /**
     * Returns the number of subscriptions to patterns.
     *
     * @return Long integer-reply the number of patterns all the clients are subscribed to.
     */
    @Override
    public Mono<Long> pubsubNumpat() {
        return null;
    }

    /**
     * Echo the given string.
     *
     * @param msg the message type: value.
     * @return V bulk-string-reply.
     */
    @Override
    public Mono<String> echo(String msg) {
        return null;
    }

    /**
     * Return the role of the instance in the context of replication.
     *
     * @return Object array-reply where the first element is one of master, slave, sentinel and the additional elements are
     * role-specific.
     */
    @Override
    public Flux<Object> role() {
        return null;
    }

    /**
     * Ping the server.
     *
     * @return String simple-string-reply.
     */
    @Override
    public Mono<String> ping() {
        return null;
    }

    /**
     * Tells a Redis cluster replica node that the client is ok reading possibly stale data and is not interested in running
     * write queries.
     *
     * @return String simple-string-reply.
     */
    @Override
    public Mono<String> readOnly() {
        return null;
    }

    /**
     * Resets readOnly flag.
     *
     * @return String simple-string-reply.
     */
    @Override
    public Mono<String> readWrite() {
        return null;
    }

    /**
     * Instructs Redis to disconnect the connection. Note that if auto-reconnect is enabled then Lettuce will auto-reconnect if
     * the connection was disconnected. Use {@link StatefulConnection#close} to close connections and
     * release resources.
     *
     * @return String simple-string-reply always OK.
     */
    @Override
    public Mono<String> quit() {
        return null;
    }

    /**
     * Wait for replication.
     *
     * @param replicas minimum number of replicas.
     * @param timeout  timeout in milliseconds.
     * @return number of replicas.
     */
    @Override
    public Mono<Long> waitForReplication(int replicas, long timeout) {
        return null;
    }

    /**
     * Dispatch a command to the Redis Server. Please note the command output type must fit to the command response.
     *
     * @param type   the command, must not be {@code null}.
     * @param output the command output, must not be {@code null}.
     * @return the command response.
     */
    @Override
    public <T> Flux<T> dispatch(ProtocolKeyword type, CommandOutput<String, String, ?> output) {
        return null;
    }

    /**
     * Dispatch a command to the Redis Server. Please note the command output type must fit to the command response.
     *
     * @param type   the command, must not be {@code null}.
     * @param output the command output, must not be {@code null}.
     * @param args   the command arguments, must not be {@code null}.
     * @return the command response.
     */
    @Override
    public <T> Flux<T> dispatch(ProtocolKeyword type, CommandOutput<String, String, ?> output, CommandArgs<String, String> args) {
        return null;
    }

    /**
     * @return {@code true} if the connection is open (connected and not closed).
     */
    @Override
    @Deprecated
    public boolean isOpen() {
        return false;
    }

    /**
     * Reset the command state. Queued commands will be canceled and the internal state will be reset. This is useful when the
     * internal state machine gets out of sync with the connection.
     */
    @Override
    @Deprecated
    public void reset() {

    }

    /**
     * Disable or enable auto-flush behavior. Default is {@code true}. If autoFlushCommands is disabled, multiple commands can
     * be issued without writing them actually to the transport. Commands are buffered until a {@link #flushCommands()} is
     * issued. After calling {@link #flushCommands()} commands are sent to the transport and executed by Redis.
     *
     * @param autoFlush state of autoFlush.
     */
    @Override
    @Deprecated
    public void setAutoFlushCommands(boolean autoFlush) {

    }

    /**
     * Flush pending commands. This commands forces a flush on the channel and can be used to buffer ("pipeline") commands to
     * achieve batching. No-op if channel is not connected.
     */
    @Override
    @Deprecated
    public void flushCommands() {

    }

    @Override
    public Mono<Boolean> copy(String s, String k1) {
        return null;
    }

    @Override
    public Mono<Boolean> copy(String s, String k1, CopyArgs copyArgs) {
        return null;
    }

    /**
     * Delete one or more keys with pipelining. Cross-slot keys will result in multiple calls to the particular cluster nodes.
     *
     * @param keys the keys.
     * @return Long integer-reply The number of keys that were removed.
     */
    @Override
    public Mono<Long> del(String... keys) {
        return null;
    }

    /**
     * Unlink one or more keys with pipelining. Cross-slot keys will result in multiple calls to the particular cluster nodes.
     *
     * @param keys the keys.
     * @return Long integer-reply The number of keys that were removed.
     */
    @Override
    public Mono<Long> unlink(String... keys) {
        return null;
    }

    /**
     * Return a serialized version of the value stored at the specified key.
     *
     * @param key the key.
     * @return byte[] bulk-string-reply the serialized value.
     */
    @Override
    public Mono<byte[]> dump(String key) {
        return null;
    }

    /**
     * Determine how many keys exist with pipelining. Cross-slot keys will result in multiple calls to the particular cluster
     * nodes.
     *
     * @param keys the keys.
     * @return Long integer-reply specifically: Number of existing keys.
     */
    @Override
    public Mono<Long> exists(String... keys) {
        return null;
    }

    /**
     * Set a key's time to live in seconds.
     *
     * @param key     the key.
     * @param seconds the seconds type: long.
     * @return Boolean integer-reply specifically:
     * <p>
     * {@code true} if the timeout was set. {@code false} if {@code key} does not exist or the timeout could not be set.
     */
    @Override
    public Mono<Boolean> expire(String key, long seconds) {
        return null;
    }

    @Override
    public Mono<Boolean> expire(String s, long l, ExpireArgs expireArgs) {
        return null;
    }

    @Override
    public Mono<Boolean> expire(String s, Duration duration) {
        return null;
    }

    @Override
    public Mono<Boolean> expire(String s, Duration duration, ExpireArgs expireArgs) {
        return null;
    }

    /**
     * Set the expiration for a key as a UNIX timestamp.
     *
     * @param key       the key.
     * @param timestamp the timestamp type: posix time.
     * @return Boolean integer-reply specifically:
     * <p>
     * {@code true} if the timeout was set. {@code false} if {@code key} does not exist or the timeout could not be set
     * (see: {@code EXPIRE}).
     */
    @Override
    public Mono<Boolean> expireat(String key, Date timestamp) {
        return null;
    }

    @Override
    public Mono<Boolean> expireat(String s, Date date, ExpireArgs expireArgs) {
        return null;
    }

    @Override
    public Mono<Boolean> expireat(String s, Instant instant) {
        return null;
    }

    @Override
    public Mono<Boolean> expireat(String s, Instant instant, ExpireArgs expireArgs) {
        return null;
    }

    @Override
    public Mono<Long> expiretime(String s) {
        return null;
    }

    /**
     * Set the expiration for a key as a UNIX timestamp.
     *
     * @param key       the key.
     * @param timestamp the timestamp type: posix time.
     * @return Boolean integer-reply specifically:
     * <p>
     * {@code true} if the timeout was set. {@code false} if {@code key} does not exist or the timeout could not be set
     * (see: {@code EXPIRE}).
     */
    @Override
    public Mono<Boolean> expireat(String key, long timestamp) {
        return null;
    }

    @Override
    public Mono<Boolean> expireat(String s, long l, ExpireArgs expireArgs) {
        return null;
    }

    /**
     * Append a value to a key.
     *
     * @param key   the key.
     * @param value the value.
     * @return Long integer-reply the length of the string after the append operation.
     */
    @Override
    public Mono<Long> append(String key, String value) {
        return null;
    }

    /**
     * Count set bits in a string.
     *
     * @param key the key.
     * @return Long integer-reply The number of bits set to 1.
     */
    @Override
    public Mono<Long> bitcount(String key) {
        return null;
    }

    /**
     * Count set bits in a string.
     *
     * @param key   the key.
     * @param start the start.
     * @param end   the end.
     * @return Long integer-reply The number of bits set to 1.
     */
    @Override
    public Mono<Long> bitcount(String key, long start, long end) {
        return null;
    }

    /**
     * Execute {@code BITFIELD} with its subcommands.
     *
     * @param key          the key.
     * @param bitFieldArgs the args containing subcommands, must not be {@code null}.
     * @return Long bulk-reply the results from the bitfield commands.
     */
    @Override
    public Flux<Value<Long>> bitfield(String key, BitFieldArgs bitFieldArgs) {
        return null;
    }

    /**
     * Find first bit set or clear in a string.
     *
     * @param key   the key.
     * @param state the state.
     * @return Long integer-reply The command returns the position of the first bit set to 1 or 0 according to the request.
     * <p>
     * If we look for set bits (the bit argument is 1) and the string is empty or composed of just zero bytes, -1 is
     * returned.
     * <p>
     * If we look for clear bits (the bit argument is 0) and the string only contains bit set to 1, the function returns
     * the first bit not part of the string on the right. So if the string is tree bytes set to the value 0xff the
     * command {@code BITPOS key 0} will return 24, since up to bit 23 all the bits are 1.
     * <p>
     * Basically the function consider the right of the string as padded with zeros if you look for clear bits and
     * specify no range or the <em>start</em> argument <strong>only</strong>.
     */
    @Override
    public Mono<Long> bitpos(String key, boolean state) {
        return null;
    }

    /**
     * Find first bit set or clear in a string.
     *
     * @param key   the key.
     * @param state the bit type: long.
     * @param start the start type: long.
     * @return Long integer-reply The command returns the position of the first bit set to 1 or 0 according to the request.
     * <p>
     * If we look for set bits (the bit argument is 1) and the string is empty or composed of just zero bytes, -1 is
     * returned.
     * <p>
     * If we look for clear bits (the bit argument is 0) and the string only contains bit set to 1, the function returns
     * the first bit not part of the string on the right. So if the string is tree bytes set to the value 0xff the
     * command {@code BITPOS key 0} will return 24, since up to bit 23 all the bits are 1.
     * <p>
     * Basically the function consider the right of the string as padded with zeros if you look for clear bits and
     * specify no range or the <em>start</em> argument <strong>only</strong>.
     */
    @Override
    public Mono<Long> bitpos(String key, boolean state, long start) {
        return null;
    }

    /**
     * Find first bit set or clear in a string.
     *
     * @param key   the key.
     * @param state the bit type: long.
     * @param start the start type: long.
     * @param end   the end type: long.
     * @return Long integer-reply The command returns the position of the first bit set to 1 or 0 according to the request.
     * <p>
     * If we look for set bits (the bit argument is 1) and the string is empty or composed of just zero bytes, -1 is
     * returned.
     * <p>
     * If we look for clear bits (the bit argument is 0) and the string only contains bit set to 1, the function returns
     * the first bit not part of the string on the right. So if the string is tree bytes set to the value 0xff the
     * command {@code BITPOS key 0} will return 24, since up to bit 23 all the bits are 1.
     * <p>
     * Basically the function consider the right of the string as padded with zeros if you look for clear bits and
     * specify no range or the <em>start</em> argument <strong>only</strong>.
     * <p>
     * However this behavior changes if you are looking for clear bits and specify a range with both
     * <strong>start</strong> and <strong>end</strong>. If no clear bit is found in the specified range, the function
     * returns -1 as the user specified a clear range and there are no 0 bits in that range.
     */
    @Override
    public Mono<Long> bitpos(String key, boolean state, long start, long end) {
        return null;
    }

    /**
     * Perform bitwise AND between strings.
     *
     * @param destination result key of the operation.
     * @param keys        operation input key names.
     * @return Long integer-reply The size of the string stored in the destination key, that is equal to the size of the longest
     * input string.
     */
    @Override
    public Mono<Long> bitopAnd(String destination, String... keys) {
        return null;
    }

    /**
     * Perform bitwise NOT between strings.
     *
     * @param destination result key of the operation.
     * @param source      operation input key names.
     * @return Long integer-reply The size of the string stored in the destination key, that is equal to the size of the longest
     * input string.
     */
    @Override
    public Mono<Long> bitopNot(String destination, String source) {
        return null;
    }

    /**
     * Perform bitwise OR between strings.
     *
     * @param destination result key of the operation.
     * @param keys        operation input key names.
     * @return Long integer-reply The size of the string stored in the destination key, that is equal to the size of the longest
     * input string.
     */
    @Override
    public Mono<Long> bitopOr(String destination, String... keys) {
        return null;
    }

    /**
     * Perform bitwise XOR between strings.
     *
     * @param destination result key of the operation.
     * @param keys        operation input key names.
     * @return Long integer-reply The size of the string stored in the destination key, that is equal to the size of the longest
     * input string.
     */
    @Override
    public Mono<Long> bitopXor(String destination, String... keys) {
        return null;
    }

    /**
     * Decrement the integer value of a key by one.
     *
     * @param key the key.
     * @return Long integer-reply the value of {@code key} after the decrement.
     */
    @Override
    public Mono<Long> decr(String key) {
        return null;
    }

    /**
     * Decrement the integer value of a key by the given number.
     *
     * @param key    the key.
     * @param amount the decrement type: long.
     * @return Long integer-reply the value of {@code key} after the decrement.
     */
    @Override
    public Mono<Long> decrby(String key, long amount) {
        return null;
    }

    /**
     * Get the value of a key.
     *
     * @param key the key.
     * @return V bulk-string-reply the value of {@code key}, or {@code null} when {@code key} does not exist.
     */
    @Override
    public Mono<String> get(String key) {
        return null;
    }

    /**
     * Returns the bit value at offset in the string value stored at key.
     *
     * @param key    the key.
     * @param offset the offset type: long.
     * @return Long integer-reply the bit value stored at <em>offset</em>.
     */
    @Override
    public Mono<Long> getbit(String key, long offset) {
        return null;
    }

    @Override
    public Mono<String> getdel(String s) {
        return null;
    }

    @Override
    public Mono<String> getex(String s, GetExArgs getExArgs) {
        return null;
    }

    /**
     * Get a substring of the string stored at a key.
     *
     * @param key   the key.
     * @param start the start type: long.
     * @param end   the end type: long.
     * @return V bulk-string-reply.
     */
    @Override
    public Mono<String> getrange(String key, long start, long end) {
        return null;
    }

    /**
     * Set the string value of a key and return its old value.
     *
     * @param key   the key.
     * @param value the value.
     * @return V bulk-string-reply the old value stored at {@code key}, or {@code null} when {@code key} did not exist.
     */
    @Override
    public Mono<String> getset(String key, String value) {
        return null;
    }

    /**
     * Increment the integer value of a key by one.
     *
     * @param key the key.
     * @return Long integer-reply the value of {@code key} after the increment.
     */
    @Override
    public Mono<Long> incr(String key) {
        return null;
    }

    /**
     * Increment the integer value of a key by the given amount.
     *
     * @param key    the key.
     * @param amount the increment type: long.
     * @return Long integer-reply the value of {@code key} after the increment.
     */
    @Override
    public Mono<Long> incrby(String key, long amount) {
        return null;
    }

    /**
     * Increment the float value of a key by the given amount.
     *
     * @param key    the key.
     * @param amount the increment type: double.
     * @return Double bulk-string-reply the value of {@code key} after the increment.
     */
    @Override
    public Mono<Double> incrbyfloat(String key, double amount) {
        return null;
    }

    /**
     * Get the values of all the given keys with pipelining. Cross-slot keys will result in multiple calls to the particular
     * cluster nodes.
     *
     * @param keys the key.
     * @return V array-reply list of values at the specified keys.
     */
    @Override
    public Flux<KeyValue<String, String>> mget(String... keys) {
        return null;
    }

    /**
     * Stream over the values of all the given keys.
     *
     * @param channel the channel.
     * @param keys    the keys.
     * @return Long array-reply list of values at the specified keys.
     */
    @Override
    @Deprecated
    public Mono<Long> mget(KeyValueStreamingChannel<String, String> channel, String... keys) {
        return null;
    }

    /**
     * Set multiple keys to multiple values with pipelining. Cross-slot keys will result in multiple calls to the particular
     * cluster nodes.
     *
     * @param map the map.
     * @return String simple-string-reply always {@code OK} since {@code MSET} can't fail.
     */
    @Override
    public Mono<String> mset(Map<String, String> map) {
        return null;
    }

    /**
     * Set multiple keys to multiple values, only if none of the keys exist with pipelining. Cross-slot keys will result in
     * multiple calls to the particular cluster nodes.
     *
     * @param map the null.
     * @return Boolean integer-reply specifically:
     * <p>
     * {@code 1} if the all the keys were set. {@code 0} if no key was set (at least one key already existed).
     */
    @Override
    public Mono<Boolean> msetnx(Map<String, String> map) {
        return null;
    }

    /**
     * Set the string value of a key.
     *
     * @param key   the key.
     * @param value the value.
     * @return String simple-string-reply {@code OK} if {@code SET} was executed correctly.
     */
    @Override
    public Mono<String> set(String key, String value) {
        return null;
    }

    /**
     * Set the string value of a key.
     *
     * @param key     the key.
     * @param value   the value.
     * @param setArgs the setArgs.
     * @return String simple-string-reply {@code OK} if {@code SET} was executed correctly.
     */
    @Override
    public Mono<String> set(String key, String value, SetArgs setArgs) {
        return null;
    }

    @Override
    public Mono<String> setGet(String s, String s2) {
        return null;
    }

    @Override
    public Mono<String> setGet(String s, String s2, SetArgs setArgs) {
        return null;
    }

    /**
     * Sets or clears the bit at offset in the string value stored at key.
     *
     * @param key    the key.
     * @param offset the offset type: long.
     * @param value  the value type: string.
     * @return Long integer-reply the original bit value stored at <em>offset</em>.
     */
    @Override
    public Mono<Long> setbit(String key, long offset, int value) {
        return null;
    }

    /**
     * Set the value and expiration of a key.
     *
     * @param key     the key.
     * @param seconds the seconds type: long.
     * @param value   the value.
     * @return String simple-string-reply.
     */
    @Override
    public Mono<String> setex(String key, long seconds, String value) {
        return null;
    }

    /**
     * Set the value and expiration in milliseconds of a key.
     *
     * @param key          the key.
     * @param milliseconds the milliseconds type: long.
     * @param value        the value.
     * @return String simple-string-reply.
     */
    @Override
    public Mono<String> psetex(String key, long milliseconds, String value) {
        return null;
    }

    /**
     * Set the value of a key, only if the key does not exist.
     *
     * @param key   the key.
     * @param value the value.
     * @return Boolean integer-reply specifically:
     * <p>
     * {@code 1} if the key was set {@code 0} if the key was not set.
     */
    @Override
    public Mono<Boolean> setnx(String key, String value) {
        return null;
    }

    /**
     * Overwrite part of a string at key starting at the specified offset.
     *
     * @param key    the key.
     * @param offset the offset type: long.
     * @param value  the value.
     * @return Long integer-reply the length of the string after it was modified by the command.
     */
    @Override
    public Mono<Long> setrange(String key, long offset, String value) {
        return null;
    }

    /**
     * The STRALGO command implements complex algorithms that operate on strings. This method uses the LCS algorithm (longest
     * common substring).
     *
     * <ul>
     * <li>Without modifiers the string representing the longest common substring is returned.</li>
     * <li>When {@link StrAlgoArgs#justLen() LEN} is given the command returns the length of the longest common substring.</li>
     * <li>When {@link StrAlgoArgs#withIdx() IDX} is given the command returns an array with the LCS length and all the ranges
     * in both the strings, start and end offset for each string, where there are matches. When
     * {@link StrAlgoArgs#withMatchLen() WITHMATCHLEN} is given each array representing a match will also have the length of the
     * match.</li>
     * </ul>
     *
     * @param strAlgoArgs command arguments.
     * @return StringMatchResult.
     */
    @Override
    public Mono<StringMatchResult> stralgoLcs(StrAlgoArgs strAlgoArgs) {
        return null;
    }

    /**
     * Get the length of the value stored in a key.
     *
     * @param key the key.
     * @return Long integer-reply the length of the string at {@code key}, or {@code 0} when {@code key} does not exist.
     */
    @Override
    public Mono<Long> strlen(String key) {
        return null;
    }

    /**
     * Asynchronously rewrite the append-only file.
     *
     * @return String simple-string-reply always {@code OK}.
     */
    @Override
    public Mono<String> bgrewriteaof() {
        return null;
    }

    /**
     * Asynchronously save the dataset to disk.
     *
     * @return String simple-string-reply.
     */
    @Override
    public Mono<String> bgsave() {
        return null;
    }

    @Override
    public Mono<String> clientCaching(boolean b) {
        return null;
    }

    /**
     * Get the current connection name.
     *
     * @return K bulk-string-reply The connection name, or a null bulk reply if no name is set.
     */
    @Override
    public Mono<String> clientGetname() {
        return null;
    }

    @Override
    public Mono<Long> clientGetredir() {
        return null;
    }

    /**
     * Set the current connection name on all cluster nodes with pipelining.
     *
     * @param name the client name.
     * @return simple-string-reply {@code OK} if the connection name was successfully set.
     */
    @Override
    public Mono<String> clientSetname(String name) {
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

    /**
     * Kill the connection of a client identified by ip:port.
     *
     * @param addr ip:port.
     * @return String simple-string-reply {@code OK} if the connection exists and has been closed.
     */
    @Override
    public Mono<String> clientKill(String addr) {
        return null;
    }

    /**
     * Kill connections of clients which are filtered by {@code killArgs}.
     *
     * @param killArgs args for the kill operation.
     * @return Long integer-reply number of killed connections.
     */
    @Override
    public Mono<Long> clientKill(KillArgs killArgs) {
        return null;
    }

    /**
     * Unblock the specified blocked client.
     *
     * @param id   the client id.
     * @param type unblock type.
     * @return Long integer-reply number of unblocked connections.
     * @since 5.1
     */
    @Override
    public Mono<Long> clientUnblock(long id, UnblockType type) {
        return null;
    }

    /**
     * Stop processing commands from clients for some time.
     *
     * @param timeout the timeout value in milliseconds.
     * @return String simple-string-reply The command returns OK or an error if the timeout is invalid.
     */
    @Override
    public Mono<String> clientPause(long timeout) {
        return null;
    }

    /**
     * Get the list of client connections.
     *
     * @return String bulk-string-reply a unique string, formatted as follows: One client connection per line (separated by LF),
     * each line is composed of a succession of property=value fields separated by a space character.
     */
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

    /**
     * Get the id of the current connection.
     *
     * @return Long The command just returns the ID of the current connection.
     * @since 5.3
     */
    @Override
    public Mono<Long> clientId() {
        return null;
    }

    /**
     * Returns an array reply of details about all Redis commands.
     *
     * @return Object array-reply.
     */
    @Override
    public Flux<Object> command() {
        return null;
    }

    /**
     * Returns an array reply of details about the requested commands.
     *
     * @param commands the commands to query for.
     * @return Object array-reply.
     */
    @Override
    public Flux<Object> commandInfo(String... commands) {
        return null;
    }

    /**
     * Returns an array reply of details about the requested commands.
     *
     * @param commands the commands to query for.
     * @return Object array-reply.
     */
    @Override
    public Flux<Object> commandInfo(CommandType... commands) {
        return null;
    }

    /**
     * Get total number of Redis commands.
     *
     * @return Long integer-reply of number of total commands in this Redis server.
     */
    @Override
    public Mono<Long> commandCount() {
        return null;
    }

    /**
     * Get the value of a configuration parameter.
     *
     * @param parameter name of the parameter.
     * @return Map&lt;String, String&gt; bulk-string-reply.
     */
    @Override
    public Mono<Map<String, String>> configGet(String parameter) {
        return null;
    }

    @Override
    public Mono<Map<String, String>> configGet(String... strings) {
        return null;
    }

    /**
     * Reset the stats returned by INFO.
     *
     * @return String simple-string-reply always {@code OK}.
     */
    @Override
    public Mono<String> configResetstat() {
        return null;
    }

    /**
     * Rewrite the configuration file with the in memory configuration.
     *
     * @return String simple-string-reply {@code OK} when the configuration was rewritten properly. Otherwise an error is
     * returned.
     */
    @Override
    public Mono<String> configRewrite() {
        return null;
    }

    /**
     * Set a configuration parameter to the given value.
     *
     * @param parameter the parameter name.
     * @param value     the parameter value.
     * @return String simple-string-reply: {@code OK} when the configuration was set properly. Otherwise an error is returned.
     */
    @Override
    public Mono<String> configSet(String parameter, String value) {
        return null;
    }

    @Override
    public Mono<String> configSet(Map<String, String> map) {
        return null;
    }

    /**
     * Remove all keys from all databases on all cluster masters with pipelining.
     *
     * @return String simple-string-reply.
     */
    @Override
    public Mono<String> flushall() {
        return null;
    }

    @Override
    public Mono<String> flushall(FlushMode flushMode) {
        return null;
    }

    /**
     * Remove all keys asynchronously from all databases on all cluster upstream nodes with pipelining.
     *
     * @return String simple-string-reply
     * @since 5.3.3
     */
    @Override
    public Mono<String> flushallAsync() {
        return null;
    }

    /**
     * Remove all keys from the current database on all cluster masters with pipelining.
     *
     * @return String simple-string-reply.
     */
    @Override
    public Mono<String> flushdb() {
        return null;
    }

    @Override
    public Mono<String> flushdb(FlushMode flushMode) {
        return null;
    }

    /**
     * Remove all keys asynchronously from the current database.
     *
     * @return String simple-string-reply.
     */
    @Override
    @Deprecated
    public Mono<String> flushdbAsync() {
        return null;
    }

    /**
     * Get information and statistics about the server.
     *
     * @return String bulk-string-reply as a collection of text lines.
     */
    @Override
    public Mono<String> info() {
        return null;
    }

    /**
     * Get information and statistics about the server.
     *
     * @param section the section type: string.
     * @return String bulk-string-reply as a collection of text lines.
     */
    @Override
    public Mono<String> info(String section) {
        return null;
    }

    /**
     * Get the UNIX time stamp of the last successful save to disk.
     *
     * @return Date integer-reply an UNIX time stamp.
     */
    @Override
    public Mono<Date> lastsave() {
        return null;
    }

    /**
     * Reports the number of bytes that a key and its value require to be stored in RAM.
     *
     * @param key the key.
     * @return memory usage in bytes.
     * @since 5.2
     */
    @Override
    public Mono<Long> memoryUsage(String key) {
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

    /**
     * Synchronously save the dataset to disk.
     *
     * @return String simple-string-reply The commands returns OK on success.
     */
    @Override
    public Mono<String> save() {
        return null;
    }

    /**
     * Return the number of keys in the selected database on all cluster masters.
     *
     * @return Long integer-reply.
     */
    @Override
    public Mono<Long> dbsize() {
        return null;
    }

    /**
     * Crash and recover.
     *
     * @param delay optional delay in milliseconds.
     * @return String simple-string-reply.
     */
    @Override
    public Mono<String> debugCrashAndRecover(Long delay) {
        return null;
    }

    /**
     * Get debugging information about the internal hash-table state.
     *
     * @param db the database number.
     * @return String simple-string-reply.
     */
    @Override
    public Mono<String> debugHtstats(int db) {
        return null;
    }

    /**
     * Get debugging information about a key.
     *
     * @param key the key.
     * @return String simple-string-reply.
     */
    @Override
    public Mono<String> debugObject(String key) {
        return null;
    }

    /**
     * Make the server crash: Out of memory.
     *
     * @return nothing, because the server crashes before returning.
     */
    @Override
    public Mono<Void> debugOom() {
        return null;
    }

    /**
     * Make the server crash: Invalid pointer access.
     *
     * @return nothing, because the server crashes before returning.
     */
    @Override
    public Mono<Void> debugSegfault() {
        return null;
    }

    /**
     * Save RDB, clear the database and reload RDB.
     *
     * @return String simple-string-reply The commands returns OK on success.
     */
    @Override
    public Mono<String> debugReload() {
        return null;
    }

    /**
     * Restart the server gracefully.
     *
     * @param delay optional delay in milliseconds.
     * @return String simple-string-reply.
     */
    @Override
    public Mono<String> debugRestart(Long delay) {
        return null;
    }

    /**
     * Get debugging information about the internal SDS length.
     *
     * @param key the key.
     * @return String simple-string-reply.
     */
    @Override
    public Mono<String> debugSdslen(String key) {
        return null;
    }

    /**
     * Find all keys matching the given pattern on all cluster masters.
     *
     * @param pattern the pattern type: patternkey (pattern).
     * @return List&lt;K&gt; array-reply list of keys matching {@code pattern}.
     */
    @Override
    public Flux<String> keys(String pattern) {
        return null;
    }

    /**
     * Find all keys matching the given pattern on all cluster masters.
     *
     * @param channel the channel.
     * @param pattern the pattern.
     * @return Long array-reply list of keys matching {@code pattern}.
     */
    @Override
    @Deprecated
    public Mono<Long> keys(KeyStreamingChannel<String> channel, String pattern) {
        return null;
    }

    /**
     * Atomically transfer a key from a Redis instance to another one.
     *
     * @param host    the host.
     * @param port    the port.
     * @param key     the key.
     * @param db      the database.
     * @param timeout the timeout in milliseconds.
     * @return String simple-string-reply The command returns OK on success.
     */
    @Override
    public Mono<String> migrate(String host, int port, String key, int db, long timeout) {
        return null;
    }

    /**
     * Atomically transfer one or more keys from a Redis instance to another one.
     *
     * @param host        the host.
     * @param port        the port.
     * @param db          the database.
     * @param timeout     the timeout in milliseconds.
     * @param migrateArgs migrate args that allow to configure further options.
     * @return String simple-string-reply The command returns OK on success.
     */
    @Override
    public Mono<String> migrate(String host, int port, int db, long timeout, MigrateArgs<String> migrateArgs) {
        return null;
    }

    /**
     * Move a key to another database.
     *
     * @param key the key.
     * @param db  the db type: long.
     * @return Boolean integer-reply specifically:.
     */
    @Override
    public Mono<Boolean> move(String key, int db) {
        return null;
    }

    /**
     * returns the kind of internal representation used in order to store the value associated with a key.
     *
     * @param key the key.
     * @return String.
     */
    @Override
    public Mono<String> objectEncoding(String key) {
        return null;
    }

    @Override
    public Mono<Long> objectFreq(String s) {
        return null;
    }

    /**
     * returns the number of seconds since the object stored at the specified key is idle (not requested by read or write
     * operations).
     *
     * @param key the key.
     * @return number of seconds since the object stored at the specified key is idle.
     */
    @Override
    public Mono<Long> objectIdletime(String key) {
        return null;
    }

    /**
     * returns the number of references of the value associated with the specified key.
     *
     * @param key the key.
     * @return Long.
     */
    @Override
    public Mono<Long> objectRefcount(String key) {
        return null;
    }

    /**
     * Remove the expiration from a key.
     *
     * @param key the key.
     * @return Boolean integer-reply specifically:
     * <p>
     * {@code true} if the timeout was removed. {@code false} if {@code key} does not exist or does not have an
     * associated timeout.
     */
    @Override
    public Mono<Boolean> persist(String key) {
        return null;
    }

    /**
     * Set a key's time to live in milliseconds.
     *
     * @param key          the key.
     * @param milliseconds the milliseconds type: long.
     * @return integer-reply, specifically:
     * <p>
     * {@code true} if the timeout was set. {@code false} if {@code key} does not exist or the timeout could not be set.
     */
    @Override
    public Mono<Boolean> pexpire(String key, long milliseconds) {
        return null;
    }

    @Override
    public Mono<Boolean> pexpire(String s, long l, ExpireArgs expireArgs) {
        return null;
    }

    @Override
    public Mono<Boolean> pexpire(String s, Duration duration) {
        return null;
    }

    @Override
    public Mono<Boolean> pexpire(String s, Duration duration, ExpireArgs expireArgs) {
        return null;
    }

    /**
     * Set the expiration for a key as a UNIX timestamp specified in milliseconds.
     *
     * @param key       the key.
     * @param timestamp the milliseconds-timestamp type: posix time.
     * @return Boolean integer-reply specifically:
     * <p>
     * {@code true} if the timeout was set. {@code false} if {@code key} does not exist or the timeout could not be set
     * (see: {@code EXPIRE}).
     */
    @Override
    public Mono<Boolean> pexpireat(String key, Date timestamp) {
        return null;
    }

    @Override
    public Mono<Boolean> pexpireat(String s, Date date, ExpireArgs expireArgs) {
        return null;
    }

    @Override
    public Mono<Boolean> pexpireat(String s, Instant instant) {
        return null;
    }

    @Override
    public Mono<Boolean> pexpireat(String s, Instant instant, ExpireArgs expireArgs) {
        return null;
    }

    @Override
    public Mono<Long> pexpiretime(String s) {
        return null;
    }

    /**
     * Set the expiration for a key as a UNIX timestamp specified in milliseconds.
     *
     * @param key       the key.
     * @param timestamp the milliseconds-timestamp type: posix time.
     * @return Boolean integer-reply specifically:
     * <p>
     * {@code true} if the timeout was set. {@code false} if {@code key} does not exist or the timeout could not be set
     * (see: {@code EXPIRE}).
     */
    @Override
    public Mono<Boolean> pexpireat(String key, long timestamp) {
        return null;
    }

    @Override
    public Mono<Boolean> pexpireat(String s, long l, ExpireArgs expireArgs) {
        return null;
    }

    /**
     * Get the time to live for a key in milliseconds.
     *
     * @param key the key.
     * @return Long integer-reply TTL in milliseconds, or a negative value in order to signal an error (see the description
     * above).
     */
    @Override
    public Mono<Long> pttl(String key) {
        return null;
    }

    /**
     * Return a random key from the keyspace on a random master.
     *
     * @return K bulk-string-reply the random key, or a {@link Mono} that completes empty when the database is empty.
     */
    @Override
    public Mono<String> randomkey() {
        return null;
    }

    /**
     * Rename a key.
     *
     * @param key    the key.
     * @param newKey the newkey type: key.
     * @return String simple-string-reply.
     */
    @Override
    public Mono<String> rename(String key, String newKey) {
        return null;
    }

    /**
     * Rename a key, only if the new key does not exist.
     *
     * @param key    the key.
     * @param newKey the newkey type: key.
     * @return Boolean integer-reply specifically:
     * <p>
     * {@code true} if {@code key} was renamed to {@code newkey}. {@code false} if {@code newkey} already exists.
     */
    @Override
    public Mono<Boolean> renamenx(String key, String newKey) {
        return null;
    }

    /**
     * Create a key using the provided serialized value, previously obtained using DUMP.
     *
     * @param key   the key.
     * @param ttl   the ttl type: long.
     * @param value the serialized-value type: string.
     * @return String simple-string-reply The command returns OK on success.
     */
    @Override
    public Mono<String> restore(String key, long ttl, byte[] value) {
        return null;
    }

    /**
     * Create a key using the provided serialized value, previously obtained using DUMP.
     *
     * @param key   the key.
     * @param value the serialized-value type: string.
     * @param args  the {@link RestoreArgs}, must not be {@code null}.
     * @return String simple-string-reply The command returns OK on success.
     * @since 5.1
     */
    @Override
    public Mono<String> restore(String key, byte[] value, RestoreArgs args) {
        return null;
    }

    /**
     * Sort the elements in a list, set or sorted set.
     *
     * @param key the key.
     * @return V array-reply list of sorted elements.
     */
    @Override
    public Flux<String> sort(String key) {
        return null;
    }

    /**
     * Sort the elements in a list, set or sorted set.
     *
     * @param channel streaming channel that receives a call for every value.
     * @param key     the key.
     * @return Long number of values.
     */
    @Override
    @Deprecated
    public Mono<Long> sort(ValueStreamingChannel<String> channel, String key) {
        return null;
    }

    /**
     * Sort the elements in a list, set or sorted set.
     *
     * @param key      the key.
     * @param sortArgs sort arguments.
     * @return V array-reply list of sorted elements.
     */
    @Override
    public Flux<String> sort(String key, SortArgs sortArgs) {
        return null;
    }

    /**
     * Sort the elements in a list, set or sorted set.
     *
     * @param channel  streaming channel that receives a call for every value.
     * @param key      the key.
     * @param sortArgs sort arguments.
     * @return Long number of values.
     */
    @Override
    @Deprecated
    public Mono<Long> sort(ValueStreamingChannel<String> channel, String key, SortArgs sortArgs) {
        return null;
    }

    @Override
    public Flux<String> sortReadOnly(String s) {
        return null;
    }

    /**
     * @param valueStreamingChannel
     * @param s
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> sortReadOnly(ValueStreamingChannel<String> valueStreamingChannel, String s) {
        return null;
    }

    @Override
    public Flux<String> sortReadOnly(String s, SortArgs sortArgs) {
        return null;
    }

    /**
     * @param valueStreamingChannel
     * @param s
     * @param sortArgs
     * @deprecated
     */
    @Override
    @Deprecated
    public Mono<Long> sortReadOnly(ValueStreamingChannel<String> valueStreamingChannel, String s, SortArgs sortArgs) {
        return null;
    }

    /**
     * Sort the elements in a list, set or sorted set.
     *
     * @param key         the key.
     * @param sortArgs    sort arguments.
     * @param destination the destination key to store sort results.
     * @return Long number of values.
     */
    @Override
    public Mono<Long> sortStore(String key, SortArgs sortArgs, String destination) {
        return null;
    }

    /**
     * Execute a Lua script server side.
     *
     * @param script Lua 5.1 script.
     * @param type   output type.
     * @param keys   key names.
     * @return script result.
     */
    @Override
    public <T> Flux<T> eval(String script, ScriptOutputType type, String... keys) {
        return null;
    }

    @Override
    public <T> Flux<T> eval(byte[] bytes, ScriptOutputType scriptOutputType, String... strings) {
        return null;
    }

    /**
     * Execute a Lua script server side.
     *
     * @param script Lua 5.1 script.
     * @param type   the type.
     * @param keys   the keys.
     * @param values the values.
     * @return script result.
     */
    @Override
    public <T> Flux<T> eval(String script, ScriptOutputType type, String[] keys, String... values) {
        return null;
    }

    @Override
    public <T> Flux<T> eval(byte[] bytes, ScriptOutputType scriptOutputType, String[] strings, String... strings2) {
        return null;
    }

    @Override
    public <T> Flux<T> evalReadOnly(byte[] bytes, ScriptOutputType scriptOutputType, String[] strings, String... strings2) {
        return null;
    }

    /**
     * Evaluates a script cached on the server side by its SHA1 digest.
     *
     * @param digest SHA1 of the script.
     * @param type   the type.
     * @param keys   the keys.
     * @return script result.
     */
    @Override
    public <T> Flux<T> evalsha(String digest, ScriptOutputType type, String... keys) {
        return null;
    }

    /**
     * Execute a Lua script server side.
     *
     * @param digest SHA1 of the script.
     * @param type   the type.
     * @param keys   the keys.
     * @param values the values.
     * @return script result.
     */
    @Override
    public <T> Flux<T> evalsha(String digest, ScriptOutputType type, String[] keys, String... values) {
        return null;
    }

    @Override
    public <T> Flux<T> evalshaReadOnly(String s, ScriptOutputType scriptOutputType, String[] strings, String... strings2) {
        return null;
    }

    /**
     * Check existence of scripts in the script cache.
     *
     * @param digests script digests.
     * @return Boolean array-reply The command returns an array of integers that correspond to the specified SHA1 digest
     * arguments. For every corresponding SHA1 digest of a script that actually exists in the script cache, an 1 is
     * returned, otherwise 0 is returned.
     */
    @Override
    public Flux<Boolean> scriptExists(String... digests) {
        return null;
    }

    /**
     * Remove all the scripts from the script cache on all cluster nodes.
     *
     * @return String simple-string-reply.
     */
    @Override
    public Mono<String> scriptFlush() {
        return null;
    }

    @Override
    public Mono<String> scriptFlush(FlushMode flushMode) {
        return null;
    }

    /**
     * Kill the script currently in execution on all cluster nodes. This call does not fail even if no scripts are running.
     *
     * @return String simple-string-reply, always {@literal OK}.
     */
    @Override
    public Mono<String> scriptKill() {
        return null;
    }

    /**
     * Load the specified Lua script into the script cache on all cluster nodes.
     *
     * @param script script content.
     * @return String bulk-string-reply This command returns the SHA1 digest of the script added into the script cache.
     */
    @Override
    public Mono<String> scriptLoad(String script) {
        return null;
    }

    @Override
    public Mono<String> scriptLoad(byte[] bytes) {
        return null;
    }

    /**
     * Create a SHA1 digest from a Lua script.
     *
     * @param script script content.
     * @return the SHA1 value.
     */
    @Override
    public String digest(String script) {
        return null;
    }

    @Override
    public String digest(byte[] bytes) {
        return null;
    }

    /**
     * Synchronously save the dataset to disk and then shut down all nodes of the cluster.
     *
     * @param save {@code true} force save operation.
     */
    @Override
    public Mono<Void> shutdown(boolean save) {
        return null;
    }

    @Override
    public Mono<Void> shutdown(ShutdownArgs shutdownArgs) {
        return null;
    }

    /**
     * Make the server a replica of another instance, or promote it as master.
     *
     * @param host the host type: string.
     * @param port the port type: string.
     * @return String simple-string-reply.
     */
    @Override
    public Mono<String> slaveof(String host, int port) {
        return null;
    }

    /**
     * Promote server as master.
     *
     * @return String simple-string-reply.
     */
    @Override
    public Mono<String> slaveofNoOne() {
        return null;
    }

    /**
     * Read the slow log.
     *
     * @return Object deeply nested multi bulk replies.
     */
    @Override
    public Flux<Object> slowlogGet() {
        return null;
    }

    /**
     * Read the slow log.
     *
     * @param count the count.
     * @return Object deeply nested multi bulk replies.
     */
    @Override
    public Flux<Object> slowlogGet(int count) {
        return null;
    }

    /**
     * Obtaining the current length of the slow log.
     *
     * @return Long length of the slow log.
     */
    @Override
    public Mono<Long> slowlogLen() {
        return null;
    }

    /**
     * Resetting the slow log.
     *
     * @return String simple-string-reply The commands returns OK on success.
     */
    @Override
    public Mono<String> slowlogReset() {
        return null;
    }

    /**
     * Return the current server time.
     *
     * @return V array-reply specifically:
     * <p>
     * A multi bulk reply containing two elements:
     * <p>
     * unix time in seconds. microseconds.
     */
    @Override
    public Flux<String> time() {
        return null;
    }

    /**
     * Incrementally iterate the keys space over the whole Cluster.
     *
     * @return KeyScanCursor&lt;K&gt; scan cursor.
     */
    @Override
    public Mono<KeyScanCursor<String>> scan() {
        return null;
    }

    /**
     * Incrementally iterate the keys space over the whole Cluster.
     *
     * @param scanArgs scan arguments.
     * @return KeyScanCursor&lt;K&gt; scan cursor.
     */
    @Override
    public Mono<KeyScanCursor<String>> scan(ScanArgs scanArgs) {
        return null;
    }

    /**
     * Incrementally iterate the keys space over the whole Cluster.
     *
     * @param scanCursor cursor to resume the scan. It's required to reuse the {@code scanCursor} instance from the previous
     *                   {@link #scan()} call.
     * @param scanArgs   scan arguments.
     * @return KeyScanCursor&lt;K&gt; scan cursor.
     */
    @Override
    public Mono<KeyScanCursor<String>> scan(ScanCursor scanCursor, ScanArgs scanArgs) {
        return null;
    }

    /**
     * Incrementally iterate the keys space over the whole Cluster.
     *
     * @param scanCursor cursor to resume the scan. It's required to reuse the {@code scanCursor} instance from the previous
     *                   {@link #scan()} call.
     * @return KeyScanCursor&lt;K&gt; scan cursor.
     */
    @Override
    public Mono<KeyScanCursor<String>> scan(ScanCursor scanCursor) {
        return null;
    }

    /**
     * Incrementally iterate the keys space over the whole Cluster.
     *
     * @param channel streaming channel that receives a call for every key.
     * @return StreamScanCursor scan cursor.
     */
    @Override
    public Mono<StreamScanCursor> scan(KeyStreamingChannel<String> channel) {
        return null;
    }

    /**
     * Incrementally iterate the keys space over the whole Cluster.
     *
     * @param channel  streaming channel that receives a call for every key.
     * @param scanArgs scan arguments.
     * @return StreamScanCursor scan cursor.
     */
    @Override
    public Mono<StreamScanCursor> scan(KeyStreamingChannel<String> channel, ScanArgs scanArgs) {
        return null;
    }

    /**
     * Incrementally iterate the keys space over the whole Cluster.
     *
     * @param channel    streaming channel that receives a call for every key.
     * @param scanCursor cursor to resume the scan. It's required to reuse the {@code scanCursor} instance from the previous
     *                   {@link #scan()} call.
     * @param scanArgs   scan arguments.
     * @return StreamScanCursor scan cursor.
     */
    @Override
    public Mono<StreamScanCursor> scan(KeyStreamingChannel<String> channel, ScanCursor scanCursor, ScanArgs scanArgs) {
        return null;
    }

    /**
     * Incrementally iterate the keys space over the whole Cluster.
     *
     * @param channel    streaming channel that receives a call for every key.
     * @param scanCursor cursor to resume the scan. It's required to reuse the {@code scanCursor} instance from the previous
     *                   {@link #scan()} call.
     * @return StreamScanCursor scan cursor.
     */
    @Override
    public Mono<StreamScanCursor> scan(KeyStreamingChannel<String> channel, ScanCursor scanCursor) {
        return null;
    }

    /**
     * Touch one or more keys with pipelining. Touch sets the last accessed time for a key. Non-exsitent keys wont get created.
     * Cross-slot keys will result in multiple calls to the particular cluster nodes.
     *
     * @param keys the keys.
     * @return Long integer-reply the number of found keys.
     */
    @Override
    public Mono<Long> touch(String... keys) {
        return null;
    }

    /**
     * Get the time to live for a key.
     *
     * @param key the key.
     * @return Long integer-reply TTL in seconds, or a negative value in order to signal an error (see the description above).
     */
    @Override
    public Mono<Long> ttl(String key) {
        return null;
    }

    /**
     * Determine the type stored at key.
     *
     * @param key the key.
     * @return String simple-string-reply type of {@code key}, or {@code none} when {@code key} does not exist.
     */
    @Override
    public Mono<String> type(String key) {
        return null;
    }

    /**
     * Single geo add.
     *
     * @param key       the key of the geo set.
     * @param longitude the longitude coordinate according to WGS84.
     * @param latitude  the latitude coordinate according to WGS84.
     * @param member    the member to add.
     * @return Long integer-reply the number of elements that were added to the set.
     */
    @Override
    public Mono<Long> geoadd(String key, double longitude, double latitude, String member) {
        return null;
    }

    @Override
    public Mono<Long> geoadd(String s, double v, double v1, String v2, GeoAddArgs geoAddArgs) {
        return null;
    }

    /**
     * Multi geo add.
     *
     * @param key          the key of the geo set.
     * @param lngLatMember triplets of double longitude, double latitude and V member.
     * @return Long integer-reply the number of elements that were added to the set.
     */
    @Override
    public Mono<Long> geoadd(String key, Object... lngLatMember) {
        return null;
    }

    @Override
    public Mono<Long> geoadd(String s, GeoValue<String>... geoValues) {
        return null;
    }

    @Override
    public Mono<Long> geoadd(String s, GeoAddArgs geoAddArgs, Object... objects) {
        return null;
    }

    @Override
    public Mono<Long> geoadd(String s, GeoAddArgs geoAddArgs, GeoValue<String>... geoValues) {
        return null;
    }

    /**
     * Retrieve Geohash strings representing the position of one or more elements in a sorted set value representing a
     * geospatial index.
     *
     * @param key     the key of the geo set.
     * @param members the members.
     * @return bulk reply Geohash strings in the order of {@code members}. Returns {@code null} if a member is not found.
     */
    @Override
    public Flux<Value<String>> geohash(String key, String... members) {
        return null;
    }

    /**
     * Retrieve members selected by distance with the center of {@code longitude} and {@code latitude}.
     *
     * @param key       the key of the geo set.
     * @param longitude the longitude coordinate according to WGS84.
     * @param latitude  the latitude coordinate according to WGS84.
     * @param distance  radius distance.
     * @param unit      distance unit.
     * @return bulk reply.
     */
    @Override
    public Flux<String> georadius(String key, double longitude, double latitude, double distance, GeoArgs.Unit unit) {
        return null;
    }

    /**
     * Retrieve members selected by distance with the center of {@code longitude} and {@code latitude}.
     *
     * @param key       the key of the geo set.
     * @param longitude the longitude coordinate according to WGS84.
     * @param latitude  the latitude coordinate according to WGS84.
     * @param distance  radius distance.
     * @param unit      distance unit.
     * @param geoArgs   args to control the result.
     * @return nested multi-bulk reply. The {@link GeoWithin} contains only fields which were requested by {@link GeoArgs}.
     */
    @Override
    public Flux<GeoWithin<String>> georadius(String key, double longitude, double latitude, double distance, GeoArgs.Unit unit, GeoArgs geoArgs) {
        return null;
    }

    /**
     * Perform a {@link #(Object, double, double, double, GeoArgs.Unit, GeoArgs)} query and store the results in a
     * sorted set.
     *
     * @param key                the key of the geo set.
     * @param longitude          the longitude coordinate according to WGS84.
     * @param latitude           the latitude coordinate according to WGS84.
     * @param distance           radius distance.
     * @param unit               distance unit.
     * @param geoRadiusStoreArgs args to store either the resulting elements with their distance or the resulting elements with
     *                           their locations a sorted set.
     * @return Long integer-reply the number of elements in the result.
     */
    @Override
    public Mono<Long> georadius(String key, double longitude, double latitude, double distance, GeoArgs.Unit unit, GeoRadiusStoreArgs<String> geoRadiusStoreArgs) {
        return null;
    }

    /**
     * Retrieve members selected by distance with the center of {@code member}. The member itself is always contained in the
     * results.
     *
     * @param key      the key of the geo set.
     * @param member   reference member.
     * @param distance radius distance.
     * @param unit     distance unit.
     * @return set of members.
     */
    @Override
    public Flux<String> georadiusbymember(String key, String member, double distance, GeoArgs.Unit unit) {
        return null;
    }

    /**
     * Retrieve members selected by distance with the center of {@code member}. The member itself is always contained in the
     * results.
     *
     * @param key      the key of the geo set.
     * @param member   reference member.
     * @param distance radius distance.
     * @param unit     distance unit.
     * @param geoArgs  args to control the result.
     * @return nested multi-bulk reply. The {@link GeoWithin} contains only fields which were requested by {@link GeoArgs}.
     */
    @Override
    public Flux<GeoWithin<String>> georadiusbymember(String key, String member, double distance, GeoArgs.Unit unit, GeoArgs geoArgs) {
        return null;
    }

    /**
     * Perform a {@link #(Object, Object, double, GeoArgs.Unit, GeoArgs)} query and store the results in a
     * sorted set.
     *
     * @param key                the key of the geo set.
     * @param member             reference member.
     * @param distance           radius distance.
     * @param unit               distance unit.
     * @param geoRadiusStoreArgs args to store either the resulting elements with their distance or the resulting elements with
     *                           their locations a sorted set.
     * @return Long integer-reply the number of elements in the result.
     */
    @Override
    public Mono<Long> georadiusbymember(String key, String member, double distance, GeoArgs.Unit unit, GeoRadiusStoreArgs<String> geoRadiusStoreArgs) {
        return null;
    }

    @Override
    public Flux<String> geosearch(String s, GeoSearch.GeoRef<String> geoRef, GeoSearch.GeoPredicate geoPredicate) {
        return null;
    }

    @Override
    public Flux<GeoWithin<String>> geosearch(String s, GeoSearch.GeoRef<String> geoRef, GeoSearch.GeoPredicate geoPredicate, GeoArgs geoArgs) {
        return null;
    }

    @Override
    public Mono<Long> geosearchstore(String s, String k1, GeoSearch.GeoRef<String> geoRef, GeoSearch.GeoPredicate geoPredicate, GeoArgs geoArgs, boolean b) {
        return null;
    }

    /**
     * Get geo coordinates for the {@code members}.
     *
     * @param key     the key of the geo set.
     * @param members the members.
     * @return a list of {@link GeoCoordinates}s representing the x,y position of each element specified in the arguments. For
     * missing elements {@code null} is returned.
     */
    @Override
    public Flux<Value<GeoCoordinates>> geopos(String key, String... members) {
        return null;
    }

    /**
     * Retrieve distance between points {@code from} and {@code to}. If one or more elements are missing {@code null} is
     * returned. Default in meters by, otherwise according to {@code unit}
     *
     * @param key  the key of the geo set.
     * @param from from member.
     * @param to   to member.
     * @param unit distance unit.
     * @return distance between points {@code from} and {@code to}. If one or more elements are missing {@code null} is
     * returned.
     */
    @Override
    public Mono<Double> geodist(String key, String from, String to, GeoArgs.Unit unit) {
        return null;
    }

    /**
     * Adds the specified elements to the specified HyperLogLog.
     *
     * @param key    the key.
     * @param values the values.
     * @return Long integer-reply specifically:
     * <p>
     * 1 if at least 1 HyperLogLog internal register was altered. 0 otherwise.
     */
    @Override
    public Mono<Long> pfadd(String key, String... values) {
        return null;
    }

    /**
     * Merge N different HyperLogLogs into a single one.
     *
     * @param destkey    the destination key.
     * @param sourcekeys the source key.
     * @return String simple-string-reply The command just returns {@code OK}.
     */
    @Override
    public Mono<String> pfmerge(String destkey, String... sourcekeys) {
        return null;
    }

    /**
     * Return the approximated cardinality of the set(s) observed by the HyperLogLog at key(s).
     *
     * @param keys the keys.
     * @return Long integer-reply specifically:
     * <p>
     * The approximated number of unique elements observed via {@code PFADD}.
     */
    @Override
    public Mono<Long> pfcount(String... keys) {
        return null;
    }

    /**
     * Delete one or more hash fields.
     *
     * @param key    the key.
     * @param fields the field type: key.
     * @return Long integer-reply the number of fields that were removed from the hash, not including specified but non existing
     * fields.
     */
    @Override
    public Mono<Long> hdel(String key, String... fields) {
        return null;
    }

    /**
     * Determine if a hash field exists.
     *
     * @param key   the key.
     * @param field the field type: key.
     * @return {@code true} if the hash contains {@code field}. {@code false} if the hash does not contain {@code field}, or
     * {@code key} does not exist.
     */
    @Override
    public Mono<Boolean> hexists(String key, String field) {
        return null;
    }

    /**
     * Get the value of a hash field.
     *
     * @param key   the key.
     * @param field the field type: key.
     * @return V bulk-string-reply the value associated with {@code field}, or {@code null} when {@code field} is not present in
     * the hash or {@code key} does not exist.
     */
    @Override
    public Mono<String> hget(String key, String field) {
        return null;
    }

    /**
     * Increment the integer value of a hash field by the given number.
     *
     * @param key    the key.
     * @param field  the field type: key.
     * @param amount the increment type: long.
     * @return Long integer-reply the value at {@code field} after the increment operation.
     */
    @Override
    public Mono<Long> hincrby(String key, String field, long amount) {
        return null;
    }

    /**
     * Increment the float value of a hash field by the given amount.
     *
     * @param key    the key.
     * @param field  the field type: key.
     * @param amount the increment type: double.
     * @return Double bulk-string-reply the value of {@code field} after the increment.
     */
    @Override
    public Mono<Double> hincrbyfloat(String key, String field, double amount) {
        return null;
    }

    @Override
    public Flux<KeyValue<String, String>> hgetall(String s) {
        return null;
    }

    /**
     * Stream over all the fields and values in a hash.
     *
     * @param channel the channel.
     * @param key     the key.
     * @return Long count of the keys.
     */
    @Override
    public Mono<Long> hgetall(KeyValueStreamingChannel<String, String> channel, String key) {
        return null;
    }

    /**
     * Get all the fields in a hash.
     *
     * @param key the key.
     * @return K array-reply list of fields in the hash, or an empty list when {@code key} does not exist.
     */
    @Override
    public Flux<String> hkeys(String key) {
        return null;
    }

    /**
     * Stream over all the fields in a hash.
     *
     * @param channel the channel.
     * @param key     the key.
     * @return Long count of the keys.
     */
    @Override
    public Mono<Long> hkeys(KeyStreamingChannel<String> channel, String key) {
        return null;
    }

    /**
     * Get the number of fields in a hash.
     *
     * @param key the key.
     * @return Long integer-reply number of fields in the hash, or {@code 0} when {@code key} does not exist.
     */
    @Override
    public Mono<Long> hlen(String key) {
        return null;
    }

    /**
     * Get the values of all the given hash fields.
     *
     * @param key    the key.
     * @param fields the field type: key.
     * @return V array-reply list of values associated with the given fields, in the same.
     */
    @Override
    public Flux<KeyValue<String, String>> hmget(String key, String... fields) {
        return null;
    }

    /**
     * Stream over the values of all the given hash fields.
     *
     * @param channel the channel.
     * @param key     the key.
     * @param fields  the fields.
     * @return Long count of the keys.
     */
    @Override
    public Mono<Long> hmget(KeyValueStreamingChannel<String, String> channel, String key, String... fields) {
        return null;
    }

    /**
     * Set multiple hash fields to multiple values.
     *
     * @param key the key.
     * @param map the null.
     * @return String simple-string-reply.
     */
    @Override
    public Mono<String> hmset(String key, Map<String, String> map) {
        return null;
    }

    @Override
    public Mono<String> hrandfield(String s) {
        return null;
    }

    @Override
    public Flux<String> hrandfield(String s, long l) {
        return null;
    }

    @Override
    public Mono<KeyValue<String, String>> hrandfieldWithvalues(String s) {
        return null;
    }

    @Override
    public Flux<KeyValue<String, String>> hrandfieldWithvalues(String s, long l) {
        return null;
    }

    /**
     * Incrementally iterate hash fields and associated values.
     *
     * @param key the key.
     * @return MapScanCursor&lt;K, V&gt; map scan cursor.
     */
    @Override
    public Mono<MapScanCursor<String, String>> hscan(String key) {
        return null;
    }

    /**
     * Incrementally iterate hash fields and associated values.
     *
     * @param key      the key.
     * @param scanArgs scan arguments.
     * @return MapScanCursor&lt;K, V&gt; map scan cursor.
     */
    @Override
    public Mono<MapScanCursor<String, String>> hscan(String key, ScanArgs scanArgs) {
        return null;
    }

    /**
     * Incrementally iterate hash fields and associated values.
     *
     * @param key        the key.
     * @param scanCursor cursor to resume from a previous scan, must not be {@code null}.
     * @param scanArgs   scan arguments.
     * @return MapScanCursor&lt;K, V&gt; map scan cursor.
     */
    @Override
    public Mono<MapScanCursor<String, String>> hscan(String key, ScanCursor scanCursor, ScanArgs scanArgs) {
        return null;
    }

    /**
     * Incrementally iterate hash fields and associated values.
     *
     * @param key        the key.
     * @param scanCursor cursor to resume from a previous scan, must not be {@code null}.
     * @return MapScanCursor&lt;K, V&gt; map scan cursor.
     */
    @Override
    public Mono<MapScanCursor<String, String>> hscan(String key, ScanCursor scanCursor) {
        return null;
    }

    /**
     * Incrementally iterate hash fields and associated values.
     *
     * @param channel streaming channel that receives a call for every key-value pair.
     * @param key     the key.
     * @return StreamScanCursor scan cursor.
     */
    @Override
    public Mono<StreamScanCursor> hscan(KeyValueStreamingChannel<String, String> channel, String key) {
        return null;
    }

    /**
     * Incrementally iterate hash fields and associated values.
     *
     * @param channel  streaming channel that receives a call for every key-value pair.
     * @param key      the key.
     * @param scanArgs scan arguments.
     * @return StreamScanCursor scan cursor.
     */
    @Override
    public Mono<StreamScanCursor> hscan(KeyValueStreamingChannel<String, String> channel, String key, ScanArgs scanArgs) {
        return null;
    }

    /**
     * Incrementally iterate hash fields and associated values.
     *
     * @param channel    streaming channel that receives a call for every key-value pair.
     * @param key        the key.
     * @param scanCursor cursor to resume from a previous scan, must not be {@code null}.
     * @param scanArgs   scan arguments.
     * @return StreamScanCursor scan cursor.
     */
    @Override
    public Mono<StreamScanCursor> hscan(KeyValueStreamingChannel<String, String> channel, String key, ScanCursor scanCursor, ScanArgs scanArgs) {
        return null;
    }

    /**
     * Incrementally iterate hash fields and associated values.
     *
     * @param channel    streaming channel that receives a call for every key-value pair.
     * @param key        the key.
     * @param scanCursor cursor to resume from a previous scan, must not be {@code null}.
     * @return StreamScanCursor scan cursor.
     */
    @Override
    public Mono<StreamScanCursor> hscan(KeyValueStreamingChannel<String, String> channel, String key, ScanCursor scanCursor) {
        return null;
    }

    /**
     * Set the string value of a hash field.
     *
     * @param key   the key.
     * @param field the field type: key.
     * @param value the value.
     * @return Boolean integer-reply specifically:
     * <p>
     * {@code true} if {@code field} is a new field in the hash and {@code value} was set. {@code false} if
     * {@code field} already exists in the hash and the value was updated.
     */
    @Override
    public Mono<Boolean> hset(String key, String field, String value) {
        return null;
    }

    /**
     * Set multiple hash fields to multiple values.
     *
     * @param key the key of the hash.
     * @param map the field/value pairs to update.
     * @return Long integer-reply: the number of fields that were added.
     * @since 5.3
     */
    @Override
    public Mono<Long> hset(String key, Map<String, String> map) {
        return null;
    }

    /**
     * Set the value of a hash field, only if the field does not exist.
     *
     * @param key   the key.
     * @param field the field type: key.
     * @param value the value.
     * @return Boolean integer-reply specifically:
     * <p>
     * {@code 1} if {@code field} is a new field in the hash and {@code value} was set. {@code 0} if {@code field}
     * already exists in the hash and no operation was performed.
     */
    @Override
    public Mono<Boolean> hsetnx(String key, String field, String value) {
        return null;
    }

    /**
     * Get the string length of the field value in a hash.
     *
     * @param key   the key.
     * @param field the field type: key.
     * @return Long integer-reply the string length of the {@code field} value, or {@code 0} when {@code field} is not present
     * in the hash or {@code key} does not exist at all.
     */
    @Override
    public Mono<Long> hstrlen(String key, String field) {
        return null;
    }

    /**
     * Get all the values in a hash.
     *
     * @param key the key.
     * @return V array-reply list of values in the hash, or an empty list when {@code key} does not exist.
     */
    @Override
    public Flux<String> hvals(String key) {
        return null;
    }

    /**
     * Stream over all the values in a hash.
     *
     * @param channel streaming channel that receives a call for every value.
     * @param key     the key.
     * @return Long count of the keys.
     */
    @Override
    public Mono<Long> hvals(ValueStreamingChannel<String> channel, String key) {
        return null;
    }

    @Override
    public Mono<String> blmove(String s, String k1, LMoveArgs lMoveArgs, long l) {
        return null;
    }

    @Override
    public Mono<String> blmove(String s, String k1, LMoveArgs lMoveArgs, double v) {
        return null;
    }

    @Override
    public Mono<KeyValue<String, List<String>>> blmpop(long l, LMPopArgs lmPopArgs, String... strings) {
        return null;
    }

    @Override
    public Mono<KeyValue<String, List<String>>> blmpop(double v, LMPopArgs lmPopArgs, String... strings) {
        return null;
    }

    /**
     * Remove and get the first element in a list, or block until one is available.
     *
     * @param timeout the timeout in seconds.
     * @param keys    the keys.
     * @return KeyValue&lt;K,V&gt; array-reply specifically:
     * <p>
     * A {@code null} multi-bulk when no element could be popped and the timeout expired. A two-element multi-bulk with
     * the first element being the name of the key where an element was popped and the second element being the value of
     * the popped element.
     */
    @Override
    public Mono<KeyValue<String, String>> blpop(long timeout, String... keys) {
        return null;
    }

    @Override
    public Mono<KeyValue<String, String>> blpop(double v, String... strings) {
        return null;
    }

    /**
     * Remove and get the last element in a list, or block until one is available.
     *
     * @param timeout the timeout in seconds.
     * @param keys    the keys.
     * @return KeyValue&lt;K,V&gt; array-reply specifically:
     * <p>
     * A {@code null} multi-bulk when no element could be popped and the timeout expired. A two-element multi-bulk with
     * the first element being the name of the key where an element was popped and the second element being the value of
     * the popped element.
     */
    @Override
    public Mono<KeyValue<String, String>> brpop(long timeout, String... keys) {
        return null;
    }

    @Override
    public Mono<KeyValue<String, String>> brpop(double v, String... strings) {
        return null;
    }

    /**
     * Pop a value from a list, push it to another list and return it; or block until one is available.
     *
     * @param timeout     the timeout in seconds.
     * @param source      the source key.
     * @param destination the destination type: key.
     * @return V bulk-string-reply the element being popped from {@code source} and pushed to {@code destination}. If
     * {@code timeout} is reached, a.
     */
    @Override
    public Mono<String> brpoplpush(long timeout, String source, String destination) {
        return null;
    }

    @Override
    public Mono<String> brpoplpush(double v, String s, String k1) {
        return null;
    }

    /**
     * Get an element from a list by its index.
     *
     * @param key   the key.
     * @param index the index type: long.
     * @return V bulk-string-reply the requested element, or {@code null} when {@code index} is out of range.
     */
    @Override
    public Mono<String> lindex(String key, long index) {
        return null;
    }

    /**
     * Insert an element before or after another element in a list.
     *
     * @param key    the key.
     * @param before the before.
     * @param pivot  the pivot.
     * @param value  the value.
     * @return Long integer-reply the length of the list after the insert operation, or {@code -1} when the value {@code pivot}
     * was not found.
     */
    @Override
    public Mono<Long> linsert(String key, boolean before, String pivot, String value) {
        return null;
    }

    /**
     * Get the length of a list.
     *
     * @param key the key.
     * @return Long integer-reply the length of the list at {@code key}.
     */
    @Override
    public Mono<Long> llen(String key) {
        return null;
    }

    @Override
    public Mono<String> lmove(String s, String k1, LMoveArgs lMoveArgs) {
        return null;
    }

    @Override
    public Mono<KeyValue<String, List<String>>> lmpop(LMPopArgs lmPopArgs, String... strings) {
        return null;
    }

    /**
     * Remove and get the first element in a list.
     *
     * @param key the key.
     * @return V bulk-string-reply the value of the first element, or {@code null} when {@code key} does not exist.
     */
    @Override
    public Mono<String> lpop(String key) {
        return null;
    }

    @Override
    public Flux<String> lpop(String s, long l) {
        return null;
    }

    /**
     * Return the index of matching elements inside a Redis list. By default, when no options are given, it will scan the list
     * from head to tail, looking for the first match of "element". If the element is found, its index (the zero-based position
     * in the list) is returned. Otherwise, if no match is found, {@code null} is returned. The returned elements indexes are
     * always referring to what {@link #lindex(Object, long)} would return. So first element from head is {@code 0},
     * and so forth.
     *
     * @param key   the key.
     * @param value the element to search for.
     * @return V integer-reply representing the matching element, or null if there is no match.
     * @since 5.3.2
     */
    @Override
    public Mono<Long> lpos(String key, String value) {
        return null;
    }

    /**
     * Return the index of matching elements inside a Redis list. By default, when no options are given, it will scan the list
     * from head to tail, looking for the first match of "element". If the element is found, its index (the zero-based position
     * in the list) is returned. Otherwise, if no match is found, {@code null} is returned. The returned elements indexes are
     * always referring to what {@link #lindex(Object, long)} would return. So first element from head is {@code 0},
     * and so forth.
     *
     * @param key   the key.
     * @param value the element to search for.
     * @param args  command arguments to configure{@code FIRST} and {@code MAXLEN} options.
     * @return V integer-reply representing the matching element, or null if there is no match.
     * @since 5.3.2
     */
    @Override
    public Mono<Long> lpos(String key, String value, LPosArgs args) {
        return null;
    }

    /**
     * Return the index of matching elements inside a Redis list using the {@code COUNT} option. By default, when no options are
     * given, it will scan the list from head to tail, looking for the first match of "element". The returned elements indexes
     * are always referring to what {@link #lindex(Object, long)} would return. So first element from head is
     * {@code 0}, and so forth.
     *
     * @param key   the key.
     * @param value the element to search for.
     * @param count limit the number of matches.
     * @return V integer-reply representing the matching elements, or empty if there is no match.
     * @since 5.3.2
     */
    @Override
    public Flux<Long> lpos(String key, String value, int count) {
        return null;
    }

    /**
     * Return the index of matching elements inside a Redis list using the {@code COUNT} option. By default, when no options are
     * given, it will scan the list from head to tail, looking for the first match of "element". The returned elements indexes
     * are always referring to what {@link #lindex(Object, long)} would return. So first element from head is
     * {@code 0}, and so forth.
     *
     * @param key   the key.
     * @param value the element to search for.
     * @param count limit the number of matches.
     * @param args  command arguments to configure{@code FIRST} and {@code MAXLEN} options.
     * @return V integer-reply representing the matching elements, or empty if there is no match.
     * @since 5.3.2
     */
    @Override
    public Flux<Long> lpos(String key, String value, int count, LPosArgs args) {
        return null;
    }

    /**
     * Prepend one or multiple values to a list.
     *
     * @param key    the key.
     * @param values the value.
     * @return Long integer-reply the length of the list after the push operations.
     */
    @Override
    public Mono<Long> lpush(String key, String... values) {
        return null;
    }

    /**
     * Prepend values to a list, only if the list exists.
     *
     * @param key    the key.
     * @param values the values.
     * @return Long integer-reply the length of the list after the push operation.
     */
    @Override
    public Mono<Long> lpushx(String key, String... values) {
        return null;
    }

    /**
     * Get a range of elements from a list.
     *
     * @param key   the key.
     * @param start the start type: long.
     * @param stop  the stop type: long.
     * @return V array-reply list of elements in the specified range.
     */
    @Override
    public Flux<String> lrange(String key, long start, long stop) {
        return null;
    }

    /**
     * Get a range of elements from a list.
     *
     * @param channel the channel.
     * @param key     the key.
     * @param start   the start type: long.
     * @param stop    the stop type: long.
     * @return Long count of elements in the specified range.
     */
    @Override
    public Mono<Long> lrange(ValueStreamingChannel<String> channel, String key, long start, long stop) {
        return null;
    }

    /**
     * Remove elements from a list.
     *
     * @param key   the key.
     * @param count the count type: long.
     * @param value the value.
     * @return Long integer-reply the number of removed elements.
     */
    @Override
    public Mono<Long> lrem(String key, long count, String value) {
        return null;
    }

    /**
     * Set the value of an element in a list by its index.
     *
     * @param key   the key.
     * @param index the index type: long.
     * @param value the value.
     * @return String simple-string-reply.
     */
    @Override
    public Mono<String> lset(String key, long index, String value) {
        return null;
    }

    /**
     * Trim a list to the specified range.
     *
     * @param key   the key.
     * @param start the start type: long.
     * @param stop  the stop type: long.
     * @return String simple-string-reply.
     */
    @Override
    public Mono<String> ltrim(String key, long start, long stop) {
        return null;
    }

    /**
     * Remove and get the last element in a list.
     *
     * @param key the key.
     * @return V bulk-string-reply the value of the last element, or {@code null} when {@code key} does not exist.
     */
    @Override
    public Mono<String> rpop(String key) {
        return null;
    }

    @Override
    public Flux<String> rpop(String s, long l) {
        return null;
    }

    /**
     * Remove the last element in a list, append it to another list and return it.
     *
     * @param source      the source key.
     * @param destination the destination type: key.
     * @return V bulk-string-reply the element being popped and pushed.
     */
    @Override
    public Mono<String> rpoplpush(String source, String destination) {
        return null;
    }

    /**
     * Append one or multiple values to a list.
     *
     * @param key    the key.
     * @param values the value.
     * @return Long integer-reply the length of the list after the push operation.
     */
    @Override
    public Mono<Long> rpush(String key, String... values) {
        return null;
    }

    /**
     * Append values to a list, only if the list exists.
     *
     * @param key    the key.
     * @param values the values.
     * @return Long integer-reply the length of the list after the push operation.
     */
    @Override
    public Mono<Long> rpushx(String key, String... values) {
        return null;
    }

    /**
     * Add one or more members to a set.
     *
     * @param key     the key.
     * @param members the member type: value.
     * @return Long integer-reply the number of elements that were added to the set, not including all the elements already
     * present into the set.
     */
    @Override
    public Mono<Long> sadd(String key, String... members) {
        return null;
    }

    /**
     * Get the number of members in a set.
     *
     * @param key the key.
     * @return Long integer-reply the cardinality (number of elements) of the set, or {@code false} if {@code key} does not
     * exist.
     */
    @Override
    public Mono<Long> scard(String key) {
        return null;
    }

    /**
     * Subtract multiple sets.
     *
     * @param keys the key.
     * @return V array-reply list with members of the resulting set.
     */
    @Override
    public Flux<String> sdiff(String... keys) {
        return null;
    }

    /**
     * Subtract multiple sets.
     *
     * @param channel the channel.
     * @param keys    the keys.
     * @return Long count of members of the resulting set.
     */
    @Override
    public Mono<Long> sdiff(ValueStreamingChannel<String> channel, String... keys) {
        return null;
    }

    /**
     * Subtract multiple sets and store the resulting set in a key.
     *
     * @param destination the destination type: key.
     * @param keys        the key.
     * @return Long integer-reply the number of elements in the resulting set.
     */
    @Override
    public Mono<Long> sdiffstore(String destination, String... keys) {
        return null;
    }

    /**
     * Intersect multiple sets.
     *
     * @param keys the key.
     * @return V array-reply list with members of the resulting set.
     */
    @Override
    public Flux<String> sinter(String... keys) {
        return null;
    }

    /**
     * Intersect multiple sets.
     *
     * @param channel the channel.
     * @param keys    the keys.
     * @return Long count of members of the resulting set.
     */
    @Override
    @Deprecated
    public Mono<Long> sinter(ValueStreamingChannel<String> channel, String... keys) {
        return null;
    }

    @Override
    public Mono<Long> sintercard(String... strings) {
        return null;
    }

    @Override
    public Mono<Long> sintercard(long l, String... strings) {
        return null;
    }

    /**
     * Intersect multiple sets and store the resulting set in a key.
     *
     * @param destination the destination type: key.
     * @param keys        the key.
     * @return Long integer-reply the number of elements in the resulting set.
     */
    @Override
    public Mono<Long> sinterstore(String destination, String... keys) {
        return null;
    }

    /**
     * Determine if a given value is a member of a set.
     *
     * @param key    the key.
     * @param member the member type: value.
     * @return Boolean integer-reply specifically:
     * <p>
     * {@code true} if the element is a member of the set. {@code false} if the element is not a member of the set, or
     * if {@code key} does not exist.
     */
    @Override
    public Mono<Boolean> sismember(String key, String member) {
        return null;
    }

    /**
     * Move a member from one set to another.
     *
     * @param source      the source key.
     * @param destination the destination type: key.
     * @param member      the member type: value.
     * @return Boolean integer-reply specifically:
     * <p>
     * {@code true} if the element is moved. {@code false} if the element is not a member of {@code source} and no
     * operation was performed.
     */
    @Override
    public Mono<Boolean> smove(String source, String destination, String member) {
        return null;
    }

    /**
     * Get all the members in a set.
     *
     * @param key the key.
     * @return V array-reply all elements of the set.
     */
    @Override
    public Flux<String> smembers(String key) {
        return null;
    }

    /**
     * Get all the members in a set.
     *
     * @param channel the channel.
     * @param key     the keys.
     * @return Long count of members of the resulting set.
     */
    @Override
    @Deprecated
    public Mono<Long> smembers(ValueStreamingChannel<String> channel, String key) {
        return null;
    }

    @Override
    public Flux<Boolean> smismember(String s, String... strings) {
        return null;
    }

    /**
     * Remove and return a random member from a set.
     *
     * @param key the key.
     * @return V bulk-string-reply the removed element, or {@code null} when {@code key} does not exist.
     */
    @Override
    public Mono<String> spop(String key) {
        return null;
    }

    /**
     * Remove and return one or multiple random members from a set.
     *
     * @param key   the key.
     * @param count number of members to pop.
     * @return V bulk-string-reply the removed element, or {@code null} when {@code key} does not exist.
     */
    @Override
    public Flux<String> spop(String key, long count) {
        return null;
    }

    /**
     * Get one random member from a set.
     *
     * @param key the key.
     * @return V bulk-string-reply without the additional {@code count} argument the command returns a Bulk Reply with the
     * randomly selected element, or {@code null} when {@code key} does not exist.
     */
    @Override
    public Mono<String> srandmember(String key) {
        return null;
    }

    /**
     * Get one or multiple random members from a set.
     *
     * @param key   the key.
     * @param count the count type: long.
     * @return V bulk-string-reply without the additional {@code count} argument the command returns a Bulk Reply with the
     * randomly selected element, or {@code null} when {@code key} does not exist.
     */
    @Override
    public Flux<String> srandmember(String key, long count) {
        return null;
    }

    /**
     * Get one or multiple random members from a set.
     *
     * @param channel streaming channel that receives a call for every value.
     * @param key     the key.
     * @param count   the count.
     * @return Long count of members of the resulting set.
     */
    @Override
    @Deprecated
    public Mono<Long> srandmember(ValueStreamingChannel<String> channel, String key, long count) {
        return null;
    }

    /**
     * Remove one or more members from a set.
     *
     * @param key     the key.
     * @param members the member type: value.
     * @return Long integer-reply the number of members that were removed from the set, not including non existing members.
     */
    @Override
    public Mono<Long> srem(String key, String... members) {
        return null;
    }

    /**
     * Add multiple sets.
     *
     * @param keys the key.
     * @return V array-reply list with members of the resulting set.
     */
    @Override
    public Flux<String> sunion(String... keys) {
        return null;
    }

    /**
     * Add multiple sets.
     *
     * @param channel streaming channel that receives a call for every value.
     * @param keys    the keys.
     * @return Long count of members of the resulting set.
     */
    @Override
    @Deprecated
    public Mono<Long> sunion(ValueStreamingChannel<String> channel, String... keys) {
        return null;
    }

    /**
     * Add multiple sets and store the resulting set in a key.
     *
     * @param destination the destination type: key.
     * @param keys        the key.
     * @return Long integer-reply the number of elements in the resulting set.
     */
    @Override
    public Mono<Long> sunionstore(String destination, String... keys) {
        return null;
    }

    /**
     * Incrementally iterate Set elements.
     *
     * @param key the key.
     * @return ValueScanCursor&lt;V&gt; scan cursor.
     */
    @Override
    public Mono<ValueScanCursor<String>> sscan(String key) {
        return null;
    }

    /**
     * Incrementally iterate Set elements.
     *
     * @param key      the key.
     * @param scanArgs scan arguments.
     * @return ValueScanCursor&lt;V&gt; scan cursor.
     */
    @Override
    public Mono<ValueScanCursor<String>> sscan(String key, ScanArgs scanArgs) {
        return null;
    }

    /**
     * Incrementally iterate Set elements.
     *
     * @param key        the key.
     * @param scanCursor cursor to resume from a previous scan, must not be {@code null}.
     * @param scanArgs   scan arguments.
     * @return ValueScanCursor&lt;V&gt; scan cursor.
     */
    @Override
    public Mono<ValueScanCursor<String>> sscan(String key, ScanCursor scanCursor, ScanArgs scanArgs) {
        return null;
    }

    /**
     * Incrementally iterate Set elements.
     *
     * @param key        the key.
     * @param scanCursor cursor to resume from a previous scan, must not be {@code null}.
     * @return ValueScanCursor&lt;V&gt; scan cursor.
     */
    @Override
    public Mono<ValueScanCursor<String>> sscan(String key, ScanCursor scanCursor) {
        return null;
    }

    /**
     * Incrementally iterate Set elements.
     *
     * @param channel streaming channel that receives a call for every value.
     * @param key     the key.
     * @return StreamScanCursor scan cursor.
     */
    @Override
    @Deprecated
    public Mono<StreamScanCursor> sscan(ValueStreamingChannel<String> channel, String key) {
        return null;
    }

    /**
     * Incrementally iterate Set elements.
     *
     * @param channel  streaming channel that receives a call for every value.
     * @param key      the key.
     * @param scanArgs scan arguments.
     * @return StreamScanCursor scan cursor.
     */
    @Override
    @Deprecated
    public Mono<StreamScanCursor> sscan(ValueStreamingChannel<String> channel, String key, ScanArgs scanArgs) {
        return null;
    }

    /**
     * Incrementally iterate Set elements.
     *
     * @param channel    streaming channel that receives a call for every value.
     * @param key        the key.
     * @param scanCursor cursor to resume from a previous scan, must not be {@code null}.
     * @param scanArgs   scan arguments.
     * @return StreamScanCursor scan cursor.
     */
    @Override
    public Mono<StreamScanCursor> sscan(ValueStreamingChannel<String> channel, String key, ScanCursor scanCursor, ScanArgs scanArgs) {
        return null;
    }

    /**
     * Incrementally iterate Set elements.
     *
     * @param channel    streaming channel that receives a call for every value.
     * @param key        the key.
     * @param scanCursor cursor to resume from a previous scan, must not be {@code null}.
     * @return StreamScanCursor scan cursor.
     */
    @Override
    @Deprecated
    public Mono<StreamScanCursor> sscan(ValueStreamingChannel<String> channel, String key, ScanCursor scanCursor) {
        return null;
    }

    @Override
    public Mono<KeyValue<String, ScoredValue<String>>> bzmpop(long l, ZPopArgs zPopArgs, String... strings) {
        return null;
    }

    @Override
    public Mono<KeyValue<String, List<ScoredValue<String>>>> bzmpop(long l, long l1, ZPopArgs zPopArgs, String... strings) {
        return null;
    }

    @Override
    public Mono<KeyValue<String, ScoredValue<String>>> bzmpop(double v, ZPopArgs zPopArgs, String... strings) {
        return null;
    }

    @Override
    public Mono<KeyValue<String, List<ScoredValue<String>>>> bzmpop(double v, int i, ZPopArgs zPopArgs, String... strings) {
        return null;
    }

    /**
     * Removes and returns a member with the lowest scores in the sorted set stored at one of the keys.
     *
     * @param timeout the timeout in seconds.
     * @param keys    the keys.
     * @return KeyValue&lt;K, ScoredValue&lt;V&gt;&gt; multi-bulk containing the name of the key, the score and the popped
     * member.
     * @since 5.1
     */
    @Override
    public Mono<KeyValue<String, ScoredValue<String>>> bzpopmin(long timeout, String... keys) {
        return null;
    }

    @Override
    public Mono<KeyValue<String, ScoredValue<String>>> bzpopmin(double v, String... strings) {
        return null;
    }

    /**
     * Removes and returns a member with the highest scores in the sorted set stored at one of the keys.
     *
     * @param timeout the timeout in seconds.
     * @param keys    the keys.
     * @return KeyValue&lt;K, ScoredValue&lt;V&gt;&gt; multi-bulk containing the name of the key, the score and the popped
     * member.
     * @since 5.1
     */
    @Override
    public Mono<KeyValue<String, ScoredValue<String>>> bzpopmax(long timeout, String... keys) {
        return null;
    }

    @Override
    public Mono<KeyValue<String, ScoredValue<String>>> bzpopmax(double v, String... strings) {
        return null;
    }

    /**
     * Add one or more members to a sorted set, or update its score if it already exists.
     *
     * @param key    the key.
     * @param score  the score.
     * @param member the member.
     * @return Long integer-reply specifically:
     * <p>
     * The number of elements added to the sorted sets, not including elements already existing for which the score was
     * updated.
     */
    @Override
    public Mono<Long> zadd(String key, double score, String member) {
        return null;
    }

    /**
     * Add one or more members to a sorted set, or update its score if it already exists.
     *
     * @param key             the key.
     * @param scoresAndValues the scoresAndValue tuples (score,value,score,value,...).
     * @return Long integer-reply specifically:
     * <p>
     * The number of elements added to the sorted sets, not including elements already existing for which the score was
     * updated.
     */
    @Override
    public Mono<Long> zadd(String key, Object... scoresAndValues) {
        return null;
    }

    /**
     * Add one or more members to a sorted set, or update its score if it already exists.
     *
     * @param key          the key.
     * @param scoredValues the scored values.
     * @return Long integer-reply specifically:
     * <p>
     * The number of elements added to the sorted sets, not including elements already existing for which the score was
     * updated.
     */
    @Override
    public Mono<Long> zadd(String key, ScoredValue<String>... scoredValues) {
        return null;
    }

    /**
     * Add one or more members to a sorted set, or update its score if it already exists.
     *
     * @param key      the key.
     * @param zAddArgs arguments for zadd.
     * @param score    the score.
     * @param member   the member.
     * @return Long integer-reply specifically:
     * <p>
     * The number of elements added to the sorted sets, not including elements already existing for which the score was
     * updated.
     */
    @Override
    public Mono<Long> zadd(String key, ZAddArgs zAddArgs, double score, String member) {
        return null;
    }

    /**
     * Add one or more members to a sorted set, or update its score if it already exists.
     *
     * @param key             the key.
     * @param zAddArgs        arguments for zadd.
     * @param scoresAndValues the scoresAndValue tuples (score,value,score,value,...).
     * @return Long integer-reply specifically:
     * <p>
     * The number of elements added to the sorted sets, not including elements already existing for which the score was
     * updated.
     */
    @Override
    public Mono<Long> zadd(String key, ZAddArgs zAddArgs, Object... scoresAndValues) {
        return null;
    }

    /**
     * Add one or more members to a sorted set, or update its score if it already exists.
     *
     * @param key          the ke.
     * @param zAddArgs     arguments for zadd.
     * @param scoredValues the scored values.
     * @return Long integer-reply specifically:
     * <p>
     * The number of elements added to the sorted sets, not including elements already existing for which the score was
     * updated.
     */
    @Override
    public Mono<Long> zadd(String key, ZAddArgs zAddArgs, ScoredValue<String>... scoredValues) {
        return null;
    }

    /**
     * Add one or more members to a sorted set, or update its score if it already exists applying the {@code INCR} option. ZADD
     * acts like ZINCRBY.
     *
     * @param key    the key.
     * @param score  the score.
     * @param member the member.
     * @return Long integer-reply specifically: The total number of elements changed.
     */
    @Override
    public Mono<Double> zaddincr(String key, double score, String member) {
        return null;
    }

    /**
     * Add one or more members to a sorted set, or update its score if it already exists applying the {@code INCR} option. ZADD
     * acts like ZINCRBY.
     *
     * @param key      the key.
     * @param zAddArgs arguments for zadd.
     * @param score    the score.
     * @param member   the member.
     * @return Long integer-reply specifically: The total number of elements changed.
     * @since 4.3
     */
    @Override
    public Mono<Double> zaddincr(String key, ZAddArgs zAddArgs, double score, String member) {
        return null;
    }

    /**
     * Get the number of members in a sorted set.
     *
     * @param key the key.
     * @return Long integer-reply the cardinality (number of elements) of the sorted set, or {@code false} if {@code key} does
     * not exist.
     */
    @Override
    public Mono<Long> zcard(String key) {
        return null;
    }

    /**
     * Count the members in a sorted set with scores within the given values.
     *
     * @param key the key.
     * @param min min score.
     * @param max max score.
     * @return Long integer-reply the number of elements in the specified score range.
     * @deprecated Use {@link #zcount(Object, Range)}.
     */
    @Override
    @Deprecated
    public Mono<Long> zcount(String key, double min, double max) {
        return null;
    }

    /**
     * Count the members in a sorted set with scores within the given values.
     *
     * @param key the key.
     * @param min min score.
     * @param max max score.
     * @return Long integer-reply the number of elements in the specified score range.
     * @deprecated Use {@link #zcount(Object, Range)}.
     */
    @Override
    @Deprecated
    public Mono<Long> zcount(String key, String min, String max) {
        return null;
    }

    /**
     * Count the members in a sorted set with scores within the given {@link Range}.
     *
     * @param key   the key.
     * @param range the range.
     * @return Long integer-reply the number of elements in the specified score range.
     * @since 4.3
     */
    @Override
    public Mono<Long> zcount(String key, Range<? extends Number> range) {
        return null;
    }

    @Override
    public Flux<String> zdiff(String... strings) {
        return null;
    }

    @Override
    public Mono<Long> zdiffstore(String s, String... strings) {
        return null;
    }

    @Override
    public Flux<ScoredValue<String>> zdiffWithScores(String... strings) {
        return null;
    }

    /**
     * Increment the score of a member in a sorted set.
     *
     * @param key    the key.
     * @param amount the increment type: long.
     * @param member the member type: value.
     * @return Double bulk-string-reply the new score of {@code member} (a double precision floating point number), represented
     * as string.
     */
    @Override
    public Mono<Double> zincrby(String key, double amount, String member) {
        return null;
    }

    @Override
    public Flux<String> zinter(String... strings) {
        return null;
    }

    @Override
    public Flux<String> zinter(ZAggregateArgs zAggregateArgs, String... strings) {
        return null;
    }

    @Override
    public Mono<Long> zintercard(String... strings) {
        return null;
    }

    @Override
    public Mono<Long> zintercard(long l, String... strings) {
        return null;
    }

    @Override
    public Flux<ScoredValue<String>> zinterWithScores(ZAggregateArgs zAggregateArgs, String... strings) {
        return null;
    }

    @Override
    public Flux<ScoredValue<String>> zinterWithScores(String... strings) {
        return null;
    }

    /**
     * Intersect multiple sorted sets and store the resulting sorted set in a new key.
     *
     * @param destination the destination.
     * @param keys        the keys.
     * @return Long integer-reply the number of elements in the resulting sorted set at {@code destination}.
     */
    @Override
    public Mono<Long> zinterstore(String destination, String... keys) {
        return null;
    }

    /**
     * Intersect multiple sorted sets and store the resulting sorted set in a new key.
     *
     * @param destination the destination.
     * @param storeArgs   the storeArgs.
     * @param keys        the keys.
     * @return Long integer-reply the number of elements in the resulting sorted set at {@code destination}.
     */
    @Override
    public Mono<Long> zinterstore(String destination, ZStoreArgs storeArgs, String... keys) {
        return null;
    }

    /**
     * Count the number of members in a sorted set between a given lexicographical range.
     *
     * @param key the key.
     * @param min min score.
     * @param max max score.
     * @return Long integer-reply the number of elements in the specified score range.
     * @deprecated Use {@link #zlexcount(Object, Range)}.
     */
    @Override
    @Deprecated
    public Mono<Long> zlexcount(String key, String min, String max) {
        return null;
    }

    /**
     * Count the number of members in a sorted set between a given lexicographical range.
     *
     * @param key   the key.
     * @param range the range.
     * @return Long integer-reply the number of elements in the specified score range.
     * @since 4.3
     */
    @Override
    public Mono<Long> zlexcount(String key, Range<? extends String> range) {
        return null;
    }

    @Override
    public Mono<List<Double>> zmscore(String s, String... strings) {
        return null;
    }

    @Override
    public Mono<KeyValue<String, ScoredValue<String>>> zmpop(ZPopArgs zPopArgs, String... strings) {
        return null;
    }

    @Override
    public Mono<KeyValue<String, List<ScoredValue<String>>>> zmpop(int i, ZPopArgs zPopArgs, String... strings) {
        return null;
    }

    /**
     * Removes and returns up to count members with the lowest scores in the sorted set stored at key.
     *
     * @param key the key.
     * @return ScoredValue&lt;V&gt; the removed element.
     * @since 5.1
     */
    @Override
    public Mono<ScoredValue<String>> zpopmin(String key) {
        return null;
    }

    /**
     * Removes and returns up to count members with the lowest scores in the sorted set stored at key.
     *
     * @param key   the key.
     * @param count the number of elements to return.
     * @return ScoredValue&lt;V&gt; array-reply list of popped scores and elements.
     * @since 5.1
     */
    @Override
    public Flux<ScoredValue<String>> zpopmin(String key, long count) {
        return null;
    }

    /**
     * Removes and returns up to count members with the highest scores in the sorted set stored at key.
     *
     * @param key the key.
     * @return ScoredValue&lt;V&gt; the removed element.
     * @since 5.1
     */
    @Override
    public Mono<ScoredValue<String>> zpopmax(String key) {
        return null;
    }

    /**
     * Removes and returns up to count members with the highest scores in the sorted set stored at key.
     *
     * @param key   the key.
     * @param count the number of elements to return.
     * @return ScoredValue&lt;V&gt; array-reply list of popped scores and elements.
     * @since 5.1
     */
    @Override
    public Flux<ScoredValue<String>> zpopmax(String key, long count) {
        return null;
    }

    @Override
    public Mono<String> zrandmember(String s) {
        return null;
    }

    @Override
    public Flux<String> zrandmember(String s, long l) {
        return null;
    }

    @Override
    public Mono<ScoredValue<String>> zrandmemberWithScores(String s) {
        return null;
    }

    @Override
    public Flux<ScoredValue<String>> zrandmemberWithScores(String s, long l) {
        return null;
    }

    /**
     * Return a range of members in a sorted set, by index.
     *
     * @param key   the key.
     * @param start the start.
     * @param stop  the stop.
     * @return V array-reply list of elements in the specified range.
     */
    @Override
    public Flux<String> zrange(String key, long start, long stop) {
        return null;
    }

    /**
     * Return a range of members in a sorted set, by index.
     *
     * @param channel streaming channel that receives a call for every value.
     * @param key     the key.
     * @param start   the start.
     * @param stop    the stop.
     * @return Long count of elements in the specified range.
     */
    @Override
    @Deprecated
    public Mono<Long> zrange(ValueStreamingChannel<String> channel, String key, long start, long stop) {
        return null;
    }

    /**
     * Return a range of members with scores in a sorted set, by index.
     *
     * @param key   the key.
     * @param start the start.
     * @param stop  the stop.
     * @return V array-reply list of elements in the specified range.
     */
    @Override
    public Flux<ScoredValue<String>> zrangeWithScores(String key, long start, long stop) {
        return null;
    }

    /**
     * Stream over a range of members with scores in a sorted set, by index.
     *
     * @param channel streaming channel that receives a call for every value.
     * @param key     the key.
     * @param start   the start.
     * @param stop    the stop.
     * @return Long count of elements in the specified range.
     */
    @Override
    @Deprecated
    public Mono<Long> zrangeWithScores(ScoredValueStreamingChannel<String> channel, String key, long start, long stop) {
        return null;
    }

    /**
     * Return a range of members in a sorted set, by lexicographical range.
     *
     * @param key the key.
     * @param min min score.
     * @param max max score.
     * @return V array-reply list of elements in the specified range.
     * @deprecated Use {@link #zrangebylex(Object, Range)}.
     */
    @Override
    @Deprecated
    public Flux<String> zrangebylex(String key, String min, String max) {
        return null;
    }

    /**
     * Return a range of members in a sorted set, by lexicographical range.
     *
     * @param key   the key.
     * @param range the range.
     * @return V array-reply list of elements in the specified range.
     * @since 4.3
     */
    @Override
    public Flux<String> zrangebylex(String key, Range<? extends String> range) {
        return null;
    }

    /**
     * Return a range of members in a sorted set, by lexicographical range.
     *
     * @param key    the key.
     * @param min    min score.
     * @param max    max score.
     * @param offset the offset.
     * @param count  the count.
     * @return V array-reply list of elements in the specified range.
     * @deprecated Use {@link #zrangebylex(Object, Range)}.
     */
    @Override
    @Deprecated
    public Flux<String> zrangebylex(String key, String min, String max, long offset, long count) {
        return null;
    }

    /**
     * Return a range of members in a sorted set, by lexicographical range.
     *
     * @param key   the key.
     * @param range the range.
     * @param limit the limit.
     * @return V array-reply list of elements in the specified range.
     * @since 4.3
     */
    @Override
    public Flux<String> zrangebylex(String key, Range<? extends String> range, Limit limit) {
        return null;
    }

    /**
     * Return a range of members in a sorted set, by score.
     *
     * @param key the key.
     * @param min min score.
     * @param max max score.
     * @return V array-reply list of elements in the specified score range.
     * @deprecated Use {@link #zrangebyscore(Object, Range)}.
     */
    @Override
    @Deprecated
    public Flux<String> zrangebyscore(String key, double min, double max) {
        return null;
    }

    /**
     * Return a range of members in a sorted set, by score.
     *
     * @param key the key.
     * @param min min score.
     * @param max max score.
     * @return V array-reply list of elements in the specified score range.
     * @deprecated Use {@link #zrangebyscore(Object, Range)}.
     */
    @Override
    @Deprecated
    public Flux<String> zrangebyscore(String key, String min, String max) {
        return null;
    }

    /**
     * Return a range of members in a sorted set, by score.
     *
     * @param key   the key.
     * @param range the range.
     * @return V array-reply list of elements in the specified score range.
     * @since 4.3
     */
    @Override
    public Flux<String> zrangebyscore(String key, Range<? extends Number> range) {
        return null;
    }

    /**
     * Return a range of members in a sorted set, by score.
     *
     * @param key    the key.
     * @param min    min score.
     * @param max    max score.
     * @param offset the offset.
     * @param count  the count.
     * @return V array-reply list of elements in the specified score range.
     * @deprecated Use {@link #zrangebyscore(Object, Range, Limit)}.
     */
    @Override
    @Deprecated
    public Flux<String> zrangebyscore(String key, double min, double max, long offset, long count) {
        return null;
    }

    /**
     * Return a range of members in a sorted set, by score.
     *
     * @param key    the key.
     * @param min    min score.
     * @param max    max score.
     * @param offset the offset.
     * @param count  the count.
     * @return V array-reply list of elements in the specified score range.
     * @deprecated Use {@link #zrangebyscore(Object, Range, Limit)}.
     */
    @Override
    @Deprecated
    public Flux<String> zrangebyscore(String key, String min, String max, long offset, long count) {
        return null;
    }

    /**
     * Return a range of members in a sorted set, by score.
     *
     * @param key   the key.
     * @param range the range.
     * @param limit the limit.
     * @return V array-reply list of elements in the specified score range.
     * @since 4.3
     */
    @Override
    public Flux<String> zrangebyscore(String key, Range<? extends Number> range, Limit limit) {
        return null;
    }

    /**
     * Stream over a range of members in a sorted set, by score.
     *
     * @param channel streaming channel that receives a call for every value.
     * @param key     the key.
     * @param min     min score.
     * @param max     max score.
     * @return Long count of elements in the specified score range.
     * @deprecated Use {@link #zrangebyscore(ValueStreamingChannel, Object, Range)}.
     */
    @Override
    @Deprecated
    public Mono<Long> zrangebyscore(ValueStreamingChannel<String> channel, String key, double min, double max) {
        return null;
    }

    /**
     * Stream over a range of members in a sorted set, by score.
     *
     * @param channel streaming channel that receives a call for every value.
     * @param key     the key.
     * @param min     min score.
     * @param max     max score.
     * @return Long count of elements in the specified score range.
     * @deprecated Use {@link #zrangebyscore(ValueStreamingChannel, Object, Range)}.
     */
    @Override
    @Deprecated
    public Mono<Long> zrangebyscore(ValueStreamingChannel<String> channel, String key, String min, String max) {
        return null;
    }

    /**
     * Stream over a range of members in a sorted set, by score.
     *
     * @param channel streaming channel that receives a call for every value.
     * @param key     the key.
     * @param range   the range.
     * @return Long count of elements in the specified score range.
     * @since 4.3
     */
    @Override
    public Mono<Long> zrangebyscore(ValueStreamingChannel<String> channel, String key, Range<? extends Number> range) {
        return null;
    }

    /**
     * Stream over range of members in a sorted set, by score.
     *
     * @param channel streaming channel that receives a call for every value.
     * @param key     the key.
     * @param min     min score.
     * @param max     max score.
     * @param offset  the offset.
     * @param count   the count.
     * @return Long count of elements in the specified score range.
     * @deprecated Use {@link #zrangebyscore(ValueStreamingChannel, Object, Range, Limit limit)}.
     */
    @Override
    @Deprecated
    public Mono<Long> zrangebyscore(ValueStreamingChannel<String> channel, String key, double min, double max, long offset, long count) {
        return null;
    }

    /**
     * Stream over a range of members in a sorted set, by score.
     *
     * @param channel streaming channel that receives a call for every value.
     * @param key     the key.
     * @param min     min score.
     * @param max     max score.
     * @param offset  the offset.
     * @param count   the count.
     * @return Long count of elements in the specified score range.
     * @deprecated Use {@link #zrangebyscore(ValueStreamingChannel, Object, Range, Limit limit)}.
     */
    @Override
    @Deprecated
    public Mono<Long> zrangebyscore(ValueStreamingChannel<String> channel, String key, String min, String max, long offset, long count) {
        return null;
    }

    /**
     * Stream over a range of members in a sorted set, by score.
     *
     * @param channel streaming channel that receives a call for every value.
     * @param key     the key.
     * @param range   the range.
     * @param limit   the limit.
     * @return Long count of elements in the specified score range.
     * @since 4.3
     */
    @Override
    public Mono<Long> zrangebyscore(ValueStreamingChannel<String> channel, String key, Range<? extends Number> range, Limit limit) {
        return null;
    }

    /**
     * Return a range of members with score in a sorted set, by score.
     *
     * @param key the key.
     * @param min min score.
     * @param max max score.
     * @return ScoredValue&lt;V&gt; array-reply list of elements in the specified score range.
     * @deprecated Use {@link #zrangebyscoreWithScores(Object, Range)}.
     */
    @Override
    @Deprecated
    public Flux<ScoredValue<String>> zrangebyscoreWithScores(String key, double min, double max) {
        return null;
    }

    /**
     * Return a range of members with score in a sorted set, by score.
     *
     * @param key the key.
     * @param min min score.
     * @param max max score.
     * @return ScoredValue&lt;V&gt; array-reply list of elements in the specified score range.
     * @deprecated Use {@link #zrangebyscoreWithScores(Object, Range)}.
     */
    @Override
    @Deprecated
    public Flux<ScoredValue<String>> zrangebyscoreWithScores(String key, String min, String max) {
        return null;
    }

    /**
     * Return a range of members with score in a sorted set, by score.
     *
     * @param key   the key.
     * @param range the range.
     * @return ScoredValue&lt;V&gt; array-reply list of elements in the specified score range.
     * @since 4.3
     */
    @Override
    public Flux<ScoredValue<String>> zrangebyscoreWithScores(String key, Range<? extends Number> range) {
        return null;
    }

    /**
     * Return a range of members with score in a sorted set, by score.
     *
     * @param key    the key.
     * @param min    min score.
     * @param max    max score.
     * @param offset the offset.
     * @param count  the count.
     * @return ScoredValue&lt;V&gt; array-reply list of elements in the specified score range.
     * @deprecated Use {@link #zrangebyscoreWithScores(Object, Range, Limit limit)}.
     */
    @Override
    @Deprecated
    public Flux<ScoredValue<String>> zrangebyscoreWithScores(String key, double min, double max, long offset, long count) {
        return null;
    }

    /**
     * Return a range of members with score in a sorted set, by score.
     *
     * @param key    the key.
     * @param min    min score.
     * @param max    max score.
     * @param offset the offset.
     * @param count  the count.
     * @return ScoredValue&lt;V&gt; array-reply list of elements in the specified score range.
     * @deprecated Use {@link #zrangebyscoreWithScores(Object, Range, Limit)}.
     */
    @Override
    @Deprecated
    public Flux<ScoredValue<String>> zrangebyscoreWithScores(String key, String min, String max, long offset, long count) {
        return null;
    }

    /**
     * Return a range of members with score in a sorted set, by score.
     *
     * @param key   the key.
     * @param range the range.
     * @param limit the limit.
     * @return ScoredValue&lt;V&gt; array-reply list of elements in the specified score range.
     * @since 4.3
     */
    @Override
    public Flux<ScoredValue<String>> zrangebyscoreWithScores(String key, Range<? extends Number> range, Limit limit) {
        return null;
    }

    /**
     * Stream over a range of members with scores in a sorted set, by score.
     *
     * @param channel streaming channel that receives a call for every scored value.
     * @param key     the key.
     * @param min     min score.
     * @param max     max score.
     * @return Long count of elements in the specified score range.
     * @deprecated Use {@link #zrangebyscoreWithScores(ScoredValueStreamingChannel, Object, Range)}.
     */
    @Override
    @Deprecated
    public Mono<Long> zrangebyscoreWithScores(ScoredValueStreamingChannel<String> channel, String key, double min, double max) {
        return null;
    }

    /**
     * Stream over a range of members with scores in a sorted set, by score.
     *
     * @param channel streaming channel that receives a call for every scored value.
     * @param key     the key.
     * @param min     min score.
     * @param max     max score.
     * @return Long count of elements in the specified score range.
     * @deprecated Use {@link #zrangebyscoreWithScores(ScoredValueStreamingChannel, Object, Range)}.
     */
    @Override
    @Deprecated
    public Mono<Long> zrangebyscoreWithScores(ScoredValueStreamingChannel<String> channel, String key, String min, String max) {
        return null;
    }

    /**
     * Stream over a range of members with scores in a sorted set, by score.
     *
     * @param channel streaming channel that receives a call for every scored value.
     * @param key     the key.
     * @param range   the range.
     * @return Long count of elements in the specified score range.
     * @since 4.3
     */
    @Override
    public Mono<Long> zrangebyscoreWithScores(ScoredValueStreamingChannel<String> channel, String key, Range<? extends Number> range) {
        return null;
    }

    /**
     * Stream over a range of members with scores in a sorted set, by score.
     *
     * @param channel streaming channel that receives a call for every scored value.
     * @param key     the key.
     * @param min     min score.
     * @param max     max score.
     * @param offset  the offset.
     * @param count   the count.
     * @return Long count of elements in the specified score range.
     * @deprecated Use {@link #zrangebyscoreWithScores(ScoredValueStreamingChannel, Object, Range, Limit limit)}.
     */
    @Override
    @Deprecated
    public Mono<Long> zrangebyscoreWithScores(ScoredValueStreamingChannel<String> channel, String key, double min, double max, long offset, long count) {
        return null;
    }

    /**
     * Stream over a range of members with scores in a sorted set, by score.
     *
     * @param channel streaming channel that receives a call for every scored value.
     * @param key     the key.
     * @param min     min score.
     * @param max     max score.
     * @param offset  the offset.
     * @param count   the count.
     * @return Long count of elements in the specified score range.
     * @deprecated Use {@link #zrangebyscoreWithScores(ScoredValueStreamingChannel, Object, Range, Limit limit)}.
     */
    @Override
    @Deprecated
    public Mono<Long> zrangebyscoreWithScores(ScoredValueStreamingChannel<String> channel, String key, String min, String max, long offset, long count) {
        return null;
    }

    /**
     * Stream over a range of members with scores in a sorted set, by score.
     *
     * @param channel streaming channel that receives a call for every scored value.
     * @param key     the key.
     * @param range   the range.
     * @param limit   the limit.
     * @return Long count of elements in the specified score range.
     * @since 4.3
     */
    @Override
    public Mono<Long> zrangebyscoreWithScores(ScoredValueStreamingChannel<String> channel, String key, Range<? extends Number> range, Limit limit) {
        return null;
    }

    @Override
    public Mono<Long> zrangestore(String s, String k1, Range<Long> range) {
        return null;
    }

    @Override
    public Mono<Long> zrangestorebylex(String s, String k1, Range<? extends String> range, Limit limit) {
        return null;
    }

    @Override
    public Mono<Long> zrangestorebyscore(String s, String k1, Range<? extends Number> range, Limit limit) {
        return null;
    }

    /**
     * Determine the index of a member in a sorted set.
     *
     * @param key    the key.
     * @param member the member type: value.
     * @return Long integer-reply the rank of {@code member}. If {@code member} does not exist in the sorted set or {@code key}
     * does not exist,.
     */
    @Override
    public Mono<Long> zrank(String key, String member) {
        return null;
    }

    @Override
    public Mono<ScoredValue<Long>> zrankWithScore(String s, String s2) {
        return null;
    }

    /**
     * Remove one or more members from a sorted set.
     *
     * @param key     the key.
     * @param members the member type: value.
     * @return Long integer-reply specifically:
     * <p>
     * The number of members removed from the sorted set, not including non existing members.
     */
    @Override
    public Mono<Long> zrem(String key, String... members) {
        return null;
    }

    /**
     * Remove all members in a sorted set between the given lexicographical range.
     *
     * @param key the key.
     * @param min min score.
     * @param max max score.
     * @return Long integer-reply the number of elements removed.
     * @deprecated Use {@link #zremrangebylex(Object, Range)}.
     */
    @Override
    @Deprecated
    public Mono<Long> zremrangebylex(String key, String min, String max) {
        return null;
    }

    /**
     * Remove all members in a sorted set between the given lexicographical range.
     *
     * @param key   the key.
     * @param range the range.
     * @return Long integer-reply the number of elements removed.
     * @since 4.3
     */
    @Override
    public Mono<Long> zremrangebylex(String key, Range<? extends String> range) {
        return null;
    }

    /**
     * Remove all members in a sorted set within the given indexes.
     *
     * @param key   the key.
     * @param start the start type: long.
     * @param stop  the stop type: long.
     * @return Long integer-reply the number of elements removed.
     */
    @Override
    public Mono<Long> zremrangebyrank(String key, long start, long stop) {
        return null;
    }

    /**
     * Remove all members in a sorted set within the given scores.
     *
     * @param key the key.
     * @param min min score.
     * @param max max score.
     * @return Long integer-reply the number of elements removed.
     * @deprecated Use {@link #zremrangebyscore(Object, Range)}.
     */
    @Override
    @Deprecated
    public Mono<Long> zremrangebyscore(String key, double min, double max) {
        return null;
    }

    /**
     * Remove all members in a sorted set within the given scores.
     *
     * @param key the key.
     * @param min min score.
     * @param max max score.
     * @return Long integer-reply the number of elements removed.
     * @deprecated Use {@link #zremrangebyscore(Object, Range)}.
     */
    @Override
    @Deprecated
    public Mono<Long> zremrangebyscore(String key, String min, String max) {
        return null;
    }

    /**
     * Remove all members in a sorted set within the given scores.
     *
     * @param key   the key.
     * @param range the range.
     * @return Long integer-reply the number of elements removed.
     * @since 4.3
     */
    @Override
    public Mono<Long> zremrangebyscore(String key, Range<? extends Number> range) {
        return null;
    }

    /**
     * Return a range of members in a sorted set, by index, with scores ordered from high to low.
     *
     * @param key   the key.
     * @param start the start.
     * @param stop  the stop.
     * @return V array-reply list of elements in the specified range.
     */
    @Override
    public Flux<String> zrevrange(String key, long start, long stop) {
        return null;
    }

    /**
     * Stream over a range of members in a sorted set, by index, with scores ordered from high to low.
     *
     * @param channel streaming channel that receives a call for every scored value.
     * @param key     the key.
     * @param start   the start.
     * @param stop    the stop.
     * @return Long count of elements in the specified range.
     */
    @Override
    public Mono<Long> zrevrange(ValueStreamingChannel<String> channel, String key, long start, long stop) {
        return null;
    }

    /**
     * Return a range of members with scores in a sorted set, by index, with scores ordered from high to low.
     *
     * @param key   the key.
     * @param start the start.
     * @param stop  the stop.
     * @return V array-reply list of elements in the specified range.
     */
    @Override
    public Flux<ScoredValue<String>> zrevrangeWithScores(String key, long start, long stop) {
        return null;
    }

    /**
     * Stream over a range of members with scores in a sorted set, by index, with scores ordered from high to low.
     *
     * @param channel streaming channel that receives a call for every scored value.
     * @param key     the key.
     * @param start   the start.
     * @param stop    the stop.
     * @return Long count of elements in the specified range.
     */
    @Override
    public Mono<Long> zrevrangeWithScores(ScoredValueStreamingChannel<String> channel, String key, long start, long stop) {
        return null;
    }

    /**
     * Return a range of members in a sorted set, by lexicographical range ordered from high to low.
     *
     * @param key   the key.
     * @param range the range.
     * @return V array-reply list of elements in the specified score range.
     * @since 4.3
     */
    @Override
    public Flux<String> zrevrangebylex(String key, Range<? extends String> range) {
        return null;
    }

    /**
     * Return a range of members in a sorted set, by lexicographical range ordered from high to low.
     *
     * @param key   the key.
     * @param range the range.
     * @param limit the limit.
     * @return V array-reply list of elements in the specified score range.
     * @since 4.3
     */
    @Override
    public Flux<String> zrevrangebylex(String key, Range<? extends String> range, Limit limit) {
        return null;
    }

    /**
     * Return a range of members in a sorted set, by score, with scores ordered from high to low.
     *
     * @param key the key.
     * @param max max score.
     * @param min min score.
     * @return V array-reply list of elements in the specified score range.
     * @deprecated Use {@link #zrevrangebyscore(Object, Range)}.
     */
    @Override
    @Deprecated
    public Flux<String> zrevrangebyscore(String key, double max, double min) {
        return null;
    }

    /**
     * Return a range of members in a sorted set, by score, with scores ordered from high to low.
     *
     * @param key the key.
     * @param max max score.
     * @param min min score.
     * @return V array-reply list of elements in the specified score range.
     * @deprecated Use {@link #zrevrangebyscore(Object, Range)}.
     */
    @Override
    @Deprecated
    public Flux<String> zrevrangebyscore(String key, String max, String min) {
        return null;
    }

    /**
     * Return a range of members in a sorted set, by score, with scores ordered from high to low.
     *
     * @param key   the key.
     * @param range the range.
     * @return V array-reply list of elements in the specified score range.
     * @since 4.3
     */
    @Override
    public Flux<String> zrevrangebyscore(String key, Range<? extends Number> range) {
        return null;
    }

    /**
     * Return a range of members in a sorted set, by score, with scores ordered from high to low.
     *
     * @param key    the key.
     * @param max    max score.
     * @param min    min score.
     * @param offset the withscores.
     * @param count  the null.
     * @return V array-reply list of elements in the specified score range.
     * @deprecated Use {@link #zrevrangebyscore(Object, Range, Limit)}.
     */
    @Override
    @Deprecated
    public Flux<String> zrevrangebyscore(String key, double max, double min, long offset, long count) {
        return null;
    }

    /**
     * Return a range of members in a sorted set, by score, with scores ordered from high to low.
     *
     * @param key    the key.
     * @param max    max score.
     * @param min    min score.
     * @param offset the offset.
     * @param count  the count.
     * @return V array-reply list of elements in the specified score range.
     * @deprecated Use {@link #zrevrangebyscore(Object, Range, Limit)}.
     */
    @Override
    @Deprecated
    public Flux<String> zrevrangebyscore(String key, String max, String min, long offset, long count) {
        return null;
    }

    /**
     * Return a range of members in a sorted set, by score, with scores ordered from high to low.
     *
     * @param key   the key.
     * @param range the range.
     * @param limit the limit.
     * @return V array-reply list of elements in the specified score range.
     * @since 4.3
     */
    @Override
    public Flux<String> zrevrangebyscore(String key, Range<? extends Number> range, Limit limit) {
        return null;
    }

    /**
     * Stream over a range of members in a sorted set, by score, with scores ordered from high to low.
     *
     * @param channel streaming channel that receives a call for every value.
     * @param key     the key.
     * @param max     max score.
     * @param min     min score.
     * @return Long count of elements in the specified range.
     * @deprecated Use {@link #zrevrangebyscore(Object, Range)}.
     */
    @Override
    @Deprecated
    public Mono<Long> zrevrangebyscore(ValueStreamingChannel<String> channel, String key, double max, double min) {
        return null;
    }

    /**
     * Stream over a range of members in a sorted set, by score, with scores ordered from high to low.
     *
     * @param channel streaming channel that receives a call for every value.
     * @param key     the key.
     * @param max     max score.
     * @param min     min score.
     * @return Long count of elements in the specified range.
     * @deprecated Use {@link #zrevrangebyscore(Object, Range)}.
     */
    @Override
    @Deprecated
    public Mono<Long> zrevrangebyscore(ValueStreamingChannel<String> channel, String key, String max, String min) {
        return null;
    }

    /**
     * Stream over a range of members in a sorted set, by score, with scores ordered from high to low.
     *
     * @param channel streaming channel that receives a call for every value.
     * @param key     the key.
     * @param range   the range.
     * @return Long count of elements in the specified range.
     * @since 4.3
     */
    @Override
    public Mono<Long> zrevrangebyscore(ValueStreamingChannel<String> channel, String key, Range<? extends Number> range) {
        return null;
    }

    /**
     * Stream over a range of members in a sorted set, by score, with scores ordered from high to low.
     *
     * @param channel streaming channel that receives a call for every value.
     * @param key     the key.
     * @param max     max score.
     * @param min     min score.
     * @param offset  the offset.
     * @param count   the count.
     * @return Long count of elements in the specified range.
     * @deprecated Use {@link #zrevrangebyscoreWithScores(Object, Range, Limit)}.
     */
    @Override
    @Deprecated
    public Mono<Long> zrevrangebyscore(ValueStreamingChannel<String> channel, String key, double max, double min, long offset, long count) {
        return null;
    }

    /**
     * Stream over a range of members in a sorted set, by score, with scores ordered from high to low.
     *
     * @param channel streaming channel that receives a call for every value.
     * @param key     the key.
     * @param max     max score.
     * @param min     min score.
     * @param offset  the offset.
     * @param count   the count.
     * @return Long count of elements in the specified range.
     * @deprecated Use {@link #zrevrangebyscoreWithScores(Object, Range, Limit)}.
     */
    @Override
    @Deprecated
    public Mono<Long> zrevrangebyscore(ValueStreamingChannel<String> channel, String key, String max, String min, long offset, long count) {
        return null;
    }

    /**
     * Stream over a range of members in a sorted set, by score, with scores ordered from high to low.
     *
     * @param channel streaming channel that receives a call for every value.
     * @param key     the key.
     * @param range   the range.
     * @param limit   the limit.
     * @return Long count of elements in the specified range.
     * @since 4.3
     */
    @Override
    public Mono<Long> zrevrangebyscore(ValueStreamingChannel<String> channel, String key, Range<? extends Number> range, Limit limit) {
        return null;
    }

    /**
     * Return a range of members with scores in a sorted set, by score, with scores ordered from high to low.
     *
     * @param key the key.
     * @param max max score.
     * @param min min score.
     * @return V array-reply list of elements in the specified score range.
     * @deprecated Use {@link #zrevrangebyscoreWithScores(Object, Range)}.
     */
    @Override
    @Deprecated
    public Flux<ScoredValue<String>> zrevrangebyscoreWithScores(String key, double max, double min) {
        return null;
    }

    /**
     * Return a range of members with scores in a sorted set, by score, with scores ordered from high to low.
     *
     * @param key the key.
     * @param max max score.
     * @param min min score.
     * @return ScoredValue&lt;V&gt; array-reply list of elements in the specified score range.
     * @deprecated Use {@link #zrevrangebyscoreWithScores(Object, Range)}.
     */
    @Override
    @Deprecated
    public Flux<ScoredValue<String>> zrevrangebyscoreWithScores(String key, String max, String min) {
        return null;
    }

    /**
     * Return a range of members with scores in a sorted set, by score, with scores ordered from high to low.
     *
     * @param key   the key.
     * @param range the range.
     * @return ScoredValue&lt;V&gt; array-reply list of elements in the specified score range.
     * @since 4.3
     */
    @Override
    public Flux<ScoredValue<String>> zrevrangebyscoreWithScores(String key, Range<? extends Number> range) {
        return null;
    }

    /**
     * Return a range of members with scores in a sorted set, by score, with scores ordered from high to low.
     *
     * @param key    the key.
     * @param max    max score.
     * @param min    min score.
     * @param offset the offset.
     * @param count  the count.
     * @return ScoredValue&lt;V&gt; array-reply list of elements in the specified score range.
     * @deprecated Use {@link #zrevrangebyscoreWithScores(Object, Range, Limit)}.
     */
    @Override
    @Deprecated
    public Flux<ScoredValue<String>> zrevrangebyscoreWithScores(String key, double max, double min, long offset, long count) {
        return null;
    }

    /**
     * Return a range of members with scores in a sorted set, by score, with scores ordered from high to low.
     *
     * @param key    the key.
     * @param max    max score.
     * @param min    min score.
     * @param offset the offset.
     * @param count  the count.
     * @return V array-reply list of elements in the specified score range.
     * @deprecated Use {@link #zrevrangebyscoreWithScores(Object, Range, Limit)}.
     */
    @Override
    @Deprecated
    public Flux<ScoredValue<String>> zrevrangebyscoreWithScores(String key, String max, String min, long offset, long count) {
        return null;
    }

    /**
     * Return a range of members with scores in a sorted set, by score, with scores ordered from high to low.
     *
     * @param key   the key.
     * @param range the range.
     * @param limit limit.
     * @return V array-reply list of elements in the specified score range.
     * @since 4.3
     */
    @Override
    public Flux<ScoredValue<String>> zrevrangebyscoreWithScores(String key, Range<? extends Number> range, Limit limit) {
        return null;
    }

    /**
     * Stream over a range of members with scores in a sorted set, by score, with scores ordered from high to low.
     *
     * @param channel streaming channel that receives a call for every scored value.
     * @param key     the key.
     * @param max     max score.
     * @param min     min score.
     * @return Long count of elements in the specified range.
     * @deprecated Use {@link #zrevrangebyscoreWithScores(ScoredValueStreamingChannel, Object, Range)}.
     */
    @Override
    @Deprecated
    public Mono<Long> zrevrangebyscoreWithScores(ScoredValueStreamingChannel<String> channel, String key, double max, double min) {
        return null;
    }

    /**
     * Stream over a range of members with scores in a sorted set, by score, with scores ordered from high to low.
     *
     * @param channel streaming channel that receives a call for every scored value.
     * @param key     the key.
     * @param max     max score.
     * @param min     min score.
     * @return Long count of elements in the specified range.
     * @deprecated Use {@link #zrevrangebyscoreWithScores(ScoredValueStreamingChannel, Object, Range)}.
     */
    @Override
    @Deprecated
    public Mono<Long> zrevrangebyscoreWithScores(ScoredValueStreamingChannel<String> channel, String key, String max, String min) {
        return null;
    }

    /**
     * Stream over a range of members with scores in a sorted set, by score, with scores ordered from high to low.
     *
     * @param channel streaming channel that receives a call for every scored value.
     * @param key     the key.
     * @param range   the range.
     * @return Long count of elements in the specified range.
     */
    @Override
    public Mono<Long> zrevrangebyscoreWithScores(ScoredValueStreamingChannel<String> channel, String key, Range<? extends Number> range) {
        return null;
    }

    /**
     * Stream over a range of members with scores in a sorted set, by score, with scores ordered from high to low.
     *
     * @param channel streaming channel that receives a call for every scored value.
     * @param key     the key.
     * @param max     max score.
     * @param min     min score.
     * @param offset  the offset.
     * @param count   the count.
     * @return Long count of elements in the specified range.
     * @deprecated Use {@link #zrevrangebyscoreWithScores(ScoredValueStreamingChannel, Object, Range, Limit)}.
     */
    @Override
    @Deprecated
    public Mono<Long> zrevrangebyscoreWithScores(ScoredValueStreamingChannel<String> channel, String key, double max, double min, long offset, long count) {
        return null;
    }

    /**
     * Stream over a range of members with scores in a sorted set, by score, with scores ordered from high to low.
     *
     * @param channel streaming channel that receives a call for every scored value.
     * @param key     the key.
     * @param max     max score.
     * @param min     min score.
     * @param offset  the offset.
     * @param count   the count.
     * @return Long count of elements in the specified range.
     * @deprecated Use {@link #zrevrangebyscoreWithScores(ScoredValueStreamingChannel, Object, Range, Limit)}.
     */
    @Override
    @Deprecated
    public Mono<Long> zrevrangebyscoreWithScores(ScoredValueStreamingChannel<String> channel, String key, String max, String min, long offset, long count) {
        return null;
    }

    /**
     * Stream over a range of members with scores in a sorted set, by score, with scores ordered from high to low.
     *
     * @param channel streaming channel that receives a call for every scored value.
     * @param key     the key.
     * @param range   the range.
     * @param limit   the limit.
     * @return Long count of elements in the specified range.
     * @since 4.3
     */
    @Override
    public Mono<Long> zrevrangebyscoreWithScores(ScoredValueStreamingChannel<String> channel, String key, Range<? extends Number> range, Limit limit) {
        return null;
    }

    @Override
    public Mono<Long> zrevrangestore(String s, String k1, Range<Long> range) {
        return null;
    }

    @Override
    public Mono<Long> zrevrangestorebylex(String s, String k1, Range<? extends String> range, Limit limit) {
        return null;
    }

    @Override
    public Mono<Long> zrevrangestorebyscore(String s, String k1, Range<? extends Number> range, Limit limit) {
        return null;
    }

    /**
     * Determine the index of a member in a sorted set, with scores ordered from high to low.
     *
     * @param key    the key.
     * @param member the member type: value.
     * @return Long integer-reply the rank of {@code member}. If {@code member} does not exist in the sorted set or {@code key}
     * does not exist,.
     */
    @Override
    public Mono<Long> zrevrank(String key, String member) {
        return null;
    }

    @Override
    public Mono<ScoredValue<Long>> zrevrankWithScore(String s, String s2) {
        return null;
    }

    /**
     * Incrementally iterate sorted sets elements and associated scores.
     *
     * @param key the key.
     * @return ScoredValueScanCursor&lt;V&gt; scan cursor.
     */
    @Override
    public Mono<ScoredValueScanCursor<String>> zscan(String key) {
        return null;
    }

    /**
     * Incrementally iterate sorted sets elements and associated scores.
     *
     * @param key      the key.
     * @param scanArgs scan arguments.
     * @return ScoredValueScanCursor&lt;V&gt; scan cursor.
     */
    @Override
    public Mono<ScoredValueScanCursor<String>> zscan(String key, ScanArgs scanArgs) {
        return null;
    }

    /**
     * Incrementally iterate sorted sets elements and associated scores.
     *
     * @param key        the key.
     * @param scanCursor cursor to resume from a previous scan, must not be {@code null}.
     * @param scanArgs   scan arguments.
     * @return ScoredValueScanCursor&lt;V&gt; scan cursor.
     */
    @Override
    public Mono<ScoredValueScanCursor<String>> zscan(String key, ScanCursor scanCursor, ScanArgs scanArgs) {
        return null;
    }

    /**
     * Incrementally iterate sorted sets elements and associated scores.
     *
     * @param key        the key.
     * @param scanCursor cursor to resume from a previous scan, must not be {@code null}.
     * @return ScoredValueScanCursor&lt;V&gt; scan cursor.
     */
    @Override
    public Mono<ScoredValueScanCursor<String>> zscan(String key, ScanCursor scanCursor) {
        return null;
    }

    /**
     * Incrementally iterate sorted sets elements and associated scores.
     *
     * @param channel streaming channel that receives a call for every scored value.
     * @param key     the key.
     * @return StreamScanCursor scan cursor.
     */
    @Override
    public Mono<StreamScanCursor> zscan(ScoredValueStreamingChannel<String> channel, String key) {
        return null;
    }

    /**
     * Incrementally iterate sorted sets elements and associated scores.
     *
     * @param channel  streaming channel that receives a call for every scored value.
     * @param key      the key.
     * @param scanArgs scan arguments.
     * @return StreamScanCursor scan cursor.
     */
    @Override
    public Mono<StreamScanCursor> zscan(ScoredValueStreamingChannel<String> channel, String key, ScanArgs scanArgs) {
        return null;
    }

    /**
     * Incrementally iterate sorted sets elements and associated scores.
     *
     * @param channel    streaming channel that receives a call for every scored value.
     * @param key        the key.
     * @param scanCursor cursor to resume from a previous scan, must not be {@code null}.
     * @param scanArgs   scan arguments.
     * @return StreamScanCursor scan cursor.
     */
    @Override
    public Mono<StreamScanCursor> zscan(ScoredValueStreamingChannel<String> channel, String key, ScanCursor scanCursor, ScanArgs scanArgs) {
        return null;
    }

    /**
     * Incrementally iterate sorted sets elements and associated scores.
     *
     * @param channel    streaming channel that receives a call for every scored value.
     * @param key        the key.
     * @param scanCursor cursor to resume from a previous scan, must not be {@code null}.
     * @return StreamScanCursor scan cursor.
     */
    @Override
    public Mono<StreamScanCursor> zscan(ScoredValueStreamingChannel<String> channel, String key, ScanCursor scanCursor) {
        return null;
    }

    /**
     * Get the score associated with the given member in a sorted set.
     *
     * @param key    the key.
     * @param member the member type: value.
     * @return Double bulk-string-reply the score of {@code member} (a double precision floating point number), represented as
     * string.
     */
    @Override
    public Mono<Double> zscore(String key, String member) {
        return null;
    }

    @Override
    public Flux<String> zunion(String... strings) {
        return null;
    }

    @Override
    public Flux<String> zunion(ZAggregateArgs zAggregateArgs, String... strings) {
        return null;
    }

    @Override
    public Flux<ScoredValue<String>> zunionWithScores(ZAggregateArgs zAggregateArgs, String... strings) {
        return null;
    }

    @Override
    public Flux<ScoredValue<String>> zunionWithScores(String... strings) {
        return null;
    }

    /**
     * Add multiple sorted sets and store the resulting sorted set in a new key.
     *
     * @param destination destination key.
     * @param keys        source keys.
     * @return Long integer-reply the number of elements in the resulting sorted set at {@code destination}.
     */
    @Override
    public Mono<Long> zunionstore(String destination, String... keys) {
        return null;
    }

    /**
     * Add multiple sorted sets and store the resulting sorted set in a new key.
     *
     * @param destination the destination.
     * @param storeArgs   the storeArgs.
     * @param keys        the keys.
     * @return Long integer-reply the number of elements in the resulting sorted set at {@code destination}.
     */
    @Override
    public Mono<Long> zunionstore(String destination, ZStoreArgs storeArgs, String... keys) {
        return null;
    }

    /**
     * Acknowledge one or more messages as processed.
     *
     * @param key        the stream key.
     * @param group      name of the consumer group.
     * @param messageIds message Id's to acknowledge.
     * @return simple-reply the lenght of acknowledged messages.
     */
    @Override
    public Mono<Long> xack(String key, String group, String... messageIds) {
        return null;
    }

    /**
     * Append a message to the stream {@code key}.
     *
     * @param key  the stream key.
     * @param body message body.
     * @return simple-reply the message Id.
     */
    @Override
    public Mono<String> xadd(String key, Map<String, String> body) {
        return null;
    }

    /**
     * Append a message to the stream {@code key}.
     *
     * @param key  the stream key.
     * @param args
     * @param body message body.
     * @return simple-reply the message Id.
     */
    @Override
    public Mono<String> xadd(String key, XAddArgs args, Map<String, String> body) {
        return null;
    }

    /**
     * Append a message to the stream {@code key}.
     *
     * @param key           the stream key.
     * @param keysAndValues message body.
     * @return simple-reply the message Id.
     */
    @Override
    public Mono<String> xadd(String key, Object... keysAndValues) {
        return null;
    }

    /**
     * Append a message to the stream {@code key}.
     *
     * @param key           the stream key.
     * @param args
     * @param keysAndValues message body.
     * @return simple-reply the message Id.
     */
    @Override
    public Mono<String> xadd(String key, XAddArgs args, Object... keysAndValues) {
        return null;
    }

    @Override
    public Mono<ClaimedMessages<String, String>> xautoclaim(String s, XAutoClaimArgs<String> xAutoClaimArgs) {
        return null;
    }

    /**
     * Gets ownership of one or multiple messages in the Pending Entries List of a given stream consumer group.
     *
     * @param key         the stream key.
     * @param consumer    consumer identified by group name and consumer key.
     * @param minIdleTime
     * @param messageIds  message Id's to claim.
     * @return simple-reply the {@link StreamMessage}.
     */
    @Override
    public Flux<StreamMessage<String, String>> xclaim(String key, Consumer<String> consumer, long minIdleTime, String... messageIds) {
        return null;
    }

    /**
     * Gets ownership of one or multiple messages in the Pending Entries List of a given stream consumer group.
     * <p>
     * Note that setting the {@code JUSTID} flag (calling this method with {@link XClaimArgs#justid()}) suppresses the message
     * bode and {@link StreamMessage#getBody()} is {@code null}.
     *
     * @param key        the stream key.
     * @param consumer   consumer identified by group name and consumer key.
     * @param args
     * @param messageIds message Id's to claim.
     * @return simple-reply the {@link StreamMessage}.
     */
    @Override
    public Flux<StreamMessage<String, String>> xclaim(String key, Consumer<String> consumer, XClaimArgs args, String... messageIds) {
        return null;
    }

    /**
     * Removes the specified entries from the stream. Returns the number of items deleted, that may be different from the number
     * of IDs passed in case certain IDs do not exist.
     *
     * @param key        the stream key.
     * @param messageIds stream message Id's.
     * @return simple-reply number of removed entries.
     */
    @Override
    public Mono<Long> xdel(String key, String... messageIds) {
        return null;
    }

    /**
     * Create a consumer group.
     *
     * @param streamOffset name of the stream containing the offset to set.
     * @param group        name of the consumer group.
     * @return simple-reply {@code true} if successful.
     */
    @Override
    public Mono<String> xgroupCreate(XReadArgs.StreamOffset<String> streamOffset, String group) {
        return null;
    }

    /**
     * Create a consumer group.
     *
     * @param streamOffset name of the stream containing the offset to set.
     * @param group        name of the consumer group.
     * @param args
     * @return simple-reply {@code true} if successful.
     * @since 5.2
     */
    @Override
    public Mono<String> xgroupCreate(XReadArgs.StreamOffset<String> streamOffset, String group, XGroupCreateArgs args) {
        return null;
    }

    @Override
    public Mono<Boolean> xgroupCreateconsumer(String s, Consumer<String> consumer) {
        return null;
    }

    @Override
    public Mono<Long> xgroupDelconsumer(String s, Consumer<String> consumer) {
        return null;
    }

    /**
     * Destroy a consumer group.
     *
     * @param key   the stream key.
     * @param group name of the consumer group.
     * @return simple-reply {@code true} if successful.
     */
    @Override
    public Mono<Boolean> xgroupDestroy(String key, String group) {
        return null;
    }

    /**
     * Set the current {@code group} id.
     *
     * @param streamOffset name of the stream containing the offset to set.
     * @param group        name of the consumer group.
     * @return simple-reply OK.
     */
    @Override
    public Mono<String> xgroupSetid(XReadArgs.StreamOffset<String> streamOffset, String group) {
        return null;
    }

    /**
     * Retrieve information about the stream at {@code key}.
     *
     * @param key the stream key.
     * @return Object array-reply.
     * @since 5.2
     */
    @Override
    public Flux<Object> xinfoStream(String key) {
        return null;
    }

    /**
     * Retrieve information about the stream consumer groups at {@code key}.
     *
     * @param key the stream key.
     * @return Object array-reply.
     * @since 5.2
     */
    @Override
    public Flux<Object> xinfoGroups(String key) {
        return null;
    }

    /**
     * Retrieve information about consumer groups of group {@code group} and stream at {@code key}.
     *
     * @param key   the stream key.
     * @param group name of the consumer group.
     * @return Object array-reply.
     * @since 5.2
     */
    @Override
    public Flux<Object> xinfoConsumers(String key, String group) {
        return null;
    }

    /**
     * Get the length of a steam.
     *
     * @param key the stream key.
     * @return simple-reply the lenght of the stream.
     */
    @Override
    public Mono<Long> xlen(String key) {
        return null;
    }

    @Override
    public Mono<PendingMessages> xpending(String s, String k1) {
        return null;
    }

    @Override
    public Flux<PendingMessage> xpending(String s, String k1, Range<String> range, Limit limit) {
        return null;
    }

    @Override
    public Flux<PendingMessage> xpending(String s, Consumer<String> consumer, Range<String> range, Limit limit) {
        return null;
    }

    @Override
    public Flux<PendingMessage> xpending(String s, XPendingArgs<String> xPendingArgs) {
        return null;
    }

    /**
     * Read messages from a stream within a specific {@link Range}.
     *
     * @param key   the stream key.
     * @param range must not be {@code null}.
     * @return StreamMessage array-reply list with members of the resulting stream.
     */
    @Override
    public Flux<StreamMessage<String, String>> xrange(String key, Range<String> range) {
        return null;
    }

    /**
     * Read messages from a stream within a specific {@link Range} applying a {@link Limit}.
     *
     * @param key   the stream key.
     * @param range must not be {@code null}.
     * @param limit must not be {@code null}.
     * @return StreamMessage array-reply list with members of the resulting stream.
     */
    @Override
    public Flux<StreamMessage<String, String>> xrange(String key, Range<String> range, Limit limit) {
        return null;
    }

    /**
     * Read messages from one or more {@link StreamOffset}s.
     *
     * @param streams the streams to read from.
     * @return StreamMessage array-reply list with members of the resulting stream.
     */
    @Override
    public Flux<StreamMessage<String, String>> xread(XReadArgs.StreamOffset<String>... streams) {
        return null;
    }

    /**
     * Read messages from one or more {@link StreamOffset}s.
     *
     * @param args    read arguments.
     * @param streams the streams to read from.
     * @return StreamMessage array-reply list with members of the resulting stream.
     */
    @Override
    public Flux<StreamMessage<String, String>> xread(XReadArgs args, XReadArgs.StreamOffset<String>... streams) {
        return null;
    }

    /**
     * Read messages from one or more {@link StreamOffset}s using a consumer group.
     *
     * @param consumer consumer/group.
     * @param streams  the streams to read from.
     * @return StreamMessage array-reply list with members of the resulting stream.
     */
    @Override
    public Flux<StreamMessage<String, String>> xreadgroup(Consumer<String> consumer, XReadArgs.StreamOffset<String>... streams) {
        return null;
    }

    /**
     * Read messages from one or more {@link StreamOffset}s using a consumer group.
     *
     * @param consumer consumer/group.
     * @param args     read arguments.
     * @param streams  the streams to read from.
     * @return StreamMessage array-reply list with members of the resulting stream.
     */
    @Override
    public Flux<StreamMessage<String, String>> xreadgroup(Consumer<String> consumer, XReadArgs args, XReadArgs.StreamOffset<String>... streams) {
        return null;
    }

    /**
     * Read messages from a stream within a specific {@link Range} in reverse order.
     *
     * @param key   the stream key.
     * @param range must not be {@code null}.
     * @return StreamMessage array-reply list with members of the resulting stream.
     */
    @Override
    public Flux<StreamMessage<String, String>> xrevrange(String key, Range<String> range) {
        return null;
    }

    /**
     * Read messages from a stream within a specific {@link Range} applying a {@link Limit} in reverse order.
     *
     * @param key   the stream key.
     * @param range must not be {@code null}.
     * @param limit must not be {@code null}.
     * @return StreamMessage array-reply list with members of the resulting stream.
     */
    @Override
    public Flux<StreamMessage<String, String>> xrevrange(String key, Range<String> range, Limit limit) {
        return null;
    }

    /**
     * Trims the stream to {@code count} elements.
     *
     * @param key   the stream key.
     * @param count length of the stream.
     * @return simple-reply number of removed entries.
     */
    @Override
    public Mono<Long> xtrim(String key, long count) {
        return null;
    }

    /**
     * Trims the stream to {@code count} elements.
     *
     * @param key                 the stream key.
     * @param approximateTrimming {@code true} to trim approximately using the {@code ~} flag.
     * @param count               length of the stream.
     * @return simple-reply number of removed entries.
     */
    @Override
    public Mono<Long> xtrim(String key, boolean approximateTrimming, long count) {
        return null;
    }

    @Override
    public Mono<Long> xtrim(String s, XTrimArgs xTrimArgs) {
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
    public Mono<String> aclDryRun(String s, RedisCommand<String, String, ?> redisCommand) {
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
    public <T> Flux<T> fcall(String s, ScriptOutputType scriptOutputType, String... strings) {
        return null;
    }

    @Override
    public <T> Flux<T> fcall(String s, ScriptOutputType scriptOutputType, String[] strings, String... strings2) {
        return null;
    }

    @Override
    public <T> Flux<T> fcallReadOnly(String s, ScriptOutputType scriptOutputType, String... strings) {
        return null;
    }

    @Override
    public <T> Flux<T> fcallReadOnly(String s, ScriptOutputType scriptOutputType, String[] strings, String... strings2) {
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
}
