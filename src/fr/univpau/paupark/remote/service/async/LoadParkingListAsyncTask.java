package fr.univpau.paupark.remote.service.async;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import fr.univpau.paupark.data.AbstractParking;
import fr.univpau.paupark.data.json.JSONParkingParser;
import fr.univpau.paupark.util.ParkingDistanceComparator;
import fr.univpau.paupark.view.presenter.ParkingListAdapter;
import android.util.Log;

/**
 * An async task which allows to load a list of parking and updates a provided presenter (adapter) when it completes.
 * 
 * @author Josuah Aron
 *
 */
public class LoadParkingListAsyncTask extends AsbtractAsyncTask
{
	/** The loaded list of parkings. */
	private List<AbstractParking> parkings;
	
	/**
	 * 
	 * @param adapterToUpdate the adapter to update when completed.
	 */
	public LoadParkingListAsyncTask(ParkingListAdapter adapterToUpdate)
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
				
				this.parkings = parser.parse(response);
				
				if(!this.parkings.isEmpty())
				{
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
		this.adapter.clear();
		
		if(this.parkings != null)
		{
			for(AbstractParking parking : this.parkings)
			{
				this.adapter.add(parking);
			}
			
			// sort parkings by distance
			ParkingDistanceComparator distanceToParkingComparator =
					new ParkingDistanceComparator();
			this.adapter.sort(distanceToParkingComparator);
		}
		
		super.onPostExecute(result);
	}
}
