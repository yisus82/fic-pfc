package mapviewer.http.controller.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mapviewer.http.controller.session.SessionManager;
import mapviewer.model.searchfacade.delegate.SearchFacadeDelegate;
import mapviewer.model.searchfacade.delegate.SearchFacadeDelegateFactory;
import mapviewer.model.searchfacade.to.POIChunkTO;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.udc.fbellas.j2ee.util.exceptions.InternalErrorException;
import es.udc.fbellas.j2ee.util.struts.action.DefaultAction;

public class FindPOIsByTagAction extends DefaultAction {

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
	String tag;
	int startIndex;
	int count;

	try {
	    startIndex = new Integer(request.getParameter("startIndex"));
	    count = new Integer(request.getParameter("count"));
	} catch (Exception e) {
	    startIndex = 1;
	    count = 5;
	}

	tag = request.getParameter("tag");
	List<String> tags = new ArrayList<String>();
	if (tag != null) tags.add(tag.toLowerCase());

	request.setAttribute("startIndex", startIndex);
	request.setAttribute("count", count);

	/* Do find POIs by tag. */
	return doFindPOIsByTag(mapping, tags, startIndex, count, request);
    }

    private ActionForward doFindPOIsByTag(ActionMapping mapping,
	    List<String> tags, int startIndex, int count,
	    HttpServletRequest request) throws InternalErrorException {
	/* Find POIs by tag. */
	SearchFacadeDelegate searchFacadeDelegate = SearchFacadeDelegateFactory
		.getDelegate();

	POIChunkTO POIs;
	POIs = searchFacadeDelegate.findPOIsByTags(tags, startIndex, count);

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
	return mapping.findForward("FindPOIsByTagContent");
    }

}