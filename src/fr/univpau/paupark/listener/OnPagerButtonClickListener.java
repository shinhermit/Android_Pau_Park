package fr.univpau.paupark.listener;


import fr.univpau.paupark.R;
import fr.univpau.paupark.view.tab.fragment.AbstractParkingTabFragment;
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
	/** The fragment of the list of parking. */
	private AbstractParkingTabFragment tab;
	
	/**
	 * Constructor.
	 * 
	 * @param tab the fragment where the list of parking is.
	 */
	public OnPagerButtonClickListener(
			AbstractParkingTabFragment tab)
	{
		this.tab = tab;
	}

	@Override
	public void onClick(View view)
	{
		switch(view.getId())
		{
		case R.id.pager_button_first:
			this.tab.showFirstPage();
			break;
			
		case R.id.pager_button_previous:
			this.tab.showPreviousPage();
			break;
			
		case R.id.pager_button_next:
			this.tab.showNextPage();
			break;
			
		case R.id.pager_button_last:
			this.tab.showLastPage();
			break;
			
		case R.id.pager_direct_access_button:
			this.showNumberPickerDialog();
			break;
		}
	}
	
	/**
	 * Show a number picker dialog which allows to choose the page to which we want to jump.
	 */
	public void showNumberPickerDialog()
	{
		Activity activity = (Activity)this.tab.getActivity();
		
	    DialogFragment newFragment =
	    		new NumberPickerDialogFragment(this.tab);
	    
	    newFragment.show(activity.getFragmentManager(), "numberPicker");
	}
}
