package fr.univpau.paupark.model;

public class Parking {
	private int _numPlaces;
	private String _name;
	private String _commune;
	private double _lat;
	private double _lng;
	private boolean _free;
	private String _type;
	
	public Parking() {
		
	}

	/**
	 * @return the _numPlaces
	 */
	public int getNumPlaces() {
		return _numPlaces;
	}

	/**
	 * @param numPlaces the _numPlaces to set
	 */
	public void setNumPlaces(int numPlaces) {
		this._numPlaces = numPlaces;
	}

	/**
	 * @return the _name
	 */
	public String getName() {
		return _name;
	}

	/**
	 * @param name the _name to set
	 */
	public void setName(String name) {
		this._name = name;
	}

	/**
	 * @return the _commune
	 */
	public String getCommune() {
		return _commune;
	}

	/**
	 * @param commune the _commune to set
	 */
	public void setCommune(String commune) {
		this._commune = commune;
	}

	/**
	 * @return the _lat
	 */
	public double getLat() {
		return _lat;
	}

	/**
	 * @param lat the _lat to set
	 */
	public void setLat(double lat) {
		this._lat = lat;
	}

	/**
	 * @return the _lng
	 */
	public double getLng() {
		return _lng;
	}

	/**
	 * @param lng the _lng to set
	 */
	public void setLng(double lng) {
		this._lng = lng;
	}

	/**
	 * @return the _free
	 */
	public boolean isFree() {
		return _free;
	}

	/**
	 * @param free the _free to set
	 */
	public void setFree(boolean free) {
		this._free = free;
	}

	/**
	 * @return the _type
	 */
	public String getType() {
		return _type;
	}

	/**
	 * @param type the _type to set
	 */
	public void setType(String type) {
		this._type = type;
	}

}
