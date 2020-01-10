package com.afs;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconName;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ScanPreview extends FlowPane {

    public ScanPreview() {
        super();
        this.setPadding(new Insets(15));
        this.getStyleClass().add("scan-result");
        this.setAlignment(Pos.TOP_LEFT);
        this.setRowValignment(VPos.CENTER);
        this.setColumnHalignment(HPos.LEFT);
        this.setHgap(15);
        this.setVgap(15);
    }

    public void addThumbnail(File file) {

        Image thumbnail = new Image(file.toURI().toString(), 150, 0, true, false);

        StackPane thumbnailBox = new StackPane();
        thumbnailBox.setAlignment(Pos.TOP_LEFT);

        JFXButton btnDelete = new JFXButton();
        FontAwesomeIcon deleteIcon = new FontAwesomeIcon();
        deleteIcon.setIcon(FontAwesomeIconName.CLOSE);
        btnDelete.setGraphic(deleteIcon);

        JFXButton btnSearch = new JFXButton();
        FontAwesomeIcon searchIcon = new FontAwesomeIcon();
        searchIcon.setIcon(FontAwesomeIconName.SEARCH);
        btnSearch.setGraphic(searchIcon);

        setOnDragOver(e -> {
            e.acceptTransferModes(TransferMode.MOVE);
            e.consume();
        });

        ImageView imageView = new ImageView(thumbnail);

        imageView.setOnDragDetected(e -> {
            System.out.println("onDragDetected ...");
            Node node = (Node) e.getSource();
            Dragboard dragboard = imageView.startDragAndDrop(TransferMode.MOVE);
            dragboard.setDragView(thumbnail, e.getX(), e.getY());
            ClipboardContent clipboardContent = new ClipboardContent();
            clipboardContent.putString("");
            dragboard.setContent(clipboardContent);
            e.consume();
        });

        imageView.setOnDragDropped(e -> {
            Dragboard db = e.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                Node source = (Node) e.getGestureSource();
                Node target = (Node) e.getTarget();
                int sourceIndex = getChildren().indexOf(source.getParent());
                int targetIndex = getChildren().indexOf(target.getParent());
                System.out.println("sourceIndex " + sourceIndex);
                System.out.println("targetIndex " + targetIndex);
                List<Node> nodes = new ArrayList<>(getChildren());
                if (sourceIndex < targetIndex) {
                    Collections.rotate(
                            nodes.subList(sourceIndex, targetIndex + 1), -1);
                } else {
                    Collections.rotate(
                            nodes.subList(targetIndex, sourceIndex + 1), 1);
                }
                getChildren().clear();
                getChildren().addAll(nodes);
                success = true;
            }
            e.setDropCompleted(success);
            e.consume();

        });

        imageView.setOnMouseEntered(e -> {
//            System.out.println("Mouse entered...");
            imageView.setCursor(Cursor.MOVE);
//            thumbnailAction.setVisible(true);
            e.consume();
        });

        imageView.setOnMouseExited(e -> {
//            System.out.println("Mouse exited...");
//            imageView.setCursor(Cursor.MOVE);
//            thumbnailAction.setVisible(false);
            e.consume();
        });

        StackPane.setMargin(btnDelete, new Insets(5, 5, 5, 5));
        StackPane.setMargin(btnSearch, new Insets(5, 5, 5, 40));

        thumbnailBox.getChildren().addAll(imageView, btnDelete, btnSearch);
        getChildren().add(thumbnailBox);

    }

    private WritableImage createSnapshot(Node node) {
        SnapshotParameters snapshotParams = new SnapshotParameters();
        WritableImage image = node.snapshot(snapshotParams, null);
        return image;
    }

}
