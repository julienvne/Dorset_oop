package com.dorset.gossipers.server;

import com.dorset.gossipers.Main;
import com.dorset.gossipers.server.packets.Packet;
import com.google.gson.Gson;

import java.util.Map;

public class PacketReceiver {

    private static final Gson gson = new Gson();

    public static void receive(String data) {
        Packet packet = gson.fromJson(data, Packet.class);
        Map<Class<? extends Packet>, PacketListener> listeners = Main.getListeners();

        if(!listeners.containsKey(packet.getClass()))
            throw new IllegalStateException("No listener registred for "+packet.getClass());

        listeners.get(packet.getClass()).onReceive(packet);
    }

}
