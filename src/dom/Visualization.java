package dom;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Visualization {
	
	 Dimension imgDim = new Dimension(200,200);
     BufferedImage mazeImage = null;

    public Visualization(){
		try {
			mazeImage = ImageIO.read(new File("C:\\workspace\\heuristic\\inp\\europe-scaled.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void Show() {
    	ImageIcon ii = new ImageIcon(mazeImage);
        JOptionPane.showMessageDialog(null, ii);
    }
    
    public void ColourRoute(Vector<Coordinate> coordinate, Color color) {
    	Graphics2D g2d = mazeImage.createGraphics();
		g2d.setColor(color);
        g2d.setStroke(new BasicStroke(2));
        for(int i=0;i<coordinate.size()-1;i++) {
        	Coordinate c1=coordinate.get(i);
        	Coordinate c2=coordinate.get(i+1);
        	g2d.drawLine(c1.x, c1.y, c2.x, c2.y);
        }
    }
}
