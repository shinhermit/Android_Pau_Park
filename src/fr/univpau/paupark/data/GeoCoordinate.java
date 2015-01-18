package fr.univpau.paupark.data;

import java.io.Serializable;

import android.util.Log;

/**
 * Represents a geographic coordinate, with components <i>latitude</i> and <i>longitude</i>.
 * 
 * @author Josuah Aron
 *
 */
public class GeoCoordinate implements Serializable
{
	/** For the serializable interface. */
	private static final long serialVersionUID = 6470943984088677691L;

	/** The latitude component of the coordinate. */
	private double latitude;
	
	/** The longitude component of the coordinate. */
	private double longitude;
	
	/** The minimal legal value for latitude. */
	public static final double LATITUDE_MIN = 0d;
	
	/** The maximal legal value for latitude. */
	public static final double LATITUDE_MAX = 90d;
	
	/** The minimal legal value for longitude. */
	public static final double LONGITUDE_MIN = -180d;
	
	/** The maximal legal value for longitude. */
	public static final double LONGITUDE_MAX = 180d;
	
	/**
	 * Constructor.
	 * 
	 * @param latitude the latitude components of the geographic coordinate.
	 * @param longitude the latitude components of the geographic coordinate.
	 */
	public GeoCoordinate(double latitude, double longitude)
	{
		this.setLatitude(latitude);
		this.setLongitude(longitude);
	}
	
	/**
	 * Copy constructor.
	 */
	public GeoCoordinate(GeoCoordinate other)
	{
		this.latitude = other.latitude;
		this.longitude = other.longitude;
	}
	
	/**
	 * Defines the latitude components of the geographic coordinate.
	 * 
	 * @param latitude the latitude components of the geographic coordinate.
	 */
	public void setLatitude(double latitude)
	{
		if(latitude < LATITUDE_MIN
			|| latitude > LATITUDE_MAX)
		{
			Log.i(this.getClass().getName(), "Latitude is "
					+ "out of bounds: value will be clamped to"
					+ " ["+LATITUDE_MIN+", "+LATITUDE_MAX+"]");
		}
		
		this.latitude = GeoCoordinate.clamp(
				latitude, LATITUDE_MIN, LATITUDE_MAX);
	}
	
	/**
	 * Defines the longitude components of the geographic coordinate.
	 * 
	 * @param longitude the longitude components of the geographic coordinate.
	 */
	public void setLongitude(double longitude)
	{
		if(longitude < LONGITUDE_MIN
			|| longitude > LONGITUDE_MAX)
		{
			Log.i(this.getClass().getName(), "Longitude is "
					+ "out of bounds: value will be clamped to"
					+ " ["+LONGITUDE_MIN+", "+LONGITUDE_MAX+"]");
		}
		
		this.longitude = GeoCoordinate.clamp(
				longitude, LONGITUDE_MIN, LONGITUDE_MAX);
	}
	
	/**
	 * Provides the latitude components of the geographic coordinate.
	 * 
	 * @return the latitude components of the geographic coordinate.
	 */
	public double getLatitude()
	{
		return this.latitude;
	}
	
	/**
	 * Provides the longitude components of the geographic coordinate.
	 * 
	 * @return the longitude components of the geographic coordinate.
	 */
	public double getLongitude()
	{
		return this.longitude;
	}
	
	/**
	 * Clamps a value between min and max.
	 * 
	 * @param value the value which is to be clamped.
	 * @param min the min value allowed.
	 * @param max the max value allowed.
	 * 
	 * @return value if <i>min <= value <= max, max if <i>value > max</i>, min if <i>value < min </i>   
	 */
	private static double clamp(double value, double min, double max)
	{
		return Math.max(Math.min(value, max), min);
	}
}
