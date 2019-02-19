package mapviewer.http.controller.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mapviewer.http.controller.session.SessionManager;
import mapviewer.http.util.Option;
import mapviewer.model.searchfacade.delegate.SearchFacadeDelegate;
import mapviewer.model.searchfacade.delegate.SearchFacadeDelegateFactory;
import mapviewer.model.wms.to.WMSTO;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import es.udc.fbellas.j2ee.util.exceptions.InternalErrorException;
import es.udc.fbellas.j2ee.util.struts.action.DefaultAction;

public class ShowSelectWMSAction extends DefaultAction {

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
	String mapIDString = request.getParameter("mapID");
	Long mapID;
	if (mapIDString != null)
	    mapID = new Long(mapIDString);
	else
	    mapID = (Long) request.getAttribute("mapID");
	String userID = SessionManager.getUserID(request);

	/* Find WMSs. */
	SearchFacadeDelegate searchFacadeDelegate = SearchFacadeDelegateFactory
		.getDelegate();

	List<WMSTO> WMSTOs;
	WMSTOs = searchFacadeDelegate.findWMSsByUser(userID, -1, -1)
		.getWMSTOs();
	WMSTOs.addAll(searchFacadeDelegate.findInterestingWMSsByUser(userID,
		-1, -1).getWMSTOs());

	if (WMSTOs.isEmpty()) {
	    ActionMessages errors = new ActionMessages();
	    errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
		    "ErrorMessages.noWMSs"));
	    saveErrors(request, errors);
	    return mapping.findForward("ErrorPage");
	}

	List<Option> options = new ArrayList<Option>();

	for (WMSTO WMSTO : WMSTOs) {
	    options.add(new Option(WMSTO.getWMSID().toString(), WMSTO.getName()
		    + " (" + WMSTO.getURL() + ")"));
	}

	request.setAttribute("WMSs", options);
	request.setAttribute("mapID", mapID);
	request.setAttribute("mode", "WMSs");

	/* Return ActionForward. */
	return mapping.findForward("SelectWMSContent");
    }

}
