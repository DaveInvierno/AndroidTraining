package com.bignerdranch.android.training;

import android.content.Context;
import android.location.Location;
import android.util.Log;

public class TrackingLocationReceiver extends LocationReceiver {
	
	@Override
	protected void onLocationReceived(Context c, Location loc) {
		RunManager.get(c).insertLocation(loc);
		Log.d("LocationReceiver", "New Location Received");
	}
	
}
