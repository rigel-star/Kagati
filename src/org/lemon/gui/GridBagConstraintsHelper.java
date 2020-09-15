package org.lemon.gui;

import java.awt.GridBagConstraints;

public class GridBagConstraintsHelper extends GridBagConstraints {

	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 1L;

	
	public GridBagConstraintsHelper() {
        gridx = 0;
        gridy = 0;
        fill = GridBagConstraints.BOTH;  // Component fills area 
    }
    
	
    /**
     * 
     *  Moves the helper's cursor to the right one column.
     *  
     * */
    public GridBagConstraintsHelper nextCol() {
        gridx++;
        return this;
    }
    
    
    /**
     * 
     * Moves the helper's cursor to first col in next row.
     * 
     * */
    public GridBagConstraintsHelper nextRow() {
        gridx = 0;
        gridy++;
        return this;
    }
    
    
    /**
     * Expandable Width.  Returns new helper allowing horizontal expansion. 
     * A new helper is created so the expansion values don't
     * pollute the origin helper.
     */
    public GridBagConstraintsHelper expandW() {
    	GridBagConstraintsHelper duplicate = ( GridBagConstraintsHelper )this.clone();
        duplicate.weightx = 1.0;
        return duplicate;
    }
    
    
    /**
     * 
     * Expandable Height. Returns new helper allowing vertical expansion.
     * 
     * */
    public GridBagConstraintsHelper expandH() {
    	GridBagConstraintsHelper duplicate = ( GridBagConstraintsHelper )this.clone();
        duplicate.weighty = 1.0;
        return duplicate;
    }
    
    
    /**
     * 
     * Sets the width of the area in terms of number of columns.
     * 
     * */
    public GridBagConstraintsHelper width( int colsWide ) {
    	GridBagConstraintsHelper duplicate = ( GridBagConstraintsHelper )this.clone();
        duplicate.gridwidth = colsWide;
        return duplicate;
    }
    
    
    /**
     * 
     * Width is set to all remaining columns of the grid.
     * 
     * */
    public GridBagConstraintsHelper width() {
    	GridBagConstraintsHelper duplicate = ( GridBagConstraintsHelper )this.clone();
        duplicate.gridwidth = REMAINDER;
        return duplicate;
    }
    
    /**
     * 
     * Sets the height of the area in terms of rows. 
     * @param rowsHigh
     * 
     * */
    public GridBagConstraintsHelper height( int rowsHigh ) {
    	GridBagConstraintsHelper duplicate = ( GridBagConstraintsHelper )this.clone();
        duplicate.gridheight = rowsHigh;
        return duplicate;
    }
    
    
    /**
     * 
     * Height is set to all remaining rows.
     * 
     * */
    public GridBagConstraintsHelper height() {
    	GridBagConstraintsHelper duplicate = ( GridBagConstraintsHelper )this.clone();
        duplicate.gridheight = REMAINDER;
        return duplicate;
    }
    
    
    /**
     * 
     * Alignment is set by parameter.
     * 
     * */
    public GridBagConstraintsHelper align( int alignment ) {
    	GridBagConstraintsHelper duplicate = ( GridBagConstraintsHelper )this.clone();
        duplicate.fill   = NONE;
        duplicate.anchor = alignment;
        return duplicate;
    }
}
