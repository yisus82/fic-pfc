package mapviewer.model.searchfacade.ejb;

import java.util.List;

import mapviewer.model.layer.to.LayerTO;
import mapviewer.model.searchfacade.to.LayerChunkTO;
import mapviewer.model.searchfacade.to.MapChunkTO;
import mapviewer.model.searchfacade.to.MapDetailsTO;
import mapviewer.model.searchfacade.to.POIChunkTO;
import mapviewer.model.searchfacade.to.POIDetailsTO;
import mapviewer.model.searchfacade.to.UserProfileChunkTO;
import mapviewer.model.searchfacade.to.WMSChunkTO;
import mapviewer.model.tag.to.TagTO;
import mapviewer.model.wms.to.WMSTO;
import es.udc.fbellas.j2ee.util.exceptions.InstanceNotFoundException;

public interface SearchFacade {

    /**
     * Returns a collection with the <code>MapTO</code>s a given user is
     * interested in. If the user is interested in no maps, the returned
     * collection has zero elements
     * <p>
     * NOTES:
     * <ul>
     * <li> If <code>startIndex</code> and <code>count</code> are equal to
     * -1 the collection returned has all of the maps</li>
     * </ul>
     * 
     * @param userID
     *                the user identifier
     * @param startIndex
     *                the index (starting from 1) of the first object to return
     * @param count
     *                the maximum number of objects to return
     * @return the collection of <code>MapTO</code>s with the data of the
     *         maps the specified user is interested in
     */
    public MapChunkTO findInterestingMapsByUser(String userID, int startIndex,
	    int count);

    /**
     * Returns a collection with the <code>POITO</code>s a given user is
     * interested in. If the user is interested in no POIs, the returned
     * collection has zero elements
     * <p>
     * NOTES:
     * <ul>
     * <li> If <code>startIndex</code> and <code>count</code> are equal to
     * -1 the collection returned has all of the POIs</li>
     * </ul>
     * 
     * @param userID
     *                the user identifier
     * @param startIndex
     *                the index (starting from 1) of the first object to return
     * @param count
     *                the maximum number of objects to return
     * @return the collection of <code>POITO</code>s with the data of the
     *         POIs the specified user is interested in
     */
    public POIChunkTO findInterestingPOIsByUser(String userID, int startIndex,
	    int count);

    /**
     * Returns a collection with the <code>WMSTO</code>s a given user is
     * interested in. If the user is interested in no WMSs, the returned
     * collection has zero elements
     * <p>
     * NOTES:
     * <ul>
     * <li> If <code>startIndex</code> and <code>count</code> are equal to
     * -1 the collection returned has all of the WMSs</li>
     * </ul>
     * 
     * @param userID
     *                the user identifier
     * @param startIndex
     *                the index (starting from 1) of the first object to return
     * @param count
     *                the maximum number of objects to return
     * @return the collection of <code>WMSTO</code>s with the data of the
     *         WMSs the specified user is interested in
     */
    public WMSChunkTO findInterestingWMSsByUser(String userID, int startIndex,
	    int count);

    /**
     * Finds a layer in the database
     * 
     * @param layerID
     *                the layer identifier
     * @return a transfer object with the layer data
     * @throws InstanceNotFoundException
     *                 if there is no layer with the <code>layerID</code>
     */
    public LayerTO findLayer(Long layerID) throws InstanceNotFoundException;

    /**
     * Returns a collection with all of the <code>LayerTO</code>s. If there
     * is no layers, the returned collection has zero elements
     * <p>
     * NOTES:
     * <ul>
     * <li> If <code>startIndex</code> and <code>count</code> are equal to
     * -1 the collection returned has all of the layers</li>
     * </ul>
     * 
     * @param startIndex
     *                the index (starting from 1) of the first object to return
     * @param count
     *                the maximum number of objects to return
     * @return the collection of <code>LayerTO</code>s with the data of the
     *         layers of the specified user
     */
    public LayerChunkTO findLayers(int startIndex, int count);

    /**
     * Returns a collection with the <code>LayerTO</code>s of a given map. If
     * the map has no layers, the returned collection has zero elements
     * <p>
     * NOTES:
     * <ul>
     * <li> If <code>startIndex</code> and <code>count</code> are equal to
     * -1 the collection returned has all of the layers</li>
     * </ul>
     * 
     * @param mapID
     *                the map identifier
     * @param startIndex
     *                the index (starting from 1) of the first object to return
     * @param count
     *                the maximum number of objects to return
     * @return the collection of <code>LayerTO</code>s with the data of the
     *         layers of the specified user
     */
    public LayerChunkTO findLayersByMap(Long mapID, int startIndex, int count);

    /**
     * Returns a collection with the <code>LayerTO</code>s of a given WMS. If
     * the WMS has no layers, the returned collection has zero elements
     * <p>
     * NOTES:
     * <ul>
     * <li> If <code>startIndex</code> and <code>count</code> are equal to
     * -1 the collection returned has all of the layers</li>
     * </ul>
     * 
     * @param WMSID
     *                the WMS identifier
     * @param startIndex
     *                the index (starting from 1) of the first object to return
     * @param count
     *                the maximum number of objects to return
     * @return the collection of <code>LayerTO</code>s with the data of the
     *         layers of the specified user
     */
    public LayerChunkTO findLayersByWMS(Long WMSID, int startIndex, int count);

    /**
     * Finds a map in the database
     * 
     * @param mapID
     *                the map identifier
     * @return a transfer object with the map data
     * @throws InstanceNotFoundException
     *                 if there is no map with the <code>mapID</code>
     */
    public MapDetailsTO findMap(Long mapID) throws InstanceNotFoundException;

    /**
     * Returns a custom transfer object with a collection with all of the
     * <code>MapTO</code>s. If there is no maps, the returned collection has
     * zero elements
     * <p>
     * NOTES:
     * <ul>
     * <li> If <code>startIndex</code> and <code>count</code> are equal to
     * -1 the collection returned has all of the maps</li>
     * </ul>
     * 
     * @param startIndex
     *                the index (starting from 1) of the first object to return
     * @param count
     *                the maximum number of objects to return
     * @return the custom transfer object with the collection of
     *         <code>MapTO</code>s with the data of the maps
     */
    public MapChunkTO findMaps(int startIndex, int count);

    /**
     * Returns a collection with the <code>MapTO</code>s with a given name.
     * If there is no maps with that name, the returned collection has zero
     * elements
     * <p>
     * NOTES:
     * <ul>
     * <li> If <code>startIndex</code> and <code>count</code> are equal to
     * -1 the collection returned has all of the maps</li>
     * </ul>
     * 
     * @param name
     *                the name of the map
     * @param startIndex
     *                the index (starting from 1) of the first object to return
     * @param count
     *                the maximum number of objects to return
     * @return the collection of <code>MapTO</code>s with the data of the
     *         maps with the specified name
     */
    public MapChunkTO findMapsByName(String name, int startIndex, int count);

    /**
     * Returns a collection with the <code>MapTO</code>s related with the
     * given tags. If there is no maps related to these tags, the returned
     * collection has zero elements
     * <p>
     * NOTES:
     * <ul>
     * <li> If <code>startIndex</code> and <code>count</code> are equal to
     * -1 the collection returned has all of the maps</li>
     * </ul>
     * 
     * @param tags
     *                the tags
     * @param startIndex
     *                the index (starting from 1) of the first object to return
     * @param count
     *                the maximum number of objects to return
     * @return the collection of <code>MapTO</code>s with the data of the
     *         maps related to the specified tags
     */
    public MapChunkTO findMapsByTags(List<String> tags, int startIndex,
	    int count);

    /**
     * Returns a collection with the <code>MapTO</code>s of a given user. If
     * the user has no maps, the returned collection has zero elements
     * <p>
     * NOTES:
     * <ul>
     * <li> If <code>startIndex</code> and <code>count</code> are equal to
     * -1 the collection returned has all of the maps</li>
     * </ul>
     * 
     * @param userID
     *                the user identifier
     * @param startIndex
     *                the index (starting from 1) of the first object to return
     * @param count
     *                the maximum number of objects to return
     * @return the collection of <code>MapTO</code>s with the data of the
     *         maps of the specified user
     */
    public MapChunkTO findMapsByUser(String userID, int startIndex, int count);

    /**
     * Returns a custom transfer object with a collection with the
     * <code>MapTO</code>s that contain a given geographic area. If there is
     * no maps, the returned collection has zero elements
     * <p>
     * NOTES:
     * <ul>
     * <li> If <code>startIndex</code> and <code>count</code> are equal to
     * -1 the collection returned has all of the maps</li>
     * </ul>
     * 
     * @param minLatitude
     *                the minimum latitude of the geographic area
     * @param minLongitude
     *                the minimum longitude of the geographic area
     * @param maxLatitude
     *                the maximum latitude of the geographic area
     * @param maxLongitude
     *                the maximum longitude of the geographic area
     * @param startIndex
     *                the index (starting from 1) of the first object to return
     * @param count
     *                the maximum number of objects to return
     * @return the custom transfer object with the collection of
     *         <code>MapTO</code>s with the data of the maps
     */
    public MapChunkTO findMapsByZone(Double minLatitude, Double minLongitude,
	    Double maxLatitude, Double maxLongitude, int startIndex, int count);

    /**
     * Returns a collection with the <code>TagTO</code>s of a given map. If
     * the map has no tags, the returned collection has zero elements
     * 
     * @param mapID
     *                the map identifier
     * @return the collection of <code>TagTO</code>s with the data of the
     *         tags of the specified map
     */
    public List<TagTO> findMapTags(Long mapID);

    /**
     * Finds a POI in the database
     * 
     * @param POIID
     *                the POI identifier
     * @return a transfer object with the POI data
     * @throws InstanceNotFoundException
     *                 if there is no POI with the <code>POIID</code>
     */
    public POIDetailsTO findPOI(Long POIID) throws InstanceNotFoundException;

    /**
     * Returns a custom transfer object with a collection with all of the
     * <code>POITO</code>s. If there is no POIs, the returned collection has
     * zero elements
     * <p>
     * NOTES:
     * <ul>
     * <li> If <code>startIndex</code> and <code>count</code> are equal to
     * -1 the collection returned has all of the POIs</li>
     * </ul>
     * 
     * @param startIndex
     *                the index (starting from 1) of the first object to return
     * @param count
     *                the maximum number of objects to return
     * @return the custom transfer object with the collection of
     *         <code>POITO</code>s with the data of the POIs
     */
    public POIChunkTO findPOIs(int startIndex, int count);

    /**
     * Returns a collection with the <code>POITO</code>s with a given name.
     * If there is no POIs with that name, the returned collection has zero
     * elements
     * <p>
     * NOTES:
     * <ul>
     * <li> If <code>startIndex</code> and <code>count</code> are equal to
     * -1 the collection returned has all of the POIs</li>
     * </ul>
     * 
     * @param name
     *                the name of the POI
     * @param startIndex
     *                the index (starting from 1) of the first object to return
     * @param count
     *                the maximum number of objects to return
     * @return the collection of <code>POITO</code>s with the data of the
     *         POIs with the specified name
     */
    public POIChunkTO findPOIsByName(String name, int startIndex, int count);

    /**
     * Returns a collection with the <code>POITO</code>s related with the
     * given tags. If there is no POIs related to these tags, the returned
     * collection has zero elements
     * <p>
     * NOTES:
     * <ul>
     * <li> If <code>startIndex</code> and <code>count</code> are equal to
     * -1 the collection returned has all of the POIs</li>
     * </ul>
     * 
     * @param tags
     *                the tags
     * @param startIndex
     *                the index (starting from 1) of the first object to return
     * @param count
     *                the maximum number of objects to return
     * @return the collection of <code>POITO</code>s with the data of the
     *         POIs related to the specified tags
     */
    public POIChunkTO findPOIsByTags(List<String> tags, int startIndex,
	    int count);

    /**
     * Returns a collection with the <code>POITO</code>s of a given user. If
     * the user has no POIs, the returned collection has zero elements
     * <p>
     * NOTES:
     * <ul>
     * <li> If <code>startIndex</code> and <code>count</code> are equal to
     * -1 the collection returned has all of the POIs</li>
     * </ul>
     * 
     * @param userID
     *                the user identifier
     * @param startIndex
     *                the index (starting from 1) of the first object to return
     * @param count
     *                the maximum number of objects to return
     * @return the collection of <code>POITO</code>s with the data of the
     *         POIs of the specified user
     */
    public POIChunkTO findPOIsByUser(String userID, int startIndex, int count);

    /**
     * Returns a custom transfer object with a collection with the
     * <code>POITO</code>s that are within a given geographic area. If there
     * is no POIs, the returned collection has zero elements
     * <p>
     * NOTES:
     * <ul>
     * <li> If <code>startIndex</code> and <code>count</code> are equal to
     * -1 the collection returned has all of the POIs</li>
     * </ul>
     * 
     * @param minLatitude
     *                the minimum latitude of the geographic area
     * @param minLongitude
     *                the minimum longitude of the geographic area
     * @param maxLatitude
     *                the maximum latitude of the geographic area
     * @param maxLongitude
     *                the maximum longitude of the geographic area
     * @param startIndex
     *                the index (starting from 1) of the first object to return
     * @param count
     *                the maximum number of objects to return
     * @return the custom transfer object with the collection of
     *         <code>POITO</code>s with the data of the POIs
     */
    public POIChunkTO findPOIsByZone(Double minLatitude, Double minLongitude,
	    Double maxLatitude, Double maxLongitude, int startIndex, int count);

    /**
     * Returns a collection with the <code>TagTO</code>s of a given POI. If
     * the POI has no tags, the returned collection has zero elements
     * 
     * @param POIID
     *                the POI identifier
     * @return the collection of <code>TagTO</code>s with the data of the
     *         tags of the specified POI
     */
    public List<TagTO> findPOITags(Long POIID);

    /**
     * Returns a custom transfer object with a collection with all of the
     * <code>UserProfileTO</code>s. If there is no users, the returned
     * collection has zero elements
     * <p>
     * NOTES:
     * <ul>
     * <li> If <code>startIndex</code> and <code>count</code> are equal to
     * -1 the collection returned has all of the users</li>
     * </ul>
     * 
     * @param startIndex
     *                the index (starting from 1) of the first object to return
     * @param count
     *                the maximum number of objects to return
     * @return the custom transfer object with the collection of
     *         <code>UserProfileTO</code>s with the data of the users
     */
    public UserProfileChunkTO findUsers(int startIndex, int count);

    /**
     * Returns a custom transfer object with a collection with the
     * <code>UserProfileTO</code>s with a given userID. If there is no users
     * with that userID, the returned collection has zero elements
     * <p>
     * NOTES:
     * <ul>
     * <li> If <code>startIndex</code> and <code>count</code> are equal to
     * -1 the collection returned has all of the users</li>
     * </ul>
     * 
     * @param userID
     *                the user identifier
     * @param startIndex
     *                the index (starting from 1) of the first object to return
     * @param count
     *                the maximum number of objects to return
     * @return the custom transfer object with the collection of
     *         <code>UserProfileTO</code>s with the data of the users
     */
    public UserProfileChunkTO findUsersByUserID(String userID, int startIndex,
	    int count);

    /**
     * Finds a WMS in the database
     * 
     * @param WMSID
     *                the WMS identifier
     * @return a transfer object with the WMS data
     * @throws InstanceNotFoundException
     *                 if there is no WMS with the <code>WMSID</code>
     */
    public WMSTO findWMS(Long WMSID) throws InstanceNotFoundException;

    /**
     * Finds a WMS in the database
     * 
     * @param URL
     *                the URL of the WMS
     * @return a transfer object with the WMS data
     * @throws InstanceNotFoundException
     *                 if there is no WMS with the <code>URL</code>
     */
    public WMSTO findWMSByURL(String URL) throws InstanceNotFoundException;

    /**
     * Returns a collection with all of the <code>WMSTO</code>s. If there is
     * no WMSs, the returned collection has zero elements
     * <p>
     * NOTES:
     * <ul>
     * <li> If <code>startIndex</code> and <code>count</code> are equal to
     * -1 the collection returned has all of the WMSs</li>
     * </ul>
     * 
     * @param startIndex
     *                the index (starting from 1) of the first object to return
     * @param count
     *                the maximum number of objects to return
     * @return the collection of <code>WMSTO</code>s with the data of the
     *         WMSs of the specified user
     */
    public WMSChunkTO findWMSs(int startIndex, int count);

    /**
     * Returns a collection with the <code>WMSTO</code>s with a given name.
     * If there is no WMSs with that name, the returned collection has zero
     * elements
     * <p>
     * NOTES:
     * <ul>
     * <li> If <code>startIndex</code> and <code>count</code> are equal to
     * -1 the collection returned has all of the WMSs</li>
     * </ul>
     * 
     * @param name
     *                the name of the WMS
     * @param startIndex
     *                the index (starting from 1) of the first object to return
     * @param count
     *                the maximum number of objects to return
     * @return the collection of <code>WMSTO</code>s with the data of the
     *         WMSs with the specified name
     */
    public WMSChunkTO findWMSsByName(String name, int startIndex, int count);

    /**
     * Returns a collection with the <code>WMSTO</code>s of a given user. If
     * the user has no WMSs, the returned collection has zero elements
     * <p>
     * NOTES:
     * <ul>
     * <li> If <code>startIndex</code> and <code>count</code> are equal to
     * -1 the collection returned has all of the WMSs</li>
     * </ul>
     * 
     * @param userID
     *                the user identifier
     * @param startIndex
     *                the index (starting from 1) of the first object to return
     * @param count
     *                the maximum number of objects to return
     * @return the collection of <code>WMSTO</code>s with the data of the
     *         WMSs of the specified user
     */
    public WMSChunkTO findWMSsByUser(String userID, int startIndex, int count);

}
