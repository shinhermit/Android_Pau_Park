package fr.univpau.paupark.listener;

import fr.univpau.paupark.view.tab.fragment.AbstractParkingTabFragment;
import android.util.Log;
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
	private AbstractParkingTabFragment tab;
	
	/** The gesture detector. */
	private GestureDetector gestureDetector;
	
	/**
	 * Constructor.
	 * 
	 * @param tab the fragment where the list of parking is.
	 * @param picker the number picker which allowed the user to select a page.
	 */
	public OnViewSwitcherGenericMotionListener(
			AbstractParkingTabFragment tab)
	{
		this.tab = tab;
		
		this.gestureDetector =
				new GestureDetector(this.tab.getActivity(), this);
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
		boolean isFling = false;
		
		if(this.tab.isPagingOn())
		{
			float width = ( e1.getRawX() > e2.getRawX() ) ?
					e1.getRawX() - e2.getRawX()
					: e2.getRawX() - e1.getRawX();
					
			float height = ( e1.getRawY() > e2.getRawY() ) ?
					e1.getRawY() - e2.getRawY()
					: e2.getRawY() - e1.getRawY();
					
			isFling = ( width >= 2 * height );
			Log.i("onFling", "width: "+width);
			Log.i("onFling", "height: "+height);
			Log.i("onFling", "isFling: "+isFling);
			
			if(isFling)
			{
				if (e1.getRawY() < e2.getRawY())
				{
					this.tab.showNextPage();
				}
				else
				{
					this.tab.showPreviousPage();
				}
			}
		}
		
		return isFling;
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
