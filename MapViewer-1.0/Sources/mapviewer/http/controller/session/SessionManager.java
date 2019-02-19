package mapviewer.http.controller.session;

import java.util.HashSet;
import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import mapviewer.model.map.to.MapTO;
import mapviewer.model.poi.to.POITO;
import mapviewer.model.searchfacade.delegate.SearchFacadeDelegate;
import mapviewer.model.searchfacade.delegate.SearchFacadeDelegateFactory;
import mapviewer.model.userfacade.delegate.UserFacadeDelegate;
import mapviewer.model.userfacade.delegate.UserFacadeDelegateFactory;
import mapviewer.model.userfacade.exceptions.AlreadyOwnedException;
import mapviewer.model.userfacade.exceptions.IncorrectPasswordException;
import mapviewer.model.userfacade.to.LoginResultTO;
import mapviewer.model.userprofile.to.UserProfileTO;
import mapviewer.model.wms.to.WMSTO;

import org.apache.struts.Globals;

import es.udc.fbellas.j2ee.util.exceptions.DuplicateInstanceException;
import es.udc.fbellas.j2ee.util.exceptions.InstanceNotFoundException;
import es.udc.fbellas.j2ee.util.exceptions.InternalErrorException;

/**
 * A facade utility class to transparently manage session objects and cookies.
 * The following objects are maintained in the session:
 * <ul>
 * <li>The userID, under the key <code>USER_ID_SESSION_ATTRIBUTE</code>.
 * This attribute is only present for authenticated users. </li>
 * <li>An instance of <code>Locale</code>. If the user has been
 * authenticated, his/her value is consistent with his/her profile information.
 * This attribute must only be accessed by using
 * <code>org.apache.struts.action.Action.getLocale</code> or
 * <code>es.udc.fbellas.j2ee.util.struts.action.DefaultActionForm.getLocale</code>.
 * </li>
 * </ul>
 * <p>
 * For users that select "remember my password" in the login wizard, the
 * following cookies are used:
 * <ul>
 * <li><code>USER_ID_COOKIE</code>: to store the userID.</li>
 * <li><code>ENCRYPTED_PASSWORD_COOKIE</code>: to store the encrypted
 * password.</li>
 * </ul>
 * <p>
 * In order to make transparent the management of session objects and cookies to
 * the implementation of controller actions, this class provides a number of
 * methods equivalent to some of the ones provided by
 * <code>UserFacadeDelegate</code>, which manage session objects and cookies,
 * and call upon the corresponding model facade's methods. These methods are as
 * follows:
 * <ul>
 * <li><code>changePassword</code></li>
 * <li><code>login</code></li>
 * <li><code>registerUser</code></li>
 * <li><code>updateUserProfileDetails</code></li>
 * </ul>
 * It is important to remember that when needing to do some of the above actions
 * from the controller, the corresponding method of this class (one of the
 * previous list) must be called, and <b>not</b> the one in model facades. The
 * rest of methods of the model facades must be called directly. Currently,
 * there exists only one, <code>findUser</code>, but in a bigger portal there
 * would be more. For example, in a customizable portal,
 * <code>UserFacadeDelegate</code> could include:
 * <code>findServicePreferences</code>, <code>updateServicePreferences</code>,
 * <code>findLayout</code>, <code>updateLayout</code>, etc. In a
 * electronic commerce shop, <code>UserFacadeDelegate</code> could include:
 * <code>getShoppingCart</code>, <code>addToShoppingCart</code>,
 * <code>removeFromShoppingCart</code>, <code>makeOrder</code>,
 * <code>findPendingOrders</code>, etc. When needing to invoke directly a
 * method of <code>UserFacadeDelegate</code>,
 * <code>SessionManager.getUserFacadeDelegate</code> must be invoked in order
 * to get the personal delegate (each user has his/her own delegate).
 * <p>
 * Same as <code>UserFacadeDelegate</code>, there exist some logical
 * restrictions with regard to the order of method calling. In particular, for
 * example, <code>updateUserProfileDetails</code> and
 * <code>changePassword</code> can not be called if <code>login</code> or
 * <code>registerUser</code> have not been previously called. After the user
 * calls one of these two methods, the user is said to be authenticated.
 */
public final class SessionManager {

    public final static String ENCRYPTED_PASSWORD_COOKIE = "encryptedPassword";

    public final static String INTERESTING_MAPS_SESSION_ATTRIBUTE = "interestingMaps";

    public final static String INTERESTING_POIS_SESSION_ATTRIBUTE = "interestingPOIs";

    public final static String INTERESTING_WMSS_SESSION_ATTRIBUTE = "interestingWMSs";

    public final static String SELECTED_LAYERS_SESSION_ATTRIBUTE = "selectedLayers";

    public final static String USER_ID_COOKIE = "userID";

    public final static String USER_ID_SESSION_ATTRIBUTE = "userID";

    public final static String USER_FACADE_DELEGATE_SESSION_ATTRIBUTE = "userFacadeDelegate";

    private final static int COOKIES_TIME_TO_LIVE_REMEMBER_MY_PASSWORD = 30 * 24 * 3600; // 30
    // days

    private final static int COOKIES_TIME_TO_LIVE_REMOVE = 0;

    public final static void addInterestingMap(HttpServletRequest request,
	    Long mapID) throws InternalErrorException,
	    InstanceNotFoundException, DuplicateInstanceException,
	    AlreadyOwnedException {
	/* Add interesting map. */
	UserFacadeDelegate userFacadeDelegate = SessionManager
		.getUserFacadeDelegate(request);

	userFacadeDelegate.addInterestingMap(mapID);

	/* Insert objects in session. */
	HttpSession session = request.getSession(true);

	SortedSet<Long> interestingMaps = SessionManager
		.getInterestingMaps(request);
	interestingMaps.add(mapID);

	session.setAttribute(SessionManager.INTERESTING_MAPS_SESSION_ATTRIBUTE,
		interestingMaps);
    }

    public final static void addInterestingPOI(HttpServletRequest request,
	    Long POIID) throws InternalErrorException,
	    InstanceNotFoundException, DuplicateInstanceException,
	    AlreadyOwnedException {
	/* Add interesting POI. */
	UserFacadeDelegate userFacadeDelegate = SessionManager
		.getUserFacadeDelegate(request);

	userFacadeDelegate.addInterestingPOI(POIID);

	/* Insert objects in session. */
	HttpSession session = request.getSession(true);

	SortedSet<Long> interestingPOIs = SessionManager
		.getInterestingPOIs(request);
	interestingPOIs.add(POIID);

	session.setAttribute(SessionManager.INTERESTING_POIS_SESSION_ATTRIBUTE,
		interestingPOIs);
    }

    public final static void addInterestingWMS(HttpServletRequest request,
	    Long WMSID) throws InternalErrorException,
	    InstanceNotFoundException, DuplicateInstanceException,
	    AlreadyOwnedException {
	/* Add interesting WMS. */
	UserFacadeDelegate userFacadeDelegate = SessionManager
		.getUserFacadeDelegate(request);

	userFacadeDelegate.addInterestingWMS(WMSID);

	/* Insert objects in session. */
	HttpSession session = request.getSession(true);

	SortedSet<Long> interestingWMSs = SessionManager
		.getInterestingWMSs(request);
	interestingWMSs.add(WMSID);

	session.setAttribute(SessionManager.INTERESTING_WMSS_SESSION_ATTRIBUTE,
		interestingWMSs);
    }

    public final static void addSelectedLayer(HttpServletRequest request,
	    Long layerID) {
	/* Insert objects in session. */
	HttpSession session = request.getSession(true);

	HashSet<Long> selectedLayers = SessionManager
		.getSelectedLayers(request);
	selectedLayers.add(layerID);

	session.setAttribute(SessionManager.SELECTED_LAYERS_SESSION_ATTRIBUTE,
		selectedLayers);
    }

    public final static void changePassword(HttpServletRequest request,
	    HttpServletResponse response, String oldClearPassword,
	    String newClearPassword) throws IncorrectPasswordException,
	    InternalErrorException {
	/* Change user's password. */
	UserFacadeDelegate userFacadeDelegate = SessionManager
		.getUserFacadeDelegate(request);

	userFacadeDelegate.changePassword(oldClearPassword, newClearPassword);
	/* Remove cookies. */
	SessionManager.leaveCookies(response, "", "",
		SessionManager.COOKIES_TIME_TO_LIVE_REMOVE);
    }

    public final static void deleteInterestingMap(HttpServletRequest request,
	    Long mapID) throws InternalErrorException,
	    InstanceNotFoundException {
	/* Delete interesting map. */
	UserFacadeDelegate userFacadeDelegate = SessionManager
		.getUserFacadeDelegate(request);

	userFacadeDelegate.deleteInterestingMap(mapID);

	/* Insert objects in session. */
	HttpSession session = request.getSession(true);

	SortedSet<Long> interestingMaps = SessionManager
		.getInterestingMaps(request);
	interestingMaps.remove(mapID);

	session.setAttribute(SessionManager.INTERESTING_MAPS_SESSION_ATTRIBUTE,
		interestingMaps);
    }

    public final static void deleteInterestingPOI(HttpServletRequest request,
	    Long POIID) throws InternalErrorException,
	    InstanceNotFoundException {
	/* Delete interesting POI. */
	UserFacadeDelegate userFacadeDelegate = SessionManager
		.getUserFacadeDelegate(request);

	userFacadeDelegate.deleteInterestingPOI(POIID);

	/* Insert objects in session. */
	HttpSession session = request.getSession(true);

	SortedSet<Long> interestingPOIs = SessionManager
		.getInterestingPOIs(request);
	interestingPOIs.remove(POIID);

	session.setAttribute(SessionManager.INTERESTING_POIS_SESSION_ATTRIBUTE,
		interestingPOIs);
    }

    public final static void deleteInterestingWMS(HttpServletRequest request,
	    Long WMSID) throws InternalErrorException,
	    InstanceNotFoundException {
	/* Delete interesting WMS. */
	UserFacadeDelegate userFacadeDelegate = SessionManager
		.getUserFacadeDelegate(request);

	userFacadeDelegate.deleteInterestingWMS(WMSID);

	/* Insert objects in session. */
	HttpSession session = request.getSession(true);

	SortedSet<Long> interestingWMSs = SessionManager
		.getInterestingWMSs(request);
	interestingWMSs.remove(WMSID);

	session.setAttribute(SessionManager.INTERESTING_WMSS_SESSION_ATTRIBUTE,
		interestingWMSs);
    }

    public final static void deleteSelectedLayer(HttpServletRequest request,
	    Long layerID) {
	/* Insert objects in session. */
	HttpSession session = request.getSession(true);

	HashSet<Long> selectedLayers = SessionManager
		.getSelectedLayers(request);
	selectedLayers.remove(layerID);

	session.setAttribute(SessionManager.SELECTED_LAYERS_SESSION_ATTRIBUTE,
		selectedLayers);
    }

    public final static SortedSet<Long> getInterestingMaps(
	    HttpServletRequest request) throws InternalErrorException {
	HttpSession session = request.getSession(true);

	SortedSet<Long> interestingMaps = (SortedSet<Long>) session
		.getAttribute(SessionManager.INTERESTING_MAPS_SESSION_ATTRIBUTE);

	if (interestingMaps == null) {
	    interestingMaps = new TreeSet<Long>();
	    String userID = SessionManager.getUserID(request);
	    if (userID == null) return interestingMaps;
	    SearchFacadeDelegate searchFacadeDelegate = SearchFacadeDelegateFactory
		    .getDelegate();
	    for (MapTO mapTO : searchFacadeDelegate.findInterestingMapsByUser(
		    userID, -1, -1).getMapTOs())
		interestingMaps.add(mapTO.getMapID());
	}

	return interestingMaps;
    }

    public final static SortedSet<Long> getInterestingPOIs(
	    HttpServletRequest request) throws InternalErrorException {
	HttpSession session = request.getSession(true);

	SortedSet<Long> interestingPOIs = (SortedSet<Long>) session
		.getAttribute(SessionManager.INTERESTING_POIS_SESSION_ATTRIBUTE);

	if (interestingPOIs == null) {
	    interestingPOIs = new TreeSet<Long>();
	    String userID = SessionManager.getUserID(request);
	    if (userID == null) return interestingPOIs;
	    SearchFacadeDelegate searchFacadeDelegate = SearchFacadeDelegateFactory
		    .getDelegate();
	    for (POITO POITO : searchFacadeDelegate.findInterestingPOIsByUser(
		    userID, -1, -1).getPOITOs())
		interestingPOIs.add(POITO.getPOIID());
	}

	return interestingPOIs;
    }

    public final static SortedSet<Long> getInterestingWMSs(
	    HttpServletRequest request) throws InternalErrorException {
	HttpSession session = request.getSession(true);

	SortedSet<Long> interestingWMSs = (SortedSet<Long>) session
		.getAttribute(SessionManager.INTERESTING_WMSS_SESSION_ATTRIBUTE);

	if (interestingWMSs == null) {
	    interestingWMSs = new TreeSet<Long>();
	    String userID = SessionManager.getUserID(request);
	    if (userID == null) return interestingWMSs;
	    SearchFacadeDelegate searchFacadeDelegate = SearchFacadeDelegateFactory
		    .getDelegate();
	    for (WMSTO WMSTO : searchFacadeDelegate.findInterestingWMSsByUser(
		    userID, -1, -1).getWMSTOs())
		interestingWMSs.add(WMSTO.getWMSID());
	}

	return interestingWMSs;
    }

    public final static Locale getLocale(HttpServletRequest request) {
	HttpSession session = request.getSession(true);
	return (Locale) session.getAttribute(Globals.LOCALE_KEY);
    }

    public final static HashSet<Long> getSelectedLayers(
	    HttpServletRequest request) {
	HttpSession session = request.getSession(true);

	HashSet<Long> selectedLayers = (HashSet<Long>) session
		.getAttribute(SessionManager.SELECTED_LAYERS_SESSION_ATTRIBUTE);

	if (selectedLayers == null) return new HashSet<Long>();

	return selectedLayers;
    }

    public final static UserFacadeDelegate getUserFacadeDelegate(
	    HttpServletRequest request) throws InternalErrorException {
	HttpSession session = request.getSession(true);
	UserFacadeDelegate userFacadeDelegate = (UserFacadeDelegate) session
		.getAttribute(SessionManager.USER_FACADE_DELEGATE_SESSION_ATTRIBUTE);

	if (userFacadeDelegate == null) {
	    userFacadeDelegate = UserFacadeDelegateFactory.getDelegate();
	    session.setAttribute(
		    SessionManager.USER_FACADE_DELEGATE_SESSION_ATTRIBUTE,
		    userFacadeDelegate);
	}

	return userFacadeDelegate;
    }

    public final static String getUserID(HttpServletRequest request) {
	HttpSession session = request.getSession(true);
	return (String) session
		.getAttribute(SessionManager.USER_ID_SESSION_ATTRIBUTE);
    }

    public final static boolean isUserAuthenticated(HttpServletRequest request) {
	HttpSession session = request.getSession(false);

	if (session == null) {
	    return false;
	}
	return session.getAttribute(SessionManager.USER_ID_SESSION_ATTRIBUTE) != null;
    }

    public final static void login(HttpServletRequest request,
	    HttpServletResponse response, String userID, String clearPassword,
	    boolean rememberMyPassword) throws IncorrectPasswordException,
	    InstanceNotFoundException, InternalErrorException {
	/*
	 * Try to login, and if successful, update session with the necessary
	 * objects for an authenticated user.
	 */
	LoginResultTO loginResultTO = SessionManager.doLogin(request, userID,
		clearPassword, false, rememberMyPassword);

	/* Add cookies if requested. */
	if (rememberMyPassword) {
	    SessionManager.leaveCookies(response, userID, loginResultTO
		    .getPassword(),
		    SessionManager.COOKIES_TIME_TO_LIVE_REMEMBER_MY_PASSWORD);
	}
    }

    /**
     * Destroys session, and removes cookies if the user had selected "remember
     * my password".
     */
    public final static void logout(HttpServletRequest request,
	    HttpServletResponse response) {
	/* Remove cookies. */
	SessionManager.leaveCookies(response, "", "",
		SessionManager.COOKIES_TIME_TO_LIVE_REMOVE);

	/* Invalidate session. */
	HttpSession session = request.getSession(true);
	session.invalidate();
    }

    public final static void registerUser(HttpServletRequest request,
	    UserProfileTO userTO) throws DuplicateInstanceException,
	    InternalErrorException {
	/* Register user. */
	UserFacadeDelegate userFacadeDelegate = SessionManager
		.getUserFacadeDelegate(request);

	userFacadeDelegate.registerUser(userTO);

	/*
	 * Update session with the necessary objects for an authenticated user.
	 */
	Locale locale = SessionManager.getLocale(userTO.getUserDetails()
		.getLocaleID());

	SessionManager.updateSesssionForAuthenticatedUser(request, userTO
		.getUserID(), locale);
    }

    public final static void setSelectedLayers(HttpServletRequest request,
	    HashSet<Long> selectedLayers) {
	HttpSession session = request.getSession(true);

	session.setAttribute(SessionManager.SELECTED_LAYERS_SESSION_ATTRIBUTE,
		selectedLayers);
    }

    /**
     * Guarantees that the session will have the necessary objects if the user
     * has been authenticated or had selected "remember my password" in the
     * past.
     */
    public final static void touchSession(HttpServletRequest request)
	    throws InternalErrorException {
	/* Check if "userID" is in the session. */
	String userID = null;
	HttpSession session = request.getSession(false);

	if (session != null) {

	    userID = (String) session
		    .getAttribute(SessionManager.USER_ID_SESSION_ATTRIBUTE);

	    if (userID != null) {
		return;
	    }
	}

	/*
	 * The user had not been authenticated or his/her session has expired.
	 * We need to check if the user has selected "remember my password" in
	 * the last login (userID and password will come as cookies). If so, we
	 * reconstruct user's session objects.
	 */
	SessionManager.updateSessionFromCookies(request);
    }

    public final static void updateUserProfileDetails(
	    HttpServletRequest request, UserProfileTO userTO)
	    throws InternalErrorException {
	/* Update user's profile details. */
	UserFacadeDelegate userFacadeDelegate = SessionManager
		.getUserFacadeDelegate(request);

	userFacadeDelegate.updateUserDetails(userTO);

	/* Update user's session objects. */
	Locale locale = SessionManager.getLocale(userTO.getUserDetails()
		.getLocaleID());
	SessionManager.updateSesssionForAuthenticatedUser(request, userTO
		.getUserID(), locale);
    }

    /**
     * Tries to log in with the corresponding method of
     * <code>UserFacadeDelegate</code>, and if successful, inserts in the
     * session the necessary objects for an authenticated user.
     */
    private final static LoginResultTO doLogin(HttpServletRequest request,
	    String userID, String password, boolean passwordIsEncrypted,
	    boolean rememberMyPassword) throws IncorrectPasswordException,
	    InstanceNotFoundException, InternalErrorException {
	/* Check "userID" and "clearPassword". */
	UserFacadeDelegate userFacadeDelegate = SessionManager
		.getUserFacadeDelegate(request);

	LoginResultTO loginResultTO = userFacadeDelegate.login(userID,
		password, passwordIsEncrypted);

	/* Insert necessary objects in the session. */
	SessionManager.updateSesssionForAuthenticatedUser(request, userID,
		loginResultTO.getLocale());

	/* Return "loginResultTO". */
	return loginResultTO;
    }

    private final static Locale getLocale(String localeID) {
	Locale locale = Locale.getDefault();

	try {
	    String[] localeStrings = localeID.split(",");
	    if (localeStrings.length > 1)
		locale = new Locale(localeStrings[0], localeStrings[1]);
	    else
		locale = new Locale(localeStrings[0]);
	} catch (Exception e) {
	}
	return locale;
    }

    private final static void leaveCookies(HttpServletResponse response,
	    String userID, String encryptedPassword, int timeToLive) {
	/* Create cookies. */
	Cookie userIDCookie = new Cookie(SessionManager.USER_ID_COOKIE, userID);
	Cookie encryptedPasswordCookie = new Cookie(
		SessionManager.ENCRYPTED_PASSWORD_COOKIE, encryptedPassword);

	/* Set maximum age to cookies. */
	userIDCookie.setMaxAge(timeToLive);
	encryptedPasswordCookie.setMaxAge(timeToLive);

	/* Add cookies to response. */
	response.addCookie(userIDCookie);
	response.addCookie(encryptedPasswordCookie);
    }

    /**
     * Tries to login (inserting necessary objects in the session) by using
     * cookies (if present).
     */
    private final static void updateSessionFromCookies(
	    HttpServletRequest request) throws InternalErrorException {
	/* Check if there are cookies in the request */
	Cookie[] cookies = request.getCookies();
	if (cookies == null) {
	    return;
	}

	/*
	 * Check if the userID and the encrypted password come as cookies.
	 */
	String userID = null;
	String encryptedPassword = null;
	int foundCookies = 0;

	for (int i = 0; (i < cookies.length) && (foundCookies < 2); i++) {
	    if (cookies[i].getName().equals(SessionManager.USER_ID_COOKIE)) {
		userID = cookies[i].getValue();
		foundCookies++;
	    }
	    if (cookies[i].getName().equals(
		    SessionManager.ENCRYPTED_PASSWORD_COOKIE)) {
		encryptedPassword = cookies[i].getValue();
		foundCookies++;
	    }
	}

	if (foundCookies != 2) {
	    return;
	}

	/*
	 * Try to login, and if successful, update session with the necessary
	 * objects for an authenticated user.
	 */
	try {
	    SessionManager.doLogin(request, userID, encryptedPassword, true,
		    true);
	} catch (InternalErrorException e) {
	    throw e;
	} catch (Exception e) { // Incorrect userID or encryptedPassword
	    return;
	}
    }

    private final static void updateSesssionForAuthenticatedUser(
	    HttpServletRequest request, String userID, Locale locale) {
	/* Insert objects in session. */
	HttpSession session = request.getSession(true);

	session.setAttribute(SessionManager.USER_ID_SESSION_ATTRIBUTE, userID);
	session.setAttribute(Globals.LOCALE_KEY, locale);
	Config.set(session, Config.FMT_LOCALE, locale);
    }

    private SessionManager() {
    }

}
