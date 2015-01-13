package fr.univpau.paupark.view.menu.contextual;

import fr.univpau.paupark.R;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class UserTipContextualActionModeCallBack implements ActionMode.Callback
{
	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu)
	{
		MenuInflater inflater = mode.getMenuInflater();
        
        inflater.inflate(R.menu.user_tip_action_menu, menu);
        
        return true;
	}

	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu menu)
	{
		return false;
	}
	
	@Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item)
	{
        switch (item.getItemId())
        {
            case R.id.upVoteTipAction:
            	// TODO
            	
                mode.finish();
                return true;
            case R.id.downVoteTipAction:
            	// TODO
            
                mode.finish();
                return true;
            case R.id.locateParkingAction:
            	// TODO
                mode.finish();
                return true;
            default:
                return false;
        }
    }

	@Override
	public void onDestroyActionMode(ActionMode mode)
	{}
}