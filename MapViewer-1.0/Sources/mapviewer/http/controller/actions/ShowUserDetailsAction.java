package mapviewer.http.controller.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mapviewer.http.controller.session.SessionManager;
import mapviewer.model.userfacade.delegate.UserFacadeDelegate;
import mapviewer.model.userprofile.to.UserProfileTO;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.udc.fbellas.j2ee.util.exceptions.InstanceNotFoundException;
import es.udc.fbellas.j2ee.util.exceptions.InternalErrorException;
import es.udc.fbellas.j2ee.util.struts.action.DefaultAction;

public class ShowUserDetailsAction extends DefaultAction {

    /*
     * (non-Javadoc)
     * 
     * @see es.udc.fbellas.j2ee.util.struts.action.DefaultAction#doExecute(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    @Override
    public ActionForward doExecute(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws InternalErrorException {
	/* Get initial data. */
	String userID = request.getParameter("userID");

	/* Do find user. */
	return doFindUser(mapping, userID, request);
    }

    private ActionForward doFindUser(ActionMapping mapping, String userID,
	    HttpServletRequest request) throws InternalErrorException {
	/* Find user. */
	UserFacadeDelegate userFacadeDelegate = SessionManager
		.getUserFacadeDelegate(request);

	UserProfileTO userProfileTO;
	try {
	    userProfileTO = userFacadeDelegate.findUser(userID);
	} catch (InstanceNotFoundException e) {
	    throw new InternalErrorException(e);
	}
	request.setAttribute("user", userProfileTO);

	request.setAttribute("mode", "users");

	/* Return ActionForward. */
	return mapping.findForward("ShowUserDetailsContent");
    }

}
