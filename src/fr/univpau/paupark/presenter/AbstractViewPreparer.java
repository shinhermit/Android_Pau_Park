package fr.univpau.paupark.presenter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * Delegaton of the getView method of ArrayAdapter<AbstractParking> 
 *
 */
abstract class AbstractViewPreparer {
	/**
	 * 
	 * @param convertView view to populate with data
	 * @param data data used to populate the view
	 * @return
	 */
	abstract public View getView(View convertView, Object data);
	
	/**
	 * Builds the view passed to getView
	 * @return
	 */
	abstract public View buildConvertView(Context context, ViewGroup parent);
}
