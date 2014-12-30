package fr.univpau.paupark.asynctask;

import java.net.URISyntaxException;
import java.net.URL;

import fr.univpau.paupark.service.Downloader;
import fr.univpau.paupark.service.JSONParkingParser;
import fr.univpau.paupark.service.ParkingsController;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

public class DownloadJSONTask extends AsyncTask<URL, Integer, Long> 
{
	private Activity _activity;

	private ParkingsController _controller;
	
	/**
	 * TODO
	 * ???? Use of interface or superclass to abstract ????
	 */
	public DownloadJSONTask(ParkingsController controller, Activity activity) 
	{
		this._activity = activity;
		this._controller = controller;
	}
	
	@Override
	protected void onPreExecute() 
	{
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected Long doInBackground(URL... urls) 
	{
        
        if (urls.length == 1) 
        {
        	try 
        	{
				String result = Downloader.query(urls[0].toURI());
				final JSONParkingParser parser = new JSONParkingParser();
				parser.setJsonString(result);
				
				this._activity.runOnUiThread(new Runnable() {
				     @Override
				     public void run() {
				    	 _controller.handleDownloadResult(parser.iterator());
//				    	 OpenDataParkings.getInstance().handleDownloadResult(parser.iterator());
				    }
				});
				
				
			} 
        	catch (URISyntaxException e) 
        	{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

        return 0L;
	}
	
	@Override
	protected void onPostExecute(Long result) 
	{
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		Log.i("errerrerr", "in onPostExecute");
	}

	@Override
	protected void onProgressUpdate(Integer... values) 
	{
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

	

}
