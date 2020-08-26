package org.lemon.tools;

import java.awt.image.BufferedImage;

import org.lemon.tools.select.SelectedArea;

public interface SelectionTool extends LemonTool {

	public SelectedArea getSelectedArea();
	
	public void clearSelection();
	
	public BufferedImage getSelectedAreaImage();
	
	public boolean isAreaSelected();
	
}
