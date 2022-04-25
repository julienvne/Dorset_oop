package com.dorset.gossipers.cores;

import com.dorset.gossipers.Board;
import com.dorset.gossipers.Main;
import com.dorset.gossipers.Player;
import com.dorset.gossipers.server.PacketSender;
import com.dorset.gossipers.server.packets.PacketClientInitPlayerBoard;
import com.dorset.gossipers.server.packets.PacketClientRequestAttack;
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

public class ClientCore extends Core {

    private static ClientCore instance;

    private static volatile boolean play = true;
    public static volatile Player clientPlayer;
    private static volatile boolean continuePlaying;
    private static volatile String status;

    @Override
    public void run() {
        System.out.println("Starting a game (client)");

        Main.lock();

        clientPlayer.getBoard().printBoard();

        while (play) {
            Main.lock();
        }
    }

    public void playRound() {
        continuePlaying = true;
        System.out.println("\n----------");
        System.out.println("\nIt's your turn !\n");

        while (continuePlaying) {
            System.out.println("Blank board of the opponent: ");
            clientPlayer.getBlankBoard().printBoard();
            System.out.println();

            // Reading data using readLine
            int x = getCoordinate("y");
            int y = getCoordinate("x");

            PacketSender.send(new PacketClientRequestAttack(x, y));
            Main.lock();

            System.out.println(status);
            /*String status = attacker.firePlayer(defender, x, y);
            System.out.println(status);

            continuePlaying = (status.equalsIgnoreCase("Touched boat") || status.equalsIgnoreCase("Sink!")) && !defender.gameOver();

            defender.getLifeEachBoats();*/
        }
    }

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

    public void updateClientPlayer(Player clientPlayer) {
        this.clientPlayer = clientPlayer;
    }

    public void updateAttackRequest(String status, boolean continuePlaying) {
        ClientCore.status = status;
        ClientCore.continuePlaying = continuePlaying;
    }


    public static synchronized ClientCore getInstance() {
        if (instance == null)
            instance = new ClientCore();
        return instance;
    }

    @Override
    public void start(Stage firstStage) throws Exception {
        Scene scene = new Scene(createContent());
        firstStage.setTitle("BattleShip Scene Client");
        firstStage.setScene(scene);
        firstStage.setResizable(false);
        firstStage.show();
    }

    private Parent createContent() {
        BorderPane root = new BorderPane();
        root.setPrefSize(939, 683);
        root.setTop(new Text("BattleShip By The Gossipers (client)"));
        Image image = new Image("com/dorset/gossipers/img/bleu2.png");
        Background bg = new Background(new BackgroundImage(image, null, null, null, null));
        root.setBackground(bg);

        HBox hbox = new HBox(100, clientPlayer.getBoard(), clientPlayer.getBlankBoard());
        hbox.setAlignment(Pos.CENTER);

        root.setCenter(hbox);

        return root;
    }

    public static void launchInterface(){
        new Thread(Application::launch).start();
    }
}
