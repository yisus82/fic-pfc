package mapviewer.model.poi.to;

/**
 * A transfer object representing the information of a POI.
 */
public class POITO {

    /**
     * The identifier of the POI
     */
    private Long POIID;

    /**
     * The name of the POI
     */
    private String name;

    /**
     * The description of the POI
     */
    private String description;

    /**
     * The latitude of the POI
     */
    private Double latitude;

    /**
     * The longitude of the POI
     */
    private Double longitude;

    /**
     * The HTML of the POI
     */
    private String HTML;

    /**
     * The identifier of the POI's owner
     */
    private String userID;

    /**
     * A constructor to create <code>POITO</code> objects
     * 
     * @param POIID
     *                the identifier of the POI
     * @param name
     *                the name of the POI
     * @param description
     *                the description of the POI
     * @param latitude
     *                the latitude of the POI
     * @param longitude
     *                the longitude of the POI
     * @param HTML
     *                the HTML of the POI
     * @param userID
     *                the identifier of the POI's owner
     */
    public POITO(Long POIID, String name, String description, Double latitude,
	    Double longitude, String HTML, String userID) {
	this.POIID = POIID;
	this.name = name;
	this.description = description;
	this.latitude = latitude;
	this.longitude = longitude;
	this.HTML = HTML;
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

	if (obj instanceof POITO) {
	    POITO POI = (POITO) obj;
	    return (POI.getPOIID().equals(this.POIID)
		    && POI.getLatitude().equals(this.latitude)
		    && POI.getLongitude().equals(this.longitude)
		    && POI.getDescription().equals(this.description)
		    && POI.getHTML().equals(this.HTML)
		    && POI.getName().equals(this.name) && POI.getUserID()
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
     * @return the <code>HTML</code>
     */
    public String getHTML() {
	return this.HTML;
    }

    /**
     * @return the <code>latitude</code>
     */
    public Double getLatitude() {
	return this.latitude;
    }

    /**
     * @return the <code>longitude</code>
     */
    public Double getLongitude() {
	return this.longitude;
    }

    /**
     * @return the <code>name</code>
     */
    public String getName() {
	return this.name;
    }

    /**
     * @return the <code>POIID</code>
     */
    public Long getPOIID() {
	return this.POIID;
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
     * @param HTML
     *                the HTML to set
     */
    public void setHTML(String HTML) {
	this.HTML = HTML;
    }

    /**
     * @param latitude
     *                the latitude to set
     */
    public void setLatitude(Double latitude) {
	this.latitude = latitude;
    }

    /**
     * @param longitude
     *                the longitude to set
     */
    public void setLongitude(Double longitude) {
	this.longitude = longitude;
    }

    /**
     * @param name
     *                the name to set
     */
    public void setName(String name) {
	this.name = name;
    }

    /**
     * @param POIID
     *                the POIID to set
     */
    public void setPOIID(Long POIID) {
	this.POIID = POIID;
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
	return ("\nPOI ID = " + this.POIID + "\nName = " + this.name
		+ "\nDescription = " + this.description + "\nLatitude = "
		+ this.latitude + "\nLongitude = " + this.longitude
		+ "\nHTML = " + this.HTML + "\nUser ID = " + this.userID + "\n");
    }

}
