package mapviewer.model.searchfacade.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import mapviewer.model.layer.entity.Layer;
import mapviewer.model.layer.to.LayerTO;
import mapviewer.model.map.entity.Map;
import mapviewer.model.poi.entity.POI;
import mapviewer.model.searchfacade.ejb.actions.FindInterestingMapsByUserAction;
import mapviewer.model.searchfacade.ejb.actions.FindInterestingPOIsByUserAction;
import mapviewer.model.searchfacade.ejb.actions.FindInterestingWMSsByUserAction;
import mapviewer.model.searchfacade.ejb.actions.FindLayersAction;
import mapviewer.model.searchfacade.ejb.actions.FindLayersByMapAction;
import mapviewer.model.searchfacade.ejb.actions.FindLayersByWMSAction;
import mapviewer.model.searchfacade.ejb.actions.FindMapsAction;
import mapviewer.model.searchfacade.ejb.actions.FindMapsByNameAction;
import mapviewer.model.searchfacade.ejb.actions.FindMapsByTagsAction;
import mapviewer.model.searchfacade.ejb.actions.FindMapsByUserAction;
import mapviewer.model.searchfacade.ejb.actions.FindMapsByZoneAction;
import mapviewer.model.searchfacade.ejb.actions.FindPOIsAction;
import mapviewer.model.searchfacade.ejb.actions.FindPOIsByNameAction;
import mapviewer.model.searchfacade.ejb.actions.FindPOIsByTagsAction;
import mapviewer.model.searchfacade.ejb.actions.FindPOIsByUserAction;
import mapviewer.model.searchfacade.ejb.actions.FindPOIsByZoneAction;
import mapviewer.model.searchfacade.ejb.actions.FindUsersAction;
import mapviewer.model.searchfacade.ejb.actions.FindUsersByUserIDAction;
import mapviewer.model.searchfacade.ejb.actions.FindWMSsAction;
import mapviewer.model.searchfacade.ejb.actions.FindWMSsByNameAction;
import mapviewer.model.searchfacade.ejb.actions.FindWMSsByUserAction;
import mapviewer.model.searchfacade.to.LayerChunkTO;
import mapviewer.model.searchfacade.to.MapChunkTO;
import mapviewer.model.searchfacade.to.MapDetailsTO;
import mapviewer.model.searchfacade.to.POIChunkTO;
import mapviewer.model.searchfacade.to.POIDetailsTO;
import mapviewer.model.searchfacade.to.UserProfileChunkTO;
import mapviewer.model.searchfacade.to.WMSChunkTO;
import mapviewer.model.tag.to.TagTO;
import mapviewer.model.userfacade.ejb.UserFacadeHelper;
import mapviewer.model.util.GlobalNames;
import mapviewer.model.wms.entity.WMS;
import mapviewer.model.wms.to.WMSTO;
import es.udc.fbellas.j2ee.util.exceptions.InstanceNotFoundException;

@Stateless
public class SearchFacadeEJB implements LocalSearchFacade, RemoteSearchFacade {

    @PersistenceContext(unitName = GlobalNames.PERSISTENCE_UNIT)
    private EntityManager entityManager;

    public SearchFacadeEJB() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.ejb.SearchFacade#findInterestingMapsByUser(java.lang.String,
     *      int, int)
     */
    @Override
    public MapChunkTO findInterestingMapsByUser(String userID, int startIndex,
	    int count) {
	FindInterestingMapsByUserAction action = new FindInterestingMapsByUserAction(
		entityManager, userID, startIndex, count);

	return action.execute();
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.ejb.SearchFacade#findInterestingPOIsByUser(java.lang.String,
     *      int, int)
     */
    @Override
    public POIChunkTO findInterestingPOIsByUser(String userID, int startIndex,
	    int count) {
	FindInterestingPOIsByUserAction action = new FindInterestingPOIsByUserAction(
		entityManager, userID, startIndex, count);

	return action.execute();
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.ejb.SearchFacade#findInterestingWMSsByUser(java.lang.String,
     *      int, int)
     */
    @Override
    public WMSChunkTO findInterestingWMSsByUser(String userID, int startIndex,
	    int count) {
	FindInterestingWMSsByUserAction action = new FindInterestingWMSsByUserAction(
		entityManager, userID, startIndex, count);

	return action.execute();
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.ejb.SearchFacade#findLayer(java.lang.Long)
     */
    @Override
    public LayerTO findLayer(Long layerID) throws InstanceNotFoundException {
	Layer layer = UserFacadeHelper.getLayer(entityManager, layerID);

	if (layer == null)
	    throw new InstanceNotFoundException(layerID, Layer.class.getName());

	return UserFacadeHelper.toLayerTO(layer);
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.ejb.SearchFacade#findLayers(int, int)
     */
    @Override
    public LayerChunkTO findLayers(int startIndex, int count) {
	FindLayersAction action = new FindLayersAction(entityManager,
		startIndex, count);

	return action.execute();
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.ejb.SearchFacade#findLayersByMap(java.lang.Long,
     *      int, int)
     */
    @Override
    public LayerChunkTO findLayersByMap(Long mapID, int startIndex, int count) {
	FindLayersByMapAction action = new FindLayersByMapAction(entityManager,
		mapID, startIndex, count);

	return action.execute();
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.ejb.SearchFacade#findLayersByWMS(java.lang.Long,
     *      int, int)
     */
    @Override
    public LayerChunkTO findLayersByWMS(Long WMSID, int startIndex, int count) {
	FindLayersByWMSAction action = new FindLayersByWMSAction(entityManager,
		WMSID, startIndex, count);

	return action.execute();
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.ejb.SearchFacade#findMap(java.lang.Long)
     */
    public MapDetailsTO findMap(Long mapID) throws InstanceNotFoundException {
	Map map = UserFacadeHelper.getMap(entityManager, mapID);

	if (map == null)
	    throw new InstanceNotFoundException(mapID, Map.class.getName());

	return SearchFacadeHelper.toMapDetailsTO(map);
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.ejb.SearchFacade#findMaps(int, int)
     */
    public MapChunkTO findMaps(int startIndex, int count) {
	FindMapsAction action = new FindMapsAction(entityManager, startIndex,
		count);

	return action.execute();
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.ejb.SearchFacade#findMapsByName(java.lang.String,
     *      int, int)
     */
    @Override
    public MapChunkTO findMapsByName(String name, int startIndex, int count) {
	FindMapsByNameAction action = new FindMapsByNameAction(entityManager,
		name, startIndex, count);

	return action.execute();
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.ejb.SearchFacade#findMapsByTags(java.util.List,
     *      int, int)
     */
    public MapChunkTO findMapsByTags(List<String> tags, int startIndex,
	    int count) {
	FindMapsByTagsAction action = new FindMapsByTagsAction(entityManager,
		tags, startIndex, count);

	return action.execute();
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.ejb.SearchFacade#findMapsByUser(java.lang.String,
     *      int, int)
     */
    public MapChunkTO findMapsByUser(String userID, int startIndex, int count) {
	FindMapsByUserAction action = new FindMapsByUserAction(entityManager,
		userID, startIndex, count);

	return action.execute();
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.ejb.SearchFacade#findMapsByZone(java.lang.Double,
     *      java.lang.Double, java.lang.Double, java.lang.Double, int, int)
     */
    @Override
    public MapChunkTO findMapsByZone(Double minLatitude, Double minLongitude,
	    Double maxLatitude, Double maxLongitude, int startIndex, int count) {
	FindMapsByZoneAction action = new FindMapsByZoneAction(entityManager,
		minLatitude, minLongitude, maxLatitude, maxLongitude,
		startIndex, count);

	return action.execute();
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.ejb.SearchFacade#findMapTags(java.lang.Long)
     */
    public List<TagTO> findMapTags(Long mapID) {
	Map map = UserFacadeHelper.getMap(entityManager, mapID);

	return UserFacadeHelper.toTagTOs(map.getTags());
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.ejb.SearchFacade#findPOI(java.lang.Long)
     */
    public POIDetailsTO findPOI(Long POIID) throws InstanceNotFoundException {
	POI POI = UserFacadeHelper.getPOI(entityManager, POIID);

	if (POI == null)
	    throw new InstanceNotFoundException(POIID, POI.class.getName());

	return SearchFacadeHelper.toPOIDetailsTO(POI);
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.ejb.SearchFacade#findPOIs(int, int)
     */
    @Override
    public POIChunkTO findPOIs(int startIndex, int count) {
	FindPOIsAction action = new FindPOIsAction(entityManager, startIndex,
		count);

	return action.execute();
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.ejb.SearchFacade#findPOIsByName(java.lang.String,
     *      int, int)
     */
    @Override
    public POIChunkTO findPOIsByName(String name, int startIndex, int count) {
	FindPOIsByNameAction action = new FindPOIsByNameAction(entityManager,
		name, startIndex, count);

	return action.execute();
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.ejb.SearchFacade#findPOIsByTags(java.util.List,
     *      int, int)
     */
    public POIChunkTO findPOIsByTags(List<String> tags, int startIndex,
	    int count) {
	FindPOIsByTagsAction action = new FindPOIsByTagsAction(entityManager,
		tags, startIndex, count);

	return action.execute();
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.ejb.SearchFacade#findPOIsByUser(java.lang.String,
     *      int, int)
     */
    public POIChunkTO findPOIsByUser(String userID, int startIndex, int count) {
	FindPOIsByUserAction action = new FindPOIsByUserAction(entityManager,
		userID, startIndex, count);

	return action.execute();
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.ejb.SearchFacade#findPOIsByZone(java.lang.Double,
     *      java.lang.Double, java.lang.Double, java.lang.Double, int, int)
     */
    @Override
    public POIChunkTO findPOIsByZone(Double minLatitude, Double minLongitude,
	    Double maxLatitude, Double maxLongitude, int startIndex, int count) {
	FindPOIsByZoneAction action = new FindPOIsByZoneAction(entityManager,
		minLatitude, minLongitude, maxLatitude, maxLongitude,
		startIndex, count);

	return action.execute();
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.ejb.SearchFacade#findPOITags(java.lang.Long)
     */
    public List<TagTO> findPOITags(Long POIID) {
	POI POI = UserFacadeHelper.getPOI(entityManager, POIID);

	return UserFacadeHelper.toTagTOs(POI.getTags());
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.ejb.SearchFacade#findUsers(int, int)
     */
    public UserProfileChunkTO findUsers(int startIndex, int count) {
	FindUsersAction action = new FindUsersAction(entityManager, startIndex,
		count);

	return action.execute();
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.ejb.SearchFacade#findUsersByUserID(java.lang.String,
     *      int, int)
     */
    @Override
    public UserProfileChunkTO findUsersByUserID(String userID, int startIndex,
	    int count) {
	FindUsersByUserIDAction action = new FindUsersByUserIDAction(
		entityManager, userID, startIndex, count);

	return action.execute();
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.ejb.SearchFacade#findWMS(java.lang.Long)
     */
    public WMSTO findWMS(Long WMSID) throws InstanceNotFoundException {
	WMS WMS = UserFacadeHelper.getWMS(entityManager, WMSID);

	if (WMS == null)
	    throw new InstanceNotFoundException(WMSID, WMS.class.getName());

	return UserFacadeHelper.toWMSTO(WMS);
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.ejb.SearchFacade#findWMSByURL(java.lang.String)
     */
    @Override
    public WMSTO findWMSByURL(String URL) throws InstanceNotFoundException {
	WMS WMS = SearchFacadeHelper.getWMSByURL(entityManager, URL);

	if (WMS == null)
	    throw new InstanceNotFoundException(URL, WMS.class.getName());

	return UserFacadeHelper.toWMSTO(WMS);
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.ejb.SearchFacade#findWMSs(int, int)
     */
    @Override
    public WMSChunkTO findWMSs(int startIndex, int count) {
	FindWMSsAction action = new FindWMSsAction(entityManager, startIndex,
		count);

	return action.execute();
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.ejb.SearchFacade#findWMSsByName(java.lang.String,
     *      int, int)
     */
    @Override
    public WMSChunkTO findWMSsByName(String name, int startIndex, int count) {
	FindWMSsByNameAction action = new FindWMSsByNameAction(entityManager,
		name, startIndex, count);

	return action.execute();
    }

    /*
     * (non-Javadoc)
     * 
     * @see mapviewer.model.searchfacade.ejb.SearchFacade#findWMSsByUser(java.lang.String,
     *      int, int)
     */
    public WMSChunkTO findWMSsByUser(String userID, int startIndex, int count) {
	FindWMSsByUserAction action = new FindWMSsByUserAction(entityManager,
		userID, startIndex, count);

	return action.execute();
    }

}
