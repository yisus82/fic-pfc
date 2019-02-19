package mapviewer.model.searchfacade.to;

import java.io.Serializable;
import java.util.List;

import mapviewer.model.map.to.MapTO;

public class MapChunkTO implements Serializable {

    private List<MapTO> mapTOs;

    private boolean existMoreMaps;

    public MapChunkTO(List<MapTO> mapTOs, boolean existMoreMaps) {
	this.mapTOs = mapTOs;
	this.existMoreMaps = existMoreMaps;
    }

    /**
     * @return the <code>existMoreMaps</code>
     */
    public boolean getExistMoreMaps() {
	return this.existMoreMaps;
    }

    /**
     * @return the <code>mapTOs</code>
     */
    public List<MapTO> getMapTOs() {
	return this.mapTOs;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return new String("\nMapTOs = " + mapTOs + "\nExistMoreMaps = "
		+ existMoreMaps + "\n");
    }

}
