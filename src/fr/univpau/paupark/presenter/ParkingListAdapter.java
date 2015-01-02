package fr.univpau.paupark.presenter;

import java.util.List;

import fr.univpau.paupark.R;
import fr.univpau.paupark.listener.OnParkingClickListener;
import fr.univpau.paupark.model.AbstractParking;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ParkingListAdapter extends ArrayAdapter<AbstractParking> {	
	private AbstractViewPreparer preparer;
	
	public ParkingListAdapter(Context context, int resource, List<AbstractParking> objects, AbstractViewPreparer preparer) 
	{
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		
		this.preparer = preparer;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		//Current parking
		AbstractParking parking = this.getItem(position);
		//Check if an existing view has been passed
		if (convertView == null)
		{
			convertView = this.preparer.buildConvertView(getContext(), parent);
		}
		
		convertView = this.preparer.getView(convertView, parking);
		
		return convertView;
	}
	
}
