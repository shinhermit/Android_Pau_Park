package fr.univpau.paupark.view.tab.fragment;

import fr.univpau.paupark.R;
import fr.univpau.paupark.listener.OnOfficialParkingListItemClickListener;
import fr.univpau.paupark.listener.OnPagerButtonClickListener;
import fr.univpau.paupark.model.PauParkPreferences;
import fr.univpau.paupark.presenter.ParkingListAdapter;
import fr.univpau.paupark.service.ParkingServiceImpl;
import fr.univpau.paupark.service.ParkingServices;
import fr.univpau.paupark.service.ParkingServices.ParkingInfoSource;
import fr.univpau.paupark.view.PauParkActivity;
import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class OfficialParkingTabFragment extends Fragment
{
	private boolean isPagingOn;
	private int nbItemsPerPage;
	private int currentPage = 0;
	private int nbPages;
	
	private LinearLayout pagerHeader;
	private LinearLayout pagerFooter;
	
	private TextView currentPageTextView;
	private TextView seekBarIndicatorTextView;
	
	private ImageButton [] navButtons;
	
    @Override
    public View onCreateView(
		LayoutInflater inflater, 
		ViewGroup container,
        Bundle savedInstanceState) 
    {
    	View view = inflater.inflate(
        		R.layout.parkings_tab, container, false);
    	
    	this.pagerHeader =
    			(LinearLayout) view.findViewById(R.id.pager_header);
    	this.pagerFooter =
    			(LinearLayout) view.findViewById(R.id.pager_footer);
    	this.currentPageTextView =
    			(TextView) view.findViewById(R.id.pager_current_page_view);
    	this.seekBarIndicatorTextView =
    			(TextView) view.findViewById(R.id.pager_seek_bar_indicator);
    	
    	// Fetch preferences
    	Activity pauParkActivity = this.getActivity();
    	
    	SharedPreferences preferences =
				pauParkActivity.getSharedPreferences(
						PauParkPreferences.class.getName(),
						Activity.MODE_PRIVATE);
		
		this.isPagingOn = preferences.getBoolean(
				PauParkPreferences.GEOLOCATION_PREF_KEY,
				PauParkPreferences.DEFAULT_IS_PAGINATION_ON);
		
		if(!this.isPagingOn)
		{
			this.pagerFooter.setVisibility(View.GONE);
			this.pagerFooter.setVisibility(View.GONE);
		}
		else
		{
			this.nbItemsPerPage = preferences.getInt(
					PauParkPreferences.LAST_NB_PARKING_ITEMS_PER_PAGE,
					PauParkPreferences.DEFAULT_NB_ITEMS_PER_PAGE);
			
			this.currentPage = preferences.getInt(
					PauParkPreferences.LAST_OFFICIAL_PARKING_ITEMS_CURRENT_PAGE,
					0);
		}
    	
    	return view;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
    	super.onActivityCreated(savedInstanceState);
    	
    	// Set presenter
    	PauParkActivity activity = (PauParkActivity)
    			this.getActivity();
    	
    	ListView parkingListView = (ListView)
    			activity.findViewById(R.id.parkingsListHolder);
    	
    	ParkingListAdapter adapter =
    			activity.getOfficialParkingListAdapter();
    	
    	parkingListView.setAdapter(adapter);
    	parkingListView.setOnItemClickListener(
    			new OnOfficialParkingListItemClickListener(adapter));

    	// Pagination buttons listeners
    	ImageButton firstPageButton =
    			(ImageButton) activity.findViewById(R.id.pager_button_first);
    	ImageButton prevPageButton =
    			(ImageButton) activity.findViewById(R.id.pager_button_previous);
    	ImageButton nextPageButton =
    			(ImageButton) activity.findViewById(R.id.pager_button_next);
    	ImageButton lastPageButton =
    			(ImageButton) activity.findViewById(R.id.pager_button_last);
    	
    	// TODO: set listeners
    	firstPageButton.setOnClickListener(new OnPagerButtonClickListener(adapter));
    	prevPageButton.setOnClickListener(new OnPagerButtonClickListener(adapter));
    	nextPageButton.setOnClickListener(new OnPagerButtonClickListener(adapter));
    	lastPageButton.setOnClickListener(new OnPagerButtonClickListener(adapter));
		
		// Configure pagination
    	adapter.setPaging(this.currentPage, this.nbItemsPerPage);
    	adapter.setPagingEnabled(this.isPagingOn);
    	
		// Query load parking service
		ParkingServices services =
				ParkingServiceImpl.getInstance();
		services.loadParkingList(
				ParkingInfoSource.OFFICIAL, adapter);
    }
}