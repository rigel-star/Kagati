package org.lemon.tools;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JWindow;

import org.lemon.image.ImageView;

public class BrushToolOptions extends JWindow {
	private static final long serialVersionUID = 1L;

	JButton ok = new JButton("OK");
	JLabel msg = new JLabel("Size");
	JSlider sl;
	
	public BrushToolOptions(ImageView parent, int x, int y) {
		
		/*last param is for getting current penSize of panel*/
		sl = new JSlider(JSlider.HORIZONTAL, 0, 10, parent.getImagePanel().getCanvasModeListener().getPenSize());
		
		setSize(200, 100);
		setLocation(x, y);
		setVisible(true);
		getRootPane().setBorder(BorderFactory.createLineBorder(Color.black, 2));
		
		this.sl.setPaintTicks(true);
		this.sl.setMajorTickSpacing(1);
		this.sl.setPaintLabels(true);
		
		BoxLayout bl = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);
		setLayout(bl);
		add(msg);
		add(sl);
		add(ok);
		
		
		ok.addActionListener(action -> {
			int size = Integer.valueOf(sl.getValue());
			parent.getImagePanel().getCanvasModeListener().setPenSize(size);
			parent.revalidate();
			System.out.println(size);
			dispose();
		});
	}
	
}
