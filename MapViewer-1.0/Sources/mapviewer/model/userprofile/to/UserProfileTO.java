package mapviewer.model.userprofile.to;

import java.io.Serializable;

/**
 * A transfer object representing the information of an user.
 */
public class UserProfileTO implements Serializable {

    /**
     * The user identifier
     */
    private String userID;

    /**
     * The password (normally encrypted) of the user
     */
    private String password;

    /**
     * The details of the user
     */
    private UserProfileDetailsTO userDetails;

    /**
     * A constructor to create <code>UserProfileTO</code> objects
     * 
     * @param userID
     *                the user identifier
     * @param password
     *                the password (normally encrypted) of the user
     * @param userDetails
     *                the details of the user
     */
    public UserProfileTO(String userID, String password,
	    UserProfileDetailsTO userDetails) {
	this.userID = userID;
	this.password = password;
	this.userDetails = userDetails;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
	if (this == obj) return true;

	if (obj instanceof UserProfileTO) {
	    UserProfileTO user = (UserProfileTO) obj;
	    return (user.getUserID().equals(this.userID)
		    && user.getPassword().equals(this.password) && user
		    .getUserDetails().equals(this.userDetails));
	}

	return false;
    }

    /**
     * @return the <code>password</code>
     */
    public String getPassword() {
	return this.password;
    }

    /**
     * @return the <code>userDetails</code>
     */
    public UserProfileDetailsTO getUserDetails() {
	return this.userDetails;
    }

    /**
     * @return the <code>userID</code>
     */
    public String getUserID() {
	return this.userID;
    }

    /**
     * @param password
     *                the password to set
     */
    public void setPassword(String password) {
	this.password = password;
    }

    /**
     * @param userDetails
     *                the userDetails to set
     */
    public void setUserDetails(UserProfileDetailsTO userDetails) {
	this.userDetails = userDetails;
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
	return ("\nUserID = " + this.userID + "\nPassword = " + this.password
		+ "\nUser details = " + this.userDetails + "\n");
    }

}
