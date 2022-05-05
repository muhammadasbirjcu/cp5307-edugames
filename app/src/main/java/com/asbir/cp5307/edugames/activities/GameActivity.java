package com.asbir.cp5307.edugames.activities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentManager;

import com.asbir.cp5307.edugames.R;
import com.asbir.cp5307.edugames.fragments.QuestionFragment;
import com.asbir.cp5307.edugames.fragments.StatusFragment;
import com.asbir.cp5307.edugames.game.SoundEffect;
import com.asbir.cp5307.edugames.game.Game;
import com.asbir.cp5307.edugames.game.GameBuilder;
import com.asbir.cp5307.edugames.game.GameSettings;
import com.asbir.cp5307.edugames.game.state.State;
import com.asbir.cp5307.edugames.game.state.StateListener;
import com.asbir.cp5307.edugames.models.Score;
import com.asbir.cp5307.edugames.sensors.ShakeDetector;
import com.asbir.cp5307.edugames.timer.Timer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class GameActivity extends BaseActivity implements StateListener {

    private StatusFragment statusFragment;
    private QuestionFragment questionFragment;
    private FloatingActionButton restartButton;
    private GameBuilder gameBuilder;
    private Game game;
    private Timer timer;

    private SensorManager sensorManager;
    private Sensor sensor;
    ShakeDetector shakeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);



        // find fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        statusFragment = (StatusFragment) fragmentManager.findFragmentById(R.id.fragment_status);
        questionFragment = (QuestionFragment) fragmentManager.findFragmentById(R.id.fragment_question);

        // create game builder
        gameBuilder = new GameBuilder(getAssets());

        // load settings
        settings = new GameSettings();
        loadSettings();

        // set-up shake detector
        initSensors();

        // restart game fab
        restartButton = findViewById(R.id.restart_button);
        restartButton.setOnClickListener(view -> restartGame());

        // set-up timer
        initiateTimer();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // start game
        onUpdate(State.START_GAME);
        sensorManager.registerListener(shakeDetector, sensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        if(shakeDetector != null) sensorManager.unregisterListener(shakeDetector);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        timer.release();
        super.onDestroy();
    }

    private void initSensors(){
        shakeDetector = new ShakeDetector();
        shakeDetector.setOnShakeListener(count -> restartGame());

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    }

    private void initiateTimer(){
        timer = new Timer();
        timer.setTickHandler(timer -> {
            if(timer.getIsRunning()){
                statusFragment.setTimerText(timer.toString());
            }
        });
    }

    private void restartGame(){
        restartButton.setVisibility(View.VISIBLE);
        game = gameBuilder.create(settings.getDifficulty(), settings.getMaxQuestions());
        game.setPlayer(settings.getPlayerName());

        questionFragment.setQuestion(game.next());
        questionFragment.show();

        statusFragment.setScoreMessage(game.getFormattedScore(getString(R.string.score_status)));
        statusFragment.setMessage(game.getFormattedGameProgression(getString(R.string.game_progression)));
        statusFragment.setPosition(0, game.count());
        timer.reset();
        timer.start();
        statusFragment.setTimerText(timer.toString());
    }

    @Override
    public void onUpdate(State state) {

        switch (state) {
            case START_GAME:
                restartGame();
                break;

            case CONTINUE_GAME:
                statusFragment.setMessage(game.getFormattedGameProgression(getString(R.string.game_progression)));
                statusFragment.setPosition(game.getPointer()+1, game.count());
                if(game.isGameOver()){
                    this.onUpdate(State.GAME_OVER);
                }else{
                    questionFragment.setQuestion(game.next());
                }
                break;

            case GAME_OVER:
                statusFragment.setMessage(getResources().getString(R.string.game_completed));

                if(settings.isAudioEnabled()) {
                    SoundEffect.instance().play(this, R.raw.startup, 0);
                }
                timer.reset();
                questionFragment.hideAnswers();
                restartButton.setVisibility(View.INVISIBLE);

                //save to database
                getDBHelper().insert(new Score(game, timer.getDuration()));

                break;
        }
    }

    @Override
    public void onCorrectAnswer() {
        if(settings.isAudioEnabled()) {
            SoundEffect.instance().play(this, R.raw.correct, 0);
        }
        game.incrementScore(1);
        statusFragment.setScoreMessage(game.getFormattedScore(getString(R.string.score_status)));
    }

}