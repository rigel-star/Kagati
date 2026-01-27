// SPDX-License-Identifier: MIT
// Copyright (c) 2026 Kagati Foundation

package org.lemon;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;

public class AppContext {
	private final PropertyChangeSupport propertyChangeSupport;

	// currently selected tool
	private String toolName = "brush";

	// node info
	private boolean isNodeBeingDragged = false;

	private File latestOpenedFile;

	public AppContext() {
		propertyChangeSupport = new PropertyChangeSupport(this);
	}

	public static AppContext defaultContext() {
		return new AppContext();
	}

	public void addPropertyChangeListener(PropertyChangeListener pcl) {
        propertyChangeSupport.addPropertyChangeListener(pcl);
    }

	public void setLatestOpenedFile(File file) {
        this.latestOpenedFile = file;
        propertyChangeSupport.firePropertyChange("latestOpenedFile", null, file);
    }

	public File getLatestOpenedFile() {
		return latestOpenedFile;
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