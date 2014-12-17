package fr.univpau.paupark.view;

import fr.univpau.paupark.R;
import fr.univpau.paupark.view.tab.fragment.ParkingsTabFragment;
import fr.univpau.paupark.view.tab.fragment.TipsTabFragment;
import fr.univpau.paupark.view.tab.listener.TabListener;
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
	    Tab tab;
	    
	    // First Tab
	    tab = bar.newTab();

        tab.setText(R.string.tab_parkings);
        tab.setTabListener(
    		   new TabListener<TipsTabFragment>(
                   this, 
                   "tipsTab", 
                   TipsTabFragment.class)
           );
        bar.addTab(tab);

	    // Second Tab
	    tab = bar.newTab();
	    
        tab.setText(R.string.tab_tips);
        tab.setTabListener(
     		   new TabListener<ParkingsTabFragment>(
                       this, 
                       "parkingsTab", 
                       ParkingsTabFragment.class)
                   );
        bar.addTab(tab);
        
		// setup action bar for tabs
	    bar.setDisplayShowTitleEnabled(false);
	}
}
