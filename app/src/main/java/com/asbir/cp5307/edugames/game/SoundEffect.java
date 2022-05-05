package com.asbir.cp5307.edugames.game;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

import java.util.HashMap;
import java.util.Map;

public class SoundEffect {
    protected SoundPool soundPool;
    private Map<Integer, Integer> loadedAudio;
    private static SoundEffect mInstance;
    private int activitiesStack;

    public SoundEffect(){
        loadedAudio = new HashMap<>();
    }

    public void play(Context context,  int resId, int loop){
        if(!loadedAudio.containsKey(resId)){
            int clickSound = soundPool.load(context, resId,1);
            loadedAudio.put(resId, clickSound);
        }

        soundPool.play(loadedAudio.get(resId),1, 1, 1, loop, 1 );
    }

    public void buildSoundPool(){
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder().setMaxStreams(3)
                .setAudioAttributes(audioAttributes).build();
    }

    public void release(){
        if(soundPool != null) soundPool.release();
    }

    public static SoundEffect instance(){
        if(mInstance == null){
            mInstance = new SoundEffect();
            mInstance.buildSoundPool();
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
