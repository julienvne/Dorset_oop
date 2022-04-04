package com.dorset.gossipers.server;

import com.dorset.gossipers.server.packets.Packet;

public interface PacketListener {

    void onReceive(Packet packet);
}
