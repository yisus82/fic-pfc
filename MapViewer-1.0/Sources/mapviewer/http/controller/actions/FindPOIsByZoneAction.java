package mapviewer.http.controller.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mapviewer.http.controller.session.SessionManager;
import mapviewer.model.searchfacade.delegate.SearchFacadeDelegate;
import mapviewer.model.searchfacade.delegate.SearchFacadeDelegateFactory;
import mapviewer.model.searchfacade.to.POIChunkTO;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import es.udc.fbellas.j2ee.util.exceptions.InternalErrorException;
import es.udc.fbellas.j2ee.util.struts.action.DefaultAction;

public class FindPOIsByZoneAction extends DefaultAction {

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
	DynaActionForm findByZoneForm = (DynaActionForm) form;

	String minLatitude;
	String minLongitude;
	String maxLatitude;
	String maxLongitude;
	int startIndex;
	int count;

	try {
	    startIndex = new Integer(request.getParameter("startIndex"));
	    count = new Integer(request.getParameter("count"));
	} catch (Exception e) {
	    startIndex = 1;
	    count = 5;
	}

	minLatitude = request.getParameter("minLatitude");
	if ((minLatitude == "") || (minLatitude == null))
	    minLatitude = (String) findByZoneForm.get("minLatitude");
	else
	    findByZoneForm.set("minLatitude", minLatitude);
	minLongitude = request.getParameter("minLongitude");
	if ((minLongitude == "") || (minLongitude == null))
	    minLongitude = (String) findByZoneForm.get("minLongitude");
	else
	    findByZoneForm.set("minLongitude", minLongitude);
	maxLatitude = request.getParameter("maxLatitude");
	if ((maxLatitude == "") || (maxLatitude == null))
	    maxLatitude = (String) findByZoneForm.get("maxLatitude");
	else
	    findByZoneForm.set("maxLatitude", maxLatitude);
	maxLongitude = request.getParameter("maxLongitude");
	if ((maxLongitude == "") || (maxLongitude == null))
	    maxLongitude = (String) findByZoneForm.get("maxLongitude");
	else
	    findByZoneForm.set("maxLongitude", maxLongitude);

	request.setAttribute("startIndex", startIndex);
	request.setAttribute("count", count);

	/* Do find POIs by zone. */
	return doFindPOIsByZone(mapping, minLatitude, minLongitude,
		maxLatitude, maxLongitude, startIndex, count, request);
    }

    private ActionForward doFindPOIsByZone(ActionMapping mapping,
	    String minLatitude, String minLongitude, String maxLatitude,
	    String maxLongitude, int startIndex, int count,
	    HttpServletRequest request) throws InternalErrorException {
	/* Find POIs by zone. */
	SearchFacadeDelegate searchFacadeDelegate = SearchFacadeDelegateFactory
		.getDelegate();

	POIChunkTO POIs;

	if ((minLatitude == null) || (minLatitude.equals(""))
		|| (minLongitude == null) || (minLongitude.equals(""))
		|| (maxLatitude == null) || (maxLatitude.equals(""))
		|| (maxLongitude == null) || (maxLongitude.equals("")))
	    POIs = searchFacadeDelegate.findPOIs(startIndex, count);
	else
	    POIs = searchFacadeDelegate.findPOIsByZone(new Double(minLatitude),
		    new Double(minLongitude), new Double(maxLatitude),
		    new Double(maxLongitude), startIndex, count);

	if (POIs.getPOITOs().size() > 0) {
	    request.setAttribute("POIs", POIs.getPOITOs());

	    if (POIs.getExistMorePOIs())
		request.setAttribute("next", startIndex + count);

	    if ((startIndex - count) > 1)
		request.setAttribute("previous", startIndex - count);
	    else if (startIndex > 1) request.setAttribute("previous", 1);
	}

	request.setAttribute("interestingPOIs", SessionManager
		.getInterestingPOIs(request));

	request.setAttribute("mode", "POIs");

	/* Return ActionForward. */
	return mapping.findForward("FindPOIsByZoneContent");
    }

}
