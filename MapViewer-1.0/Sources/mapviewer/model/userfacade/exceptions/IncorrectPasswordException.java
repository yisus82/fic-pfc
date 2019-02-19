package mapviewer.model.userfacade.exceptions;

import es.udc.fbellas.j2ee.util.exceptions.ModelException;

/**
 * An exception thrown when a bad password is found when asked
 */
public class IncorrectPasswordException extends ModelException {

    /**
     * The user identifier
     */
    private String userID;

    /**
     * Constructs a new exception with the message "Incorrect password exception =>
     * userID = <code>userID</code>"
     * 
     * @param userID
     *                the user identifier
     */
    public IncorrectPasswordException(String userID) {
	super("Incorrect password exception => userID = " + userID);
	this.userID = userID;
    }

    /**
     * @return the <code>userID</code>
     */
    public String getUserID() {
	return this.userID;
    }

}
