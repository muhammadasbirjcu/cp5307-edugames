package com.asbir.cp5307.edugames.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.asbir.cp5307.edugames.R;
import com.asbir.cp5307.edugames.game.BackgroundMusic;
import com.asbir.cp5307.edugames.game.Difficulty;
import com.asbir.cp5307.edugames.game.GameSettings;
import com.asbir.cp5307.edugames.game.GameXMLParser;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.List;

public class LandingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);



        Button playButton = findViewById(R.id.playButton);
        Button topScoresButton = findViewById(R.id.topScoreButton);
        Button settingsButton = findViewById(R.id.settingsButton);

        settings = new GameSettings();
        loadSettings();



        playButton.setOnClickListener(view -> {
            playClickSound();
            startGameActivity();
        });

        topScoresButton.setOnClickListener(view -> {
            playClickSound();
            startLeaderboardActivity();
        });

        settingsButton.setOnClickListener(view -> {
            playClickSound();
            startSettingsActivity();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((TextView)findViewById(R.id.player_name)).setText(settings.getPlayerName() + "!");
        ((TextView)findViewById(R.id.difficulty_text)).setText(
                String.format(
                        getResources().getString(R.string.difficulty_information),
                        settings.getDifficulty().toString()
                )
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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