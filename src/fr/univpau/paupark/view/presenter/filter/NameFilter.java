package fr.univpau.paupark.view.presenter.filter;

import java.util.Locale;

import fr.univpau.paupark.data.AbstractParking;


/**
 * 
 * Filter by parking name.
 * 
 * @author Émilien Arino
 */
public class NameFilter extends AbstractParkingFilter 
{
	public static String FILTER_ID = "com.univpau.paupark.nameFilter";
	
	private String nameFilter = "";
	private String previousNameFilter = "";
	
	/// Get filter unique identifier
	@Override
	public String getFilterId() {
		return FILTER_ID;
	}
	
	@Override
	public boolean filterOut(AbstractParking parking) 
	{
		boolean filterOut = true;
		
		if (!this.nameFilter.equals(""))
		{
			filterOut = ! (
					this.cleanString(parking.getName())
					.toLowerCase(Locale.FRANCE)
					.contains(this.nameFilter.toLowerCase(Locale.FRANCE))
					||
					this.cleanString(parking.getTown())
					.toLowerCase(Locale.FRANCE)
					.contains(this.nameFilter.toLowerCase(Locale.FRANCE))
				);
		}
		else
		{
			// Nothing to compare against => filter disabled.
			filterOut = false;
		}
		
		return filterOut;
	}

	@Override
	public void setValue(Object filterValue) 
	{
		this.previousNameFilter = this.nameFilter;
		
		this.nameFilter = this.cleanString((String)filterValue);
	}

	@Override
	public boolean isNewValue(Object value)
	{
		return this.nameFilter.equals(this.cleanString((String)value)) == false;		
	}

	@Override
	public void restorePreviousFilterValue() 
	{
		this.nameFilter = this.previousNameFilter;
		
	}
	
	/// Returns argument converted to lower case according to default locale 
	/// and trimmed
	private String cleanString(String in)
	{
		return in.trim().toLowerCase(Locale.getDefault());
	}
}
