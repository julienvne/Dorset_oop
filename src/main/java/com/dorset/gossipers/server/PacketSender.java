package com.dorset.gossipers.server;

import com.dorset.gossipers.GsonUtils;
import com.dorset.gossipers.Main;
import com.dorset.gossipers.server.client.GreatClient;
import com.dorset.gossipers.server.packets.Packet;
import com.dorset.gossipers.server.redis.RedisManager;
import com.dorset.gossipers.server.server.GreatServer;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class PacketSender {

    public static void send(Packet packet){
        Type typeOfSrc = new TypeToken<Packet>(){}.getType();
        String data = GsonUtils.gson.toJson(packet, typeOfSrc);
        ClientType clientType = Main.getClientType();
        if(clientType == null)
            throw new IllegalStateException("ClientType not defined");
        switch (Main.SOCKET_TYPE){
            case SOCKET -> {
                if(clientType == ClientType.CLIENT){
                    System.out.println("Sending packet "+packet.getClass().getSimpleName()+" to server");
                    GreatClient.client.sendMessage(data);
                } else if(clientType == ClientType.SERVER){
                    GreatServer.server.sendMessage(data);
                    System.out.println("Sending packet "+packet.getClass().getSimpleName()+" to client");
                }
            }
            case REDIS -> {
                if(clientType == ClientType.CLIENT){
                    System.out.println("Sending packet "+packet.getClass().getSimpleName()+" to server");
                    RedisManager.getInstance().sendMessage(data, ClientType.SERVER);
                } else if(clientType == ClientType.SERVER){
                    System.out.println("Sending packet "+packet.getClass().getSimpleName()+" to client");
                    RedisManager.getInstance().sendMessage(data, ClientType.CLIENT);
                }
            }
        }
    }

}
