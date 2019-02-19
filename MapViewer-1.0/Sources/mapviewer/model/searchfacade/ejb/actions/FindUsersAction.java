package mapviewer.model.searchfacade.ejb.actions;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import mapviewer.model.searchfacade.to.UserProfileChunkTO;
import mapviewer.model.userfacade.ejb.UserFacadeHelper;
import mapviewer.model.userprofile.entity.UserProfile;
import mapviewer.model.userprofile.to.UserProfileTO;

public class FindUsersAction {

    private final EntityManager entityManager;

    private final int startIndex;

    private final int count;

    public FindUsersAction(EntityManager entityManager, int startIndex,
	    int count) {
	this.entityManager = entityManager;
	this.startIndex = startIndex;
	this.count = count;
    }

    public UserProfileChunkTO execute() {
	/*
	 * Find count+1 users to determine if there exist more users above the
	 * specified range.
	 */
	Query query = entityManager
		.createQuery("SELECT u FROM UserProfile u ORDER BY u.userID");
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
