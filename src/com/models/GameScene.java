package com.models;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameScene extends Scene {
    private final GraphicsContext graphicsContext;
    private final Snake snake;
    private final Food food;

    private boolean start;
    private boolean gameOver;
    private int score;

    public final int GRID_ROWS_COLUMNS = 20;
    public final int CELL_SIZE;

    public GameScene(GraphicsContext graphicsContext, Group root, double screenWidth, double screenHeight, Color color) {
        super(root, screenWidth, screenHeight, color);
        // width or height can be used to divide because there the same size
        this.CELL_SIZE = (int) screenWidth / GRID_ROWS_COLUMNS;

        this.graphicsContext = graphicsContext;
        this.snake = new Snake();
        this.food = new Food();
        this.start = false;
        this.gameOver = false;
        this.score = 0;

        this.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.LEFT) {
                snake.changeDirection("left");
            }
            if (!gameOver) {
                if (keyEvent.getCode() == KeyCode.RIGHT) {
                    snake.changeDirection("right");
                    start = true;
                }
                if (keyEvent.getCode() == KeyCode.UP) {
                    snake.changeDirection("up");
                    start = true;
                }
                if (keyEvent.getCode() == KeyCode.DOWN) {
                    snake.changeDirection("down");
                    start = true;
                }
            }
            if (keyEvent.getCode() == KeyCode.R) {
                gameOver = false;
            }
        });
    }

    public void drawGameOverMessage() {
        graphicsContext.setFill(Color.CRIMSON);
        graphicsContext.setFont(new Font("Digital-7", 70));
        graphicsContext.fillText("GAME OVER", 205, 400);
        graphicsContext.fillText("Press R to Reset", 150, 480);
    }

    public void drawScore() {
        graphicsContext.setFill(Color.LAWNGREEN);
        graphicsContext.setFont(new Font("Digital-7", 30));
        graphicsContext.fillText("Score: " + this.score, 40, 30);
    }

    public void drawGrid() {
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.setStroke(Color.WHITE);

        for (int i = 0; i < GRID_ROWS_COLUMNS; ++i) {
            for (int j = 0; j < GRID_ROWS_COLUMNS; ++j) {
                graphicsContext.fillRect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                if ((i > 0 && i < GRID_ROWS_COLUMNS - 1) && (j > 0 && j < GRID_ROWS_COLUMNS - 1)) {
                    graphicsContext.strokeRect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }
    }

    public void drawSnake() {
        for (Point2D bodyPart : snake.getBody()) {
            graphicsContext.setFill(snake.getCurrentColor());
            graphicsContext.setStroke(Color.BLACK);

            graphicsContext.strokeRect(bodyPart.getX(), bodyPart.getY(), CELL_SIZE, CELL_SIZE);
            graphicsContext.fillRect(bodyPart.getX(), bodyPart.getY(), CELL_SIZE, CELL_SIZE);
        }
    }

    public void generateFood() {
        if (food.getIsEaten()) {
            food.generateNewPosition(this);
            food.generateColor();
            food.setIsEaten(false);
        }
        if (food.getPosition() == null || snake.getBody().contains(food.getPosition())) {
            do {
                food.generateNewPosition(this);
            } while (snake.getBody().contains(food.getPosition()));
        }
    }

    public void drawFood() {
        graphicsContext.setFill(food.COLORS[food.getCurrentColor()]);
        graphicsContext.setStroke(Color.BLACK);

        graphicsContext.fillRect(
                food.getPosition().getX(),
                food.getPosition().getY(),
                CELL_SIZE,
                CELL_SIZE
        );
        graphicsContext.strokeRect(
                food.getPosition().getX(),
                food.getPosition().getY(),
                CELL_SIZE,
                CELL_SIZE
        );
    }

    public void checkCollision() {
        if (snake.getBody().get(snake.getBody().size() - 1).equals(food.getPosition())) { // snake eats food
            snake.incrementLength();
            snake.setCurrentColor(food.COLORS[food.getCurrentColor()]);
            food.setIsEaten(true);
        } else if (snake.getBody().get(snake.getBody().size() - 1).getX() < CELL_SIZE || // snake is out of bounds
                snake.getBody().get(snake.getBody().size() - 1).getY() < CELL_SIZE ||
                snake.getBody().get(snake.getBody().size() - 1).getX() > CELL_SIZE * (GRID_ROWS_COLUMNS - 2) ||
                snake.getBody().get(snake.getBody().size() - 1).getY() > CELL_SIZE * (GRID_ROWS_COLUMNS - 2)) {
            gameOver = true;
            start = false;
        } else { // snake eats its own tail
            for (int i = 1; i < snake.getBody().size(); ++i) {
                if (snake.getBody().get(snake.getBody().size() - 1).equals(snake.getBody().get(i - 1))) {
                    gameOver = true;
                    start = false;
                    break;
                }
            }
        }
    }

    public void incrementScore() {
        this.score++;
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

    public void setScore(int score) {
        this.score = score;
    }
}