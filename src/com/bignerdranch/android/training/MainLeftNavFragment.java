package com.bignerdranch.android.training;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainLeftNavFragment extends Fragment {
	
	private Button mGeoQuiz;
	private Button mCriminalIntent;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_mainleftnav, parent, false);
		
		mGeoQuiz = (Button)v.findViewById(R.id.geo_quiz);
		mGeoQuiz.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), QuizActivity.class);
				startActivity(i);
			}
		});
		
		mCriminalIntent = (Button)v.findViewById(R.id.criminal_intent);
		mCriminalIntent.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), CrimeListActivity.class);
				startActivity(i);
			}
		});
		
		return v;
	}
}
