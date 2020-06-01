package application;

import java.io.IOException;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;


/**
 * @author Ramesh Poudel
 * @version 1.1
 * @since 2020
 * */
public class ApplicationLauncher {

	public static void main(String[] args) throws IOException {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					new ApplicationFrame();
				} catch (Exception ex3) {
					ex3.printStackTrace();
				}	
			}
		});
	}
}
