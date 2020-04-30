package application;

import java.io.IOException;

import javax.swing.SwingUtilities;

/**
 * @author Ramesh Poudel
 * @version 1.1
 * @since 2020
 * */
public class MainApplication {

	public static void main(String[] args) throws IOException {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					new MainApplicationFrame();
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}
		});
	}
}
