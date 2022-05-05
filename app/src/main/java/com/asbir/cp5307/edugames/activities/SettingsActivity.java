package com.asbir.cp5307.edugames.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.asbir.cp5307.edugames.R;
import com.asbir.cp5307.edugames.game.BackgroundMusic;
import com.asbir.cp5307.edugames.game.Difficulty;
import com.asbir.cp5307.edugames.game.GameSettings;
import com.google.android.material.slider.Slider;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

public class SettingsActivity extends BaseActivity {
    private TextInputEditText nameInput;
    private SwitchMaterial audioSwitch;
    private Spinner difficultySpinner;
    private Button doneButton;
    private TextInputEditText maxQuestionsInput;

    private String durationFormat;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        nameInput = findViewById(R.id.name_input);
        audioSwitch = findViewById(R.id.audio_switch);
        doneButton = findViewById(R.id.done_button);
        difficultySpinner = findViewById(R.id.difficulty_spinner);
        maxQuestionsInput = findViewById(R.id.max_questions_input);

        durationFormat = getResources().getString(R.string.game_duration);

        settings = new GameSettings();

        // done button listener
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLandingActivity();
            }
        });

        audioSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    if(!BackgroundMusic.instance(SettingsActivity.this).isPlaying()){
                        BackgroundMusic.instance(SettingsActivity.this).start();
                    }
                }else{
                    BackgroundMusic.instance(SettingsActivity.this).stop();
                }
            }
        });

        // set the spinner values
        ArrayAdapter<Difficulty> difficultiesAdapter = new ArrayAdapter<Difficulty>(this, R.layout.spinner_list_item, Difficulty.values());
        difficultiesAdapter.setDropDownViewResource(R.layout.spinner_item);
        difficultySpinner.setAdapter(difficultiesAdapter);
    }


    @Override
    protected void onPause() {
        uiToSettings();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        settingsToUI();
    }

    private void settingsToUI(){
        // set initial values
        nameInput.setText(settings.getPlayerName());
        audioSwitch.setChecked(settings.isAudioEnabled());
        difficultySpinner.setSelection(settings.getDifficulty().ordinal());
        maxQuestionsInput.setText(String.valueOf(settings.getMaxQuestions()));
    }

    private void uiToSettings(){
        // set initial values
        settings.setPlayerName(nameInput.getText().toString().trim());
        settings.setAudioEnabled(audioSwitch.isChecked());
        settings.setDifficulty(Difficulty.values()[difficultySpinner.getSelectedItemPosition()]);
        settings.setMaxQuestions(Integer.valueOf(maxQuestionsInput.getText().toString()));
    }

    private void startLandingActivity(){
        Intent gameIntent = new Intent(this, LandingActivity.class);
        startActivity(gameIntent);
        finish();
    }
}