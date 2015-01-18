package fr.univpau.paupark.util;

/**
 * Holds the constants which represent the keys associated with the values
 * saved in the preferences.
 * 
 * @author Josuah Aron
 *
 */
public class PauParkPreferences
{
	/** Key of the value which tells whether the user wants to use geolocation or not. */
	public static final String GEOLOCATION_PREF_KEY = "GEOLOCATION_PREF_KEY";
	
	/** Key of the value which tells whether the user wants to use pagination for the parking listing or not. */
	public static final String PAGINATION_PREF_KEY = "PAGINATION_PREF_KEY";
	
	/** Key of the value which saves the user's nickname. */
	public static final String NICKNAME_KEY = "NICKNAME_KEY";
	
	/** Key of the value which saves the last number of items of the official parking list per page chosen by the user. */
	public static final String LAST_NB_OFFICIAL_PARKING_ITEMS_PER_PAGE = "LAST_NB_OFFICIAL_PARKING_ITEMS_PER_PAGE";
	
	/** Key of the value which saves the last number of items of the user tip parking list per page chosen by the user. */
	public static final String LAST_NB_USER_TIP_PARKING_ITEMS_PER_PAGE = "LAST_NB_USER_TIP_PARKING_ITEMS_PER_PAGE";
	
	/** Key of the value which saves the last current page of the official parking list. */
	public static final String LAST_OFFICIAL_PARKING_ITEMS_CURRENT_PAGE = "LAST_OFFICIAL_PARKING_ITEMS_CURRENT_PAGE";
	
	/** Key of the value which saves the last current page of the user tip parking list. */
	public static final String LAST_USER_TIP_PARKING_ITEMS_CURRENT_PAGE = "LAST_USER_TIP_PARKING_ITEMS_CURRENT_PAGE";
	
	/** Key of the value which saves the last selected distance filter of the official parking list. */
	public static final String LAST_OFFICIAL_PARKING_SELECTED_DISTANCE_FILTER = "LAST_OFFICIAL_PARKING_SELECTED_DISTANCE_FILTER";
	
	/** Key of the value which saves the last selected distance filter of the user tip parking list. */
	public static final String LAST_USER_TIP_PARKING_SELECTED_DISTANCE_FILTER = "LAST_USER_TIP_PARKING_SELECTED_DISTANCE_FILTER";
	
	/** Default value for the pagination state. */
	public static final boolean DEFAULT_IS_PAGINATION_ON = true;
	
	/** Default value for the pagination state. */
	public static final int DEFAULT_NB_ITEMS_PER_PAGE = 5;
	
	/** The max number of items per page. */
	public static final int SEEK_BAR_MAX = 20;
}
