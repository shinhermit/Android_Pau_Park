package fr.univpau.paupark.listener;

import fr.univpau.paupark.R;
import fr.univpau.paupark.presenter.ParkingListAdapter;
import android.view.View;
import android.view.View.OnClickListener;

public class OnPagerButtonClickListener implements OnClickListener
{
	private ParkingListAdapter listViewAdapter;
	
	public OnPagerButtonClickListener(
			ParkingListAdapter listViewAdapter)
	{
		this.listViewAdapter = listViewAdapter;
	}

	@Override
	public void onClick(View view)
	{
		switch(view.getId())
		{
		case R.id.pager_button_first:
			this.listViewAdapter.showFirstPage();
			break;
			
		case R.id.pager_button_previous:
			this.listViewAdapter.showPreviousPage();
			break;
			
		case R.id.pager_button_next:
			this.listViewAdapter.showNextPage();
			break;
			
		case R.id.pager_button_last:
			this.listViewAdapter.showLastPage();
			break;
			
		default: // direct access button
		}
		
		this.listViewAdapter.notifyDataSetChanged();
	}

}
