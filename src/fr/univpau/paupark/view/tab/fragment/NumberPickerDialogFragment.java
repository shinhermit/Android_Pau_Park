package fr.univpau.paupark.view.tab.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import fr.univpau.paupark.R;
import fr.univpau.paupark.listener.OnNumberPickerOkButtonClickListener;

/**
 * A dialog which allows the user to select a page to which he wants to jump when browsing a list of parking with paging activated.
 * 
 * @author Josuah Aron
 *
 */
public class NumberPickerDialogFragment extends DialogFragment
{
	/** The fragment of the list of parking. */
	private AbstractParkingTabFragment tab;
	
	private ViewGroup parent;
	
	/**
	 * Constructor.
	 * 
	 * @param tab the fragment where the list of parking is.
	 * @param parent Optional view to be the parent of the generated hierarchy.
	 */
	public NumberPickerDialogFragment(
			AbstractParkingTabFragment tab, ViewGroup parent)
	{
		this.tab = tab;
		this.parent = parent;
	}
	
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.number_picker, this.parent);
        this.parent = null; // release reference
        
        NumberPicker picker = (NumberPicker)
        		view.findViewById(R.id.number_picker);
        
        picker.setMinValue(1);
        picker.setMaxValue(this.tab.getLastPage() + 1);
        picker.setValue(this.tab.getCurrentPage()+1);
        
        builder.setView(view)
               .setPositiveButton("Ok",
            		   new OnNumberPickerOkButtonClickListener(
            				   this.tab, picker))
               .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
               {
                   public void onClick(DialogInterface dialog, int id)
                   {
                	   NumberPickerDialogFragment.this.getDialog().cancel();
                   }
               });
        
        // Create the AlertDialog object and return it
        return builder.create();
    }
}