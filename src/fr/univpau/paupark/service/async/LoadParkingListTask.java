package fr.univpau.paupark.service.async;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import fr.univpau.paupark.model.AbstractParking;
import fr.univpau.paupark.model.OfficialParking;
import fr.univpau.paupark.presenter.ParkingListAdapter;
import fr.univpau.paupark.service.JSONParkingParser;
import android.util.Log;

/**
 * An async task which allows to load a list of parking and updates a provided presenter (adapter) when it completes.
 * 
 * @author Josuah Aron
 *
 */
public class LoadParkingListTask extends AsbtractAsyncTask
{
	/** The loaded list of parkings. */
	private List<AbstractParking> parkings;
	
	/**
	 * 
	 * @param adapter the adapter to update when completed.
	 */
	public LoadParkingListTask(ParkingListAdapter adapter)
	{
		super(adapter);
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
				
				this.parkings = parser.parse(response);

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
		this.adapter.clear();
		
		if(this.parkings != null)
		{
			for(AbstractParking parking : this.parkings)
			{
				this.adapter.add(parking);
			}
		}
		
		super.onPostExecute(result);
	}
}
