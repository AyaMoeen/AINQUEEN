package com.example.myprojectai;

import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class demo extends Application {

    @Override
    public void start(Stage primaryStage) {
        int chessSize = 8;
        double cellSize = 50.0;

        // Create a pane for the chessboard
        Pane chessboardPane = createChessboard(chessSize, cellSize);

        // Add rotating queen icons around the chessboard
        addRotatingIcons(chessboardPane, 20);

        // Center the chessboard pane in the main stack pane
        StackPane root = new StackPane(chessboardPane);
        Scene scene = new Scene(root, chessSize * cellSize, chessSize * cellSize);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Beautiful Chessboard Example");
        primaryStage.show();
    }

    private Pane createChessboard(int size, double cellSize) {
        Pane chessboardPane = new Pane();

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Button cell = new Button();
                cell.setMinSize(50, 50);
                cell.setMaxSize(50, 50);



                chessboardPane.getChildren().add(cell);
            }
        }

        return chessboardPane;
    }

    private void addRotatingIcons(Pane pane, int numIcons) {
        double centerX = pane.getWidth() / 2;
        double centerY = pane.getHeight() / 2;

        for (int i = 0; i < numIcons; i++) {
            Image img=new Image("C:\\Users\\hp\\IdeaProjects\\MyProjectAi\\src\\main\\resources\\com\\example\\myprojectai\\AA.png");

            ImageView iconView = new ImageView(img);
            iconView.setFitWidth(30);
            iconView.setFitHeight(30);

            double angle = i * 360.0 / numIcons;
            iconView.setLayoutX(centerX + 150 * Math.cos(Math.toRadians(angle)));
            iconView.setLayoutY(centerY - 150 * Math.sin(Math.toRadians(angle)));

            RotateTransition rotateTransition = new RotateTransition(Duration.seconds(10), iconView);
            rotateTransition.setByAngle(360);
            rotateTransition.setCycleCount(RotateTransition.INDEFINITE);
            rotateTransition.setAutoReverse(false);
            rotateTransition.play();

            pane.getChildren().add(iconView);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
