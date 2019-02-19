package mapviewer.model.userfacade.to;

import java.io.Serializable;
import java.util.Locale;

/**
 * A custom transfer object representing the relevant data of an user.
 */
public class LoginResultTO implements Serializable {

    /**
     * The user identifier
     */
    private String userID;

    /**
     * The encrypted password of the user
     */
    private String password;

    /**
     * The locale of the user
     */
    private Locale locale;

    /**
     * A constructor to create <code>LoginResultTO</code> objects
     * 
     * @param userID
     *                the user identifier
     * @param password
     *                the encrypted password of the user
     * @param locale
     *                the locale of the user
     */
    public LoginResultTO(String userID, String password, Locale locale) {
	this.userID = userID;
	this.password = password;
	this.locale = locale;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
	if (this == object) return true;

	if (object instanceof LoginResultTO) {
	    LoginResultTO result = (LoginResultTO) object;
	    return (result.getLocale().equals(this.locale)
		    && result.getPassword().equals(this.password) && result
		    .getUserID().equals(this.userID));
	}

	return false;
    }

    /**
     * @return the <code>locale</code>
     */
    public Locale getLocale() {
	return this.locale;
    }

    /**
     * @return the <code>password</code>
     */
    public String getPassword() {
	return this.password;
    }

    /**
     * @return the <code>userID</code>
     */
    public String getUserID() {
	return this.userID;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return ("\nLocale = " + this.locale + "\nUser ID = " + this.userID
		+ "\nPassword = " + this.password + "\n");
    }

}
