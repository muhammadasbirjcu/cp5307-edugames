package com.asbir.cp5307.edugames.game;

import android.content.Context;
import android.content.res.AssetManager;
import android.media.Image;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GameBuilder {
    AssetManager assetManager;
    public GameBuilder(AssetManager assetManager){
        this.assetManager = assetManager;
    }

    /**
     * Create a new game
     * @param difficulty
     * @return
     */
    public Game create(Difficulty difficulty, int numberOfQuestions){
        GameXMLParser parser = new GameXMLParser(difficulty, assetManager);
        List<Question> questions = new ArrayList<Question>();
        try{
            List<Question> clonedQuestions = parser.read();
            Collections.shuffle(clonedQuestions);

            // truncate list to first limit-1
            questions = clonedQuestions
                    .stream()
                    .limit(numberOfQuestions)
                    .collect(Collectors.toList());

        }catch (Exception e){

        }

        for(int i = 0; i < questions.size(); i++){
            questions.get(i).shuffleAnswers();
        }

        return new Game(difficulty, questions.toArray(new Question[]{}));

    }

}
