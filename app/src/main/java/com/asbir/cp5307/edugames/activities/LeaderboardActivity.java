package com.asbir.cp5307.edugames.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.asbir.cp5307.edugames.R;
import com.asbir.cp5307.edugames.adapters.ScoreListAdapter;
import com.asbir.cp5307.edugames.models.Score;

import java.util.ArrayList;

public class LeaderboardActivity extends BaseActivity {
    private ListView scoresList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        ArrayList<Score> scores = getDBHelper().getLeaderboard(20);

        ScoreListAdapter adapter = new ScoreListAdapter(this, scores);
        scoresList = findViewById(R.id.scores_list);
        scoresList.setAdapter(adapter);

    }
}