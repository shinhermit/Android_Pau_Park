package fr.univpau.paupark.view;

import fr.univpau.paupark.R;
import fr.univpau.paupark.service.ParkingsController;
import fr.univpau.paupark.view.tab.fragment.ParkingsTabFragment;
import fr.univpau.paupark.view.tab.fragment.TipsTabFragment;
import fr.univpau.paupark.view.tab.listener.TabListener;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Main activity of the application.
 * 
 * @author Josuah Aron
 *
 */
public class PauParkActivity extends Activity
{
	/** The return code of the setting modification activity. */
	private static final int SETTINGS_ACTIVITY_RESQUEST_CODE = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		this.createActionBarTabs();
	}
	
	/* ** Option Menu** */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater =
				this.getMenuInflater();
		
		inflater.inflate(R.menu.main_activity_menu, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		case R.id.settingsAction:
			this.startSettingsActivity();
			break;
		case R.id.addParkingTipAction:
			this.startAddTipActivity();
			break;
		case R.id.refreshAction:
			ParkingsController.getInstance().refreshParkings();
			break;
		}
		return true;
	}
	
	/* ** Activity Navigation ** */
	@Override
	protected void onActivityResult (int requestCode,
			int resultCode, Intent data)
	{
		// update UI after setting update ? (user nickname)
	}
	
	/**
	 * Starts the activity which allows to change the settings.
	 */
	public void startSettingsActivity()
	{
		Intent intent = new Intent();
		
		intent.setClass(this, SettingsActivity.class);
		
		this.startActivityForResult(intent,
				PauParkActivity.SETTINGS_ACTIVITY_RESQUEST_CODE);
	}
	
	/**
	 * Starts the activity which allows to add a new parking tip.
	 */
	public void startAddTipActivity()
	{
		Intent intent = new Intent();
		
		intent.setClass(this, AddTipActivity.class);
		
		this.startActivity(intent);
	}
	
	/**
	 * Creates the tabs of this activity.
	 */
	private void createActionBarTabs()
	{
		ActionBar bar = getActionBar();
	    bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    Tab tab;
	    
	    // First Tab
	    tab = bar.newTab();

        tab.setText(R.string.tab_parkings);
        tab.setTabListener(
    		   new TabListener<ParkingsTabFragment>(
                   this, 
                   "parkingsTab", 
                   ParkingsTabFragment.class)
           );
        bar.addTab(tab);

	    // Second Tab
	    tab = bar.newTab();
	    
        tab.setText(R.string.tab_tips);
        tab.setTabListener(
     		   new TabListener<TipsTabFragment>(
                       this, 
                       "tipsTab", 
                       TipsTabFragment.class)
                   );
        bar.addTab(tab);
        
		// setup action bar for tabs
	    bar.setDisplayShowTitleEnabled(false);
	}
}
