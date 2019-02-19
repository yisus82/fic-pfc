package mapviewer.model.searchfacade.ejb.actions;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import mapviewer.model.poi.entity.POI;
import mapviewer.model.poi.to.POITO;
import mapviewer.model.searchfacade.to.POIChunkTO;
import mapviewer.model.userfacade.ejb.UserFacadeHelper;

public class FindPOIsByNameAction {

    private final EntityManager entityManager;

    private final int startIndex;

    private final int count;

    private final String name;

    public FindPOIsByNameAction(EntityManager entityManager, String name,
	    int startIndex, int count) {
	this.entityManager = entityManager;
	this.name = name;
	this.startIndex = startIndex;
	this.count = count;
    }

    public POIChunkTO execute() {
	/*
	 * Find count+1 POIs to determine if there exist more POIs above the
	 * specified range.
	 */
	String queryString = "SELECT p FROM POI p WHERE LOWER(p.name) LIKE '%"
		+ name.toLowerCase() + "%' ORDER BY p.name";
	Query query = entityManager.createQuery(queryString);
	if (startIndex > 0) query = query.setFirstResult(startIndex - 1);
	if (count > 0) query = query.setMaxResults(count + 1);

	List<POI> POIs = query.getResultList();
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
