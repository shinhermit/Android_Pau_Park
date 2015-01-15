package fr.univpau.paupark.service;

import fr.univpau.paupark.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetworkStatusReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) 
	{
		
		if (intent.getAction().equals(android.net.ConnectivityManager.CONNECTIVITY_ACTION))
		{
			if (!this.isConnected(context))
			{
				String disconnected = context.getString(R.string.network_status_down); 
				
				Toast.makeText(context, disconnected, Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	private boolean isConnected(Context context)
	{
		ConnectivityManager cm =
		        (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		 
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnected = activeNetwork != null &&
		                      activeNetwork.isConnectedOrConnecting();
		
		return isConnected;
	}
	

}
