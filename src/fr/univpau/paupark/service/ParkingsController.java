package fr.univpau.paupark.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;
import fr.univpau.paupark.asynctask.DownloadJSONTask;
import fr.univpau.paupark.model.Parking;
import fr.univpau.paupark.model.ParkingsAdapter;

public class ParkingsController  {
	/**
	 * Singleton implementation.
	 */
	private static ParkingsController _INSTANCE = new ParkingsController();
		
	/**
	 * Parser used to extract parkings from _JSONString 
	 */
	private JSONParkingParser _parser;
	
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
	
	public void downloadParkings(Activity activity, ParkingsAdapter adapter, JSONParkingParser parser)
	{
		this._parser = parser;
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
			URL url = new URL("http://opendata.agglo-pau.fr/sc/webserv.php?serv=getSj&ui=542293D8B5&did=18&proj=WGS84");
			new DownloadJSONTask(this._activity).execute(url);
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
	 * @param result
	 */
	public void setDownloadResult(String result)
	{
		Log.i("info", "in setDownloadResult");
		this._parser.setJsonString(result);
		
		Iterator<Parking> iterator = this._parser.iterator();
		Log.i("ParkingsRetriever", "setDownloadResult");
		int i = 0;
		while (iterator.hasNext()) 
		{
			Log.i("ParkingsRetriever", "" + i++);
			Parking p = iterator.next();
			this._adapter.add(p);
			this._adapter.notifyDataSetChanged();
		}

	}

}
