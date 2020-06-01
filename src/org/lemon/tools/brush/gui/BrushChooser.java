package org.lemon.tools.brush.gui;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import org.lemon.gui.image.ImageView;
import org.lemon.tools.BrushTool;
import org.lemon.tools.brush.utils.Brushes;

public class BrushChooser extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JButton normalBrush;
	private JButton softBrush;

	private JComponent context;

	public BrushChooser(JComponent context) {

		this.context = context;

		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		addAll();
		addActionListeners();

	}

	
	
	private void addAll() {
		add(createNormalBrushButton());
		add(createSoftBrushButton());
	}

	
	
	/**
	 * normal brush tool button
	 */
	private JButton createNormalBrushButton() {
		normalBrush = new JButton("Normal");
		return normalBrush;
	}

	
	
	
	/**
	 * creating actual normal brush
	 */
	private BrushTool createNormalBrush() {
		Graphics2D canvas = null;

		if (context instanceof ImageView) {
			canvas = ((ImageView) context).getImagePanel().getImage().createGraphics();
			return Brushes.createNormalBrush(canvas);
		}
		return null;
	}

	
	
	
	/**
	 * creating actual soft brush
	 */
	private BrushTool createSoftBrush() {
		Graphics2D canvas = null;

		if (context instanceof ImageView) {
			canvas = ((ImageView) context).getImagePanel().getImage().createGraphics();
			return Brushes.createSoftBrush(canvas);
		}
		return null;
	}

	
	
	
	/**
	 * soft brush tool button
	 */
	private JButton createSoftBrushButton() {
		softBrush = new JButton("Soft");
		return softBrush;
	}

	
	
	
	/**
	 * adding action listeners to all the buttons
	 */
	private void addActionListeners() {
		this.normalBrush.addActionListener(this);
		this.softBrush.addActionListener(this);
	}

	
	
	
	/**
	 * changing brush
	 */
	private void changeBrush(BrushTool newBrush) {

		if (context instanceof ImageView) {
			changeBrushIfImageView(newBrush);
		}
	}

	
	
	
	/**
	 * changing brush if the context is image view
	 */
	private void changeBrushIfImageView(BrushTool newBrush) {
		
		((ImageView) context).getImagePanel().getCanvasModeListener().setBrush(newBrush);
		context.revalidate();
	}

	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == this.normalBrush) {
			changeBrush(createNormalBrush());
		}
		else if (e.getSource() == this.softBrush) {
			changeBrush(createSoftBrush());
		}
		
	}

}
