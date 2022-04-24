package com.dorset.gossipers;

import com.dorset.gossipers.cores.ClientCore;
import com.dorset.gossipers.cores.ICore;
import com.dorset.gossipers.cores.ServerCore;
import com.dorset.gossipers.server.ClientType;
import com.dorset.gossipers.server.PacketListener;
import com.dorset.gossipers.server.SocketType;
import com.dorset.gossipers.server.client.GreatClient;
import com.dorset.gossipers.server.packets.*;
import com.dorset.gossipers.server.redis.RedisManager;
import com.dorset.gossipers.server.server.GreatServer;


import java.util.HashMap;
import java.util.Map;

public class Main {

    public static final SocketType SOCKET_TYPE = SocketType.REDIS;
    private static final Object lock = new Object();
    private static final Map<Class<? extends Packet>, PacketListener> listeners = new HashMap<>();
    private static ClientType clientType;

    public static void main(String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("server")) {
            clientType = ClientType.SERVER;
            switch (SOCKET_TYPE){
                case REDIS -> {
                    RedisManager.getInstance();
                    System.out.println("Listening server");
                    Main.lock();

                }
                case SOCKET -> GreatServer.createServer();
            }
        } else if (args.length == 2 && args[0].equalsIgnoreCase("client")) {
            String ip = args[1];
            clientType = ClientType.CLIENT;
            switch (SOCKET_TYPE){
                case REDIS -> {
                    RedisManager.getInstance().sendMessage("START_GAME", ClientType.SERVER);
                }
                case SOCKET -> GreatClient.createClient(ip);
            }
        }

        //Register PacketListener
        listeners.put(PacketClientInitPlayerBoard.class, new PacketClientInitPlayerBoard.Listener());
        listeners.put(PacketClientPlay.class, new PacketClientPlay.Listener());
        listeners.put(PacketClientRequestAttack.class, new PacketClientRequestAttack.Listener());
        listeners.put(PacketClientResponseAttack.class, new PacketClientResponseAttack.Listener());
        listeners.put(PacketClientWon.class, new PacketClientWon.Listener());

        ICore icore = null;

        switch (clientType) {
            case SERVER -> icore = new ServerCore();
            case CLIENT -> icore = new ClientCore();
        }

        icore.run();
    }

    public static Map<Class<? extends Packet>, PacketListener> getListeners(){
        return listeners;
    }

    public static ClientType getClientType() {
        return clientType;
    }

    public static void lock() {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void release() {
        synchronized (lock) {
            lock.notify();
        }
    }
}
