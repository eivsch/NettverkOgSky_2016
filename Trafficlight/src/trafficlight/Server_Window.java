/**
 * Nettverk og skytjenester
 * HiOA vår 2016
 * Oblig 1
 * Gruppe 13:
 * Gretar Ævarsson - s198586
 * Eivind Schulstad - s198752
 **/

package trafficlight;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Server_Window extends JFrame{
    // GUI elements
    public static JTextArea logArea, clientListArea;
    private final JSlider redSlider, yellowSlider, greenSlider;    
    private int redDuration = 10, yellowDuration = 5, greenDuration = 15;
    private JLabel pictureLabel;
    private ImageIcon image;    
    private final JPanel masterPanel, grid, under;
    private DrawPanel dp;
    
    // List for incoming connections/clients
    public static ArrayList<String> clients;
    
    public Server_Window(){          
        super("Trafficlight - Group 13");
       
        // labels
        JPanel labelPanel = new JPanel(new GridLayout(0,1,15,15));
        labelPanel.add(new JLabel("RED", SwingConstants.RIGHT));
        labelPanel.add(new JLabel("YELLOW", SwingConstants.RIGHT));
        labelPanel.add(new JLabel("GREEN", SwingConstants.RIGHT));
        
        // sliders
        redSlider = new JSlider(JSlider.HORIZONTAL, 5, 45, 10);
        redSlider.setMinorTickSpacing(1);
        redSlider.setMajorTickSpacing(10);
        redSlider.setPaintTicks(true);
        redSlider.setPaintLabels(true);
        yellowSlider = new JSlider(JSlider.HORIZONTAL, 2, 12, 5);
        yellowSlider.setMinorTickSpacing(1);
        yellowSlider.setMajorTickSpacing(2);
        yellowSlider.setPaintTicks(true);
        yellowSlider.setPaintLabels(true);
        greenSlider = new JSlider(JSlider.HORIZONTAL, 5, 45, 15);
        greenSlider.setMinorTickSpacing(1);
        greenSlider.setMajorTickSpacing(10);
        greenSlider.setPaintTicks(true);
        greenSlider.setPaintLabels(true);
        
        JPanel sliderPanel = new JPanel(new GridLayout(0,1,15,15));
        sliderPanel.add(redSlider);
        sliderPanel.add(yellowSlider);
        sliderPanel.add(greenSlider);
        
        // slider listeners
        redSlider.addChangeListener(new ChangeListener() { 
            @Override 
            public void stateChanged(ChangeEvent e) { 
                JSlider source = (JSlider) e.getSource();
                if(!source.getValueIsAdjusting()){
                    redDuration = source.getValue();
                    logArea.append("New red duration (sec): " + redDuration + "\n");
                }
            }		
        });
        
        yellowSlider.addChangeListener(new ChangeListener() { 
            @Override 
            public void stateChanged(ChangeEvent e) { 
                JSlider source = (JSlider) e.getSource();                
                if(!source.getValueIsAdjusting()){
                    yellowDuration = source.getValue();
                    logArea.append("New yellow duration (sec): " + yellowDuration + "\n");
                }
            }		
        });
        
        greenSlider.addChangeListener(new ChangeListener() { 
            @Override 
            public void stateChanged(ChangeEvent e) { 
                JSlider source = (JSlider) e.getSource();
                if(!source.getValueIsAdjusting()){
                    greenDuration = source.getValue();                
                    logArea.append("New green duration (sec): " + greenDuration + "\n");
                }
            }		
        });
        
        // text areas
        logArea = new JTextArea();
        clientListArea = new JTextArea();
        
        logArea.setEditable(false);
        clientListArea.setEditable(false);
        
        Border border = BorderFactory.createLineBorder(Color.BLACK);        
        
        logArea.setBorder(BorderFactory.createCompoundBorder(border, 
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        clientListArea.setBorder(BorderFactory.createCompoundBorder(border, 
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        
        JScrollPane scroll1 = new JScrollPane(logArea);
        JScrollPane scroll2 = new JScrollPane(clientListArea);  
        
        // server window has several layouts
        JPanel rowLabels = new JPanel(new GridLayout(0,2));
        JPanel rowTextArea = new JPanel(new GridLayout(0,2));
        JPanel textAreaPanel = new JPanel(new BorderLayout());
        rowLabels.add(new JLabel("Log:"));
        rowLabels.add(new JLabel("Clients:"));
        rowTextArea.add(scroll1);
        rowTextArea.add(scroll2);
        textAreaPanel.add(rowLabels, BorderLayout.PAGE_START);
        textAreaPanel.add(rowTextArea, BorderLayout.CENTER);
        
        // DrawPanel
        dp = new DrawPanel();
        dp.changePicture("red_light.jpg");
        
        // add all the components to the main server window
        masterPanel = new JPanel(new BorderLayout());
        grid = new JPanel(new GridLayout(1, 3, 5, 5));
        under = new JPanel(new BorderLayout());
        masterPanel.add(grid, BorderLayout.PAGE_START);
        masterPanel.add(under, BorderLayout.CENTER);
        grid.add(labelPanel);
        grid.add(sliderPanel);
        grid.add(dp);
        under.add(textAreaPanel, BorderLayout.CENTER);
        this.getContentPane().add(masterPanel);
        setSize(400,800);
    }
    
    // getters and setters
    public static JTextArea getLogArea() {
        return logArea;
    }

    public void setLogArea(JTextArea logArea) {
        this.logArea = logArea;
    }

    public static JTextArea getClientListArea() {
        return clientListArea;
    }

    public void addClient(String ip) {
        clientListArea.append(ip);
    }

    public int getRedDuration() {
        return redDuration;
    }

    public void setRedDuration(int redDuration) {
        this.redDuration = redDuration;
    }

    public int getYellowDuration() {
        return yellowDuration;
    }

    public void setYellowDuration(int yellowDuration) {
        this.yellowDuration = yellowDuration;
    }

    public int getGreenDuration() {
        return greenDuration;
    }

    public void setGreenDuration(int greenDuration) {
        this.greenDuration = greenDuration;
    }

    public JLabel getPictureLabel() {
        return pictureLabel;
    }

    public void setPictureLabel(JLabel pictureLabel) {
        this.pictureLabel = pictureLabel;
    }

    public ImageIcon getImage() {
        return image;
    }

    // change image
    public void setImage(String color) {
        dp.changePicture(color + "_light.jpg");
    }

    private class DrawPanel extends JPanel{
        Image image;
        
        public void changePicture(String path){
            image = new ImageIcon(path).getImage();
            repaint();
        }
        
        public void paintComponent(Graphics g){
            g.setColor(new Color(238,238,238));
            g.fillRect(10,10,134,76);
            g.drawImage(image,10,10,this);
        }
    }
}
