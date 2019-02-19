package mapviewer.http.controller.util;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import mapviewer.http.util.Option;

/**
 * An utility class to compare to <code>Option</code>s
 */
public class OptionComparator implements Comparator<Option> {

    /**
     * The locale used to compare
     */
    private Locale locale;

    /**
     * A constructor to create <code>OptionComparator</code> objects.
     * 
     * @param locale
     *                the locale used to compare
     */
    public OptionComparator(Locale locale) {
	this.locale = locale;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(Option o1, Option o2) {
	Collator collator = Collator.getInstance(locale);

	return collator.compare(o1.getLabelProperty(), o2.getLabelProperty());
    }

}
