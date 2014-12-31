package fr.univpau.paupark.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import fr.univpau.paupark.model.AbstractParking;
import fr.univpau.paupark.model.GeoCoordinate;
import fr.univpau.paupark.model.OfficialParking;
import fr.univpau.paupark.model.AbstractParking.CraftType;
import fr.univpau.paupark.model.UserTipParking;

public class JSONParkingParser
{
	/**
	 * Official parking list type displayed as top level property.
	 */
	public static String TYPE_OFFICIAL_PARKING_LIST = "Parking_WGS84";
	/**
	 * User tips parking list type displayed as top level property.
	 */
	public static String TYPE_USERTIPS_PARKING_LIST = "Parking_AndroidGPSData";
	
	public List<AbstractParking> parse(String jsonString)
	{
		ArrayList<AbstractParking> parkings =
				new ArrayList<AbstractParking>();
		
		try {
			JSONObject json = new JSONObject(jsonString);
			
			//The type of list being parsed
			boolean isOfficialParkingList = json.getString("name").equals(TYPE_OFFICIAL_PARKING_LIST);
			
			//List of parkings
			JSONArray parkingArray = json.getJSONArray("features");
			
			//Iterate through parkings and build corresponding Parking objects
			int count = parkingArray.length();
			for (int i = 0; i < count; i++) 
			{
				JSONObject currentParking = parkingArray.getJSONObject(i);
				
				//type should be "Feature"
				if (currentParking.getString("type").equals("Feature")) {
					//Coordinates
					JSONObject geom = currentParking.getJSONObject("geometry");
					JSONArray coordinates = geom.getJSONArray("coordinates");
					double lng = coordinates.getDouble(0);
					double lat = coordinates.getDouble(1);
					
					//Other properties
					JSONObject properties = currentParking.getJSONObject("properties");
					OfficialParking.CraftType craftType = this.craftEnumFromJson(properties.getString("Ouvrage"));
					boolean free = properties.getString("Pay_grat").equals("Gratuit");
					//String commune = new String(properties.getString("COMMUNE").getBytes("ISO-8859-1"), "UTF-8");
					String town = properties.getString("COMMUNE");
					String name = properties.getString("NOM");
					int capacity = properties.getInt("Places");
					

					AbstractParking p;
					
					if (isOfficialParkingList)
					{
						p = new OfficialParking(capacity, name, town,
								new GeoCoordinate(lat, lng), free,craftType); 
					}
					else 
					{
						//Read additionnal properties if dealing with users tips parking list
						long id = properties.getLong("id");
						String authorNickName = properties.getString("pseudo");
						String description = properties.getString("comment");
						long upvotes = properties.getLong("upvotes");
						long downvotes = properties.getLong("downvotes");

						p = new UserTipParking(id, capacity, name, town,
								new GeoCoordinate(lat, lng), free, craftType,
								description, authorNickName, upvotes, downvotes);
					}
					
					parkings.add(p);
						
				}
			}
		}
		catch (JSONException e)
		{
			Log.e(this.getClass().getName(), null, e);
		}
		
		return parkings;
	}
	
	private AbstractParking.CraftType craftEnumFromJson(String jsonValue)
	{
		String lowerCased = jsonValue.toLowerCase(Locale.FRANCE);
		
		AbstractParking.CraftType craftType = null;
		
		if(lowerCased.equals("plein air"))
		{
			craftType =  AbstractParking.CraftType.OPENED;
		}
		else if(lowerCased.equals("souterrain"))
		{
			
			craftType = AbstractParking.CraftType.UNDERGROUND;
		}
		
		return craftType;
	}
}
