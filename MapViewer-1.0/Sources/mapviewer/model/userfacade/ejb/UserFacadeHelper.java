package mapviewer.model.userfacade.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import mapviewer.model.layer.entity.Layer;
import mapviewer.model.layer.to.LayerTO;
import mapviewer.model.map.entity.Map;
import mapviewer.model.map.to.MapTO;
import mapviewer.model.poi.entity.POI;
import mapviewer.model.poi.to.POITO;
import mapviewer.model.tag.entity.Tag;
import mapviewer.model.tag.to.TagTO;
import mapviewer.model.userprofile.entity.UserProfile;
import mapviewer.model.userprofile.to.UserProfileDetailsTO;
import mapviewer.model.userprofile.to.UserProfileTO;
import mapviewer.model.wms.entity.WMS;
import mapviewer.model.wms.to.WMSTO;

public class UserFacadeHelper {

    public static Layer getLayer(EntityManager entityManager, Long layerID) {
	return entityManager.find(Layer.class, layerID);
    }

    public static Map getMap(EntityManager entityManager, Long mapID) {
	return entityManager.find(Map.class, mapID);
    }

    public static POI getPOI(EntityManager entityManager, Long POIID) {
	return entityManager.find(POI.class, POIID);
    }

    public static Tag getTag(EntityManager entityManager, String tag) {
	return entityManager.find(Tag.class, tag);
    }

    public static UserProfile getUserProfile(EntityManager entityManager,
	    String userID) {
	return entityManager.find(UserProfile.class, userID);
    }

    public static WMS getWMS(EntityManager entityManager, Long WMSID) {
	return entityManager.find(WMS.class, WMSID);
    }

    public static Layer toLayer(EntityManager entityManager, LayerTO layerTO) {
	return new Layer(layerTO.getTitle(), layerTO.getName(), layerTO
		.getStyleTitle(), layerTO.getStyleName(), layerTO
		.getMinLatitude(), layerTO.getMinLongitude(), layerTO
		.getMaxLatitude(), layerTO.getMaxLongitude(), UserFacadeHelper
		.getWMS(entityManager, layerTO.getWMSID()));
    }

    public static List<Layer> toLayers(EntityManager entityManager,
	    List<LayerTO> layerTOs) {
	List<Layer> layers = new ArrayList<Layer>();
	for (LayerTO layerTO : layerTOs)
	    layers.add(UserFacadeHelper.toLayer(entityManager, layerTO));
	return layers;
    }

    public static LayerTO toLayerTO(Layer layer) {
	return new LayerTO(layer.getLayerID(), layer.getTitle(), layer
		.getName(), layer.getStyleTitle(), layer.getStyleName(), layer
		.getMinLatitude(), layer.getMinLongitude(), layer
		.getMaxLatitude(), layer.getMaxLongitude(), layer.getWMS()
		.getWMSID());
    }

    public static List<LayerTO> toLayerTOs(List<Layer> layers) {
	List<LayerTO> layerTOs = new ArrayList<LayerTO>();
	for (Layer layer : layers)
	    layerTOs.add(UserFacadeHelper.toLayerTO(layer));
	return layerTOs;
    }

    public static Map toMap(EntityManager entityManager, MapTO mapTO) {
	return new Map(mapTO.getName(), mapTO.getDescription(), mapTO
		.getMinLatitude(), mapTO.getMinLongitude(), mapTO
		.getMaxLatitude(), mapTO.getMaxLongitude(), UserFacadeHelper
		.getUserProfile(entityManager, mapTO.getUserID()));
    }

    public static List<Map> toMaps(EntityManager entityManager,
	    List<MapTO> mapTOs) {
	List<Map> maps = new ArrayList<Map>();
	for (MapTO mapTO : mapTOs)
	    maps.add(UserFacadeHelper.toMap(entityManager, mapTO));
	return maps;
    }

    public static MapTO toMapTO(Map map) {
	return new MapTO(map.getMapID(), map.getName(), map.getDescription(),
		map.getMinLatitude(), map.getMinLongitude(), map
			.getMaxLatitude(), map.getMaxLongitude(), map.getUser()
			.getUserID());
    }

    public static List<MapTO> toMapTOs(List<Map> maps) {
	List<MapTO> mapTOs = new ArrayList<MapTO>();
	for (Map map : maps)
	    mapTOs.add(UserFacadeHelper.toMapTO(map));
	return mapTOs;
    }

    public static POI toPOI(EntityManager entityManager, POITO POITO) {
	return new POI(POITO.getName(), POITO.getDescription(), POITO
		.getLatitude(), POITO.getLongitude(), POITO.getHTML(),
		UserFacadeHelper.getUserProfile(entityManager, POITO
			.getUserID()));
    }

    public static List<POI> toPOIs(EntityManager entityManager,
	    List<POITO> POITOs) {
	List<POI> POIs = new ArrayList<POI>();
	for (POITO POITO : POITOs)
	    POIs.add(UserFacadeHelper.toPOI(entityManager, POITO));
	return POIs;
    }

    public static POITO toPOITO(POI POI) {
	return new POITO(POI.getPOIID(), POI.getName(), POI.getDescription(),
		POI.getLatitude(), POI.getLongitude(), POI.getHTML(), POI
			.getUser().getUserID());
    }

    public static List<POITO> toPOITOs(List<POI> POIs) {
	List<POITO> POITOs = new ArrayList<POITO>();
	for (POI POI : POIs)
	    POITOs.add(UserFacadeHelper.toPOITO(POI));
	return POITOs;
    }

    public static Tag toTag(EntityManager entityManager, TagTO tagTO) {
	return new Tag(tagTO.getTag());
    }

    public static List<Tag> toTags(EntityManager entityManager,
	    List<TagTO> tagTOs) {
	List<Tag> tags = new ArrayList<Tag>();
	for (TagTO tagTO : tagTOs)
	    tags.add(UserFacadeHelper.toTag(entityManager, tagTO));
	return tags;
    }

    public static TagTO toTagTO(Tag tag) {
	return new TagTO(tag.getTag());
    }

    public static List<TagTO> toTagTOs(List<Tag> tags) {
	List<TagTO> tagTOs = new ArrayList<TagTO>();
	for (Tag tag : tags)
	    tagTOs.add(UserFacadeHelper.toTagTO(tag));
	return tagTOs;
    }

    public static UserProfile toUserProfile(EntityManager entityManager,
	    UserProfileTO userProfileTO) {
	UserProfileDetailsTO details = userProfileTO.getUserDetails();
	return new UserProfile(userProfileTO.getUserID(), userProfileTO
		.getPassword(), details.getFirstName(), details.getSurname(),
		details.getEmail(), details.getLocaleID());
    }

    public static List<UserProfile> toUserProfiles(EntityManager entityManager,
	    List<UserProfileTO> userProfileTOs) {
	List<UserProfile> userProfiles = new ArrayList<UserProfile>();
	for (UserProfileTO userProfileTO : userProfileTOs)
	    userProfiles.add(UserFacadeHelper.toUserProfile(entityManager,
		    userProfileTO));
	return userProfiles;
    }

    public static UserProfileTO toUserProfileTO(UserProfile userProfile) {
	return new UserProfileTO(userProfile.getUserID(), userProfile
		.getPassword(), new UserProfileDetailsTO(userProfile
		.getFirstName(), userProfile.getSurname(), userProfile
		.getEmail(), userProfile.getLocaleID()));
    }

    public static List<UserProfileTO> toUserProfileTOs(
	    List<UserProfile> userProfiles) {
	List<UserProfileTO> userProfileTOs = new ArrayList<UserProfileTO>();
	for (UserProfile userProfile : userProfiles)
	    userProfileTOs.add(UserFacadeHelper.toUserProfileTO(userProfile));
	return userProfileTOs;
    }

    public static WMS toWMS(EntityManager entityManager, WMSTO WMSTO) {
	return new WMS(WMSTO.getName(), WMSTO.getDescription(), WMSTO.getURL(),
		UserFacadeHelper.getUserProfile(entityManager, WMSTO
			.getUserID()));
    }

    public static List<WMS> toWMSs(EntityManager entityManager,
	    List<WMSTO> WMSTOs) {
	List<WMS> WMSs = new ArrayList<WMS>();
	for (WMSTO WMSTO : WMSTOs)
	    WMSs.add(UserFacadeHelper.toWMS(entityManager, WMSTO));
	return WMSs;
    }

    public static WMSTO toWMSTO(WMS WMS) {
	return new WMSTO(WMS.getWMSID(), WMS.getName(), WMS.getDescription(),
		WMS.getURL(), WMS.getUser().getUserID());
    }

    public static List<WMSTO> toWMSTOs(List<WMS> WMSs) {
	List<WMSTO> WMSTOs = new ArrayList<WMSTO>();
	for (WMS WMS : WMSs)
	    WMSTOs.add(UserFacadeHelper.toWMSTO(WMS));
	return WMSTOs;
    }

    private UserFacadeHelper() {
    }

}
