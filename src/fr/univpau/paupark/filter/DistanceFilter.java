package fr.univpau.paupark.filter;

import android.location.Location;
import fr.univpau.paupark.model.AbstractParking;
import fr.univpau.paupark.service.PauParkLocation;

public class DistanceFilter extends AbstractParkingFilter {
	public static String FILTER_ID = "com.univpau.paupark.distanceFilter";
	
	private Float distanceFilter = 0f;
	private Float previousDistanceFilter = 0f;
	
	@Override
	public boolean filterOut(AbstractParking parking) {
		boolean filterOut = true;
		
		//Reference point for distance measurement.
		Location currentLocation = PauParkLocation.getInstance().getLocation();

		if (currentLocation != null && this.distanceFilter != 0f)
		{
			// Comparison parameters available
			Location parkingLoc = new Location("parking");
			parkingLoc.setLatitude(parking.getCoordinates().getLatitude());
			parkingLoc.setLongitude(parking.getCoordinates().getLongitude());
			
			if (currentLocation.distanceTo(parkingLoc) < this.distanceFilter)
			{
				//parking is close enough
				filterOut = false;
			}
		}
		else
		{
			// Current location unknown 
			// or no distance selected => filter disabled.
			filterOut = false;
		}
		
		return filterOut;
	}

	@Override
	public void setValue(Object filterValue) {
		this.previousDistanceFilter = this.distanceFilter;
		
		this.distanceFilter = (Float) filterValue;
	}

	@Override
	public boolean isNewValue(Object value)
	{
		return this.distanceFilter.equals((Float) value) == false;		
	}
	
	@Override
	public String getFilterId() {
		return FILTER_ID;
	}

	@Override
	public void restorePreviousFilterValue() {
		this.distanceFilter = this.previousDistanceFilter;
	}

	
}
