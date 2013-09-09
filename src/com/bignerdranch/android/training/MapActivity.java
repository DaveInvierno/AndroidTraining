package com.bignerdranch.android.training;

import android.annotation.TargetApi;
import android.app.Activity;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapActivity extends Activity {
	static final LatLng C2BLDG = new LatLng(14.550962,121.049009);
	
	GoogleMap mMap;
	Location mCurrentLocation;
	LocationClient mLocationClient;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        
        
        setUpMapIfNeeded();
    }
	
	@TargetApi(11)
	private void setUpMapIfNeeded() {
	    // Do a null check to confirm that we have not already instantiated the map.
	    if (mMap == null) {
	        mMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map))
	                            .getMap();
	        // Check if we were successful in obtaining the map.
	        if (mMap != null) {
	            // The Map is verified. It is now safe to manipulate the map.
	        	
	        	/*Marker melbourne = mMap.addMarker(new MarkerOptions()
	        	                          .position(MELBOURNE)
	        	                          .title("Melbourne")
	        	                          .snippet("Population: 4,137,400"));*/
	        	mMap.addMarker(new MarkerOptions()
		            .position(C2BLDG)
		            .title("C2 Building"));
	        	
	        	// Instantiates a new CircleOptions object and defines the center and radius
	        	CircleOptions circleOptions = new CircleOptions()
	        	    .center(C2BLDG)
	        	    .radius(500); // In meters

	        	// Get back the mutable Circle
	        	Circle circle = mMap.addCircle(circleOptions);
	        	
	        	mMap.setMyLocationEnabled(true);
	        	mCurrentLocation = mMap.getMyLocation();
	        	
	        	CameraUpdate center = CameraUpdateFactory.newLatLng(C2BLDG);
	        	CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);

	        	mMap.moveCamera(center);
	        	mMap.animateCamera(zoom);
	        }
	    }
	}
}

