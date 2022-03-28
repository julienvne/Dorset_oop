package com.dorset.gossipers.server.packets;

import com.dorset.gossipers.Player;

public class SendPlayerPacket implements Serializer<Player> {

    @Override
    public String serialize(Player player) {
        return null;
    }

    @Override
    public Player deserialize(String input) {
        return null;
    }
}
