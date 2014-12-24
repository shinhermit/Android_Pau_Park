package fr.univpau.paupark.asynctask;

import java.net.URISyntaxException;
import java.net.URL;

import fr.univpau.paupark.model.ParkingList;
import fr.univpau.paupark.service.Downloader;
import android.os.AsyncTask;
import android.util.Log;

public class DownloadJSONTask extends AsyncTask<URL, Integer, Long> {
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected Long doInBackground(URL... urls) {
        
        if (urls.length == 1) 
        {
        	try {
				String result = Downloader.query(urls[0].toURI());
				ParkingList.getInstance().setListAsJson(result);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

        return 0L;
	}
	
	@Override
	protected void onPostExecute(Long result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		Log.i("errerrerr", "in onPostExecute");
		Log.i("list list list", ParkingList.getInstance().getListAsJson());
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

	

}
