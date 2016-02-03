package evaluation.evalBench.images;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

/**
 * basic data structure for an image url with optional double value.
 * <tt>NaN</tt> is used for undefined value.
 * Objects are used by {@link ImageTask}.
 * @author Alexander Rind
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ValuedImage {
	@XmlValue
	public String url;
	
	@XmlAttribute(required=false)
	public double value = Double.NaN;

	@XmlAttribute(required=false, name="initial-selection")
	public boolean initialSelection = false;

	@Override
	public String toString() {
		return value + "|" + url;
	}

}
