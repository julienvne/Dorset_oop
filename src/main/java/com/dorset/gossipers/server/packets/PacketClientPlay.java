package com.dorset.gossipers.server.packets;

import com.dorset.gossipers.Main;
import com.dorset.gossipers.server.PacketListener;

public class PacketClientPlay extends Packet{

    public PacketClientPlay() {
    }

    @Override
    public void read(String[] data) {}

    @Override
    public String[] write() {
        return new String[]{};
    }

    public static class Listener implements PacketListener {

        @Override
        public void onReceive(Packet packet) {
            Main.release();
        }
    }
}
