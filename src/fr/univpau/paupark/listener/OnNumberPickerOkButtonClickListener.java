package fr.univpau.paupark.listener;

import fr.univpau.paupark.view.tab.fragment.AbstractParkingTabFragment;
import fr.univpau.paupark.view.tab.fragment.OfficialParkingTabFragment;
import android.content.DialogInterface;
import android.widget.NumberPicker;

/**
 * Listener for the click event on the "OK" button of the direct navigation page selection dialog (number picker).
 * 
 * @author Josuah Aron
 *
 */
public class OnNumberPickerOkButtonClickListener implements DialogInterface.OnClickListener
{
	/** The fragment of the list of parking. */
	private AbstractParkingTabFragment tab;
	
	/** The number picker which allowed the user to select a page. */
	private NumberPicker picker;
	
	/**
	 * Constructor.
	 * 
	 * @param tab the fragment where the list of parking is.
	 * @param picker the number picker which allowed the user to select a page.
	 */
	public OnNumberPickerOkButtonClickListener(
			AbstractParkingTabFragment tab,
			NumberPicker picker)
	{
		this.tab = tab;
		this.picker = picker;
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which)
	{
		this.tab.showPage(this.picker.getValue() - 1);
	}
}
