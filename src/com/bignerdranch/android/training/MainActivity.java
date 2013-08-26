package com.bignerdranch.android.training;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class MainActivity extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.mainContainer);
		
		if(fragment == null) {
			fragment = new MainLeftNavFragment();
			fm.beginTransaction().add(R.id.mainContainer, fragment).commit();
		}
	}
}
