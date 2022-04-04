package com.dorset.gossipers.server;

import com.dorset.gossipers.Main;
import com.dorset.gossipers.server.client.GreatClient;
import com.dorset.gossipers.server.packets.Packet;
import com.dorset.gossipers.server.server.GreatServer;
import com.google.gson.Gson;

public class PacketSender {

    private static final Gson gson = new Gson();

    public static void send(Packet packet){
        String data = gson.toJson(packet);
        ClientType clientType = Main.getClientType();

        if(clientType == null) {
            throw new IllegalStateException("ClientType not defined");
        } else if(clientType == ClientType.CLIENT){
            GreatClient.client.sendMessage(data);
        } else if(clientType == ClientType.SERVER){
            GreatServer.server.sendMessage(data);
        }
    }

}
