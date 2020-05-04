package org.lemon.tools.brush;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JWindow;

import org.lemon.gui.image.LemonImageView;

public class BrushToolOptions extends JWindow {
	private static final long serialVersionUID = 1L;

	JButton ok = new JButton("OK");
	JLabel msg = new JLabel("Size");
	JSlider sl;
	
	public BrushToolOptions(JComponent container, int x, int y) {
		
		if(container == null)
			return;
		
		//init slider as per container type
		this.initSlider(container);
		
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
			
			if(container instanceof LemonImageView) {
				ifImageView(container, size);
			}
			
		});
	}
	
	
	private void initSlider(JComponent comp){
		if(comp instanceof LemonImageView)
			/*last param is for getting current penSize of panel*/
			sl = new JSlider(JSlider.HORIZONTAL, 0, 10, ((LemonImageView) comp).getImagePanel().getCanvasModeListener().getPenSize()); 
	}
	
	
	private void ifImageView(JComponent comp, int size) {
		LemonImageView view = (LemonImageView) comp;
		view.getImagePanel().getCanvasModeListener().setPenSize(size);
		view.revalidate();
		dispose();
	}
	
	
}
