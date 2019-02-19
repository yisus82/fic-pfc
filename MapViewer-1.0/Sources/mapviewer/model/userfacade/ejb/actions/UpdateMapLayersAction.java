package mapviewer.model.userfacade.ejb.actions;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import mapviewer.model.layer.entity.Layer;
import mapviewer.model.map.entity.Map;
import mapviewer.model.userfacade.ejb.UserFacadeHelper;

public class UpdateMapLayersAction {

    private EntityManager entityManager;

    private Long mapID;

    private List<Long> layerIDs;

    public UpdateMapLayersAction(EntityManager entityManager, Long mapID,
	    List<Long> layerIDs) {
	this.entityManager = entityManager;
	this.mapID = mapID;
	this.layerIDs = layerIDs;
    }

    public void execute() {
	Map map = UserFacadeHelper.getMap(entityManager, mapID);
	List<Layer> layers = new ArrayList<Layer>();
	for (Long layerID : layerIDs)
	    layers.add(UserFacadeHelper.getLayer(entityManager, layerID));
	map.setLayers(layers);
    }
}
