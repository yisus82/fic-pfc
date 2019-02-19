package mapviewer.model.userprofile.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import mapviewer.model.map.entity.Map;
import mapviewer.model.poi.entity.POI;
import mapviewer.model.wms.entity.WMS;

/**
 * An entity representing the information of an user.
 */
@Entity
public class UserProfile {

    /**
     * The user identifier
     */
    private String userID;

    /**
     * The password (normally encrypted) of the user
     */
    private String password;

    /**
     * The first name of the user
     */
    private String firstName;

    /**
     * The surname of the user
     */
    private String surname;

    /**
     * The e-mail of the user
     */
    private String email;

    /**
     * The identifier of the user's locale
     */
    private String localeID;

    /**
     * Maps owned by the user
     */
    private List<Map> maps;

    /**
     * WMSs owned by the user
     */
    private List<WMS> WMSs;

    /**
     * POIs owned to the user
     */
    private List<POI> POIs;

    /**
     * Maps the user is interested on
     */
    private List<Map> interestingMaps;

    /**
     * WMSs the user is interested on
     */
    private List<WMS> interestingWMSs;

    /**
     * POIs the user is interested on
     */
    private List<POI> interestingPOIs;

    /**
     * The version of the entity
     */
    private long version;

    /**
     * A default constructor to create <code>UserProfile</code> objects
     */
    public UserProfile() {
	maps = new ArrayList<Map>();
	WMSs = new ArrayList<WMS>();
	POIs = new ArrayList<POI>();
	interestingMaps = new ArrayList<Map>();
	interestingWMSs = new ArrayList<WMS>();
	interestingPOIs = new ArrayList<POI>();
    }

    /**
     * A constructor to create <code>UserProfile</code> objects
     * 
     * @param userID
     *                the user identifier
     * @param password
     *                the password (normally encrypted) of the user
     * @param firstName
     *                the first name of the user
     * @param surname
     *                the surname of the user
     * @param email
     *                the e-mail of the user
     * @param localeID
     *                the identifier of the user's locale
     */
    public UserProfile(String userID, String password, String firstName,
	    String surname, String email, String localeID) {
	this.userID = userID;
	this.password = password;
	this.firstName = firstName;
	this.surname = surname;
	this.email = email;
	this.localeID = localeID;
    }

    /**
     * @return the <code>email</code>
     */
    public String getEmail() {
	return this.email;
    }

    /**
     * @return the <code>firstName</code>
     */
    public String getFirstName() {
	return this.firstName;
    }

    /**
     * @return The interestingMaps
     */
    @ManyToMany
    @JoinTable(name = "InterestedUsersMap", joinColumns = @JoinColumn(name = "userID"), inverseJoinColumns = @JoinColumn(name = "mapID"))
    public List<Map> getInterestingMaps() {
	return this.interestingMaps;
    }

    /**
     * @return The interestingPOIs
     */
    @ManyToMany
    @JoinTable(name = "InterestedUsersPOI", joinColumns = @JoinColumn(name = "userID"), inverseJoinColumns = @JoinColumn(name = "POIID"))
    public List<POI> getInterestingPOIs() {
	return this.interestingPOIs;
    }

    /**
     * @return The interestingWMSs
     */
    @ManyToMany
    @JoinTable(name = "InterestedUsersWMS", joinColumns = @JoinColumn(name = "userID"), inverseJoinColumns = @JoinColumn(name = "WMSID"))
    public List<WMS> getInterestingWMSs() {
	return this.interestingWMSs;
    }

    /**
     * @return the <code>localeID</code>
     */
    public String getLocaleID() {
	return this.localeID;
    }

    /**
     * @return the <code>maps</code>
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    public List<Map> getMaps() {
	return this.maps;
    }

    /**
     * @return the <code>password</code>
     */
    public String getPassword() {
	return this.password;
    }

    /**
     * @return the <code>POIs</code>
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    public List<POI> getPOIs() {
	return this.POIs;
    }

    /**
     * @return the <code>surname</code>
     */
    public String getSurname() {
	return this.surname;
    }

    /**
     * @return the <code>userID</code>
     */
    @Id
    public String getUserID() {
	return this.userID;
    }

    /**
     * @return the <code>version</code>
     */
    @Version
    public long getVersion() {
	return this.version;
    }

    /**
     * @return the <code>WMSs</code>
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    public List<WMS> getWMSs() {
	return this.WMSs;
    }

    /**
     * @param email
     *                the email to set
     */
    public void setEmail(String email) {
	this.email = email;
    }

    /**
     * @param firstName
     *                the firstName to set
     */
    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    /**
     * @param interestingMaps
     *                The interestingMaps to set
     */
    public void setInterestingMaps(List<Map> interestingMaps) {
	this.interestingMaps = interestingMaps;
    }

    /**
     * @param interestingPOIs
     *                The interestingPOIs to set
     */
    public void setInterestingPOIs(List<POI> interestingPOIs) {
	this.interestingPOIs = interestingPOIs;
    }

    /**
     * @param interestingWMSs
     *                The interestingWMSs to set
     */
    public void setInterestingWMSs(List<WMS> interestingWMSs) {
	this.interestingWMSs = interestingWMSs;
    }

    /**
     * @param localeID
     *                the identifier of the user's locale
     */
    public void setLocaleID(String localeID) {
	this.localeID = localeID;
    }

    /**
     * @param maps
     *                the maps to set
     */
    public void setMaps(List<Map> maps) {
	this.maps = maps;
    }

    /**
     * @param password
     *                the password to set
     */
    public void setPassword(String password) {
	this.password = password;
    }

    /**
     * @param POIs
     *                the POIs to set
     */
    public void setPOIs(List<POI> POIs) {
	this.POIs = POIs;
    }

    /**
     * @param surname
     *                the surname to set
     */
    public void setSurname(String surname) {
	this.surname = surname;
    }

    /**
     * @param userID
     *                the userID to set
     */
    public void setUserID(String userID) {
	this.userID = userID;
    }

    /**
     * @param version
     *                the version to set
     */
    public void setVersion(long version) {
	this.version = version;
    }

    /**
     * @param WMSs
     *                the WMSs to set
     */
    public void setWMSs(List<WMS> WMSs) {
	this.WMSs = WMSs;
    }

}
