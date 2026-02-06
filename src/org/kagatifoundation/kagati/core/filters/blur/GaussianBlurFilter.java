// SPDX-License-Identifier: MIT
// Copyright (c) 2026 Kagati Foundation

package org.kagatifoundation.kagati.core.filters.blur;

import org.kagatifoundation.kagati.core.filters.ConvolutionFilter;

public class GaussianBlurFilter extends ConvolutionFilter {
	/**
     * A 3x3 convolution kernel for a simple blur.
     */
    protected static float[] blurMatrix = {
		1/14f, 2/14f, 1/14f,
		2/14f, 2/14f, 2/14f,
		1/14f, 2/14f, 1/14f
	};

	public GaussianBlurFilter() {
		super(blurMatrix);
	}
}