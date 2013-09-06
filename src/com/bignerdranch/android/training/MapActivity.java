package com.bignerdranch.android.training;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MapActivity extends FragmentActivity implements LocationListener, LocationSource {
	GoogleMap mMap;
	private OnLocationChangedListener mListener;
	private LocationManager locationManager;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        
        if(locationManager != null)
        {
            boolean gpsIsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean networkIsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
             
            if(gpsIsEnabled)
            {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000L, 10F, this);
            }
            else if(networkIsEnabled)
            {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000L, 10F, this);
            }
            else
            {
                Log.d("MapActivity", "GPS Disabled");
            }
        }
        else
        {
        	Log.d("MapActivity", "Error on location manager");
        }
        
        setUpMapIfNeeded();
    }
	
	 @Override
    public void onPause()
    {
		 Log.d("MapActivity", "onPause");
        if(locationManager != null)
        {
            locationManager.removeUpdates(this);
        }
         
        super.onPause();
    }
     
    @Override
    public void onResume()
    {
    	Log.d("MapActivity", "onResume");
        super.onResume();
         
        setUpMapIfNeeded();
         
        if(locationManager != null)
        {
            mMap.setMyLocationEnabled(true);
        }
    }
	
	private void setUpMapIfNeeded() {
		Log.d("MapActivity", "setUpMapIfNeeded");
		// Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) 
        {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
				
				@Override
				public void onMyLocationChange(Location arg0) {
					Log.d("MapActivity", "My Location Changed");
				}
			});
            // Check if we were successful in obtaining the map.
            
            if (mMap != null) 
            {
                setUpMap();
            }
 
            //This is how you register the LocationSource
            mMap.setLocationSource(this);
        }
	}
	
	private void setUpMap() 
    {
		Log.d("MapActivity", "setUpMap");
        mMap.setMyLocationEnabled(true);
    }
     
    @Override
    public void activate(OnLocationChangedListener listener) 
    {
    	Log.d("MapActivity", "activate");
        mListener = listener;
    }
     
    @Override
    public void deactivate() 
    {
    	Log.d("MapActivity", "deactivate");
        mListener = null;
    }
 
    @Override
    public void onLocationChanged(Location location) 
    {
    	Log.d("MapActivity", "onLocationChanged");
        if( mListener != null )
        {
            mListener.onLocationChanged( location );
 
            mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
            
            Log.d("MapActivity", "location changed");
        }
    }
 
    @Override
    public void onProviderDisabled(String provider) 
    {
    	Log.d("MapActivity", "onProviderDisabled");
        // TODO Auto-generated method stub
        Toast.makeText(this, "provider disabled", Toast.LENGTH_SHORT).show();
    }
 
    @Override
    public void onProviderEnabled(String provider) 
    {
    	Log.d("MapActivity", "onProviderEnabled");
        // TODO Auto-generated method stub
        Toast.makeText(this, "provider enabled", Toast.LENGTH_SHORT).show();
    }
 
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) 
    {
    	Log.d("MapActivity", "onStatusChanged");
        // TODO Auto-generated method stub
        Toast.makeText(this, "status changed", Toast.LENGTH_SHORT).show();
    }
    
    
}

