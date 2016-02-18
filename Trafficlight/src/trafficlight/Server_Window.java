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
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author s198752
 */
public class Server_Window extends JFrame{
    public static JTextArea logArea, clientListArea;
    private JSlider redSlider, yellowSlider, greenSlider;
    private Icon redIcon, greenIcon, yellowIcon;
    private JRadioButton offButton, redButton, yellowButton, greenButton;
    //private JPanel leftColumn, rightColumn, topLeft, bottomLeft, radioPanel, sliderPanel, picturePanel; 
    private final String labelOff = "Off", labelRed = "Red", labelYellow = "Yellow", labelGreen = "Green";
    private int redDuration = 10, yellowDuration = 5, greenDuration = 15;
    private String defaultColor = "Red";
    private JLabel pictureLabel;
    private ImageIcon image;
    public static ArrayList<String> clients;
    private JPanel masterPanel, grid, under;
    private DrawPanel dp;
    
    public Server_Window(){          
        super("Trafficlight - group 13");
        
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
        
        redButton.addActionListener(al); 
        yellowButton.addActionListener(al); 
        greenButton.addActionListener(al);

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
        
        redSlider = new JSlider(JSlider.HORIZONTAL, 5, 50, 10);
        redSlider.setMinorTickSpacing(1);
        redSlider.setMajorTickSpacing(5);
        redSlider.setPaintTicks(true);
        redSlider.setPaintLabels(true);
        yellowSlider = new JSlider(JSlider.HORIZONTAL, 2, 12, 5);
        yellowSlider.setMinorTickSpacing(1);
        yellowSlider.setMajorTickSpacing(2);
        yellowSlider.setPaintTicks(true);
        yellowSlider.setPaintLabels(true);
        greenSlider = new JSlider(JSlider.HORIZONTAL, 5, 50, 15);
        greenSlider.setMinorTickSpacing(1);
        greenSlider.setMajorTickSpacing(5);
        greenSlider.setPaintTicks(true);
        greenSlider.setPaintLabels(true);
        
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
        
        JPanel sliderPanel = new JPanel(new GridLayout(0,1));
        sliderPanel.add(new JLabel(""));
        sliderPanel.add(redSlider);
        sliderPanel.add(yellowSlider);
        sliderPanel.add(greenSlider);
        
        // set up text areas
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
        
        grid.add(radioPanel);
        grid.add(sliderPanel);
        grid.add(dp);
        under.add(textAreaPanel, BorderLayout.CENTER);
        //setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        this.getContentPane().add(masterPanel);
        setSize(600,800);
        
        
        
        //createAndShowGUI();
    }
   
    /*
    public void createAndShowGUI(){
        JFrame frame = new JFrame("Trafficlight");
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JComponent newContentPane = new Server_Window();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);
        //frame.setSize(800, 400);
        frame.pack();
        frame.setVisible(true);
    }
    */
    
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

    public void setImage(String color) {
        switch(color){
            case "red" : dp.changePicture("red_light.jpg");
                break;
            case "yellow" : dp.changePicture("yellow_light.jpg");
                break;
            case "green" : dp.changePicture("green_light.jpg");
                break;                 
        }      
    }

    
    private class DrawPanel extends JPanel{
        Image image1;
        public void changePicture(String path){
            image1 = new ImageIcon(path).getImage();
            repaint();
        }
        public void paintComponent(Graphics g)
        {
            g.setColor(new Color(238,238,238));
            g.fillRect(10,10,134,76);
            g.drawImage(image1,10,10,this);
        }
    }
}
