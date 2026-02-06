package org.kagatifoundation.kagati.core.pipeline;

import java.awt.image.BufferedImage;
import java.util.List;

import org.kagatifoundation.kagati.core.ImageFilter;

public record ImageProject(BufferedImage source, List<ImageFilter> pipeline) {
	
}