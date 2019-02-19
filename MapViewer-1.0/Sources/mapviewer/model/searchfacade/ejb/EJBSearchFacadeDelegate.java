package mapviewer.model.searchfacade.ejb;

import java.util.List;

import javax.naming.NamingException;

import mapviewer.model.layer.to.LayerTO;
import mapviewer.model.searchfacade.delegate.SearchFacadeDelegate;
import mapviewer.model.searchfacade.to.LayerChunkTO;
import mapviewer.model.searchfacade.to.MapChunkTO;
import mapviewer.model.searchfacade.to.MapDetailsTO;
import mapviewer.model.searchfacade.to.POIChunkTO;
import mapviewer.model.searchfacade.to.POIDetailsTO;
import mapviewer.model.searchfacade.to.UserProfileChunkTO;
import mapviewer.model.searchfacade.to.WMSChunkTO;
import mapviewer.model.tag.to.TagTO;
import mapviewer.model.wms.to.WMSTO;
import es.udc.fbellas.j2ee.util.configuration.ConfigurationParametersManager;
import es.udc.fbellas.j2ee.util.exceptions.InstanceNotFoundException;
import es.udc.fbellas.j2ee.util.exceptions.InternalErrorException;
import es.udc.fbellas.j2ee.util.jndi.ServiceLocator;

public class EJBSearchFacadeDelegate implements SearchFacadeDelegate {

    private final static String SEARCH_FACADE_JNDI_NAME_PARAMETER = "EJBSearchFacadeDelegate/searchFacadeJNDIName";

    private static String searchFacadeJNDIName;

    static {

	try {

	    /* Initialize "searchFacadeJNDIName". */
	    EJBSearchFacadeDelegate.searchFacadeJNDIName = ConfigurationParametersManager
		    .getParameter(EJBSearchFacadeDelegate.SEARCH_FACADE_JNDI_NAME_PARAMETER);

	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

    private SearchFacade searchFacade;

    public EJBSearchFacadeDelegate() throws InternalErrorException {
	try {

	    searchFacade = ServiceLocator.findService(SearchFacade.class,
		    EJBSearchFacadeDelegate.searchFacadeJNDIName);

	} catch (NamingException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.delegate.SearchFacadeDelegate#findInterestingMapsByUser(java.lang.String,
     *      int, int)
     */
    @Override
    public MapChunkTO findInterestingMapsByUser(String userID, int startIndex,
	    int count) throws InternalErrorException {
	try {
	    return searchFacade.findInterestingMapsByUser(userID, startIndex,
		    count);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.delegate.SearchFacadeDelegate#findInterestingPOIsByUser(java.lang.String,
     *      int, int)
     */
    @Override
    public POIChunkTO findInterestingPOIsByUser(String userID, int startIndex,
	    int count) throws InternalErrorException {
	try {
	    return searchFacade.findInterestingPOIsByUser(userID, startIndex,
		    count);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.delegate.SearchFacadeDelegate#findInterestingWMSsByUser(java.lang.String,
     *      int, int)
     */
    @Override
    public WMSChunkTO findInterestingWMSsByUser(String userID, int startIndex,
	    int count) throws InternalErrorException {
	try {
	    return searchFacade.findInterestingWMSsByUser(userID, startIndex,
		    count);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.delegate.SearchFacadeDelegate#findLayer(java.lang.Long)
     */
    @Override
    public LayerTO findLayer(Long layerID) throws InstanceNotFoundException,
	    InternalErrorException {
	try {
	    return searchFacade.findLayer(layerID);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.delegate.SearchFacadeDelegate#findLayers(int,
     *      int)
     */
    @Override
    public LayerChunkTO findLayers(int startIndex, int count)
	    throws InternalErrorException {
	try {
	    return searchFacade.findLayers(startIndex, count);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.delegate.SearchFacadeDelegate#findLayersByMap(java.lang.Long,
     *      int, int)
     */
    @Override
    public LayerChunkTO findLayersByMap(Long mapID, int startIndex, int count)
	    throws InternalErrorException {
	try {
	    return searchFacade.findLayersByMap(mapID, startIndex, count);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.delegate.SearchFacadeDelegate#findLayersByWMS(java.lang.Long,
     *      int, int)
     */
    @Override
    public LayerChunkTO findLayersByWMS(Long WMSID, int startIndex, int count)
	    throws InternalErrorException {
	try {
	    return searchFacade.findLayersByWMS(WMSID, startIndex, count);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.delegate.SearchFacadeDelegate#findMap(java.lang.Long)
     */
    public MapDetailsTO findMap(Long mapID) throws InstanceNotFoundException,
	    InternalErrorException {
	try {
	    return searchFacade.findMap(mapID);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.delegate.SearchFacadeDelegate#findMaps(int,
     *      int)
     */
    public MapChunkTO findMaps(int startIndex, int count)
	    throws InternalErrorException {
	try {
	    return searchFacade.findMaps(startIndex, count);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.delegate.SearchFacadeDelegate#findMapsByName(java.lang.String,
     *      int, int)
     */
    @Override
    public MapChunkTO findMapsByName(String name, int startIndex, int count)
	    throws InternalErrorException {
	try {
	    return searchFacade.findMapsByName(name, startIndex, count);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.delegate.SearchFacadeDelegate#findMapsByTags(java.util.List,
     *      int, int)
     */
    public MapChunkTO findMapsByTags(List<String> tags, int startIndex,
	    int count) throws InternalErrorException {
	try {
	    return searchFacade.findMapsByTags(tags, startIndex, count);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.delegate.SearchFacadeDelegate#findMapsByUser(java.lang.String,
     *      int, int)
     */
    public MapChunkTO findMapsByUser(String userID, int startIndex, int count)
	    throws InternalErrorException {
	try {
	    return searchFacade.findMapsByUser(userID, startIndex, count);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.delegate.SearchFacadeDelegate#findMapsByZone(java.lang.Double,
     *      java.lang.Double, java.lang.Double, java.lang.Double, int, int)
     */
    @Override
    public MapChunkTO findMapsByZone(Double minLatitude, Double minLongitude,
	    Double maxLatitude, Double maxLongitude, int startIndex, int count)
	    throws InternalErrorException {
	try {
	    return searchFacade.findMapsByZone(minLatitude, minLongitude,
		    maxLatitude, maxLongitude, startIndex, count);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.delegate.SearchFacadeDelegate#findMapTags(java.lang.Long)
     */
    public List<TagTO> findMapTags(Long mapID) throws InternalErrorException {
	try {
	    return searchFacade.findMapTags(mapID);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.delegate.SearchFacadeDelegate#findPOI(java.lang.Long)
     */
    public POIDetailsTO findPOI(Long POIID) throws InstanceNotFoundException,
	    InternalErrorException {
	try {
	    return searchFacade.findPOI(POIID);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.delegate.SearchFacadeDelegate#findPOIs(int,
     *      int)
     */
    @Override
    public POIChunkTO findPOIs(int startIndex, int count)
	    throws InternalErrorException {
	try {
	    return searchFacade.findPOIs(startIndex, count);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.delegate.SearchFacadeDelegate#findPOIsByName(java.lang.String,
     *      int, int)
     */
    @Override
    public POIChunkTO findPOIsByName(String name, int startIndex, int count)
	    throws InternalErrorException {
	try {
	    return searchFacade.findPOIsByName(name, startIndex, count);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.delegate.SearchFacadeDelegate#findPOIsByTags(java.util.List,
     *      int, int)
     */
    public POIChunkTO findPOIsByTags(List<String> tags, int startIndex,
	    int count) throws InternalErrorException {
	try {
	    return searchFacade.findPOIsByTags(tags, startIndex, count);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.delegate.SearchFacadeDelegate#findPOIsByUser(java.lang.String,
     *      int, int)
     */
    public POIChunkTO findPOIsByUser(String userID, int startIndex, int count)
	    throws InternalErrorException {
	try {
	    return searchFacade.findPOIsByUser(userID, startIndex, count);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.delegate.SearchFacadeDelegate#findPOIsByZone(java.lang.Double,
     *      java.lang.Double, java.lang.Double, java.lang.Double, int, int)
     */
    @Override
    public POIChunkTO findPOIsByZone(Double minLatitude, Double minLongitude,
	    Double maxLatitude, Double maxLongitude, int startIndex, int count)
	    throws InternalErrorException {
	try {
	    return searchFacade.findPOIsByZone(minLatitude, minLongitude,
		    maxLatitude, maxLongitude, startIndex, count);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.delegate.SearchFacadeDelegate#findPOITags(java.lang.Long)
     */
    public List<TagTO> findPOITags(Long POIID) throws InternalErrorException {
	try {
	    return searchFacade.findPOITags(POIID);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.delegate.SearchFacadeDelegate#findUsers(int,
     *      int)
     */
    public UserProfileChunkTO findUsers(int startIndex, int count)
	    throws InternalErrorException {
	try {
	    return searchFacade.findUsers(startIndex, count);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.delegate.SearchFacadeDelegate#findUsersByUserID(java.lang.String,
     *      int, int)
     */
    @Override
    public UserProfileChunkTO findUsersByUserID(String userID, int startIndex,
	    int count) throws InternalErrorException {
	try {
	    return searchFacade.findUsersByUserID(userID, startIndex, count);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.delegate.SearchFacadeDelegate#findWMS(java.lang.Long)
     */
    public WMSTO findWMS(Long WMSID) throws InstanceNotFoundException,
	    InternalErrorException {
	try {
	    return searchFacade.findWMS(WMSID);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.delegate.SearchFacadeDelegate#findWMSByURL(java.lang.String)
     */
    @Override
    public WMSTO findWMSByURL(String URL) throws InstanceNotFoundException,
	    InternalErrorException {
	try {
	    return searchFacade.findWMSByURL(URL);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.delegate.SearchFacadeDelegate#findWMSs(int,
     *      int)
     */
    @Override
    public WMSChunkTO findWMSs(int startIndex, int count)
	    throws InternalErrorException {
	try {
	    return searchFacade.findWMSs(startIndex, count);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.delegate.SearchFacadeDelegate#findWMSsByName(java.lang.String,
     *      int, int)
     */
    @Override
    public WMSChunkTO findWMSsByName(String name, int startIndex, int count)
	    throws InternalErrorException {
	try {
	    return searchFacade.findWMSsByName(name, startIndex, count);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.delegate.SearchFacadeDelegate#findWMSsByUser(java.lang.String,
     *      int, int)
     */
    public WMSChunkTO findWMSsByUser(String userID, int startIndex, int count)
	    throws InternalErrorException {
	try {
	    return searchFacade.findWMSsByUser(userID, startIndex, count);
	} catch (RuntimeException e) {
	    throw new InternalErrorException(e);
	}
    }

}
