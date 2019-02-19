package mapviewer.model.wms.to;

import java.io.Serializable;

/**
 * A transfer object representing the information of a WMS.
 */
public class WMSTO implements Serializable {

    /**
     * The identifier of the WMS
     */
    private Long WMSID;

    /**
     * The name of the WMS
     */
    private String name;

    /**
     * The description of the WMS
     */
    private String description;

    /**
     * The URL of the WMS
     */
    private String URL;

    /**
     * The identifier of the WMS' owner
     */
    private String userID;

    /**
     * A constructor to create <code>WMSTO</code> objects
     * 
     * @param WMSID
     *                the identifier of the WMS
     * @param description
     *                the description of the WMS
     * @param name
     *                the name of the WMS
     * @param URL
     *                the URL of the WMS
     * @param userID
     *                the identifier of the WMS' owner
     */
    public WMSTO(Long WMSID, String name, String description, String URL,
	    String userID) {
	this.WMSID = WMSID;
	this.name = name;
	this.description = description;
	this.URL = URL;
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

	if (obj instanceof WMSTO) {
	    WMSTO WMS = (WMSTO) obj;
	    return (WMS.getDescription().equals(this.description)
		    && WMS.getName().equals(this.name)
		    && WMS.getURL().equals(this.URL)
		    && WMS.getUserID().equals(this.userID) && WMS.getWMSID()
		    .equals(this.WMSID));
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
     * @return the <code>name</code>
     */
    public String getName() {
	return this.name;
    }

    /**
     * @return the <code>URL</code>
     */
    public String getURL() {
	return this.URL;
    }

    /**
     * @return the <code>userID</code>
     */
    public String getUserID() {
	return this.userID;
    }

    /**
     * @return the <code>WMSID</code>
     */
    public Long getWMSID() {
	return this.WMSID;
    }

    /**
     * @param description
     *                the description to set
     */
    public void setDescription(String description) {
	this.description = description;
    }

    /**
     * @param name
     *                the name to set
     */
    public void setName(String name) {
	this.name = name;
    }

    /**
     * @param URL
     *                the URL to set
     */
    public void setURL(String URL) {
	this.URL = URL;
    }

    /**
     * @param userID
     *                the userID to set
     */
    public void setUserID(String userID) {
	this.userID = userID;
    }

    /**
     * @param WMSID
     *                the WMSID to set
     */
    public void setWMSID(Long WMSID) {
	this.WMSID = WMSID;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return ("\nWMSID = " + this.WMSID + "\nName = " + this.name
		+ "\nDescription = " + this.description + "\nURL = " + this.URL
		+ "\nUser ID = " + this.userID + "\n");
    }

}
