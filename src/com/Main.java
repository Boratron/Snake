package com;

import com.models.*;
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
    private static int score;

    static {
        score = 0;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(SCREEN_WIDTH_HEIGHT, SCREEN_WIDTH_HEIGHT);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        Group root = new Group();

        Snake snake = new Snake();
        Food food = new Food();

        GameScene gameScene = new GameScene(
                graphicsContext, snake, food, root, SCREEN_WIDTH_HEIGHT, SCREEN_WIDTH_HEIGHT, Color.BLACK);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(76), e -> run(gameScene)));
        timeline.setCycleCount(Animation.INDEFINITE);

        root.getChildren().add(canvas);

        primaryStage.setTitle("Snake");
        primaryStage.setScene(gameScene);

        primaryStage.setResizable(false);
        primaryStage.show();

        timeline.play();
    }

    private static void run(GameScene gameScene) {
        if (!gameScene.isStart() && !gameScene.isGameOver()) {
            score = 0;
            gameScene.drawGrid();
            gameScene.drawScore(score);
            gameScene.drawSnake();
            gameScene.generateFood();
            gameScene.drawFood();
        }
        if (gameScene.isStart() && !gameScene.isGameOver()) {
            gameScene.drawGrid();
            gameScene.drawScore(score);

            gameScene.checkCollision();

            if (gameScene.getFood().getIsEaten()) ++score;

            gameScene.generateFood();
            gameScene.drawFood();

            gameScene.getSnake().move();
            gameScene.drawSnake();
        }
        if (!gameScene.isStart() && gameScene.isGameOver()) {
            gameScene.drawGrid();
            gameScene.drawScore(score);
            gameScene.drawGameOverMessage();
            gameScene.getSnake().reset();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}