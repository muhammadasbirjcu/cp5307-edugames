package com.asbir.cp5307.edugames.activities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentManager;

import com.asbir.cp5307.edugames.R;
import com.asbir.cp5307.edugames.fragments.QuestionFragment;
import com.asbir.cp5307.edugames.fragments.StatusFragment;
import com.asbir.cp5307.edugames.game.Game;
import com.asbir.cp5307.edugames.game.GameBuilder;
import com.asbir.cp5307.edugames.game.GameSettings;
import com.asbir.cp5307.edugames.game.QuestionImageManager;
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
    private int winSound;
    private int completedSound;

    private SensorManager sensorManager;
    private Sensor sensor;
    ShakeDetector shakeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        winSound = soundPool.load(this, R.raw.correct, 1);
        completedSound = soundPool.load(this, R.raw.startup, 1);

        restartButton = findViewById(R.id.restart_button);

        // find fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        statusFragment = (StatusFragment) fragmentManager.findFragmentById(R.id.fragment_status);
        questionFragment = (QuestionFragment) fragmentManager.findFragmentById(R.id.fragment_question);

        gameBuilder = new GameBuilder(new QuestionImageManager(getAssets(), "celebs"));

        // load settings
        settings = new GameSettings();
        loadSettings();

        // set-up shake detector
        initSensors();

        // fab click
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartGame();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        initiateTimer();
        sensorManager.registerListener(shakeDetector, sensor, SensorManager.SENSOR_DELAY_UI);
        onUpdate(State.START_GAME);
    }

    @Override
    protected void onPause() {
        if(shakeDetector != null) sensorManager.unregisterListener(shakeDetector);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initSensors(){
        shakeDetector = new ShakeDetector();
        shakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake(int count) {
                restartGame();
            }
        });

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    }

    private void initiateTimer(){
        timer = new Timer(settings.getDuration());
        timer.setTickHandler(timer -> {
            if(timer.getIsRunning()){
                // updates the countdown timer
                statusFragment.setTimePosition(timer.getSecondsRemaining(), timer.getDuration());
            }
            if(timer.timeHasElapsed()){
                // stop the game when time has elapsed
                GameActivity.this.onUpdate(State.GAME_OVER);
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
        timer.reset();
        timer.start();
    }

    @Override
    public void onUpdate(State state) {

        switch (state) {
            case START_GAME:
                restartGame();
                break;

            case CONTINUE_GAME:
                statusFragment.setMessage(game.getFormattedGameProgression(getString(R.string.game_progression)));
                if(game.isGameOver()){
                    this.onUpdate(State.GAME_OVER);
                }else{
                    questionFragment.setQuestion(game.next());
                }
                break;

            case GAME_OVER:
                if(timer.timeHasElapsed()){
                    statusFragment.setMessage(getResources().getString(R.string.time_is_up));
                }else{
                    statusFragment.setMessage(getResources().getString(R.string.game_completed));
                }
                if(settings.isAudioEnabled()) {
                    soundPool.play(completedSound, 1, 1, 1, 0, 1);
                }
                int remainingTime = timer.getSecondsRemaining();
                timer.reset();
                questionFragment.hideAnswers();
                restartButton.setVisibility(View.INVISIBLE);

                //save to database
                getDBHelper().insert(new Score(game, remainingTime));

                break;
        }
    }

    @Override
    public void onCorrectAnswer() {
        if(settings.isAudioEnabled()) {
            soundPool.play(winSound, 1, 1, 1, 0, 1);
        }
        game.incrementScore(1);
        statusFragment.setScoreMessage(game.getFormattedScore(getString(R.string.score_status)));
    }

}