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

    public Visualization(Schedule schedule){
		try {
			mazeImage = ImageIO.read(new File("C:\\workspace\\heuristic\\inp\\europe-scaled.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DrawSchedule(schedule);
		Show();
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
    
    void DrawSchedule(Schedule schedule){
		Vector<Route> routes = schedule.Routes();
		Vector<Color>colors = new Vector<Color>();
		colors.add(Color.BLUE);
		colors.add(Color.BLACK);
		colors.add(Color.RED);
		colors.add(Color.YELLOW);
		colors.add(Color.GREEN);
		colors.add(Color.PINK);

		for(int i=0;i<routes.size();i++) {
			Route route = routes.get(i);
			Vector<Coordinate> coor = new Vector<Coordinate>();
			for(int j =0; j<route.getCities().size();j++){
				coor.add(new Coordinate(route.getCities().get(j).X(), route.getCities().get(j).Y()));
			}
			ColourRoute(coor, colors.get(i));			
		}
	}
}
