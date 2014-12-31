package fr.univpau.paupark.service;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import fr.univpau.paupark.R;
import fr.univpau.paupark.model.GeoCoordinate;
import fr.univpau.paupark.model.UserTipParking;
import fr.univpau.paupark.presenter.ParkingListAdapter;
import fr.univpau.paupark.service.async.DownVoteParkingTipAsyncTask;
import fr.univpau.paupark.service.async.LoadParkingListTask;
import fr.univpau.paupark.service.async.SaveParkingAsyncTask;
import fr.univpau.paupark.service.async.UpVoteParkingTipAsyncTask;

/**
 * Implementation of the services available on parking.
 * 
 * @author Josuah Aron
 *
 */
public class ParkingServiceImpl implements ParkingServices
{
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
	private static ParkingServiceImpl instance;
	
	/**
	 * Creates the singleton instance of this class.
	 * 
	 * @param context the android execution context.
	 * 
	 * @return the singleton instance of this class.
	 */
	public static final void create(Context context)
	{
		if(ParkingServiceImpl.instance == null)
		{
			// Create singleton
			ParkingServiceImpl.instance =
					new ParkingServiceImpl();
			
			// Initialise class
			try
			{
				ParkingServiceImpl.QUERY_OFFICIAL_PARKING = URI.create(
						context.getString(R.string.parking_official_source)
						).toURL();
				
				ParkingServiceImpl.QUERY_ALL_USER_TIPS = URI.create(
						context.getString(R.string.parking_user_tip_all)
						).toURL();
				
				ParkingServiceImpl.SELECT_USER_TIPS = URI.create(
						context.getString(R.string.parking_user_tip_select)
						).toURL();
				
				ParkingServiceImpl.ADD_USER_TIP = URI.create(
						context.getString(R.string.parking_user_tip_add)
						).toURL();
				
				ParkingServiceImpl.UPVOTE_USER_TIP = URI.create(
						context.getString(R.string.parking_user_tip_upvote)).toURL();
				
				ParkingServiceImpl.DOWNVOTE_USER_TIP = URI.create(
						context.getString(R.string.parking_user_tip_downvote)
						).toURL();
			}
			catch (MalformedURLException e)
			{
				Log.e(ParkingServiceImpl.class.getName(), null, e);
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
	public static final ParkingServices getInstance()
	{
		return ParkingServiceImpl.instance;
	}
	
	@Override
	public void loadParkingList(ParkingInfoSource source,
			ParkingListAdapter adapter)
	{
		URL query = null;
		
		switch(source)
		{
		case OFFICIAL:
			query = ParkingServiceImpl.QUERY_OFFICIAL_PARKING;
			break;
			
		case USERS:
			query = ParkingServiceImpl.QUERY_ALL_USER_TIPS;
			break;
		}
		
		new LoadParkingListTask(adapter).execute(query);
	}

	@Override
	public void loadParkingList(
			ParkingInfoSource source,
			ParkingListAdapter destination,
			int startAt,
			int endAt)
	{
		this.loadParkingList(source, destination); // TODO
	}

	@Override
	public void saveParkingTip(UserTipParking parking, ParkingListAdapter adapter)
	{
		URL base = ParkingServiceImpl.ADD_USER_TIP;
		URL query = null;
		GeoCoordinate coordinates = parking.getCoordinates();
		
		Uri queryURI = Uri.parse(base.toString())
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
			query = URI.create(queryURI.toString()).toURL();
			
			new SaveParkingAsyncTask(parking, adapter).execute(query);
		}
		catch (MalformedURLException e)
		{
			Log.e(this.getClass().getName(), null, e);
		}
	}

	@Override
	public void updateParkingTip(long id,
			UserTipParking newparking, ParkingListAdapter uodateMe)
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
		
		Uri queryURI = Uri.parse(ParkingServiceImpl.UPVOTE_USER_TIP.toString())
				.buildUpon()
				.appendQueryParameter("id", String.valueOf(id))
				.build();
		
		try
		{
			query = URI.create(queryURI.toString()).toURL();
			
			new UpVoteParkingTipAsyncTask(updateMe).execute(query);
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
		
		Uri queryURI = Uri.parse(ParkingServiceImpl.DOWNVOTE_USER_TIP.toString())
				.buildUpon()
				.appendQueryParameter("id", String.valueOf(id))
				.build();
		
		try
		{
			query = URI.create(queryURI.toString()).toURL();
			
			new DownVoteParkingTipAsyncTask(updateMe).execute(query);
		}
		catch (MalformedURLException e)
		{
			Log.e(this.getClass().getName(), null, e);
		}
	}
}
