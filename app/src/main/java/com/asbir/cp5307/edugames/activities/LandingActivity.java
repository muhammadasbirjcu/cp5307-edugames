package com.asbir.cp5307.edugames.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.asbir.cp5307.edugames.R;

public class LandingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        Button playButton = findViewById(R.id.playButton);
        Button topScoresButton = findViewById(R.id.topScoreButton);

        playButton.setOnClickListener(view -> startGameActivity());

        topScoresButton.setOnClickListener(view -> startLeaderboardActivity());
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.settingsMenu){
            startSettingsActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void startGameActivity(){
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    private void startLeaderboardActivity(){
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
    }

    private void startSettingsActivity(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}