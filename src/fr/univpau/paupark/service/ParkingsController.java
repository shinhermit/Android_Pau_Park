package fr.univpau.paupark.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import android.app.Activity;
import fr.univpau.paupark.R;
import fr.univpau.paupark.asynctask.DownloadJSONTask;
import fr.univpau.paupark.model.Parking;
import fr.univpau.paupark.model.ParkingsAdapter;

public class ParkingsController  {
	/**
	 * Singleton implementation.
	 */
	private static ParkingsController _INSTANCE = new ParkingsController();
	
	private ParkingsAdapter _adapter;
	
	private Activity _activity;
	
	/**
	 * Constructor
	 */
	public ParkingsController() {}
	
	/**
	 * Singleton implementation.
	 * @return
	 */
	public static ParkingsController getInstance() 
	{
		return _INSTANCE;
	}
	
	public void downloadParkings(Activity activity, ParkingsAdapter adapter)
	{
		this._activity = activity;
		this._adapter = adapter;
		
		this._loadParkings();
	}
	
	/**
	 * Loads list of parking through async task
	 */
	private void _loadParkings() 
	{
		//Create a new list (erasing any previously loaded data)
		this._adapter.clear();
		
		try 
		{
			String urlString = this._activity.getResources().getString(R.string.json_url);
			
			new DownloadJSONTask(this._activity).execute(new URL(urlString));
		} 
		catch (MalformedURLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Forces parking list to be reloaded.
	 */
	public void setExpired()
	{
		this._adapter.clear();
	}
	
	/**
	 * Called by {@link DownloadJSONTask} when download done.
	 * @param iterator presenting the list of downloaded parkings
	 */
	public void handleDownloadResult(Iterator<Parking> iterator)
	{		
		while (iterator.hasNext()) 
		{
			Parking p = iterator.next();
			this._adapter.add(p);
		}
		
		this._adapter.notifyDataSetChanged();;

	}

}
