package mapviewer.model.searchfacade.to;

import java.io.Serializable;
import java.util.List;

import mapviewer.model.poi.to.POITO;

public class POIChunkTO implements Serializable {

    private List<POITO> POITOs;

    private boolean existMorePOIs;

    public POIChunkTO(List<POITO> POITOs, boolean existMorePOIs) {
	this.POITOs = POITOs;
	this.existMorePOIs = existMorePOIs;
    }

    /**
     * @return the <code>existMorePOIs</code>
     */
    public boolean getExistMorePOIs() {
	return this.existMorePOIs;
    }

    /**
     * @return the <code>POITOs</code>
     */
    public List<POITO> getPOITOs() {
	return this.POITOs;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return new String("\nPOITOs = " + POITOs + "\nExistMorePOIs = "
		+ existMorePOIs + "\n");
    }

}
