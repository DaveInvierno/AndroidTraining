package com.bignerdranch.android.training;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends Activity {
	
	public static final String EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.training.answer_is_true";
	public static final String EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.training.answer_shown";
	private static final String KEY_VALUE = "value";

	private TextView mAnswerTextView;
	private Button mShowAnswer;
	
	private boolean mAnswerIsTrue;
	private boolean mIsCheated;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cheat);
		
		mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
		
		mAnswerTextView = (TextView)findViewById(R.id.answer_text_view);
		
		mShowAnswer = (Button)findViewById(R.id.show_answer_button);
		mShowAnswer.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mAnswerIsTrue)
					mAnswerTextView.setText(R.string.true_button);
				else
					mAnswerTextView.setText(R.string.false_button);
				
				mIsCheated = true;
				setAnswerShownResult(true);
			}
		});
		
		if(savedInstanceState != null) {
			mIsCheated = savedInstanceState.getBoolean(KEY_VALUE, false);
			setAnswerShownResult(mIsCheated);
			if(mIsCheated) {
				if(mAnswerIsTrue)
					mAnswerTextView.setText(R.string.true_button);
				else
					mAnswerTextView.setText(R.string.false_button);
			}
		} else {
			mIsCheated = false;
			setAnswerShownResult(false);
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putBoolean(KEY_VALUE, mIsCheated);
	}
	
	private void setAnswerShownResult(boolean isAnswerShown) {
		Intent data = new Intent();
		data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
		setResult(RESULT_OK, data);
	}
}
