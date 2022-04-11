package com.dorset.gossipers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
//import java.io.File;
import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Node;
import javafx.stage.Stage;



public class MenuController {
    //@FXML private Label welcomeText;
    @FXML private Stage stage;
    @FXML private Scene scene;
    @FXML private Parent root;
    @FXML private ImageView img;


    //Quit the game
    @FXML
    protected void onQuitButtonClick() {
        //File file = new File("D:/gossipers/Dorset_oop/src/main/resources/com/dorset/gossipers/img/bleuCarre.png");
        //Image newImg = new Image(file.toURI().toString());
        //img.setImage(newImg);
        Platform.exit();
    }

    @FXML
    protected void onCreateButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CreateGameScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 939, 683);
        stage.setScene(scene);
        stage.show();
        System.out.print("Server scene");
    }

    @FXML
    protected void onJoinButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("JoinScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 939, 683);
        stage.setScene(scene);
        stage.show();
        System.out.print("Client scene");
    }
}