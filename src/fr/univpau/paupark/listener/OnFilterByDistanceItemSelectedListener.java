package fr.univpau.paupark.listener;

import fr.univpau.paupark.view.presenter.filter.DistanceFilter;
import fr.univpau.paupark.view.tab.fragment.AbstractParkingTabFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * Listener for the spinner allowing to set distance filter value.
 * 
 * @author Émilien Arino
 * @author Josuah Aron
 *
 */
public class OnFilterByDistanceItemSelectedListener implements OnItemSelectedListener
{
	private int lastPosition = 0;
	
	/** The fragment of the list of parking. */
	private AbstractParkingTabFragment tab;
	
	/**
	 * Constructor.
	 * 
	 * @param tab the fragment where the list of parking is.
	 * @param lastSelectedItemPosition forces the value of the position of the last selected item (for start-up updates).
	 */
	public OnFilterByDistanceItemSelectedListener(
			AbstractParkingTabFragment tab,
			int lastSelectedItemPosition)
	{
		this.tab = tab;
		this.lastPosition = lastSelectedItemPosition;
	}
	
	/**
	 * Constructor.
	 * 
	 * @param tab the fragment where the list of parking is.
	 */
	public OnFilterByDistanceItemSelectedListener(
			AbstractParkingTabFragment tab)
	{
		this.tab = tab;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		
		if (this.lastPosition != position)
		{
			float newValue = DistanceFilter.getDistanceByPosition(position);

			boolean filterUpdated = this.tab.setFilterByDistanceValue(newValue);
			
			if (filterUpdated)
			{
				this.lastPosition = position;
			}
			else 
			{
				// cancel change
				parent.setSelection(this.lastPosition);
			}
		}
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		//Do nothing
	}
}
