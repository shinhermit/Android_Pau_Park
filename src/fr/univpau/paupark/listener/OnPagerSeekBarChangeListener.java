package fr.univpau.paupark.listener;

import fr.univpau.paupark.view.tab.fragment.AbstractParkingTabFragment;
import fr.univpau.paupark.view.tab.fragment.OfficialParkingTabFragment;
import android.widget.SeekBar;

/**
 * Listener for event from the pager seek bar which allows to change the number of items which are to be displayed on each page.
 * @author Josuah Aron
 *
 */
public class OnPagerSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener
{
	/** The fragment of the list of parking. */
	private AbstractParkingTabFragment tab;
	
	/**
	 * Constructor.
	 * 
	 * @param tab the fragment where the list of parking is.
	 */
	public OnPagerSeekBarChangeListener(
			AbstractParkingTabFragment tab)
	{
		this.tab = tab;
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser)
	{
		if(fromUser)
		{
			this.tab.setNbItemsPerPage(progress);
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar)
	{}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar)
	{
		this.tab.setNbItemsPerPage(seekBar.getProgress());
	}
}
