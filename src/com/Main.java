package com;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main extends Application {
    private final static int SCREEN_SIZE_WIDTH = 800;
    private final static int SCREEN_SIZE_HEIGHT = 800;

    private final static Canvas canvas = new Canvas(GameGrid.GRID_WIDTH, GameGrid.GRID_HEIGHT);
    private final static GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
    private final static Timeline timeLine;

    private static boolean running = false;
    private static boolean gameIsOver = false;

    private static int score = 0;

    static {
        timeLine = new Timeline(new KeyFrame(Duration.millis(75), e -> run()));
        timeLine.setCycleCount(Animation.INDEFINITE);
    }

    private static class GameGrid {
        private final static int GRID_WIDTH = 800;
        private final static int GRID_HEIGHT = 800;
        private final static int GRID_ROWS = 20;
        private final static int GRID_COLUMNS = 20;
        private final static int SQUARE_SIZE = GRID_WIDTH / GRID_ROWS; // 40

        private static void drawBackground() {
            graphicsContext.setFill(Color.BLACK);
            graphicsContext.setStroke(Color.WHITE);

            for (int i = 0; i < GRID_ROWS; ++i) {
                for (int j = 0; j < GRID_COLUMNS; ++j) {
                    graphicsContext.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                    if ((i > 0 && i < GRID_ROWS - 1) && (j > 0 && j < GRID_COLUMNS - 1)) {
                        graphicsContext.strokeRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                    }
                }
            }
        }
    }

    private static class Snake {
        private static final Point2D defaultPosition = new Point2D(360, 440); // the snake head
        private static int xSpeed = 0;
        private static int ySpeed = 0;
        private static String direction = "right"; // default facing direction
        private static final List<Point2D> snakeBody = new ArrayList<>(); // end of the arraylist will be the snake's head
        private static int length = 1;
        private static Color currentColor = Color.LAWNGREEN;

        static {
            snakeBody.add(defaultPosition);
        }

        private static void reset() {
            snakeBody.clear();
            snakeBody.add(defaultPosition);
            direction = "right";
            length = 1;
        }

        private static void spawnSnake() {
            graphicsContext.setFill(currentColor);
            graphicsContext.setStroke(Color.BLACK);

            graphicsContext.fillRect(defaultPosition.getX(), defaultPosition.getY(), GameGrid.SQUARE_SIZE, GameGrid.SQUARE_SIZE);
            graphicsContext.strokeRect(defaultPosition.getX(), defaultPosition.getY(), GameGrid.SQUARE_SIZE, GameGrid.SQUARE_SIZE);
        }

        private static void changeDirection(String currentDirection) {
            if (currentDirection.equals("left") && !direction.equals("right")) {
                direction = "left";
            } else if (currentDirection.equals("right") && !direction.equals("left")) {
                direction = "right";
            } else if (currentDirection.equals("up") && !direction.equals("down")) {
                direction = "up";
            } else if (currentDirection.equals("down") && !direction.equals("up")) {
                direction = "down";
            }
        }

        private static void move() {
            switch (direction) {
                case "left":
                    xSpeed = -40;
                    ySpeed = 0;
                    break;
                case "right":
                    xSpeed = 40;
                    ySpeed = 0;
                    break;
                case "up":
                    xSpeed = 0;
                    ySpeed = -40;
                    break;
                case "down":
                    xSpeed = 0;
                    ySpeed = 40;
                    break;
            }

            int xHeadPosition = (int) snakeBody.get(snakeBody.size() - 1).getX() + xSpeed;
            int yHeadPosition = (int) snakeBody.get(snakeBody.size() - 1).getY() + ySpeed;

            snakeBody.add(new Point2D(xHeadPosition, yHeadPosition));
            //System.out.println("Snake position: " + snakeBody.get(snakeBody.size() - 1).toString());

            if (snakeBody.size() > length) snakeBody.remove(0);
            drawSnake();
        }

        private static void drawSnake() {
            for (Point2D bodyPart : snakeBody) {
                graphicsContext.setStroke(Color.BLACK);
                graphicsContext.setFill(currentColor);

                graphicsContext.strokeRect(bodyPart.getX(), bodyPart.getY(), GameGrid.SQUARE_SIZE, GameGrid.SQUARE_SIZE);
                graphicsContext.fillRect(bodyPart.getX(), bodyPart.getY(), GameGrid.SQUARE_SIZE, GameGrid.SQUARE_SIZE);
            }
        }

        private static boolean eatFood() {
            boolean isFoodEaten = false;
            if (Snake.snakeBody.get(Snake.snakeBody.size() - 1).equals(Food.position)) {
                ++length;
                ++score;
                currentColor = Food.COLORS[Food.currentColor];
                isFoodEaten = true;
            }
            return isFoodEaten;
        }

        private static boolean destroySnake() {
            boolean isGameOver = false;
            if (
                snakeBody.get(snakeBody.size() - 1).getX() < GameGrid.SQUARE_SIZE ||
                snakeBody.get(snakeBody.size() - 1).getY() < GameGrid.SQUARE_SIZE ||
                snakeBody.get(snakeBody.size() - 1).getX() > GameGrid.SQUARE_SIZE * (GameGrid.GRID_ROWS - 2) ||
                snakeBody.get(snakeBody.size() - 1).getY() > GameGrid.SQUARE_SIZE * (GameGrid.GRID_COLUMNS - 2)
            ) {
                isGameOver = true;
            } else {
                for (int i = 1; i < snakeBody.size(); ++i) {
                    if (snakeBody.get(0).equals(snakeBody.get(i))) {
                        isGameOver = true;
                        break;
                    }
                }
            }
            return isGameOver;
        }
    }

    private static class Food {
        private static Point2D position;

        private static final Color[] COLORS = {
                Color.CRIMSON,
                Color.ORANGE,
                Color.YELLOW,
                Color.PURPLE,
                Color.ALICEBLUE,
                Color.LAWNGREEN,
                Color.WHITESMOKE
        };
        private static int currentColor = new Random().nextInt(COLORS.length);

        static {
            // generate numbers between 1 to 18
            do {
                int xPosition = (int) (1 + (Math.random() * (GameGrid.GRID_ROWS - 2))) * GameGrid.SQUARE_SIZE;
                int yPosition = (int) (1 + (Math.random() * (GameGrid.GRID_COLUMNS - 2))) * GameGrid.SQUARE_SIZE;
                position = new Point2D(xPosition, yPosition);
            } while (position.equals(Snake.snakeBody.get(Snake.snakeBody.size() - 1)));
        }

        private static void generateFood() {
            if (Snake.eatFood()) {
                int xPosition = (int) (1 + (Math.random() * (GameGrid.GRID_ROWS - 2))) * GameGrid.SQUARE_SIZE;
                int yPosition = (int) (1 + (Math.random() * (GameGrid.GRID_COLUMNS - 2))) * GameGrid.SQUARE_SIZE;
                position = new Point2D(xPosition, yPosition);

                for (Point2D part : Snake.snakeBody) {
                    if (position.equals(part)) { // generate new position if food spawn on the snake's body
                        xPosition = (int) (1 + (Math.random() * (GameGrid.GRID_ROWS - 2))) * GameGrid.SQUARE_SIZE;
                        yPosition = (int) (1 + (Math.random() * (GameGrid.GRID_COLUMNS - 2))) * GameGrid.SQUARE_SIZE;
                        position = new Point2D(xPosition, yPosition);
                    }
                }
                currentColor = new Random().nextInt(COLORS.length);
                //System.out.println("Food position: " + position.toString());
            } else {
                drawFood();
            }
        }

        private static void drawFood() {
            graphicsContext.setFill(COLORS[currentColor]);
            graphicsContext.setStroke(Color.BLACK);

            graphicsContext.fillRect(position.getX(), position.getY(), GameGrid.SQUARE_SIZE, GameGrid.SQUARE_SIZE);
            graphicsContext.strokeRect(position.getX(), position.getY(), GameGrid.SQUARE_SIZE, GameGrid.SQUARE_SIZE);
        }
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();

        Scene scene = new Scene(root, SCREEN_SIZE_WIDTH, SCREEN_SIZE_HEIGHT, Color.BLACK);

        root.getChildren().add(canvas);

        primaryStage.setTitle("Snake");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        timeLine.play();

        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.LEFT) {
                Snake.changeDirection("left");
            }
            if (keyEvent.getCode() == KeyCode.RIGHT) {
                Snake.changeDirection("right");
            }
            if (keyEvent.getCode() == KeyCode.UP) {
                Snake.changeDirection("up");
            }
            if (keyEvent.getCode() == KeyCode.DOWN) {
                Snake.changeDirection("down");
            }
            if (keyEvent.getCode() == KeyCode.ENTER) {
                if (!running) running = true;
            }
            if (keyEvent.getCode() == KeyCode.R) {
                if (gameIsOver) gameIsOver = false;
            }
        });
    }

    private static void drawScore() {
        graphicsContext.setFill(Color.LAWNGREEN);
        graphicsContext.setFont(new Font("Digital-7", 30));
        graphicsContext.fillText("Score: " + score, 40, 30);
    }

    private static void pressToStartGame() {
        graphicsContext.setFill(Color.CRIMSON);
        graphicsContext.setFont(new Font("Digital-7", 65));
        graphicsContext.fillText("Press Enter to Start", 120, 400);
    }

    private static void gameOver() {
        graphicsContext.setFill(Color.CRIMSON);
        graphicsContext.setFont(new Font("Digital-7", 70));
        graphicsContext.fillText("GAME OVER", 205, 400);
        graphicsContext.fillText("Press R to Reset", 150, 480);
    }

    private static void run() {
        if (!running && !gameIsOver) {
            score = 0;
            GameGrid.drawBackground();
            drawScore();
            Snake.spawnSnake();
            pressToStartGame();
        }
        if (running && !gameIsOver) {
            GameGrid.drawBackground();
            drawScore();
            Snake.move();
            Food.generateFood();
        }
        if (Snake.destroySnake()) { // game over
            GameGrid.drawBackground();
            drawScore();
            gameOver();
            Snake.reset();
            running = false;
            gameIsOver = true;
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}