package com.afs;

import com.jfoenix.controls.JFXDecorator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("\\views\\scene.fxml"));

        JFXDecorator decorator = new JFXDecorator(stage, root);

        double width = 800;
        double height = 600;
        try {
            Rectangle2D bounds = Screen.getScreens().get(0).getBounds();
            width = bounds.getWidth() / 2.5;
            height = bounds.getHeight() / 1.35;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Scene scene = new Scene(decorator, width, height);
        scene.getStylesheets().add(getClass().getResource("\\css\\styles.css").toExternalForm());

        stage.setTitle("JavaFX and Gradle");
        stage.setScene(scene);
        stage.setFullScreen(true);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
