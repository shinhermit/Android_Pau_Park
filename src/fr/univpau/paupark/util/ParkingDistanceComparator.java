package fr.univpau.paupark.util;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import android.location.Location;
import fr.univpau.paupark.data.AbstractParking;

/**
 * 
 * Used to compare parkings by distance from current location to them.
 *
 */
public class ParkingDistanceComparator implements Comparator<AbstractParking>{
	private Location location;
	private Map<AbstractParking, Integer> distances = 
			new HashMap<AbstractParking, Integer>();
	
	public ParkingDistanceComparator() {
		this.location = PauParkLocation.getInstance().getLocation();
	}	

	/**
	 * Returns :
	 *  - int > 0 if rhs is closer than lhs
	 *  - int == 0 if rhs and lhs are at the same distance
	 *  - int < 0 if lhs is closer than rhs
	 */
	@Override
	public int compare(AbstractParking lhs, AbstractParking rhs) 
	{
		int distanceToLhs = this.distanceToParking(lhs);
		int distanceToRhs = this.distanceToParking(rhs);
				
		return distanceToLhs - distanceToRhs;
	}

	/**
	 * Returns the distance from current location to argument or 0 if
	 * current location is unknown.
	 * 
	 * @param parking
	 * @return
	 */
	private int distanceToParking(AbstractParking parking)
	{
		int distance = 0;

		if (this.location != null)
		{
			if (this.distances.containsKey(parking))
			{
				distance = this.distances.get(parking);
			}
			else
			{
				Location lhsLoc = new Location("lhsLoc");
				lhsLoc.setLatitude(parking.getCoordinates().getLatitude());
				lhsLoc.setLongitude(parking.getCoordinates().getLongitude());
		
				distance = (int) this.location.distanceTo(lhsLoc);
				
				this.distances.put(parking, distance);
			}
		}
		
		return distance;
	}
}