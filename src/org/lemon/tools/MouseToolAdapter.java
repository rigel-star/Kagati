package org.lemon.tools;

import java.awt.event.MouseAdapter;

import org.lemon.gui.ImageView;

public abstract class MouseToolAdapter extends MouseAdapter implements LemonTool {
    protected ImageView imageView;
    
    public MouseToolAdapter(ImageView imageView) {
        this.imageView = imageView;
    }
}