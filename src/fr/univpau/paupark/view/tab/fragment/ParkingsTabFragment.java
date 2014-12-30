package fr.univpau.paupark.view.tab.fragment;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import fr.univpau.paupark.R;
import fr.univpau.paupark.model.Parking;
import fr.univpau.paupark.model.OpenDataParkingsAdapter;
import fr.univpau.paupark.service.OpenDataParkings;
import android.app.Fragment;
import android.content.res.Resources.NotFoundException;
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
    	//ParkingsController controller = ParkingsController.getInstance();
    	OpenDataParkings service = OpenDataParkings.getInstance();
    	
    	OpenDataParkingsAdapter adapter = new OpenDataParkingsAdapter(getActivity(), 0, new ArrayList<Parking>());
    	
    	ListView parkingListView = (ListView) getActivity().findViewById(R.id.openDataParkingsListHolder);
    	parkingListView.setAdapter(adapter);
    	
    	
		try 
		{
			
			URL serviceUrl = new URL(getResources().getString(R.string.json_url));
	    	service.initialize(getActivity(), adapter, serviceUrl);
	    	
	    	//Download through async task
	    	service.downloadParkings();
	    	
		} 
		catch (MalformedURLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (NotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
}