package mapviewer.model.map.to;

/**
 * A transfer object representing the information of a map.
 */
public class MapTO {

    /**
     * The identifier of the map
     */
    private Long mapID;

    /**
     * The name of the map
     */
    private String name;

    /**
     * The description of the map
     */
    private String description;

    /**
     * The minimum latitude of the map
     */
    private Double minLatitude;

    /**
     * The minimum longitude of the map
     */
    private Double minLongitude;

    /**
     * The maximum latitude of the map
     */
    private Double maxLatitude;

    /**
     * The maximum longitude of the map
     */
    private Double maxLongitude;

    /**
     * The identifier of the map's owner
     */
    private String userID;

    /**
     * A constructor to create <code>MapTO</code> objects
     * 
     * @param mapID
     *                the identifier of the map
     * @param description
     *                the description of the map
     * @param name
     *                the name of the map
     * @param minLatitude
     *                the minimum latitude of the map
     * @param minLongitude
     *                the minimum longitude of the map
     * @param maxLatitude
     *                the maximum latitude of the map
     * @param maxLongitude
     *                the maximum longitude of the map
     * @param userID
     *                the identifier of the map's owner
     */
    public MapTO(Long mapID, String name, String description,
	    Double minLatitude, Double minLongitude, Double maxLatitude,
	    Double maxLongitude, String userID) {
	this.mapID = mapID;
	this.name = name;
	this.description = description;
	this.minLatitude = minLatitude;
	this.minLongitude = minLongitude;
	this.maxLatitude = maxLatitude;
	this.maxLongitude = maxLongitude;
	this.userID = userID;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
	if (this == obj) return true;

	if (obj instanceof MapTO) {
	    MapTO map = (MapTO) obj;
	    return (map.getDescription().equals(this.description)
		    && map.getMapID().equals(this.mapID)
		    && map.getMaxLatitude().equals(this.maxLatitude)
		    && map.getMaxLongitude().equals(this.maxLongitude)
		    && map.getMinLatitude().equals(this.minLatitude)
		    && map.getMinLongitude().equals(this.minLongitude)
		    && map.getName().equals(this.name) && map.getUserID()
		    .equals(this.userID));
	}

	return false;
    }

    /**
     * @return the <code>description</code>
     */
    public String getDescription() {
	return this.description;
    }

    /**
     * @return the <code>mapID</code>
     */
    public Long getMapID() {
	return this.mapID;
    }

    /**
     * @return the <code>maxLatitude</code>
     */
    public Double getMaxLatitude() {
	return this.maxLatitude;
    }

    /**
     * @return the <code>maxLongitude</code>
     */
    public Double getMaxLongitude() {
	return this.maxLongitude;
    }

    /**
     * @return the <code>minLatitude</code>
     */
    public Double getMinLatitude() {
	return this.minLatitude;
    }

    /**
     * @return the <code>minLongitude</code>
     */
    public Double getMinLongitude() {
	return this.minLongitude;
    }

    /**
     * @return the <code>name</code>
     */
    public String getName() {
	return this.name;
    }

    /**
     * @return the <code>userID</code>
     */
    public String getUserID() {
	return this.userID;
    }

    /**
     * @param description
     *                the description to set
     */
    public void setDescription(String description) {
	this.description = description;
    }

    /**
     * @param mapID
     *                the mapID to set
     */
    public void setMapID(Long mapID) {
	this.mapID = mapID;
    }

    /**
     * @param maxLatitude
     *                the maxLatitude to set
     */
    public void setMaxLatitude(Double maxLatitude) {
	this.maxLatitude = maxLatitude;
    }

    /**
     * @param maxLongitude
     *                the maxLongitude to set
     */
    public void setMaxLongitude(Double maxLongitude) {
	this.maxLongitude = maxLongitude;
    }

    /**
     * @param minLatitude
     *                the minLatitude to set
     */
    public void setMinLatitude(Double minLatitude) {
	this.minLatitude = minLatitude;
    }

    /**
     * @param minLongitude
     *                the minLongitude to set
     */
    public void setMinLongitude(Double minLongitude) {
	this.minLongitude = minLongitude;
    }

    /**
     * @param name
     *                the name to set
     */
    public void setName(String name) {
	this.name = name;
    }

    /**
     * @param userID
     *                the userID to set
     */
    public void setUserID(String userID) {
	this.userID = userID;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return ("\nMap ID = " + this.mapID + "\nName = " + this.name
		+ "\nDescription = " + this.description + "\nMin Latitude = "
		+ this.minLatitude + "\nMin Longitude = " + this.minLongitude
		+ "\nMax Latitude = " + this.maxLatitude + "\nMax Longitude = "
		+ this.maxLongitude + "\nUser ID = " + this.userID + "\n");
    }

}
