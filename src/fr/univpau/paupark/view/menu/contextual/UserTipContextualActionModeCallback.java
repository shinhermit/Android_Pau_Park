package fr.univpau.paupark.view.menu.contextual;

import fr.univpau.paupark.R;
import fr.univpau.paupark.model.UserTipParking;
import fr.univpau.paupark.presenter.ParkingListAdapter;
import fr.univpau.paupark.service.ParkingServiceImpl;
import fr.univpau.paupark.service.ParkingServices;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * A contextual menu callback for user tip parking list items.
 * 
 * @author Josuah Aron
 *
 */
public class UserTipContextualActionModeCallback
					extends AbstractParkingContextualActionModeCallback
{
	/**
	 * Constructor.
	 * 
	 * @param userTipListAdapter the adapter which manages the items of the list view.
	 */
	public UserTipContextualActionModeCallback(
			ParkingListAdapter userTipListAdapter)
	{
		super(userTipListAdapter);
	}
	
	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu)
	{
		MenuInflater inflater = mode.getMenuInflater();
        
        inflater.inflate(R.menu.user_tip_contextual_menu, menu);
        
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
            case R.id.upVoteTipAction:
            	this.upvoteParkingTip();
                mode.finish();
                return true;

            case R.id.downVoteTipAction:
            	this.downvoteParkingTip();
                mode.finish();
                return true;

            case R.id.locateUserTipParkingAction:
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
	
	/**
	 * Helper to query an upvote for the selected item.
	 */
	private void upvoteParkingTip()
	{
		ParkingServices services = 
				ParkingServiceImpl.getInstance();
		
		UserTipParking parking = (UserTipParking)
				this.parkingListAdapter.getItem((int)this.selectedItem);
		
		if(parking != null)
		{
			services.upvoteParkingTip(
					parking.getId(),
					this.parkingListAdapter);
		}
	}
	
	/**
	 * Helper to query a downvote for the selected item.
	 */
	private void downvoteParkingTip()
	{
		ParkingServices services = 
				ParkingServiceImpl.getInstance();
		
		UserTipParking parking = (UserTipParking)
				this.parkingListAdapter.getItem((int)this.selectedItem);
		
		if(parking != null)
		{
			services.downvoteParkingTip(
					parking.getId(),
					this.parkingListAdapter);
		}
	}
}