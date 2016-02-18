/**
 * 
 * trafficlight server-class!
 * 
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
    public static int timer_green;
    static int timer_yellow;
    static int timer_red;
    static int global_timer;
    static int client_counter;    
    static String current_light;
    static int portNumber;    
    public static boolean changed;
    
    public static void main(String[] args) throws IOException
    {
        Server_Window w = new Server_Window();
        w.setDefaultCloseOperation(EXIT_ON_CLOSE);
        w.setVisible(true);
        w.setResizable(false);
        //w.createAndShowGUI();
        
        portNumber = 5555; // Default port to use
        changed = false;
        
        System.out.println("HEI");                
        
        // scheduled task which runs every second
        timer_green = 20;
        timer_yellow = 5;
        timer_red = 5;      
        global_timer = timer_red;
        client_counter = 0;
        current_light = "red";
        
        // generate two threads, one for sockets and one for timer
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        
        // timer that runs every second
        Runnable timer = () -> {                    
            
            // check parameters in server window
            timer_red = w.getRedDuration();
            timer_yellow = w.getYellowDuration();
            timer_green = w.getGreenDuration();

            //System.out.println(current_light + ": " + global_timer);
            changed = false;
            
            if(global_timer == 1)
            {
                switch (current_light) {
                    case "red":
                        current_light = "green";                            
                        global_timer = timer_green+1;
                        break;
                    case "yellow":
                        current_light = "red";
                        global_timer = timer_red+1;
                        break;
                    default:
                        current_light = "yellow";
                        global_timer = timer_yellow+1;
                        break;
                }
                
                changed = true;
                Server_Window.logArea.append("Color sent to clients: " + current_light + "\n");
                
                
                // update image
                w.setImage(current_light);                
            }

            global_timer--; 
        };        
        
        // Thread that listens to incoming TCP socket connections
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
        
        executor.scheduleAtFixedRate(timer, 0, 1, TimeUnit.SECONDS);
        executor.schedule(incomingSockets, 0, TimeUnit.SECONDS);
        
        
    }
}

class ClientServer extends Thread
{
    Socket connectSocket;
    InetAddress clientAddr;
    
    
    
    public ClientServer(Socket connectSocket)
    {
        this.connectSocket = connectSocket;
        clientAddr = connectSocket.getInetAddress();
        System.out.println("Client connected: " + clientAddr);
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
            System.out.println("Changed: " + TCP_Server.changed);
            
            String sendToClient;
            
            while((sendToClient = TCP_Server.current_light) != null && !sendToClient.isEmpty()){
                System.out.print("");  
                boolean test = TCP_Server.changed;
                if(test){
                    System.out.println("Sending changes to client: " + TCP_Server.current_light);
                    out.println(TCP_Server.current_light);                    
                }
            }
            
            /*
            if(TCP_Server.global_timer == 2){
                System.out.println("test");
                out.println(TCP_Server.current_light);
            }
            */
            /*
            String receivedText;
            // read from the connection socket
            while (((receivedText = in.readLine())!=null))
            {
                System.out.println("Client [" + clientAddr.getHostAddress() + "]: > " + receivedText);
                // Write the converted uppercase string to the connection socket
                String ucaseText = receivedText.toUpperCase();
                out.println(ucaseText);
                System.out.println("Server [" + InetAddress.getLocalHost().getHostAddress() + "]: > " + ucaseText);
            }
            */
                 
            // close the connection socket
            System.out.println("Server Exiting");
            connectSocket.close();

        } catch (IOException e)
        {
            System.out.println("Exception occurred when trying to communicate with the client " + clientAddr.getHostAddress());
            System.out.println(e.getMessage());
        }
    }
}



