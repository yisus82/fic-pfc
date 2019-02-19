package mapviewer.model.userfacade.ejb.actions;

import javax.persistence.EntityManager;

import mapviewer.model.userfacade.ejb.UserFacadeHelper;
import mapviewer.model.userfacade.exceptions.IncorrectPasswordException;
import mapviewer.model.userfacade.util.PasswordEncrypter;
import mapviewer.model.userprofile.entity.UserProfile;

public class ChangePasswordAction {

    private EntityManager entityManager;

    private String userID;

    private String oldEncryptedPassword;

    private String newEncryptedPassword;

    public ChangePasswordAction(EntityManager entityManager, String userID,
	    String oldClearPassword, String newClearPassword) {
	this.entityManager = entityManager;
	this.userID = userID;
	this.oldEncryptedPassword = PasswordEncrypter.crypt(oldClearPassword);
	this.newEncryptedPassword = PasswordEncrypter.crypt(newClearPassword);
    }

    public void execute() throws IncorrectPasswordException {
	UserProfile userProfile = UserFacadeHelper.getUserProfile(
		entityManager, userID);

	if (!userProfile.getPassword().equals(oldEncryptedPassword)) {
	    throw new IncorrectPasswordException(userProfile.getUserID());
	}

	userProfile.setPassword(newEncryptedPassword);
    }

}
