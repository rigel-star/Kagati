package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lemon.filters.BlurImg;

public class Test {

	public static void main(String[] args) throws IOException {
		
		BufferedImage src = ImageIO.read(new File("C:\\Users\\Ramesh\\Desktop\\opencv\\mack.jpg"));
		
		BlurImg bimg = new BlurImg(src);
		
		ImageIO.write(bimg.getBlurredImg(), "jpg", new File("C:\\\\Users\\\\Ramesh\\\\Desktop\\\\mack.jpg"));

	}

}
