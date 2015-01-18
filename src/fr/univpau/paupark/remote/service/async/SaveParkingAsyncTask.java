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
 * An async task which allows to save a parking tip and updates a provided presenter (adapter) when it completes.
 * 
 * @author Josuah Aron
 *
 */
public class SaveParkingAsyncTask extends AsbtractAsyncTask
{
	/** The parking tip which is to be inserted. */
	private UserTipParking inserted = null;
	
	/**
	 * 
	 * @param parkingToInsert the parking tip which is to be inserted.
	 * @param adapterToUpdate the presenter to update.
	 */
	public SaveParkingAsyncTask(ParkingListAdapter adapterToUpdate)
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
				
				List<AbstractParking> res =
						parser.parse(response);
				
				if(!res.isEmpty())
				{
					this.inserted =
							(UserTipParking) res.get(0);
					
					status = AsbtractAsyncTask.SUCCESS;
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
		if(this.inserted != null)
		{
			this.adapter.insert(this.inserted, 0);
		}
		
		super.onPostExecute(result);
	}
}