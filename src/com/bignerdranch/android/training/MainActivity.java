package com.bignerdranch.android.training;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button mGeoQuiz;
	private Button mCriminalIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mGeoQuiz = (Button)findViewById(R.id.geo_quiz);
		mGeoQuiz.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, QuizActivity.class);
				startActivity(i);
			}
		});
		
		mCriminalIntent = (Button)findViewById(R.id.criminal_intent);
		mCriminalIntent.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, CrimeActivity.class);
				startActivity(i);
			}
		});
	}
}
