package com.bignerdranch.android.training;

import java.util.UUID;

import android.support.v4.app.Fragment;

//Generic Activity for an Activity with one Fragment
public class CrimeActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		//return new CrimeFragment();
		
		UUID crimeId = (UUID)getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
		return CrimeFragment.newInstance(crimeId);
	}

}
