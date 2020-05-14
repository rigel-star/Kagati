package application;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lemon.filters.basic_filters.ImageHSB;
import org.lemon.gui.dialogs.color_remover.SelectedColorPreview;
import org.rampcv.utils.Tools;


public class Test {

	public static void main(String[] args) throws IOException {
		var img = ImageIO.read(new File("C:\\Users\\Ramesh\\Desktop\\styletransfer\\model.jpg"));
		
		var s = new SelectedColorPreview(img);
		s.addNewColor(new Color(20, 20, 160));
		
		
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
								
								Color c = new Color(img.getRGB(x, y));
								float hsb[] = Tools.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue());
								
								hsb[1] = ImageHSB.incSaturation(img, x, y, 0.1f);
								
								int rgb = Tools.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
								
								img.setRGB(x, y, rgb);
								
							}
							
						}
					}
					
					
					try {
						if(!ImageIO.write(out, "jpg", new File("C:\\Users\\Ramesh\\Desktop\\tracked.jpg")) ||
								!ImageIO.write(img, "jpg", new File("C:\\Users\\Ramesh\\Desktop\\incSatr.jpg"))) {
							System.out.println("LOL mistake somewhere. finddd iittttttt");
						}
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
