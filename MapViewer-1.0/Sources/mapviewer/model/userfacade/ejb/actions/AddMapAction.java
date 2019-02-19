package mapviewer.model.userfacade.ejb.actions;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import mapviewer.model.map.entity.Map;
import mapviewer.model.map.to.MapTO;
import mapviewer.model.tag.entity.Tag;
import mapviewer.model.tag.to.TagTO;
import mapviewer.model.userfacade.ejb.UserFacadeHelper;

public class AddMapAction {

    private EntityManager entityManager;

    private Map map;

    private List<TagTO> tagTOs;

    public AddMapAction(EntityManager entityManager, MapTO mapTO,
	    List<TagTO> tagTOs) {
	this.entityManager = entityManager;
	map = UserFacadeHelper.toMap(entityManager, mapTO);
	this.tagTOs = new ArrayList<TagTO>();
	for (TagTO tagTO : tagTOs)
	    if (!this.tagTOs.contains(tagTO)) this.tagTOs.add(tagTO);
    }

    public MapTO execute() {
	entityManager.persist(map);

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
