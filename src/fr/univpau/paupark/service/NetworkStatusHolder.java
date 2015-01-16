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
		
	public boolean isConnected()
	{
		return this.connected;
	}
	
	public void checkConnection(Context context)
	{
		this.connected = NetworkStatusChecker.isConnected(context);
	}
	
}
