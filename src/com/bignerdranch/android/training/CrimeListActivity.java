package com.bignerdranch.android.training;

import android.support.v4.app.Fragment;

// Generic Activity for an Activity with one Fragment
public class CrimeListActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new CrimeListFragment();
	}

}
