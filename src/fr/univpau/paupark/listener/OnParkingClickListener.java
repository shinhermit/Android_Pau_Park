package fr.univpau.paupark.listener;

import fr.univpau.paupark.R;
import fr.univpau.paupark.model.AbstractParking;
import fr.univpau.paupark.model.GeoCoordinate;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;



public class OnParkingClickListener implements OnClickListener {

	private AbstractParking _parking;
	private Context _context;
	
	public OnParkingClickListener(Context context, AbstractParking parking)
	{
		this._context = context;
		this._parking = parking;
	}
	
	@Override
	public void onClick(View v)
	{
		GeoCoordinate coordinates = this._parking.getCoordinates();
		String label = this._parking.getName();		
		String uriBegin = this._context.getResources().getString(R.string.google_maps_url);
		String defaultZoom = this._context.getResources().getString(R.string.google_maps_default_zoom);
		
		String query = coordinates.getLatitude() + "," + coordinates.getLongitude() + "(" + label + ")";
		String encodedQuery = Uri.encode(query);
		
		String uriString = uriBegin + "?q=" + encodedQuery + "&z=" + defaultZoom;
		
		Uri uri = Uri.parse(uriString);
		
		Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
		this._context.startActivity(intent);
	}

}
