package mapviewer.model.poi.entity;

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

import mapviewer.model.tag.entity.Tag;
import mapviewer.model.userprofile.entity.UserProfile;

/**
 * An entity representing the information of a POI.
 */
@Entity
public class POI {

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
     * The POI's owner
     */
    private UserProfile user;

    /**
     * Users interested on the map
     */
    private List<UserProfile> interestedUsers;

    /**
     * Tags related to the POI
     */
    private List<Tag> tags;

    /**
     * The version of the entity
     */
    private long version;

    /**
     * A default constructor to create <code>POI</code> objects
     */
    public POI() {
	interestedUsers = new ArrayList<UserProfile>();
	tags = new ArrayList<Tag>();
    }

    /**
     * A constructor to create <code>POITO</code> objects
     * 
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
     * @param user
     *                the POI's owner
     */
    public POI(String name, String description, Double latitude,
	    Double longitude, String HTML, UserProfile user) {
	/**
	 * NOTE: "POIID" *must* be left as "null" since its value is
	 * automatically generated (otherwise, "EntityManager.persist" may throw
	 * an exception indicating the entity to persist is detached).
	 */
	this.name = name;
	this.description = description;
	this.latitude = latitude;
	this.longitude = longitude;
	this.HTML = HTML;
	this.user = user;
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
     * @return the <code>interestedUsers</code>
     */
    @ManyToMany
    @JoinTable(name = "InterestedUsersPOI", joinColumns = @JoinColumn(name = "POIID"), inverseJoinColumns = @JoinColumn(name = "userID"))
    public List<UserProfile> getInterestedUsers() {
	return this.interestedUsers;
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
    @SequenceGenerator(name = "POIIdentifierGenerator", sequenceName = "POISeq")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "POIIdentifierGenerator")
    public Long getPOIID() {
	return this.POIID;
    }

    /**
     * @return the <code>tags</code>
     */
    @ManyToMany
    @JoinTable(name = "POITag", joinColumns = @JoinColumn(name = "POIID"), inverseJoinColumns = @JoinColumn(name = "tag"))
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
     * @param HTML
     *                the HTML to set
     */
    public void setHTML(String HTML) {
	this.HTML = HTML;
    }

    /**
     * @param interestedUsers
     *                the interestedUsers to set
     */
    public void setInterestedUsers(List<UserProfile> interestedUsers) {
	this.interestedUsers = interestedUsers;
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
