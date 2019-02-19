package mapviewer.model.searchfacade.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import mapviewer.model.layer.entity.Layer;
import mapviewer.model.map.entity.Map;
import mapviewer.model.poi.entity.POI;
import mapviewer.model.searchfacade.to.LayerDetailsTO;
import mapviewer.model.searchfacade.to.MapDetailsTO;
import mapviewer.model.searchfacade.to.POIDetailsTO;
import mapviewer.model.userfacade.ejb.UserFacadeHelper;
import mapviewer.model.wms.entity.WMS;

public class SearchFacadeHelper {

    public static WMS getWMSByURL(EntityManager entityManager, String URL) {
	Query query = entityManager.createQuery(
		"SELECT w FROM WMS w WHERE w.URL = :URL").setParameter("URL",
		URL);

	WMS WMS;
	try {
	    WMS = (WMS) query.getSingleResult();
	} catch (Exception e) {
	    WMS = null;
	}

	return WMS;
    }

    public static LayerDetailsTO toLayerDetailsTO(Layer layer) {
	return new LayerDetailsTO(layer.getLayerID(), layer.getTitle(), layer
		.getName(), layer.getStyleTitle(), layer.getStyleName(), layer
		.getMinLatitude(), layer.getMinLongitude(), layer
		.getMaxLatitude(), layer.getMaxLongitude(), UserFacadeHelper
		.toWMSTO(layer.getWMS()));
    }

    public static List<LayerDetailsTO> toLayerDetailsTOs(List<Layer> layers) {
	List<LayerDetailsTO> layerDetailsTOs = new ArrayList<LayerDetailsTO>();
	for (Layer layer : layers)
	    layerDetailsTOs.add(SearchFacadeHelper.toLayerDetailsTO(layer));
	return layerDetailsTOs;
    }

    public static MapDetailsTO toMapDetailsTO(Map map) {
	return new MapDetailsTO(map.getMapID(), map.getName(), map
		.getDescription(), map.getMinLatitude(), map.getMinLongitude(),
		map.getMaxLatitude(), map.getMaxLongitude(), map.getUser()
			.getUserID(), UserFacadeHelper.toTagTOs(map.getTags()));
    }

    public static List<MapDetailsTO> toMapDetailsTOs(List<Map> maps) {
	List<MapDetailsTO> mapDetailsTOs = new ArrayList<MapDetailsTO>();
	for (Map map : maps)
	    mapDetailsTOs.add(SearchFacadeHelper.toMapDetailsTO(map));
	return mapDetailsTOs;
    }

    public static POIDetailsTO toPOIDetailsTO(POI POI) {
	return new POIDetailsTO(POI.getPOIID(), POI.getName(), POI
		.getDescription(), POI.getLatitude(), POI.getLongitude(), POI
		.getHTML(), POI.getUser().getUserID(), UserFacadeHelper
		.toTagTOs(POI.getTags()));
    }

    public static List<POIDetailsTO> toPOIDetailsTOs(List<POI> POIs) {
	List<POIDetailsTO> POIDetailsTOs = new ArrayList<POIDetailsTO>();
	for (POI POI : POIs)
	    POIDetailsTOs.add(SearchFacadeHelper.toPOIDetailsTO(POI));
	return POIDetailsTOs;
    }

    private SearchFacadeHelper() {
    }

}
