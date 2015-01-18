package fr.univpau.paupark.view.menu.contextual;

import fr.univpau.paupark.R;
import fr.univpau.paupark.view.presenter.ParkingListAdapter;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * A contextual menu callback for official parking list items.
 * 
 * @author Josuah Aron
 *
 */
public class OfficialParkingContextualActionModeCallback
					extends AbstractParkingContextualActionModeCallback
{
	/**
	 * Constructor.
	 * 
	 * @param officialParkingListAdapter the adapter which manages the items of the list view.
	 */
	public OfficialParkingContextualActionModeCallback(
			ParkingListAdapter officialParkingListAdapter)
	{
		super(officialParkingListAdapter);
	}

	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu)
	{
		MenuInflater inflater = mode.getMenuInflater();
        
        inflater.inflate(R.menu.official_parking_contextual_menu, menu);
        
        return true;
	}

	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu menu)
	{
		return false;
	}

	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item)
	{
        switch (item.getItemId())
        {
            case R.id.locateOfficialParkingAction:
            	this.startMapActivity();
                mode.finish();
                return true;

            default:
            	return false;
        }
	}

	@Override
	public void onDestroyActionMode(ActionMode mode)
	{}
}
