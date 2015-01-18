package fr.univpau.paupark.remote.service;

import fr.univpau.paupark.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NetworkStatusChangeReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) 
	{
		
		if (intent.getAction().equals(android.net.ConnectivityManager.CONNECTIVITY_ACTION))
		{
			NetworkStatusHolder holder = NetworkStatusHolder.getInstance();
			holder.checkConnection(context);
			
			if (!holder.isConnected())
			{
				String disconnected = context.getString(R.string.network_status_down);
				Toast.makeText(context, disconnected, Toast.LENGTH_SHORT).show();
			}
			
		}
	}

}
