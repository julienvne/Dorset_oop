package com.dorset.gossipers.server;

import com.dorset.gossipers.GsonUtils;
import com.dorset.gossipers.Main;
import com.dorset.gossipers.server.packets.Packet;

import java.util.Map;

public class PacketReceiver {

    public static void receive(String data) {
        Packet packet = GsonUtils.gson.fromJson(data, Packet.class);
        Map<Class<? extends Packet>, PacketListener> listeners = Main.getListeners();

        if(!listeners.containsKey(packet.getClass()))
            throw new IllegalStateException("No listener registered for "+packet.getClass());

        listeners.get(packet.getClass()).onReceive(packet);
    }

}
