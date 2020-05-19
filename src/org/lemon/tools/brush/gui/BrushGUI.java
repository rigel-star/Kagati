package org.lemon.tools.brush.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JWindow;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.lemon.gui.drawing.plain_canvas.PlainDrawingPanel;
import org.lemon.gui.image.ImageView;
import org.lemon.tools.brush.BrushTool;
import org.lemon.tools.brush.utils.Brushes;


public class BrushGUI extends JWindow implements ActionListener, ChangeListener {
	private static final long serialVersionUID = 1L;

	private GridBagConstraints gbc = new GridBagConstraints();
	private Font font = new Font(null, Font.PLAIN, 13);
	private Color bgColor = new Color(210, 210, 210);
	private JPanel main = new JPanel();
	private JComponent context;
	private Graphics2D g2d;

	
	private JSlider width;
	
	private Map<String, BrushTool> brushMap = new HashMap<String, BrushTool>();
	private JComboBox<String> brushes;
	
	
	public BrushGUI(JComponent context, int x, int y) {
		
		this.context = context;
		
		setVisible(true);
		setLocation(x, y);
		
		main.setLayout(new GridBagLayout());
		main.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		createOpacitySlider();
		createWidthSlider();
		createColorButton();
		createBrushComboBox();
		
		
		gbc.gridx = 20;
		gbc.gridy = 125;
		
		var ok = new JButton("OK");
		ok.setFont(font);
		ok.addActionListener(a -> dispose());
		main.add(ok, gbc);
		
		getRootPane().setBorder(new LineBorder(Color.black, 2));
		getContentPane().add(main);
		pack();
		
	}
	
	
	
	private void createOpacitySlider() {
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		var opLbl = new JLabel("Opacity: ");
		opLbl.setFont(font);
		main.add(opLbl, gbc);
		
		gbc.gridx = 20;
		gbc.gridy = 0;
		
		var opacity = new JSlider();
		opacity.setBackground(bgColor);
		opacity.addChangeListener(this);
		
		main.add(opacity, gbc);
		
	}
	
	
	private void createWidthSlider() {
		gbc.gridx = 0;
		gbc.gridy = 25;
		
		var wLbl = new JLabel("Width: ");
		wLbl.setFont(font);
		main.add(wLbl, gbc);
		
		gbc.gridx = 20;
		gbc.gridy = 45;
		
		if(context instanceof ImageView) {
			/*last param is for getting current brushStrokeSize of panel*/
			this.width = new JSlider(JSlider.HORIZONTAL, 0, 10, ((ImageView) context).
													getImagePanel().getCanvasModeListener().getBrush().getStrokeSize()); 
		}
		
		width.setBackground(bgColor);
		width.addChangeListener(this);
		
		main.add(width, gbc);
	}

	
	
	private void createColorButton() {
		gbc.gridx = 0;
		gbc.gridy = 65;
		
		var colLbl = new JLabel("Color: ");
		colLbl.setFont(font);
		main.add(colLbl, gbc);
		
		gbc.gridx = 20;
		gbc.gridy = 65;
		
		var color = new JButton();
		colLbl.setSize(30, 100);
		color.setBackground(Color.BLUE);
		color.addActionListener(this);
		
		main.add(color, gbc);
	}
	
	
	
	private void createBrushComboBox() {
		gbc.gridx = 0;
		gbc.gridy = 85;
		
		var bruLbl = new JLabel("Brush: ");
		bruLbl.setFont(font);
		main.add(bruLbl, gbc);
		
		gbc.gridx = 20;
		gbc.gridy = 85;
		
		var brushNames = new String[]{"Normal", "Soft", "Wooble"};
		
		initPaintContext();
		
		brushMap.put(brushNames[0], Brushes.createNormalBrush(g2d));
		brushMap.put(brushNames[1], Brushes.createSoftBrush(g2d));
		brushMap.put(brushNames[2], Brushes.createWobbleBrush(g2d));
		
		brushes = new JComboBox<String>(brushNames);
		brushes.setFont(font);
		brushes.addActionListener(this);
		
		main.add(brushes, gbc);
	}
	
	
	
	private void initPaintContext() {
		
		if(context instanceof ImageView) {
			g2d = ((ImageView) context).getImagePanel().getImage().createGraphics();
		}
		else if(context instanceof PlainDrawingPanel) {
			g2d = (Graphics2D) ((PlainDrawingPanel) context).getCanvas().getGraphics();
		}
		
	}
	
	
	
	
	private void changeBrushSize(int size) {
		
		if(context instanceof ImageView) {
			var view = (ImageView) context;
			var brush = view.getImagePanel().getCanvasModeListener().getBrush();
			
			brush.setStrokeSize(size);
			
			context.revalidate();
		}
		
	}
	
	
	
	/**
	 * changing brush
	 */
	private void changeBrush(BrushTool newBrush) {
		
		if (context instanceof ImageView) {
			((ImageView) context).getImagePanel().getCanvasModeListener().setBrush(newBrush);
			context.revalidate();
		}
	}
	
	
	
	@Override
	public void stateChanged(ChangeEvent e) {
		
		var size = width.getValue();
		
		if(e.getSource() == this.width) {
			
			changeBrushSize(size);
		
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == this.brushes) {
			changeBrush(brushMap.get(brushes.getSelectedItem().toString()));
		}
		
	}

}

