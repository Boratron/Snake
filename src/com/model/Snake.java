package com.model;

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
        direction = "right";
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

    public void changeDirection(String newDirection) {
        if (newDirection.equals("left") && !direction.equals("right")) {
            direction = "left";
        } else if (newDirection.equals("right") && !direction.equals("left")) {
            direction = "right";
        } else if (newDirection.equals("up") && !direction.equals("down")) {
            direction = "up";
        } else if (newDirection.equals("down") && !direction.equals("up")) {
            direction = "down";
        }
    }

    public void move() {
        int speed = 40;

        switch (direction) {
            case "left":
                xSpeed = -speed;
                ySpeed = 0;
                break;
            case "right":
                xSpeed = speed;
                ySpeed = 0;
                break;
            case "up":
                xSpeed = 0;
                ySpeed = -speed;
                break;
            case "down":
                xSpeed = 0;
                ySpeed = speed;
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

    public String getDirection() {
        return direction;
    }

    public Color getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(Color currentColor) {
        this.currentColor = currentColor;
    }
}