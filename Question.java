package com.example.geoquiz;

public class Question {
    private int mTextResId;
    private boolean mAnswer;

    public Question(int textResId, boolean answer) {
        mTextResId = textResId;
        mAnswer = answer;
    }

    public int getmTextResId() {
        return mTextResId;
    }

    public void setmTextResId(int mTextResId) {
        this.mTextResId = mTextResId;
    }

    public boolean ismAnswer() {
        return mAnswer;
    }

    public void setmAnswer(boolean mAnswer) {
        this.mAnswer = mAnswer;
    }
}
