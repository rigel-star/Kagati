// SPDX-License-Identifier: MIT
// Copyright (c) 2026 Kagati Foundation

package org.kagatifoundation.kagati;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;

import org.kagatifoundation.kagati.tools.ToolType;

public class AppContext {
	private final PropertyChangeSupport propertyChangeSupport;

	// currently selected tool
	private ToolType currToolType =  ToolType.Brush;

	// node info
	private boolean isNodeBeingDragged = false;

	// the last file which was opened
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

	public void setTool(ToolType tool) {
		ToolType oldState = this.currToolType;
		this.currToolType = tool;
		propertyChangeSupport.firePropertyChange("currentToolType", oldState, tool);
	}

	public ToolType getTool() {
		return currToolType;
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