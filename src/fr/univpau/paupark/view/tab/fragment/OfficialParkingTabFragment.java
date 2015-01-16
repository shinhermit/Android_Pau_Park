package fr.univpau.paupark.view.tab.fragment;

import fr.univpau.paupark.R;
import fr.univpau.paupark.listener.OnOfficialParkingListItemClickListener;
import fr.univpau.paupark.listener.OnPagerButtonClickListener;
import fr.univpau.paupark.listener.OnPagerSeekBarChangeListener;
import fr.univpau.paupark.listener.OnViewSwitcherGenericMotionListener;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class OfficialParkingTabFragment extends Fragment
{
	private boolean isPagingOn;
	private int nbItemsPerPage;
	private int currentPage = 0;
	
	/** The presenter of the list of parking. */
	private ParkingListAdapter listViewAdapter;
	
	private LinearLayout pagerHeader;
	private LinearLayout pagerFooter;
	private ViewSwitcher viewSwitcher;
	
	private Animation slideInLeft;
	private Animation slideInRight;
	private Animation slideOutLeft;
	private Animation slideOutRight;
	
	private TextView currentPageTextView;
	private TextView seekBarIndicatorTextView;
	
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
		
		this.nbItemsPerPage = preferences.getInt(
				PauParkPreferences.LAST_NB_PARKING_ITEMS_PER_PAGE,
				PauParkPreferences.DEFAULT_NB_ITEMS_PER_PAGE);
		
		this.currentPage = preferences.getInt(
				PauParkPreferences.LAST_OFFICIAL_PARKING_ITEMS_CURRENT_PAGE,
				0);
		
		if(!this.isPagingOn)
		{
			this.pagerHeader.setVisibility(View.GONE);
			this.pagerFooter.setVisibility(View.GONE);
		}
    	
    	return view;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
    	super.onActivityCreated(savedInstanceState);
    	
    	PauParkActivity activity = (PauParkActivity)
    			this.getActivity();

    	// Configure view switcher
    	ViewSwitcher viewSwitcher = (ViewSwitcher)
    			activity.findViewById(R.id.viewswitcher);
    	
    	this.slideInLeft = AnimationUtils.loadAnimation(activity,
    		    android.R.anim.slide_in_left);
    	this.slideOutRight = AnimationUtils.loadAnimation(activity,
    		    android.R.anim.slide_out_right);
    	
    	this.slideInRight = AnimationUtils.loadAnimation(activity,
    		    R.anim.slide_in_right);
    	this.slideOutLeft = AnimationUtils.loadAnimation(activity,
    		    R.anim.slide_out_left);
    	
    	// Set presenter
    	ListView parkingListView = (ListView)
    			activity.findViewById(R.id.parkingsListHolder);
    	ListView parkingListViewBis = (ListView)
    			activity.findViewById(R.id.parkingsListHolderBis);
    	
    	ParkingListAdapter adapter =
    			activity.getOfficialParkingListAdapter();
    	
    	parkingListView.setAdapter(adapter);
    	
    	parkingListView.setOnItemClickListener(
    			new OnOfficialParkingListItemClickListener(adapter));
    	
    	parkingListViewBis.setAdapter(adapter);
    	parkingListViewBis.setOnItemClickListener(
    			new OnOfficialParkingListItemClickListener(adapter));

    	// Pagination buttons / seek bar
    	ImageButton firstPageButton =
    			(ImageButton) activity.findViewById(R.id.pager_button_first);
    	ImageButton prevPageButton =
    			(ImageButton) activity.findViewById(R.id.pager_button_previous);
    	ImageButton nextPageButton =
    			(ImageButton) activity.findViewById(R.id.pager_button_next);
    	ImageButton lastPageButton =
    			(ImageButton) activity.findViewById(R.id.pager_button_last);
    	
    	ImageButton directAccessButton =
    			(ImageButton) activity.findViewById(R.id.pager_direct_access_button);
    	
    	SeekBar seekBar =
    			(SeekBar) activity.findViewById(R.id.pager_per_page_seek);
    	
    	// Pagination listeners
    	firstPageButton.setOnClickListener(new OnPagerButtonClickListener(this));
    	prevPageButton.setOnClickListener(new OnPagerButtonClickListener(this));
    	nextPageButton.setOnClickListener(new OnPagerButtonClickListener(this));
    	lastPageButton.setOnClickListener(new OnPagerButtonClickListener(this));
    	
    	directAccessButton.setOnClickListener(new OnPagerButtonClickListener(this));
    	
    	seekBar.setOnSeekBarChangeListener(new OnPagerSeekBarChangeListener(this));
    	
    	viewSwitcher.setOnTouchListener(
    			new OnViewSwitcherGenericMotionListener(this));
    	
//    	parkingListView.setOnTouchListener(
//    			new OnViewSwitcherGenericMotionListener(this));
//		
//    	parkingListViewBis.setOnTouchListener(
//    			new OnViewSwitcherGenericMotionListener(this));
		
		// Configure pagination
    	adapter.setPaging(this.currentPage, this.nbItemsPerPage);
    	adapter.setPagingEnabled(this.isPagingOn);
    	
		// Query load parking service
		ParkingServices services =
				ParkingServiceImpl.getInstance();
		services.loadParkingList(
				ParkingInfoSource.OFFICIAL, adapter);

		// Update pager info
		this.updatePagerInfo(
				adapter.getCurrentPageIndex() + 1,
				adapter.getPageCount());
		
		seekBar.setMax(20);
		seekBar.setProgress(adapter.getCurrentPageIndex());
		this.updatePagerSeekBarIndicator(adapter.getNumberOfItemsPerPage());
		
		// Keep the references
		this.listViewAdapter = adapter;
    	this.viewSwitcher = viewSwitcher;
    }
    
    /**
     * Allows pager events listeners to deal with a first page request event.
     * 
     *  <p>Updates the entire fragment accordingly.</p>
     */
    public void showFirstPage()
    {
    	this.listViewAdapter.showFirstPage();
    	
    	this.viewSwitcherPrevious();
    	
    	this.listViewAdapter.notifyDataSetChanged();
    	
    	this.updatePagerInfo(
    			this.listViewAdapter.getCurrentPageIndex() + 1,
    			this.listViewAdapter.getPageCount());
    }
    
    /**
     * Allows pager events listeners to deal with a previous page request event.
     * 
     *  <p>Updates the entire fragment accordingly.</p>
     */
    public void showPreviousPage()
    {
    	this.listViewAdapter.showPreviousPage();
    	
    	this.viewSwitcherPrevious();
    	
    	this.listViewAdapter.notifyDataSetChanged();
    	
    	this.updatePagerInfo(
    			this.listViewAdapter.getCurrentPageIndex() + 1,
    			this.listViewAdapter.getPageCount());
    }
    
    /**
     * Allows pager events listeners to deal with a next page request event.
     * 
     *  <p>Updates the entire fragment accordingly.</p>
     */
    public void showNextPage()
    {
    	this.listViewAdapter.showNextPage();
    	
    	this.viewSwitcherNext();
    	
    	this.listViewAdapter.notifyDataSetChanged();
    	
    	this.updatePagerInfo(
    			this.listViewAdapter.getCurrentPageIndex() + 1,
    			this.listViewAdapter.getPageCount());
    }
    
    /**
     * Allows pager events listeners to deal with a last page request event.
     * 
     *  <p>Updates the entire fragment accordingly.</p>
     */
    public void showLastPage()
    {
    	this.listViewAdapter.showLastPage();
    	
    	this.viewSwitcherNext();
    	
    	this.listViewAdapter.notifyDataSetChanged();
    	
    	this.updatePagerInfo(
    			this.listViewAdapter.getCurrentPageIndex() + 1,
    			this.listViewAdapter.getPageCount());
    }
    
    /**
     * Allows pager events listeners to deal with a direct page access request event.
     * 
     *  <p>Updates the entire fragment accordingly.</p>
     */
    public void showPage(int page)
    {
    	this.listViewAdapter.showPage(page);
    	
    	this.viewSwitcherNext();
    	
    	this.listViewAdapter.notifyDataSetChanged();
    	
    	this.updatePagerInfo(
    			this.listViewAdapter.getCurrentPageIndex() + 1,
    			this.listViewAdapter.getPageCount());
    }
    
    /**
     * Allows pager events listeners to deal with a change of the number of items to display per page.
     * 
     *  <p>Updates the entire fragment accordingly.</p>
     */
    public void setNbItemsPerPage(int nbItemsPerPage)
    {
    	this.listViewAdapter.setNumberOfItemsPerPage(nbItemsPerPage);
    	
    	this.listViewAdapter.notifyDataSetChanged();
    	
    	this.updatePagerSeekBarIndicator(nbItemsPerPage);
    	this.updatePagerInfo(
    			this.listViewAdapter.getCurrentPageIndex() + 1,
    			this.listViewAdapter.getPageCount());
    }
    
    /**
     * Updates the information about the currently displayed page.
     * 
     * @param currentPage the currently displayed page.
     * @param nbPages the number of pages.
     */
    private void updatePagerInfo(int currentPage, int nbPages)
    {
    	this.currentPageTextView.setText(String.valueOf(currentPage)+"/"+nbPages);
    }
    
    /**
     * Updates the information about the number of items per pages.
     * 
     * @param currentPage the currently displayed page.
     * @param nbPages the number of pages.
     */
    private void updatePagerSeekBarIndicator(int nbItemsPerPage)
    {
    	this.seekBarIndicatorTextView.setText(String.valueOf(nbItemsPerPage));
    }
   
    /**
     * Pager event listener helper.
     * <p>Provide the index of the last page. Useful for the direct access page number picker</p>
     */
    public int getLastPage()
    {
    	return this.listViewAdapter.getLastPage();
    }
    
    /**
     * Pager event listener helper.
     * <p>Provide the index of the last page. Useful for the direct access page number picker</p>
     */
    public int getCurrentPage()
    {
    	return this.listViewAdapter.getCurrentPageIndex();
    }
    
    /**
     * Set the appropriate animation and switches the view switcher.
     */
    private void viewSwitcherPrevious()
    {
    	this.viewSwitcher.setInAnimation(this.slideInLeft);
    	this.viewSwitcher.setInAnimation(this.slideOutRight);
    	
    	this.viewSwitcher.showNext();
    }
    
    /**
     * Set the appropriate animation and switches the view switcher.
     */
    private void viewSwitcherNext()
    {
    	this.viewSwitcher.setInAnimation(this.slideInRight);
    	this.viewSwitcher.setInAnimation(this.slideOutLeft);
    	
    	this.viewSwitcher.showNext();
    }
}