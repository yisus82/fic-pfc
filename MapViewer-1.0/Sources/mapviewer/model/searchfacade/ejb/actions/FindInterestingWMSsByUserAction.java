package mapviewer.model.searchfacade.ejb.actions;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import mapviewer.model.searchfacade.to.WMSChunkTO;
import mapviewer.model.userfacade.ejb.UserFacadeHelper;
import mapviewer.model.wms.entity.WMS;
import mapviewer.model.wms.to.WMSTO;

public class FindInterestingWMSsByUserAction {

    private final EntityManager entityManager;

    private final int startIndex;

    private final int count;

    private final String userID;

    public FindInterestingWMSsByUserAction(EntityManager entityManager,
	    String userID, int startIndex, int count) {
	this.entityManager = entityManager;
	this.userID = userID;
	this.startIndex = startIndex;
	this.count = count;
    }

    public WMSChunkTO execute() {
	/*
	 * Find count+1 WMSs to determine if there exist more WMSs above the
	 * specified range.
	 */
	Query query = entityManager
		.createQuery(
			"SELECT u.interestingWMSs FROM UserProfile u WHERE u.userID = :userID")
		.setParameter("userID", userID);
	if (startIndex > 0) query = query.setFirstResult(startIndex - 1);
	if (count > 0) query = query.setMaxResults(count + 1);

	List<WMS> WMSs = query.getResultList();
	boolean existMoreWMSs = ((WMSs.size() == (count + 1)) && !(WMSs
		.isEmpty()));

	/*
	 * Remove the last WMS from the returned list if there exist more WMSs
	 * above the specified range.
	 */
	if (existMoreWMSs) WMSs.remove(WMSs.size() - 1);

	List<WMSTO> WMSTOs = UserFacadeHelper.toWMSTOs(WMSs);

	return new WMSChunkTO(WMSTOs, existMoreWMSs);
    }

}
