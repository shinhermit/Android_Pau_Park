package fr.univpau.paupark.listener;

import fr.univpau.paupark.view.menu.contextual.UserTipContextualActionModeCallBack;
import android.app.Activity;
import android.content.Context;
import android.view.ActionMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class OnUserTipListItemClickListener implements OnItemClickListener
{
	private ActionMode.Callback actionModeCallBack =
			new UserTipContextualActionModeCallBack();
	
	@Override
	public void onItemClick(
			AdapterView<?> parent,
			View view,
			int position,
			long id)
	{
		Context context = view.getContext();
		
		((Activity)context)
				.startActionMode(this.actionModeCallBack);
		
        view.setSelected(true);
	}
}
