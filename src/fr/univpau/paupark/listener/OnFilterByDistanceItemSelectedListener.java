package fr.univpau.paupark.listener;

import java.util.ArrayList;
import java.util.List;

import fr.univpau.paupark.view.tab.fragment.AbstractParkingTabFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.SeekBar;

/**
 * Listener for event from the pager seek bar which allows to change the number of items which are to be displayed on each page.
 * @author Josuah Aron
 *
 */
public class OnFilterByDistanceItemSelectedListener implements OnItemSelectedListener
{
	/** The fragment of the list of parking. */
	private AbstractParkingTabFragment tab;
	
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
		
		//TODO : externalize !
		//For test purposes only
		//!!
		List<Float> options = new ArrayList<Float>();
		options.add(0f);
		options.add(1, 250f);
		options.add(2, 500f);
		options.add(3, 1000f);
		options.add(4, 2500f);
		options.add(5, 5000f);
		options.add(6, 10000f);
		
		this.tab.setFilterByDistanceValue(Float.valueOf(options.get(position)));
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		//Do nothing
	}


}
