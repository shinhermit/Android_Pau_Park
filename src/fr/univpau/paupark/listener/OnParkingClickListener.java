package fr.univpau.paupark.listener;

import fr.univpau.paupark.model.Parking;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;



public class OnParkingClickListener implements OnClickListener {

	private Parking _parking;
	private Context _context;
	
	public OnParkingClickListener(Context context, Parking parking)
	{
		this._context = context;
		this._parking = parking;
	}
	
	@Override
	public void onClick(View v) {
		String latLng = String.valueOf(this._parking.getLat()) + ", " + String.valueOf(this._parking.getLng());
		Toast.makeText(_context, latLng , Toast.LENGTH_SHORT).show();;
	}

}
