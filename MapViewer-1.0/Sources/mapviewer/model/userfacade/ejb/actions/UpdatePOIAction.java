package mapviewer.model.userfacade.ejb.actions;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import mapviewer.model.poi.entity.POI;
import mapviewer.model.poi.to.POITO;
import mapviewer.model.tag.entity.Tag;
import mapviewer.model.tag.to.TagTO;
import mapviewer.model.userfacade.ejb.UserFacadeHelper;
import es.udc.fbellas.j2ee.util.exceptions.InstanceNotFoundException;

public class UpdatePOIAction {

    private EntityManager entityManager;

    private POITO POITO;

    private List<TagTO> tagTOs;

    public UpdatePOIAction(EntityManager entityManager, POITO POITO,
	    List<TagTO> tagTOs) {
	this.entityManager = entityManager;
	this.POITO = POITO;
	this.tagTOs = new ArrayList<TagTO>();
	for (TagTO tagTO : tagTOs)
	    if (!this.tagTOs.contains(tagTO)) this.tagTOs.add(tagTO);
    }

    public POITO execute() throws InstanceNotFoundException {
	POI POI = UserFacadeHelper.getPOI(entityManager, POITO.getPOIID());

	if (POI == null)
	    throw new InstanceNotFoundException(POITO.getPOIID(), POI.class
		    .getName());

	POI.setDescription(POITO.getDescription());
	POI.setHTML(POITO.getHTML());
	POI.setLatitude(POITO.getLatitude());
	POI.setLongitude(POITO.getLongitude());
	POI.setName(POITO.getName());

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
