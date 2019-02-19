package mapviewer.model.userfacade.exceptions;

import es.udc.fbellas.j2ee.util.exceptions.InstanceException;

public class AlreadyOwnedException extends InstanceException {

    public AlreadyOwnedException(Object key, String className) {
	super("Already owned", key, className);
    }

}
