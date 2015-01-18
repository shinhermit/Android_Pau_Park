package fr.univpau.paupark.view.presenter.filter;

import fr.univpau.paupark.model.AbstractParking;

/**
 * Allows to filter a list of parking.
 * 
 * <p>An object passes the filter if the the method filter out return false.</p>
 * 
 * @author Émilien Arino
 *
 */
abstract public class AbstractParkingFilter
{
	/**
	 * 
	 * @return the filter unique identifier
	 */
	abstract public String getFilterId();
	
	/**
	 * Criteria enforcement.
	 * 
	 * @param parking the parking which is tested on the filter.
	 * @return false if the parking meets the filter criteria.
	 */
	abstract public boolean filterOut(AbstractParking parking);
	
	/**
	 * Defines a value on which parking will be tested.
	 * 
	 * @param filterValue the value on which parking will be tested.
	 */
	abstract public void setValue(Object filterValue);
	
	/**
	 * Tells whether the given value is different from the current criteria value.
	 * @param filterValue an eligible value for filter criteria. 
	 * @return true if argument holds a new filter value
	 */
	abstract public boolean isNewValue(Object filterValue);

	/**
	 * Restores the  previous filter value.
	 * <p>Only one level of history.</p>
	 */
	abstract public void restorePreviousFilterValue();
}
