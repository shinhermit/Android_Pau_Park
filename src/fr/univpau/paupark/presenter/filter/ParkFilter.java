package fr.univpau.paupark.presenter.filter;

import fr.univpau.paupark.model.AbstractParking;

/**
 * Interface of the filter, which allow to define a selection criteria for parking items.
 * 
 * @author Josuah Aron
 *
 */
public interface ParkFilter
{
	/**
	 * Checks whether the given parking matches the criteria or not.
	 * 
	 * @param parking the parking which is to be test for match on the criteria.
	 * 
	 * @return true if the parking matches the criteria, false otherwise.
	 */
	public boolean match(AbstractParking parking);
}
