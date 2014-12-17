package fr.univpau.paupark.activity;

import fr.univpau.paupark.R;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

public class PauParkActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parkings_tab);
		
		this.createActionBarTabs();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater =
				this.getMenuInflater();
		
		inflater.inflate(R.menu.settings_menu, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
	private void createActionBarTabs()
	{
		ActionBar bar = getActionBar();
	    bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    
	    Tab tab = bar.newTab();
        tab.setText(R.string.tab_parkings);
        tab.setTabListener(new ParkingsTabListener(this));
        bar.addTab(tab);
        
	    tab = bar.newTab();
        tab.setText(R.string.tab_tips);
        tab.setTabListener(new TipsTabListener(this));
        bar.addTab(tab);
	}
}
