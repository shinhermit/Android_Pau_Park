package fr.univpau.paupark.filter;

import fr.univpau.paupark.model.AbstractParking;

abstract public class AbstractParkingFilter {
	/// Get filter unique identifier
	abstract public String getFilterId();
	
	/// Returns false if the parking meets the filter criteria
	abstract public boolean filterOut(AbstractParking parking);
	
	/// Sets the new filter value
	abstract public void setValue(Object filterValue);
	
	/// Returns true if argument holds a new filter value
	abstract public boolean isNewValue(Object filterValue);

	/// Restore previous filter value
	/// Single level of history
	abstract public void restorePreviousFilterValue();
}
