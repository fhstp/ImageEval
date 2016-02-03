package evaluation.evalBench.images;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import evaluation.evalBench.task.Question;

/**
 * @author Alexander Rind
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class MultiImageSelectionQuestion extends Question {

	@XmlElement(required=false)
	private List<ValuedImage> correctImage = null;

	@XmlTransient
	private List<ValuedImage> givenAnswer = null;

	public MultiImageSelectionQuestion() {
		// TODO Auto-generated constructor stub
	}

	public MultiImageSelectionQuestion(String aQuestionId, String aQuestionText) {
		super(aQuestionId, aQuestionText);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object getCorrectAnswer() {
		return correctImage;
	}

	@Override
	public Object getGivenAnswer() {
		return givenAnswer;
	}

	public void setGivenAnswer(List<ValuedImage> givenAnswer) {
		this.givenAnswer = givenAnswer;
	}
}
