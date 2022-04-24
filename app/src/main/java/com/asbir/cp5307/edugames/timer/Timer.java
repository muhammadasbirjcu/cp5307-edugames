package com.asbir.cp5307.edugames.timer;

import android.os.Handler;

import androidx.annotation.NonNull;

public class Timer {
    private int duration;
    private int secondsRemaining;
    private Handler handler;
    private Runnable runnable;
    private Boolean isRunning;

    private Tickable tickHandler;

    public Timer(int duration){
        this.duration = duration;
        this.secondsRemaining = duration;
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
            handler.removeCallbacks(runnable);
            isRunning = false;
        }catch (Exception ex){}
        this.secondsRemaining = this.duration;
    }

    void tick(){
        secondsRemaining--;
    }

    public boolean timeHasElapsed(){
        return this.secondsRemaining <= 0;
    }

    @NonNull
    @Override
    public String toString(){
        int hours = secondsRemaining / 3600;
        int minutes = (secondsRemaining % 3600) / 60;
        int seconds = (secondsRemaining % 3600) % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public boolean getIsRunning(){
        return isRunning;
    }
}
