package fr.univpau.paupark.model;

/**
 * Represents a parking tip added by a user.
 * 
 * @author Josuah Aron
 *
 */
public class UserTipParking extends AbstractParking
{
	/** For the serializable interface. */
	private static final long serialVersionUID = -2276161051032190577L;
	
	/** The unique id in the remote storage. */
	private long id = -1;
	
	/** The nickname of the user who added the parking tip. */
	private String authorNickname = null;
	
	/** The description of the parking given by the user. */
	private String description = null;
	
	/** The current votes for this parking tip. */
	private long votes = 0l;

	/** The current upvotes for this parking tip. */
	private long upvotes = 0l;

	/** The current downvotes for this parking tip. */
	private long downvotes = 0l;

	/**
	 * 
	 * @param id the unique id in the remote storage.
	 * @param capacity the number of place of the parking (may be dynamic, ie available places).
	 * @param name the name of the parking.
	 * @param town the city where the parking is.
	 * @param coordinates the geographic coordinate of the parking.
	 * @param isCharged tells whether the parking is charged or not.
	 * @param craftType sky-opened or underground.
	 * @param description the description of the parking, given by the user.
	 * @param authorNickName the nickname of the user who added the parking tip.
	 * @param upvotes current number of upvotes for the tip.
	 * @param upvotes current number of downvotes for the tip.
	 */
	public UserTipParking(long id, int capacity, String name, String town,
			GeoCoordinate coordinates, boolean isCharged, CraftType craftType,
			String description, String authorNickName,  long upvotes, 
			long downvotes)
	{
		super(capacity, name, town, coordinates, isCharged, craftType);
		
		this.id = id;
		this.description = description;
		this.authorNickname = authorNickName;
		this.upvotes = upvotes;
		this.downvotes = downvotes;
	}
	
	/**
	 * 
	 * @param id the unique id in the remote storage.
	 * @param capacity the number of place of the parking (may be dynamic, ie available places).
	 * @param name the name of the parking.
	 * @param town the city where the parking is.
	 * @param coordinates the geographic coordinate of the parking.
	 * @param isCharged tells whether the parking is charged or not.
	 * @param craftType sky-opened or underground.
	 * @param description the description of the parking, given by the user.
	 * @param authorNickName the nickname of the user who added the parking tip.
	 */
	public UserTipParking(long id, int capacity, String name, String town,
			GeoCoordinate coordinates, boolean isCharged, CraftType craftType,
			String description, String authorNickName)
	{
		this(id, capacity, name, town,
				coordinates, isCharged, craftType, description, authorNickName, 0, 0);		

	}

	/**
	 * 
	 * @param capacity the number of place of the parking (may be dynamic, ie available places).
	 * @param name the name of the parking.
	 * @param town the city where the parking is.
	 * @param coordinates the geographic coordinate of the parking.
	 * @param isCharged tells whether the parking is charged or not.
	 * @param craftType sky-opened or underground.
	 * @param description the description of the parking, given by the user.
	 * @param authorNickName the nickname of the user who added the parking tip.
	 */
	public UserTipParking(int capacity, String name, String town,
			GeoCoordinate coordinates, boolean isCharged, CraftType craftType,
			String description, String authorNickName)
	{
		this(-1, capacity, name, town,
				coordinates, isCharged, craftType, description, authorNickName);
	}

	/**
	 * 
	 * @param capacity the number of place of the parking (may be dynamic, ie available places).
	 * @param name the name of the parking.
	 * @param town the city where the parking is.
	 * @param coordinates the geographic coordinate of the parking.
	 * @param isCharged tells whether the parking is charged or not.
	 * @param craftType sky-opened or underground.
	 */
	public UserTipParking(int capacity, String name, String town,
			GeoCoordinate coordinates, boolean isCharged, CraftType craftType)
	{
		this(-1, capacity, name, town,
				coordinates, isCharged, craftType, null, null);
	}
	
	/**
	 * Default contructor.
	 */
	public UserTipParking() 
	{
		super();
	}

	/**
	 * 
	 * @return the unique id in the remote storage.
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * 
	 * @param id the unique id in the remote storage.
	 */
	public void setId(long id)
	{
		this.id = id;
	}

	/**
	 * 
	 * @return the nickname of the user who added the parking tip.
	 */
	public String getAuthorNickname()
	{
		return authorNickname;
	}

	/**
	 * 
	 * @param authorNickName the nickname of the user who added the parking tip.
	 */
	public void setAuthorNickname(String authorNickname)
	{
		this.authorNickname = authorNickname;
	}

	/**
	 * 
	 * @return the description of the parking, given by the user.
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * 
	 * @param description the description of the parking, given by the user.
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * 
	 * @return the current value of the votes of the users.
	 */
	public long getVotes()
	{
		return votes;
	}

	/**
	 * 
	 * @return Current number of upvotes
	 */
	public long getUpvotes()
	{
		return upvotes;
	}
	
	/**
	 * 
	 * @return Current number of downvotes
	 */
	public long getDownvotes()
	{
		return downvotes;
	}

	
	/**
	 * 
	 * @return increases the note of the parking tip.
	 */
	public long upvote()
	{
		return ++ this.votes;
	}

	/**
	 * 
	 * @return decreases the note of the parking tip.
	 */
	public long downvote()
	{
		return -- this.votes;
	}
}
