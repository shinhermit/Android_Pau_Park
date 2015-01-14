package fr.univpau.paupark.view.menu.contextual;

import fr.univpau.paupark.R;
import fr.univpau.paupark.model.GeoCoordinate;
import fr.univpau.paupark.model.UserTipParking;
import fr.univpau.paupark.presenter.ParkingListAdapter;
import fr.univpau.paupark.service.ParkingServiceImpl;
import fr.univpau.paupark.service.ParkingServices;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
public class UserTipContextualActionModeCallBack implements ActionMode.Callback
{
	/** The adapter which manages the items of the list view.*/
	private ParkingListAdapter userTipListAdapter = null;
	
	/** Holds the application context. */
	private Context context = null;
	
	/** The currently selected item. */
	private long selectedItem = 0;
	
	/** The URL to start the google maps activity. */
	private static Uri GOOGLE_MAPS_URI = null;
	
	/** The value of the default zoom for the google maps activity URL. */
	private static String DEFAULT_ZOOM = null; 
	
	/**
	 * Constructor.
	 * 
	 * @param userTipListAdapter the adapter which manages the items of the list view.
	 */
	public UserTipContextualActionModeCallBack(
			ParkingListAdapter userTipListAdapter)
	{
		this.userTipListAdapter = userTipListAdapter;
		
		this.context = userTipListAdapter.getContext();
		
		this.initStaticMembers();
	}
	
	/**
	 * Constructor helper to initialize the static member only if they have not already been initialized.
	 */
	private void initStaticMembers()
	{
		if(UserTipContextualActionModeCallBack.DEFAULT_ZOOM == null)
		{
			UserTipContextualActionModeCallBack.DEFAULT_ZOOM = 
					this.context.getResources().getString(
							R.string.google_maps_default_zoom);
		}

		if(UserTipContextualActionModeCallBack.GOOGLE_MAPS_URI == null)
		{
			UserTipContextualActionModeCallBack.GOOGLE_MAPS_URI = Uri.parse(
					this.context.getResources().getString(
							R.string.google_maps_url));
		}
	}
	
	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu)
	{
		MenuInflater inflater = mode.getMenuInflater();
        
        inflater.inflate(R.menu.user_tip_action_menu, menu);
        
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

            case R.id.locateParkingAction:
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
	 * Allows the list view item click listener to provide the position of the selected item in the list.
	 * 
	 * @param selectedItem the position of the selected item in the list.
	 */
	public void setSelectedItem(long selectedItem)
	{
		this.selectedItem = selectedItem;
	}
	
	/**
	 * Helper to query an upvote for the selected item.
	 */
	private void upvoteParkingTip()
	{
		ParkingServices services = 
				ParkingServiceImpl.getInstance();
		
		UserTipParking parking = (UserTipParking)
				this.userTipListAdapter.getItem((int)this.selectedItem);
		
		if(parking != null)
		{
			services.upvoteParkingTip(
					parking.getId(),
					this.userTipListAdapter);
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
				this.userTipListAdapter.getItem((int)this.selectedItem);
		
		if(parking != null)
		{
			services.downvoteParkingTip(
					parking.getId(),
					this.userTipListAdapter);
		}
	}
	
	/**
	 * Helper to start the google maps activity with the coordinates of the selected item.
	 */
	private void startMapActivity()
	{
		UserTipParking parking = (UserTipParking)
				this.userTipListAdapter.getItem((int)this.selectedItem);
		
		if(parking != null)
		{
			GeoCoordinate coordinates = parking.getCoordinates();
			String label = parking.getName();
			
			String qValue= coordinates.getLatitude() + "," + coordinates.getLongitude() + "(" + label + ")";
			
			Uri googleMapsQuery = UserTipContextualActionModeCallBack.GOOGLE_MAPS_URI
					.buildUpon()
					.appendQueryParameter("q", qValue)
					.appendQueryParameter("z", UserTipContextualActionModeCallBack.DEFAULT_ZOOM)
					.build();
			
			Intent intent = new Intent(
					android.content.Intent.ACTION_VIEW,
					googleMapsQuery);
			
			this.context.startActivity(intent);
		}
	}
}