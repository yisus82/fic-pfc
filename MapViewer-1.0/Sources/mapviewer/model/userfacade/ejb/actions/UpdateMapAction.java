package mapviewer.model.userfacade.ejb.actions;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import mapviewer.model.map.entity.Map;
import mapviewer.model.map.to.MapTO;
import mapviewer.model.tag.entity.Tag;
import mapviewer.model.tag.to.TagTO;
import mapviewer.model.userfacade.ejb.UserFacadeHelper;
import es.udc.fbellas.j2ee.util.exceptions.InstanceNotFoundException;

public class UpdateMapAction {

    private EntityManager entityManager;

    private MapTO mapTO;

    private List<TagTO> tagTOs;

    public UpdateMapAction(EntityManager entityManager, MapTO mapTO,
	    List<TagTO> tagTOs) {
	this.entityManager = entityManager;
	this.mapTO = mapTO;
	this.tagTOs = new ArrayList<TagTO>();
	for (TagTO tagTO : tagTOs)
	    if (!this.tagTOs.contains(tagTO)) this.tagTOs.add(tagTO);
    }

    public MapTO execute() throws InstanceNotFoundException {
	Map map = UserFacadeHelper.getMap(entityManager, mapTO.getMapID());

	if (map == null)
	    throw new InstanceNotFoundException(mapTO.getMapID(), Map.class
		    .getName());

	map.setDescription(mapTO.getDescription());
	map.setName(mapTO.getName());
	map.setMaxLatitude(mapTO.getMaxLatitude());
	map.setMaxLongitude(mapTO.getMaxLongitude());
	map.setMinLatitude(mapTO.getMinLatitude());
	map.setMinLongitude(mapTO.getMinLongitude());

	List<Tag> tags = new ArrayList<Tag>();

	for (TagTO tagTO : tagTOs) {
	    Tag tag = UserFacadeHelper.toTag(entityManager, tagTO);
	    tags.add(tag);
	    if (UserFacadeHelper.getTag(entityManager, tag.getTag()) == null)
		entityManager.persist(tag);
	}

	map.setTags(tags);

	return UserFacadeHelper.toMapTO(map);
    }

}
