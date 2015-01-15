package fr.univpau.paupark.service;

import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class PauParkLocation extends Observable implements LocationListener {
	private static PauParkLocation INSTANCE = null;
	
	private Geocoder _geocoder = null;
	private Location _location = null;
	private LocationManager _locationManager;
	
	public static PauParkLocation getInstance(LocationManager manager, Context context)
	{
		if (INSTANCE == null)
		{
			INSTANCE = new PauParkLocation(manager, context);
		}
		
		return INSTANCE;
	}
	
	public static PauParkLocation getInstance()
	{
		return INSTANCE;
	}
	
	private PauParkLocation(LocationManager manager, Context context) {
		_locationManager = manager;
		
		this._geocoder = new Geocoder(context, Locale.getDefault());
	}
	
	@Override
	public void onLocationChanged(Location location) {
		this._location = location;
		this.notifyObservers();
	}
	
	
	/**
	 * Returns the name of the town of last acquired location.
	 * 
	 * @return
	 */
	public String getTown()
	{
		String town = null;
		
		if (this._location != null && Geocoder.isPresent()) //no geocoder on emulator
		{
			try {
				List<Address> addresses = this._geocoder.getFromLocation(
					this._location.getLatitude(),
					this._location.getLongitude(), 
					1
				);
				
				if (addresses.size() > 0)
				{
					town = addresses.get(0).getLocality();
				}
				
			} catch (Exception e)
			{
				Log.e(this.getClass().getName(), null, e);
			}
		}
		
		return town;
	}
	
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) 
	{	
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) 
	{
		// TODO Auto-generated method stub
		
	}
	
	public Location getLocation () 
	{
		Location location = this._location;
		
		if (location == null)
		{
			location = this._getLastKnownLocation();
		}
		
		
		return location;
	}
	
	
	// Loads the result which has the best accuracy.
	// Useful when no location update occured yet.
	private Location _getLastKnownLocation()
	{
		Location bestResult = null;
	    float bestAccuracy = Float.MAX_VALUE;
		
	    if (_locationManager != null)
	    {
			List<String> matchingProviders = _locationManager.getAllProviders();
			
			for (String provider: matchingProviders) {
			    
				Location location = _locationManager.getLastKnownLocation(provider);
			    
			    if (location != null) {
			        float accuracy = location.getAccuracy();
			        
				    if (accuracy <= bestAccuracy) {
					    bestResult = location;
					    bestAccuracy = accuracy;
				    }
			    }
			  
			}
	    }
	    
		return bestResult;		
	}

	public void enableUpdates()
	{
		if (_locationManager != null)
		{
			//_locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
	
			_locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		}
	}
	
	public void disableUpdates()
	{
		if (_locationManager != null)
		{
			_locationManager.removeUpdates(this);
		}
	}
	
	public void setUpdates(boolean value)
	{
		if (value)
		{
			this.enableUpdates();
		}
		else 
		{
			this.disableUpdates();
		}
	}
}
