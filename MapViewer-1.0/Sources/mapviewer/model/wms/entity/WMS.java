package mapviewer.model.wms.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import mapviewer.model.layer.entity.Layer;
import mapviewer.model.userprofile.entity.UserProfile;

/**
 * An entity representing the information of a WMS.
 */
@Entity
public class WMS {

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
    private UserProfile user;

    /**
     * Users interested on the WMS
     */
    private List<UserProfile> interestedUsers;

    /**
     * The layers related to the WMS
     */
    private List<Layer> layers;

    /**
     * The version of the entity
     */
    private long version;

    /**
     * A default constructor to create <code>WMS</code> objects
     */
    public WMS() {
	interestedUsers = new ArrayList<UserProfile>();
	layers = new ArrayList<Layer>();
    }

    /**
     * A constructor to create <code>WMS</code> objects
     * 
     * @param description
     *                the description of the WMS
     * @param name
     *                the name of the WMS
     * @param URL
     *                the URL of the WMS
     * @param user
     *                the WMS' owner
     */
    public WMS(String name, String description, String URL, UserProfile user) {
	/**
	 * NOTE: "WMSID" *must* be left as "null" since its value is
	 * automatically generated (otherwise, "EntityManager.persist" may throw
	 * an exception indicating the entity to persist is detached).
	 */
	this.name = name;
	this.description = description;
	this.URL = URL;
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
    @JoinTable(name = "InterestedUsersWMS", joinColumns = @JoinColumn(name = "WMSID"), inverseJoinColumns = @JoinColumn(name = "userID"))
    public List<UserProfile> getInterestedUsers() {
	return this.interestedUsers;
    }

    /**
     * @return the <code>layers</code>
     */
    @OneToMany(mappedBy = "WMS", cascade = CascadeType.ALL)
    public List<Layer> getLayers() {
	return this.layers;
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
     * @return the <code>WMSID</code>
     */
    @SequenceGenerator(name = "WMSIdentifierGenerator", sequenceName = "WMSSeq")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "WMSIdentifierGenerator")
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

    /**
     * @param WMSID
     *                the WMSID to set
     */
    public void setWMSID(Long WMSID) {
	this.WMSID = WMSID;
    }
}
