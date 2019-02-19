package mapviewer.model.searchfacade.ejb.actions;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import mapviewer.model.map.entity.Map;
import mapviewer.model.map.to.MapTO;
import mapviewer.model.searchfacade.to.MapChunkTO;
import mapviewer.model.userfacade.ejb.UserFacadeHelper;

public class FindMapsByZoneAction {

    private final EntityManager entityManager;

    private final int startIndex;

    private final int count;

    private final Double minLatitude;

    private final Double minLongitude;

    private final Double maxLatitude;

    private final Double maxLongitude;

    public FindMapsByZoneAction(EntityManager entityManager,
	    Double minLatitude, Double minLongitude, Double maxLatitude,
	    Double maxLongitude, int startIndex, int count) {
	this.entityManager = entityManager;
	this.minLatitude = minLatitude;
	this.minLongitude = minLongitude;
	this.maxLatitude = maxLatitude;
	this.maxLongitude = maxLongitude;
	this.startIndex = startIndex;
	this.count = count;
    }

    public MapChunkTO execute() {
	/*
	 * Find count+1 maps to determine if there exist more maps above the
	 * specified range.
	 */
	Query query = entityManager
		.createQuery(
			"SELECT m FROM Map m WHERE m.minLatitude >= :minLatitude"
				+ " AND m.maxLatitude <= :maxLatitude AND m.minLongitude >= :minLongitude"
				+ " AND m.maxLongitude <= :maxLongitude ORDER BY m.name")
		.setParameter("minLatitude", minLatitude).setParameter(
			"maxLatitude", maxLatitude).setParameter(
			"minLongitude", minLongitude).setParameter(
			"maxLongitude", maxLongitude);
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
