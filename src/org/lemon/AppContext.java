// SPDX-License-Identifier: MIT
// Copyright (c) 2026 Kagati Foundation

package org.lemon;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class AppContext {
	private final PropertyChangeSupport propertyChangeSupport;

	// currently selected tool
	private String toolName = "brush";

	// node info
	private boolean isNodeBeingDragged = false;

	public AppContext() {
		propertyChangeSupport = new PropertyChangeSupport(this);
	}

	public static AppContext defaultContext() {
		return new AppContext();
	}

	public void addPropertyChangeListener(PropertyChangeListener pcl) {
        propertyChangeSupport.addPropertyChangeListener(pcl);
    }

	public void setTool(String toolName) {
		String oldState = this.toolName;
		this.toolName = toolName;
		propertyChangeSupport.firePropertyChange("toolName", oldState, toolName);
	}

	public String getTool() {
		return toolName;
	}

	public void setIsNodeBeingDragged(boolean newState) {
		boolean oldState = isNodeBeingDragged;
		this.isNodeBeingDragged = newState;
		propertyChangeSupport.firePropertyChange("isNodeBeingDragged", oldState, newState);
	}

	public boolean getIsNodeBeingDragged() {
		return isNodeBeingDragged;
	}
}