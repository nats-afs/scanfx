package com.afs.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class SidebarController implements Initializable {

    @FXML
    private ImageView sidebarImg;

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private Label label3;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Location " + location);
//        File fIle = new File(getClass().getResource("./images/background.jpg").getFile());
//        System.out.println("File path " + imageFile.getPath());
//        Image image = new Image(String.valueOf(fIle));
//        sidebarImg.setImage(image);
        System.out.println("sidebar controller init");
    }
}
