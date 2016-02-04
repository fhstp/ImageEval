package evaluation.evalBench.images;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import evaluation.evalBench.EvaluationManager;
import evaluation.evalBench.task.Task;

/**
 * Question to select all images with annotated value above a threshold.
 *
 * @author Alexander Rind
 */
public class ThresholdValueImageSelectionQuestion extends
		MultiImageSelectionQuestion {

	@XmlElement(required = false)
	double threshold = 0.0;

	/**
	 * whether images below the threshold should be selected.
	 */
	@XmlElement(required = false)
	boolean belowThreshold = false;

	// we completely ignore #correctAnswer of the base class
	// so that it cannot be loaded from the task list XML
	@XmlTransient
	private List<ValuedImage> correctImages = null;

	private List<ValuedImage> findCorrectImages() {
		if (correctImages == null) {
			List<ValuedImage> img = new LinkedList<ValuedImage>();
			Task task = EvaluationManager.getInstance().getSessionGroup().getActiveSubSession().getCurrentTask();
			ValuedImage[] images = ((ImageTask) task).getImages();

			for (ValuedImage c : images) {
				if ((belowThreshold && c.value < threshold) || (!belowThreshold && c.value > threshold)) {
					img.add(c);
				}
			}
			// no image has a value fitting threshold --> empty list
			// no image has a value --> empty list
			if (! img.isEmpty()) {
				correctImages = img;
			}
		}
		return correctImages;
	}

	@Override
	public List<ValuedImage> getCorrectAnswer() {
		return findCorrectImages();
	}

	@Override
	public double determineError() {
		if (getGivenAnswer() == null || getCorrectAnswer() == null) {
			return 1.0;
		}

		double error = 0.0;

		// sum error for all false positives
		for (ValuedImage g : getGivenAnswer()) {
			if ((belowThreshold && g.value >= threshold) || (!belowThreshold && g.value <= threshold)) {
				error += Math.abs(g.value - threshold);
			}
		}

		// sum error for all false negatives
		for (ValuedImage c : getCorrectAnswer()) {
			if (! getGivenAnswer().contains(c)) {
				error += Math.abs(c.value - threshold);
			}
		}

		return error;
	}
}
