package fr.univpau.paupark.service.async;

import java.net.URISyntaxException;
import java.net.URL;

import android.util.Log;
import fr.univpau.paupark.presenter.ParkingListAdapter;

/**
 * An async task which allows to down-vote a parking tip and updates a provided presenter (adapter) when it completes.
 * 
 * @author Josuah Aron
 *
 */
public class DownVoteParkingTipAsyncTask extends AsbtractAsyncTask
{
	/**
	 * 
	 * @param adapterToUpdate the presenter to update.
	 */
	public DownVoteParkingTipAsyncTask(ParkingListAdapter adapterToUpdate)
	{
		super(adapterToUpdate);
	}
	
	@Override
	protected Long doInBackground(URL... urls)
	{
		long status = AsbtractAsyncTask.SUCCESS;
		
		if(urls.length == 1)
		{
			try
			{
				this.query(urls[0].toURI());
			}
			catch (URISyntaxException e)
			{
				status = AsbtractAsyncTask.FAILURE;
				
				Log.e(this.getClass().getName(), null, e);
			}
		}
		
		return status;
	}
}