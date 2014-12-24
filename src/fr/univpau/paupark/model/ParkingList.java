package fr.univpau.paupark.model;

public class ParkingList {
	private static ParkingList _INSTANCE = new ParkingList();
	private String _listOfParkings;
	private boolean _loaded = false;
	
	private ParkingList () {}
	
	public static ParkingList getInstance ()
	{
		return _INSTANCE;
	}
	
	public void setListAsJson (String parkings) 
	{
		this._listOfParkings = parkings;
		this._loaded = true;
	}
	
	public String getListAsJson ()
	{
		return this._listOfParkings;
	}
	
	public boolean isReady ()
	{
		return this._loaded;
	}
}
