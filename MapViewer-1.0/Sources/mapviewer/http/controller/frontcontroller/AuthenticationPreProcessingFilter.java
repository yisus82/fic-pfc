package mapviewer.http.controller.frontcontroller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mapviewer.http.controller.session.SessionManager;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * A filter to check if the action to be executed requires that the user had
 * been authenticated. If the user has not been authenticated and the action
 * requires it, <code>doProcess</code> returns the <code>ActionForward</code>
 * returned by <code>mapping.findForward("AuthenticationPage")</code>.
 */
public class AuthenticationPreProcessingFilter extends PreProcessingFilter {

    public AuthenticationPreProcessingFilter(PreProcessingFilter nextFilter) {
	super(nextFilter);
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.http.controller.frontcontroller.PreProcessingFilter#doProcess(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse,
     *      org.apache.struts.action.Action,
     *      org.apache.struts.action.ActionForm,
     *      org.apache.struts.action.ActionMapping)
     */
    @Override
    protected ActionForward doProcess(HttpServletRequest request,
	    HttpServletResponse response, Action action, ActionForm form,
	    ActionMapping mapping) {
	MapViewerActionMapping mapViewerActionMapping = (MapViewerActionMapping) mapping;

	if (mapViewerActionMapping.getAuthenticationRequired()
		&& (!SessionManager.isUserAuthenticated(request)))
	    return mapping.findForward("AuthenticationPage");

	return null;
    }

}
