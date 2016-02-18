/**
 * Nettverk og skytjenester
 * HiOA vår 2016
 * Oblig 1
 * Gruppe 13:
 * Gretar Ævarsson - s198586
 * Eivind Schulstad - s198752
 **/

package trafficlight;

import java.net.*;
import java.io.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class TCP_Server
{
    
    static int timer_green;     // green time
    static int timer_yellow;    // yellow time
    static int timer_red;       // red time
    static int countdown;    // timer used for countdown
    static String current_light;
    static int portNumber;      
    
    // global variable, is true for 1 sec when traffic light changes
    public static boolean global_changed;
    
    public static void main(String[] args) throws IOException
    {
        // open the server window
        Server_Window w = new Server_Window();
        w.setDefaultCloseOperation(EXIT_ON_CLOSE);
        w.setVisible(true);
        w.setResizable(false);
        
        // Initialize
        timer_green = 20;
        timer_yellow = 5;
        timer_red = 5;      
        countdown = timer_red;
        current_light = "red";
        portNumber = 5555; 
        global_changed = false;

        // generate two threads, one for sockets and one for timer
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        
        // first thread: timer that runs every second
        Runnable timer = () -> {                    
            
            // check if parameters have changed
            timer_red = w.getRedDuration();
            timer_yellow = w.getYellowDuration();
            timer_green = w.getGreenDuration();

            // set variable to false every second but is overidden below if 
            // certain criterias are met
            global_changed = false;
            
            // if timer is up
            if(countdown == 1)
            {
                // change light and reset countdown
                switch (current_light) {
                    case "red":
                        current_light = "green";                            
                        countdown = timer_green+1;
                        break;
                    case "yellow":
                        current_light = "red";
                        countdown = timer_red+1;
                        break;
                    default:
                        current_light = "yellow";
                        countdown = timer_yellow+1;
                        break;
                }
                
                // used for checking when to send message to clients
                global_changed = true;
                
                // write to log
                Server_Window.logArea.append("Color sent to clients: " + current_light + "\n");

                // update image
                w.setImage(current_light);                
            }
            countdown--; 
        };        
        
        // second thread: listens to incoming TCP socket connections
        Runnable incomingSockets = () -> {
            try (
                // Create server socket with the given port number
                ServerSocket serverSocket = new ServerSocket(portNumber);
            )
            {
                // continuously listening for clients 
                while (true)
                {
                    // create and start a new ClientServer thread for each connected client
                    ClientServer clientserver = new ClientServer(serverSocket.accept());
                    clientserver.start();
                }
            } catch (IOException e)
            {
                System.out.println("Exception occurred when trying to listen on port "
                        + portNumber + " or listening for a connection");
                System.out.println(e.getMessage());
            }
        };
        
        // first thread runs every second
        executor.scheduleAtFixedRate(timer, 0, 1, TimeUnit.SECONDS);
        
        // second thread runs continously
        executor.schedule(incomingSockets, 0, TimeUnit.SECONDS);
    }
}

// a thread is created for each client that connects to the server
class ClientServer extends Thread
{
    Socket connectSocket;
    InetAddress clientAddr;

    // contructor
    public ClientServer(Socket connectSocket)
    {
        this.connectSocket = connectSocket;
        clientAddr = connectSocket.getInetAddress();
        
        // add client's IP to the client list on GUI
        Server_Window.getClientListArea().append(clientAddr.toString() + "\n");
    }

    public void run()
    {
        try (
                // Create server socket with the given port number
                PrintWriter out = new PrintWriter(connectSocket.getOutputStream(), true);
                // Stream reader from the connection socket
                BufferedReader in = new BufferedReader( new InputStreamReader(connectSocket.getInputStream()));
        )
        {
            String sendToClient;
            long lastSec = 0;   // used for delay
            
            while((sendToClient = TCP_Server.current_light) != null && !sendToClient.isEmpty()){
                
                // induce 1 sec delay, to prevent 'flooding' of messages to clients
                long sec = System.currentTimeMillis() / 1000;
                
                if (sec != lastSec) {
                    boolean sendMessage = TCP_Server.global_changed;
                    
                    if(sendMessage)
                        out.println(TCP_Server.current_light);
             
                   lastSec = sec;
                }
            }            
                 
            // close the connection socket
            System.out.println("Server Exiting");
            connectSocket.close();
        } catch (IOException e){
            System.out.println("Exception occurred when trying to communicate with the client " + clientAddr.getHostAddress());
            System.out.println(e.getMessage());
        }
    }
}



