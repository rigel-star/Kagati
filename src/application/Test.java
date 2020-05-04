package application;

import java.io.IOException;

import org.lemon.utils.IDManager;

public class Test {

	public static void main(String[] args) throws IOException {
		//var img = ImageIO.read(new File("C:\\Users\\Ramesh\\Desktop\\styletransfer\\galaxy.jpg"));
		
		//new Opacity(img, 0.2f);
		
		IDManager id = new IDManager();
		
		for(int i=0; i<10; i++) {
			int rid = id.getSequentialId();
			id.addId(rid, null);
			System.out.println(rid);
		}
		
	}

}
