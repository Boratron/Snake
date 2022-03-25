package com.scene;

import com.model.Food;
import com.model.GameState;
import com.model.Snake;
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
    private final GameState gameState;

    public final int GRID_ROWS_COLUMNS = 20;
    public final int CELL_SIZE;

    public GameScene(GraphicsContext graphicsContext, Group root, double screenWidth, double screenHeight, Color color) {
        super(root, screenWidth, screenHeight, color);
        // width or height can be used to divide because there the same size
        this.CELL_SIZE = (int) screenWidth / GRID_ROWS_COLUMNS;

        this.graphicsContext = graphicsContext;
        this.snake = new Snake();
        this.food = new Food();
        this.gameState = new GameState();

        this.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.LEFT) {
                snake.changeDirection("left");
            }
            if (!gameState.isGameOver()) {
                if (keyEvent.getCode() == KeyCode.RIGHT) {
                    snake.changeDirection("right");
                    gameState.setStart(true);
                }
                if (keyEvent.getCode() == KeyCode.UP) {
                    snake.changeDirection("up");
                    gameState.setStart(true);
                }
                if (keyEvent.getCode() == KeyCode.DOWN) {
                    snake.changeDirection("down");
                    gameState.setStart(true);
                }
            }
            if (keyEvent.getCode() == KeyCode.R) {
                gameState.setGameOver(false);
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
        graphicsContext.fillText("Score: " + gameState.getScore(), 40, 30);
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

    public void checkCollision() {
        if (snake.getBody().get(snake.getBody().size() - 1).equals(food.getPosition())) { // snake eats food
            snake.incrementLength();
            snake.setCurrentColor(food.COLORS[food.getCurrentColor()]);
            food.setIsEaten(true);
        } else if (snake.getBody().get(snake.getBody().size() - 1).getX() < CELL_SIZE || // snake is out of bounds
                snake.getBody().get(snake.getBody().size() - 1).getY() < CELL_SIZE ||
                snake.getBody().get(snake.getBody().size() - 1).getX() > CELL_SIZE * (GRID_ROWS_COLUMNS - 2) ||
                snake.getBody().get(snake.getBody().size() - 1).getY() > CELL_SIZE * (GRID_ROWS_COLUMNS - 2)) {
            gameState.setGameOver(true);
            gameState.setStart(false);
        } else { // snake eats its own tail
            for (int i = 1; i < snake.getBody().size(); ++i) {
                if (snake.getBody().get(snake.getBody().size() - 1).equals(snake.getBody().get(i - 1))) {
                    gameState.setGameOver(true);
                    gameState.setStart(false);
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

    public GameState getGameState() {
        return gameState;
    }
}