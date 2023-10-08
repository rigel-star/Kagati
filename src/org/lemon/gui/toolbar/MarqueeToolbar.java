package org.lemon.gui.toolbar;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import org.lemon.gui.AbstractToolBar;
import org.lemon.tools.LemonTool.ToolType;
import org.lemon.utils.Utils;

public class MarqueeToolbar extends AbstractToolBar {
    private JButton rectMarqueeTool = new JButton(Utils.resizeIconToStdKagatiIconSize(new ImageIcon("icons/tools/select64.png")));
    private JButton circleMarqueeTool = new JButton(Utils.resizeIconToStdKagatiIconSize(new ImageIcon("icons/tools/select-circle64.png")));
    private JButton[] buttons = {
        rectMarqueeTool,
        circleMarqueeTool
    };

    public MarqueeToolbar() {
        super();
        ActionListener al = new MarqueeToolbarActionHandler();
        rectMarqueeTool.addActionListener(al);
        circleMarqueeTool.addActionListener(al);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(rectMarqueeTool);
        add(circleMarqueeTool);
    }

    private class MarqueeToolbarActionHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == rectMarqueeTool) {
                toolChangeSupport.firePropertyChange("currentlySelectedTool", null, ToolType.RECT_MARQUEE);
                toggleButtons(rectMarqueeTool);
            }
            else if(e.getSource() == circleMarqueeTool) {
                toolChangeSupport.firePropertyChange("currentlySelectedTool", null, ToolType.CIRCLE_MARQUEE);
                toggleButtons(circleMarqueeTool);
            }
        }
    }

    private void toggleButtons(JButton selected) {
        selected.setBackground(new Color(200, 200, 200));
        selected.setForeground(Color.BLACK);
        for(JButton button: buttons) {
            if(selected != button) {
                button.setBackground(new Color(100, 100, 100));
                button.setForeground(Color.WHITE);
                button.repaint();
            }
        }
    }
}