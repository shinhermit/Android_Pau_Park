package fr.univpau.paupark.view.presenter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * Delegation of the getView method of ArrayAdapter<AbstractParking> 
 * 
 * @author Émilien Arino
 *
 */
abstract class AbstractViewPreparer {
	/**
	 * 
	 * @param convertView view to populate with data
	 * @param data data used to populate the view
	 * @return
	 */
	abstract public View populateView(View convertView, Object data);
	
	/**
	 * Builds the view passed to prepare
	 * @return
	 */
	abstract public View buildConvertView(Context context, ViewGroup parent);
}
