package com.dorset.gossipers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;

public class MenuController {
    //@FXML private Label welcomeText;
    private Stage stage;
    private Scene scene;
    private Parent root;


    //Quit the game
    @FXML
    protected void onQuitButtonClick() {
        Platform.exit();
    }

    @FXML
    protected void onCreateButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CreateGameScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 939, 683);
        stage.setScene(scene);
        stage.show();
        System.out.print("New scene");
    }

    @FXML
    protected void onJoinButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("JoinScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 939, 683);
        stage.setScene(scene);
        stage.show();
        System.out.print("New scene");
    }
}