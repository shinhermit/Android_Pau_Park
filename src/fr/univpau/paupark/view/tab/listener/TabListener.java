package fr.univpau.paupark.view.tab.listener;
import fr.univpau.paupark.service.ParkingServices.ParkingInfoSource;
import fr.univpau.paupark.view.PauParkActivity;
import fr.univpau.paupark.view.tab.fragment.OfficialParkingTabFragment;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

/**
 * The Tab listener which allows to switch tabs when the user clicks
 * on the action bar's tabs buttons.
 * 
 * <p>Base on the example the android documentation</p>
 * 
 * @author Josuah Aron
 *
 * @param <T>
 * 		the type of fragment to use for the tab which this listener listens to.
 */
public class TabListener<T extends Fragment> implements ActionBar.TabListener
{
	/** The fragment the tab will use. */
    private Fragment mFragment;
    
    /** The activity the tab belongs to. */
    private final Activity mActivity;
    
    /** The identifier tag for the fragment. */
    private final String mTag;
    
    /** The fragment's Class, used to instantiate the fragment. */
    private final Class<T> mClass;

    /** Constructor used each time a new tab is created.
      * @param activity  The host Activity, used to instantiate the fragment
      * @param tag  The identifier tag for the fragment
      * @param clz  The fragment's Class, used to instantiate the fragment
      */
    public TabListener(Activity activity, String tag, Class<T> clz) {
        mActivity = activity;
        mTag = tag;
        mClass = clz;
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft)
    {
        // Check if the fragment is already initialized
        if (mFragment == null)
        {
            // If not, instantiate and add it to the activity
            mFragment = Fragment.instantiate(mActivity, mClass.getName());
            ft.add(android.R.id.content, mFragment, mTag);
        }
        else
        {
            // If it exists, simply attach it in order to show it
            ft.attach(mFragment);
        }
        
        // Feed back to activity
        if(mClass.equals(OfficialParkingTabFragment.class))
        {
        	((PauParkActivity)mActivity).setParkingSource(
        			ParkingInfoSource.OFFICIAL);
        }
        else
        {
        	((PauParkActivity)mActivity).setParkingSource(
        			ParkingInfoSource.USERS);
        }
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft)
    {
        if (mFragment != null)
        {
            // Detach the fragment, because another one is being attached
            ft.detach(mFragment);
        }
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft)
    {
        // User selected the already selected tab. Usually do nothing.
    }
}

