package com.asbir.cp5307.edugames.game;

public class Game {
    private Question[] questions;
    private int pointer;
    private int score;


    public Game(Question[] questions){
        this.pointer = -1;
        this.score = 0;
        this.questions = questions;
    }

    /**
     * Check if current question is the last in the game.
     * @return
     */
    public boolean isGameOver(){
        return this.pointer >= (this.questions.length - 1);
    }

    /**
     * Move to the next question
     * @return the next question
     */
    public Question next(){
        this.pointer++;
        return this.questions[this.pointer];
    }

    /**
     * Increment score
     * @param step
     */
    public void incrementScore(int step){
        this.score += step;
    }

    /**
     * Get formatted score
     * @return
     */
    public String getFormattedScore(){
        return String.format("Score: %d/%d",this.score, this.questions.length);
    }

    /**
     * Get the score
     * @return
     */
    public int getScore(){
        return this.score;
    }

    /**
     * Counts the number of questions in this game
     * @return
     */
    public int count(){
        return this.questions.length;
    }
}
