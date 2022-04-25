package com.asbir.cp5307.edugames.activities;

import androidx.appcompat.app.AppCompatActivity;

import com.asbir.cp5307.edugames.game.GameSettings;

public class BaseActivity  extends AppCompatActivity {
    protected GameSettings settings;

    public void loadSettings()
    {
        settings.load(getSharedPreferences(GameSettings.PREFERENCE_KEY, MODE_PRIVATE));
    }

    public void saveSettings()
    {
        settings.save(getSharedPreferences(GameSettings.PREFERENCE_KEY, MODE_PRIVATE).edit());
    }

    @Override
    protected void onPause() {
        saveSettings();
        super.onPause();
    }

    @Override
    protected void onResume() {
        loadSettings();
        super.onResume();
    }
}
