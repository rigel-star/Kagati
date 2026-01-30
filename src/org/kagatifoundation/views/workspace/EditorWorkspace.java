// SPDX-License-Identifier: MIT
// Copyright (c) 2026 Kagati Foundation

package org.kagatifoundation.views.workspace;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.JDesktopPane;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import org.kagatifoundation.AppContext;

import org.kagatifoundation.views.workspace.frames.ImageFrame;

public class EditorWorkspace extends JDesktopPane implements PropertyChangeListener {

	private final AppContext ctx;

	public EditorWorkspace(AppContext ctx) {
		this.ctx = ctx;
		this.ctx.addPropertyChangeListener(this);

		ImageFrame imageFrame = new ImageFrame(new File("/Users/rigelstar/Desktop/extra/Test Images/worker.jpeg"));
		addFrame(imageFrame);
	}

	private void addFrame(WorkspaceFrame frame) {
		frame.addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameClosing(InternalFrameEvent e) {
				super.internalFrameClosing(e);
				System.out.println("A frame has been closed!");
			}
		});

		add(frame);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if ("latestOpenedFile".equals(evt.getPropertyName())) {
			File file = (File) evt.getNewValue();
			ImageFrame frame = new ImageFrame(file);
			addFrame(frame);
		}

		throw new UnsupportedOperationException("Unimplemented method 'propertyChange'");
	}
}