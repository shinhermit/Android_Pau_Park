package fr.univpau.paupark.view;

import fr.univpau.paupark.R;
import android.app.Activity;
import android.os.Bundle;

public class SettingsActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.settings);
	}
}
