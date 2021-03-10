package com.example.geoquiz;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    private Button mTrueButton,mFalseButton,mResetButton,mCheatButton;
    private ImageButton mNextButton,mPrevButton;
    private TextView mScoreView,mQuestionView;

    private Question mQuestionBank[] = new Question[] {
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    private int mCurrentIndex = 0;
    private boolean finishedQuest[] = new boolean[mQuestionBank.length];
    private int dogru,yanlis = 0;
    private static final String SAVED_INDEX = "mCurrentIndex";
    private static final String SAVED_ANSWER_LIST = "finishedQuest[]";
    private static final String SAVED_DOGRU = "dogru";
    private static final String SAVED_YANLIS = "yanlis";
    private static final String SAVED_IS_CHEATER = "mIsCheater";
    private static final int REQUESTED_CODE_CHEAT = 0;

    private boolean mIsCheater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTrueButton = (Button) findViewById(R.id.trueButton);
        mFalseButton = (Button) findViewById(R.id.falseButton);
        mNextButton = (ImageButton) findViewById(R.id.nextButton);
        mPrevButton = (ImageButton) findViewById(R.id.prevButton);
        mResetButton = (Button) findViewById(R.id.resetButton);
        mCheatButton = (Button) findViewById(R.id.cheatButton);
        mScoreView = (TextView) findViewById(R.id.scoreText);
        mQuestionView = (TextView) findViewById(R.id.questionText);

        if(savedInstanceState!=null){
            mCurrentIndex = savedInstanceState.getInt(SAVED_INDEX,0);
            finishedQuest = savedInstanceState.getBooleanArray(SAVED_ANSWER_LIST);
            dogru = savedInstanceState.getInt(SAVED_DOGRU,0);
            yanlis = savedInstanceState.getInt(SAVED_YANLIS,0);
            mIsCheater = savedInstanceState.getBoolean(SAVED_IS_CHEATER,false);
        }

        View.OnClickListener event = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex+1) % mQuestionBank.length;
                mIsCheater = false;
                updateQuestion();
            }
        };

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mNextButton.setOnClickListener(event);
        mQuestionView.setOnClickListener(event);

        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentIndex==0){
                    mCurrentIndex = mQuestionBank.length-1;
                }
                else{
                    mCurrentIndex = mCurrentIndex-1;
                }
                mIsCheater = false;
                updateQuestion();
            }
        });

        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = 0;
                dogru = 0;
                yanlis = 0;
                finishedQuest = new boolean[mQuestionBank.length];
                mScoreView.setText("Score: "+dogru+"/"+(dogru+yanlis)+"\nTotal: "+mQuestionBank.length);
                mIsCheater = false;
                updateQuestion();
            }
        });

        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerOfQuest = mQuestionBank[mCurrentIndex].ismAnswer();
                Intent intent = CheatActivity.newIntent(MainActivity.this, answerOfQuest);
                startActivityForResult(intent,REQUESTED_CODE_CHEAT);

            }
        });


        mScoreView.setText("Score: "+dogru+"/"+(dogru+yanlis)+"\nTotal: "+mQuestionBank.length);
        updateQuestion();
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(SAVED_INDEX, mCurrentIndex);
        savedInstanceState.putBooleanArray(SAVED_ANSWER_LIST,finishedQuest);
        savedInstanceState.putInt(SAVED_DOGRU,dogru);
        savedInstanceState.putInt(SAVED_YANLIS,yanlis);
        savedInstanceState.putBoolean(SAVED_IS_CHEATER,mIsCheater);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUESTED_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }

    private void checkAnswer(boolean userAnswerInput){
        finishedQuest[mCurrentIndex] = true;
       if(finishedQuest[mCurrentIndex]==false){
           enable();
       }
       else{
           disable();
       }
       int messageId = 0;
       boolean answerOfQuestion = mQuestionBank[mCurrentIndex].ismAnswer();
       if(mIsCheater){
           messageId = R.string.warning;
       }
       else{
           if(userAnswerInput==answerOfQuestion){
               messageId = R.string.correct_toast;
               dogru++;
           }
           else{
               messageId = R.string.false_toast;
               yanlis++;
           }
       }

        mScoreView.setText("Score: "+dogru+"/"+(dogru+yanlis)+"\nTotal: "+mQuestionBank.length);
        Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show();
       mNextButton.performClick();
    }

    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getmTextResId();
        mQuestionView.setText(question);
        if(finishedQuest[mCurrentIndex]==false){
            enable();
        }
        else{
            disable();
        }
    }

    private void enable(){
        mTrueButton.setEnabled(true);
        mFalseButton.setEnabled(true);
    }

    private void disable(){
        mTrueButton.setEnabled(false);
        mFalseButton.setEnabled(false);
    }
}