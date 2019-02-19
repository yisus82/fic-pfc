package mapviewer.http.controller.frontcontroller;

import org.apache.struts.action.ActionMapping;

public class MapViewerActionMapping extends ActionMapping {

    private boolean authenticationRequired;

    public MapViewerActionMapping() {
	authenticationRequired = false;
    }

    public boolean getAuthenticationRequired() {
	return authenticationRequired;
    }

    public void setAuthenticationRequired(boolean authenticationRequired) {
	this.authenticationRequired = authenticationRequired;
    }

}
