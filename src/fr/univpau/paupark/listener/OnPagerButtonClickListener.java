package fr.univpau.paupark.listener;


import fr.univpau.paupark.R;
import fr.univpau.paupark.presenter.ParkingListAdapter;
import fr.univpau.paupark.view.tab.fragment.NumberPickerDialogFragment;
import android.app.Activity;
import android.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Listener for click on arrow button of the pager.
 * 
 * @author Josuah Aron
 *
 */
public class OnPagerButtonClickListener implements OnClickListener
{
	/** The presenter of the list of parking. */
	private ParkingListAdapter listViewAdapter;
	
	/**
	 * Constructor.
	 * 
	 * @param listViewAdapter the presenter of the list of parking.
	 */
	public OnPagerButtonClickListener(
			ParkingListAdapter listViewAdapter)
	{
		this.listViewAdapter = listViewAdapter;
	}

	@Override
	public void onClick(View view)
	{
		switch(view.getId())
		{
		case R.id.pager_button_first:
			this.listViewAdapter.showFirstPage();
			break;
			
		case R.id.pager_button_previous:
			this.listViewAdapter.showPreviousPage();
			break;
			
		case R.id.pager_button_next:
			this.listViewAdapter.showNextPage();
			break;
			
		case R.id.pager_button_last:
			this.listViewAdapter.showLastPage();
			break;
			
		case R.id.pager_direct_access_button:
			this.showNumberPickerDialog();
			break;
		}
		
		this.listViewAdapter.notifyDataSetChanged();
	}
	
	/**
	 * Show a number picker dialog which allows to choose the page to which we want to jump.
	 */
	public void showNumberPickerDialog()
	{
		Activity activity = (Activity)this.listViewAdapter.getContext();
		
	    DialogFragment newFragment =
	    		new NumberPickerDialogFragment(this.listViewAdapter);
	    
	    newFragment.show(activity.getFragmentManager(), "numberPicker");
	}
}
