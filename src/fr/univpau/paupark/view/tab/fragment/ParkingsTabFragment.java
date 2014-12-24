package fr.univpau.paupark.view.tab.fragment;

import java.util.ArrayList;
import java.util.List;

import fr.univpau.paupark.R;
import fr.univpau.paupark.model.Parking;
import fr.univpau.paupark.model.ParkingsAdapter;
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
    	
    	List<Parking> parkings = new ArrayList<Parking>();
    	Parking p1 = new Parking(12, "p1", "Pau", 12, 23, true, "Plein air");
    	Parking p2 = new Parking(123, "p2", "Pau", 12, 23, true, "Plein air");
    	Parking p3 = new Parking(124, "p3", "Pau", 12, 23, true, "Plein air");
    	Parking p4 = new Parking(233, "p4", "Pau", 12, 23, true, "Plein air");
    	Parking p5 = new Parking(1245, "p5", "Pau", 12, 23, true, "Plein air");
    	Parking p6 = new Parking(552, "p6", "Pau", 12, 23, true, "Plein air");
    	Parking p7 = new Parking(112, "p7", "Pau", 12, 23, true, "Plein air");
    	Parking p8 = new Parking(1222, "p8", "Pau", 12, 23, true, "Plein air");
    	Parking p9 = new Parking(982, "p9", "Pau", 12, 23, true, "Plein air");

    	
    	
    	ParkingsAdapter adapter = new ParkingsAdapter(getActivity(), 0, parkings);
    	
    	adapter.add(p1);
    	adapter.add(p2);
    	adapter.add(p3);
    	adapter.add(p4);
    	adapter.add(p5);
    	adapter.add(p6);
    	adapter.add(p7);
    	adapter.add(p8);
    	adapter.add(p9);
    	
    	ListView parkingsList = (ListView) getActivity().findViewById(R.id.parkingsListHolder);
    	parkingsList.setAdapter(adapter);

    }
}