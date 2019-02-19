package mapviewer.model.tag.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Version;

import mapviewer.model.map.entity.Map;
import mapviewer.model.poi.entity.POI;

/**
 * An entity representing the information of a tag.
 */
@Entity
public class Tag {

    /**
     * The tag itself
     */
    private String tag;

    /**
     * Maps related with the tag
     */
    private List<Map> maps;

    /**
     * POIs related to the tag
     */
    private List<POI> POIs;

    /**
     * The version of the entity
     */
    private long version;

    /**
     * A default constructor to create <code>Tag</code> objects
     */
    public Tag() {
	maps = new ArrayList<Map>();
	POIs = new ArrayList<POI>();
    }

    /**
     * A constructor to create <code>Tag</code> objects.
     * 
     * @param tag
     *                the tag itself
     */
    public Tag(String tag) {
	this.tag = tag;
    }

    /**
     * @return the <code>maps</code>
     */
    @ManyToMany
    @JoinTable(name = "MapTag", joinColumns = @JoinColumn(name = "tag"), inverseJoinColumns = @JoinColumn(name = "mapID"))
    public List<Map> getMaps() {
	return this.maps;
    }

    /**
     * @return the <code>pOIs</code>
     */
    @ManyToMany
    @JoinTable(name = "POITag", joinColumns = @JoinColumn(name = "tag"), inverseJoinColumns = @JoinColumn(name = "POIID"))
    public List<POI> getPOIs() {
	return this.POIs;
    }

    /**
     * @return the <code>tag</code>
     */
    @Id
    public String getTag() {
	return this.tag;
    }

    /**
     * @return the <code>version</code>
     */
    @Version
    public long getVersion() {
	return this.version;
    }

    /**
     * @param maps
     *                the maps to set
     */
    public void setMaps(List<Map> maps) {
	this.maps = maps;
    }

    /**
     * @param is
     *                the pOIs to set
     */
    public void setPOIs(List<POI> is) {
	this.POIs = is;
    }

    /**
     * @param tag
     *                the tag to set
     */
    public void setTag(String tag) {
	this.tag = tag;
    }

    /**
     * @param version
     *                the version to set
     */
    public void setVersion(long version) {
	this.version = version;
    }

}
