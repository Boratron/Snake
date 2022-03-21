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
    public final int CELL_SIZE = GRID_WIDTH_HEIGHT / GRID_ROWS_COLUMNS; // 40

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
                graphicsContext.fillRect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                if ((i > 0 && i < GRID_ROWS_COLUMNS - 1) && (j > 0 && j < GRID_ROWS_COLUMNS - 1)) {
                    graphicsContext.strokeRect(i * CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }
    }

    public void drawSnake() {
        for (Point2D bodyPart : snake.getBody()) {
            graphicsContext.setStroke(Color.BLACK);
            graphicsContext.setFill(snake.getCurrentColor());

            graphicsContext.strokeRect(bodyPart.getX(), bodyPart.getY(), CELL_SIZE, CELL_SIZE);
            graphicsContext.fillRect(bodyPart.getX(), bodyPart.getY(), CELL_SIZE, CELL_SIZE);
        }
    }

    public void generateFood() {
        if (food.getPosition() == null || food.getPosition().equals(snake.getDefaultPosition())) {
            Point2D position;
            do {
                position = food.generateNewPosition(this);
            } while (position.equals(snake.getBody().get(snake.getBody().size() - 1)));
            food.setPosition(position);
        }
        if (food.getIsEaten()) {
            Point2D foodPosition = food.generateNewPosition(this);
            for (Point2D body : snake.getBody()) {
                if (foodPosition.equals(body)) { // generate new position if food spawn on the snake's body
                    foodPosition = food.generateNewPosition(this);
                } else {
                    food.setPosition(foodPosition);
                }
            }
            food.generateColor();
            food.setIsEaten(false);
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
                if (snake.getBody().get(0).equals(snake.getBody().get(i))) {
                    gameOver = true;
                    start = false;
                    break;
                }
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