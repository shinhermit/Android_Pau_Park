package fr.univpau.paupark.model;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import android.location.Location;
import fr.univpau.paupark.remote.service.PauParkLocation;

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
		int dToLhs = 0;
		int dToRhs = 0;
		
		if (this.location != null)
		{
			if (this.distances.containsKey(lhs))
			{
				dToLhs = this.distances.get(lhs);
			}
			else
			{
				Location lhsLoc = new Location("lhsLoc");
				lhsLoc.setLatitude(lhs.coordinates.getLatitude());
				lhsLoc.setLongitude(lhs.coordinates.getLongitude());
		
				dToLhs = (int) this.location.distanceTo(lhsLoc);
				
				this.distances.put(lhs, dToLhs);
			}
			
			if (this.distances.containsKey(rhs))
			{
				dToRhs = this.distances.get(rhs);
			}
			else
			{
				Location rhsLoc = new Location("rhsLoc");
				rhsLoc.setLatitude(rhs.coordinates.getLatitude());
				rhsLoc.setLongitude(rhs.coordinates.getLongitude());
		
				dToRhs = (int) this.location.distanceTo(rhsLoc);
				
				this.distances.put(rhs, dToRhs);
			}
		}
				
		return dToLhs - dToRhs;
	}

}