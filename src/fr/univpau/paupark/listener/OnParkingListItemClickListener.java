package fr.univpau.paupark.listener;

import fr.univpau.paupark.view.menu.contextual.AbstractParkingContextualActionModeCallback;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * List view item click listener.
 * <p>This listener is to be added to a list view (of official parking).</p>
 * 
 * @author Josuah Aron
 *
 */
public class OnParkingListItemClickListener implements OnItemClickListener
{
	/** The contextual menu call back to use when an item is clicked. */
	private AbstractParkingContextualActionModeCallback actionModeCallBack;
	
	/**
	 * Constructor.
	 * 
	 * @param officialParkingListAdapter the adapter which manages the items of the list view.
	 */
	public OnParkingListItemClickListener(
			AbstractParkingContextualActionModeCallback actionModeCallBack)
	{
		this.actionModeCallBack = actionModeCallBack;
	}

	@Override
	public void onItemClick(
			AdapterView<?> parent,
			View view,
			int position,
			long id)
	{
		Context context = view.getContext();
		
		this.actionModeCallBack.setSelectedItem(position);
		
		((Activity)context)
				.startActionMode(this.actionModeCallBack);
		
        view.setSelected(true);
	}

}
