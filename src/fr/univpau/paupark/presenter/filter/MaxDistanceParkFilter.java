package fr.univpau.paupark.presenter.filter;

import android.location.Location;
import fr.univpau.paupark.model.AbstractParking;

/**
 * A filter which matches when the distance from the tested parking to a specified reference
 * point is less or equal to a specified maximum distance.
 * 
 * @author Josuah Aron
 *
 */
public class MaxDistanceParkFilter implements ParkFilter
{
	/** The reference point to which the distance is tested. */
	private Location refPoint = null;
	
	/** The max distance to the reference point. */
	private float maxDistance = 0f;
	
	/**
	 * Creates a filter which matches when the distance from the tested parking to a specified reference
	 * point is less or equal to a specified maximum distance.
	 * 
	 * @param refPoint the reference point to which the distance is tested.
	 * @param maxDistance the max distance to the reference point.
	 */
	public MaxDistanceParkFilter(Location refPoint, float maxDistance)
	{
		this.set(refPoint, maxDistance);
	}
	
	/**
	 * Creates a filter which matches when the distance from the tested parking to a specified reference
	 * point is less or equal to a specified maximum distance.
	 */
	public MaxDistanceParkFilter()
	{}
	
	@Override
	public boolean match(AbstractParking parking)
	{
		boolean withinRange = false;

		if (this.refPoint != null && this.maxDistance != 0f)
		{
			// Comparison parameters available
			Location parkingLoc = new Location("parking");
			
			parkingLoc.setLatitude(parking.getCoordinates().getLatitude());
			parkingLoc.setLongitude(parking.getCoordinates().getLongitude());
			
			if (this.refPoint.distanceTo(parkingLoc) < this.maxDistance)
			{
				withinRange = true;
			}
		}
		else
		{
			// Current location unknown 
			// or no distance selected => filter disabled.
			withinRange = true;
		}
		
		return withinRange;
	}

	/**
	 * Defines the reference point to which the distance is tested and the max distance to the reference point.
	 * 
	 * @param refPoint the reference point to which the distance is tested
	 * @param maxDistance the max distance to the reference point.
	 */
	public void set(Location refPoint, float maxDistance)
	{
		this.refPoint = refPoint;
		
		this.maxDistance =
				Math.abs(maxDistance);
	}

	/**
	 * 
	 * @return the reference point to which the distance is tested
	 */
	public Location getRefPoint()
	{
		return refPoint;
	}

	/**
	 * 
	 * @param refPoint the reference point to which the distance is tested
	 */
	public void setRefPoint(Location refPoint)
	{
		this.refPoint = refPoint;
	}

	/**
	 * 
	 * @return the max distance to the reference point.
	 */
	public float getMaxDistance()
	{
		return maxDistance;
	}

	/**
	 * 
	 * @param maxDistance the max distance to the reference point.
	 */
	public void setMaxDistance(float maxDistance)
	{
		this.maxDistance =
				Math.abs(maxDistance);
	}
}
