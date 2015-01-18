package fr.univpau.paupark.view.presenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.univpau.paupark.R;
import fr.univpau.paupark.model.AbstractParking;
import fr.univpau.paupark.model.ParkingDistanceComparator;
import fr.univpau.paupark.model.PauParkPreferences;
import fr.univpau.paupark.view.presenter.filter.AbstractParkingFilter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

/**
 * An adapter which allows paging.
 * 
 * <p>Adapter for parking lists views.</p>
 * 
 * @author Josuah Aron
 * @author Émilien Arino
 *
 */
public class ParkingListAdapter extends ArrayAdapter<AbstractParking>
{	
	/** Creates the appropriate view according to the type of adapter needed (official parking list / user tip).*/
	private AbstractViewPreparer preparer;
	
	/** The index of the current page. */
	private int currentPage = 0;
	
	/** The number of item on each page. */
	private int nbItemsPerPage =
			PauParkPreferences.DEFAULT_NB_ITEMS_PER_PAGE;
	
	/** Tells whether paging is activated or not. */
	private boolean isPaginOn =
			PauParkPreferences.DEFAULT_IS_PAGINATION_ON;
	
	/** The raw list of parkings */
	private List<AbstractParking> unfilteredParkingList = 
			new ArrayList<AbstractParking>();
	
	/** Filters to tell whether an element should be shown or not. */
	private Map<String, AbstractParkingFilter> filters = 
			new HashMap<String, AbstractParkingFilter>();
	/**
	 * Constructor.
	 * 
	 * <p>This constructor creates an adapter with paging enabled.</p>
	 * 
	 * @param context the application context.
	 * @param resource the resource which will be used to inflate the view of each item. 
	 * @param objects the list of objects which must be presented.
	 * @param preparer creates the appropriate view according to the the tip of item list.
	 * @param currentPage the page to begin with.
	 * @param nbItemsPerPage the number of items on each page.
	 */
	public ParkingListAdapter(
			Context context,
			int resource,
			List<AbstractParking> objects,
			AbstractViewPreparer preparer,
			int currentPageIndex,
			int nbItemsPerPage)
	{
		super(context, resource, objects);
		
		this.preparer = preparer;
		
		this.setPaging(currentPageIndex, nbItemsPerPage);
	}
	
	/**
	 * Constructor.
	 * 
	 * <p>This constructor creates an adapter with paging disabled.</p>
	 * 
	 * @param context the application context.
	 * @param resource the resource which will be used to inflate the view of each item. 
	 * @param objects the list of objects which must be presented.
	 * @param preparer creates the appropriate view according to the the tip of item list.
	 */
	public ParkingListAdapter(
			Context context,
			int resource,
			List<AbstractParking> objects,
			AbstractViewPreparer preparer)
	{
		super(context, resource, objects);
		
		this.preparer = preparer;
		
		this.isPaginOn = false;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		//Current parking
		AbstractParking parking = this.getItem(position);
		
		//Check if an existing view has been passed
		if (convertView == null)
		{
			convertView = this.preparer.buildConvertView(getContext(), parent);
		}
		
		convertView = this.preparer.populateView(convertView, parking);
		
		return convertView;
	}
	
	@Override
	public int getCount()
	{
		return (this.isPaginOn) ?
				this.getPageItemCount(this.currentPage)
				: super.getCount();
	}
	
	@Override
	public long getItemId(int positionInCurrentPage)
	{
		return (this.isPaginOn) ?
				super.getItemId(
				this.toGlobalPosition(positionInCurrentPage))
				: super.getItemId(positionInCurrentPage);
	}
	
	@Override
	public int getItemViewType(int positionInCurrentPage)
	{
		return (this.isPaginOn) ?
				super.getItemViewType(
				this.toGlobalPosition(positionInCurrentPage))
				: super.getItemViewType(positionInCurrentPage);
	}
	
	@Override
	public int getPosition(AbstractParking item)
	{
		return (this.isPaginOn) ?
				this.toPagePosition(super.getPosition(item))
				: super.getPosition(item);
	}
	
	@Override
	public AbstractParking getItem(int positionInCurrentPage)
	{
		return (this.isPaginOn) ?
				super.getItem(this.toGlobalPosition(positionInCurrentPage))
				: super.getItem(positionInCurrentPage);
	}
	
	@Override
	public View getDropDownView(int positionInCurrentPage, View convertView, ViewGroup parent)
	{
		return (this.isPaginOn) ?
				super.getDropDownView(
						this.toGlobalPosition(positionInCurrentPage), convertView, parent)
				: super.getDropDownView(positionInCurrentPage, convertView, parent);
	}
	
	/**
	 * 
	 * @param positionInCurrentPage the position of the item in the current page.
	 * @return the position of the object when paging is not activated.
	 */
	private int toGlobalPosition(int positionInCurrentPage)
	{
		return this.currentPage * this.nbItemsPerPage + positionInCurrentPage;
	}
	
	/**
	 * 
	 * @param position the position of the item when paging is disabled..
	 * @return the position of the object in it's page.
	 */
	private int toPagePosition(int position)
	{
		return position - ( (position/this.nbItemsPerPage) * this.nbItemsPerPage );
	}
	
	/**
	 * Provides the page where the item must be displayed.
	 * 
	 * @param item the item of which the page is queried.
	 * @return the page where the item must be displayed
	 */
	public int getPageOf(AbstractParking item)
	{
		return super.getPosition(item) / this.nbItemsPerPage;
	}
	
	/**
	 * 
	 * @return true of paging is enabled on this adapter, false otherwise.
	 */
	public boolean isPaginEnabled()
	{
		return this.isPaginOn;
	}
	
	/**
	 * 
	 * @param pagingState passing true enables paging, false disables paging.
	 */
	public void setPagingEnabled(boolean pagingState)
	{
		this.isPaginOn = pagingState;
	}
	
	/**
	 * 
	 * @return the index of the active page for this adapter.
	 */
	public int getCurrentPageIndex()
	{
		return this.currentPage;
	}
	
	/**
	 * Defines the active page for this adapter.
	 * 
	 * @param pageIndex index of the new active page.
	 */
	public void setCurrentPage(int pageIndex)
	{
		this.currentPage = Math.max( 0, Math.min(pageIndex, this.getLastPage()) );
	}
	
	/**
	 * 
	 * @return the number of items of each page.
	 */
	public int getNumberOfItemsPerPage()
	{
		return this.nbItemsPerPage;
	}
	
	/**
	 *
	 * @param nbItems the number of items on each page.
	 */
	public void setNumberOfItemsPerPage(int nbItems)
	{
		this.nbItemsPerPage = Math.max(1, nbItems);
		
		// ensure current page is not out of bounds
		this.setCurrentPage(this.currentPage);
	}
	
	/**
	 * Provides the index of the last page.
	 * 
	 * @return the index of the last page.
	 */
	public int getLastPage()
	{
		return super.getCount() / this.nbItemsPerPage;
	}
	
	/**
	 * Provides the number of pages needed to show all the items of this adapter.
	 * 
	 * @return the number of pages needed to show all the items of this adapter.
	 */
	public int getPageCount()
	{
		return this.getLastPage() + 1;
	}
	
	/**
	 * Provides the number of item for this given page.
	 * 
	 * @param the page for which the number of item is being look up.
	 * @return the number of item for this given page.
	 */
	public int getPageItemCount(int page)
	{
		if(page != this.getLastPage())
		{
			return this.nbItemsPerPage;
		}
		else
		{
			if(this.getPageCount() == 1)
			{
				return super.getCount();
			}
			else
			{
				return super.getCount() - page * this.nbItemsPerPage;
			}
		}
	}
	
	/**
	 * Configures the paging.
	 * 
	 * @param currentPage the active page.
	 * @param nbItemsPerPage the number of item per page.
	 */
	public void setPaging(int currentPage, int nbItemsPerPage)
	{
		this.isPaginOn = true;
		
		this.setCurrentPage(currentPage);
		this.setNumberOfItemsPerPage(nbItemsPerPage);
	}
	
	/**
	 * Set the current page of the adapter to the first page
	 * 
	 * @return the index of the new active page.
	 */
	public int showFirstPage()
	{
		this.currentPage = 0;
		
		return this.currentPage;
	}
	
	/**
	 * Sets the active page of the adapter to the next page.
	 * 
	 * @return the index of the new active page.
	 */
	public int showNextPage()
	{
		this.currentPage = (this.currentPage + 1) % this.getPageCount();
		
		return this.currentPage;
	}
	
	/**
	 * Sets the active page of the adapter to the previous page.
	 * 
	 * @return the index of the new active page.
	 */
	public int showPreviousPage()
	{
		this.currentPage = (this.currentPage + this.getLastPage()) % this.getPageCount();
		
		return this.currentPage;
	}
	
	/**
	 * Sets the active page of the adapter to the last page.
	 * 
	 * @return the index of the new active page.
	 */
	public int showLastPage()
	{
		this.currentPage = this.getLastPage();
		
		return this.currentPage;
	}
	
	/**
	 * Sets the active page of the adapter to the given page.
	 * 
	 * @return the index of the new active page.
	 */
	public int showPage(int page)
	{
		this.setCurrentPage(page);
		
		return this.currentPage;
	}

	/**
	 * Tests all the filters contained in this presenter and provides a filtered list of parking.
	 * 
	 * @param parkingList the unfiltered list of parking.
	 * 
	 * @return a filtered list of parking.
	 */
	private List<AbstractParking> applyFilters(
			List<AbstractParking> parkingList)
	{
		boolean filterOut;
		List<AbstractParking> filtered =
				new ArrayList<AbstractParking>();
		
		for(AbstractParking parking : this.unfilteredParkingList)
		{
			filterOut = false;
			
			// Test against all filters
			for (AbstractParkingFilter filter : this.filters.values())
			{
				if (filter.filterOut(parking))
				{
					filterOut = true;
					break;
				}
			}

			// Parking meets all filters criteria
			if (!filterOut)
			{
				filtered.add(parking);
			}
		}
		
		return filtered;
	}
	
	@Override
	public void add(AbstractParking object) {
		this.insert(object, 0);
	}

	@Override
	public void addAll(Collection<? extends AbstractParking> collection) {
		for (AbstractParking parking : collection)
		{
			this.insert(parking, 0);
		}
	}

	@Override
	public void addAll(AbstractParking... items) {
		for(AbstractParking item : items)
		{
			this.insert(item, 0);
		}
	}

	@Override
	public void insert(AbstractParking object, int index) {
		// Add to unfiltered list.
		this.unfilteredParkingList.add(index, object);
		
		boolean filterOut = false;
		
		for (AbstractParkingFilter filter : this.filters.values())
		{
			if (filter.filterOut(object))
			{
				filterOut = true;
				break;
			}
		}

		if (!filterOut)
		{
			// parking meets filters criteria
			// add to adapter for display.
			super.insert(object, index);
		}
	}

	@Override
	public void remove(AbstractParking object) {
		// In parent and in local collection
		super.remove(object);
		
		this.unfilteredParkingList.remove(object);
		
		// Ensure current page is not out of bounds
		this.setCurrentPage(this.currentPage);
	}

	@Override
	public void clear() {
		// In parent and in local collection 
		super.clear();
		
		this.unfilteredParkingList.clear();
		
		// Ensure current page is not out of bounds
		this.setCurrentPage(this.currentPage);
	}
	
	/**
	 * Register a new filter in adapter.
	 * 
	 * @param filter
	 */
	public void addFilter(AbstractParkingFilter filter)
	{
		this.filters.put(filter.getFilterId(), filter);
	}
	
	/**
	 * Pass new filter value to filter of type filterId
	 *  
	 * @param filterId filter type identification
	 * @param value filter value
	 * @return Returns true if list of parkings after filtering isn't empty 
	 */
	public boolean setFilterValue(String filterId, Object value)
	{
		boolean filterUpdated = false;

		AbstractParkingFilter filter = this.filters.get(filterId);
		
		if (filter != null)
		{
			//filter found
			
			if (filter.isNewValue(value))
			{
				// filter has been modified 
				filterUpdated = true;
				
				// set new filter value
				filter.setValue(value);
				
				// Apply new filter.
				List<AbstractParking> filtered =
						this.applyFilters(this.unfilteredParkingList);
				
				if (filtered.isEmpty())
				{
					// new filter returned no result
					// reset 
					filterUpdated = false;
					filter.restorePreviousFilterValue();
					
					// notify user
					Context context = getContext();
					Toast.makeText(
						context, 
						R.string.filter_by_distance_no_values, 
						Toast.LENGTH_SHORT
					).show();
		
				}
				else
				{
					super.clear();
					
					super.addAll(filtered);
					
					// sort list of parkings by distance
					ParkingDistanceComparator distanceToParkingComparator =
							new ParkingDistanceComparator();
					this.sort(distanceToParkingComparator);
					
					// Ensure current page is not out of bounds
					this.setCurrentPage(this.currentPage);
				}
				
				// update view : unnecessary if list unmodified ??
				// sort calls notifyDataSetChanged : useless if list modified
				// notifyDataSetChanged();
			}
		}
		
		return filterUpdated;
	}
}
