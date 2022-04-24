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

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Main extends Application{

    public static final SocketType SOCKET_TYPE = SocketType.REDIS;
    private static final Object lock = new Object();
    private static final Map<Class<? extends Packet>, PacketListener> listeners = new HashMap<>();
    private static ClientType clientType;

    public static void main(String[] args) {
        //launch();

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

    //----------------les supers petasses--------------------



    private Parent createContent () {
        BorderPane root = new BorderPane();
        root.setPrefSize(939, 683);
        root.setTop(new Text("BattleShip By The Gossipers"));
        Image image = new Image("com/dorset/gossipers/img/bleu2.png");
        Background bg = new Background(new BackgroundImage(image, null, null, null, null));
        root.setBackground(bg);

        Parent enemyBoard = new Board(false);

        Parent playerBoard = new Board(true);

        HBox hbox = new HBox(100, enemyBoard, playerBoard);
        hbox.setAlignment(Pos.CENTER);

        root.setCenter(hbox);

        return root;
    }

   /* private void enemyMove() {
        while (enemyTurn) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);

            Cell cell = playerBoard.getCell(x, y);
            if (cell.wasShot)
                continue;

            enemyTurn = cell.shoot();

            if (playerBoard.ships == 0) {
                System.out.println("YOU LOSE");
                System.exit(0);
            }
        }
    }

    private void startGame() {
        // place enemy ships
        int type = 5;

        while (type > 0) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);

            if (enemyBoard.placeShip(new Ship(type, Math.random() < 0.5), x, y)) {
                type--;
            }
        }

        running = true;
    }
*/
    @Override
    public void start (Stage firstStage) throws Exception {
        Scene scene = new Scene(createContent());
        firstStage.setTitle("BattleShip Scene");
        firstStage.setScene(scene);
        firstStage.setResizable(false);
        firstStage.show();
    }
}
