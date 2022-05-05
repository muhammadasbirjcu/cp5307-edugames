package com.asbir.cp5307.edugames.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.asbir.cp5307.edugames.R;
import com.asbir.cp5307.edugames.models.Score;
import com.asbir.cp5307.edugames.timer.Timer;

import java.util.ArrayList;

public class ScoreListAdapter extends ArrayAdapter<Score> {

    private final Activity context;

    public ScoreListAdapter(Activity context, ArrayList<Score> scores) {
        super(context, R.layout.score_list_item, scores);

        this.context = context;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.score_list_item, null,true);

        Score score = getItem(position);
        String otherInfo = String.format(
                "%s - Completed in %s",
                score.getDate(),
                Timer.format(score.getTimeElapsed())
        );
        String scoreDetails = String.format(
                "%d / %d",
                score.getScore(),
                score.getNumQuestions()
        );
        ((TextView)rowView.findViewById(R.id.ranking_text)).setText(String.valueOf(position + 1));
        ((TextView)rowView.findViewById(R.id.player_text)).setText(score.getPlayer());
        ((TextView)rowView.findViewById(R.id.other_info_text)).setText(otherInfo);
        ((TextView)rowView.findViewById(R.id.score_pct_text)).setText(String.format("%.0f%%", score.getScorePct()));
        ((TextView)rowView.findViewById(R.id.score_details_text)).setText(scoreDetails);


        return rowView;
    };
}
