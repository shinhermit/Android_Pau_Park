package fr.univpau.paupark.listener;

import fr.univpau.paupark.view.tab.fragment.OfficialParkingTabFragment;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * Parking list pager gesture listener.
 *  
 * @author Josuah Aron
 *
 */
public class OnViewSwitcherGenericMotionListener implements OnGestureListener, OnTouchListener
{
	/** The fragment of the list of parking. */
	private OfficialParkingTabFragment tab;
	
	/** The gesture detector. */
	private GestureDetector gestureDetector;
	
	/**
	 * Constructor.
	 * 
	 * @param tab the fragment where the list of parking is.
	 * @param picker the number picker which allowed the user to select a page.
	 */
	public OnViewSwitcherGenericMotionListener(
			OfficialParkingTabFragment tab)
	{
		this.tab = tab;
		
		this.gestureDetector = new GestureDetector(this.tab.getActivity(), this);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		return this.gestureDetector.onTouchEvent(event);
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2,
			float velocityX, float velocityY)
	{
		if(this.tab.isPagingOn())
		{
			if (e1.getRawY() > e2.getRawY())
			{
				this.tab.showNextPage();
			}
			else
			{
				this.tab.showPreviousPage();
			}
		}
		
		return true;
	}
	
	@Override
	public boolean onDown(MotionEvent event)
	{
		return false;
	}

	@Override
	public void onShowPress(MotionEvent event)
	{
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e)
	{
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2,
			float distanceX, float distanceY)
	{
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e)
	{}
}
