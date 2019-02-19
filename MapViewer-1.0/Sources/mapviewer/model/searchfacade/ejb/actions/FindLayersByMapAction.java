package mapviewer.model.searchfacade.ejb.actions;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import mapviewer.model.layer.entity.Layer;
import mapviewer.model.searchfacade.ejb.SearchFacadeHelper;
import mapviewer.model.searchfacade.to.LayerChunkTO;
import mapviewer.model.searchfacade.to.LayerDetailsTO;

public class FindLayersByMapAction {

    private final EntityManager entityManager;

    private final int startIndex;

    private final int count;

    private final Long mapID;

    public FindLayersByMapAction(EntityManager entityManager, Long mapID,
	    int startIndex, int count) {
	this.entityManager = entityManager;
	this.mapID = mapID;
	this.startIndex = startIndex;
	this.count = count;
    }

    public LayerChunkTO execute() {
	/*
	 * Find count+1 layers to determine if there exist more layers above the
	 * specified range.
	 */
	Query query = entityManager.createQuery(
		"SELECT m.layers FROM Map m WHERE m.mapID = :mapID")
		.setParameter("mapID", mapID);
	if (startIndex > 0) query = query.setFirstResult(startIndex - 1);
	if (count > 0) query = query.setMaxResults(count + 1);

	List<Layer> layers = query.getResultList();
	boolean existMoreLayers = ((layers.size() == (count + 1)) && !(layers
		.isEmpty()));

	/*
	 * Remove the last layer from the returned list if there exist more
	 * layers above the specified range.
	 */
	if (existMoreLayers) layers.remove(layers.size() - 1);

	List<LayerDetailsTO> layerTOs = SearchFacadeHelper
		.toLayerDetailsTOs(layers);

	return new LayerChunkTO(layerTOs, existMoreLayers);
    }

}
