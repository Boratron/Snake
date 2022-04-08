package com;

import com.controller.Controller;
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

        Controller controller = new Controller(graphicsContext, gameScene);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(70), e -> run(controller)));
        timeline.setCycleCount(Animation.INDEFINITE);

        root.getChildren().add(canvas);

        primaryStage.setTitle("Snake");
        primaryStage.setScene(gameScene);

        primaryStage.setResizable(false);
        primaryStage.show();

        timeline.play();
    }

    private static void run(Controller controller) {
        if (!controller.isStart() && !controller.isGameOver()) { // game not yet started
            controller.resetScore();
            controller.checkFoodGeneratedOverlapsSnake();

            controller.clearScreen();

            controller.renderGrid();
            controller.renderScore();
            controller.renderHighScore();
            controller.renderSnake();
            controller.renderFood();
        }
        if (controller.isStart() && !controller.isGameOver()) { // game has started
            controller.checkCollision();
            controller.moveSnake();

            controller.clearScreen();

            controller.renderGrid();
            controller.renderScore();
            controller.renderHighScore();
            controller.renderFood();
            controller.renderSnake();
        }
        if (!controller.isStart() && controller.isGameOver()) { // game over
            controller.resetSnake();
            controller.compareScores();

            controller.clearScreen();

            controller.renderScore();
            controller.renderHighScore();
            controller.renderGameOverMessage();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}