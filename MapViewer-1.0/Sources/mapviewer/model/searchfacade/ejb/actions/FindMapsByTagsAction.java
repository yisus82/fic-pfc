package mapviewer.model.searchfacade.ejb.actions;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import mapviewer.model.map.entity.Map;
import mapviewer.model.map.to.MapTO;
import mapviewer.model.searchfacade.to.MapChunkTO;
import mapviewer.model.tag.entity.Tag;
import mapviewer.model.userfacade.ejb.UserFacadeHelper;

public class FindMapsByTagsAction {

    private final EntityManager entityManager;

    private final int startIndex;

    private final int count;

    private final List<String> tags;

    public FindMapsByTagsAction(EntityManager entityManager, List<String> tags,
	    int startIndex, int count) {
	this.entityManager = entityManager;
	this.tags = tags;
	this.startIndex = startIndex;
	this.count = count;
    }

    public MapChunkTO execute() {
	/*
	 * Find count+1 maps to determine if there exist more maps above the
	 * specified range.
	 */
	if (tags.isEmpty())
	    return new MapChunkTO(new ArrayList<MapTO>(), false);
	Query query = entityManager.createQuery(
		"SELECT DISTINCT m FROM Map m, IN (m.tags) t WHERE t.tag IN (:tags) "
			+ "ORDER BY m.name").setParameter("tags", tags);
	if (startIndex > 0) query = query.setFirstResult(startIndex - 1);
	if (count > 0) query = query.setMaxResults(count + 1);

	List<Map> results = query.getResultList();
	List<Map> maps = new ArrayList<Map>();
	for (Map map : results) {
	    List<Tag> mapTags = map.getTags();
	    List<String> stringTags = new ArrayList<String>();
	    for (Tag tag : mapTags)
		stringTags.add(tag.getTag());
	    if (stringTags.containsAll(tags)) maps.add(map);
	}
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
