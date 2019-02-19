package mapviewer.http.util;

/**
 * A class to represent options for use in collections that are utilized by the
 * <code>&lt;html:options&gt;</code> tag.
 */
public class Option implements Comparable<Option> {

    /**
     * The property attribute value
     */
    private String property;

    /**
     * The labelProperty attribute value
     */
    private String labelProperty;

    /**
     * A constructor to create <code>Option</code> objects.
     * 
     * @param property
     *                the property attribute value
     * @param labelProperty
     *                the labelProperty attribute value
     */
    public Option(String property, String labelProperty) {
	this.property = property;
	this.labelProperty = labelProperty;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Option o) {
	return this.labelProperty.compareTo(o.labelProperty);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
	if (this == obj) return true;

	if (obj instanceof Option) {
	    Option option = (Option) obj;
	    return (option.getLabelProperty().equals(this.labelProperty) && option
		    .getProperty().equals(this.property));
	}

	return false;
    }

    /**
     * @return the <code>labelProperty</code>
     */
    public String getLabelProperty() {
	return this.labelProperty;
    }

    /**
     * @return the <code>property</code>
     */
    public String getProperty() {
	return this.property;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	return (this.labelProperty.hashCode() | this.property.hashCode());
    }

    /**
     * @param labelProperty
     *                the labelProperty to set
     */
    public void setLabelProperty(String labelProperty) {
	this.labelProperty = labelProperty;
    }

    /**
     * @param property
     *                the property to set
     */
    public void setProperty(String property) {
	this.property = property;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return ("\nLabel property = " + this.labelProperty + "\nProperty = " + this.property);
    }

}
