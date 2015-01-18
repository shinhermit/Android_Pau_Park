package fr.univpau.paupark.data;

/**
 * Represents a parking obtained from the official pau park opened data.
 * 
 * @author Josuah Aron
 *
 */
public class OfficialParking extends AbstractParking
{
	/** For the serializable interface. */
	private static final long serialVersionUID = 4632946201378766888L;

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
	public OfficialParking(int capacity, String name, String town,
			GeoCoordinate coordinates, boolean isCharged, CraftType craftType)
	{
		super(capacity, name, town, coordinates, isCharged, craftType);
	}
	
	/**
	 * Default constructor.
	 */
	public OfficialParking() 
	{
		super();
	}
}
