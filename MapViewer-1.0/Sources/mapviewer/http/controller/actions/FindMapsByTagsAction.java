package mapviewer.http.controller.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mapviewer.http.controller.session.SessionManager;
import mapviewer.model.searchfacade.delegate.SearchFacadeDelegate;
import mapviewer.model.searchfacade.delegate.SearchFacadeDelegateFactory;
import mapviewer.model.searchfacade.to.MapChunkTO;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import es.udc.fbellas.j2ee.util.exceptions.InternalErrorException;
import es.udc.fbellas.j2ee.util.struts.action.DefaultAction;

public class FindMapsByTagsAction extends DefaultAction {

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
	DynaActionForm findByTagsForm = (DynaActionForm) form;

	String tagsString;
	int startIndex;
	int count;

	try {
	    startIndex = new Integer(request.getParameter("startIndex"));
	    count = new Integer(request.getParameter("count"));
	} catch (Exception e) {
	    startIndex = 1;
	    count = 5;
	}

	tagsString = request.getParameter("tags");
	if ((tagsString == "") || (tagsString == null))
	    tagsString = (String) findByTagsForm.get("tags");
	else
	    findByTagsForm.set("tags", tagsString);

	request.setAttribute("startIndex", startIndex);
	request.setAttribute("count", count);

	/* Do find maps by tags. */
	return doFindMapsByTags(mapping, tagsString, startIndex, count, request);
    }

    private ActionForward doFindMapsByTags(ActionMapping mapping,
	    String tagsString, int startIndex, int count,
	    HttpServletRequest request) throws InternalErrorException {
	/* Find maps by tags. */
	SearchFacadeDelegate searchFacadeDelegate = SearchFacadeDelegateFactory
		.getDelegate();

	MapChunkTO maps;

	if ((tagsString == null) || (tagsString.equals("")))
	    maps = searchFacadeDelegate.findMaps(startIndex, count);
	else {
	    String[] tagsArray = tagsString.split(" ");
	    List<String> tags = new ArrayList<String>();
	    for (String tag : tagsArray)
		tags.add(tag.toLowerCase());
	    maps = searchFacadeDelegate.findMapsByTags(tags, startIndex, count);
	}

	if (maps.getMapTOs().size() > 0) {
	    request.setAttribute("maps", maps.getMapTOs());

	    if (maps.getExistMoreMaps())
		request.setAttribute("next", startIndex + count);

	    if ((startIndex - count) > 1)
		request.setAttribute("previous", startIndex - count);
	    else if (startIndex > 1) request.setAttribute("previous", 1);
	}

	request.setAttribute("interestingMaps", SessionManager
		.getInterestingMaps(request));

	request.setAttribute("mode", "maps");

	/* Return ActionForward. */
	return mapping.findForward("FindMapsByTagsContent");
    }

}
