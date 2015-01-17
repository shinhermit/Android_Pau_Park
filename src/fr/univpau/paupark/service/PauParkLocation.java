package fr.univpau.paupark.service;

import java.util.List;
import java.util.Locale;
import java.util.Observable;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
/**
 * Receives location updates.
 * Observable : observers notified of available location updates.
 */
public class PauParkLocation extends Observable implements LocationListener {
	/// Singleton implementation
	private static PauParkLocation INSTANCE = null;
	
	/// Performs geocoding requests
	private Geocoder _geocoder = null;
	
	/// Last acquired location
	private Location _location = null;
	
	/// Location manager to start or stop receiving location updates.
	private LocationManager _locationManager;

	/// Only one provider left (fused provider)
	private boolean _hasProvider = false;
	
	/// First getInstance to call 
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
	
	/// 
	private PauParkLocation(LocationManager manager, Context context) {
		_locationManager = manager;
		
		if (Geocoder.isPresent())
		{
			this._geocoder = new Geocoder(context, Locale.getDefault());
		}
		
		_hasProvider = _locationManager.getAllProviders().size() > 0;
	}
	
	/// Receives location updates. 
	/// Records the update and notifies observers that a location update has occured.
	@Override
	public void onLocationChanged(Location location) {
		this._location = location;
		this.notifyObservers();
	}
	
	
	/// Returns the name of the town of last acquired location or null if 
	/// geocoding is not possible.
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
		this._hasProvider = true;
	}

	@Override
	public void onProviderDisabled(String provider) 
	{
		this._hasProvider = false;
	}
	
	/// Returns the last acquired location through LocationListener
	/// or queries the available location providers for their last
	/// known location if no update received yet.
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
	
	/// Toggles location updates : 
	/// - value == true : enable upadtes
	/// - value == false : disable updates
	public void receiveUpdates(boolean value)
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

	/// Enable location updates
	private void enableUpdates()
	{
		if (_locationManager != null)
		{
			//Crashes emulator
			//_locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
	
			_locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		}
	}
	
	/// Disable location updates
	private void disableUpdates()
	{
		if (_locationManager != null)
		{
			_locationManager.removeUpdates(this);
		}
	}
}
