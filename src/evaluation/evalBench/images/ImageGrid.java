package evaluation.evalBench.images;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

// based on http://stackoverflow.com/a/26215276/1140589

/**
 * grid of images implemented using Swing {@link JList}.
 * Currently not in use.
 * @author Alexander Rind
 * @deprecated
 */
@SuppressWarnings("serial")
public class ImageGrid extends JPanel {

	private JList<TileModel> tileList = new JList<TileModel>();

	public ImageGrid(ListModel<TileModel> model) {
		// tileList.setModel(model);
		tileList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		tileList.setVisibleRowCount(2);
		tileList.setCellRenderer(new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list,
					Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				if (value != null) {
					TileModel tileValue = (TileModel) value;
					value = tileValue.getIcon();
				} else {
					value = "";
				}
				return super.getListCellRendererComponent(list, value, index,
						isSelected, cellHasFocus);
			}
		});
		setLayout(new BorderLayout());
		add(new JScrollPane(tileList));

		tileList.addListSelectionListener(new ListListener());
	}

	public void setImages(ValuedImage[] images, String baseUrl) {
		DefaultListModel<TileModel> model = new DefaultListModel<TileModel>();
		for (ValuedImage vImg : images) {
			String path = baseUrl + vImg.url;
			try {
				URL imgUrl = new URL(path);
				// TODO consider a queue to cache recent TileModel objects
				BufferedImage bImg = ImageIO.read(imgUrl);
				ImageIcon icon = new ImageIcon(bImg);
				model.addElement(new TileModel(vImg, icon));
			} catch (IOException e) {
				e.printStackTrace();
				// TODO handle IO of loading tiles
			}
		}
		tileList.setVisibleRowCount((int) Math.ceil(Math.sqrt(images.length)));
		tileList.setModel(model);
		// TODO set initial selection 
	}

	public List<ValuedImage> getSelectedTiles() {
		List<TileModel> sel = tileList.getSelectedValuesList();
		ArrayList<ValuedImage> result = new ArrayList<ValuedImage>(sel.size());
		for (TileModel tile : sel) {
			result.add(tile.getSource());
		}
		return result;
	}

	private class ListListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			// Window win = SwingUtilities.getWindowAncestor(CarGridPanel.this);
			// win.dispose();
		}

	}

	private static class TileModel {

		private ValuedImage source;
		private Icon icon;

		public TileModel(ValuedImage source, Icon icon) {
			this.source = source;
			this.icon = icon;
		}

		public ValuedImage getSource() {
			return source;
		}

		public Icon getIcon() {
			return icon;
		}
	}
}
