package mapviewer.model.searchfacade.ejb.actions;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import mapviewer.model.map.entity.Map;
import mapviewer.model.map.to.MapTO;
import mapviewer.model.searchfacade.to.MapChunkTO;
import mapviewer.model.userfacade.ejb.UserFacadeHelper;

public class FindMapsByNameAction {

    private final EntityManager entityManager;

    private final int startIndex;

    private final int count;

    private final String name;

    public FindMapsByNameAction(EntityManager entityManager, String name,
	    int startIndex, int count) {
	this.entityManager = entityManager;
	this.name = name;
	this.startIndex = startIndex;
	this.count = count;
    }

    public MapChunkTO execute() {
	/*
	 * Find count+1 maps to determine if there exist more maps above the
	 * specified range.
	 */
	String queryString = "SELECT m FROM Map m WHERE LOWER(m.name) LIKE '%"
		+ name.toLowerCase() + "%' ORDER BY m.name";
	Query query = entityManager.createQuery(queryString);
	if (startIndex > 0) query = query.setFirstResult(startIndex - 1);
	if (count > 0) query = query.setMaxResults(count + 1);

	List<Map> maps = query.getResultList();
	boolean existMoreMaps = ((maps.size() == (count + 1)) && !(maps
		.isEmpty()));

	/*
	 * Remove the last map from the returned list if there exist more maps
	 * above the specified range.
	 */
	if (existMoreMaps) maps.remove(maps.size() - 1);
	List<MapTO> mapTOs = UserFacadeHelper.toMapTOs(maps);

	return new MapChunkTO(mapTOs, existMoreMaps);
    }

}
