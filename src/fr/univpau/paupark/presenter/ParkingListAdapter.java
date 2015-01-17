package fr.univpau.paupark.presenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fr.univpau.paupark.R;
import fr.univpau.paupark.model.AbstractParking;
import fr.univpau.paupark.model.PauParkPreferences;
import fr.univpau.paupark.presenter.filter.ParkFilter;
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
	
	/** The last filter used. Filters allows to restrict the displayed items to only those which match the filter criteria. */
	private ParkFilter parkFilter = null;
	
	/** The raw list of parkings */
	private List<AbstractParking> unfilteredParkingList =
			new ArrayList<AbstractParking>();
	
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
				this.getPageSize(this.currentPage)
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
	 * @return the number of item for this given page.
	 */
	public int getPageSize(int page)
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
	 * Called when distanceFilter is modified.
	 *  
	 * @return the number of elements after applying the filter.
	 */
	public int applyFilter(ParkFilter filter)
	{
		if(filter == null) // cancel filtering
		{
			super.clear();
			super.addAll(this.unfilteredParkingList);
		}
		else
		{
			List<AbstractParking> filtered =
					new ArrayList<AbstractParking>();
			
			for(AbstractParking parking: this.unfilteredParkingList)
			{
				if(filter.match(parking))
				{
					filtered.add(parking);
				}
			}
			
			if (filtered.isEmpty())
			{
				// notify user
				Context context = getContext();
				
				Toast
				.makeText(
					context, 
					R.string.filter_by_distance_no_values, 
					Toast.LENGTH_SHORT
				)
				.show();
			}
			else
			{
				this.parkFilter = filter;
				
				// Clear all parkings
				super.clear();
				
				// Refilter list.
				super.addAll(filtered);
			}
		}
		
		return this.getCount();
	}

	@Override
	public void insert(AbstractParking parking, int index) {
		// Add to unfiltered list.
		this.unfilteredParkingList.add(index, parking);
		
		// Add to filtered list.
		if(this.parkFilter == null) // no filter
		{
			super.insert(parking, index);
		}
		else if (this.parkFilter.match(parking))
		{
			super.insert(parking, index);
		}
	}
	
	@Override
	public void add(AbstractParking parking) {
		this.insert(parking, 0);
	}

	@Override
	public void addAll(Collection<? extends AbstractParking> collection) {
		for (AbstractParking parking : collection)
		{
			this.add(parking);
		}
	}

	@Override
	public void addAll(AbstractParking... items) {
		for(AbstractParking item : items)
		{
			this.add(item);
		}
	}

	@Override
	public void remove(AbstractParking parking) {
		// In parent and in local collection
		super.remove(parking);
		
		this.unfilteredParkingList.remove(parking);
	}

	@Override
	public void clear() {
		// In parent and in local collection 
		super.clear();
		
		this.unfilteredParkingList.clear();
	}
}
