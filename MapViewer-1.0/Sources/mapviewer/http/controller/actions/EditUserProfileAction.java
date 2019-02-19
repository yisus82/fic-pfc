package mapviewer.http.controller.actions;

import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mapviewer.http.controller.session.SessionManager;
import mapviewer.http.controller.util.OptionComparator;
import mapviewer.http.util.Option;
import mapviewer.model.userprofile.to.UserProfileDetailsTO;
import mapviewer.model.userprofile.to.UserProfileTO;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import es.udc.fbellas.j2ee.util.exceptions.InstanceNotFoundException;
import es.udc.fbellas.j2ee.util.exceptions.InternalErrorException;
import es.udc.fbellas.j2ee.util.struts.action.DefaultAction;

public class EditUserProfileAction extends DefaultAction {

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
	/*
	 * Insert list of locales as attributes in the "request".
	 */
	Locale[] locales = Locale.getAvailableLocales();
	Locale sessionLocale = SessionManager.getLocale(request);
	SortedSet<Option> options = new TreeSet<Option>(new OptionComparator(
		sessionLocale));

	for (Locale locale : locales) {
	    String country = locale.getCountry();
	    if (country != "")
		options.add(new Option(locale.getLanguage() + "," + country,
			locale.getDisplayName(locale).toLowerCase(locale)));
	    else
		options.add(new Option(locale.getLanguage(), locale
			.getDisplayName(locale).toLowerCase(locale)));
	}

	request.setAttribute("locales", options);

	/* Fill "form". */
	DynaActionForm userProfileForm = (DynaActionForm) form;

	/*
	 * If the request is to allow the user to correct errors in the form,
	 * "userProfileForm" must not be modified.
	 */
	String action = (String) userProfileForm.get("action");

	if (request.getAttribute(Globals.ERROR_KEY) == null) {

	    /*
	     * If the user is updating his/her profile, set the rest of
	     * attributes.
	     */

	    if ("UPDATE".equals(action)) {

		UserProfileTO userProfileTO;
		try {
		    userProfileTO = SessionManager.getUserFacadeDelegate(
			    request)
			    .findUser(SessionManager.getUserID(request));
		} catch (InstanceNotFoundException e) {
		    throw new InternalErrorException(e);
		}
		UserProfileDetailsTO userProfileDetailsTO = userProfileTO
			.getUserDetails();

		userProfileForm.set("firstName", userProfileDetailsTO
			.getFirstName());
		userProfileForm.set("surname", userProfileDetailsTO
			.getSurname());
		userProfileForm.set("email", userProfileDetailsTO.getEmail());
		userProfileForm.set("localeID", userProfileDetailsTO
			.getLocaleID());
	    }

	    else {
		userProfileForm.set("localeID", Locale.getDefault()
			.getLanguage()
			+ "," + Locale.getDefault().getCountry());
	    }

	}

	request.setAttribute("mode", "users");

	/* Return ActionForward. */
	if ("UPDATE".equals(action)) {
	    return mapping.findForward("UpdateUserProfileDetailsForm");
	}
	return mapping.findForward("RegisterUserForm");
    }
}
