package fr.univpau.paupark.remote.service;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import fr.univpau.paupark.R;
import fr.univpau.paupark.model.GeoCoordinate;
import fr.univpau.paupark.model.UserTipParking;
import fr.univpau.paupark.remote.service.async.LoadParkingListAsyncTask;
import fr.univpau.paupark.remote.service.async.SaveParkingAsyncTask;
import fr.univpau.paupark.remote.service.async.VoteParkingTipAsyncTask;
import fr.univpau.paupark.remote.service.async.event.OnTaskCompleteListener;
import fr.univpau.paupark.view.presenter.ParkingListAdapter;

/**
 * Implementation of the services available on parking.
 * 
 * @author Josuah Aron
 *
 */
public class RemoteParkingServicesImpl implements RemoteParkingServices
{
	/** Allows to perform some actions when the service finish*/
	private OnTaskCompleteListener onTaskCompleteListener = null;
	
	/** The URL to query the official list of parking. */
	private static URL QUERY_OFFICIAL_PARKING;
	
	/** The URL to query the parking tips added by the users. */
	private static URL QUERY_ALL_USER_TIPS;
	
	/** The URL to query some of parking tips added by the users. */
	private static URL SELECT_USER_TIPS;
	
	/** The URL to query the insertion of a parking tip. */
	private static URL ADD_USER_TIP;
	
	/** The URL to query the up-vote of a parking tip. */
	private static URL UPVOTE_USER_TIP;
	
	/** The URL to query the down-vote of a parking tip. */
	private static URL DOWNVOTE_USER_TIP;
	
	/** The singleton instance of this class. */
	private static RemoteParkingServicesImpl instance;
	
	/**
	 * Creates the singleton instance of this class.
	 * 
	 * @param context the android execution context.
	 * 
	 * @return the singleton instance of this class.
	 */
	public static final void create(Context context)
	{
		if(RemoteParkingServicesImpl.instance == null)
		{
			// Create singleton
			RemoteParkingServicesImpl.instance =
					new RemoteParkingServicesImpl();
			
			// Initialise class
			try
			{
				RemoteParkingServicesImpl.QUERY_OFFICIAL_PARKING = URI.create(
						context.getString(R.string.parking_official_source)
						).toURL();
				
				RemoteParkingServicesImpl.QUERY_ALL_USER_TIPS = URI.create(
						context.getString(R.string.parking_user_tip_all)
						).toURL();
				
				RemoteParkingServicesImpl.SELECT_USER_TIPS = URI.create(
						context.getString(R.string.parking_user_tip_select)
						).toURL();
				
				RemoteParkingServicesImpl.ADD_USER_TIP = URI.create(
						context.getString(R.string.parking_user_tip_add)
						).toURL();
				
				RemoteParkingServicesImpl.UPVOTE_USER_TIP = URI.create(
						context.getString(R.string.parking_user_tip_upvote)).toURL();
				
				RemoteParkingServicesImpl.DOWNVOTE_USER_TIP = URI.create(
						context.getString(R.string.parking_user_tip_downvote)
						).toURL();
			}
			catch (MalformedURLException e)
			{
				Log.e(RemoteParkingServicesImpl.class.getName(), null, e);
			}
		}
	}

	/**
	 * Provides the singleton instance of this class.
	 * 
	 * @param context the android execution context.
	 * 
	 * @return the singleton instance of this class.
	 */
	public static final RemoteParkingServices getInstance()
	{
		return RemoteParkingServicesImpl.instance;
	}
	
	@Override
	public void setOnTaskCompleteListener(
			OnTaskCompleteListener onTaskCompleteListener)
	{
		this.onTaskCompleteListener = onTaskCompleteListener;
	}
	
	@Override
	public void loadParkingList(ParkingInfoSource source,
			ParkingListAdapter adapter)
	{
		URL query = null;
		
		switch(source)
		{
		case OFFICIAL:
			query = RemoteParkingServicesImpl.QUERY_OFFICIAL_PARKING;
			break;
			
		case USERS:
			query = RemoteParkingServicesImpl.QUERY_ALL_USER_TIPS;
			break;
		}
		
		new LoadParkingListAsyncTask(adapter)
				.setOnTaskCompleteListener(this.onTaskCompleteListener)
				.execute(query);
	}

	@Override
	public void loadParkingList(
			ParkingInfoSource source,
			ParkingListAdapter adapter,
			int startAt,
			int nb)
	{
		URL base = null;
		
		switch(source)
		{
		case OFFICIAL:
			base = RemoteParkingServicesImpl.QUERY_OFFICIAL_PARKING;
			break;
			
		case USERS:
			base = RemoteParkingServicesImpl.QUERY_ALL_USER_TIPS;
			break;
		}
		
		Uri queryUri = Uri.parse(base.toString())
				.buildUpon()
				.appendQueryParameter("start", String.valueOf(startAt))
				.appendQueryParameter("count", String.valueOf(nb))
				.build();
		
		try
		{
			URL query = URI.create(queryUri.toString()).toURL();
			
			new LoadParkingListAsyncTask(adapter)
					.setOnTaskCompleteListener(this.onTaskCompleteListener)
					.execute(query);
		}
		catch (MalformedURLException e)
		{
			Log.e(this.getClass().getName(), null, e);
		}
	}

	@Override
	public void saveParkingTip(UserTipParking parking,
			ParkingListAdapter adapter)
	{
		URL base = RemoteParkingServicesImpl.ADD_USER_TIP;
		GeoCoordinate coordinates = parking.getCoordinates();
		
		Uri queryUri = Uri.parse(base.toString())
				.buildUpon()
				.appendQueryParameter("name", parking.getName())
				.appendQueryParameter("town", parking.getTown())
				.appendQueryParameter("free", String.valueOf(parking.isCharged() ? 0 : 1))
				.appendQueryParameter("ouvrage", parking.getCraftType().toString())
				.appendQueryParameter("lat", String.valueOf(coordinates.getLatitude()))
				.appendQueryParameter("lng", String.valueOf(coordinates.getLongitude()))
				.appendQueryParameter("description", parking.getDescription())
				.appendQueryParameter("pseudo", parking.getAuthorNickname())
				.appendQueryParameter("vacancy", String.valueOf(parking.getCapacity()))
				.build();
		
		try
		{
			URL query = URI.create(queryUri.toString()).toURL();
			
			new SaveParkingAsyncTask(adapter)
					.setOnTaskCompleteListener(this.onTaskCompleteListener)
					.execute(query);
		}
		catch (MalformedURLException e)
		{
			Log.e(this.getClass().getName(), null, e);
		}
	}

	@Override
	public void updateParkingTip(long id,
			UserTipParking newparking, ParkingListAdapter updateMe)
	{
		// TODO
	}

	@Override
	public void deleteParkingTip(long id, ParkingListAdapter updateMe)
	{
		// TODO
	}

	@Override
	public void upvoteParkingTip(long id, ParkingListAdapter updateMe)
	{
		URL query = null;
		
		Uri queryUri = Uri.parse(RemoteParkingServicesImpl.UPVOTE_USER_TIP.toString())
				.buildUpon()
				.appendQueryParameter("id", String.valueOf(id))
				.build();
		
		try
		{
			query = URI.create(queryUri.toString()).toURL();
			
			new VoteParkingTipAsyncTask(updateMe)
					.setOnTaskCompleteListener(this.onTaskCompleteListener)
					.execute(query);
		}
		catch (MalformedURLException e)
		{
			Log.e(this.getClass().getName(), null, e);
		}
	}

	@Override
	public void downvoteParkingTip(long id, ParkingListAdapter updateMe)
	{
		URL query = null;
		
		Uri queryUri = Uri.parse(RemoteParkingServicesImpl.DOWNVOTE_USER_TIP.toString())
				.buildUpon()
				.appendQueryParameter("id", String.valueOf(id))
				.build();
		
		try
		{
			query = URI.create(queryUri.toString()).toURL();
			
			new VoteParkingTipAsyncTask(updateMe)
					.setOnTaskCompleteListener(this.onTaskCompleteListener)
					.execute(query);
		}
		catch (MalformedURLException e)
		{
			Log.e(this.getClass().getName(), null, e);
		}
	}
}
