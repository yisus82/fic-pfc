package mapviewer.model.searchfacade.to;

import mapviewer.model.wms.to.WMSTO;

/**
 * A transfer object representing the information of a layer.
 */
public class LayerDetailsTO {
    /**
     * The identifier of the layer
     */
    private Long layerID;

    /**
     * The title of the layer
     */
    private String title;

    /**
     * The name of the layer
     */
    private String name;

    /**
     * The title of the layer's style
     */
    private String styleTitle;

    /**
     * The name of the layer's style
     */
    private String styleName;

    /**
     * The minimum latitude of the layer
     */
    private Double minLatitude;

    /**
     * The minimum longitude of the layer
     */
    private Double minLongitude;

    /**
     * The maximum latitude of the layer
     */
    private Double maxLatitude;

    /**
     * The maximum longitude of the layer
     */
    private Double maxLongitude;

    /**
     * The WMS the layer belongs to
     */
    private WMSTO WMS;

    /**
     * A constructor to create <code>LayerDetailsTO</code> objects.
     * 
     * @param layerID
     *                the identifier of the layer
     * @param title
     *                the title of the layer
     * @param name
     *                the name of the layer
     * @param styleTitle
     *                the title of the layer's style
     * @param styleName
     *                the name of the layer's style
     * @param minLatitude
     *                the minimum latitude of the layer
     * @param minLongitude
     *                the minimum longitude of the layer
     * @param maxLatitude
     *                the maximum latitude of the layer
     * @param maxLongitude
     *                the maximum longitude of the layer
     * @param WMS
     *                the WMS the layer belongs to
     */
    public LayerDetailsTO(Long layerID, String title, String name,
	    String styleTitle, String styleName, Double minLatitude,
	    Double minLongitude, Double maxLatitude, Double maxLongitude,
	    WMSTO WMS) {
	this.layerID = layerID;
	this.title = title;
	this.name = name;
	this.styleTitle = styleTitle;
	this.styleName = styleName;
	this.minLatitude = minLatitude;
	this.minLongitude = minLongitude;
	this.maxLatitude = maxLatitude;
	this.maxLongitude = maxLongitude;
	this.WMS = WMS;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
	if (this == obj) return true;

	if (obj instanceof LayerDetailsTO) {
	    LayerDetailsTO layer = (LayerDetailsTO) obj;
	    return (layer.getLayerID().equals(this.layerID)
		    && layer.getMaxLatitude().equals(this.maxLatitude)
		    && layer.getMaxLongitude().equals(this.maxLongitude)
		    && layer.getMinLatitude().equals(this.minLatitude)
		    && layer.getMinLongitude().equals(this.minLongitude)
		    && layer.getName().equals(this.name)
		    && layer.getStyleName().equals(this.styleName)
		    && layer.getStyleTitle().equals(this.styleTitle)
		    && layer.getTitle().equals(this.title) && layer.getWMS()
		    .equals(this.WMS));
	}

	return false;
    }

    /**
     * @return the <code>layerID</code>
     */
    public Long getLayerID() {
	return this.layerID;
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
     * @return the <code>styleName</code>
     */
    public String getStyleName() {
	return this.styleName;
    }

    /**
     * @return the <code>styleTitle</code>
     */
    public String getStyleTitle() {
	return this.styleTitle;
    }

    /**
     * @return the <code>title</code>
     */
    public String getTitle() {
	return this.title;
    }

    /**
     * @return the <code>WMS</code>
     */
    public WMSTO getWMS() {
	return this.WMS;
    }

    /**
     * @param layerID
     *                the layerID to set
     */
    public void setLayerID(Long layerID) {
	this.layerID = layerID;
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
     * @param styleName
     *                the styleName to set
     */
    public void setStyleName(String styleName) {
	this.styleName = styleName;
    }

    /**
     * @param styleTitle
     *                the styleTitle to set
     */
    public void setStyleTitle(String styleTitle) {
	this.styleTitle = styleTitle;
    }

    /**
     * @param title
     *                the title to set
     */
    public void setTitle(String title) {
	this.title = title;
    }

    /**
     * @param WMS
     *                the WMS to set
     */
    public void setWMSTO(WMSTO WMS) {
	this.WMS = WMS;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return ("\nLayer ID = " + this.layerID + "\nTitle = " + this.title
		+ "\nName = " + this.name + "\nStyle title = "
		+ this.styleTitle + "\nStyle name = " + this.styleName
		+ "\nMin Latitude = " + this.minLatitude + "\nMin Longitude = "
		+ this.minLongitude + "\nMax Latitude = " + this.maxLatitude
		+ "\nMax Longitude = " + this.maxLongitude + "\nWMS = "
		+ this.WMS + "\n");
    }

}
