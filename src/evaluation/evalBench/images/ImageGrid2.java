package evaluation.evalBench.images;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import evaluation.evalBench.EvaluationManager;

/**
 * grid of images implemented directly using Swing {@link JLabel}s.
 * @author Alexander Rind
 */
@SuppressWarnings("serial")
public class ImageGrid2 extends JPanel {
	
	private static final Border BORDER_SELECTED = BorderFactory
			.createCompoundBorder(
					BorderFactory.createLineBorder(Color.ORANGE, 2, false),
					BorderFactory.createEmptyBorder(1, 1, 1, 1));
	private static final Border BORDER_DEFAULT = BorderFactory
			.createEmptyBorder(3, 3, 3, 3);
	
	private JPanel grid = new JPanel();
	private ClickListener listener = new ClickListener();
	
	private Tile[] tiles;
	private int rowcount = 2; 
	
	private boolean multipleSelection = false;
	private boolean interactive = false;
	
	public ImageGrid2() {
		super();
		setLayout(new GridBagLayout());
//		add(new javax.swing.JScrollPane(grid));
		add(grid);
	}

	public void setImages(ValuedImage[] images, String baseUrl) {
		tiles = new Tile[images.length];
		
		rowcount = (int) Math.ceil(Math.sqrt(images.length));
		grid.removeAll();
		grid.setLayout(new GridLayout(rowcount, rowcount, 1, 1));

		for (int i=0; i<images.length; i++) {
			String path = baseUrl + images[i].url;
			try {
				URL imgUrl = new URL(path);
				// TODO consider a queue to cache recent TileModel objects
				BufferedImage bImg = ImageIO.read(imgUrl);
				ImageIcon icon = new ImageIcon(bImg);
				tiles[i] = new Tile(images[i], icon);
				
				// set initial selection
				if (images[i].initialSelection) {
					tiles[i].setSelected(true);
				}
				
				tiles[i].getComponent().addMouseListener(listener);
				tiles[i].getComponent().setBorder(
						tiles[i].isSelected() ? BORDER_SELECTED : BORDER_DEFAULT);
				grid.add(tiles[i].getComponent());
			} catch (IOException e) {
				e.printStackTrace();
				// TODO handle IO of loading tiles
			}
		}
	}
	
	public List<ValuedImage> getSelectedTiles() {
		ArrayList<ValuedImage> result = new ArrayList<ValuedImage>();
		for (int i=0; i<tiles.length; i++) {
			if (tiles[i].isSelected())
				result.add(tiles[i].getSource());
		}
		return result;
	}
	
	public void clear() {
		grid.removeAll();
	}

	public int getRowCount() {
		return rowcount;
	}

	/**
	 * This must be set after {@link ImageGrid#setImages(ValuedImage[], String)}.
	 * @param rowcount
	 */
	public void setRowCount(int rowcount) {
		this.rowcount = rowcount;
	}

	public boolean isMultipleSelection() {
		return multipleSelection;
	}

	public void setMultipleSelection(boolean multipleSelection) {
		this.multipleSelection = multipleSelection;
	}

	public boolean isInteractive() {
		return interactive;
	}

	public void setInteractive(boolean interactive) {
		this.interactive = interactive;
	}

	private class ClickListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (!ImageGrid2.this.interactive) {
				return;
			}

			if (ImageGrid2.this.multipleSelection) {
				for (int i = 0; i < tiles.length; i++) {
					if (tiles[i].getComponent() == e.getComponent()) {
						tiles[i].setSelected(! tiles[i].isSelected());
						tiles[i].getComponent().setBorder(
								tiles[i].isSelected() ? BORDER_SELECTED
										: BORDER_DEFAULT);
						EvaluationManager.getInstance().log(
								"tile "
										+ tiles[i].getSource()
										+ (tiles[i].isSelected() ? " selected"
												: " unselected"));
					}
				}
			} else {
				for (int i = 0; i < tiles.length; i++) {
					if (tiles[i].getComponent() == e.getComponent()) {
						tiles[i].setSelected(true);
						tiles[i].getComponent().setBorder(BORDER_SELECTED);
						EvaluationManager.getInstance().log(
								"tile " + tiles[i].getSource()
										+ " single selected");
					} else {
						tiles[i].setSelected(false);
						tiles[i].getComponent().setBorder(BORDER_DEFAULT);
					}
				}
			}
		}

	}

	private static class Tile {

		private ValuedImage source;
		private JLabel comp;
		private boolean selected = false;

		public Tile(ValuedImage source, Icon icon) {
			this.source = source;
			this.comp = new JLabel(icon);
		}

		public ValuedImage getSource() {
			return source;
		}

		public JLabel getComponent() {
			return comp;
		}

		public boolean isSelected() {
			return selected;
		}

		public void setSelected(boolean selected) {
			this.selected = selected;
		}
	}
}
