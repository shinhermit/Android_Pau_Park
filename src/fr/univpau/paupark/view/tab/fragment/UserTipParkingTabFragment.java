package fr.univpau.paupark.view.tab.fragment;

import fr.univpau.paupark.listener.OnLoadParkingTaskCompleteListener;
import fr.univpau.paupark.remote.service.RemoteParkingServicesImpl;
import fr.univpau.paupark.remote.service.RemoteParkingServices;
import fr.univpau.paupark.remote.service.RemoteParkingServices.ParkingInfoSource;
import fr.univpau.paupark.util.PauParkPreferences;
import fr.univpau.paupark.view.PauParkActivity;
import fr.univpau.paupark.view.menu.contextual.AbstractParkingContextualActionModeCallback;
import fr.univpau.paupark.view.menu.contextual.UserTipContextualActionModeCallback;
import fr.univpau.paupark.view.presenter.ParkingListAdapter;

/**
 * Fragment of the tab of the official parking list.
 * 
 * @author Josuah Aron
 *
 */
public class UserTipParkingTabFragment extends AbstractParkingTabFragment
{
	/**
	 * Constructor.
	 */
	public UserTipParkingTabFragment()
	{
		super(PauParkPreferences.LAST_NB_USER_TIP_PARKING_ITEMS_PER_PAGE,
				PauParkPreferences.LAST_USER_TIP_PARKING_ITEMS_CURRENT_PAGE,
				PauParkPreferences.LAST_USER_TIP_PARKING_SELECTED_DISTANCE_FILTER);
	}
	
	@Override
	protected ParkingListAdapter getAdapter()
	{
		PauParkActivity activity =
				(PauParkActivity) this.getActivity();
		
		return activity.getUSerTipParkingListAdapter();
	}

	@Override
	protected AbstractParkingContextualActionModeCallback
			createContextualActionModeCallback(ParkingListAdapter adapter)
	{
		return new UserTipContextualActionModeCallback(adapter);
	}

	@Override
	protected void loadParkingList(ParkingListAdapter adapter)
	{
		RemoteParkingServices services =
				RemoteParkingServicesImpl.getInstance();
		
		services.setOnTaskCompleteListener(
				new OnLoadParkingTaskCompleteListener(this));
		
		services.loadParkingList(
				ParkingInfoSource.USERS, adapter);
		
		services.setOnTaskCompleteListener(null);
	}

	@Override
	protected void notityCreation(PauParkActivity activity)
	{
    	activity.setUserTipParkingTabFragment(this);
	}
}