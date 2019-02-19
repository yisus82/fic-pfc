package mapviewer.model.tag.to;

/**
 * A transfer object representing the information of a tag.
 */
public class TagTO {

    /**
     * The tag itself
     */
    private String tag;

    /**
     * A constructor to create <code>TagTO</code> objects.
     * 
     * @param tag
     *                the tag itself
     */
    public TagTO(String tag) {
	this.tag = tag;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
	if (this == obj) return true;

	if (obj instanceof TagTO) {
	    TagTO tag = (TagTO) obj;
	    return (tag.getTag().equals(this.tag));
	}

	return false;
    }

    /**
     * @return the <code>tag</code>
     */
    public String getTag() {
	return this.tag;
    }

    /**
     * @param tag
     *                the tag to set
     */
    public void setTag(String tag) {
	this.tag = tag;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return ("\nTag = " + this.tag + "\n");
    }

}
