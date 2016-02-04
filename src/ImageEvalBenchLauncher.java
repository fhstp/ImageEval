import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import evaluation.evalBench.EvaluationDelegate;
import evaluation.evalBench.EvaluationManager;
import evaluation.evalBench.images.ExtremeValueImageSelectionQuestion;
import evaluation.evalBench.images.ImageEvaluationPanelFactory;
import evaluation.evalBench.images.ImageGrid2;
import evaluation.evalBench.images.MultiImageSelectionQuestion;
import evaluation.evalBench.images.SingleImageSelectionQuestion;
import evaluation.evalBench.images.ImageTask;
import evaluation.evalBench.io.XMLTaskListCreator;
import evaluation.evalBench.panel.TaskDialog;
import evaluation.evalBench.session.EvaluationSession;
import evaluation.evalBench.session.EvaluationSessionGroup;
import evaluation.evalBench.task.Task;
import evaluation.evalBench.task.TaskList;

public class ImageEvalBenchLauncher extends JFrame implements EvaluationDelegate {
	
	private static final long serialVersionUID = 1L;
	
	private static final String FRAME_TITLE = "VisualizationTool";

	private JPanel evalPanel;
	private JPanel visPanel;
	
	private ImageGrid2 imageGrid;
	
	private TaskDialog taskDialog;

	/**
	 * start point
	 * @param args not used
	 */
	public static void main(String[] args) {
	    Locale.setDefault(Locale.US);
		initLnf();

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				EvaluationDelegate delegate = new ImageEvalBenchLauncher();
				EvaluationManager.getInstance().setDelegate(delegate);
				prepareEvalBench();
			}
		});
	}
	
	/**
	 * init look and feel (tries to set the system look and feel)
	 */
	private static void initLnf() {
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		System.setProperty("apple.awt.showGrowBox", "false");
		System.setProperty("apple.awt.brushMetalLook", "false");
		System.setProperty("apple.awt.brushMetalRounded", "true");

		try {
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			System.err.println("could not set look and feel " + e.getLocalizedMessage());
		}
	}
	
	/**
	 * Initialize new Frame with title and add a visualization panel
	 */
	private ImageEvalBenchLauncher(){
		this.setTitle(FRAME_TITLE);
//		setJMenuBar(createMenuBar());
		
		visPanel = getVisualizationPanel(true); 
		
		add(visPanel, BorderLayout.CENTER);
		taskDialog= new TaskDialog(null);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		pack();
		setLocationRelativeTo(null);

		setVisible(true);
	}
	
//	/**
//	 * create a new menu bar
//	 * @return menubar
//	 */
//	private javax.swing.JMenuBar createMenuBar() {
//		final javax.swing.JMenuBar bar = new javax.swing.JMenuBar();
//		
//		final javax.swing.JMenu evalMenu = new javax.swing.JMenu("Evaluation");
//		evalMenu.add(new EvalAction());
//		bar.add(evalMenu);
//
//		return bar;
//	}
	
	/**
	 * create a JPanel with a visualization
	 * @return
	 */
	public JPanel getVisualizationPanel(boolean initial){
		JPanel aPanel = new JPanel();
		aPanel.setLayout(new BorderLayout());
		aPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		aPanel.setPreferredSize(new Dimension(400, 600));
		if (initial)
		{
			aPanel.add(new JLabel("<html><body>" +
					"<center><b>Visualization Panel</b></center><br>" +
					"Choose <i>Evaluation</i> in the menu bar to start the evaluation</body>" +
					"</html>", JLabel.CENTER), BorderLayout.CENTER);
		}
		else
		{
			
		}
		
		return aPanel;
	}
	
	private void setVisPanelToNormalState()
	{
		visPanel.setVisible(false);
		visPanel.removeAll();
		visPanel.add(new JLabel("<html><body>" +
				"<center><b>Thank you</b></center><br>" +
				"The Evaluation is now complete.</body>" +
				"</html>", JLabel.CENTER), BorderLayout.CENTER);
		visPanel.setVisible(true);
		pack();
	}
	
	private void setVisPanelToEvalState()
	{
		visPanel.setVisible(false);
		visPanel.removeAll();
		imageGrid = new ImageGrid2();
		visPanel.add(imageGrid, BorderLayout.CENTER);
		visPanel.setVisible(true);
		pack();
	}
	
	/**
	 * create evaluationPanel for answering tasks
	 */
	private void addEvaluationPanel(){
		if (evalPanel == null) {
			evalPanel = new JPanel();
			evalPanel.setLayout(new BorderLayout());
			evalPanel.setPreferredSize(new Dimension(250, 600));
	
		}
		
		add(evalPanel, BorderLayout.EAST);
		pack();
		
	}
	
	/**
	 * remove evaluation panel from frame
	 */
	private void removeEvaluationPanel(){
		evalPanel.removeAll();
		remove(evalPanel);
		pack();
	}
	

	/**
	 * set the task panel as a child of the evaluation panel
	 * @param aPanel a task panel
	 */
	private void setMyEvaluationPanel(JPanel aPanel){
		evalPanel.setVisible(false);
		evalPanel.removeAll();
		evalPanel.add(aPanel, BorderLayout.CENTER);
		evalPanel.setVisible(true);
	}
	

	/*
	 * ------------------- Initial Evaluation Configuration -------------------
	 */
	
	/**
	 * In the EvalBench demo this used to be in <tt>EvalAction</tt>.
	 */
	public static void prepareEvalBench() {
		// change journal to xml type  
		//EvaluationManager.getInstance().setJournalFactory(new XMLJournalFactory());

		// consider extended tasks in task list creation
		XMLTaskListCreator taskListCreator = new XMLTaskListCreator();
		taskListCreator.setClassesToBeBound(TaskList.class, ImageTask.class,
				SingleImageSelectionQuestion.class, MultiImageSelectionQuestion.class,
				ExtremeValueImageSelectionQuestion.class);
		EvaluationManager.getInstance().setTaskListCreator(taskListCreator);
		
		// create new session group for participant one
		EvaluationSessionGroup sessionGroup = new EvaluationSessionGroup("participant1");
		
		// create training session for visualization type A
		EvaluationSession trainingA = new EvaluationSession("TrainingA"); 
		trainingA.getConfiguration().put("VisualizationType", "A");
		trainingA.getConfiguration().put("Training", "true");
		
		// create actual session for visualization type A
		EvaluationSession sessionA = new EvaluationSession("EvaluationA"); 
		sessionA.getConfiguration().put("VisualizationType", "A");
		
		// create training session for visualization type B
		EvaluationSession trainingB = new EvaluationSession("TrainingB"); 
		trainingB.getConfiguration().put("VisualizationType", "B");
		
		// create actual session for visualization type A
		EvaluationSession sessionB = new EvaluationSession("EvaluationB"); 
		sessionB.getConfiguration().put("VisualizationType", "B");
		
		// add sessions to group
		sessionGroup.addSession(trainingA); 
		sessionGroup.addSession(sessionA); 
		sessionGroup.addSession(trainingB); 
		sessionGroup.addSession(sessionB);
		
		// set session group (triggers execution of session group)
		EvaluationManager.getInstance().setSessionGroup(sessionGroup); 
	}
	
	/*
	 * ------------------- Evaluation Delegate Methods -------------------
	 */
	
	/**
	 * a session group is about to begin
	 */
	public void prepareForEvaluationSessionGroup(EvaluationSessionGroup sessionGroup) {
				
		// set vis state
		setVisPanelToEvalState();
						
		// add an evaluation frame to the right side of the frame.  
		addEvaluationPanel();
		
		// panel factory will link ImageGrid2 to Question objects <-- ImageGrid2 must exist
		ImageEvaluationPanelFactory panelFactory = new ImageEvaluationPanelFactory(imageGrid);
		EvaluationManager.getInstance().setPanelFactory(panelFactory);

		// choose a session and trigger the execution with a defined task list (demotasks.xml)
		EvaluationManager.getInstance().startEvaluationSession(sessionGroup.getSessionList().get(0), "xml/image-tasks.xml"); 
		
	}
	
	/**
	 * a session is about to begin
	 */
	public void prepareForEvaluationSession(EvaluationSession aSession) {
	
		// prepare for a evaluation session, e.g. load a dataset for the visualization
		
		// show an info dialog 
//        taskDialog.announceSession(aSession, true);
		
	}
	
	/**
	 * a evaluation session did finish
	 */
	public void evaluationSessionDidFinish(EvaluationSession aSession) {
		
		// show an info dialog 
		JOptionPane.showMessageDialog(this, "Session " + aSession.getTitle() + " did finish");

		// remove evaluation panel from GUI
		removeEvaluationPanel();
		
		// set visualization state back to normal
		setVisPanelToNormalState();
		
	}
	
	/**
	 * a task from the current session is about to begin 
	 */
	public void prepareForEvaluationTask(Task aTask) {
		
//        taskDialog.showDescription(aTask);
        
		if (ImageTask.class.isInstance(aTask)) {
			ImageTask iTask = (ImageTask) aTask;
			imageGrid.setImages(iTask.getImages(), iTask.getBaseUrl());
			imageGrid.setInteractive(false);
		} else {
			imageGrid.clear();
		}
		
		// get a task panel from the manager
		setMyEvaluationPanel( EvaluationManager.getInstance().getPanelForTask(aTask));
	}

	/**
	 * a task from the current session was answered
	 */
	public void evaluationTaskWasAnswered(Task aTask) {

		if (ImageTask.class.isInstance(aTask)) {
//			ImageTask iTask = (ImageTask) aTask;
			System.err.println("selected images: " + java.util.Arrays.toString(imageGrid.getSelectedTiles().toArray()));
		}
		
        // do something after a task was finished (e.g. update GUI)
        if ("true".equals(aTask.getConfiguration().get("Training"))) {

            boolean repeat = taskDialog.showFeedback(aTask);
            if (repeat) {
                EvaluationManager.getInstance()
                .getSessionGroup()
                        .getActiveSubSession().addTask(aTask, 0);
            }
        }
	}


	/**
	 * the reset button on a task panel was pressed
	 */
	public void resetGUIForEvaluationSession(EvaluationSession aSession) {
		
		// reset the visualization to the initial state of the session 
		
	}

}
