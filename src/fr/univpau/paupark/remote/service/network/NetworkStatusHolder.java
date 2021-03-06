package fr.univpau.paupark.remote.service.network;

import android.content.Context;

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
