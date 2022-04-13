package com.dorset.gossipers.server.redis;

import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class RedisManager {

    private static RedisManager instance;

    public RedisManager() {
        System.out.println("????");
        final Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://45.155.171.152:6379")
                .setPassword("redisdorset");

        RedissonClient client = Redisson.create(config);

        RBucket<String> bucket = client.getBucket("bonsoir");
        bucket.set("kkikikk");
        System.out.println(bucket.get());
    }

    public static RedisManager getInstance() {
        if(instance == null)
            instance = new RedisManager();
        return instance;
    }
}
