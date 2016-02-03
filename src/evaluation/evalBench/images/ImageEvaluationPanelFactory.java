package evaluation.evalBench.images;

import evaluation.evalBench.panel.DefaultEvaluationPanelFactory;
import evaluation.evalBench.panel.QuestionPanelStrategy;
import evaluation.evalBench.task.Question;

/**
 * @author Alexander Rind
 */
public class ImageEvaluationPanelFactory extends DefaultEvaluationPanelFactory {

	private ImageGrid2 imageGrid;

	public ImageEvaluationPanelFactory(ImageGrid2 imageGrid) {
		super();
		this.imageGrid = imageGrid;
	}

	public void setImageGrid(ImageGrid2 imageGrid) {
		this.imageGrid = imageGrid;
	}

    @Override
    public QuestionPanelStrategy getStrategy(Question question) {
        if (question instanceof SingleImageSelectionQuestion
                || question instanceof MultiImageSelectionQuestion) {
            return new ImageSelectionPanelStrategy(question, imageGrid);
        } else {
            return super.getStrategy(question);
        }
    }
}
