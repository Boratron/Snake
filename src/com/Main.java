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

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(71), e -> run(gameController)));
        timeline.setCycleCount(Animation.INDEFINITE);

        root.getChildren().add(canvas);

        primaryStage.setTitle("Snake");
        primaryStage.setScene(gameScene);

        primaryStage.setResizable(false);
        primaryStage.show();

        timeline.play();
    }

    private static void run(GameController gameController) {
        if (!gameController.getGameState().isStart() && !gameController.getGameState().isGameOver()) { // game not yet started
            gameController.getGameState().setScore(0);
            gameController.updateGameGrid();
            gameController.updateScore();
            gameController.updateSnake();
            gameController.generateFood();
            gameController.updateFood();
        }
        if (gameController.getGameState().isStart() && !gameController.getGameState().isGameOver()) { // game has started
            gameController.updateGameGrid();
            gameController.updateScore();

            gameController.checkCollision();

            gameController.generateFood();
            gameController.updateFood();

            gameController.getSnake().move();
            gameController.updateSnake();
        }
        if (!gameController.getGameState().isStart() && gameController.getGameState().isGameOver()) { // game is over
            gameController.updateGameGrid();
            gameController.updateScore();
            gameController.updateGameOverMessage();
            gameController.getSnake().reset();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}