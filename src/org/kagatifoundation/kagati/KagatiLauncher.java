// SPDX-License-Identifier: MIT
// Copyright (c) 2026 Kagati Foundation

package org.kagatifoundation.kagati;

import java.io.IOException;

import com.formdev.flatlaf.FlatDarkLaf;

public class KagatiLauncher {
	public static void main(String[] args) throws IOException {
		try {
            FlatDarkLaf.setup();
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        java.awt.EventQueue.invokeLater(() -> {
            new AppFrame();
        });
	}
}