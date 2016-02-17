/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficlight;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author s198752
 */
public class Server_Window extends JPanel{
    public static JTextArea logArea, clientListArea;
    private JSlider redSlider, yellowSlider, greenSlider;
    private Icon redIcon, greenIcon, yellowIcon;
    private JRadioButton offButton, redButton, yellowButton, greenButton;
    //private JPanel leftColumn, rightColumn, topLeft, bottomLeft, radioPanel, sliderPanel, picturePanel; 
    private final String labelOff = "Off", labelRed = "Red", labelYellow = "Yellow", labelGreen = "Green";
    private int redDuration = 25, yellowDuration = 5, greenDuration = 50;
    private String defaultColor = "Red";
    private JLabel pictureLabel;
    private ImageIcon image;
    public static ArrayList<String> clients;
    
    public Server_Window(){
          
        super(new BorderLayout());

        //Create the radio buttons.
        offButton = new JRadioButton(labelOff);
        offButton.setActionCommand(labelOff);

        redButton = new JRadioButton(labelRed);
        redButton.setActionCommand(labelRed);
        redButton.setSelected(true);

        yellowButton = new JRadioButton(labelYellow);
        yellowButton.setActionCommand(labelYellow);

        greenButton = new JRadioButton(labelGreen);
        greenButton.setActionCommand(labelGreen);
        
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                AbstractButton aButton = (AbstractButton) actionEvent.getSource();
                defaultColor = aButton.getText();
            }
        };
        
        redButton.addActionListener(al); yellowButton.addActionListener(al); greenButton.addActionListener(al);

        //Group the radio buttons.
        ButtonGroup group = new ButtonGroup();
        group.add(offButton);
        group.add(redButton);
        group.add(yellowButton);
        group.add(greenButton);

        //Set up the picture label.
        image = new ImageIcon(getClass().getClassLoader().getResource("resources/red_light.jpg"));
        pictureLabel = new JLabel(image);
       
        //Put the radio buttons in a column in a panel.
        JPanel radioPanel = new JPanel(new GridLayout(0, 1));
        radioPanel.add(offButton);
        radioPanel.add(redButton);
        radioPanel.add(yellowButton);
        radioPanel.add(greenButton);
        
        redSlider = new JSlider(JSlider.HORIZONTAL, 0, 50, 25);
        redSlider.setMinorTickSpacing(5);
        redSlider.setMajorTickSpacing(10);
        redSlider.setPaintTicks(true);
        redSlider.setPaintLabels(true);
        yellowSlider = new JSlider(JSlider.HORIZONTAL, 0, 10, 5);
        yellowSlider.setMinorTickSpacing(5);
        yellowSlider.setMajorTickSpacing(10);
        yellowSlider.setPaintTicks(true);
        yellowSlider.setPaintLabels(true);
        greenSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        greenSlider.setMinorTickSpacing(5);
        greenSlider.setMajorTickSpacing(10);
        greenSlider.setPaintTicks(true);
        greenSlider.setPaintLabels(true);
        
        redSlider.addChangeListener(new ChangeListener() { 
            @Override 
            public void stateChanged(ChangeEvent e) { 
                JSlider source = (JSlider) e.getSource();
                redDuration = source.getValue();
            }		
        });
        
        yellowSlider.addChangeListener(new ChangeListener() { 
            @Override 
            public void stateChanged(ChangeEvent e) { 
                JSlider source = (JSlider) e.getSource();
                yellowDuration = source.getValue();
            }		
        });
        
        greenSlider.addChangeListener(new ChangeListener() { 
            @Override 
            public void stateChanged(ChangeEvent e) { 
                JSlider source = (JSlider) e.getSource();
                greenDuration = source.getValue();
            }		
        });
        
        JPanel sliderPanel = new JPanel(new GridLayout(0,1));
        sliderPanel.add(redSlider);
        sliderPanel.add(yellowSlider);
        sliderPanel.add(greenSlider);
        
        add(radioPanel, BorderLayout.LINE_START);
        add(sliderPanel, BorderLayout.CENTER);
        add(pictureLabel, BorderLayout.LINE_END);
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        
        //createAndShowGUI();
    }
   
    public void createAndShowGUI(){
        JFrame frame = new JFrame("Trafficlight");
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JComponent newContentPane = new Server_Window();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);
        
        frame.pack();
        frame.setVisible(true);
    }
    
    public JTextArea getLogArea() {
        return logArea;
    }

    public void setLogArea(JTextArea logArea) {
        this.logArea = logArea;
    }

    public JTextArea getClientListArea() {
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

    public void setImage(String color) {
        switch(color){
            case "red" : image = new ImageIcon(getClass().getClassLoader().getResource("resources/red_light.jpg"));
                break;
            case "yellow" : image = new ImageIcon(getClass().getClassLoader().getResource("resources/yellow_light.jpg"));
                break;
            case "green" : image = new ImageIcon(getClass().getClassLoader().getResource("resources/green_light.jpg"));
                break;
            
                       
        }
        
    }
}
