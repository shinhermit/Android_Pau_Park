package fr.univpau.paupark.view.tab.fragment;

import fr.univpau.paupark.listener.OnLoadParkingTaskCompleteListener;
import fr.univpau.paupark.model.PauParkPreferences;
import fr.univpau.paupark.presenter.ParkingListAdapter;
import fr.univpau.paupark.service.ParkingServiceImpl;
import fr.univpau.paupark.service.ParkingServices;
import fr.univpau.paupark.service.ParkingServices.ParkingInfoSource;
import fr.univpau.paupark.view.PauParkActivity;
import fr.univpau.paupark.view.menu.contextual.AbstractParkingContextualActionModeCallback;
import fr.univpau.paupark.view.menu.contextual.UserTipContextualActionModeCallback;

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
				PauParkPreferences.LAST_USER_TIP_PARKING_ITEMS_CURRENT_PAGE);
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
		ParkingServices services =
				ParkingServiceImpl.getInstance();
		
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