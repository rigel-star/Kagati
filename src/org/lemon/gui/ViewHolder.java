package org.lemon.gui;

public interface ViewHolder {
	
	
	/**
	 * 
	 * @return View attached with this {@code ViewHolder}.
	 * 
	 * */
	public default Object getView() {
		
		return null;
	}
	
}
