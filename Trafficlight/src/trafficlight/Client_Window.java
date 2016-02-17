/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficlight;

import java.awt.Container;
import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author s198752
 */
public class Client_Window extends JFrame{
    private JLabel pictureLabel;
    private ImageIcon image;
    
    public Client_Window(){
        super("Client");
        
        image = new ImageIcon(getClass().getClassLoader().getResource("resources/red_light.jpg"));
        pictureLabel = new JLabel(image);
        
        Container c = getContentPane();
        c.setLayout(new FlowLayout());
        c.add(pictureLabel);
        
        setSize(200, 300);
        setVisible(true);
    }
}
