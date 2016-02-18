/**
 * Nettverk og skytjenester
 * HiOA vår 2016
 * Oblig 1
 * Gruppe 13:
 * Gretar Ævarsson - s198586
 * Eivind Schulstad - s198752
 **/

package trafficlight;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Client_Window extends JFrame{
    private JLabel pictureLabel;
    private ImageIcon image;
    private DrawPanel panel;
    
    // constructor
    public Client_Window(){
        super("Client");
        
        // setup window elements
        panel = new DrawPanel();
        panel.changePicture("no_light.jpg");
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
           
        setSize(70, 180);
        setResizable(false);
        setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
        // change the traffic light
        public void changeLight(String color) {
            panel.changePicture(color + "_light.jpg");
    }
    
    private class DrawPanel extends JPanel{
        Image image;
        
        public void changePicture(String path){
            image = new ImageIcon(path).getImage();
            repaint();
        }
        
        public void paintComponent(Graphics g){	
            g.setColor(new Color(238,238,238));
            g.fillRect(1,1,134,76);
            g.drawImage(image,1,1,this);
        }
    }
}
