package com.bignerdranch.android.training;

public class TrueFalse {
	private int mQuestion;
	private boolean mTrueQuestion;
	private boolean mIsCheated;
	
	public TrueFalse(int question, boolean trueQuestion, boolean isCheated) {
		mQuestion = question;
		mTrueQuestion = trueQuestion;
		mIsCheated = isCheated;
	}

	public int getQuestion() {
		return mQuestion;
	}

	public void setQuestion(int question) {
		mQuestion = question;
	}

	public boolean isTrueQuestion() {
		return mTrueQuestion;
	}

	public void setTrueQuestion(boolean trueQuestion) {
		mTrueQuestion = trueQuestion;
	}
	
	public boolean isCheated() {
		return mIsCheated;
	}

	public void setCheated(boolean cheated) {
		mIsCheated = cheated;
	}
}
