package mapviewer.model.userfacade.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import mapviewer.model.layer.entity.Layer;
import mapviewer.model.layer.to.LayerTO;
import mapviewer.model.map.entity.Map;
import mapviewer.model.map.to.MapTO;
import mapviewer.model.poi.entity.POI;
import mapviewer.model.poi.to.POITO;
import mapviewer.model.tag.to.TagTO;
import mapviewer.model.userfacade.ejb.actions.UpdateMapLayersAction;
import mapviewer.model.userfacade.ejb.actions.AddMapAction;
import mapviewer.model.userfacade.ejb.actions.AddPOIAction;
import mapviewer.model.userfacade.ejb.actions.ChangePasswordAction;
import mapviewer.model.userfacade.ejb.actions.LoginAction;
import mapviewer.model.userfacade.ejb.actions.RegisterUserAction;
import mapviewer.model.userfacade.ejb.actions.UpdateMapAction;
import mapviewer.model.userfacade.ejb.actions.UpdatePOIAction;
import mapviewer.model.userfacade.exceptions.AlreadyOwnedException;
import mapviewer.model.userfacade.exceptions.IncorrectPasswordException;
import mapviewer.model.userfacade.exceptions.NotAuthorizedException;
import mapviewer.model.userfacade.to.LoginResultTO;
import mapviewer.model.userprofile.entity.UserProfile;
import mapviewer.model.userprofile.to.UserProfileDetailsTO;
import mapviewer.model.userprofile.to.UserProfileTO;
import mapviewer.model.util.GlobalNames;
import mapviewer.model.wms.entity.WMS;
import mapviewer.model.wms.to.WMSTO;
import es.udc.fbellas.j2ee.util.exceptions.DuplicateInstanceException;
import es.udc.fbellas.j2ee.util.exceptions.InstanceNotFoundException;

@Stateful
public class UserFacadeEJB implements LocalUserFacade, RemoteUserFacade {

    @PersistenceContext(unitName = GlobalNames.PERSISTENCE_UNIT)
    private EntityManager entityManager;

    private String userID;

    public UserFacadeEJB() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.userfacade.ejb.UserFacade#addInterestingMap(java.lang.Long)
     */
    @Override
    public void addInterestingMap(Long mapID) throws InstanceNotFoundException,
	    DuplicateInstanceException, AlreadyOwnedException {
	Map map = UserFacadeHelper.getMap(entityManager, mapID);
	if (map == null)
	    throw new InstanceNotFoundException(mapID, Map.class.getName());
	if (map.getUser().getUserID() == userID)
	    throw new AlreadyOwnedException(mapID, Map.class.getName());
	UserProfile userProfile = UserFacadeHelper.getUserProfile(
		entityManager, userID);
	List<Map> interestingMaps = userProfile.getInterestingMaps();
	if (interestingMaps.contains(map))
	    throw new DuplicateInstanceException(mapID, Map.class.getName());
	interestingMaps.add(map);
	userProfile.setInterestingMaps(interestingMaps);
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.userfacade.ejb.UserFacade#addInterestingPOI(java.lang.Long)
     */
    @Override
    public void addInterestingPOI(Long POIID) throws InstanceNotFoundException,
	    DuplicateInstanceException, AlreadyOwnedException {
	POI POI = UserFacadeHelper.getPOI(entityManager, POIID);
	if (POI == null)
	    throw new InstanceNotFoundException(POIID, POI.class.getName());
	if (POI.getUser().getUserID() == userID)
	    throw new AlreadyOwnedException(POIID, POI.class.getName());
	UserProfile userProfile = UserFacadeHelper.getUserProfile(
		entityManager, userID);
	List<POI> interestingPOIs = userProfile.getInterestingPOIs();
	if (interestingPOIs.contains(POI))
	    throw new DuplicateInstanceException(POIID, POI.class.getName());
	interestingPOIs.add(POI);
	userProfile.setInterestingPOIs(interestingPOIs);
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.userfacade.ejb.UserFacade#addInterestingWMS(java.lang.Long)
     */
    @Override
    public void addInterestingWMS(Long WMSID) throws InstanceNotFoundException,
	    DuplicateInstanceException, AlreadyOwnedException {
	WMS WMS = UserFacadeHelper.getWMS(entityManager, WMSID);
	if (WMS == null)
	    throw new InstanceNotFoundException(WMSID, WMS.class.getName());
	if (WMS.getUser().getUserID() == userID)
	    throw new AlreadyOwnedException(WMSID, WMS.class.getName());
	UserProfile userProfile = UserFacadeHelper.getUserProfile(
		entityManager, userID);
	List<WMS> interestingWMSs = userProfile.getInterestingWMSs();
	if (interestingWMSs.contains(WMS))
	    throw new DuplicateInstanceException(WMSID, WMS.class.getName());
	interestingWMSs.add(WMS);
	userProfile.setInterestingWMSs(interestingWMSs);
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.userfacade.ejb.UserFacade#addMap(mapviewer.model.map.to.MapTO,
     *      java.util.List)
     */
    @Override
    public MapTO addMap(MapTO mapTO, List<TagTO> tagTOs) {
	AddMapAction action = new AddMapAction(entityManager, mapTO, tagTOs);

	return action.execute();
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.userfacade.ejb.UserFacade#addPOI(mapviewer.model.poi.to.POITO,
     *      java.util.List)
     */
    @Override
    public POITO addPOI(POITO POITO, List<TagTO> tagTOs) {
	AddPOIAction action = new AddPOIAction(entityManager, POITO, tagTOs);

	return action.execute();
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.userfacade.ejb.UserFacade#addWMS(mapviewer.model.wms.to.WMSTO,
     *      java.util.List)
     */
    @Override
    public WMSTO addWMS(WMSTO WMSTO, List<LayerTO> layerTOs) {
	WMS WMS = UserFacadeHelper.toWMS(entityManager, WMSTO);

	entityManager.persist(WMS);

	List<Layer> layers = new ArrayList<Layer>();

	for (LayerTO layerTO : layerTOs) {
	    layerTO.setWMSID(WMS.getWMSID());
	    layers.add(UserFacadeHelper.toLayer(entityManager, layerTO));
	}

	WMS.setLayers(layers);

	return UserFacadeHelper.toWMSTO(WMS);
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.userfacade.ejb.UserFacade#changePassword(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void changePassword(String oldClearPassword, String newClearPassword)
	    throws IncorrectPasswordException {
	ChangePasswordAction action = new ChangePasswordAction(entityManager,
		userID, oldClearPassword, newClearPassword);

	action.execute();
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.userfacade.ejb.UserFacade#deleteInterestingMap(java.lang.Long)
     */
    @Override
    public void deleteInterestingMap(Long mapID)
	    throws InstanceNotFoundException {
	Map map = UserFacadeHelper.getMap(entityManager, mapID);
	if (map == null)
	    throw new InstanceNotFoundException(mapID, Map.class.getName());
	UserProfile userProfile = UserFacadeHelper.getUserProfile(
		entityManager, userID);
	List<Map> interestingMaps = userProfile.getInterestingMaps();
	if (!interestingMaps.contains(map))
	    throw new InstanceNotFoundException(mapID, Map.class.getName());
	interestingMaps.remove(map);
	userProfile.setInterestingMaps(interestingMaps);
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.userfacade.ejb.UserFacade#deleteInterestingPOI(java.lang.Long)
     */
    @Override
    public void deleteInterestingPOI(Long POIID)
	    throws InstanceNotFoundException {
	POI POI = UserFacadeHelper.getPOI(entityManager, POIID);
	if (POI == null)
	    throw new InstanceNotFoundException(POIID, POI.class.getName());
	UserProfile userProfile = UserFacadeHelper.getUserProfile(
		entityManager, userID);
	List<POI> interestingPOIs = userProfile.getInterestingPOIs();
	if (!interestingPOIs.contains(POI))
	    throw new InstanceNotFoundException(POIID, POI.class.getName());
	interestingPOIs.remove(POI);
	userProfile.setInterestingPOIs(interestingPOIs);
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.userfacade.ejb.UserFacade#deleteInterestingWMS(java.lang.Long)
     */
    @Override
    public void deleteInterestingWMS(Long WMSID)
	    throws InstanceNotFoundException {
	WMS WMS = UserFacadeHelper.getWMS(entityManager, WMSID);
	if (WMS == null)
	    throw new InstanceNotFoundException(WMSID, WMS.class.getName());
	UserProfile userProfile = UserFacadeHelper.getUserProfile(
		entityManager, userID);
	List<WMS> interestingWMSs = userProfile.getInterestingWMSs();
	if (!interestingWMSs.contains(WMS))
	    throw new InstanceNotFoundException(WMSID, WMS.class.getName());
	interestingWMSs.remove(WMS);
	userProfile.setInterestingWMSs(interestingWMSs);
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.userfacade.ejb.UserFacade#deleteMap(java.lang.Long)
     */
    @Override
    public void deleteMap(Long mapID) throws InstanceNotFoundException,
	    NotAuthorizedException {
	Map map = UserFacadeHelper.getMap(entityManager, mapID);

	if (map == null)
	    throw new InstanceNotFoundException(mapID, Map.class.getName());
	if (userID.equals("admin"))
	    entityManager.remove(map);
	else if (!userID.equals(map.getUser().getUserID()))
	    throw new NotAuthorizedException("delete this map");
	else {
	    List<UserProfile> interestedUsers = map.getInterestedUsers();
	    if (!interestedUsers.isEmpty()) {
		UserProfile userProfile = interestedUsers.remove(0);
		map.setUser(userProfile);
		map.setInterestedUsers(interestedUsers);
	    } else
		entityManager.remove(map);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.userfacade.ejb.UserFacade#deletePOI(java.lang.Long)
     */
    @Override
    public void deletePOI(Long POIID) throws InstanceNotFoundException,
	    NotAuthorizedException {
	POI POI = UserFacadeHelper.getPOI(entityManager, POIID);

	if (POI == null)
	    throw new InstanceNotFoundException(POIID, POI.class.getName());

	if (userID.equals("admin"))
	    entityManager.remove(POI);
	else if (!userID.equals(POI.getUser().getUserID()))
	    throw new NotAuthorizedException("delete this POI");
	else {
	    List<UserProfile> interestedUsers = POI.getInterestedUsers();
	    if (!interestedUsers.isEmpty()) {
		UserProfile userProfile = interestedUsers.remove(0);
		POI.setUser(userProfile);
		POI.setInterestedUsers(interestedUsers);
	    } else
		entityManager.remove(POI);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.userfacade.ejb.UserFacade#deleteWMS(java.lang.Long)
     */
    @Override
    public void deleteWMS(Long WMSID) throws InstanceNotFoundException,
	    NotAuthorizedException {
	WMS WMS = UserFacadeHelper.getWMS(entityManager, WMSID);

	if (WMS == null)
	    throw new InstanceNotFoundException(WMSID, WMS.class.getName());

	if (userID.equals("admin"))
	    entityManager.remove(WMS);
	else if (!userID.equals(WMS.getUser().getUserID()))
	    throw new NotAuthorizedException("delete this WMS");
	else {
	    List<UserProfile> interestedUsers = WMS.getInterestedUsers();
	    if (!interestedUsers.isEmpty()) {
		UserProfile userProfile = interestedUsers.remove(0);
		WMS.setUser(userProfile);
		WMS.setInterestedUsers(interestedUsers);
	    } else
		entityManager.remove(WMS);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.userfacade.ejb.UserFacade#findUser(java.lang.String)
     */
    @Override
    public UserProfileTO findUser(String userID)
	    throws InstanceNotFoundException {
	UserProfile userProfile = UserFacadeHelper.getUserProfile(
		entityManager, userID);

	if (userProfile == null)
	    throw new InstanceNotFoundException(userID, UserProfile.class
		    .getName());

	return UserFacadeHelper.toUserProfileTO(userProfile);
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.userfacade.ejb.UserFacade#userID(java.lang.String,
     *      java.lang.String, boolean)
     */
    @Override
    public LoginResultTO login(String userID, String password,
	    boolean passwordIsEncrypted) throws InstanceNotFoundException,
	    IncorrectPasswordException {
	LoginAction action = new LoginAction(entityManager, userID, password,
		passwordIsEncrypted);
	LoginResultTO loginResultTO = action.execute();

	this.userID = userID;

	return loginResultTO;
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.userfacade.ejb.UserFacade#registerUser(mapviewer.model.userprofile.to.UserProfileTO)
     */
    @Override
    public UserProfileTO registerUser(UserProfileTO userProfileTO)
	    throws DuplicateInstanceException {
	RegisterUserAction action = new RegisterUserAction(entityManager,
		userProfileTO);

	userID = userProfileTO.getUserID();

	return action.execute();
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.userfacade.ejb.UserFacade#updateMap(mapviewer.model.map.to.MapTO,
     *      java.util.List)
     */
    @Override
    public MapTO updateMap(MapTO mapTO, List<TagTO> tagTOs)
	    throws InstanceNotFoundException, NotAuthorizedException {
	if (!userID.equals(mapTO.getUserID()))
	    throw new NotAuthorizedException("update this map");

	UpdateMapAction action = new UpdateMapAction(entityManager, mapTO,
		tagTOs);

	return action.execute();
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.userfacade.ejb.UserFacade#addLayersToMap(java.lang.Long,
     *      java.util.List)
     */
    @Override
    public void updateMapLayers(Long mapID, List<Long> layerIDs)
	    throws InstanceNotFoundException, NotAuthorizedException {
	Map map = UserFacadeHelper.getMap(entityManager, mapID);

	if (map == null)
	    throw new InstanceNotFoundException(mapID, Map.class.getName());

	if (!userID.equals(map.getUser().getUserID()))
	    throw new NotAuthorizedException("update this map");

	UpdateMapLayersAction action = new UpdateMapLayersAction(entityManager,
		mapID, layerIDs);

	action.execute();
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.userfacade.ejb.UserFacade#updatePOI(mapviewer.model.poi.to.POITO,
     *      java.util.List)
     */
    @Override
    public POITO updatePOI(POITO POITO, List<TagTO> tagTOs)
	    throws InstanceNotFoundException, NotAuthorizedException {
	if (!userID.equals(POITO.getUserID()))
	    throw new NotAuthorizedException("update this POI");

	UpdatePOIAction action = new UpdatePOIAction(entityManager, POITO,
		tagTOs);

	return action.execute();
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.userfacade.ejb.UserFacade#updateUserDetails(mapviewer.model.userprofile.to.UserProfileTO)
     */
    @Override
    public UserProfileTO updateUserDetails(UserProfileTO userProfileTO) {
	UserProfile userProfile = UserFacadeHelper.getUserProfile(
		entityManager, userID);

	UserProfileDetailsTO details = userProfileTO.getUserDetails();
	userProfile.setFirstName(details.getFirstName());
	userProfile.setSurname(details.getSurname());
	userProfile.setEmail(details.getEmail());
	userProfile.setLocaleID(details.getLocaleID());

	return UserFacadeHelper.toUserProfileTO(userProfile);
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.userfacade.ejb.UserFacade#updateWMS(mapviewer.model.wms.to.WMSTO)
     */
    @Override
    public WMSTO updateWMS(WMSTO WMSTO) throws InstanceNotFoundException,
	    NotAuthorizedException {
	if (!userID.equals(WMSTO.getUserID()))
	    throw new NotAuthorizedException("update this WMS");

	WMS WMS = UserFacadeHelper.getWMS(entityManager, WMSTO.getWMSID());

	if (WMS == null)
	    throw new InstanceNotFoundException(WMSTO.getWMSID(), WMS.class
		    .getName());

	WMS.setDescription(WMSTO.getDescription());
	WMS.setName(WMSTO.getName());

	return UserFacadeHelper.toWMSTO(WMS);
    }

}
