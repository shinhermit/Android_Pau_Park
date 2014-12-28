package fr.univpau.paupark.asynctask;

import java.net.URISyntaxException;
import java.net.URL;

import fr.univpau.paupark.service.Downloader;
import fr.univpau.paupark.service.ParkingsController;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

public class DownloadJSONTask extends AsyncTask<URL, Integer, Long> 
{
	private Activity _activity;
	/**
	 * TODO
	 * ???? Use of interface or superclass to abstract ????
	 */
	public DownloadJSONTask(Activity activity) 
	{
		this._activity = activity;
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
				final String result = Downloader.query(urls[0].toURI());
				
				this._activity.runOnUiThread(new Runnable() {
				     @Override
				     public void run() {
				    	 ParkingsController.getInstance().setDownloadResult(result);
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
