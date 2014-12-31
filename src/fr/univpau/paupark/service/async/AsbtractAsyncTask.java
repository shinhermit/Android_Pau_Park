package fr.univpau.paupark.service.async;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import fr.univpau.paupark.presenter.ParkingListAdapter;
import android.os.AsyncTask;
import android.util.Log;

/**
 * An async task which updates a presenter (adapter) when it completes.
 * 
 * @author Josuah Aron
 *
 */
public abstract class AsbtractAsyncTask extends AsyncTask<URL, Integer, Long>
{
	/** The presenter to update after task is complete. */
	protected ParkingListAdapter adapter;

	/** Return code when query fails. */
	protected static final long FAILURE = -1l;
	
	/** Return code when query succeeds. */
	protected static final long SUCCESS = 0l;
	
	/**
	 * Constructor.
	 * 
	 * @param adapter the presenter to update after task is complete.
	 */
	public AsbtractAsyncTask(ParkingListAdapter adapter)
	{
		this.adapter = adapter;
	}
	
	/**
	 * 
	 * @return the adapter which is to be updated by this task.
	 */
	public ParkingListAdapter getAdapter()
	{
		return adapter;
	}

	/**
	 * Executes a query on the network.
	 * 
	 * @param uri the URI to query.
	 * 
	 * @return the result of the query.
	 */
	protected String query(URI uri)
	{
		HttpEntity httpEntity;
		HttpResponse response;
		
		String responseString = null;
		
		DefaultHttpClient client =
				new DefaultHttpClient();
		
		try
		{
			//request 
			HttpGet request = new HttpGet(uri);
			
			response = client.execute(request);

			httpEntity = response.getEntity();
			
			responseString =
					EntityUtils.toString(httpEntity);
			
		}
		catch (ClientProtocolException e)
		{
			Log.e(this.getClass().getName(), null, e);
		}
		catch (IOException e)
		{
			Log.e(this.getClass().getName(), null, e);
 		}
		
		return responseString;
	}
}
