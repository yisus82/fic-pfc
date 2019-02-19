package mapviewer.model.userfacade.ejb.actions;

import javax.persistence.EntityManager;

import mapviewer.model.userfacade.ejb.UserFacadeHelper;
import mapviewer.model.userfacade.util.PasswordEncrypter;
import mapviewer.model.userprofile.entity.UserProfile;
import mapviewer.model.userprofile.to.UserProfileDetailsTO;
import mapviewer.model.userprofile.to.UserProfileTO;
import es.udc.fbellas.j2ee.util.exceptions.DuplicateInstanceException;

public class RegisterUserAction {

    private EntityManager entityManager;

    private UserProfile userProfile;

    public RegisterUserAction(EntityManager entityManager, UserProfileTO userTO) {
	this.entityManager = entityManager;
	String encryptedPassword = PasswordEncrypter
		.crypt(userTO.getPassword());
	UserProfileDetailsTO details = userTO.getUserDetails();
	userProfile = new UserProfile(userTO.getUserID(), encryptedPassword,
		details.getFirstName(), details.getSurname(), details
			.getEmail(), details.getLocaleID());
    }

    public UserProfileTO execute() throws DuplicateInstanceException {
	if (entityManager.find(UserProfile.class, userProfile.getUserID()) == null) {
	    entityManager.persist(userProfile);
	    return UserFacadeHelper.toUserProfileTO(userProfile);
	}
	throw new DuplicateInstanceException(userProfile.getUserID(),
		UserProfile.class.getName());
    }

}
