import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

public class Advertisement extends Panel {
	JFrame frame = new JFrame("Image Label");
	BufferedImage image;
	
	public Advertisement(String imageURL) {
		  try {
			String imageName= imageURL;
			File input = new File(imageName);
			this.image = ImageIO.read(input);
		} catch (IOException ie) {
			System.out.println("Error:"+ie.getMessage());
		}
//	    frame.add("Center", new ImageLabel(image, "Event Poster!!"));
//	    frame.pack();
//	    frame.setVisible(true);
	}

	public void paint(Graphics g) {
	g.drawImage( image, 0, 0, null);
	}

	static public void main(String args[]) throws
	Exception {
	JFrame frame = new JFrame("Display image");
	String imageURL=args[0];
	Panel panel = new Advertisement(imageURL);
	frame.getContentPane().add(panel);
	frame.setSize(500, 500);
	frame.setVisible(true);
	}
}

//String imageURL;

//File imageInput = new File(imageURL);
//BufferedImage posterImage = ImageIO.read(imageInput);
//Button posterButton= new Button(shell,SWT.PUSH);
//posterButton.setImage(posterImage);
//
//Image posterImage = new Image(display, imageURL);
//Button posterBtn= new Button(shell,SWT.PUSH);
//posterBtn.setImage(posterImage);