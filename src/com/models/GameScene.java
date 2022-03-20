package com.models;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameScene extends Scene {
    public final int GRID_WIDTH_HEIGHT = 800; // 800 x 800
    public final int GRID_ROWS_COLUMNS = 20; // 20 x 20
    public final int SQUARE_SIZE = GRID_WIDTH_HEIGHT / GRID_ROWS_COLUMNS; // 40

    private final GraphicsContext graphicsContext;

    private final Snake snake;
    private final Food food;

    private boolean start;
    private boolean gameOver;

    public GameScene(GraphicsContext graphicsContext, Snake snake, Food food, Group root, double width, double height, Color color) {
        super(root, width, height, color);
        this.graphicsContext = graphicsContext;
        this.snake = snake;
        this.food = food;
        this.start = false;
        this.gameOver = false;

        this.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.LEFT) {
                snake.changeDirection("left");
            }
            if (keyEvent.getCode() == KeyCode.RIGHT) {
                snake.changeDirection("right");
            }
            if (keyEvent.getCode() == KeyCode.UP) {
                snake.changeDirection("up");
            }
            if (keyEvent.getCode() == KeyCode.DOWN) {
                snake.changeDirection("down");
            }
            if (keyEvent.getCode() == KeyCode.ENTER) {
                start = true;
            }
            if (keyEvent.getCode() == KeyCode.R) {
                gameOver = false;
            }
        });
    }

    public void drawStartMessage() {
        graphicsContext.setFill(Color.WHITESMOKE);
        graphicsContext.setFont(new Font("Digital-7", 65));
        graphicsContext.fillText("Press Enter to Start", 120, 400);
    }

    public void drawGameOverMessage() {
        graphicsContext.setFill(Color.CRIMSON);
        graphicsContext.setFont(new Font("Digital-7", 70));
        graphicsContext.fillText("GAME OVER", 205, 400);
        graphicsContext.fillText("Press R to Reset", 150, 480);
    }

    public void drawScore(int score) {
        graphicsContext.setFill(Color.LAWNGREEN);
        graphicsContext.setFont(new Font("Digital-7", 30));
        graphicsContext.fillText("Score: " + score, 40, 30);
    }

    public void drawGrid() {
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.setStroke(Color.WHITE);

        for (int i = 0; i < GRID_ROWS_COLUMNS; ++i) {
            for (int j = 0; j < GRID_ROWS_COLUMNS; ++j) {
                graphicsContext.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                if ((i > 0 && i < GRID_ROWS_COLUMNS - 1) && (j > 0 && j < GRID_ROWS_COLUMNS - 1)) {
                    graphicsContext.strokeRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
                }
            }
        }
    }

    public void drawSnake() {
        for (Point2D bodyPart : snake.getBody()) {
            graphicsContext.setStroke(Color.BLACK);
            graphicsContext.setFill(snake.getCurrentColor());

            graphicsContext.strokeRect(bodyPart.getX(), bodyPart.getY(), SQUARE_SIZE, SQUARE_SIZE);
            graphicsContext.fillRect(bodyPart.getX(), bodyPart.getY(), SQUARE_SIZE, SQUARE_SIZE);
        }
    }

    public void spawnSnake() {
        graphicsContext.setFill(snake.getCurrentColor());
        graphicsContext.setStroke(Color.BLACK);

        graphicsContext.fillRect(
                snake.getDefaultPosition().getX(),
                snake.getDefaultPosition().getY(),
                SQUARE_SIZE,
                SQUARE_SIZE
        );
        graphicsContext.strokeRect(
                snake.getDefaultPosition().getX(),
                snake.getDefaultPosition().getY(),
                SQUARE_SIZE,
                SQUARE_SIZE
        );
    }

    public void spawnFood() {
        Point2D position;
        do {
            position = food.generateNewPosition(this);
        } while (position.equals(snake.getBody().get(snake.getBody().size() - 1)));
        food.setPosition(position);
    }

    public void generateFood() {
        if (food.getIsEaten()) {
            Point2D foodPosition;
            boolean running = true;

            while (running) {
                foodPosition = food.generateNewPosition(this);

                for (Point2D position : snake.getBody()) {
                    if (position.getX() == foodPosition.getX() && position.getY() == foodPosition.getY()) { // generate new position if food spawn on the snake's body
                        break;
                    }
                    running = false;
                }
                food.setPosition(foodPosition);
            }
            food.generateColor();
            food.setIsEaten(false);
            System.out.println("eat");
        }
    }

    public void drawFood() {
        graphicsContext.setFill(food.COLORS[food.getCurrentColor()]);
        graphicsContext.setStroke(Color.BLACK);

        graphicsContext.fillRect(
                food.getPosition().getX(),
                food.getPosition().getY(),
                SQUARE_SIZE,
                SQUARE_SIZE
        );
        graphicsContext.strokeRect(
                food.getPosition().getX(),
                food.getPosition().getY(),
                SQUARE_SIZE,
                SQUARE_SIZE
        );
    }

    public void snakeFoodCollision() {
        if (
                snake.getBody().get(snake.getBody().size() - 1).getX() == food.getPosition().getX() &&
                        snake.getBody().get(snake.getBody().size() - 1).getY() == food.getPosition().getY()
        ) {
            snake.incrementLength();
            snake.setCurrentColor(food.getCOLORS()[food.getCurrentColor()]);

            food.setIsEaten(true);
        }
    }

    public void snakeOutOfBoundsCollision() {
        if (
                snake.getBody().get(snake.getBody().size() - 1).getX() < SQUARE_SIZE ||
                        snake.getBody().get(snake.getBody().size() - 1).getY() < SQUARE_SIZE ||
                        snake.getBody().get(snake.getBody().size() - 1).getX() > SQUARE_SIZE * (GRID_ROWS_COLUMNS - 2) ||
                        snake.getBody().get(snake.getBody().size() - 1).getY() > SQUARE_SIZE * (GRID_ROWS_COLUMNS - 2)
        ) {
            gameOver = true;
            start = false;
        }
    }

    public void snakeTailCollision() {
        for (int i = 1; i < snake.getBody().size(); ++i) {
            if (
                    snake.getBody().get(0).getX() == snake.getBody().get(i).getX() &&
                            snake.getBody().get(0).getY() == snake.getBody().get(i).getY()
            ) {
                gameOver = true;
                start = false;
                break;
            }
        }
    }

    public Snake getSnake() {
        return snake;
    }

    public Food getFood() {
        return food;
    }

    public boolean isStart() {
        return start;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}