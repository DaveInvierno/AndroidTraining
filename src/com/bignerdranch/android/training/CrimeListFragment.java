package com.bignerdranch.android.training;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class CrimeListFragment extends ListFragment {
	
	//private static int REQUEST_CRIME = 1;
	
	private ArrayList<Crime> mCrimes;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.crimes_title);
		mCrimes = CrimeLab.get(getActivity()).getCrimes();
		
		/*
		// create an instance of ArrayAdapter<T> and make it the adapter for CrimeListFragment’s ListView
		ArrayAdapter<Crime> adapter = 
				new ArrayAdapter<Crime>(getActivity(), 
										android.R.layout.simple_list_item_1, 
										mCrimes);
		*/
		
		// hook up your custom adapter
		CrimeAdapter adapter = new CrimeAdapter(mCrimes);
		setListAdapter(adapter);	// set the adapter of the implicit ListView managed by CrimeListFragment.
	}
	
	public void onResume() {
		super.onResume();
		((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
	}
	
	// Responding to list item clicks
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		/*
	 	The getListAdapter() method is a ListFragment convenience method that returns the adapter that
	 	is set on the ListFragment’s list view. You then call the adapter’s getItem(int) method using the
	 	position parameter of onListItemClick(…) and cast the result to a Crime.
		 */
		//Crime c = (Crime)(getListAdapter()).getItem(position);
		
		Crime c = ((CrimeAdapter)getListAdapter()).getItem(position);
		//Log.d("CrimeListFragment", c.getTitle() + " was clicked");
		
		// Start CrimeActivity
		//Intent i = new Intent(getActivity(), CrimeActivity.class);
		// Start CrimePagerActivity with this crime
		Intent i = new Intent(getActivity(), CrimePagerActivity.class);
		i.putExtra(CrimeFragment.EXTRA_CRIME_ID, c.getId());
		
		startActivity(i);
		//startActivityForResult(i, REQUEST_CRIME);
	}
	
	// custom adapter to display crimes
	private class CrimeAdapter extends ArrayAdapter<Crime> {
		
		public CrimeAdapter(ArrayList<Crime> crimes) {
			super(getActivity(), 0, crimes);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// If we weren't given a view, inflate one
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_crime, null);
			}
			
			// Configure the view for this Crime
			Crime c = getItem(position);
			
			TextView titleTextView = (TextView)convertView.findViewById(R.id.crime_list_item_titleTextView);
			titleTextView.setText(c.getTitle());
			TextView dateTextView = (TextView)convertView.findViewById(R.id.crime_list_item_dateTextView);
			dateTextView.setText(c.getDate().toString());
			CheckBox solvedCheckBox = (CheckBox)convertView.findViewById(R.id.crime_list_item_solvedCheckBox);
			solvedCheckBox.setChecked(c.isSolved());
			return convertView;
		}
	}
	
	
}
