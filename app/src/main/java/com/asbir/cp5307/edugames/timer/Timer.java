package com.asbir.cp5307.edugames.timer;

import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.Locale;

public class Timer {
    private int duration;
    private Handler handler;
    private Runnable runnable;
    private Boolean isRunning;

    private Tickable tickHandler;

    public Timer(){
        this.duration = duration;
        this.isRunning = false;
    }

    public void start(){
        isRunning = true;
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if(isRunning){
                    Timer.this.tick();
                    handler.postDelayed(this, 1000);
                    if(tickHandler != null){
                        tickHandler.onTimerTick(Timer.this);
                    }
                }
            }
        };
        handler.post(runnable);
    }

    public void setTickHandler(Tickable tickHandler) {
        this.tickHandler = tickHandler;
    }


    public void reset(){
        try{
            release();
        }catch (Exception ex){
            Log.e("Timer", "reset", ex);
        }
        this.duration = 0;
    }

    public void release(){
        if(handler != null)handler.removeCallbacks(runnable);
        isRunning = false;
    }

    void tick(){
        duration++;
    }

    public int getDuration() {
        return duration;
    }

    @NonNull
    @Override
    public String toString(){
        return format(this.duration);
    }

    public boolean getIsRunning(){
        return isRunning;
    }

    public static String format(int duration){
//        return String.format(Locale.getDefault(), "Time remaining: %02d", seconds);
        int hours = duration / 3600;
        int minutes = (duration % 3600) / 60;
        int seconds = (duration % 3600) % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
