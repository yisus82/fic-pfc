package mapviewer.model.userprofile.to;

import java.io.Serializable;

/**
 * A transfer object representing the information of an user profile details.
 */
public class UserProfileDetailsTO implements Serializable {

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
     * A constructor to create <code>UserProfileDetailsTO</code> objects
     * 
     * @param firstName
     *                the first name of the user
     * @param surname
     *                the surname of the user
     * @param email
     *                the e-mail of the user
     * @param localeID
     *                the identifier of the user's locale
     */
    public UserProfileDetailsTO(String firstName, String surname, String email,
	    String localeID) {
	this.firstName = firstName;
	this.surname = surname;
	this.email = email;
	this.localeID = localeID;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
	if (this == obj) return true;

	if (obj instanceof UserProfileDetailsTO) {
	    UserProfileDetailsTO details = (UserProfileDetailsTO) obj;
	    return (details.getEmail().equals(this.email)
		    && details.getFirstName().equals(this.firstName)
		    && details.getLocaleID().equals(this.localeID) && details
		    .getSurname().equals(this.surname));
	}

	return false;
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
     * @return the <code>localeID</code>
     */
    public String getLocaleID() {
	return this.localeID;
    }

    /**
     * @return the <code>surname</code>
     */
    public String getSurname() {
	return this.surname;
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
     * @param localeID
     *                the localeID to set
     */
    public void setLocaleID(String localeID) {
	this.localeID = localeID;
    }

    /**
     * @param surname
     *                the surname to set
     */
    public void setSurname(String surname) {
	this.surname = surname;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return ("\nE-mail = " + this.email + "\nFirst name = " + this.firstName
		+ "\nLocale ID = " + this.localeID + "\nSurname = "
		+ this.surname + "\n");
    }

}
