package com.asbir.cp5307.edugames.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;

import com.asbir.cp5307.edugames.database.DBHelper;
import com.asbir.cp5307.edugames.game.BackgroundMusic;
import com.asbir.cp5307.edugames.game.SoundEffect;
import com.asbir.cp5307.edugames.game.GameSettings;
import com.asbir.cp5307.edugames.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

public class BaseActivity  extends AppCompatActivity {
    protected GameSettings settings;


    public void loadSettings(){
        if(settings == null) settings = new GameSettings();
        settings.load(getSharedPreferences(GameSettings.PREFERENCE_KEY, MODE_PRIVATE));
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

        // start playing background music if audio is enabled
        startBackgroundMusic();
        BackgroundMusic.instance(this).pushActivity();

        SoundEffect.instance().pushActivity();
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
        SoundEffect.instance().popActivity();
        if(SoundEffect.instance().isLastActivity()){
            SoundEffect.instance().release();
        }

        BackgroundMusic.instance(this).popActivity();
        if(BackgroundMusic.instance(this).isLastActivity()){
            BackgroundMusic.instance(this).release();
        }
        super.onDestroy();
    }

    public void playClickSound() {
        if(!settings.isAudioEnabled()) return;
        SoundEffect.instance().play(this, R.raw.click, 0);
    }

    public void startBackgroundMusic(){
        if(settings != null && settings.isAudioEnabled() && !BackgroundMusic.instance(this).isPlaying()){
            BackgroundMusic.instance(this).start();
        }
    }

    public void tweet(String message){
        Twitter twitter = TwitterFactory.getSingleton();
        String tweet = String.format(message);
        try{
            Status status = twitter.updateStatus(tweet);
        }catch (IllegalStateException ex){
            (new MaterialAlertDialogBuilder(this))
                    .setTitle("Tweet")
                    .setMessage(getString(R.string.twitter_error_auth))
                    .setPositiveButton(getString(R.string.dialog_ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .show();
        }catch (Exception ex){
            (new MaterialAlertDialogBuilder(this))
                    .setTitle("Tweet")
                    .setMessage(getString(R.string.twitter_error))
                    .setPositiveButton(getString(R.string.dialog_ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .show();
        }

    }
}
