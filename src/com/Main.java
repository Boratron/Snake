package com;

import com.scene.GameScene;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
    private final static int SCREEN_WIDTH_HEIGHT = 800;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(SCREEN_WIDTH_HEIGHT, SCREEN_WIDTH_HEIGHT);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        Group root = new Group();
        GameScene gameScene = new GameScene(graphicsContext, root, SCREEN_WIDTH_HEIGHT, SCREEN_WIDTH_HEIGHT, Color.BLACK);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(71), e -> run(gameScene)));
        timeline.setCycleCount(Animation.INDEFINITE);

        root.getChildren().add(canvas);

        primaryStage.setTitle("Snake");
        primaryStage.setScene(gameScene);

        primaryStage.setResizable(false);
        primaryStage.show();

        timeline.play();
    }

    private static void run(GameScene gameScene) {
        if (!gameScene.getGameState().isStart() && !gameScene.getGameState().isGameOver()) { // game not yet started
            gameScene.getGameState().setScore(0);
            gameScene.drawGrid();
            gameScene.drawScore();
            gameScene.drawSnake();
            gameScene.generateFood();
            gameScene.drawFood();
        }
        if (gameScene.getGameState().isStart() && !gameScene.getGameState().isGameOver()) { // game has started
            gameScene.drawGrid();
            gameScene.drawScore();

            gameScene.checkCollision();

            gameScene.generateFood();
            gameScene.drawFood();

            gameScene.getSnake().move();
            gameScene.drawSnake();
        }
        if (!gameScene.getGameState().isStart() && gameScene.getGameState().isGameOver()) { // game is over
            gameScene.drawGrid();
            gameScene.drawScore();
            gameScene.drawGameOverMessage();
            gameScene.getSnake().reset();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}