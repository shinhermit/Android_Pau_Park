package fr.univpau.paupark.service.async;

import java.net.URISyntaxException;
import java.net.URL;

import android.util.Log;
import fr.univpau.paupark.model.UserTipParking;
import fr.univpau.paupark.presenter.ParkingListAdapter;

/**
 * An async task which allows to save a parking tip and updates a provided presenter (adapter) when it completes.
 * 
 * @author Josuah Aron
 *
 */
public class SaveParkingAsyncTask extends AsbtractAsyncTask
{
	/** The parking tip which is to be inserted. */
	private UserTipParking parkingToInsert;
	
	/**
	 * 
	 * @param parkingToInsert the parking tip which is to be inserted.
	 * @param adapterToUpdate the presenter to update.
	 */
	public SaveParkingAsyncTask(UserTipParking parkingToInsert, ParkingListAdapter adapterToUpdate)
	{
		super(adapterToUpdate);
		
		this.parkingToInsert = parkingToInsert;
	}
	
	@Override
	protected Long doInBackground(URL... urls)
	{
		long status = AsbtractAsyncTask.SUCCESS;
		
		if(urls.length == 1)
		{
			try
			{
				String response = this.query(urls[0].toURI());
				
				long res = Long.parseLong(response);
				
				if(res >= 0)
				{
					parkingToInsert.setId(res);
				}
			}
			catch (URISyntaxException e)
			{
				status = AsbtractAsyncTask.FAILURE;
				
				Log.e(this.getClass().getName(), null, e);
			}
		}
		
		return status;
	}
	
	@Override
	protected void onPostExecute(Long result)
	{
		if(this.parkingToInsert.getId() >= 0)
		{
			adapter.add(parkingToInsert);
		}
		
		super.onPostExecute(result);
	}
}