package mapviewer.http.controller.frontcontroller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mapviewer.http.controller.session.SessionManager;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.udc.fbellas.j2ee.util.exceptions.InternalErrorException;

/**
 * A filter to guarantee that the session will have the necessary objects if the
 * user has been authenticated or had selected "remember my password" in the
 * past.
 */
public class SessionPreProcessingFilter extends PreProcessingFilter {

    public SessionPreProcessingFilter(PreProcessingFilter nextFilter) {
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
	    ActionMapping mapping) throws InternalErrorException {
	SessionManager.touchSession(request);

	return null;
    }

}
