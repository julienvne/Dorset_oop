package com.dorset.gossipers.server.packets;

import com.dorset.gossipers.GsonUtils;
import com.dorset.gossipers.Main;
import com.dorset.gossipers.Player;
import com.dorset.gossipers.cores.ClientCore;
import com.dorset.gossipers.server.PacketListener;

public class PacketClientResponseAttack extends Packet{

    private String status;
    private boolean continuePlaying;
    private Player clientPlayer;

    public PacketClientResponseAttack() {
    }

    public PacketClientResponseAttack(String status, boolean continuePlaying, Player clientPlayer) {
        this.status = status;
        this.continuePlaying = continuePlaying;
        this.clientPlayer = clientPlayer;
    }

    @Override
    public void read(String[] data) {
        this.status = data[0];
        this.continuePlaying = Boolean.parseBoolean(data[1]);
        this.clientPlayer = gson.fromJson(data[2], Player.class);
    }

    @Override
    public String[] write() {
        return new String[]{
                status,
                continuePlaying+"",
                gson.toJson(clientPlayer)
        };
    }

    public static class Listener implements PacketListener{

        @Override
        public void onReceive(Packet packet) {
            PacketClientResponseAttack responseAttack = (PacketClientResponseAttack) packet;
            ClientCore instance = ClientCore.getInstance();
            instance.updateAttackRequest(responseAttack.status, responseAttack.continuePlaying);
            instance.updateClientPlayer(responseAttack.clientPlayer);

            Main.release();
        }
    }
}
