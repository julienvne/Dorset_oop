package com.dorset.gossipers.server.packets;

import com.google.gson.Gson;

public abstract class Packet {

    protected static Gson gson = new Gson();

    public Packet() {
    }

    public abstract void read(String[] data);
    public abstract String[] write();

}
