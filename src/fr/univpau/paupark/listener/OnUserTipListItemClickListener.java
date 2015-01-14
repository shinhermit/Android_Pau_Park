package fr.univpau.paupark.listener;

import fr.univpau.paupark.presenter.ParkingListAdapter;
import fr.univpau.paupark.view.menu.contextual.UserTipContextualActionModeCallBack;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * List view item click listener.
 * <p>This listener is to be added to a list view (of user tip parking).</p>
 * 
 * @author Josuah Aron
 *
 */
public class OnUserTipListItemClickListener implements OnItemClickListener
{
	/** The contextual menu call back to use when an item is clicked. */
	private UserTipContextualActionModeCallBack actionModeCallBack;
	
	/**
	 * Constructor.
	 * 
	 * @param userTipListAdapter the adapter which manages the items of the list view.
	 */
	public OnUserTipListItemClickListener(
			ParkingListAdapter userTipListAdapter)
	{
		this.actionModeCallBack =
				new UserTipContextualActionModeCallBack(userTipListAdapter);
	}
	
	@Override
	public void onItemClick(
			AdapterView<?> parent,
			View view,
			int position,
			long id)
	{
		Context context = view.getContext();
		
		this.actionModeCallBack.setSelectedItem(id);
		
		((Activity)context)
				.startActionMode(this.actionModeCallBack);
		
        view.setSelected(true);
	}
}
