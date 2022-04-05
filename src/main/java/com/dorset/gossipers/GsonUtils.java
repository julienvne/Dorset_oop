package com.dorset.gossipers;

import com.dorset.gossipers.server.packets.Packet;
import com.dorset.gossipers.server.packets.PacketAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtils {

    public static Gson gson;

    static {
        GsonBuilder gsonBilder = new GsonBuilder();
        gsonBilder.registerTypeAdapter(Packet.class, new PacketAdapter());
        gson = gsonBilder.create();
    }

}
