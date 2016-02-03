package evaluation.evalBench.images;

import java.util.Iterator;

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
		return !imageGrid.getSelectedTiles().isEmpty();
	}

	@Override
	public void inputFinished() {
		if (m_question instanceof SingleImageSelectionQuestion) {
			Iterator<ValuedImage> i = imageGrid.getSelectedTiles().iterator();
			((SingleImageSelectionQuestion) m_question).setGivenAnswer(
					i.hasNext() ? i.next() : null);
		} else if (m_question instanceof MultiImageSelectionQuestion) {
			((MultiImageSelectionQuestion) m_question).setGivenAnswer(
					imageGrid.getSelectedTiles());
		}
	}
}
