package evaluation.evalBench.images;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import evaluation.evalBench.task.Question;

/**
 * @author Alexander Rind
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SingleImageSelectionQuestion extends Question {

	@XmlElement(required=false)
	private ValuedImage correctImage = null;

	@XmlTransient
	private ValuedImage givenAnswer = null;

	@Override
	public Object getCorrectAnswer() {
		return correctImage;
	}

	@Override
	public Object getGivenAnswer() {
        return givenAnswer;
	}

	public void setGivenAnswer(ValuedImage givenAnswer) {
		this.givenAnswer = givenAnswer;
	}

}
