package fr.univpau.paupark.view.tab.fragment;

import fr.univpau.paupark.R;
import fr.univpau.paupark.filter.DistanceFilter;
import fr.univpau.paupark.filter.NameFilter;
import fr.univpau.paupark.listener.OnFilterByDistanceItemSelectedListener;
import fr.univpau.paupark.listener.OnParkingListItemClickListener;
import fr.univpau.paupark.listener.OnPagerButtonClickListener;
import fr.univpau.paupark.listener.OnPagerSeekBarChangeListener;
import fr.univpau.paupark.listener.OnViewSwitcherGenericMotionListener;
import fr.univpau.paupark.model.PauParkPreferences;
import fr.univpau.paupark.presenter.ParkingListAdapter;
import fr.univpau.paupark.view.PauParkActivity;
import fr.univpau.paupark.view.menu.contextual.AbstractParkingContextualActionModeCallback;
import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewSwitcher;

/**
 * Base of the fragments of the tab of the main activity.
 * 
 * @author Josuah Aron
 * @author Émilien Arino
 *
 */
public abstract class AbstractParkingTabFragment extends Fragment
{
	/** The presenter of the list of parking. */
	private ParkingListAdapter listViewAdapter;
	
	/** Holds the widget which represents the pager header (allows disabling). */
	private LinearLayout pagerHeader;
	
	/** Holds the widget which represents the pager footer (allows disabling). */
	private LinearLayout pagerFooter;
	
	/** Provides animation when changing pages. */
	private ViewSwitcher viewSwitcher;
	
	/** page sliding animation. */
	private Animation slideInLeft;
	
	/** page sliding animation. */
	private Animation slideInRight;
	
	/** page sliding animation. */
	private Animation slideOutLeft;
	
	/** page sliding animation. */
	private Animation slideOutRight;
	
	/** Holds the text view which informs the user of the currently displayed page. */
	private TextView currentPageTextView;
	
	/** Holds the seek bar which allows the user to change the number of pages on each page. */
	private SeekBar seekBar;
	
	/** Holds the text view which informs the user of the number of pages on each page. */
	private TextView seekBarIndicatorTextView;
	
	/** Holds the spinner which allows to select a disntace filter. */
	private Spinner filterByDistanceSpinner;
	
	/** Key of the value which saves the last number of items of the parking list per page chosen by the user. */
	private final String pageItemCountPrefKey;
	
	/** Key of the value which saves the last current page of the parking list. */
	private final String currentPagePrefKey;
	
	/** Key of the value which saves the last distance selected distance filter. */
	private final String distanceFilterPrefKey;
	
	/** Tell whether the view creation method is called for the first time or not. */
	private boolean isFirstViewCreation = true;
	
	public AbstractParkingTabFragment(
			String pageItemCountPrefKey,
			String currentPagePrefKey,
			String distanceFilterPrefKey)
	{
		this.pageItemCountPrefKey = pageItemCountPrefKey;
		this.currentPagePrefKey = currentPagePrefKey;
		this.distanceFilterPrefKey = distanceFilterPrefKey;
	}
	
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
    	this.seekBar =
    			(SeekBar) view.findViewById(R.id.pager_per_page_seek);
    	this.currentPageTextView =
    			(TextView) view.findViewById(R.id.pager_current_page_view);
    	this.seekBarIndicatorTextView =
    			(TextView) view.findViewById(R.id.pager_seek_bar_indicator);
    	
    	this.filterByDistanceSpinner =
    			(Spinner) view.findViewById(R.id.filter_by_distance_spinner);
    	
    	// Configure seek bar
    	this.seekBar.setMax(PauParkPreferences.SEEK_BAR_MAX);
    	
    	return view;
    }
    
    /**
     * Allows to define the adapter according to the list of parking (official vs user tip).
     * 
     * @return the appropriate adapter.
     */
    protected abstract ParkingListAdapter getAdapter();
    
    /**
     * Allows to create the contextual menu according to the actual fragment used (for official parking or user tip).
     * 
     * @return the appropriate adapter.
     */
    protected abstract AbstractParkingContextualActionModeCallback
    			createContextualActionModeCallback(ParkingListAdapter adapter);
    
    /**
     * Loads the appropriate parking list (official vs user tip).
     * 
     * @return the adapter which will be filled with the loaded items.
     */
    protected abstract void loadParkingList(ParkingListAdapter updateMe);
    
    /**
     * Notifies the activity of the creation of this tab.
     */
    protected abstract void notityCreation(PauParkActivity activity);

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
    	super.onActivityCreated(savedInstanceState);
    	
    	// Find widgets
    	View view = this.getView();
    	ViewSwitcher viewSwitcher = (ViewSwitcher)
    			view.findViewById(R.id.viewswitcher);
    	ListView parkingListView = (ListView)
    			view.findViewById(R.id.parkingsListHolder);
    	ListView parkingListViewBis = (ListView)
    			view.findViewById(R.id.parkingsListHolderBis);
    	
    	ImageButton firstPageButton =
    			(ImageButton) view.findViewById(R.id.pager_button_first);
    	ImageButton prevPageButton =
    			(ImageButton) view.findViewById(R.id.pager_button_previous);
    	ImageButton nextPageButton =
    			(ImageButton) view.findViewById(R.id.pager_button_next);
    	ImageButton lastPageButton =
    			(ImageButton) view.findViewById(R.id.pager_button_last);
    	
    	ImageButton directAccessButton =
    			(ImageButton) view.findViewById(R.id.pager_direct_access_button);
    	
    	// Get the context
    	PauParkActivity activity = (PauParkActivity)
    			this.getActivity();
    	
    	// Feed back creation
    	if(this.isFirstViewCreation)
    	{
    		this.notityCreation(activity);
    		this.isFirstViewCreation = false;
    	}
    	
    	// Fetch preferences
    	SharedPreferences preferences =
    			activity.getSharedPreferences(
						PauParkPreferences.class.getName(),
						Activity.MODE_PRIVATE);
		
		boolean isPagingOn = preferences.getBoolean(
				PauParkPreferences.PAGINATION_PREF_KEY,
				PauParkPreferences.DEFAULT_IS_PAGINATION_ON);
		
		int nbItemsPerPage = preferences.getInt(
				this.pageItemCountPrefKey,
				PauParkPreferences.DEFAULT_NB_ITEMS_PER_PAGE);
		
		int currentPage = preferences.getInt(
				this.currentPagePrefKey,
				0);
		
		int selectedDistanceFilterItem =
				preferences.getInt(
				this.distanceFilterPrefKey,
				0);
		
		if(!isPagingOn)
		{
			this.pagerHeader.setVisibility(View.GONE);
			this.pagerFooter.setVisibility(View.GONE);
		}
    	
    	// Configure view switcher
    	this.slideInLeft = AnimationUtils.loadAnimation(activity,
    		    android.R.anim.slide_in_left);
    	this.slideOutRight = AnimationUtils.loadAnimation(activity,
    		    android.R.anim.slide_out_right);
    	
    	this.slideInRight = AnimationUtils.loadAnimation(activity,
    		    R.anim.slide_in_right);
    	this.slideOutLeft = AnimationUtils.loadAnimation(activity,
    		    R.anim.slide_out_left);
    	
    	// Set presenter
    	ParkingListAdapter adapter = this.getAdapter();
    	
    	parkingListView.setAdapter(adapter);
    	parkingListView.setOnItemClickListener(
    			new OnParkingListItemClickListener(
    					this.createContextualActionModeCallback(adapter)));
    	
    	parkingListViewBis.setAdapter(adapter);
    	parkingListViewBis.setOnItemClickListener(
    			new OnParkingListItemClickListener(
    					this.createContextualActionModeCallback(adapter)));

    	// Populate distance filter
    	ArrayAdapter<String> spinnerArrayAdapter = 
    			new ArrayAdapter<String>(
    					this.getActivity(), 
    					android.R.layout.simple_spinner_item, 
    					DistanceFilter.getOptionsLabels());
    	spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	this.filterByDistanceSpinner.setAdapter(spinnerArrayAdapter);
    	this.filterByDistanceSpinner.setSelection(
    			selectedDistanceFilterItem, true);
    	
    	// Pagination listeners
    	firstPageButton.setOnClickListener(
    			new OnPagerButtonClickListener(this));
    	prevPageButton.setOnClickListener(
    			new OnPagerButtonClickListener(this));
    	nextPageButton.setOnClickListener(
    			new OnPagerButtonClickListener(this));
    	lastPageButton.setOnClickListener(
    			new OnPagerButtonClickListener(this));
    	
    	directAccessButton.setOnClickListener(
    			new OnPagerButtonClickListener(this));
    	
    	this.seekBar.setOnSeekBarChangeListener(
    			new OnPagerSeekBarChangeListener(this));
    	
    	this.filterByDistanceSpinner.setOnItemSelectedListener(
    			new OnFilterByDistanceItemSelectedListener(this));
    	
    	parkingListView.setOnTouchListener(
    			new OnViewSwitcherGenericMotionListener(this));
		
    	parkingListViewBis.setOnTouchListener(
    			new OnViewSwitcherGenericMotionListener(this));
		
		// Configure pagination
    	adapter.setPaging(currentPage, nbItemsPerPage);
    	adapter.setPagingEnabled(isPagingOn);
    	
		// Query load parking service
    	this.loadParkingList(adapter);
		
		// Keep the references
		this.listViewAdapter = adapter;
    	this.viewSwitcher = viewSwitcher;
    }
    
    @Override
    public void onDestroyView()
    {
		super.onDestroyView();
		
		SharedPreferences preferences =
				this.getActivity().getSharedPreferences(
						PauParkPreferences.class.getName(),
						Activity.MODE_PRIVATE);
		
		Editor preferenceEditor = preferences.edit();
		
		preferenceEditor.putInt(
				this.pageItemCountPrefKey,
				this.listViewAdapter.getNumberOfItemsPerPage());
		
		preferenceEditor.putInt(
				this.currentPagePrefKey,
				this.listViewAdapter.getCurrentPageIndex());
		
		if(this.filterByDistanceSpinner.getSelectedItemPosition() != AdapterView.INVALID_POSITION)
		{
			preferenceEditor.putInt(
					this.distanceFilterPrefKey,
					this.filterByDistanceSpinner.getSelectedItemPosition());
		}
		
		preferenceEditor.commit();
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
     * Updates the pager info according to the current state of the adapter.
     */
    public void updatePager()
    {
    	this.seekBar.setProgress(
    			this.listViewAdapter.getNumberOfItemsPerPage());
    	
    	this.updatePagerSeekBarIndicator(
    			this.listViewAdapter.getNumberOfItemsPerPage());
    	
    	this.updatePagerInfo(
    			this.listViewAdapter.getCurrentPageIndex() + 1,
    			this.listViewAdapter.getPageCount());
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
     * 
     * @return true if paging is activated, false otherwise.
     */
    public boolean isPagingOn()
    {
    	return this.listViewAdapter.isPaginEnabled();
    }
    
    /**
     * Sets the appropriate animation and switches the view switcher.
     */
    private void viewSwitcherPrevious()
    {
    	this.viewSwitcher.setInAnimation(this.slideInLeft);
    	this.viewSwitcher.setInAnimation(this.slideOutRight);
    	
    	this.viewSwitcher.showNext();
    }
    
    /**
     * Sets the appropriate animation and switches the view switcher.
     */
    private void viewSwitcherNext()
    {
    	this.viewSwitcher.setInAnimation(this.slideInRight);
    	this.viewSwitcher.setInAnimation(this.slideOutLeft);
    	
    	this.viewSwitcher.showNext();
    }

    /**
     * Sets the value of the distance filter.
     * 
     * Updates view.
     * 
     * Returns true if new filter value was set.
     */
    public boolean setFilterByDistanceValue(float distance)
    {
    	boolean updated = this.listViewAdapter.setFilterValue(
    			DistanceFilter.FILTER_ID, distance);
    	
    	if(updated)
    	{
	    	this.updatePagerInfo(
	    			this.listViewAdapter.getCurrentPageIndex() + 1,
	    			this.listViewAdapter.getPageCount());
    	}
    	
    	return updated;
    }
    
    /**
     * Sets the value of the name filter.
     * 
     * Updates view.
     * 
     * Returns true if new filter value was set.
     */
    public boolean setFilterByNameValue(String name)
    {
    	boolean updated = this.listViewAdapter.setFilterValue(
    			NameFilter.FILTER_ID, name);
    	
    	if(updated)
    	{
	    	this.updatePagerInfo(
	    			this.listViewAdapter.getCurrentPageIndex() + 1,
	    			this.listViewAdapter.getPageCount());
    	}
    	
    	return updated;
    }
}
