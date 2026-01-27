// SPDX-License-Identifier: MIT
// Copyright (c) 2026 Kagati Foundation

package org.lemon;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class AppContext {
	private final PropertyChangeSupport support;

	private String toolName = "brush";

	public AppContext() {
		support = new PropertyChangeSupport(this);
	}

	public static AppContext defaultContext() {
		return new AppContext();
	}

	public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

	public void setTool(String toolName) {
		this.toolName = toolName;
	}

	public String getTool() {
		return toolName;
	}
}