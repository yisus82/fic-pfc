package mapviewer.model.userfacade.exceptions;

/**
 * An exception thrown when an user is not authorized to do an action
 */
public class NotAuthorizedException extends Exception {

    public String action;

    /**
     * Constructs an exception with the text "You are not authorized to
     * <code>action</code>"
     * 
     * @param action
     *                the action the user is not authorized to do
     */
    public NotAuthorizedException(String action) {
	super("You are not authorized to " + action);
	this.action = action;
    }

    /**
     * @return the <code>action</code>
     */
    public String getAction() {
	return this.action;
    }

}
