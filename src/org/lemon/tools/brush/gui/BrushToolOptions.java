package org.lemon.tools.brush.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JWindow;

import org.lemon.gui.image.ImagePanel;
import org.lemon.gui.image.ImageView;

public class BrushToolOptions extends JWindow {
	private static final long serialVersionUID = 1L;

	
	private JComponent context;
	
	private BrushChooser brushChooser;
	
	private JPanel brushContainer;
	private JPanel sizeContainer;
	
	
	JButton ok = new JButton("OK");
	JSlider sl;
	
	public BrushToolOptions(JComponent context, int x, int y) {
		
		this.context = context;
		//init slider as per container type
		this.initSlider(context);
		
		setSize(200, 100);
		setLocation(x, y);
		setVisible(true);
		getRootPane().setBorder(BorderFactory.createLineBorder(Color.black, 2));
		
		this.sl.setPaintTicks(true);
		this.sl.setMajorTickSpacing(1);
		this.sl.setPaintLabels(true);
		
		BoxLayout bl = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);
		setLayout(bl);
		
		init();
		addAll();
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		c.add(sizeContainer, BorderLayout.SOUTH);
		c.add(brushContainer, BorderLayout.NORTH);
		
		
		this.ok.addActionListener(action -> {
			int size = Integer.valueOf(sl.getValue());
			
			if(context instanceof ImageView) {
				ifImageView(context, size);
			}
			
		});
		
	}
	
	
	
	private void init() {
		
		this.brushChooser = new BrushChooser(context);
		
		this.brushContainer = new JPanel();
		
		this.sizeContainer = new JPanel();
		this.sizeContainer.setLayout(new BoxLayout(sizeContainer, BoxLayout.Y_AXIS));
	}
	
	
	private void addAll() {
		
		this.brushContainer.add(brushChooser);
		
		this.sizeContainer.add(sl);
		this.sizeContainer.add(ok);
	}
	
	
	
	
	private void initSlider(JComponent comp){
		if(comp instanceof ImageView)
			/*last param is for getting current brushStrokeSize of panel*/
			this.sl = new JSlider(JSlider.HORIZONTAL, 0, 10, ((ImageView) comp).
													getImagePanel().getCanvasModeListener().getBrush().getStrokeSize()); 
	}
	
	
	private void ifImageView(JComponent comp, int size) {
		ImageView view = (ImageView) comp;
		ImagePanel pan = view.getImagePanel();
		pan.getCanvasModeListener().getBrush().setStrokeSize(size);
		pan.revalidate();
		dispose();
	}
	
	
}
