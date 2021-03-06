package com.lambdaworks.redis;

import java.util.List;
import java.util.Map;

import com.lambdaworks.redis.output.KeyStreamingChannel;
import com.lambdaworks.redis.output.KeyValueStreamingChannel;
import com.lambdaworks.redis.output.ValueStreamingChannel;

/**
 * Asynchronous executed commands for Hashes (Key-Value pairs).
 * 
 * @param <K> Key type.
 * @param <V> Value type.
 * @author <a href="mailto:mpaluch@paluch.biz">Mark Paluch</a>
 * @since 3.0
 */
public interface RedisHashesAsyncConnection<K, V> {

    /**
     * Delete one or more hash fields.
     * 
     * @param key the key
     * @param fields the field type: key
     * @return RedisFuture&lt;Long&gt; integer-reply the number of fields that were removed from the hash, not including
     *         specified but non existing fields.
     */
    RedisFuture<Long> hdel(K key, K... fields);

    /**
     * Determine if a hash field exists.
     * 
     * @param key the key
     * @param field the field type: key
     * @return RedisFuture&lt;Boolean&gt; integer-reply specifically:
     * 
     *         <code>1</code> if the hash contains <code>field</code>. <code>0</code> if the hash does not contain
     *         <code>field</code>, or <code>key</code> does not exist.
     */
    RedisFuture<Boolean> hexists(K key, K field);

    /**
     * Get the value of a hash field.
     * 
     * @param key the key
     * @param field the field type: key
     * @return RedisFuture&lt;V&gt; bulk-string-reply the value associated with <code>field</code>, or <code>nil</code> when
     *         <code>field</code> is not present in the hash or <code>key</code> does not exist.
     */
    RedisFuture<V> hget(K key, K field);

    /**
     * Increment the integer value of a hash field by the given number.
     * 
     * @param key the key
     * @param field the field type: key
     * @param amount the increment type: long
     * @return RedisFuture&lt;Long&gt; integer-reply the value at <code>field</code> after the increment operation.
     */
    RedisFuture<Long> hincrby(K key, K field, long amount);

    /**
     * Increment the float value of a hash field by the given amount.
     * 
     * @param key the key
     * @param field the field type: key
     * @param amount the increment type: double
     * @return RedisFuture&lt;Double;&gt; bulk-string-reply the value of <code>field</code> after the increment.
     */
    RedisFuture<Double> hincrbyfloat(K key, K field, double amount);

    /**
     * Get all the fields and values in a hash.
     * 
     * @param key the key
     * @return RedisFuture&lt;Map&lt;K,V&gt;&gt; array-reply list of fields and their values stored in the hash, or an empty
     *         list when <code>key</code> does not exist.
     */
    RedisFuture<Map<K, V>> hgetall(K key);

    /**
     * Stream over all the fields and values in a hash.
     * 
     * @param channel the channel
     * @param key the key
     * 
     * @return RedisFuture&lt;Long&gt; count of the keys.
     */
    RedisFuture<Long> hgetall(KeyValueStreamingChannel<K, V> channel, K key);

    /**
     * Get all the fields in a hash.
     * 
     * @param key the key
     * @return RedisFuture&lt;List&lt;K&gt;&gt; array-reply list of fields in the hash, or an empty list when <code>key</code>
     *         does not exist.
     */
    RedisFuture<List<K>> hkeys(K key);

    /**
     * Get all the fields in a hash.
     * 
     * @param channel the channel
     * @param key the key
     * 
     * @return RedisFuture&lt;Long&gt; count of the keys.
     */
    RedisFuture<Long> hkeys(KeyStreamingChannel<K> channel, K key);

    /**
     * Get the number of fields in a hash.
     * 
     * @param key the key
     * @return RedisFuture&lt;Long&gt; integer-reply number of fields in the hash, or <code>0</code> when <code>key</code> does
     *         not exist.
     */
    RedisFuture<Long> hlen(K key);

    /**
     * Get the values of all the given hash fields.
     * 
     * @param key the key
     * @param fields the field type: key
     * @return RedisFuture&lt;List&lt;V&gt;&gt; array-reply list of values associated with the given fields, in the same
     */
    RedisFuture<List<V>> hmget(K key, K... fields);

    /**
     * Stream over the values of all the given hash fields.
     * 
     * @param channel the channel
     * @param key the key
     * @param fields the fields
     * 
     * @return RedisFuture&lt;Long&gt; count of the keys
     */
    RedisFuture<Long> hmget(ValueStreamingChannel<V> channel, K key, K... fields);

    /**
     * Set multiple hash fields to multiple values.
     * 
     * @param key the key
     * @param map the null
     * @return RedisFuture&lt;String&gt; simple-string-reply
     */
    RedisFuture<String> hmset(K key, Map<K, V> map);

    /**
     * Set the string value of a hash field.
     * 
     * @param key the key
     * @param field the field type: key
     * @param value the value
     * @return RedisFuture&lt;Boolean&gt; integer-reply specifically:
     * 
     *         <code>1</code> if <code>field</code> is a new field in the hash and <code>value</code> was set. <code>0</code> if
     *         <code>field</code> already exists in the hash and the value was updated.
     */
    RedisFuture<Boolean> hset(K key, K field, V value);

    /**
     * Set the value of a hash field, only if the field does not exist.
     * 
     * @param key the key
     * @param field the field type: key
     * @param value the value
     * @return RedisFuture&lt;Boolean&gt; integer-reply specifically:
     * 
     *         <code>1</code> if <code>field</code> is a new field in the hash and <code>value</code> was set. <code>0</code> if
     *         <code>field</code> already exists in the hash and no operation was performed.
     */
    RedisFuture<Boolean> hsetnx(K key, K field, V value);

    /**
     * Get all the values in a hash.
     * 
     * @param key the key
     * @return RedisFuture&lt;List&lt;V&gt;&gt; array-reply list of values in the hash, or an empty list when <code>key</code>
     *         does not exist.
     */
    RedisFuture<List<V>> hvals(K key);

    /**
     * Stream over all the values in a hash.
     * 
     * @param channel the channel
     * @param key the key
     * 
     * @return RedisFuture&lt;Long&gt; acount of the keys.
     */
    RedisFuture<Long> hvals(ValueStreamingChannel<V> channel, K key);

    /**
     * Incrementally iterate hash fields and associated values.
     */
    RedisFuture<MapScanCursor<K, V>> hscan(K key);

    /**
     * Incrementally iterate hash fields and associated values.
     */
    RedisFuture<MapScanCursor<K, V>> hscan(K key, ScanArgs scanArgs);

    /**
     * Incrementally iterate hash fields and associated values.
     */
    RedisFuture<MapScanCursor<K, V>> hscan(K key, ScanCursor scanCursor, ScanArgs scanArgs);

    /**
     * Incrementally iterate hash fields and associated values.
     */
    RedisFuture<MapScanCursor<K, V>> hscan(K key, ScanCursor scanCursor);

    /**
     * Incrementally iterate hash fields and associated values.
     */
    RedisFuture<StreamScanCursor> hscan(KeyValueStreamingChannel<K, V> channel, K key);

    /**
     * Incrementally iterate hash fields and associated values.
     */
    RedisFuture<StreamScanCursor> hscan(KeyValueStreamingChannel<K, V> channel, K key, ScanArgs scanArgs);

    /**
     * Incrementally iterate hash fields and associated values.
     * 
     * @param channel the key
     * @param key the cursor type: long
     * @param scanCursor the pattern type: pattern
     * @param scanArgs the count type: long
     */
    RedisFuture<StreamScanCursor> hscan(KeyValueStreamingChannel<K, V> channel, K key, ScanCursor scanCursor, ScanArgs scanArgs);

    /**
     * Incrementally iterate hash fields and associated values.
     */
    RedisFuture<StreamScanCursor> hscan(KeyValueStreamingChannel<K, V> channel, K key, ScanCursor scanCursor);
}
