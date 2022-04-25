package com.dorset.gossipers.cores;

import com.dorset.gossipers.*;
import com.dorset.gossipers.server.ClientType;
import com.dorset.gossipers.server.PacketSender;
import com.dorset.gossipers.server.SocketType;
import com.dorset.gossipers.server.packets.PacketClientInitPlayerBoard;
import com.dorset.gossipers.server.packets.PacketClientPlay;
import com.dorset.gossipers.server.packets.PacketClientUpdateColor;
import com.dorset.gossipers.server.packets.PacketClientWon;
import com.dorset.gossipers.server.server.GreatServer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ServerCore extends Core {

    private static ServerCore instance;
    private static volatile Player serverPlayer;
    private static volatile Player clientPlayer;
    public static volatile boolean yourTurn;
    private static volatile int x = 0;
    private static volatile int y = 0;
    private static volatile String status = "";

    @Override
    public void run() {
        System.out.println("Starting a game (server)");
        yourTurn = true;
        Board serverBoard = new Board(true);
        Board clientBoard = new Board(true);
        Board serverBlankBoard = new Board(false, event -> {
            if (!yourTurn)
                return;

            Cell cell = (Cell) event.getSource();
            x = cell.x;
            y = cell.y;
            status = serverPlayer.firePlayer(clientPlayer, x, y, cell);
            PacketSender.send(new PacketClientUpdateColor(x, y, status));

            boolean continuePlaying = (status.equalsIgnoreCase("Touched") || status.equalsIgnoreCase("Sink!"))
                    && !clientPlayer.gameOver();


            if (!continuePlaying) {
                yourTurn = false;
                Main.release();
            }
        });
        Board clientBlankBoard = new Board(false);
        Boat[] serverBoat = Board.createArrayOfBoats();
        Boat[] clientBoat = Board.createArrayOfBoats();

        serverBoard.fillBoardWithBoat(serverBoat);
        clientBoard.fillBoardWithBoat(clientBoat);

        serverPlayer = new Player(serverBoard, serverBlankBoard, serverBoat);
        clientPlayer = new Player(clientBoard, clientBlankBoard, clientBoat);

        PacketSender.send(new PacketClientInitPlayerBoard(clientBoard.getBoard(), clientBlankBoard.getBoard(), clientBoat));
        Thread thread = new Thread(Application::launch);
        thread.setName("Interface-Thread");
        thread.start();

        while (!serverPlayer.gameOver() && !clientPlayer.gameOver()) {
            Main.lock();

            if (!clientPlayer.gameOver()) {
                System.out.println("Waiting for input from client");
                PacketSender.send(new PacketClientPlay(x, y, status));
                //playRound(clientPlayer, serverPlayer, "2");
                Main.lock();
            }
        }

        if (Main.SOCKET_TYPE == SocketType.SOCKET)
            GreatServer.closeConnection();
        boolean gameOver = serverPlayer.gameOver();
        PacketClientWon packetClientWon = new PacketClientWon(gameOver ? ClientType.CLIENT : ClientType.SERVER);
        PacketSender.send(packetClientWon);

        if (gameOver) {
            System.out.println("You lost the game !");
        } else {
            System.out.println("You won the game !");
        }

        System.exit(0);
    }

   /* public void playRound() {
        boolean continuePlaying = true;
        System.out.println("\n----------");
        System.out.println("\nIt's your turn !\n");

        while (continuePlaying) {
            System.out.println("Blank board of the opponent: ");
            serverPlayer.getBlankBoard().printBoard();
            System.out.println();

            // Reading data using readLine
            int x = getCoordinate("y");
            int y = getCoordinate("x");

            String status = serverPlayer.firePlayer(clientPlayer, x, y);
            System.out.println(status);

            continuePlaying = (status.equalsIgnoreCase("Touched boat") || status.equalsIgnoreCase("Sink!"))
                    && !clientPlayer.gameOver();
        }
    }*/

    public int getCoordinate(String coord) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean check = true;
        int x = 0;
        while (check) {
            System.out.print("Choose the " + coord + " coordinate: ");
            try {
                x = Integer.parseInt(reader.readLine());
                if (x > 9 || x < 0) {
                    System.out.println("Please enter a valid number...");
                } else {
                    check = false;
                }
            } catch (Exception e) { //catches an exception when the user enters a string or anything but an int
                System.out.println("Please only use digits to make a selection.");
            }
        }
        return x;

    }

    public Player getServerPlayer() {
        return serverPlayer;
    }

    public Player getClientPlayer() {
        return clientPlayer;
    }

    public static synchronized ServerCore getInstance() {
        if (instance == null)
            instance = new ServerCore();
        return instance;
    }

    @Override
    public void start(Stage firstStage) throws Exception {
        Scene scene = new Scene(createContent());
        firstStage.setTitle("BattleShip Scene Server");
        firstStage.setScene(scene);
        firstStage.setResizable(false);
        firstStage.show();
    }

    private Parent createContent() {
        BorderPane root = new BorderPane();
        root.setPrefSize(939, 683);
        root.setTop(new Text("BattleShip By The Gossipers (server)"));
        Image image = new Image("com/dorset/gossipers/img/bleu2.png");
        Background bg = new Background(new BackgroundImage(image, null, null, null, null));
        root.setBackground(bg);

        HBox hbox = new HBox(100, serverPlayer.getBoard(), serverPlayer.getBlankBoard());
        hbox.setAlignment(Pos.CENTER);

        root.setCenter(hbox);

        return root;
    }
}
