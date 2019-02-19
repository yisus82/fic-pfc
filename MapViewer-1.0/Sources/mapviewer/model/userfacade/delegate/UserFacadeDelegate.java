package mapviewer.model.userfacade.delegate;

import java.io.Serializable;
import java.util.List;

import mapviewer.model.layer.to.LayerTO;
import mapviewer.model.map.to.MapTO;
import mapviewer.model.poi.to.POITO;
import mapviewer.model.tag.to.TagTO;
import mapviewer.model.userfacade.exceptions.AlreadyOwnedException;
import mapviewer.model.userfacade.exceptions.IncorrectPasswordException;
import mapviewer.model.userfacade.exceptions.NotAuthorizedException;
import mapviewer.model.userfacade.to.LoginResultTO;
import mapviewer.model.userprofile.to.UserProfileTO;
import mapviewer.model.wms.to.WMSTO;
import es.udc.fbellas.j2ee.util.exceptions.DuplicateInstanceException;
import es.udc.fbellas.j2ee.util.exceptions.InstanceNotFoundException;
import es.udc.fbellas.j2ee.util.exceptions.InternalErrorException;

/**
 * A facade to model the interaction of the user with the portal. There is some
 * logical restrictions with regard to the method calling order. In particular,
 * to call methods the user has to be authenticated. This happens when the user
 * calls <code>userID</code> or <code>registerUser</code>.
 */
public interface UserFacadeDelegate extends Serializable {

    /**
     * Adds a map to the maps a given user is interested in
     * 
     * @param mapID
     *                the identifier of the map to add
     * @throws InstanceNotFoundException
     *                 if there is no map with the <code>mapID</code>
     * @throws DuplicateInstanceException
     *                 if there is another map with the <code>mapID</code>
     * @throws AlreadyOwnedException
     *                 if the map with the <code>mapID</code> is already owned
     *                 by the specified user
     * @throws InternalErrorException
     *                 if a severe error occurs
     */
    public void addInterestingMap(Long mapID) throws InstanceNotFoundException,
	    DuplicateInstanceException, AlreadyOwnedException,
	    InternalErrorException;

    /**
     * Adds a POI to the POIs a given user is interested in
     * 
     * @param POIID
     *                the identifier of the POI to add
     * @throws InstanceNotFoundException
     *                 if there is no POI with the <code>POIID</code>
     * @throws DuplicateInstanceException
     *                 if there is another POI with the <code>POIID</code>
     * @throws AlreadyOwnedException
     *                 if the POI with the <code>POIID</code> is already owned
     *                 by the specified user
     * @throws InternalErrorException
     *                 if a severe error occurs
     */
    public void addInterestingPOI(Long POIID) throws InstanceNotFoundException,
	    DuplicateInstanceException, AlreadyOwnedException,
	    InternalErrorException;

    /**
     * Adds a WMS to the WMSs a given user is interested in
     * 
     * @param WMSID
     *                the identifier of the WMS to add
     * @throws InstanceNotFoundException
     *                 if there is no WMS with the <code>WMSID</code>
     * @throws DuplicateInstanceException
     *                 if there is another WMS with the <code>WMSID</code>
     * @throws AlreadyOwnedException
     *                 if the WMS with the <code>WMSID</code> is already owned
     *                 by the specified user
     * @throws InternalErrorException
     *                 if a severe error occurs
     */
    public void addInterestingWMS(Long WMSID) throws InstanceNotFoundException,
	    DuplicateInstanceException, AlreadyOwnedException,
	    InternalErrorException;

    /**
     * Adds a map in the database
     * 
     * @param mapTO
     *                a transfer object with all of the map data, but the ID
     * @param tagTOs
     *                a collection of transfer objects with all of the map tags
     *                data
     * @return the same transfer object but with all of the map data (including
     *         the ID)
     * @throws InternalErrorException
     *                 if a severe error occurs
     */
    public MapTO addMap(MapTO mapTO, List<TagTO> tagTOs)
	    throws InternalErrorException;

    /**
     * Adds a POI in the database
     * 
     * @param POITO
     *                a transfer object with all of the POI data, but the ID
     * @param tagTOs
     *                a collection of transfer objects with all of the POI tags
     *                data
     * @return the same transfer object but with all of the POI data (including
     *         the ID)
     * @throws InternalErrorException
     *                 if a severe error occurs
     */
    public POITO addPOI(POITO POITO, List<TagTO> tagTOs)
	    throws InternalErrorException;

    /**
     * Adds a WMS in the database
     * 
     * @param WMSTO
     *                a transfer object with all of the WMS data, but the ID
     * @param layerTOs
     *                a collection of transfer objects with all of the layers
     *                data
     * @return the same transfer object but with all of the WMS data (including
     *         the ID)
     * @throws InternalErrorException
     *                 if a severe error occurs
     */
    public WMSTO addWMS(WMSTO WMSTO, List<LayerTO> layerTOs)
	    throws InternalErrorException;

    /**
     * Changes the password of the user in the database
     * 
     * @param oldClearPassword
     *                the old password (not encrypted)
     * @param newClearPassword
     *                the new password (not encrypted)
     * @throws IncorrectPasswordException
     *                 if the old password given is not correct
     * @throws InternalErrorException
     *                 if a severe error occurs
     */
    public void changePassword(String oldClearPassword, String newClearPassword)
	    throws IncorrectPasswordException, InternalErrorException;

    /**
     * Deletes a map from the maps a given user is interested in
     * 
     * @param mapID
     *                the identifier of the map to delete
     * @throws InstanceNotFoundException
     *                 if there is no map with the <code>mapID</code>
     * @throws InternalErrorException
     *                 if a severe error occurs
     */
    public void deleteInterestingMap(Long mapID)
	    throws InstanceNotFoundException, InternalErrorException;

    /**
     * Deletes a POI from the POIs a given user is interested in
     * 
     * @param POIID
     *                the identifier of the POI to delete
     * @throws InstanceNotFoundException
     *                 if there is no POI with the <code>POIID</code>
     * @throws InternalErrorException
     *                 if a severe error occurs
     */
    public void deleteInterestingPOI(Long POIID)
	    throws InstanceNotFoundException, InternalErrorException;

    /**
     * Deletes a WMS from the WMSs a given user is interested in
     * 
     * @param WMSID
     *                the identifier of the WMS to delete
     * @throws InstanceNotFoundException
     *                 if there is no WMS with the <code>WMSID</code>
     * @throws InternalErrorException
     *                 if a severe error occurs
     */
    public void deleteInterestingWMS(Long WMSID)
	    throws InstanceNotFoundException, InternalErrorException;

    /**
     * Deletes a map from the database
     * 
     * @param mapID
     *                the identifier of the map to delete
     * @throws InstanceNotFoundException
     *                 if there is no map with the <code>mapID</code>
     * @throws NotAuthorizedException
     *                 if the user is not the owner of the map
     * @throws InternalErrorException
     *                 if a severe error occurs
     */
    public void deleteMap(Long mapID) throws InstanceNotFoundException,
	    NotAuthorizedException, InternalErrorException;

    /**
     * Deletes a POI from the database
     * 
     * @param POIID
     *                the identifier of the POI to delete
     * @throws InstanceNotFoundException
     *                 if there is no POI with the <code>POIID</code>
     * @throws NotAuthorizedException
     *                 if the user is not the owner of the POI
     * @throws InternalErrorException
     *                 if a severe error occurs
     */
    public void deletePOI(Long POIID) throws InstanceNotFoundException,
	    NotAuthorizedException, InternalErrorException;

    /**
     * Deletes a WMS from the database
     * 
     * @param WMSID
     *                the identifier of the WMS to delete
     * @throws InstanceNotFoundException
     *                 if there is no WMS with the <code>WMSID</code>
     * @throws NotAuthorizedException
     *                 if the user is not the owner of the WMS
     * @throws InternalErrorException
     *                 if a severe error occurs
     */
    public void deleteWMS(Long WMSID) throws InstanceNotFoundException,
	    NotAuthorizedException, InternalErrorException;

    /**
     * Finds an user in the database
     * 
     * @param userID
     *                the user identifier
     * @return a transfer object with the user data
     * @throws InstanceNotFoundException
     *                 if there is no user with the <code>userID</code>
     * @throws InternalErrorException
     *                 if a severe error occurs
     */
    public UserProfileTO findUser(String userID)
	    throws InstanceNotFoundException, InternalErrorException;

    /**
     * Allows an user to be authenticated
     * 
     * @param userID
     *                the user identifier
     * @param password
     *                the password of the user
     * @param passwordIsEncrypted
     *                <code>true</code> if the given password is encrypted<br>
     *                <code>false</code> otherwise
     * @return a custom transfer object with the relevant data of the user
     * @throws InstanceNotFoundException
     *                 if there is no user with the <code>userID</code>
     * @throws IncorrectPasswordException
     *                 if the password given is not correct
     * @throws InternalErrorException
     *                 if a severe error occurs
     */
    public LoginResultTO login(String userID, String password,
	    boolean passwordIsEncrypted) throws InstanceNotFoundException,
	    IncorrectPasswordException, InternalErrorException;

    /**
     * Registers an user in the database
     * 
     * @param userTO
     *                a transfer object with all of the user data
     * @return a transfer object with all of the user data
     * @throws DuplicateInstanceException
     *                 if there is another user with the same identifier in the
     *                 database
     * @throws InternalErrorException
     *                 if a severe error occurs
     */
    public UserProfileTO registerUser(UserProfileTO userTO)
	    throws DuplicateInstanceException, InternalErrorException;

    /**
     * Updates a map in the database
     * 
     * @param mapTO
     *                a transfer object with the new map data
     * @param tagTOs
     *                a collection of transfer objects with the new map tags
     *                data
     * @return a transfer object with all of the map data updated
     * @throws InstanceNotFoundException
     *                 if there is no map with the identifier of the
     *                 <code>mapTO</code>
     * @throws NotAuthorizedException
     *                 if the user is not the owner of the map
     * @throws InternalErrorException
     *                 if a severe error occurs
     */
    public MapTO updateMap(MapTO mapTO, List<TagTO> tagTOs)
	    throws InstanceNotFoundException, NotAuthorizedException,
	    InternalErrorException;

    /**
     * Updates map layers in the database
     * 
     * @param mapID
     *                the map identifier
     * @param layerIDs
     *                a collection of layer identifiers
     * @throws InstanceNotFoundException
     *                 if there is no map with the <code>mapID</code>
     * @throws NotAuthorizedException
     *                 if the user is not the owner of the map
     * @throws InternalErrorException
     *                 if a severe error occurs
     */
    public void updateMapLayers(Long mapID, List<Long> layerIDs)
	    throws InstanceNotFoundException, NotAuthorizedException,
	    InternalErrorException;

    /**
     * Updates a POI in the database
     * 
     * @param POITO
     *                a transfer object with the new POI data
     * @param tagTOs
     *                a collection of transfer objects with the new POI tags
     *                data
     * @return a transfer object with all of the POI data updated
     * @throws InstanceNotFoundException
     *                 if there is no POI with the identifier of the
     *                 <code>POITO</code>
     * @throws NotAuthorizedException
     *                 if the user is not the owner of the POI
     * @throws InternalErrorException
     *                 if a severe error occurs
     */
    public POITO updatePOI(POITO POITO, List<TagTO> tagTOs)
	    throws InstanceNotFoundException, NotAuthorizedException,
	    InternalErrorException;

    /**
     * Updates the user details in the database
     * 
     * @param userProfileTO
     *                a transfer object with the new user data
     * @return a transfer object with all of the user data updated
     * @throws InternalErrorException
     *                 if a severe error occurs
     */
    public UserProfileTO updateUserDetails(UserProfileTO userProfileTO)
	    throws InternalErrorException;

    /**
     * Updates a WMS in the database
     * 
     * @param WMSTO
     *                a transfer object with the new WMS data
     * @return a transfer object with all of the WMS data updated
     * @throws InstanceNotFoundException
     *                 if there is no WMS with the identifier of the
     *                 <code>WMSTO</code>
     * @throws NotAuthorizedException
     *                 if the user is not the owner of the WMS
     * @throws InternalErrorException
     *                 if a severe error occurs
     */
    public WMSTO updateWMS(WMSTO WMSTO) throws InstanceNotFoundException,
	    NotAuthorizedException, InternalErrorException;

}
