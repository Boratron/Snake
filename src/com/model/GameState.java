package com.model;

public class GameState {
    private boolean start;
    private boolean gameOver;
    private boolean newHighScore;
    private int score;
    private int highScore;

    public GameState() {
        this.start = false;
        this.gameOver = false;
        this.newHighScore = false;
        this.score = 0;
        this.highScore = 0;
    }

    public void incrementScore() {
        this.score++;
    }

    public void compareScores() {
        if (highScore < score) {
            highScore = score;
            newHighScore = true;
        }
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isNewHighScore() {
        return newHighScore;
    }

    public void setNewHighScore(boolean newHighScore) {
        this.newHighScore = newHighScore;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getHighScore() {
        return highScore;
    }
}