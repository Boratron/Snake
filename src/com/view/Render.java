package com.view;

import com.Main;
import com.model.Food;
import com.model.GameState;
import com.model.Snake;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Render {
    private final GraphicsContext graphicsContext;

    public Render(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    public void renderGameOverMessage() {
        graphicsContext.setFill(Color.CRIMSON);
        graphicsContext.setFont(new Font("Digital-7", 70));
        graphicsContext.fillText("GAME OVER", 205, 400);
        graphicsContext.fillText("Press R to Reset", 150, 480);
    }

    public void renderScore(GameState gameState) {
        graphicsContext.setFill(Color.LAWNGREEN);
        graphicsContext.setFont(new Font("Digital-7", 30));
        graphicsContext.fillText("Score: " + gameState.getScore(), 40, 30);
    }

    public void renderHighScore(GameState gameState) {
        graphicsContext.setFill(Color.LAWNGREEN);
        graphicsContext.setFont(new Font("Digital-7", 30));
        if (gameState.isHighScoreNew()) {
            graphicsContext.fillText("New High Score: " + gameState.getHighScore(), 500, 30);
        } else {
            graphicsContext.fillText("High Score: " + gameState.getHighScore(), 550, 30);
        }
    }

    public void renderGrid() {
        graphicsContext.setStroke(Color.WHITE);
        for (int i = 1; i < Main.GRID_ROWS_COLUMNS - 1; ++i) {
            for (int j = 1; j < Main.GRID_ROWS_COLUMNS - 1; ++j) {
                graphicsContext.strokeRect(i * Main.CELL_SIZE, j * Main.CELL_SIZE, Main.CELL_SIZE, Main.CELL_SIZE);
            }
        }
    }

    public void renderBlankScreen() {
        for (int i = 0; i < Main.GRID_ROWS_COLUMNS; ++i) {
            for (int j = 0; j < Main.GRID_ROWS_COLUMNS; ++j) {
                graphicsContext.clearRect(i * Main.CELL_SIZE, j * Main.CELL_SIZE, Main.CELL_SIZE, Main.CELL_SIZE);
            }
        }
    }

    public void renderSnake(Snake snake) {
        for (Point2D bodyPart : snake.getBody()) {
            graphicsContext.setFill(snake.getCurrentColor());
            graphicsContext.setStroke(Color.BLACK);

            graphicsContext.strokeRect(bodyPart.getX(), bodyPart.getY(), Main.CELL_SIZE, Main.CELL_SIZE);
            graphicsContext.fillRect(bodyPart.getX(), bodyPart.getY(), Main.CELL_SIZE, Main.CELL_SIZE);
        }

        Point2D snakeHead = snake.getBody().get(snake.getBody().size() - 1);
        graphicsContext.setFill(Color.BLACK);

        switch (snake.getDirection()) { // snake eyes position
            case "left":
                graphicsContext.fillRoundRect(snakeHead.getX() + 10, snakeHead.getY() + 5, Main.CELL_SIZE - 30,
                        Main.CELL_SIZE - 30, Main.CELL_SIZE - 30, Main.CELL_SIZE - 30);
                graphicsContext.fillRoundRect(snakeHead.getX() + 10, snakeHead.getY() + 25, Main.CELL_SIZE - 30,
                        Main.CELL_SIZE - 30, Main.CELL_SIZE - 30, Main.CELL_SIZE - 30);
                break;
            case "right":
                graphicsContext.fillRoundRect(snakeHead.getX() + 20, snakeHead.getY() + 5, Main.CELL_SIZE - 30,
                        Main.CELL_SIZE - 30, Main.CELL_SIZE - 30, Main.CELL_SIZE - 30);
                graphicsContext.fillRoundRect(snakeHead.getX() + 20, snakeHead.getY() + 25, Main.CELL_SIZE - 30,
                        Main.CELL_SIZE - 30, Main.CELL_SIZE - 30, Main.CELL_SIZE - 30);
                break;
            case "up":
                graphicsContext.fillRoundRect(snakeHead.getX() + 5, snakeHead.getY() + 10, Main.CELL_SIZE - 30,
                        Main.CELL_SIZE - 30, Main.CELL_SIZE - 30, Main.CELL_SIZE - 30);
                graphicsContext.fillRoundRect(snakeHead.getX() + 25, snakeHead.getY() + 10, Main.CELL_SIZE - 30,
                        Main.CELL_SIZE - 30, Main.CELL_SIZE - 30, Main.CELL_SIZE - 30);
                break;
            case "down":
                graphicsContext.fillRoundRect(snakeHead.getX() + 5, snakeHead.getY() + 20, Main.CELL_SIZE - 30,
                        Main.CELL_SIZE - 30, Main.CELL_SIZE - 30, Main.CELL_SIZE - 30);
                graphicsContext.fillRoundRect(snakeHead.getX() + 25, snakeHead.getY() + 20, Main.CELL_SIZE - 30,
                        Main.CELL_SIZE - 30, Main.CELL_SIZE - 30, Main.CELL_SIZE - 30);
                break;
        }
    }

    public void renderFood(Food food) {
        graphicsContext.setFill(food.COLORS[food.getCurrentColor()]);
        graphicsContext.setStroke(Color.BLACK);

        graphicsContext.fillRect(
                food.getPosition().getX(),
                food.getPosition().getY(),
                Main.CELL_SIZE,
                Main.CELL_SIZE
        );
        graphicsContext.strokeRect(
                food.getPosition().getX(),
                food.getPosition().getY(),
                Main.CELL_SIZE,
                Main.CELL_SIZE
        );
    }
}