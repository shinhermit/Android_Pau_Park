package fr.univpau.paupark.service;

import fr.univpau.paupark.R;
import android.content.Context;
import android.widget.Toast;

public class NetworkStatusHolder {
	private static NetworkStatusHolder INSTANCE = new NetworkStatusHolder();
	
	private NetworkStatusHolder() {}
	
	private boolean connected = false;
	
	public static NetworkStatusHolder getInstance()
	{
		return INSTANCE;
	}
	
	public void setConnected(boolean connected, Context context)
	{
		this.connected = connected;
		
		if (this.connected == false)
		{
			String disconnected = context.getString(R.string.network_status_down);
			Toast.makeText(context, disconnected, Toast.LENGTH_SHORT).show();
		}
	}
	
	public boolean isConnected()
	{
		return this.connected;
	}
	
	
}
