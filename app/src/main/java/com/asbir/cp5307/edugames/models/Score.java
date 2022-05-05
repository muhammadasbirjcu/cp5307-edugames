package com.asbir.cp5307.edugames.models;

import com.asbir.cp5307.edugames.game.Difficulty;
import com.asbir.cp5307.edugames.game.Game;

public class Score {
    private int id;
    private String player;
    private Difficulty difficulty;
    private String date;
    private int score;
    private int timeElapsed;
    private int numQuestions;


    public Score(String player, Difficulty difficulty, String date, int score, int timeElapsed, int numQuestions) {
        this.player = player;
        this.difficulty = difficulty;
        this.date = date;
        this.score = score;
        this.timeElapsed = timeElapsed;
        this.numQuestions = numQuestions;
    }

    public Score(Game game, int timeRemaining){
        this.player = game.getPlayer();
        this.difficulty = game.getDifficulty();
        this.score = game.getScore();
        this.timeElapsed = timeElapsed;
        this.numQuestions = game.count();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTimeElapsed() {
        return timeElapsed;
    }

    public void setTimeElapsed(int timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    public int getNumQuestions() {
        return numQuestions;
    }

    public void setNumQuestions(int numQuestions) {
        this.numQuestions = numQuestions;
    }

    public float getScorePct() {
        return (numQuestions == 0 ? 0 : (((float)score / numQuestions) * 100));
    }
}
