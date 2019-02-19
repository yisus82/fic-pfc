package mapviewer.model.userfacade.ejb.actions;

import java.util.Locale;

import javax.persistence.EntityManager;

import mapviewer.model.userfacade.ejb.UserFacadeHelper;
import mapviewer.model.userfacade.exceptions.IncorrectPasswordException;
import mapviewer.model.userfacade.to.LoginResultTO;
import mapviewer.model.userfacade.util.PasswordEncrypter;
import mapviewer.model.userprofile.entity.UserProfile;
import es.udc.fbellas.j2ee.util.exceptions.InstanceNotFoundException;

public class LoginAction {

    private EntityManager entityManager;

    private String userID;

    private String encryptedPassword;

    public LoginAction(EntityManager entityManager, String userID,
	    String password, boolean passwordIsEncrypted) {
	this.entityManager = entityManager;
	this.userID = userID;

	if (passwordIsEncrypted) {
	    encryptedPassword = password;
	} else {
	    encryptedPassword = PasswordEncrypter.crypt(password);
	}
    }

    public LoginResultTO execute() throws IncorrectPasswordException,
	    InstanceNotFoundException {
	UserProfile userProfile = UserFacadeHelper.getUserProfile(
		entityManager, userID);

	if (userProfile == null) {
	    throw new InstanceNotFoundException(userID, UserProfile.class
		    .getName());
	}

	if (!userProfile.getPassword().equals(encryptedPassword)) {
	    throw new IncorrectPasswordException(userID);
	}

	Locale locale = Locale.getDefault();

	try {
	    String[] localeStrings = userProfile.getLocaleID().split(",");
	    if (localeStrings.length > 1)
		locale = new Locale(localeStrings[0], localeStrings[1]);
	    locale = new Locale(localeStrings[0]);
	} catch (Exception e) {
	}

	return new LoginResultTO(userProfile.getUserID(), userProfile
		.getPassword(), locale);
    }

}
