package fr.univpau.paupark.service;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.univpau.paupark.model.Parking;

public class JSONParkingParser implements Iterable<Parking> {
	private ArrayList<Parking> _parkings = new ArrayList<Parking>();
	
	private String _jsonString;
	
	public JSONParkingParser() {}

	
	public void setJsonString(String json)
	{
		this._jsonString = json;
		this.parse();
	}
	
	@Override
	public Iterator<Parking> iterator() {
		Iterator<Parking> parkingIterator = this._parkings.iterator();
		
		return parkingIterator;
	}

	/**
	 * {
	 *   "name": "Parking_WGS84",
	 *   "type": "FeatureCollection",
	 *   "features": [
	 *     {
	 *       "type": "Feature",
	 *       "geometry": {
	 *         "type": "Point",
	 *         "coordinates": [
	 *           -0.36805447415889,
	 *           43.293705019517
	 *         ]
	 *       },
	 *       "properties": {
	 *         "Ouvrage": "Souterrain", (ou "Plein air")
	 *         "Pay_grat": "Payant", (ou "Gratuit")
	 *         "COMMUNE": "PAU",
	 *         "NOM": "Parking Aragon",
	 *         "Places": 418
	 *       }
	 *     },
	 *     …
	 *   ]
	 * }
	 * @return
	 */
	private void parse()
	{
		this._parkings.clear();
		
		try {
			JSONObject json = new JSONObject(this._jsonString);
			
			//List of parkings
			JSONArray parkings = json.getJSONArray("features");
			
			//Iterate through parkings and build corresponding Parking objects
			int count = parkings.length();
			for (int i = 0; i < count; i++) 
			{
				JSONObject currentParking = parkings.getJSONObject(i);
				
				//type should be "Feature"
				if (currentParking.getString("type").equals("Feature")) {
					//Coordinates
					JSONObject geom = currentParking.getJSONObject("geometry");
					JSONArray coordinates = geom.getJSONArray("coordinates");
					double lng = coordinates.getDouble(0);
					double lat = coordinates.getDouble(1);
					
					//Other properties
					JSONObject properties = currentParking.getJSONObject("properties");
					String type = properties.getString("Ouvrage");
					boolean free = properties.getString("Pay_grat").equals("Gratuit");
					String commune = properties.getString("COMMUNE");
					String name = properties.getString("NOM");
					int numVacancy = properties.getInt("Places");
					
					this._parkings.add(
						new Parking(numVacancy, name, commune, lat, lng, free,type)
					);
				}
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
