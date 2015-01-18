package fr.univpau.paupark.listener;

import fr.univpau.paupark.remote.service.async.event.OnTaskCompleteListener;
import fr.univpau.paupark.view.tab.fragment.AbstractParkingTabFragment;

/**
 * Listens for the completion event of the excution of a task by the PauPark service and updates the UI.
 * 
 * @author Josuah Aron
 *
 */
public class OnLoadParkingTaskCompleteListener implements OnTaskCompleteListener
{
	/** The fragment which asked a task to the service. */
	private AbstractParkingTabFragment tab;
	
	/**
	 * Creates a listener which Listens for the completion event of the excution of a task by the PauPark service and updates the UI.
	 * 
	 * @param tab he fragment which asked a task to the service.
	 */
	public OnLoadParkingTaskCompleteListener(AbstractParkingTabFragment tab)
	{
		this.tab = tab;
	}

	@Override
	public void onTaskComplete()
	{
		this.tab.updatePager();
	}
}
