package mapviewer.model.searchfacade.ejb.actions;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import mapviewer.model.poi.entity.POI;
import mapviewer.model.poi.to.POITO;
import mapviewer.model.searchfacade.to.POIChunkTO;
import mapviewer.model.tag.entity.Tag;
import mapviewer.model.userfacade.ejb.UserFacadeHelper;

public class FindPOIsByTagsAction {

    private final EntityManager entityManager;

    private final int startIndex;

    private final int count;

    private final List<String> tags;

    public FindPOIsByTagsAction(EntityManager entityManager, List<String> tags,
	    int startIndex, int count) {
	this.entityManager = entityManager;
	this.tags = tags;
	this.startIndex = startIndex;
	this.count = count;
    }

    public POIChunkTO execute() {
	/*
	 * Find count+1 POIs to determine if there exist more POIs above the
	 * specified range.
	 */
	if (tags.isEmpty())
	    return new POIChunkTO(new ArrayList<POITO>(), false);
	Query query = entityManager.createQuery(
		"SELECT DISTINCT p FROM POI p, IN (p.tags) t WHERE t.tag IN (:tags) "
			+ "ORDER BY p.name").setParameter("tags", tags);
	if (startIndex > 0) query = query.setFirstResult(startIndex - 1);
	if (count > 0) query = query.setMaxResults(count + 1);

	List<POI> results = query.getResultList();
	List<POI> POIs = new ArrayList<POI>();
	for (POI POI : results) {
	    List<Tag> POItags = POI.getTags();
	    List<String> stringTags = new ArrayList<String>();
	    for (Tag tag : POItags)
		stringTags.add(tag.getTag());
	    if (stringTags.containsAll(tags)) POIs.add(POI);
	}
	boolean existMorePOIs = ((POIs.size() == (count + 1)) && !(POIs
		.isEmpty()));

	/*
	 * Remove the last POI from the returned list if there exist more POIs
	 * above the specified range.
	 */
	if (existMorePOIs) POIs.remove(POIs.size() - 1);

	List<POITO> POITOs = UserFacadeHelper.toPOITOs(POIs);

	return new POIChunkTO(POITOs, existMorePOIs);
    }

}
