package fr.univpau.paupark.model;

import java.io.Serializable;

import android.content.Context;
import fr.univpau.paupark.R;

public abstract class AbstractParking implements Serializable
{
	/** For the serializable interface. */
	private static final long serialVersionUID = 2033302082653142241L;
	
	protected int capacity;
	protected String name;
	protected String town;
	protected GeoCoordinate coordinates;
	protected CraftType craftType;
	protected boolean isCharged;
	
	
	/**
	 * Represents the "Ouvrage" field.
	 * 
	 * @author Josuah Aron
	 *
	 */
	public enum CraftType
	{
		/** The parking the an opened-air parking. */
		OPENED,
		/** The parking is underground. */
		UNDERGROUND;
		
		/** Allows to access the string resources (for internationalisation). */
		private static Context context;
		
		@Override
		public String toString() // TODO
		{
			String name = null;
			
			if(CraftType.context == null)
			{
				name = super.toString();
			}
			else
			{
				switch(this)
				{
				case UNDERGROUND:
					name = CraftType.context.getString(R.string.parking_craft_underground);
					break;
				case OPENED:
					name = CraftType.context.getString(R.string.parking_craft_opened);
					break;
				}
			}
			
			return name;
		}
		
		/**
		 * Setting the context in order to access the string resources.
		 * 
		 * @param context the android context.
		 */
		public static void init(Context context)
		{
			if(CraftType.context == null)
			{
				CraftType.context = context;
			}
		}
	}

	/**
	 * Constructor.
	 * 
	 * @param capacity the number of place of the parking (may be dynamic, ie available places).
	 * @param name the name of the parking.
	 * @param town the city where the parking is.
	 * @param coordinates the geographic coordinate of the parking.
	 * @param isCharged tells whether the parking is charged or not.
	 * @param craftType sky-opened or underground.
	 */
	protected AbstractParking(int capacity, String name, String town,
			GeoCoordinate coordinates, boolean isCharged, CraftType craftType)
	{
		this.capacity = capacity;
		this.name = name;
		this.town = town;
		this.coordinates = new GeoCoordinate(coordinates);
		this.isCharged = isCharged;
		this.craftType = craftType;
	}
	
	/**
	 * Default constructor.
	 */
	protected AbstractParking() 
	{
		this(0, null, null, null, false, null);
	}
	
	// TODO: comment
	/**
	 *
	 * @param parkingIsFree
	 * @param parkingIsNotFree
	 * @return
	 */
	public String getCommuneTypePriceTagLine(String parkingIsFree, String parkingIsNotFree)
	{
		return this.town + " - " + this.craftType + " - " + (this.isCharged ? parkingIsNotFree  : parkingIsFree);
	}
	
	// TODO: comment
	/**
	 * 
	 * @param vacancy
	 * @param vacancyPlural
	 * @param noVacancy
	 * @return
	 */
	public String getVacancyTagLine(String vacancy, String vacancyPlural, String noVacancy)
	{
		String line = noVacancy;
		
		if (this.capacity > 0)
		{
			line = String.valueOf(this.capacity) + " " + vacancy;
			
			if (this.capacity > 1)
			{
				line = String.valueOf(this.capacity) + " " + vacancyPlural;
			}
		}

		return line;
	}

	/**
	 * 
	 * @return the number of place of the parking (may be dynamic, ie available places).
	 */
	public int getCapacity()
	{
		return this.capacity;
	}

	/**
	 * 
	 * @param capacity the number of place of the parking (may be dynamic, ie available places).
	 */
	public void setCapacity(int capacity)
	{
		this.capacity = capacity;
	}

	/**
	 * 
	 * @return the name of the parking.
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * @param name the name of the parking.
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * 
	 * @return the town where the parking is.
	 */
	public String getTown()
	{
		return this.town;
	}

	/**
	 * @param town the town where the parking is.
	 */
	public void setTown(String town)
	{
		this.town = town;
	}

	/**
	 * 
	 * @return the geographic coordinates of the parking.
	 */
	public GeoCoordinate getCoordinates()
	{
		return this.coordinates;
	}

	/**
	 * @param coordinates the geographic coordinates of the parking.
	 */
	public void setCoordinates(GeoCoordinate coordinates)
	{
		this.coordinates = coordinates;
	}

	/**
	 * 
	 * @return the geographic coordinates of the parking.
	 */
	public CraftType getCraftType()
	{
		return this.craftType;
	}

	/**
	 * 
	 * @param craftType sky-opened or underground.
	 */
	public void setCraftType(CraftType craftType)
	{
		this.craftType = craftType;
	}

	/**
	 * Tells whether the parking is charged or not.
	 * @return true if the parking is charged, false if it is charge free.
	 */
	public boolean isCharged()
	{
		return this.isCharged;
	}

	/**
	 * 
	 * @param isCharged tells whether the parking is charged or not.
	 */
	public void setCharged(boolean isCharged)
	{
		this.isCharged = isCharged;
	}
}
