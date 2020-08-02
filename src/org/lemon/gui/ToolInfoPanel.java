package org.lemon.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ToolInfoPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	
	private String msg = null;
	private JLabel msgLbl = null;
	
	/**
	 * @param msg Message to show when user clicks on specific tool.
	 * */
	public ToolInfoPanel(String msg) {
		
		msgLbl = new JLabel(msg);
		
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createLineBorder(Color.black, 1, true));
		add(msgLbl);
		
		msgLbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				changeMessage("Message changed");
				msgLbl.setText(getMessage());
				msgLbl.revalidate();
			}
		});
	}
	
	
	/**
	 * Change the message.
	 * @param msg New message
	 * */
	public void changeMessage(String msg) {
		this.msg = msg;
	}
	
	
	/**
	 * @return current message
	 * */
	public String getMessage() {
		return msg;
	}

}
