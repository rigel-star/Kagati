package org.lemon.tools.brush.gui;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import org.lemon.gui.ImageView;
import org.lemon.tools.BrushTool;
import org.lemon.tools.brush.SoftBrushTool;

public class BrushChooser extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JButton normalBrush;
	private JButton softBrush;

	private JComponent context;

	public BrushChooser(JComponent context) {
		this.context = context;

		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(createNormalBrushButton());
		add(createSoftBrushButton());
		normalBrush.addActionListener(this);
		softBrush.addActionListener(this);
	}

	private JButton createNormalBrushButton() {
		normalBrush = new JButton("Normal");
		return normalBrush;
	}

	private BrushTool createNormalBrush() {
		Graphics2D canvas = null;

		if (context instanceof ImageView) {
			canvas = ((ImageView) context).getDrawable();
			return new BrushTool(canvas);
		}
		return null;
	}

	private BrushTool createSoftBrush() {
		Graphics2D canvas = null;

		if (context instanceof ImageView) {
			canvas = ((ImageView) context).getDrawable();
			return new SoftBrushTool(canvas);
		}
		return null;
	}
	
	private JButton createSoftBrushButton() {
		softBrush = new JButton("Soft");
		return softBrush;
	}

	private void changeBrush(BrushTool newBrush) {
		if (context instanceof ImageView) {
			
		}
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