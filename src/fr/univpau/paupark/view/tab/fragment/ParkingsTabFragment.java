package fr.univpau.paupark.view.tab.fragment;

import java.util.ArrayList;

import fr.univpau.paupark.R;
import fr.univpau.paupark.model.Parking;
import fr.univpau.paupark.model.ParkingsAdapter;
import fr.univpau.paupark.service.JSONParkingParser;
import fr.univpau.paupark.service.ParkingsController;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ParkingsTabFragment extends Fragment {
    @Override
    public View onCreateView(
		LayoutInflater inflater, 
		ViewGroup container,
        Bundle savedInstanceState) 
    {
        // Inflate the layout for this fragment
    	// and return the root of the fragment layout
        return inflater.inflate(R.layout.parkings_tab, container, false);
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onActivityCreated(savedInstanceState);
    	ParkingsController controller = ParkingsController.getInstance();
    	
    	ParkingsAdapter adapter = new ParkingsAdapter(getActivity(), 0, new ArrayList<Parking>());
    	
    	ListView parkingListView = (ListView) getActivity().findViewById(R.id.parkingsListHolder);
    	parkingListView.setAdapter(adapter);
    	
    	//Download through async task
    	controller.downloadParkings(getActivity(), adapter, new JSONParkingParser());
    }
}