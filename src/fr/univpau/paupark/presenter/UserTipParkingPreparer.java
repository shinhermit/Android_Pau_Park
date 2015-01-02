package fr.univpau.paupark.presenter;

import fr.univpau.paupark.R;
import fr.univpau.paupark.model.UserTipParking;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class UserTipParkingPreparer extends AbstractViewPreparer {	
	@Override
	public View getView(View convertView, Object data) {
		UserTipParking parking = (UserTipParking) data;
		Context context = convertView.getContext();
		Resources res = context.getResources();
		
		// Author of the tip
		TextView author = (TextView) 
				convertView.findViewById(
						R.id.userTipParkingAsListItem_authorNickname
				);
		author.setText(parking.getAuthorNickname());
		
		// Town type and price
		TextView townTypePrice = (TextView) 
				convertView.findViewById(
						R.id.userTipParkingAsListItem_townTypePrice
				);
		townTypePrice.setText(
				parking.getCommuneTypePriceTagLine(
						res.getString(R.string.parking_isfree), 
						res.getString(R.string.parking_isnotFree)
				)
			);	
		
		// Name of the parking
		TextView name = (TextView) 
				convertView.findViewById(
						R.id.userTipParkingAsListItem_name
				);
		name.setText(parking.getName());
		
		// Number of upvotes
		TextView upvotes = (TextView) 
				convertView.findViewById(
						R.id.userTipParkingAsListItem_upvotes
				);
		upvotes.setText(String.valueOf(parking.getUpvotes()));
		
		//Number of downvotes
		TextView downvotes = (TextView) 
				convertView.findViewById(
						R.id.userTipParkingAsListItem_downvotes
				);
		downvotes.setText(String.valueOf(parking.getDownvotes()));
		
		
		//Parking capacity
		TextView capacity = (TextView) 
				convertView.findViewById(
						R.id.userTipParkingAsListItem_capacity
				);
		capacity.setText(parking.getVacancyTagLine(
				res.getString(R.string.parking_vacancy), 
				res.getString(R.string.parking_vacancyPlural),
				res.getString(R.string.parking_noVacancy)
			)
		);
		
		return convertView;
	}

	@Override
	public View buildConvertView(Context context, ViewGroup parent) {

		View view = 
				LayoutInflater.from(context).inflate(
						R.layout.usertip_parking_as_list_item, parent, false);
		
		return view;
	}

}
