package fr.univpau.paupark.remote.service;

import fr.univpau.paupark.data.UserTipParking;
import fr.univpau.paupark.remote.service.async.event.OnTaskCompleteListener;
import fr.univpau.paupark.view.presenter.ParkingListAdapter;

/**
 * Services available on parking.
 * 
 * @author Josuah Aron
 *
 */
public interface RemoteParkingServices
{
	/**
	 * Represents the type of parking information requested.
	 *  
	 * @author Josuah Aron
	 *
	 */
	public enum ParkingInfoSource
	{
		/** The parking information from the official source. */
		OFFICIAL,
		/** The parking information from the user  tips. */
		USERS
	}
	
	/**
	 * Allows to do some actions when the service ends a task.
	 * 
	 * @param taskCompleteListener listens the event triggered when the service has finish a task.
	 */
	public void setOnTaskCompleteListener(
			OnTaskCompleteListener taskCompleteListener);
	
	/**
	 * Load the list of parking.
	 * 
	 * @param source the type of parking information requested (official source or user tips).
	 * @param destination the list adapter to fill with the loaded parking list.
	 */
	public void loadParkingList(
			ParkingInfoSource source, ParkingListAdapter destination);
	
	
	/**
	 * Load the list of parking.
	 * 
	 * @param source the type of parking information requested (official source or user tips).
	 * @param destination the list adapter to fill with the loaded parking list.
	 * @param startAt the starting position in the source list.
	 * @param nb the number of items to fetch.
	 */
	public void loadParkingList(ParkingInfoSource source,
			ParkingListAdapter destination, int startAt, int nb);
	
	/**
	 * Save the parking tip in the remote data source.
	 * @param updateMe the current list of parking tips (will be update after remote insertion).
	 */
	public void saveParkingTip(UserTipParking parking, ParkingListAdapter updateMe);
	
	/**
	 * Updates the parking tip from the remote data source.
	 */
	public void updateParkingTip(long id, UserTipParking newparking, ParkingListAdapter uodateMe);
	
	/**
	 * Delete the parking tip from the remote data source.
	 */
	public void deleteParkingTip(long id, ParkingListAdapter updateMe);
	
	/**
	 * Up-votes the parking tip.
	 * 
	 * @param id the id of the parking tip which is to be up-voted.
	 */
	public void upvoteParkingTip(long id, ParkingListAdapter updateMe);
	
	/**
	 * Down-votes the parking tip.
	 * 
	 * @param id the id of the parking tip which is to be down-voted.
	 * 
	 * @return true if down-vote request succeeded, false otherwise.
	 */
	public void downvoteParkingTip(long id, ParkingListAdapter updateMe);
}
