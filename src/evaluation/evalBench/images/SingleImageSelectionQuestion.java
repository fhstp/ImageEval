package evaluation.evalBench.images;

import javax.xml.bind.annotation.XmlElement;

import evaluation.evalBench.task.Question;

/**
 * @author Alexander Rind
 */
public class SingleImageSelectionQuestion extends Question {

	private ValuedImage correctAnswer = null;
	private ValuedImage givenAnswer = null;

	@XmlElement(required=false)
	@Override
	public ValuedImage getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(ValuedImage img) {
		this.correctAnswer = img;
	}

	@XmlElement(required = false)
	@Override
	public ValuedImage getGivenAnswer() {
        return givenAnswer;
	}

	public void setGivenAnswer(ValuedImage givenAnswer) {
		this.givenAnswer = givenAnswer;
	}
}
