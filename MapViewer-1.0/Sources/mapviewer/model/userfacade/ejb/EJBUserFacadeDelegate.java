package mapviewer.model.userfacade.ejb;

import java.util.List;

import javax.naming.NamingException;

import mapviewer.model.layer.to.LayerTO;
import mapviewer.model.map.to.MapTO;
import mapviewer.model.poi.to.POITO;
import mapviewer.model.tag.to.TagTO;
import mapviewer.model.userfacade.delegate.UserFacadeDelegate;
import mapviewer.model.userfacade.exceptions.AlreadyOwnedException;
import mapviewer.model.userfacade.exceptions.IncorrectPasswordException;
import mapviewer.model.userfacade.exceptions.NotAuthorizedException;
import mapviewer.model.userfacade.to.LoginResultTO;
import mapviewer.model.userprofile.to.UserProfileTO;
import mapviewer.model.wms.to.WMSTO;
import es.udc.fbellas.j2ee.util.configuration.ConfigurationParametersManager;
import es.udc.fbellas.j2ee.util.exceptions.DuplicateInstanceException;
import es.udc.fbellas.j2ee.util.exceptions.InstanceNotFoundException;
import es.udc.fbellas.j2ee.util.exceptions.InternalErrorException;
import es.udc.fbellas.j2ee.util.jndi.ServiceLocator;

public class EJBUserFacadeDelegate implements UserFacadeDelegate {

    private final static String USER_FACADE_JNDI_NAME_PARAMETER = "EJBUserFacadeDelegate/userFacadeJNDIName";

    private static String userFacadeJNDIName;

    static {

	try {

	    /* Initialize "userFacadeJNDIName". */
	    EJBUserFacadeDelegate.userFacadeJNDIName = ConfigurationParametersManager
		    .getParameter(EJBUserFacadeDelegate.USER_FACADE_JNDI_NAME_PARAMETER);

	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

    private UserFacade userFacade;

    public EJBUserFacadeDelegate() throws InternalErrorException {
	try {

	    userFacade = ServiceLocator.findService(UserFacade.class,
		    EJBUserFacadeDelegate.userFacadeJNDIName);

	} catch (NamingException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.userfacade.delegate.UserFacadeDelegate#addInterestingMap(java.lang.Long)
     */
    @Override
    public void addInterestingMap(Long mapID) throws InstanceNotFoundException,
	    DuplicateInstanceException, AlreadyOwnedException,
	    InternalErrorException {
	try {
	    userFacade.addInterestingMap(mapID);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.userfacade.delegate.UserFacadeDelegate#addInterestingPOI(java.lang.Long)
     */
    @Override
    public void addInterestingPOI(Long POIID) throws InstanceNotFoundException,
	    DuplicateInstanceException, AlreadyOwnedException,
	    InternalErrorException {
	try {
	    userFacade.addInterestingPOI(POIID);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.userfacade.delegate.UserFacadeDelegate#addInterestingWMS(java.lang.Long)
     */
    @Override
    public void addInterestingWMS(Long WMSID) throws InstanceNotFoundException,
	    DuplicateInstanceException, AlreadyOwnedException,
	    InternalErrorException {
	try {
	    userFacade.addInterestingWMS(WMSID);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.userfacade.delegate.UserFacadeDelegate#addMap(mapviewer.model.map.to.MapTO,
     *      java.util.List)
     */
    @Override
    public MapTO addMap(MapTO mapTO, List<TagTO> tagTOs)
	    throws InternalErrorException {
	try {
	    return userFacade.addMap(mapTO, tagTOs);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.userfacade.delegate.UserFacadeDelegate#addPOI(mapviewer.model.poi.to.POITO,
     *      java.util.List)
     */
    @Override
    public POITO addPOI(POITO POITO, List<TagTO> tagTOs)
	    throws InternalErrorException {
	try {
	    return userFacade.addPOI(POITO, tagTOs);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.userfacade.delegate.UserFacadeDelegate#addWMS(mapviewer.model.wms.to.WMSTO,
     *      java.util.List)
     */
    @Override
    public WMSTO addWMS(WMSTO WMSTO, List<LayerTO> layerTOs)
	    throws InternalErrorException {
	try {
	    return userFacade.addWMS(WMSTO, layerTOs);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.userfacade.delegate.UserFacadeDelegate#changePassword(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void changePassword(String oldClearPassword, String newClearPassword)
	    throws IncorrectPasswordException, InternalErrorException {
	try {
	    userFacade.changePassword(oldClearPassword, newClearPassword);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.userfacade.delegate.UserFacadeDelegate#deleteInterestingMap(java.lang.Long)
     */
    @Override
    public void deleteInterestingMap(Long mapID)
	    throws InstanceNotFoundException, InternalErrorException {
	try {
	    userFacade.deleteInterestingMap(mapID);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.userfacade.delegate.UserFacadeDelegate#deleteInterestingPOI(java.lang.Long)
     */
    @Override
    public void deleteInterestingPOI(Long POIID)
	    throws InstanceNotFoundException, InternalErrorException {
	try {
	    userFacade.deleteInterestingPOI(POIID);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.userfacade.delegate.UserFacadeDelegate#deleteInterestingWMS(java.lang.Long)
     */
    @Override
    public void deleteInterestingWMS(Long WMSID)
	    throws InstanceNotFoundException, InternalErrorException {
	try {
	    userFacade.deleteInterestingWMS(WMSID);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.userfacade.delegate.UserFacadeDelegate#deleteMap(java.lang.Long)
     */
    @Override
    public void deleteMap(Long mapID) throws InstanceNotFoundException,
	    NotAuthorizedException, InternalErrorException {
	try {
	    userFacade.deleteMap(mapID);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.userfacade.delegate.UserFacadeDelegate#deletePOI(java.lang.Long)
     */
    @Override
    public void deletePOI(Long POIID) throws InstanceNotFoundException,
	    NotAuthorizedException, InternalErrorException {
	try {
	    userFacade.deletePOI(POIID);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.userfacade.delegate.UserFacadeDelegate#deleteWMS(java.lang.Long)
     */
    @Override
    public void deleteWMS(Long WMSID) throws InstanceNotFoundException,
	    NotAuthorizedException, InternalErrorException {
	try {
	    userFacade.deleteWMS(WMSID);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.userfacade.delegate.UserFacadeDelegate#findUser(java.lang.String)
     */
    @Override
    public UserProfileTO findUser(String userID) throws InternalErrorException,
	    InstanceNotFoundException {
	try {
	    return userFacade.findUser(userID);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.userfacade.delegate.UserFacadeDelegate#login(java.lang.String,
     *      java.lang.String, boolean)
     */
    @Override
    public LoginResultTO login(String userID, String password,
	    boolean passwordIsEncrypted) throws InstanceNotFoundException,
	    IncorrectPasswordException, InternalErrorException {
	try {
	    LoginResultTO loginResultTO = userFacade.login(userID, password,
		    passwordIsEncrypted);

	    return loginResultTO;
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.userfacade.delegate.UserFacadeDelegate#registerUser(mapviewer.model.userprofile.to.UserProfileTO)
     */
    @Override
    public UserProfileTO registerUser(UserProfileTO userTO)
	    throws DuplicateInstanceException, InternalErrorException {
	try {
	    return userFacade.registerUser(userTO);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.userfacade.delegate.UserFacadeDelegate#updateMap(mapviewer.model.map.to.MapTO,
     *      java.util.List)
     */
    @Override
    public MapTO updateMap(MapTO mapTO, List<TagTO> tagTOs)
	    throws InstanceNotFoundException, NotAuthorizedException,
	    InternalErrorException {
	try {
	    return userFacade.updateMap(mapTO, tagTOs);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.userfacade.delegate.UserFacadeDelegate#updateMapLayers(java.lang.Long,
     *      java.util.List)
     */
    @Override
    public void updateMapLayers(Long mapID, List<Long> layerIDs)
	    throws InstanceNotFoundException, NotAuthorizedException,
	    InternalErrorException {
	try {
	    userFacade.updateMapLayers(mapID, layerIDs);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.userfacade.delegate.UserFacadeDelegate#updatePOI(mapviewer.model.poi.to.POITO,
     *      java.util.List)
     */
    @Override
    public POITO updatePOI(POITO POITO, List<TagTO> tagTOs)
	    throws InstanceNotFoundException, NotAuthorizedException,
	    InternalErrorException {
	try {
	    return userFacade.updatePOI(POITO, tagTOs);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.userfacade.delegate.UserFacadeDelegate#updateUserDetails(mapviewer.model.userprofile.to.UserProfileTO)
     */
    @Override
    public UserProfileTO updateUserDetails(UserProfileTO userProfileTO)
	    throws InternalErrorException {
	try {
	    return userFacade.updateUserDetails(userProfileTO);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.userfacade.delegate.UserFacadeDelegate#updateWMS(mapviewer.model.wms.to.WMSTO)
     */
    @Override
    public WMSTO updateWMS(WMSTO WMSTO) throws InstanceNotFoundException,
	    NotAuthorizedException, InternalErrorException {
	try {
	    return userFacade.updateWMS(WMSTO);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

}
