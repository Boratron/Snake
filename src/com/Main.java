package com;

import com.controller.GameController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
    public final static int SCREEN_WIDTH_HEIGHT = 800;
    public final static int GRID_ROWS_COLUMNS = 20;
    public final static int CELL_SIZE = SCREEN_WIDTH_HEIGHT / GRID_ROWS_COLUMNS;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(SCREEN_WIDTH_HEIGHT, SCREEN_WIDTH_HEIGHT);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        Group root = new Group();
        Scene gameScene = new Scene(root, SCREEN_WIDTH_HEIGHT, SCREEN_WIDTH_HEIGHT, Color.BLACK);

        GameController gameController = new GameController(graphicsContext, gameScene);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(70), e -> run(gameController)));
        timeline.setCycleCount(Animation.INDEFINITE);

        root.getChildren().add(canvas);

        primaryStage.setTitle("Snake");
        primaryStage.setScene(gameScene);

        primaryStage.setResizable(false);
        primaryStage.show();

        timeline.play();
    }

    private static void run(GameController gameController) {
        if (!gameController.isStart() && !gameController.isGameOver()) { // game not yet started
            gameController.setScore(0);
            gameController.generateFood();

            gameController.clearScreen();

            gameController.renderGrid();
            gameController.renderScore();
            gameController.renderHighScore();
            gameController.renderSnake();
            gameController.renderFood();
        }
        if (gameController.isStart() && !gameController.isGameOver()) { // game has started
            gameController.checkCollision();
            gameController.generateFood();
            gameController.moveSnake();

            gameController.clearScreen();

            gameController.renderGrid();
            gameController.renderScore();
            gameController.renderHighScore();
            gameController.renderFood();
            gameController.renderSnake();
        }
        if (!gameController.isStart() && gameController.isGameOver()) { // game is over
            gameController.resetSnake();
            gameController.computeHighScore();

            gameController.clearScreen();

            gameController.renderHighScore();
            gameController.renderGameOverMessage();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}