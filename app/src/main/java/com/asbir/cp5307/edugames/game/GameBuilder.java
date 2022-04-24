package com.asbir.cp5307.edugames.game;

import android.media.Image;

import java.io.IOException;

public class GameBuilder {
    private QuestionImageManager manager;
    private int numberOfQuestions;

    public GameBuilder(QuestionImageManager manager){
        this.manager = manager;
        this.numberOfQuestions = manager.count();
    }

    /**
     * Create a new game
     * @param difficulty
     * @return
     */
    public Game create(Difficulty difficulty){
        int possibleAnswers = possibleAnswers(difficulty);

        Question[] questions = new Question[numberOfQuestions];
        for (int i = 0; i < numberOfQuestions; ++i){
            try{
                questions[i] =
                        new Question(
                                manager.getName(i),
                                manager.get(i),
                                manager.shuffleNames(i, possibleAnswers)
                        );
            }catch (IOException ex){
                int a = 0;
            }

        }
        return new Game(difficulty, questions);
    }

    /**
     * Get number of possible answers based on difficulty
     * @param difficulty
     * @return
     */
    private static int possibleAnswers(Difficulty difficulty) {

        switch (difficulty){
            case MEDIUM:
                return 4;
            case HARD:
                return 6;
            case EXPERT:
                return 8;
            case EASY:
            default:
                return 2;
        }

    }
}
