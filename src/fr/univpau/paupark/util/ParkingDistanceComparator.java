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

	@Override
	public int compare(AbstractParking lhs, AbstractParking rhs) 
	{
		int dToLhs = this.distanceToParking(lhs);
		int dToRhs = this.distanceToParking(rhs);
				
		return dToLhs - dToRhs;
	}

	/**
	 * Returns the distance from current location to argument or 0 if
	 * current location is unknownk
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