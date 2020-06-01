package application;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lemon.gui.dialogs.colremover.SelectedColorPreview;
import org.rampcv.color.Range;
import org.rampcv.math.BasicMath;


public class Test {

	public static void main(String[] args) throws IOException {
		var img = ImageIO.read(new File("C:\\Users\\Ramesh\\Desktop\\styletransfer\\model.jpg"));
		
		var s = new SelectedColorPreview(img);
		s.addNewColor(new Color(20, 20, 160));
		s.addNewColor(Color.red);
		s.addNewColor(new Color(250, 250, 0));
		s.addNewColor(new Color(128, 128, 128));
		
		var lerp = new Color(0, 255, 0);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				boolean done = s.updatePreview();
				
				BufferedImage out = null;
				
				if(done) {
					out = s.getRenderedPreview();
					
					for(int x=0; x<img.getWidth(); x++) {
						for(int y=0; y<img.getHeight(); y++) {
							
							if(out.getRGB(x, y) > Color.black.getRGB()) {
								
								var val = Range.constrain(BasicMath.lerp(1.0f, img.getRGB(x, y), lerp.getRGB()), 0, 255);
								
								img.setRGB(x, y, (int) val);
								
							}
							
						}
					}
					
					
					try {
						if(!ImageIO.write(out, "jpg", new File("C:\\Users\\Ramesh\\Desktop\\tracked.jpg")) ||
								!ImageIO.write(img, "jpg", new File("C:\\Users\\Ramesh\\Desktop\\incSatr.jpg"))) {
							System.out.println("LOL mistake somewhere. finddd iittttttt");
						}
						else
							System.out.println("Done.");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				else
					System.out.println("Failed");
				
				
			}
		}).start();
		
	}

}
