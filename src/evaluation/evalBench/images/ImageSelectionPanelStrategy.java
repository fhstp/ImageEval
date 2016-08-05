package evaluation.evalBench.images;

import java.util.Iterator;
import java.util.ResourceBundle;

import evaluation.evalBench.panel.DefaultQuestionPanelStrategy;
import evaluation.evalBench.task.Question;

/**
 * @author Alexander Rind
 */
public class ImageSelectionPanelStrategy extends
		DefaultQuestionPanelStrategy {

	private ImageGrid2 imageGrid;

	public ImageSelectionPanelStrategy(Question aQuestion,
			ImageGrid2 imageGrid) {
		super(aQuestion);
		this.imageGrid = imageGrid;

		// configure image grid widget
		imageGrid.setInteractive(true);
		imageGrid.setMultipleSelection(aQuestion instanceof MultiImageSelectionQuestion);
	}

	@Override
	public boolean checkForCorrectInput() {
		if (imageGrid.getSelectedTiles().isEmpty()) {
			if (super.getQuestion() instanceof SingleImageSelectionQuestion) {
				super.setErrorMessage(ResourceBundle
						.getBundle("evaluation.evalBench.images.gui")
						.getString("error.singleimageselectionquestion.none"));
			} else {
				super.setErrorMessage(ResourceBundle
						.getBundle("evaluation.evalBench.images.gui")
						.getString("error.multiimageselectionquestion.none"));
			}
			return false;
		} else {
			super.setErrorMessage("");
			return true;
		}
	}

	@Override
	public void inputFinished() {
		Question question = super.getQuestion();
		if (question instanceof SingleImageSelectionQuestion) {
			Iterator<ValuedImage> i = imageGrid.getSelectedTiles().iterator();
			((SingleImageSelectionQuestion) question).setGivenAnswer(
					i.hasNext() ? i.next() : null);
		} else if (question instanceof MultiImageSelectionQuestion) {
			((MultiImageSelectionQuestion) question).setGivenAnswer(
					imageGrid.getSelectedTiles());
		}
	}
}
