package fr.univpau.paupark.filter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;
import android.location.Location;
import fr.univpau.paupark.R;
import fr.univpau.paupark.model.AbstractParking;
import fr.univpau.paupark.service.PauParkLocation;

/**
 *
 * Filter by distance.
 *
 */
public class DistanceFilter extends AbstractParkingFilter {
	public static String FILTER_ID = "com.univpau.paupark.distanceFilter";
	
//	public static List<String, Float> filterOptions = new LinkedHashMap<String, Float>();
	private static List<String> sFilterLabels = new ArrayList<String>();
	private static List<Float> sFilterValues = new ArrayList<Float>();
	public static Float FILTER_BY_DISTANCE_0M_VALUE = 0f;
	public static Float FILTER_BY_DISTANCE_250M_VALUE = 250f;
	public static Float FILTER_BY_DISTANCE_500M_VALUE = 500f;
	public static Float FILTER_BY_DISTANCE_1000M_VALUE = 1000f;
	public static Float FILTER_BY_DISTANCE_2500M_VALUE = 2500f;
	public static Float FILTER_BY_DISTANCE_5000M_VALUE = 5000f;
	public static Float FILTER_BY_DISTANCE_10000M_VALUE = 10000f;
	public static int FILTER_BY_DISTANCE_0M_LABEL_RESOURCE = R.string.filter_by_distance_0m_label;
	public static int FILTER_BY_DISTANCE_250M_LABEL_RESOURCE = R.string.filter_by_distance_250m_label;
	public static int FILTER_BY_DISTANCE_500M_LABEL_RESOURCE = R.string.filter_by_distance_500m_label;
	public static int FILTER_BY_DISTANCE_1000M_LABEL_RESOURCE = R.string.filter_by_distance_1000m_label;
	public static int FILTER_BY_DISTANCE_2500M_LABEL_RESOURCE = R.string.filter_by_distance_2500m_label;
	public static int FILTER_BY_DISTANCE_5000M_LABEL_RESOURCE = R.string.filter_by_distance_5000m_label;
	public static int FILTER_BY_DISTANCE_10000M_LABEL_RESOURCE = R.string.filter_by_distance_10000m_label;
	
	private Float distanceFilter = 0f;
	private Float previousDistanceFilter = 0f;
	
	public DistanceFilter(Context context) {
		this.buildOptions(context);
	}
	
	private void buildOptions (Context context)
	{
		if (DistanceFilter.sFilterLabels.size() == 0)
		{
			//Options needs to be initialized
			// Illimité
			DistanceFilter.sFilterLabels.add(
				context.getString(
					DistanceFilter.FILTER_BY_DISTANCE_0M_LABEL_RESOURCE
				)	
			);
			DistanceFilter.sFilterValues.add(
				DistanceFilter.FILTER_BY_DISTANCE_0M_VALUE
			);
			
			// 250m
			DistanceFilter.sFilterLabels.add(
				context.getString(
					DistanceFilter.FILTER_BY_DISTANCE_250M_LABEL_RESOURCE
				)
			);
			DistanceFilter.sFilterValues
					.add(DistanceFilter.FILTER_BY_DISTANCE_250M_VALUE);

			// 500m
			DistanceFilter.sFilterLabels.add(
				context.getString(
					DistanceFilter.FILTER_BY_DISTANCE_500M_LABEL_RESOURCE
				)
			);
			DistanceFilter.sFilterValues
					.add(DistanceFilter.FILTER_BY_DISTANCE_500M_VALUE);

			// 1000m
			DistanceFilter.sFilterLabels.add(
				context.getString(
					DistanceFilter.FILTER_BY_DISTANCE_1000M_LABEL_RESOURCE
				)
			);
			DistanceFilter.sFilterValues
					.add(DistanceFilter.FILTER_BY_DISTANCE_1000M_VALUE);

			// 2500m
			DistanceFilter.sFilterLabels.add(
				context.getString(
					DistanceFilter.FILTER_BY_DISTANCE_2500M_LABEL_RESOURCE
				)
			);
			DistanceFilter.sFilterValues
					.add(DistanceFilter.FILTER_BY_DISTANCE_2500M_VALUE);

			// 5000m
			DistanceFilter.sFilterLabels.add(
				context.getString(
					DistanceFilter.FILTER_BY_DISTANCE_5000M_LABEL_RESOURCE
				)
			);
			DistanceFilter.sFilterValues
					.add(DistanceFilter.FILTER_BY_DISTANCE_5000M_VALUE);

			// 10000m
			DistanceFilter.sFilterLabels.add(
				context.getString(
					DistanceFilter.FILTER_BY_DISTANCE_10000M_LABEL_RESOURCE
				)
			);
			DistanceFilter.sFilterValues
					.add(DistanceFilter.FILTER_BY_DISTANCE_10000M_VALUE);
		}
	}
	
	/// Get filter unique identifier
	@Override
	final public String getFilterId() {
		return FILTER_ID;
	}
	
	/// Returns list of available options
	public static List<String> getOptionsLabels()
	{
		return sFilterLabels;
	}
	
	public static Float getDistanceByPosition(int position)
	{
		return DistanceFilter.sFilterValues.get(position);
	}
	
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
	public void restorePreviousFilterValue() {
		this.distanceFilter = this.previousDistanceFilter;
	}

	
}
