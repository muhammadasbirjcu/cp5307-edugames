package com.asbir.cp5307.edugames.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.asbir.cp5307.edugames.R;
import com.asbir.cp5307.edugames.game.Difficulty;
import com.asbir.cp5307.edugames.game.GameSettings;
import com.google.android.material.slider.Slider;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

public class SettingsActivity extends AppCompatActivity {
    private TextInputEditText nameInput;
    private SwitchMaterial audioSwitch;
    private TextView gameDurationText;
    private Slider durationSlider;
    private Spinner difficultySpinner;
    private Button doneButton;

    private GameSettings settings;
    private String durationFormat;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        nameInput = findViewById(R.id.name_input);
        audioSwitch = findViewById(R.id.audio_switch);
        gameDurationText = findViewById(R.id.gameDurationText);
        durationSlider = findViewById(R.id.duration_slider);
        doneButton = findViewById(R.id.done_button);
        difficultySpinner = findViewById(R.id.difficulty_spinner);
        durationFormat = getResources().getString(R.string.game_duration);

        settings = new GameSettings();
        settings.load(getSharedPreferences(GameSettings.PREFERENCE_KEY, MODE_PRIVATE));
        settingsToUI();

        // done button listener
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLandingActivity();
            }
        });

        // reflect changes in slider and show it in the textview/label
        durationSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                gameDurationText.setText(String.format(durationFormat, value));
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
        settings.save(getSharedPreferences(GameSettings.PREFERENCE_KEY, MODE_PRIVATE).edit());
        super.onPause();
    }


    @Override
    protected void onResume() {
        settings.load(getSharedPreferences(GameSettings.PREFERENCE_KEY, MODE_PRIVATE));
        settingsToUI();
        super.onResume();
    }

    private void settingsToUI(){
        // set initial values
        nameInput.setText(settings.getPlayerName());
        audioSwitch.setChecked(settings.isAudioEnabled());
        durationSlider.setValue(settings.getDuration());
        gameDurationText.setText(String.format(durationFormat, durationSlider.getValue()));
        difficultySpinner.setSelection(settings.getDifficulty().ordinal());
    }

    private void uiToSettings(){
        // set initial values
        settings.setPlayerName(nameInput.getText().toString().trim());
        settings.setAudioEnabled(audioSwitch.isChecked());
        settings.setDuration(Math.round(durationSlider.getValue()));
        settings.setDifficulty(Difficulty.values()[difficultySpinner.getSelectedItemPosition()]);
    }

    private void startLandingActivity(){
        Intent gameIntent = new Intent(this, LandingActivity.class);
        startActivity(gameIntent);
        finish();
    }
}