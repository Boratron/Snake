package com.model;

import com.Main;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.util.Random;

public class Food {
    private final Random random;

    private Point2D position;
    private int currentColor;

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
        generateNewPosition();
        this.random = new Random();
        this.currentColor = random.nextInt(COLORS.length);
    }

    public void generateNewPosition() {
        int xPosition = (int) (1 + (Math.random() * (Main.GRID_ROWS_COLUMNS - 2))) * Main.CELL_SIZE;
        int yPosition = (int) (1 + (Math.random() * (Main.GRID_ROWS_COLUMNS - 2))) * Main.CELL_SIZE;
        this.position = new Point2D(xPosition, yPosition);
    }

    public void generateColor() {
        this.currentColor = random.nextInt(COLORS.length);
    }

    public Point2D getPosition() {
        return position;
    }

    public int getCurrentColor() {
        return currentColor;
    }
}