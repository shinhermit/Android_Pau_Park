package fr.univpau.paupark.service;

public class OpenDataParkings extends ParkingsController {
	private static OpenDataParkings _instance = new OpenDataParkings();
	
	private OpenDataParkings() {}
	
	public static OpenDataParkings getInstance()
	{
		return _instance;
	}
}
