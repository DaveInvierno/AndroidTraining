package com.bignerdranch.android.training;

import java.util.Date;
import java.util.UUID;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class CrimeFragment extends Fragment {
	
	private static final String TAG = "CrimeFragment";
	public static final String EXTRA_CRIME_ID = "com.bignerdranch.android.training.crime_id";
	private static final String DIALOG_DATE = "date";
	private static final String DIALOG_TIME = "time";
	private static final int REQUEST_DATE = 0;
	private static final int REQUEST_TIME = 3;
	private static final int REQUEST_PHOTO = 1;
	private static final String DIALOG_IMAGE = "image";
	private static final int REQUEST_CONTACT = 2;
	
	private Crime mCrime;
	private EditText mTitleField;
	private Button mDateButton;
	private Button mTimeButton;
	private CheckBox mSolvedCheckbox;
	private ImageButton mPhotoButton;
	private ImageView mPhotoView;
	private Button mSuspectButton;
	private Callbacks mCallbacks;
	
	/**
	* Required interface for hosting activities.
	*/
	public interface Callbacks {
		void onCrimeUpdated(Crime crime);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mCallbacks = (Callbacks)activity;
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		mCallbacks = null;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		setRetainInstance(true);
		
		mCrime = new Crime();
		
		// get extra from intent
		//UUID crimeId = (UUID)getActivity().getIntent().getSerializableExtra(EXTRA_CRIME_ID);
		
		// Retrieving arguments: Getting crimeId from the arguments
		UUID crimeId = (UUID)getArguments().getSerializable(EXTRA_CRIME_ID);
		mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
	}
	
	@TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_crime, parent, false);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			if (NavUtils.getParentActivityName(getActivity()) != null) {
				getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
			}
		}
		
		mTitleField = (EditText)v.findViewById(R.id.crime_title);
		mTitleField.setText(mCrime.getTitle());
		mTitleField.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				mCrime.setTitle(arg0.toString());
				mCallbacks.onCrimeUpdated(mCrime);
				getActivity().setTitle(mCrime.getTitle());
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		mDateButton = (Button)v.findViewById(R.id.crime_date);
		updateDate();
		mDateButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentManager fm = getActivity().getSupportFragmentManager();
				//DatePickerFragment dialog = new DatePickerFragment();
				DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
				dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
				dialog.show(fm, DIALOG_DATE);
			}
		});
		
		mTimeButton = (Button)v.findViewById(R.id.crime_time);
		updateTime();
		mTimeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentManager fm = getActivity().getSupportFragmentManager();
				TimePickerFragment dialog = TimePickerFragment.newInstance(mCrime.getTime());
				dialog.setTargetFragment(CrimeFragment.this, REQUEST_TIME);
				dialog.show(fm, DIALOG_TIME);
			}
		});
		
		mSolvedCheckbox = (CheckBox)v.findViewById(R.id.crime_solved);
		mSolvedCheckbox.setChecked(mCrime.isSolved());
		mSolvedCheckbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// Set the crime's solved property
				mCrime.setSolved(isChecked);
				mCallbacks.onCrimeUpdated(mCrime);
			}
		});
		
		mPhotoButton = (ImageButton)v.findViewById(R.id.crime_imageButton);
		mPhotoButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), CrimeCameraActivity.class);
				//startActivity(i);
					startActivityForResult(i, REQUEST_PHOTO);
			}
		});
		
		mPhotoView = (ImageView)v.findViewById(R.id.crime_imageView);
		mPhotoView.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Photo p = mCrime.getPhoto();
				if (p == null)
				return;
				FragmentManager fm = getActivity().getSupportFragmentManager();
				String path = getActivity().getFileStreamPath(p.getFilename()).getAbsolutePath();
				ImageFragment.newInstance(path).show(fm, DIALOG_IMAGE);
			}
		});
		
		// If camera is not available, disable camera functionality
		PackageManager pm = getActivity().getPackageManager();
		if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA) && !pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)) {
			mPhotoButton.setEnabled(false);
		}
		
		Button reportButton = (Button)v.findViewById(R.id.crime_reportButton);
		reportButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
				 
				// set title
				alertDialogBuilder.setTitle("Send Message?");
				
				// set dialog message
				alertDialogBuilder
					.setCancelable(false)
					.setPositiveButton("OK",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							Intent i = new Intent(Intent.ACTION_SEND);
							i.setType("text/plain");
							i.putExtra(Intent.EXTRA_TEXT, getCrimeReport());
							i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.crime_report_subject));
							i = Intent.createChooser(i, getString(R.string.send_report));
							startActivity(i);
						}
					  })
					.setNegativeButton("CANCEL",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// if this button is clicked, just close
							// the dialog box and do nothing
							dialog.cancel();
						}
					});
	 
					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();
	 
					// show it
					alertDialog.show();
			}
		});
		
		mSuspectButton = (Button)v.findViewById(R.id.crime_suspectButton);
		mSuspectButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
				startActivityForResult(i, REQUEST_CONTACT);
			}
		});
	
		if (mCrime.getSuspect() != null) {
			mSuspectButton.setText(mCrime.getSuspect());
		}
		
		return v;
	}
	
	public static CrimeFragment newInstance(UUID crimeId) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_CRIME_ID, crimeId);
		
		CrimeFragment fragment = new CrimeFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
	
	public void updateDate() {
		mDateButton.setText(DateFormat.format("MMMM dd, yyyy", mCrime.getDate()));
	}
	
	public void updateTime() {
		mTimeButton.setText(DateFormat.format("hh:mm:ss aaa", mCrime.getTime()));
	}
	
	private void showPhoto() {
		// (Re)set the image button's image based on our photo
		Photo p = mCrime.getPhoto();
		BitmapDrawable b = null;
		
		if (p != null) {
			String path = getActivity().getFileStreamPath(p.getFilename()).getAbsolutePath();
			b = PictureUtils.getScaledDrawable(getActivity(), path);
		}
			mPhotoView.setImageDrawable(b);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		showPhoto();
	}
	
	@Override
	public void onStop() {
		super.onStop();
		PictureUtils.cleanImageView(mPhotoView);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK) return;
		
		if (requestCode == REQUEST_DATE) {
			Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
			mCrime.setDate(date);
			mCallbacks.onCrimeUpdated(mCrime);
			updateDate();
		} else if (requestCode == REQUEST_TIME) {
			Date date = (Date)data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
			mCrime.setTime(date);
			mCallbacks.onCrimeUpdated(mCrime);
			updateTime();
		} else if (requestCode == REQUEST_PHOTO) {
			// Create a new Photo object and attach it to the crime
			String filename = data.getStringExtra(CrimeCameraFragment.EXTRA_PHOTO_FILENAME);
			
			if (filename != null) {
				Log.i(TAG, "filename: " + filename);
				Photo p = new Photo(filename);
				mCrime.setPhoto(p);
				mCallbacks.onCrimeUpdated(mCrime);
				showPhoto();
				Log.i(TAG, "Crime: " + mCrime.getTitle() + " has a photo");
			}
		} else if (requestCode == REQUEST_CONTACT) {
			Uri contactUri = data.getData();
			// Specify which fields you want your query to return
			// values for.
			String[] queryFields = new String[] {
					ContactsContract.Contacts.DISPLAY_NAME
			};
			// Perform your query - the contactUri is like a "where"
			// clause here
			Cursor c = getActivity().getContentResolver()
			.query(contactUri, queryFields, null, null, null);
			
			// Double-check that you actually got results
			if (c.getCount() == 0) {
				c.close();
				return;
			}
			
			// Pull out the first column of the first row of data -
			// that is your suspect's name.
			c.moveToFirst();
			String suspect = c.getString(0);
			mCrime.setSuspect(suspect);
			mCallbacks.onCrimeUpdated(mCrime);
			mSuspectButton.setText(suspect);
			c.close();
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				if (NavUtils.getParentActivityName(getActivity()) != null) {
					NavUtils.navigateUpFromSameTask(getActivity());
				}
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
		CrimeLab.get(getActivity()).saveCrimes();
	}
	
	private String getCrimeReport() {
		String solvedString = null;
		
		if (mCrime.isSolved()) {
			solvedString = getString(R.string.crime_report_solved);
		} else {
			solvedString = getString(R.string.crime_report_unsolved);
		}
		
		String dateFormat = "EEE, MMM dd";
		String dateString = DateFormat.format(dateFormat, mCrime.getDate()).toString();
		String suspect = mCrime.getSuspect();
		
		if (suspect == null) {
			suspect = getString(R.string.crime_report_no_suspect);
		} else {
			suspect = getString(R.string.crime_report_suspect, suspect);
		}
		
		String report = getString(R.string.crime_report, mCrime.getTitle(), dateString, solvedString, suspect);
		
		return report;
	}
}
