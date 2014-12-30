package fr.univpau.paupark.model;

import java.util.List;

import fr.univpau.paupark.R;
import fr.univpau.paupark.listener.OnParkingClickListener;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PauParkParkingsAdapter extends ArrayAdapter<Parking> {	
	public PauParkParkingsAdapter(Context context, int resource, List<Parking> objects) 
	{
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		//Current parking
		Parking parking = this.getItem(position);
		//Check if an existing view has been passed
		if (convertView == null)
		{
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.paupark_parking_as_list_item, parent, false);
		}
		
		return convertView;
	}
	
}
