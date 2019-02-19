package mapviewer.model.userfacade.ejb.actions;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import mapviewer.model.poi.entity.POI;
import mapviewer.model.poi.to.POITO;
import mapviewer.model.tag.entity.Tag;
import mapviewer.model.tag.to.TagTO;
import mapviewer.model.userfacade.ejb.UserFacadeHelper;

public class AddPOIAction {

    private EntityManager entityManager;

    private POI POI;

    private List<TagTO> tagTOs;

    public AddPOIAction(EntityManager entityManager, POITO POITO,
	    List<TagTO> tagTOs) {
	this.entityManager = entityManager;
	POI = UserFacadeHelper.toPOI(entityManager, POITO);
	this.tagTOs = new ArrayList<TagTO>();
	for (TagTO tagTO : tagTOs)
	    if (!this.tagTOs.contains(tagTO)) this.tagTOs.add(tagTO);
    }

    public POITO execute() {
	entityManager.persist(POI);

	List<Tag> tags = new ArrayList<Tag>();

	for (TagTO tagTO : tagTOs) {
	    Tag tag = UserFacadeHelper.toTag(entityManager, tagTO);
	    tags.add(tag);
	    if (UserFacadeHelper.getTag(entityManager, tag.getTag()) == null)
		entityManager.persist(tag);
	}

	POI.setTags(tags);

	return UserFacadeHelper.toPOITO(POI);
    }

}
