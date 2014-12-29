package fr.univpau.paupark.view;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import fr.univpau.paupark.R;
import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

public class AddTipActivity extends Activity implements LocationListener
{
	private EditText _latitudeEdit;
	private EditText _longitudeEdit;
	private EditText _townEdit;
	private Geocoder _geocoder;
	private boolean _hasCoordinates = false;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.new_parking_tip);
		
		this._latitudeEdit = (EditText) findViewById(R.id.newParkingCoordinateLatitude);
		this._longitudeEdit = (EditText) findViewById(R.id.newParkingCoordinateLongitude);
		this._townEdit = (EditText) findViewById(R.id.newParkingCity);
		this._geocoder = new Geocoder(this, Locale.getDefault());
		
		
		//Register the current class with the Location Manager to receive location updates
		LocationManager locationManager = 
				(LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		
		Criteria crit = new Criteria();
		crit.setAccuracy(Criteria.ACCURACY_FINE);
		String provider = locationManager.getBestProvider(crit, true);
		Location loc = locationManager.getLastKnownLocation(provider);
		this.onLocationChanged(loc);
	}

	private void _getTown()
	{
		if (this._hasCoordinates && this._geocoder.isPresent()) //no geocoder on emulator
		{
			try {
				List<Address> addresses = this._geocoder.getFromLocation(
					Double.parseDouble(this._latitudeEdit.getText().toString()),
					Double.parseDouble(this._longitudeEdit.getText().toString()), 
					1);
				
				if (addresses.size() > 0)
				{
					this._townEdit.setText(addresses.get(0).getLocality());
				}
				
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		//Make use of new location
		this._hasCoordinates = true;
		
		this._latitudeEdit.setText(String.valueOf(location.getLatitude()));
		this._longitudeEdit.setText(String.valueOf(location.getLongitude()));
		
		this._getTown();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		//handle enabling of location provider
		Log.i("provider", "enabled " + provider);
		this._getTown();
	}

	@Override
	public void onProviderDisabled(String provider) {
		//handle disabling of location provider
		Log.i("provider", "disabled " + provider);
	}
	
	
}
