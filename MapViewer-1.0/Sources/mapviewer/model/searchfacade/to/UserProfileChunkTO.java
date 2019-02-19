package mapviewer.model.searchfacade.to;

import java.io.Serializable;
import java.util.List;

import mapviewer.model.userprofile.to.UserProfileTO;

public class UserProfileChunkTO implements Serializable {

    private List<UserProfileTO> userProfileTOs;

    private boolean existMoreUserProfiles;

    public UserProfileChunkTO(List<UserProfileTO> userProfileTOs,
	    boolean existMoreUserProfiles) {
	this.userProfileTOs = userProfileTOs;
	this.existMoreUserProfiles = existMoreUserProfiles;
    }

    /**
     * @return the <code>existMoreUserProfiles</code>
     */
    public boolean getExistMoreUserProfiles() {
	return this.existMoreUserProfiles;
    }

    /**
     * @return the <code>userProfileTOs</code>
     */
    public List<UserProfileTO> getUserProfileTOs() {
	return this.userProfileTOs;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return new String("\nUserProfileTOs = " + userProfileTOs
		+ "\nExistMoreUserProfiles = " + existMoreUserProfiles + "\n");
    }

}
