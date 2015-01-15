package fr.univpau.paupark.view.menu.contextual;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.ActionMode;
import fr.univpau.paupark.R;
import fr.univpau.paupark.model.AbstractParking;
import fr.univpau.paupark.model.GeoCoordinate;
import fr.univpau.paupark.presenter.ParkingListAdapter;

/**
 * Common base for contextual menus callback.
 * 
 * @author Josuah Aron
 *
 */
public abstract class AbstractParkingContextualActionModeCallback implements ActionMode.Callback
{
	/** The adapter which manages the items of the list view.*/
	protected ParkingListAdapter parkingListAdapter = null;
	
	/** Holds the application context. */
	protected Context context = null;
	
	/** The currently selected item. */
	protected long selectedItem = 0;
	
	/** The URL to start the google maps activity. */
	protected static Uri GOOGLE_MAPS_URI = null;
	
	/** The value of the default zoom for the google maps activity URL. */
	protected static String DEFAULT_ZOOM = null;
	
	/**
	 * Constructor.
	 * @param parkingListAdapter the adapter which manages the items of the list view.
	 */
	public AbstractParkingContextualActionModeCallback(
			ParkingListAdapter parkingListAdapter)
	{
		this.parkingListAdapter = parkingListAdapter;
		
		this.context = parkingListAdapter.getContext();
		
		this.initStaticMembers();
	}
	
	/**
	 * Constructor's helper to initialise the static member only if they have not already been initialised.
	 */
	private void initStaticMembers()
	{
		if(AbstractParkingContextualActionModeCallback.DEFAULT_ZOOM == null)
		{
			AbstractParkingContextualActionModeCallback.DEFAULT_ZOOM = 
					this.context.getResources().getString(
							R.string.google_maps_default_zoom);
		}

		if(AbstractParkingContextualActionModeCallback.GOOGLE_MAPS_URI == null)
		{
			AbstractParkingContextualActionModeCallback.GOOGLE_MAPS_URI = Uri.parse(
					this.context.getResources().getString(
							R.string.google_maps_url));
		}
	}
	
	/**
	 * Helper to start the google maps activity with the coordinates of the selected item.
	 */
	protected void startMapActivity()
	{
		AbstractParking parking = 
				this.parkingListAdapter.getItem((int)this.selectedItem);
		
		if(parking != null)
		{
			GeoCoordinate coordinates = parking.getCoordinates();
			String label = parking.getName();
			
			String qValue= coordinates.getLatitude() + "," + coordinates.getLongitude() + "(" + label + ")";
			
			Uri googleMapsQuery = UserTipContextualActionModeCallback.GOOGLE_MAPS_URI
					.buildUpon()
					.appendQueryParameter("q", qValue)
					.appendQueryParameter("z", UserTipContextualActionModeCallback.DEFAULT_ZOOM)
					.build();
			
			Intent intent = new Intent(
					android.content.Intent.ACTION_VIEW,
					googleMapsQuery);
			
			this.context.startActivity(intent);
		}
	}
	
	/**
	 * Allows the list view item click listener to provide the position of the selected item in the list.
	 * 
	 * @param selectedItem the position of the selected item in the list.
	 */
	public void setSelectedItem(long selectedItem)
	{
		this.selectedItem = selectedItem;
	}
}
