package com.afs;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import eu.gnome.morena.Device;
import eu.gnome.morena.Manager;
import eu.gnome.morena.Scanner;
import eu.gnome.morena.TransferListener;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class FXMLController implements Initializable {

    @FXML
    private ImageView imgSidebar;

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private Label label3;

    @FXML
    private HBox mainView;

    @FXML
    private JFXComboBox<?> cmbSource;

    @FXML
    private JFXButton btnStart;

    private ScanPreview scanResult;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initScanner();
    }

    private void initScanner() {

        try {
            Manager manager = Manager.getInstance();
            List devices = manager.listDevices();
            cmbSource.setItems(FXCollections.observableArrayList(devices));
            cmbSource.getSelectionModel().selectFirst();

//            scanResult = new HBox();
//            scanResult.getChildren().addListener((ListChangeListener<Node>) c -> {
//                if (scanResult.getChildren().size() > 0){
//
//                }
//
//            });

            btnStart.setOnAction(event -> {
                System.out.println("init scan");
                Device device = (Device) devices.get(cmbSource.getSelectionModel().getSelectedIndex());

                if (device instanceof Scanner) {
                    Scanner scanner = (Scanner) device;

                    try {
                        scanner.startTransfer(new FileTransferHandler());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            System.out.println("Upps ... exception");
            System.out.println(e.getMessage());
        }
    }

    private WritableImage createSnapshot(Node node) {
        SnapshotParameters snapshotParams = new SnapshotParameters();
        WritableImage image = node.snapshot(snapshotParams, null);
        return image;
    }

    class FileTransferHandler implements TransferListener {
        File imageFile;
        String message;
        boolean transferDone = false;

        /**
         * Transferred image is handled in this callback method. File containing the image is provided as an argument. The image file may be invalidated after this method returns
         * and so if the application needs to retain the file moving or renaming should be used. The image type may vary
         * depending on the interface (Wia/ICA) and the device driver. Typical format includes BMP for WIA scanners and JPEG for WIA camera and for ICA devices.
         * Please note that this method runs in different thread than that where the device.startTransfer() has been called.
         *
         * @param file - the file containing the acquired image
         * @see eu.gnome.morena.TransferDoneListener#transferDone(java.io.File)
         */
        @Override
        public void transferDone(File file) {
            imageFile = file;
            System.out.println("image file:" + imageFile.getAbsolutePath());
//            if (!adfScan.isSelected())
            transferDone = true;
            Platform.runLater(() -> {
                System.out.println(file.toURI().toString());
//                Image thumbnail = new Image(file.toURI().toString(), 100, 0, true, false);

                for (int i = 0; i < 10; i++)
//                    scanResult.addThumbnail(file);

//                for (int i = 0; i < 10; i++) {
//                    VBox thumbnailBox = new VBox();
//                    HBox thumbnailAction = new HBox();
//                    thumbnailBox.setSpacing(10);
//                    thumbnailAction.setSpacing(10);
//
//                    JFXButton btnDelete = new JFXButton();
//                    FontAwesomeIcon deleteIcon = new FontAwesomeIcon();
//                    deleteIcon.setIcon(FontAwesomeIconName.CLOSE);
//                    btnDelete.setGraphic(deleteIcon);
//
//                    JFXButton btnSearch = new JFXButton();
//                    FontAwesomeIcon searchIcon = new FontAwesomeIcon();
//                    searchIcon.setIcon(FontAwesomeIconName.SEARCH);
//                    btnSearch.setGraphic(searchIcon);
//
//                    JFXButton btnDrag = new JFXButton();
//                    FontAwesomeIcon dragIcon = new FontAwesomeIcon();
//                    dragIcon.setIcon(FontAwesomeIconName.ARROWS_ALT);
//                    btnDrag.setGraphic(dragIcon);
//
//                    btnSearch.getStyleClass().add("action-icon");
//
//                    btnSearch.setOnMouseClicked(e -> {
//                        System.out.println("cliok on searchIcon");
//                    });
//
//                    btnDelete.setOnMouseClicked(e -> {
//                        System.out.println("cliok on deleteIcon");
//                        scanResult.getChildren().remove(thumbnailBox);
//                    });
//
//                    btnDrag.setOnMouseClicked(e -> System.out.println("cliok on dragIcon"));
//
//                    btnDrag.setOnMouseDragEntered(e -> {
//                        System.out.println(e.getSceneX());
//                    });
//
//                    btnDrag.setOnDragDetected(e -> {
//                        Dragboard dragboard = btnDrag.startDragAndDrop(TransferMode.MOVE);
//                        System.out.println(e.getScreenX());
//                    });
//
//                    thumbnailAction.getChildren().addAll(btnDelete, btnSearch, btnDrag);
//
//                    thumbnailBox.getChildren().addAll(new ImageView(thumbnail), thumbnailAction);
//                    scanResult.getChildren().add(thumbnailBox);
//                }

//                scanResult.getChildren().add(new ImageView("file:" + imageFile.getAbsolutePath()));
                    if (transferDone) {
//                    disableProgressBar();
//                    status.setText("Transfer done");
                        System.out.println("Transfer done");
                        transferDone = false;
                    }
            });
        }

        /**
         * This callback method is called when scanning process failed for any reason. Description of the problem is provided.
         */
        @Override
        public void transferFailed(int code, String error) {
            message = (code == 0) ? "Feeder empty" : "Scan error (" + code + ") " + error;
            System.err.println(message);
            transferDone = false;
            Platform.runLater(() -> {
//                disableProgressBar();
//                status.setText(message);
                System.out.println(message);

            });

        }

        @Override
        public void transferProgress(int percent) {
//            pbar.setProgress(percent / 100d);
//            System.out.println("transfer progress " + percent);
            System.out.println("tranfer ...");
        }

    }
}
