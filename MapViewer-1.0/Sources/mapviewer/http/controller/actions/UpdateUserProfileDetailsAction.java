package mapviewer.http.controller.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mapviewer.http.controller.session.SessionManager;
import mapviewer.model.userprofile.to.UserProfileDetailsTO;
import mapviewer.model.userprofile.to.UserProfileTO;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import es.udc.fbellas.j2ee.util.exceptions.InstanceNotFoundException;
import es.udc.fbellas.j2ee.util.exceptions.InternalErrorException;
import es.udc.fbellas.j2ee.util.struts.action.DefaultAction;

public class UpdateUserProfileDetailsAction extends DefaultAction {

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
	DynaActionForm userProfileForm = (DynaActionForm) form;
	UserProfileDetailsTO userProfileDetailsTO = new UserProfileDetailsTO(
		(String) userProfileForm.get("firstName"),
		(String) userProfileForm.get("surname"),
		(String) userProfileForm.get("email"), (String) userProfileForm
			.get("localeID"));
	String userID = SessionManager.getUserID(request);
	UserProfileTO userProfileTO;
	try {
	    userProfileTO = SessionManager.getUserFacadeDelegate(request)
		    .findUser(userID);
	} catch (InstanceNotFoundException e) {
	    throw new InternalErrorException(e);
	}
	userProfileTO.setUserID(userID);
	userProfileTO.setUserDetails(userProfileDetailsTO);

	/* Update user profile details. */
	ActionMessages errors = new ActionMessages();

	SessionManager.updateUserProfileDetails(request, userProfileTO);

	request.setAttribute("mode", "users");

	/* Return ActionForward. */
	if (errors.isEmpty()) {
	    return mapping.findForward("MainPage");
	}
	saveErrors(request, errors);
	return new ActionForward(mapping.getInput());
    }

}
