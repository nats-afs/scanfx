package com.afs;

import com.jfoenix.controls.JFXDecorator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        VBox sidebar = FXMLLoader.load(getClass().getResource("\\views\\sidebar.fxml"));
        FlowPane header = FXMLLoader.load(getClass().getResource("\\views\\header.fxml"));
        FlowPane footer = FXMLLoader.load(getClass().getResource("\\views\\footer.fxml"));

        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(sidebar);
        borderPane.setTop(header);
        borderPane.setBottom(footer);

        Scene scene = new Scene(borderPane);
        scene.getStylesheets().add(getClass().getResource("\\css\\styles.css").toExternalForm());

        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
