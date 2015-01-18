package fr.univpau.paupark.remote.service.async;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import android.util.Log;
import fr.univpau.paupark.data.AbstractParking;
import fr.univpau.paupark.data.UserTipParking;
import fr.univpau.paupark.data.json.JSONParkingParser;
import fr.univpau.paupark.view.presenter.ParkingListAdapter;

/**
 * An async task which allows to up-vote a parking tip and updates a provided presenter (adapter) when it completes.
 * 
 * @author Josuah Aron
 *
 */
public class VoteParkingTipAsyncTask extends AsbtractAsyncTask
{
	/** The parking tip which is to be up/down voted. */
	private AbstractParking modifiedParking = null;

	/**
	 * 
	 * @param adapterToUpdate the presenter to update when completed.
	 */
	public VoteParkingTipAsyncTask(ParkingListAdapter adapterToUpdate)
	{
		super(adapterToUpdate);
	}
	
	@Override
	protected Long doInBackground(URL... urls)
	{
		long status = AsbtractAsyncTask.FAILURE;
		
		if(urls.length == 1)
		{
			try
			{
				String response = this.query(urls[0].toURI());

				JSONParkingParser parser =
						new JSONParkingParser();
				
				List<AbstractParking> parkings =
						parser.parse(response);
				
				if(!parkings.isEmpty())
				{
					status = AsbtractAsyncTask.SUCCESS;
					
					this.modifiedParking = parkings.get(0);
				}
			}
			catch (URISyntaxException e)
			{
				Log.e(this.getClass().getName(), null, e);
			}
		}
		
		return status;
	}
	
	@Override
	protected void onPostExecute(Long result)
	{
		if(this.modifiedParking != null)
		{
			int i = 0;
			boolean removed = false;
			AbstractParking park;
			
			while(i < this.adapter.getCount()
					&& !removed)
			{
				park = this.adapter.getItem(i);
				
				if(((UserTipParking)park).getId() ==
						((UserTipParking)this.modifiedParking).getId())
				{
					// Remove old object
					this.adapter.remove(park);
					
					removed = true;
					
					// Add modified
					this.adapter.insert(this.modifiedParking, i);
				}
				
				++i;
			}
		}
		
		super.onPostExecute(result);
	}
}