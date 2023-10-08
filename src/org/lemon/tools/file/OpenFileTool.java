package org.lemon.tools.file;

import java.awt.image.BufferedImage;

import org.lemon.gui.ImageView;
import org.lemon.gui.WorkspaceArena;
import org.lemon.gui.image.ChooseImage;
import org.lemon.tools.LemonTool;

public class OpenFileTool implements LemonTool {
    private WorkspaceArena arena;

    public OpenFileTool(WorkspaceArena arena) {
        this.arena = arena;
        ChooseImage imgChoose = new ChooseImage();
        BufferedImage img = imgChoose.getChoosenImage();
        String title = imgChoose.getChoosenFile().getName();
        ImageView imageView = new ImageView(img, title);
        this.arena.addView(imageView);
    } 
}