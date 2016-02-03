package evaluation.evalBench.images;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import evaluation.evalBench.images.ValuedImage;
import evaluation.evalBench.task.Task;

/**
 * extends {@link Task} with an array of {@link ValuedImage} and an optional base url.
 * @author Alexander Rind
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ImageTask extends Task {
	
	@XmlElement(name="base-url", required=false)
	private String baseUrl = ""; 
	
	@XmlElement
	private ValuedImage[] images;

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String url) {
		this.baseUrl = url;
	}
	
	public ValuedImage[] getImages() {
		return images;
	}

	public void setImages(ValuedImage[] images) {
		this.images = images;
	}

	public ImageTask() {
		super();
	}

	public ImageTask(String aTaskId, String aTaskDescription,
			String aTaskInstruction) {
		super(aTaskId, aTaskDescription, aTaskInstruction);
	}
}
