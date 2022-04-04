package com.view;

import com.Main;
import com.model.Food;
import com.model.GameState;
import com.model.Snake;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameView {
    private final GraphicsContext graphicsContext;

    public GameView(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    public void drawGameOverMessage() {
        graphicsContext.setFill(Color.CRIMSON);
        graphicsContext.setFont(new Font("Digital-7", 70));
        graphicsContext.fillText("GAME OVER", 205, 400);
        graphicsContext.fillText("Press R to Reset", 150, 480);
    }

    public void drawScore(GameState gameState) {
        graphicsContext.setFill(Color.LAWNGREEN);
        graphicsContext.setFont(new Font("Digital-7", 30));
        graphicsContext.fillText("Score: " + gameState.getScore(), 40, 30);
    }

    public void drawHighScore(GameState gameState) {
        graphicsContext.setFill(Color.LAWNGREEN);
        graphicsContext.setFont(new Font("Digital-7", 30));
        graphicsContext.fillText("High Score: " + gameState.getHighScore(), 550, 30);
    }

    public void drawGrid() {
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.setStroke(Color.WHITE);

        for (int i = 0; i < Main.GRID_ROWS_COLUMNS; ++i) {
            for (int j = 0; j < Main.GRID_ROWS_COLUMNS; ++j) {
                graphicsContext.fillRect(i * Main.CELL_SIZE, j * Main.CELL_SIZE, Main.CELL_SIZE, Main.CELL_SIZE);
                if ((i > 0 && i < Main.GRID_ROWS_COLUMNS - 1) && (j > 0 && j < Main.GRID_ROWS_COLUMNS - 1)) {
                    graphicsContext.strokeRect(i * Main.CELL_SIZE, j * Main.CELL_SIZE, Main.CELL_SIZE, Main.CELL_SIZE);
                }
            }
        }
    }

    public void eraseGrid() {
        for (int i = 0; i < Main.GRID_ROWS_COLUMNS; ++i) {
            for (int j = 0; j < Main.GRID_ROWS_COLUMNS; ++j) {
                graphicsContext.clearRect(i * Main.CELL_SIZE, j * Main.CELL_SIZE, Main.CELL_SIZE, Main.CELL_SIZE);
            }
        }
    }

    public void drawSnake(Snake snake) {
        for (Point2D bodyPart : snake.getBody()) {
            graphicsContext.setFill(snake.getCurrentColor());
            graphicsContext.setStroke(Color.BLACK);

            graphicsContext.strokeRect(bodyPart.getX(), bodyPart.getY(), Main.CELL_SIZE, Main.CELL_SIZE);
            graphicsContext.fillRect(bodyPart.getX(), bodyPart.getY(), Main.CELL_SIZE, Main.CELL_SIZE);
        }
    }

    public void drawFood(Food food) {
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