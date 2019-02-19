package mapviewer.model.map.entity;

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

import mapviewer.model.layer.entity.Layer;
import mapviewer.model.tag.entity.Tag;
import mapviewer.model.userprofile.entity.UserProfile;

/**
 * An entity representing the information of a map.
 */
@Entity
public class Map {

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
     * The map's owner
     */
    private UserProfile user;

    /**
     * Users interested on the map
     */
    private List<UserProfile> interestedUsers;

    /**
     * Layers related to the map
     */
    private List<Layer> layers;

    /**
     * Tags related to the map
     */
    private List<Tag> tags;

    /**
     * The version of the entity
     */
    private long version;

    /**
     * A default constructor to create <code>Map</code> objects
     */
    public Map() {
	interestedUsers = new ArrayList<UserProfile>();
	layers = new ArrayList<Layer>();
	tags = new ArrayList<Tag>();
    }

    /**
     * A constructor to create <code>Map</code> objects
     * 
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
     * @param user
     *                the map's owner
     */
    public Map(String name, String description, Double minLatitude,
	    Double minLongitude, Double maxLatitude, Double maxLongitude,
	    UserProfile user) {
	/**
	 * NOTE: "mapID" *must* be left as "null" since its value is
	 * automatically generated (otherwise, "EntityManager.persist" may throw
	 * an exception indicating the entity to persist is detached).
	 */
	this.name = name;
	this.description = description;
	this.minLatitude = minLatitude;
	this.minLongitude = minLongitude;
	this.maxLatitude = maxLatitude;
	this.maxLongitude = maxLongitude;
	this.user = user;
    }

    /**
     * @return the <code>description</code>
     */
    public String getDescription() {
	return this.description;
    }

    /**
     * @return the <code>interestedUsers</code>
     */
    @ManyToMany
    @JoinTable(name = "InterestedUsersMap", joinColumns = @JoinColumn(name = "mapID"), inverseJoinColumns = @JoinColumn(name = "userID"))
    public List<UserProfile> getInterestedUsers() {
	return this.interestedUsers;
    }

    /**
     * @return the <code>layers</code>
     */
    @ManyToMany
    @JoinTable(name = "LayerMap", joinColumns = @JoinColumn(name = "mapID"), inverseJoinColumns = @JoinColumn(name = "layerID"))
    public List<Layer> getLayers() {
	return this.layers;
    }

    /**
     * @return the <code>mapID</code>
     */
    @SequenceGenerator(name = "MapIdentifierGenerator", sequenceName = "MapSeq")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "MapIdentifierGenerator")
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
     * @return the <code>tags</code>
     */
    @ManyToMany
    @JoinTable(name = "MapTag", joinColumns = @JoinColumn(name = "mapID"), inverseJoinColumns = @JoinColumn(name = "tag"))
    public List<Tag> getTags() {
	return this.tags;
    }

    /**
     * @return the <code>user</code>
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "userID")
    public UserProfile getUser() {
	return this.user;
    }

    /**
     * @return the <code>version</code>
     */
    @Version
    public long getVersion() {
	return this.version;
    }

    /**
     * @param description
     *                the description to set
     */
    public void setDescription(String description) {
	this.description = description;
    }

    /**
     * @param interestedUsers
     *                the interestedUsers to set
     */
    public void setInterestedUsers(List<UserProfile> interestedUsers) {
	this.interestedUsers = interestedUsers;
    }

    /**
     * @param layers
     *                the layers to set
     */
    public void setLayers(List<Layer> layers) {
	this.layers = layers;
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
     * @param tags
     *                the tags to set
     */
    public void setTags(List<Tag> tags) {
	this.tags = tags;
    }

    /**
     * @param user
     *                the user to set
     */
    public void setUser(UserProfile user) {
	this.user = user;
    }

    /**
     * @param version
     *                the version to set
     */
    public void setVersion(long version) {
	this.version = version;
    }

}
