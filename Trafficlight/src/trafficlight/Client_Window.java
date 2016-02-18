/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficlight;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author s198752
 */
public class Client_Window extends JFrame{
    private JLabel pictureLabel;
    private ImageIcon image;
    private DrawPanel panel;
    
    public Client_Window(){
        super("Client");
              
        panel = new DrawPanel();
        panel.changePicture("no_light.jpg");
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
           
        setSize(70, 180);
        setResizable(false);
        setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
        public void changeLight(String color) {
        switch(color){
            case "red" : panel.changePicture("red_light.jpg");
                break;
            case "yellow" : panel.changePicture("yellow_light.jpg");
                break;
            case "green" : panel.changePicture("green_light.jpg");
                break;                 
        }      
    }
    
    private class DrawPanel extends JPanel{
        Image image;
        public void changePicture(String path){
            image = new ImageIcon(path).getImage();
            repaint();
        }
        public void paintComponent(Graphics g)
        {	
            g.setColor(new Color(238,238,238));
            g.fillRect(1,1,134,76);
            g.drawImage(image,1,1,this);
        }
    }
}
