package com.asbir.cp5307.edugames.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import com.asbir.cp5307.edugames.R;
import com.asbir.cp5307.edugames.fragments.QuestionFragment;
import com.asbir.cp5307.edugames.fragments.StatusFragment;
import com.asbir.cp5307.edugames.game.Difficulty;
import com.asbir.cp5307.edugames.game.Game;
import com.asbir.cp5307.edugames.game.GameBuilder;
import com.asbir.cp5307.edugames.game.QuestionImageManager;
import com.asbir.cp5307.edugames.game.state.State;
import com.asbir.cp5307.edugames.game.state.StateListener;
import com.asbir.cp5307.edugames.timer.Timer;

public class GameActivity extends AppCompatActivity implements StateListener {

    private StatusFragment statusFragment;
    private QuestionFragment questionFragment;
    private GameBuilder gameBuilder;
    private Game game;
    private Timer timer;
    private Difficulty level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // find fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        statusFragment = (StatusFragment) fragmentManager.findFragmentById(R.id.fragment_status);
        questionFragment = (QuestionFragment) fragmentManager.findFragmentById(R.id.fragment_question);

        gameBuilder = new GameBuilder(new QuestionImageManager(getAssets(), "celebs"));

        level = Difficulty.MEDIUM;
    }

    @Override
    protected void onStart() {
        super.onStart();
        initiateTimer();
        onUpdate(State.START_GAME);
    }

    private void initiateTimer(){
        timer = new Timer(20);
        timer.setTickHandler(timer -> {
            if(timer.getIsRunning()){
                // updates the countdown timer
                statusFragment.setMessage(timer.toString());
            }
            if(timer.timeHasElapsed()){
                // stop the game when time has elapsed
                GameActivity.this.onUpdate(State.GAME_OVER);
            }
        });
    }

    @Override
    public void onUpdate(State state) {

        switch (state) {
            case START_GAME:
                game = gameBuilder.create(level);
                game.setPlayer("");
                questionFragment.setQuestion(game.next());
                questionFragment.show();
                timer.start();
                break;
            case CONTINUE_GAME:
                statusFragment.setScoreMessage(game.getFormattedScore());
                if(game.isGameOver()){
                    this.onUpdate(State.GAME_OVER);
                }else{
                    questionFragment.setQuestion(game.next());
                }
                break;
            case GAME_OVER:
                timer.reset();
                statusFragment.setMessage("Game Over!");
                questionFragment.hideAnswers();
                break;
        }
    }

    @Override
    public void onCorrectAnswer() {
        game.incrementScore(1);
        statusFragment.setScoreMessage(game.getFormattedScore());
    }
}