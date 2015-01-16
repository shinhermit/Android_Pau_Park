package fr.univpau.paupark.listener;

import fr.univpau.paupark.presenter.ParkingListAdapter;
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
	/** The presenter of the list of parking. */
	private ParkingListAdapter listViewAdapter;
	
	/** The number picker which allowed the user to select a page. */
	private NumberPicker picker;
	
	/**
	 * Constructor.
	 * 
	 * @param listViewAdapter the presenter of the list of parking.
	 * @param picker the number picker which allowed the user to select a page.
	 */
	public OnNumberPickerOkButtonClickListener(
			ParkingListAdapter listViewAdapter,
			NumberPicker picker)
	{
		this.listViewAdapter = listViewAdapter;
		this.picker = picker;
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which)
	{
		this.listViewAdapter.showPage(this.picker.getValue() - 1);
		
		this.listViewAdapter.notifyDataSetChanged();
	}
}
