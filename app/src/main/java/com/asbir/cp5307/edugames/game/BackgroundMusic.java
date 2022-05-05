package com.asbir.cp5307.edugames.game;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;

import com.asbir.cp5307.edugames.R;

import java.util.HashMap;
import java.util.Map;

public class BackgroundMusic {
    protected MediaPlayer player;
    private static BackgroundMusic mInstance;
    private int activitiesStack;


    public BackgroundMusic(Context context, int resourceId){
        player = MediaPlayer.create(context, resourceId);
        player.setVolume(0.1f, 0.1f);
        player.setLooping(true);
    }

    public void start(){
        player.start();
    }

    public void stop(){
        player.stop();
    }

    public boolean isPlaying(){
        return player.isPlaying();
    }

    public void release(){
        if(player != null) player.release();
    }

    public static BackgroundMusic instance(Context context){
        if(mInstance == null){
            mInstance = new BackgroundMusic(context, R.raw.bgmusic);
        }
        return mInstance;
    }

    public void pushActivity(){
        this.activitiesStack++;
    }

    public void popActivity(){
        this.activitiesStack++;
    }

    public boolean isLastActivity(){
        return this.activitiesStack == 0;
    }
}
