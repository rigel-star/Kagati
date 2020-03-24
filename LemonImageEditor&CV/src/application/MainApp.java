package application;

import java.io.IOException;

import javax.swing.SwingUtilities;


public class MainApp {

	public static void main(String[] args) throws IOException {
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					new MainFrame();
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}
		});
		

	}

}
