package fr.univpau.paupark.view.tab.fragment;

import fr.univpau.paupark.R;
import fr.univpau.paupark.presenter.ParkingListAdapter;
import fr.univpau.paupark.service.ParkingServiceImpl;
import fr.univpau.paupark.service.ParkingServices;
import fr.univpau.paupark.service.ParkingServices.ParkingInfoSource;
import fr.univpau.paupark.view.PauParkActivity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class TipsTabFragment extends Fragment {
    @Override
    public View onCreateView(
		LayoutInflater inflater, 
		ViewGroup container,
        Bundle savedInstanceState) 
    {
        // Inflate the layout for this fragment
    	// and return the root of the fragment layout
        return inflater.inflate(R.layout.tips_tab, container, false);
    }
        
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
    	super.onActivityCreated(savedInstanceState);
    	
    	// Set presenter
    	PauParkActivity activity = (PauParkActivity)
    			this.getActivity();
    	
    	ListView parkingListView = (ListView)
    			activity.findViewById(R.id.userTipsListHolder);
    	
    	ParkingListAdapter adapter =
    			activity.getParkingTipListAdapter();
    	
    	parkingListView.setAdapter(adapter);
    	
		// Query load parking service
		ParkingServices services =
				ParkingServiceImpl.getInstance();
		services.loadParkingList(ParkingInfoSource.USERS, adapter);
    }
}