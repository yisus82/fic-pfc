package mapviewer.model.searchfacade.to;

import java.io.Serializable;
import java.util.List;

public class LayerChunkTO implements Serializable {

    private List<LayerDetailsTO> layerTOs;

    private boolean existMoreLayers;

    public LayerChunkTO(List<LayerDetailsTO> layerTOs, boolean existMoreLayers) {
	this.layerTOs = layerTOs;
	this.existMoreLayers = existMoreLayers;
    }

    /**
     * @return the <code>existMoreLayers</code>
     */
    public boolean getExistMoreLayers() {
	return this.existMoreLayers;
    }

    /**
     * @return the <code>layerTOs</code>
     */
    public List<LayerDetailsTO> getLayerTOs() {
	return this.layerTOs;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return new String("\nLayerTOs = " + layerTOs + "\nExistMoreLayers = "
		+ existMoreLayers + "\n");
    }

}
