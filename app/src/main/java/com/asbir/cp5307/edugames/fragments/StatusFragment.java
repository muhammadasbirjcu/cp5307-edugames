package com.asbir.cp5307.edugames.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.asbir.cp5307.edugames.R;
import com.asbir.cp5307.edugames.game.state.StateListener;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class StatusFragment extends Fragment {
    private StateListener listener;
    private TextView message;
    private TextView score;
    private LinearProgressIndicator gameProgressIndicator;

    public StatusFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (StateListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_status, container, false);
        message = view.findViewById(R.id.messageText);
        score = view.findViewById(R.id.scoreText);
        gameProgressIndicator = view.findViewById(R.id.gameProgressIndicator);
        return view;
    }

    public void setMessage(String message) {
        this.message.setText(message);
    }

    public void setScoreMessage(String scoreMessage) {
        this.score.setText(scoreMessage);
    }

    public void setTimePosition(int current, int max){
        gameProgressIndicator.setMax(max);
        gameProgressIndicator.setProgress(current);
    }
}