package com.asbir.cp5307.edugames.game;

import android.content.SharedPreferences;
import android.os.Bundle;

public class GameSettings {
    private boolean audioEnabled;
    private String playerName;
    private int maxQuestions;
    private int duration;
    private Difficulty difficulty;

    public static final String PREFERENCE_KEY = "edugames";


    protected SharedPreferences sharedPref;
    protected SharedPreferences.Editor editor;

    public GameSettings(){
        audioEnabled = true;
        playerName = "Guest";
        maxQuestions = 10;
        duration = 30;
        difficulty = Difficulty.EASY;
    }

    public void save(SharedPreferences.Editor preferenceEditor){
        preferenceEditor.putBoolean("audioEnabled", audioEnabled);
        preferenceEditor.putString("playerName", playerName);
        preferenceEditor.putInt("maxQuestions", maxQuestions);
        preferenceEditor.putInt("duration", duration);
        preferenceEditor.putInt("difficulty", difficulty.ordinal());
        preferenceEditor.commit();
    }

    public void load(SharedPreferences preference){
        audioEnabled = preference.getBoolean("audioEnabled", true);
        playerName = preference.getString("playerName", "Guest");
        maxQuestions = preference.getInt("maxQuestions", 10);
        duration = preference.getInt("duration", 30);
        difficulty = Difficulty.values()[preference.getInt("difficulty", 0)];
    }

    public boolean isAudioEnabled() {
        return audioEnabled;
    }

    public void setAudioEnabled(boolean audioEnabled) {
        this.audioEnabled = audioEnabled;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getMaxQuestions() {
        return maxQuestions;
    }

    public void setMaxQuestions(int maxQuestions) {
        this.maxQuestions = maxQuestions;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
