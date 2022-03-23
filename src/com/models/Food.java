package com.models;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.util.Random;

public class Food {
    private final Random random;

    private Point2D position;
    private int currentColor;
    private boolean eaten;

    public final Color[] COLORS = {
            Color.CRIMSON,
            Color.ORANGE,
            Color.YELLOW,
            Color.PURPLE,
            Color.ALICEBLUE,
            Color.LAWNGREEN,
            Color.WHITESMOKE
    };

    public Food() {
        this.position = null;
        this.random = new Random();
        this.currentColor = random.nextInt(COLORS.length);
        this.eaten = false;
    }

    public Point2D generateNewPosition(GameScene gameScene) {
        int xPosition = (int) (1 + (Math.random() * (gameScene.GRID_ROWS_COLUMNS - 2))) * gameScene.CELL_SIZE;
        int yPosition = (int) (1 + (Math.random() * (gameScene.GRID_ROWS_COLUMNS - 2))) * gameScene.CELL_SIZE;
        return new Point2D(xPosition, yPosition);
    }

    public void generateColor() {
        this.currentColor = random.nextInt(COLORS.length);
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public int getCurrentColor() {
        return currentColor;
    }

    public boolean getIsEaten() {
        return eaten;
    }

    public void setIsEaten(boolean isEaten) {
        this.eaten = isEaten;
    }
}