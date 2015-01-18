package fr.univpau.paupark.remote.service.async;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import fr.univpau.paupark.remote.service.async.event.OnTaskCompleteListener;
import fr.univpau.paupark.view.presenter.ParkingListAdapter;
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
	/** Allows to perform some actions when the service finish*/
	private OnTaskCompleteListener onTaskCompleteListener = null;
	
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
	 * Allows to do some actions when the service ends a task.
	 * 
	 * @param taskCompleteListener listens the event triggered when the service has finish a task.
	 * @return this object (for chaining).
	 */
	public AsbtractAsyncTask setOnTaskCompleteListener(
			OnTaskCompleteListener onTaskCompleteListener)
	{
		this.onTaskCompleteListener = onTaskCompleteListener;
		
		return this;
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
					EntityUtils.toString(httpEntity, HTTP.UTF_8);
			
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
	
	@Override
	protected void onPostExecute(Long result)
	{
		if(this.onTaskCompleteListener != null)
		{
			this.onTaskCompleteListener.onTaskComplete();
		}
		
		super.onPostExecute(result);
	}
}
