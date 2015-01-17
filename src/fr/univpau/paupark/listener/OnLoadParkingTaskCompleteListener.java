package fr.univpau.paupark.listener;

import fr.univpau.paupark.service.async.listener.OnTaskCompleteListener;
import fr.univpau.paupark.view.tab.fragment.AbstractParkingTabFragment;

public class OnLoadParkingTaskCompleteListener implements OnTaskCompleteListener
{
	private AbstractParkingTabFragment tab;
	
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
