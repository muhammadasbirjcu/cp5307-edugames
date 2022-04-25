package com.asbir.cp5307.edugames.activities;

import androidx.appcompat.app.AppCompatActivity;

import com.asbir.cp5307.edugames.database.DBHelper;
import com.asbir.cp5307.edugames.game.GameSettings;

public class BaseActivity  extends AppCompatActivity {
    protected GameSettings settings;


    public void loadSettings(){
        if(settings != null) settings.load(getSharedPreferences(GameSettings.PREFERENCE_KEY, MODE_PRIVATE));
    }

    public void saveSettings(){
        if(settings != null) settings.save(getSharedPreferences(GameSettings.PREFERENCE_KEY, MODE_PRIVATE).edit());
    }

    public DBHelper getDBHelper(){
        return DBHelper.getInstance(this);
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
