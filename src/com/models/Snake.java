package com.models;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private final Point2D defaultPosition; // snake head
    private final List<Point2D> body;

    private int xSpeed;
    private int ySpeed;
    private String direction;
    private int length;
    private Color currentColor;

    public Snake() {
        defaultPosition = new Point2D(360, 440);
        xSpeed = 0;
        ySpeed = 0;
        direction = "right"; // default facing direction
        body = new ArrayList<>(); // end of the arraylist will be the snake's head
        body.add(defaultPosition);
        length = 1;
        currentColor = Color.LAWNGREEN;
    }

    public void reset() {
        body.clear();
        body.add(defaultPosition);
        direction = "right";
        length = 1;
    }

    public void changeDirection(String currentDirection) {
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

    public void move() {
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

        int xHeadPosition = (int) body.get(body.size() - 1).getX() + xSpeed;
        int yHeadPosition = (int) body.get(body.size() - 1).getY() + ySpeed;

        body.add(new Point2D(xHeadPosition, yHeadPosition));

        if (body.size() > length) body.remove(0);
    }

    public void incrementLength() {
        ++length;
    }

    public List<Point2D> getBody() {
        return body;
    }

    public Color getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(Color currentColor) {
        this.currentColor = currentColor;
    }
}