package fr.univpau.paupark.listener;

import fr.univpau.paupark.remote.service.RemoteParkingServices.ParkingInfoSource;
import fr.univpau.paupark.view.PauParkActivity;
import fr.univpau.paupark.view.tab.fragment.AbstractParkingTabFragment;
import android.app.Fragment;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

/**
 * Listener for the SearchView Button. Triggers name filtering.
 * 
 * @author Émilien Arino
 * @author Josuah Aron
 *
 */
public class OnFilterByNameTextListener implements OnQueryTextListener
{
	/** Last query */
	private String lastSearch = "";
	
	/** The acitivity being executed. */
	private PauParkActivity activity;
	
	/** The input field containing the query to perform */
	private SearchView inputField;
	/**
	 * Constructor.
	 * 
	 * @param tab the fragment where the list of parking is.
	 */
	public OnFilterByNameTextListener(
			PauParkActivity activity,
			SearchView inputField)
	{
		this.activity = activity;
		this.inputField = inputField;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		boolean success = false;
		
		if (!this.lastSearch.equals(query))
		{
			success = this.executeSearchQuery(query);
		}

		
		return success;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		// TODO Auto-generated method stub
		return false;
	}

	// Called by SearchView.onCloseListener
	public void reset()
	{
		this.lastSearch = "";
		//Make sure the list is not filtered by name
		this.executeSearchQuery("");
	}
	
	private boolean executeSearchQuery(String query)
	{
		boolean success = false;
		
		Fragment tabFragment;
		if (this.activity.getSource() == ParkingInfoSource.OFFICIAL)
		{
			tabFragment = this.activity.getOfficialParkingTabFragment();
		}
		else
		{
			tabFragment = this.activity.getUserTipParkingTabFragment();
		}
		
		boolean filterUpdated = 
				((AbstractParkingTabFragment)tabFragment)
					.setFilterByNameValue(query);
		
		if (filterUpdated)
		{
			this.lastSearch = query;
			success = true;
		}
		else
		{
			// cancel change
			this.inputField.setQuery(this.lastSearch, false);
		}
		
		return success;
	}
}
