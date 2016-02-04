package evaluation.evalBench.images;

import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;

import evaluation.evalBench.task.Question;

/**
 * @author Alexander Rind
 */
public class MultiImageSelectionQuestion extends Question {

	private List<ValuedImage> correctAnswer = null;

	private List<ValuedImage> givenAnswer = null;

	@XmlElementWrapper(name = "correctAnswers", required = false)
	@Override
	public List<ValuedImage> getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(List<ValuedImage> correctImage) {
		this.correctAnswer = correctImage;
	}

	@XmlElementWrapper(name = "givenAnswers", required = false)
	@Override
	public List<ValuedImage> getGivenAnswer() {
		return givenAnswer;
	}

	public void setGivenAnswer(List<ValuedImage> givenAnswer) {
		this.givenAnswer = givenAnswer;
	}
}
