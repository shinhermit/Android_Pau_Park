package fr.univpau.paupark.view;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import fr.univpau.paupark.R;
import fr.univpau.paupark.model.GeoCoordinate;
import fr.univpau.paupark.model.OfficialParking;
import fr.univpau.paupark.model.PauParkPreferences;
import fr.univpau.paupark.model.AbstractParking.CraftType;
import fr.univpau.paupark.model.UserTipParking;
import fr.univpau.paupark.service.PauParkLocation;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

/**
 * The activity (view) which allows to add a new parking tip.
 * Observes PauParkLocation for location updates.
 * 
 * @author Émilien Arino
 * @author Josuah Aron
 *
 */
public class AddTipActivity extends Activity implements Observer
{
	/** Handles location queries and updates */
	private PauParkLocation _pauParkLocation;
	
	/* Hold the form fields, in order to get the user's input. */
	/** The field which allows the user to provide a nickname. */
	private EditText _nicknameEdit;
	
	/** The field which allows the user to provide a name for the parking. */
	private EditText _parkingNameEdit;
	
	/** The field which allows the user to provide the town where the parking is. */
	private EditText _townEdit;
	
	/** The field which allows the user to provide the number of places in the parking. */
	private EditText _parkingSizeEdit;
	
	/** The field which allows the user to tell whether the parking is charged or not. */
	private CheckBox _isChargedCheck;
	
	/** The field which allows the user to provide the type of craft of the parking (underground, opened). */
	private Spinner _craftTypeSpinner;
	
	/** The field which allows the user to provide the latitude coordinate of the parking. */
	private EditText _latitudeEdit;
	
	/** The field which allows the user to provide the latitude coordinate of the parking. */
	private EditText _longitudeEdit;
	
	/** The field which allows the user to provide a comment on the parking. */
	private EditText _descriptionEdit;
	
	/** Will hold the result to return to the main activity. */
	private Intent intent;
	
	/** The name of the data return attached to the intent returned to the main activity. */
	public static final String PARKING_EXTRA = "PARKING_EXTRA";
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.new_parking_tip);
		
		// Hold the form inputs
		this._nicknameEdit = (EditText)
				findViewById(R.id.newParkingCreatorNickname);
		this._parkingNameEdit = (EditText)
				findViewById(R.id.newParkingName);
		this._townEdit = (EditText)
				findViewById(R.id.newParkingCity);
		this._parkingSizeEdit = (EditText)
				findViewById(R.id.newParkingSize);
		this._isChargedCheck = (CheckBox)
				findViewById(R.id.newParkingPricing);
		this._craftTypeSpinner = (Spinner)
				findViewById(R.id.newParkingCraftType);
		this._latitudeEdit = (EditText) 
				findViewById(R.id.newParkingCoordinateLatitude);
		this._longitudeEdit = (EditText)
				findViewById(R.id.newParkingCoordinateLongitude);
		this._descriptionEdit = (EditText)
				findViewById(R.id.newParkingComment);
		
		//Request location updates.
		this._pauParkLocation = PauParkLocation.getInstance();
		this._pauParkLocation.addObserver(this);
	}
	
	@Override
	protected void onStart()
	{
		super.onStart();
		
		// CraftType spinner
		SpinnerAdapter adapter = new ArrayAdapter<OfficialParking.CraftType>(
				this,
				android.R.layout.simple_spinner_item,
				OfficialParking.CraftType.values());
		this._craftTypeSpinner.setAdapter(adapter);
		
		// Pre-set user nickname from settings
		SharedPreferences preferences =
				this.getSharedPreferences(
						PauParkPreferences.class.getName(),
						Activity.MODE_PRIVATE);
		
		String nickName =
				preferences.getString(
						PauParkPreferences.NICKNAME_KEY, "");
		
		this._nicknameEdit.setText(nickName);
		
		//Get coordinates and town name if geoloc is used
		boolean useGeoLoc =
				preferences.getBoolean(
						PauParkPreferences.GEOLOCATION_PREF_KEY, true);

		if (useGeoLoc)
		{
			//set coordinates and town name if known
			this.updateCoordinates();
		}
		else
		{
			Toast
			.makeText(
					this,
					R.string.geolocation_status_disabled,
					Toast.LENGTH_SHORT)
			.show();
		}
	}
	
	/* ** Option Menu ** */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater =
				this.getMenuInflater();
		
		inflater.inflate(R.menu.add_tip_menu, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		case R.id.saveParkingTipAction:
			if ( this.checkInputs() )
			{
				this.terminate();
			}
			break;
		// Add other menu
		}
		
		return true;
	}
	
	/* ** Private helpers. ** */
	
	/**
	 * Check is the compulsory fields of the form have been filled and keeps the values if validated.
	 * 
	 * @return true if the input is valid, false othewise.
	 */
	private boolean checkInputs()
	{
		boolean valid = true;
		String errMess = "";
		
		OfficialParking.CraftType craftTypes[] =
				OfficialParking.CraftType.values();
		
		String authorNickName = null;
		String name = null;
		String town = null;
		String description = null;
		int capacity = 0;
		double coordLatitude = 0;
		double coordLongitude = 0;
		boolean isCharged = false;
		CraftType craftType = null;
		
		/* Compulsory */
		
		// NICKNAME
		authorNickName =
				this._nicknameEdit.getText().toString();
		if(authorNickName.equals(""))
		{
			errMess += this.getString(
					R.string.new_parking_creator_nickname_error) + "";
			valid = false;
		}
		else
		{
			// PARKING NAME
			name = 
					this._parkingNameEdit.getText().toString();
			if(name.equals(""))
			{
				errMess += this.getString(
						R.string.new_parking_name_error) + "";
				valid = false;
			}
			else
			{
				// TOWN
				town =
						this._townEdit.getText().toString();
				if(town.equals(""))
				{
					errMess += this.getString(R.string.new_parking_city_error) + "";
					valid = false;
				}
				else
				{
					// LATITUDE
					if(this._latitudeEdit.getText().toString().equals(""))
					{
						errMess += this.getString(R.string.new_parking_coordinates_latitude_error) + "";
						valid = false;
					}
					else
					{
						coordLatitude =
								Double.parseDouble(
								this._latitudeEdit.getText().toString());
						
						// LONGITUDE
						if(this._longitudeEdit.getText().toString().equals(""))
						{
							errMess += this.getString(R.string.new_parking_coordinates_longitude_error) + "";
							valid = false;
						}
						else
						{
							coordLongitude =
									Double.parseDouble(
									this._longitudeEdit.getText().toString());
						}
					}
				}
			}
		}
		
		/* No need for control */
		isCharged =
				this._isChargedCheck.isChecked();
		
		craftType =
				craftTypes[this._craftTypeSpinner.getSelectedItemPosition()];
		
		description =
				this._descriptionEdit.getText().toString();
		
		capacity =
				(!this._longitudeEdit.getText().toString().equals("")) ?
				Integer.parseInt(
						this._parkingSizeEdit.getText().toString())
				: 0;
		
		if(!valid)
		{
			Toast toast = Toast.makeText(this, errMess, Toast.LENGTH_SHORT);
			toast.show();
		}
		
		/* Set data intent*/
		if(valid)
		{
			this.intent = new Intent();
			
			UserTipParking parking = new UserTipParking(capacity, name, town,
					new GeoCoordinate(coordLatitude, coordLongitude), isCharged,
					craftType, description, authorNickName);
			
			this.intent.putExtra(AddTipActivity.PARKING_EXTRA, (Serializable)parking);
		}
		
		return valid;
	}
	
	/**
	 * Ends this activity, so that the application can go back to the main activity.
	 */
	private void terminate()
	{
		// Configure intent
		if(this.intent == null)
		{
			this.intent = new Intent();
		}
		
		this.intent.setClass(this, PauParkActivity.class);
		
		// Set result
		Activity parent = this.getParent();
		
		if (parent == null)
		{
		    this.setResult(Activity.RESULT_OK,
		    		this.intent);
		}
		else
		{
		    parent.setResult(Activity.RESULT_OK,
		    		this.intent);
		}
		
		this.finish();
	}

	/**
	 * Retrieves and updates coordinates and town name
	 */
	private void updateCoordinates()
	{
		//update fields with new data
		Location newLoc = this._pauParkLocation.getLocation();
		
		this._latitudeEdit.setText(String.valueOf(newLoc.getLatitude()));
		this._longitudeEdit.setText(String.valueOf(newLoc.getLongitude()));
		
		if(Geocoder.isPresent())
		{
			String town = this._pauParkLocation.getTown();
			
			if(town != null)
			{
				this._townEdit.setText(town);
			}
			else
			{
				Toast.makeText(
						this,
						R.string.geocoding_failed,
						Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	/**
	 * Receives location update notification from PauParkLocation.
	 */
	@Override
	public void update(Observable observable, Object data) 
	{
		this.updateCoordinates();
	}
}
