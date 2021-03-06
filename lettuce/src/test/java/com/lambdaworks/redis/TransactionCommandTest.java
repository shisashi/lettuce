// Copyright (C) 2011 - Will Glozer.  All rights reserved.

package com.lambdaworks.redis;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TransactionCommandTest extends AbstractCommandTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void discard() throws Exception {
        assertThat(redis.multi()).isEqualTo("OK");
        redis.set(key, value);
        assertThat(redis.discard()).isEqualTo("OK");
        assertThat(redis.get(key)).isNull();
    }

    @Test
    public void exec() throws Exception {
        assertThat(redis.multi()).isEqualTo("OK");
        redis.set(key, value);
        assertThat(redis.exec()).isEqualTo(list("OK"));
        assertThat(redis.get(key)).isEqualTo(value);
    }

    @Test
    public void watch() throws Exception {
        assertThat(redis.watch(key)).isEqualTo("OK");

        RedisConnection<String, String> redis2 = client.connect();
        redis2.set(key, value + "X");
        redis2.close();

        redis.multi();
        redis.append(key, "foo");
        assertThat(redis.exec()).isEqualTo(list());

    }

    @Test
    public void unwatch() throws Exception {
        assertThat(redis.unwatch()).isEqualTo("OK");
    }

    @Test
    public void commandsReturnNullInMulti() throws Exception {
        assertThat(redis.multi()).isEqualTo("OK");
        assertThat(redis.set(key, value)).isNull();
        assertThat(redis.get(key)).isNull();
        assertThat(redis.exec()).isEqualTo(list("OK", value));
        assertThat(redis.get(key)).isEqualTo(value);
    }

    @Test
    public void execmulti() throws Exception {
        redis.multi();
        redis.set("one", "1");
        redis.set("two", "2");
        redis.mget("one", "two");
        redis.llen(key);
        assertThat(redis.exec()).isEqualTo(list("OK", "OK", list("1", "2"), 0L));
    }

    @Test
    public void errorInMulti() throws Exception {
        redis.multi();
        redis.set(key, value);
        redis.lpop(key);
        redis.get(key);
        List<Object> values = redis.exec();
        assertThat(values.get(0)).isEqualTo("OK");
        assertThat(values.get(1) instanceof RedisException).isTrue();
        assertThat(values.get(2)).isEqualTo(value);
    }

    @Test
    public void execWithoutMulti() throws Exception {
        exception.expect(RedisException.class);
        exception.expectMessage("ERR EXEC without MULTI");
        redis.exec();
    }

    protected List<Object> list(Object... args) {
        return Arrays.asList(args);
    }
}
