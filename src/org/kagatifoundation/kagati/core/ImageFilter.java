// SPDX-License-Identifier: MIT
// Copyright (c) 2026 Kagati Foundation

package org.kagatifoundation.kagati.core;

import java.awt.image.BufferedImage;

public interface ImageFilter {
	BufferedImage apply(BufferedImage source);
}