package com.dorset.gossipers.server.redis;

import com.dorset.gossipers.Main;
import com.dorset.gossipers.server.ClientType;
import com.dorset.gossipers.server.PacketReceiver;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RedisManager {

    private static RedisManager instance;
    private static RedissonClient client;

    public RedisManager() {
        final Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://45.155.171.152:6379")
                .setPassword("redisdorset");

        client = Redisson.create(config);

        new Thread(() -> {
            try {
                switch (Main.getClientType()){
                    case SERVER -> {
                        System.out.println("Listeneing on server message");
                        client.getTopic(ClientType.SERVER.name()).addListener(String.class, (charSequence, s) -> {
                            if(charSequence.toString().equals(ClientType.SERVER.name())){
                                System.out.println("Receive message (server): "+s);
                                if(s.equals("START_GAME")){
                                    Main.release();
                                    return;
                                }

                                PacketReceiver.receive(s);
                            }
                        });
                    }
                    case CLIENT -> {
                        System.out.println("Listening on client message");
                        client.getTopic(ClientType.CLIENT.name()).addListener(String.class, (charSequence, s) -> {
                            if(charSequence.toString().equals(ClientType.CLIENT.name())){
                                System.out.println("Receive message (client): "+s);
                                PacketReceiver.receive(s);
                            }
                        });
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();

        RBucket<String> bucket = client.getBucket("bonsoir");
        bucket.set("kkikikk");
        System.out.println(bucket.get());
    }

    public static RedisManager getInstance() {
        if(instance == null)
            instance = new RedisManager();
        return instance;
    }

    public void sendMessage(String message, ClientType clientType){
        try {
            RTopic rTopic = client.getTopic(clientType.name());
            System.out.println("Publishing message on "+clientType.name());
            rTopic.publish(message);
        } catch (Exception e) {
            Logger.getGlobal().log(Level.SEVERE, "Failed to publish ", e);
        }
    }
}
