package org.lemon;

import java.awt.Color;
import java.io.IOException;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.lemon.gui.ApplicationFrame;

/**
 * 
 * @author Ramesh Poudel
 * @version 1.1
 * @since 2020
 * 
 */
public class Lemon {
	public static void main( String[] args ) throws IOException {
		
		SwingUtilities.invokeLater( new Runnable() {

			@Override
			public void run() {
				/**
				 * Enabling dark theme for nimbus
				 * */
				UIManager.put("control", new Color(100, 100, 100));
				UIManager.put("info", new Color(100, 100, 100));
				UIManager.put("nimbusBase", new Color(18, 30, 49));
				UIManager.put("nimbusAlertYellow", new Color(248, 187, 0));
				UIManager.put("nimbusDisabledText", new Color(180, 180, 180));
				UIManager.put("nimbusFocus", new Color(115, 164, 209));
				UIManager.put("nimbusGreen", new Color(176, 179, 50));
				UIManager.put("nimbusInfoBlue", new Color(66, 139, 221));
				UIManager.put("nimbusLightBackground", new Color(18, 30, 49));
				UIManager.put("nimbusOrange", new Color(191, 98, 4));
				UIManager.put("nimbusRed", new Color(169, 46, 34));
				UIManager.put("nimbusSelectedText", new Color(255, 255, 255));
				UIManager.put("nimbusSelectionBackground", new Color(104, 93, 156));
				UIManager.put("text", new Color(230, 230, 230));
				
				try {
					
					UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

				} catch (Exception ex3) {
					ex3.printStackTrace();

					try {
						/**
						 * If nimbus is not supported. 
						 * */
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

					} catch (Exception ex) {
						ex.printStackTrace();
					}

				}
				new ApplicationFrame();
			}
		});
	}
}
