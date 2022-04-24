package com.asbir.cp5307.edugames.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.asbir.cp5307.edugames.R;
import com.asbir.cp5307.edugames.game.Question;
import com.asbir.cp5307.edugames.game.state.State;
import com.asbir.cp5307.edugames.game.state.StateListener;

public class QuestionFragment extends Fragment {
    private StateListener listener;
    private Question question;
    private GridView answersGrid;
    private ImageView questionImage;

    public QuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question, container, false);
        answersGrid = (GridView) view.findViewById(R.id.answersGrid);
        questionImage = (ImageView) view.findViewById(R.id.questionImage);

        answersGrid.setOnItemClickListener((adapterView, view1, i, l) -> {
            String answer = question.getPossibleNames()[i];
            if(question.check(answer)){
                listener.onCorrectAnswer();
            }
            listener.onUpdate(State.CONTINUE_GAME);
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (StateListener) context;
    }

    public Question getQuestion() {
        return question;
    }

    /**
     * Sets the question and binds UI components to it.
     * @param question
     */
    public void setQuestion(Question question) {
        this.question = question;
        this.applyChangesToUI();
    }

    public void applyChangesToUI(){
        // set answers
        ArrayAdapter<String> answersAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, question.getPossibleNames());
        answersGrid.setAdapter(answersAdapter);

        // set image
        questionImage.setImageBitmap(question.getCelebrityImage());
    }

    public void show(){
        questionImage.setVisibility(View.VISIBLE);
        answersGrid.setVisibility(View.VISIBLE);
    }

    public void hideAnswers(){
        answersGrid.setVisibility(View.INVISIBLE);
    }
}