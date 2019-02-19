package mapviewer.http.controller.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mapviewer.model.searchfacade.delegate.SearchFacadeDelegate;
import mapviewer.model.searchfacade.delegate.SearchFacadeDelegateFactory;
import mapviewer.model.searchfacade.to.UserProfileChunkTO;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import es.udc.fbellas.j2ee.util.exceptions.InternalErrorException;
import es.udc.fbellas.j2ee.util.struts.action.DefaultAction;

public class FindUsersByUserIDAction extends DefaultAction {

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
	DynaActionForm findByUserIDForm = (DynaActionForm) form;

	String userID;
	int startIndex;
	int count;

	try {
	    startIndex = new Integer(request.getParameter("startIndex"));
	    count = new Integer(request.getParameter("count"));
	} catch (Exception e) {
	    startIndex = 1;
	    count = 5;
	}

	userID = request.getParameter("userID");
	if ((userID == "") || (userID == null))
	    userID = (String) findByUserIDForm.get("userID");
	else
	    findByUserIDForm.set("userID", userID);

	request.setAttribute("startIndex", startIndex);
	request.setAttribute("count", count);

	/* Do find users by userID. */
	return doFindUsersByUserID(mapping, userID, startIndex, count, request);
    }

    private ActionForward doFindUsersByUserID(ActionMapping mapping,
	    String userID, int startIndex, int count, HttpServletRequest request)
	    throws InternalErrorException {
	/* Find users by userID. */
	SearchFacadeDelegate searchFacadeDelegate = SearchFacadeDelegateFactory
		.getDelegate();

	UserProfileChunkTO users;

	if ((userID == null) || (userID.equals("")))
	    users = searchFacadeDelegate.findUsers(startIndex, count);
	else
	    users = searchFacadeDelegate.findUsersByUserID(userID, startIndex,
		    count);

	if (users.getUserProfileTOs().size() > 0) {
	    request.setAttribute("users", users.getUserProfileTOs());

	    if (users.getExistMoreUserProfiles())
		request.setAttribute("next", startIndex + count);

	    if ((startIndex - count) > 1)
		request.setAttribute("previous", startIndex - count);
	    else if (startIndex > 1) request.setAttribute("previous", 1);
	}

	request.setAttribute("mode", "users");

	/* Return ActionForward. */
	return mapping.findForward("FindUsersByUserIDContent");
    }

}
