package mapviewer.model.layer.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import mapviewer.model.map.entity.Map;
import mapviewer.model.wms.entity.WMS;

/**
 * An entity representing the information of a layer.
 */
@Entity
public class Layer {
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
    private WMS WMS;

    /**
     * Maps related to the layer
     */
    private List<Map> maps;

    /**
     * The version of the entity
     */
    private long version;

    /**
     * A constructor to create <code>Layer</code> objects.
     */
    public Layer() {
	maps = new ArrayList<Map>();
    }

    /**
     * A constructor to create <code>Layer</code> objects.
     * 
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
    public Layer(String title, String name, String styleTitle,
	    String styleName, Double minLatitude, Double minLongitude,
	    Double maxLatitude, Double maxLongitude, WMS WMS) {
	/**
	 * NOTE: "layerID" *must* be left as "null" since its value is
	 * automatically generated (otherwise, "EntityManager.persist" may throw
	 * an exception indicating the entity to persist is detached).
	 */
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

    /**
     * @return the <code>layerID</code>
     */
    @SequenceGenerator(name = "LayerIdentifierGenerator", sequenceName = "LayerSeq")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "LayerIdentifierGenerator")
    public Long getLayerID() {
	return this.layerID;
    }

    /**
     * @return the <code>maps</code>
     */
    @ManyToMany
    @JoinTable(name = "LayerMap", joinColumns = @JoinColumn(name = "layerID"), inverseJoinColumns = @JoinColumn(name = "mapID"))
    public List<Map> getMaps() {
	return this.maps;
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
     * @return the <code>version</code>
     */
    @Version
    public long getVersion() {
	return this.version;
    }

    /**
     * @return the <code>WMS</code>
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "WMSID")
    public WMS getWMS() {
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
     * @param maps
     *                the maps to set
     */
    public void setMaps(List<Map> maps) {
	this.maps = maps;
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
     * @param version
     *                the version to set
     */
    public void setVersion(long version) {
	this.version = version;
    }

    /**
     * @param wms
     *                the wMS to set
     */
    public void setWMS(WMS wms) {
	this.WMS = wms;
    }

}
