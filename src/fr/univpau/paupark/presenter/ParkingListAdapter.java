package fr.univpau.paupark.presenter;

import java.util.List;

import fr.univpau.paupark.model.AbstractParking;
import fr.univpau.paupark.model.PauParkPreferences;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

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
		Log.i("ParkingListAdapter", "position: "+position);
		
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
		Log.i("ParkingListAdapter", "getCount: "+Math.min(super.getCount(), this.nbItemsPerPage));
		Log.i("ParkingListAdapter", "nbItemsPerPage: "+this.nbItemsPerPage);
		return (this.isPaginOn) ?
				Math.min(super.getCount(), this.nbItemsPerPage)
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
	 * Provides the page where the item must be displayed.
	 * 
	 * @param item the item of which the page is queried.
	 * @return the page where the item must be displayed
	 */
	public int getPage(AbstractParking item)
	{
		return super.getPosition(item) / this.nbItemsPerPage;
	}
	
	/**
	 * 
	 * @param positionInCurrentPage the position of the item in the current page.
	 * @return the position of the object when paging is not activated.
	 */
	private int toGlobalPosition(int positionInCurrentPage)
	{
		return (this.isPaginOn) ?
				this.currentPage * this.nbItemsPerPage + positionInCurrentPage
				: positionInCurrentPage;
	}
	
	/**
	 * 
	 * @param position the position of the item when paging is disabled..
	 * @return the position of the object in it's page.
	 */
	private int toPagePosition(int position)
	{
		return (this.isPaginOn) ?
				position - ( (position/this.nbItemsPerPage) * this.nbItemsPerPage )
				: position;
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
		this.currentPage = Math.max( 0, Math.min(pageIndex, super.getCount() - 1) );
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
		this.nbItemsPerPage = Math.max(0, nbItems);
	}
	
	/**
	 * Provides the number of pages needed to show all the items of this adapter.
	 * 
	 * @return the number of pages needed to show all the items of this adapter.
	 */
	public int getNbPage()
	{
		return super.getCount() / this.nbItemsPerPage;
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
}
