package fr.univpau.paupark.view;

import fr.univpau.paupark.R;
import fr.univpau.paupark.model.PauParkPreferences;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Switch;

public class SettingsActivity extends Activity
{
	private Switch useGeolocSwitch;
	private Switch usePaginationSwitch;
	private EditText nickNameEditText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.settings);
		
		this.useGeolocSwitch = (Switch)
				this.findViewById(R.id.prefGeolocSwitch);
		
		this.usePaginationSwitch = (Switch)
				this.findViewById(R.id.prefPaginationSwitch);
		
		this.nickNameEditText = (EditText)
				this.findViewById(R.id.prefNicknameEditText);
	}
	
	@Override
	protected void onStart()
	{
		super.onStart();
		
		boolean useGeoloc;
		boolean usePagination;
		String nickName;
		
		// Retrieve preferences
		SharedPreferences preferences =
				this.getPreferences(Activity.MODE_PRIVATE);
		
		useGeoloc = preferences.getBoolean(
				PauParkPreferences.GEOLOCALISATION_PREF_KEY, true);
		
		usePagination = preferences.getBoolean(
				PauParkPreferences.PAGINATION_PREF_KEY, true);
		
		nickName = preferences.getString(PauParkPreferences.NICKNAME_KEY, "");
		
		// Update UI with preferences
		this.useGeolocSwitch.setChecked(useGeoloc);
		this.usePaginationSwitch.setChecked(usePagination);
		this.nickNameEditText.setText(nickName);
	}
	
	/* ** Option Menu** */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater =
				this.getMenuInflater();
		
		inflater.inflate(R.menu.settings_menu, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		case R.id.saveSettingsAction:
			this.saveSettings();
			this.terminate();
			break;
		// Add other menu
		}
		
		return true;
	}
	
	private void saveSettings()
	{
		// register preferences
		SharedPreferences preferences =
				this.getPreferences(Activity.MODE_PRIVATE);
		
		Editor preferenceEditor = preferences.edit();
		
		preferenceEditor.putBoolean(
				PauParkPreferences.GEOLOCALISATION_PREF_KEY,
				this.useGeolocSwitch.isChecked());
		
		preferenceEditor.putBoolean(
				PauParkPreferences.PAGINATION_PREF_KEY,
				this.usePaginationSwitch.isChecked());
		
		preferenceEditor.putString(PauParkPreferences.NICKNAME_KEY,
				this.nickNameEditText.getText().toString());
		
		preferenceEditor.commit();
	}
	
	private void terminate()
	{
		Intent data = new Intent();
		data.setClass(this, PauParkActivity.class);
		
		this.setResult(1, data);
		this.finish();
	}
}
