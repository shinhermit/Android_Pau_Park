package fr.univpau.paupark.view;

import java.util.ArrayList;

import fr.univpau.paupark.R;
import fr.univpau.paupark.model.AbstractParking;
import fr.univpau.paupark.model.OfficialParking;
import fr.univpau.paupark.model.UserTipParking;
import fr.univpau.paupark.presenter.ParkingListAdapter;
import fr.univpau.paupark.service.ParkingServiceImpl;
import fr.univpau.paupark.service.ParkingServices;
import fr.univpau.paupark.service.ParkingServices.ParkingInfoSource;
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
	/** Holds the current source of parking list (official vs user tips). */
	private ParkingInfoSource parkingSource = null;
	
	/** The presenter of the official parking list. */
	private ParkingListAdapter officialParkingListAdapter;
	
	/** The presenter of the parking tip list. */
	private ParkingListAdapter parkingTipListAdapter;
	
	/** The return code of the setting modification activity. Package visibility.*/
	static final int SETTINGS_ACTIVITY_RESQUEST_CODE = 1;
	
	/** The return code of the parking tip addition activity. Package visibility.*/
	static final int ADD_TIP_ACTIVITY_RESQUEST_CODE = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		this.createActionBarTabs();
		
		ParkingServiceImpl.create(this);
		
		// Allows internationalisation
		OfficialParking.CraftType.init(this);
		
		this.officialParkingListAdapter =
				new ParkingListAdapter(this, R.id.parkingsListHolder,
						new ArrayList<AbstractParking>());
		
		this.parkingTipListAdapter =
				new ParkingListAdapter(this, R.id.parkingsListHolder,
						new ArrayList<AbstractParking>()); // TODO
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
			this.refresh(this.parkingSource);
			break;
		}
		return true;
	}
	
	/* ** Activity Navigation ** */
	@Override
	protected void onActivityResult (int requestCode,
			int resultCode, Intent data)
	{
		switch(requestCode)
		{
		case PauParkActivity.ADD_TIP_ACTIVITY_RESQUEST_CODE:
			if(data != null)
			{
				ParkingServices services = 
					ParkingServiceImpl.getInstance();
		
				services.saveParkingTip(
						(UserTipParking)data.getSerializableExtra(AddTipActivity.PARKING_EXTRA),
					this.parkingTipListAdapter);
			}
			break;
		}
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
		
		this.startActivityForResult(intent,
				PauParkActivity.ADD_TIP_ACTIVITY_RESQUEST_CODE);
	}
	
	/**
	 * Refreshes the list of parking from the specified source.
	 * 
	 * @param source the parking information source (official/users tips).
	 */
	private void refresh(ParkingInfoSource source)
	{
		// Query load parking service
		ParkingServices services =
				ParkingServiceImpl.getInstance();
		
		ParkingListAdapter adapter =
				(source == ParkingInfoSource.OFFICIAL) ?
				this.officialParkingListAdapter :
					this.parkingTipListAdapter;
		
		services.loadParkingList(source, adapter);
	}

	/**
	 * Provides the presenter of the official parking list (useful for tab fragments).
	 * 
	 * @return the presenter of the official parking list.
	 */
	public ParkingListAdapter getOfficialParkingListAdapter()
	{
		return officialParkingListAdapter;
	}

	/**
	 * Provides the presenter of the parking tip list (useful for tab fragments).
	 * 
	 * @return the presenter of the parking tip list.
	 */
	public ParkingListAdapter getParkingTipListAdapter()
	{
		return parkingTipListAdapter;
	}
	
	/**
	 * Provides the current source of parking list (official vs user tips).
	 * 
	 * @return the current source of parking list.
	 */
	public ParkingInfoSource getSource()
	{
		return parkingSource;
	}

	/**
	 * Defines the current source of parking list (official vs user tips).
	 * 
	 * <p>Useful for  feedback from the tab listener.</p>
	 * 
	 * @param source the current source of parking list.
	 */
	public void setParkingSource(ParkingInfoSource source)
	{
		this.parkingSource = source;
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
