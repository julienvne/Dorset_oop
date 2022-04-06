package com.dorset.gossipers;

import javafx.fxml.FXML;
//import javafx.scene.control.Label;
import javafx.application.Platform;

public class MenuController {
    //@FXML
    //private Label welcomeText;

    //Quit the game
    @FXML
    protected void onQuitButtonClick() {
        Platform.exit();
    }
}