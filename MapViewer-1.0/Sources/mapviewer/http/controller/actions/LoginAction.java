package mapviewer.http.controller.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mapviewer.http.controller.session.SessionManager;
import mapviewer.model.userfacade.exceptions.IncorrectPasswordException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import es.udc.fbellas.j2ee.util.exceptions.InstanceNotFoundException;
import es.udc.fbellas.j2ee.util.exceptions.InternalErrorException;
import es.udc.fbellas.j2ee.util.struts.action.DefaultAction;

public class LoginAction extends DefaultAction {

    /*
     * (non-Javadoc)
     * 
     * @see es.udc.fbellas.j2ee.util.struts.action.DefaultAction#doExecute(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected ActionForward doExecute(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws InternalErrorException {
	/* Get data. */
	DynaActionForm loginForm = (DynaActionForm) form;
	String userID = (String) loginForm.get("userID");
	String password = (String) loginForm.get("password");
	boolean rememberMyPassword = loginForm.get("rememberMyPassword") != null;

	/* Do login. */
	ActionMessages errors = new ActionMessages();

	try {

	    SessionManager.login(request, response, userID, password,
		    rememberMyPassword);

	} catch (InstanceNotFoundException e) {
	    errors.add("userID", new ActionMessage(
		    "ErrorMessages.userID.notFound"));
	} catch (IncorrectPasswordException e) {
	    errors.add("password", new ActionMessage(
		    "ErrorMessages.password.incorrect"));
	}

	/* Return ActionForward. */
	if (errors.isEmpty()) {
	    return mapping.findForward("Home");
	}
	saveErrors(request, errors);
	return new ActionForward(mapping.getInput());
    }

}
