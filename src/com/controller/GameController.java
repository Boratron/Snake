package com.controller;

import com.Main;
import com.model.Food;
import com.model.GameState;
import com.model.Snake;
import com.view.GameView;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

public class GameController {
    // models
    private final Snake snake;
    private final Food food;
    private final GameState gameState;

    // view
    private final GameView gameView;

    public GameController(GraphicsContext graphicsContext, Scene gameScene) {
        this.snake = new Snake();
        this.food = new Food();
        this.gameState = new GameState();

        this.gameView = new GameView(graphicsContext);

        gameScene.setOnKeyPressed(keyEvent -> {
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

    public void updateGameOverMessage() {
        gameView.drawGameOverMessage();
    }

    public void updateScore() {
        gameView.drawScore(gameState);
    }

    public void updateGameGrid() {
        gameView.drawGrid();
    }

    public void updateSnake() {
        gameView.drawSnake(snake);
    }

    public void updateFood() {
        gameView.drawFood(food);
    }

    public void moveSnake() {
        snake.move();
    }

    public void resetSnake() {
        snake.reset();
    }

    public void setScore(int score) {
        gameState.setScore(score);
    }

    public void generateFood() {
        if (food.getIsEaten()) {
            food.generateNewPosition();
            food.generateColor();
            food.setIsEaten(false);
        }
        if (food.getPosition() == null || snake.getBody().contains(food.getPosition())) {
            do {
                food.generateNewPosition();
            } while (snake.getBody().contains(food.getPosition()));
        }
    }

    public void checkCollision() {
        if (snake.getBody().get(snake.getBody().size() - 1).equals(food.getPosition())) { // snake eats food
            snake.incrementLength();
            gameState.incrementScore();
            snake.setCurrentColor(food.COLORS[food.getCurrentColor()]);
            food.setIsEaten(true);
        } else if (snake.getBody().get(snake.getBody().size() - 1).getX() < Main.CELL_SIZE || // snake is out of bounds
                snake.getBody().get(snake.getBody().size() - 1).getY() < Main.CELL_SIZE ||
                snake.getBody().get(snake.getBody().size() - 1).getX() > Main.CELL_SIZE * (Main.GRID_ROWS_COLUMNS - 2) ||
                snake.getBody().get(snake.getBody().size() - 1).getY() > Main.CELL_SIZE * (Main.GRID_ROWS_COLUMNS - 2)) {
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

    public boolean isStart() {
        return gameState.isStart();
    }

    public boolean isGameOver() {
        return gameState.isGameOver();
    }
}