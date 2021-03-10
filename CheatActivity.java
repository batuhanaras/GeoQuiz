package com.example.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {


    private Button mShowButton;
    private TextView mAnswerView;

    private static final String EXTRA_ANSWER = "com.bignerdranch.android.sinavdeneme.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.sinavdeneme.answer_shown";
    private static final String KOPYA = "kopya";
    private boolean mAnswerOfQuestion;
    private boolean mAnswerIsShown;

    public static Intent newIntent(Context packageContext, boolean answerOfQuest) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER, answerOfQuest);
        return intent;
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        mShowButton = (Button) findViewById(R.id.showButton);
        mAnswerView = (TextView) findViewById(R.id.answerText);

        if(savedInstanceState!=null){
            mAnswerIsShown = savedInstanceState.getBoolean(KOPYA,false);
            setAnswerShownResult(mAnswerIsShown);
        }

        mAnswerOfQuestion = getIntent().getBooleanExtra(EXTRA_ANSWER,false);

        mShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAnswerOfQuestion){
                    mAnswerView.setText(R.string.truebut);
                }
                else{
                    mAnswerView.setText(R.string.falsebut);
                }
                mAnswerIsShown = true;
                setAnswerShownResult(mAnswerIsShown);
            }
        });
    }

    private void setAnswerShownResult(boolean mAnswerIsShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN,mAnswerIsShown);
        setResult(RESULT_OK,data);
    }
}