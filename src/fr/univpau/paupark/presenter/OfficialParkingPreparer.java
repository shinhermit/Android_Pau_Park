package fr.univpau.paupark.presenter;

import fr.univpau.paupark.R;
import fr.univpau.paupark.listener.OnParkingClickListener;
import fr.univpau.paupark.model.OfficialParking;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class OfficialParkingPreparer extends AbstractViewPreparer {	
	
	@Override
	public View getView(View convertView, Object data) {
		OfficialParking parking = (OfficialParking) data;
		
		Context context = convertView.getContext();
		
		TextView parkingTown = (TextView) convertView.findViewById(R.id.parkingAsListItem_TownTypePrice);
		parkingTown.setText(
			parking.getCommuneTypePriceTagLine(
					context.getResources().getString(R.string.parking_isfree), 
					context.getResources().getString(R.string.parking_isnotFree)
			)
		);		
		
		TextView parkingName = (TextView) convertView.findViewById(R.id.parkingAsListItem_parkingName);
		parkingName.setText(parking.getName());
		
		TextView parkingVacancy = (TextView) convertView.findViewById(R.id.parkingAsListItem_parkingVacancy);
		parkingVacancy.setText(parking.getVacancyTagLine(
				context.getResources().getString(R.string.parking_vacancy), 
				context.getResources().getString(R.string.parking_vacancyPlural),
				context.getResources().getString(R.string.parking_noVacancy)
			)
		);
		
		OnParkingClickListener listener = new OnParkingClickListener(context, parking);
		convertView.setOnClickListener(listener);
		
		return convertView;
	}

	@Override
	public View buildConvertView(Context context, ViewGroup parent) {
		View view = 
				LayoutInflater.from(context).inflate(
						R.layout.parking_as_list_item, parent, false);
		
		return view;
	}

}
