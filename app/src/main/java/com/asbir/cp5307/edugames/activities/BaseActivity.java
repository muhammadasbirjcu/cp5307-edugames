package com.asbir.cp5307.edugames.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;

import com.asbir.cp5307.edugames.database.DBHelper;
import com.asbir.cp5307.edugames.game.GameSettings;
import com.asbir.cp5307.edugames.R;

public class BaseActivity  extends AppCompatActivity {
    protected GameSettings settings;
    protected SoundPool soundPool;
    private int clickSound = -1;


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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder().setMaxStreams(3)
                .setAudioAttributes(audioAttributes).build();
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

    @Override
    protected void onDestroy() {
        if(soundPool != null){
            soundPool.release();
        }
        super.onDestroy();
    }

    public void playClickSound() {
        if(!settings.isAudioEnabled()) return;
        if(clickSound == -1) clickSound = soundPool.load(this, R.raw.click,1);
        soundPool.play(clickSound,1, 1, 1, 0, 1 );
    }
}
