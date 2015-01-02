package fr.univpau.paupark.service.async;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import android.util.Log;
import fr.univpau.paupark.model.AbstractParking;
import fr.univpau.paupark.model.UserTipParking;
import fr.univpau.paupark.presenter.ParkingListAdapter;
import fr.univpau.paupark.service.JSONParkingParser;

/**
 * An async task which allows to save a parking tip and updates a provided presenter (adapter) when it completes.
 * 
 * @author Josuah Aron
 *
 */
public class SaveParkingAsyncTask extends AsbtractAsyncTask
{
	/** The parking tip which is to be inserted. */
	private UserTipParking inserted;
	
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
		long status = AsbtractAsyncTask.SUCCESS;
		
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
		if(this.inserted != null)
		{
			this.adapter.add(this.inserted);
		}
		
		super.onPostExecute(result);
	}
}