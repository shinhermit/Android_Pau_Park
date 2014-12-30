package fr.univpau.paupark.service;

import java.net.URL;
import java.util.Iterator;

import android.app.Activity;
import android.widget.ArrayAdapter;
import fr.univpau.paupark.asynctask.DownloadJSONTask;
import fr.univpau.paupark.model.Parking;

public abstract class ParkingsController  {
	private ArrayAdapter<Parking> _adapter;
	
	private Activity _activity;
	
	private URL _serviceURL;
		
	public void initialize(Activity activity, ArrayAdapter<Parking> adapter, URL serviceURL)
	{
		this._activity = activity;
		this._adapter = adapter;
		this._serviceURL = serviceURL;
	}
	
	/**
	 * TODO clean up interface
	 */
	public void downloadParkings()
	{
		this._loadParkings();
	}
	
	/**
	 * TODO clean up interface
	 * Loads list of parking through async task
	 */
	private void _loadParkings() 
	{
		//Create a new list (erasing any previously loaded data)
		this._adapter.clear();
		
		new DownloadJSONTask(this, this._activity).execute(this._serviceURL);		
	}
	
	/**
	 * TODO clean up interface
	 * Forces parking list to be reloaded.
	 */
	public void refreshParkings()
	{
		this._loadParkings();
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
