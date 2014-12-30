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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class AddTipActivity extends Activity implements LocationListener
{
	/* Hold the form fields, in order to get the user's input. */
	/** The field which allows the user to provide a nickname. */
	private EditText _nicknameEdit;
	
	/** The field which allows the user to provide a name for the parking. */
	private EditText _parkingNameEdit;
	
	/** The field which allows the user to provide the town where the parking is. */
	private EditText _cityEdit;
	
	/** The field which allows the user to provide the number of places in the parking. */
	private EditText _parkingSize;
	
	/** The field which allows the user to tell whether the parking is charged or not. */
	private CheckBox _isChargedCheck;
	
	/** The field which allows the user to provide the type of craft of the parking (underground, opened). */
	private Spinner _craftTypeSpinner;
	
	/** The field which allows the user to provide the latitude coordinate of the parking. */
	private EditText _latitudeEdit;
	
	/** The field which allows the user to provide the latitude coordinate of the parking. */
	private EditText _longitudeEdit;
	
	/** The field which allows the user to provide a comment on the parking. */
	private EditText _commentEdit;
	
	/** */ // TODO
	private Geocoder _geocoder;
	
	/** */ // TODO
	private boolean _hasCoordinates = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.new_parking_tip);
		
		this._nicknameEdit = (EditText)
				findViewById(R.id.newParkingCreatorNickname);
		this._parkingNameEdit = (EditText)
				findViewById(R.id.newParkingName);
		this._cityEdit = (EditText)
				findViewById(R.id.newParkingCity);
		this._parkingSize = (EditText)
				findViewById(R.id.newParkingSize);
		this._isChargedCheck = (CheckBox)
				findViewById(R.id.newParkingPricing);
		this._craftTypeSpinner = (Spinner)
				findViewById(R.id.newParkingCraftType);
		this._latitudeEdit = (EditText) 
				findViewById(R.id.newParkingCoordinateLatitude);
		this._longitudeEdit = (EditText)
				findViewById(R.id.newParkingCoordinateLongitude);
		this._commentEdit = (EditText)
				findViewById(R.id.newParkingComment);
		
		this._geocoder = new Geocoder(this, Locale.getDefault());
	}
	
	@Override
	protected void onStart()
	{
		super.onStart();
		
		// Location Manager: register the current class to receive location updates
		LocationManager locationManager = 
				(LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		
		Criteria crit = new Criteria();
		crit.setAccuracy(Criteria.ACCURACY_FINE);
		
		String provider = locationManager.getBestProvider(crit, true);
		
		Location loc = locationManager.getLastKnownLocation(provider);
		
		if(loc != null)
		{
			this.onLocationChanged(loc);
		}
		
		// set CraftType spinner
	}

	private void _getTown()
	{
		if (this._hasCoordinates && Geocoder.isPresent()) //no geocoder on emulator
		{
			try {
				List<Address> addresses = this._geocoder.getFromLocation(
					Double.parseDouble(this._latitudeEdit.getText().toString()),
					Double.parseDouble(this._longitudeEdit.getText().toString()), 
					1);
				
				if (addresses.size() > 0)
				{
					this._cityEdit.setText(addresses.get(0).getLocality());
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
	public void onLocationChanged(Location location)
	{
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
