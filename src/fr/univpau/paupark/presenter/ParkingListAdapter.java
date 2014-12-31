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
	public ParkingListAdapter(Context context, int resource, List<AbstractParking> objects) 
	{
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		//Current parking
		AbstractParking parking = this.getItem(position);
		//Check if an existing view has been passed
		if (convertView == null)
		{
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.parking_as_list_item, parent, false);
		}
		
		TextView parkingTown = (TextView) convertView.findViewById(R.id.parkingAsListItem_TownTypePrice);
		parkingTown.setText(
			parking.getCommuneTypePriceTagLine(
				getContext().getResources().getString(R.string.parking_isfree), 
				getContext().getResources().getString(R.string.parking_isnotFree)
			)
		);		
		
		TextView parkingName = (TextView) convertView.findViewById(R.id.parkingAsListItem_parkingName);
		parkingName.setText(parking.getName());
		
		TextView parkingVacancy = (TextView) convertView.findViewById(R.id.parkingAsListItem_parkingVacancy);
		parkingVacancy.setText(parking.getVacancyTagLine(
				getContext().getResources().getString(R.string.parking_vacancy), 
				getContext().getResources().getString(R.string.parking_vacancyPlural),
				getContext().getResources().getString(R.string.parking_noVacancy)
			)
		);
		
		OnParkingClickListener listener = new OnParkingClickListener(getContext(), parking);
		convertView.setOnClickListener(listener);
		
		return convertView;
	}
	
}
