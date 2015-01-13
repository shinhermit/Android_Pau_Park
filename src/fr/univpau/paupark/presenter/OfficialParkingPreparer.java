package fr.univpau.paupark.presenter;

import fr.univpau.paupark.R;
import fr.univpau.paupark.listener.OnOfficialParkingClickListener;
import fr.univpau.paupark.model.OfficialParking;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class OfficialParkingPreparer extends AbstractViewPreparer {	
	
	@Override
	public View populateView(View convertView, Object data) {
		OfficialParking parking = (OfficialParking) data;
		
		Context context = convertView.getContext();
		
		Resources res = context.getResources();
		
		
		TextView parkingTown = (TextView) convertView.findViewById(R.id.parkingAsListItem_TownTypePrice);
		parkingTown.setText(
			parking.getCommuneTypePriceTagLine(
					res.getString(R.string.parking_isfree), 
					res.getString(R.string.parking_isnotFree)
			)
		);		
		
		TextView parkingName = (TextView) convertView.findViewById(R.id.parkingAsListItem_parkingName);
		parkingName.setText(parking.getName());
		
		TextView parkingVacancy = (TextView) convertView.findViewById(R.id.parkingAsListItem_parkingVacancy);
		parkingVacancy.setText(parking.getVacancyTagLine(
				res.getString(R.string.parking_vacancy), 
				res.getString(R.string.parking_vacancyPlural),
				res.getString(R.string.parking_noVacancy)
			)
		);
		
		convertView.setOnClickListener(
				new OnOfficialParkingClickListener(context, parking));
		
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
