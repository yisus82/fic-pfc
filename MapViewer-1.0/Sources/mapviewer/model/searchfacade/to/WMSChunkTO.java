package mapviewer.model.searchfacade.to;

import java.io.Serializable;
import java.util.List;

import mapviewer.model.wms.to.WMSTO;

public class WMSChunkTO implements Serializable {

    private List<WMSTO> WMSTOs;

    private boolean existMoreWMSs;

    public WMSChunkTO(List<WMSTO> WMSTOs, boolean existMoreWMSs) {
	this.WMSTOs = WMSTOs;
	this.existMoreWMSs = existMoreWMSs;
    }

    /**
     * @return the <code>existMoreWMSs</code>
     */
    public boolean getExistMoreWMSs() {
	return this.existMoreWMSs;
    }

    /**
     * @return the <code>WMSTOs</code>
     */
    public List<WMSTO> getWMSTOs() {
	return this.WMSTOs;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return new String("\nWMSTOs = " + WMSTOs + "\nExistMoreWMSs = "
		+ existMoreWMSs + "\n");
    }

}
