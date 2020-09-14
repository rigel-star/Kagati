package org.lemon.gui.image;


/**
 * 
 * {@code ImagePanel} will perform the task as per which mode is applied
 * to that {@code ImagePanel} at that time. 
 * 
 * */
public enum PanelMode {
	
	/**
	 * 
	 * Default mode for {@code ImagePanel} states that, the {@code ImagePanel} can
	 * do nothing and nor tool is selected is selected at the time.
	 * 
	 * */
	DEFAULT_MODE,
	
	/**
	 * 
	 * Canvas mode for {@code ImagePanel} states that, {@code BrushTool}
	 * or other painting task is currently selected at the time.
	 * 
	 * */
	CANVAS_MODE,
	
	/**
	 * 
	 * Snap mode for {@code ImagePanel} states that, {@code SelectionTool} 
	 * is selected at the time.
	 * 
	 * */
	SNAP_MODE,
	
}
