package com.dorset.gossipers;

import com.dorset.gossipers.server.ClientType;
import com.dorset.gossipers.server.PacketListener;
import com.dorset.gossipers.server.client.GreatClient;
import com.dorset.gossipers.server.packets.Packet;
import com.dorset.gossipers.server.packets.PacketInitPlayerBoard;
import com.dorset.gossipers.server.server.GreatServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private static final Map<Class<? extends Packet>, PacketListener> listeners = new HashMap<>();
    private static ClientType clientType;

    public static void main(String[] args) throws IOException {
        if (args.length == 1 && args[0].equalsIgnoreCase("server")) {
            clientType = ClientType.SERVER;
            GreatServer.createServer();
        } else if (args.length == 2 && args[0].equalsIgnoreCase("client")) {
            String ip = args[1];

            clientType = ClientType.CLIENT;
            GreatClient.createClient(ip);
            return;
        }

        //Register PacketListener
        listeners.put(PacketInitPlayerBoard.class, new PacketInitPlayerBoard.Listener());

        Board server = new Board();
        Board client = new Board();
        Board serverBlankBoard = new Board();
        Board clientBlankBoard = new Board();

        Boat[] array1 = Board.createArrayOfBoats();
        Boat[] array2 = Board.createArrayOfBoats();

        server.fillBoardWithBoat(array1);
        client.fillBoardWithBoat(array2);

        Player player1 = new Player(server, serverBlankBoard, array1);
        Player player2 = new Player(client, clientBlankBoard, array2);

        while (!player1.gameOver() || !player2.gameOver()) {
            // Enter data using BufferReader
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in));

            player2.getBoard().printBoard();

            // Reading data using readLine
            int x = Integer.parseInt(reader.readLine());
            while (x > 9)
                x = Integer.parseInt(reader.readLine());

            int y = Integer.parseInt(reader.readLine());
            while (y > 9)
                y = Integer.parseInt(reader.readLine());

            System.out.println(player1.firePlayer(player2, x, y));
            player2.getLifeEachBoats();

        }
    }

    public static Map<Class<? extends Packet>, PacketListener> getListeners(){
        return listeners;
    }

    public static ClientType getClientType() {
        return clientType;
    }
}
