package com.asbir.cp5307.edugames.game;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Question {
    private String question;
    private String correctAnswer;
    private Bitmap illustration;
    private ArrayList<String> answers;

    public Question(String correctAnswer, Bitmap illustration, String[] answers){
        this.correctAnswer = correctAnswer;
        this.illustration = illustration;
        this.answers = new ArrayList<>();
        this.question = "";
    }

    public Question(String question){
        this.question = question;
        this.correctAnswer = "";
        this.illustration = null;
        this.answers = new ArrayList<>();
    }

    public boolean check(String chosenAnswer){
        return answers.contains(chosenAnswer);
    }

    public Bitmap getIllustration(){
        return illustration;
    }

    public String[] getAnswers(){
        return answers.toArray(new String[]{});
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void setIllustration(Bitmap illustration) {
        this.illustration = illustration;
    }

    public void addAnswer(String answer) {
        this.answers.add(answer);
    }
}
