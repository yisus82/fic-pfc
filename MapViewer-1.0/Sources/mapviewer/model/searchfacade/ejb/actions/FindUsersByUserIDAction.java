package mapviewer.model.searchfacade.ejb.actions;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import mapviewer.model.searchfacade.to.UserProfileChunkTO;
import mapviewer.model.userfacade.ejb.UserFacadeHelper;
import mapviewer.model.userprofile.entity.UserProfile;
import mapviewer.model.userprofile.to.UserProfileTO;

public class FindUsersByUserIDAction {

    private final EntityManager entityManager;

    private final String userID;

    private final int startIndex;

    private final int count;

    public FindUsersByUserIDAction(EntityManager entityManager, String userID,
	    int startIndex, int count) {
	this.entityManager = entityManager;
	this.userID = userID;
	this.startIndex = startIndex;
	this.count = count;
    }

    public UserProfileChunkTO execute() {
	/*
	 * Find count+1 users to determine if there exist more users above the
	 * specified range.
	 */
	String queryString = "SELECT u FROM UserProfile u WHERE LOWER(u.userID) LIKE '%"
		+ userID.toLowerCase() + "%' ORDER BY u.userID";
	Query query = entityManager.createQuery(queryString);
	if (startIndex > 0) query = query.setFirstResult(startIndex - 1);
	if (count > 0) query = query.setMaxResults(count + 1);

	List<UserProfile> userProfiles = query.getResultList();
	boolean existMoreUserProfiles = ((userProfiles.size() == (count + 1)) && !(userProfiles
		.isEmpty()));

	/*
	 * Remove the last user from the returned list if there exist more users
	 * above the specified range.
	 */
	if (existMoreUserProfiles)
	    userProfiles.remove(userProfiles.size() - 1);

	List<UserProfileTO> userProfileTOs = UserFacadeHelper
		.toUserProfileTOs(userProfiles);

	return new UserProfileChunkTO(userProfileTOs, existMoreUserProfiles);
    }

}
