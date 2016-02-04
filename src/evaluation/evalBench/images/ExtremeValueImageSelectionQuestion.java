package evaluation.evalBench.images;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import evaluation.evalBench.EvaluationManager;
import evaluation.evalBench.task.Task;

/**
 * Question to select image with maximum/minimum annotated value.
 * This be used for a task to find the maximum at a marked time,
 * number of clusters, homogeneity, ...
 * @author Alexander Rind
 */
public class ExtremeValueImageSelectionQuestion extends
		SingleImageSelectionQuestion {

	@XmlElement(required=false)
	boolean identifyMinimum = false;

	// we completely ignore #correctAnswer of the base class
	// so that it cannot be loaded from the task list XML
	@XmlTransient
	private ValuedImage correctImage = null;

	private ValuedImage determineCorrectImage() {
		if (correctImage == null) {
			ValuedImage img = null;
			Task task = EvaluationManager.getInstance().getSessionGroup().getActiveSubSession().getCurrentTask();
			ValuedImage[] images = ((ImageTask) task).getImages();

			double value = identifyMinimum ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
			for (ValuedImage c : images) {
				if ((identifyMinimum && c.value < value)  || (!identifyMinimum && c.value > value)) {
					value = c.value;
					img = c;
				}
			}
			correctImage = img;
		}
		return correctImage;
	}

    /**
     * get the correct answer(s) for this question. usually used for logging or journal.
     * Note that only the first image with maximum value will be returned here,
     * if more than one image have the same maximum value.
     * @return the (first) correct answer for this task
     */
	@Override
	public ValuedImage getCorrectAnswer() {
		return determineCorrectImage();
	}

	@Override
	public double determineError() {
		ValuedImage given = super.getGivenAnswer();
		if (given != null) {
			if (Double.isNaN(determineCorrectImage().value) ||  Double.isNaN(given.value)) {
				return (correctImage == given) ? 0.0 : 1.0;
			} else {
				return Math.abs(correctImage.value - given.value);
			}
		} else
			return 1.0;
	}
}
