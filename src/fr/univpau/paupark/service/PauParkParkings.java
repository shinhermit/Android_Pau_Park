package fr.univpau.paupark.service;

public class PauParkParkings extends ParkingsController {
	private static PauParkParkings _instance = new PauParkParkings();
	
	private PauParkParkings() {}
	
	public static PauParkParkings getInstance()
	{
		return _instance;
	}
}
