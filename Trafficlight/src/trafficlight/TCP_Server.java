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


public class TCP_Server
{
    static int counter_green;
    static int counter_yellow;
    static int counter_red;
    static int global_counter;
    static String current_light;
    static int portNumber;
    static Server_Window w;
    static ServerSocket serverSocket;
    public static void main(String[] args) throws IOException
    {
        w = new Server_Window();
        Server_Window.createAndShowGUI();
        
        portNumber = 5555; // Default port to use

        if (args.length > 0)
        {
            if (args.length == 1)
                portNumber = Integer.parseInt(args[0]);
            else
            {
                System.err.println("Usage: java EchoUcaseServerMutiClients [<port number>]");
                System.exit(1);
            }
        }

        System.out.println("Hi, I am the EchoUCase Multi-client TCP server.");

        try{
          // Create server socket with the given port number
          serverSocket = new ServerSocket(portNumber);
        } catch (IOException e)
        {
            System.out.println("Exception occurred when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
                
        
        // scheduled task which runs every second
        counter_green = 20;
        counter_yellow = 5;
        counter_red = 10;
        
        global_counter = counter_red;
        current_light = "red";
        
        Runnable runnable = new Runnable()
        {
            @Override
            public void run() 
            {
                // check parameters in server window
                counter_red = w.getRedDuration();
                counter_yellow = w.getYellowDuration();
                counter_green = w.getGreenDuration();
                
                
                
                System.out.println(current_light + ": " + global_counter);
                
                if(global_counter == 1)
                {
                    switch (current_light) {
                        case "red":
                            current_light = "green";
                            global_counter = counter_green;
                            break;
                        case "yellow":
                            current_light = "red";
                            global_counter = counter_red;
                            break;
                        default:
                            current_light = "yellow";
                            global_counter = counter_yellow;
                            break;
                    }
                }
                
                global_counter--;
                
                checkForClients();
            }
        };

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);
    }
    
    public static void checkForClients(){
        try 
        {
            System.out.println("C");
            // continuously listening for clients 
            
                // create and start a new ClientServer thread for each connected client
                ClientServer clientserver = new ClientServer(serverSocket.accept());
                System.out.println("E");
                clientserver.start();
                System.out.println("D");
                //w.getClientListArea().append(clientserver.getIP());
     
                    
        } catch (IOException e)
        {
            System.out.println("Exception occurred when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}

class ClientServer extends Thread
{
    Socket connectSocket;
    InetAddress clientAddr;
    String IP;

    public ClientServer(Socket connectSocket)
    {
        System.out.println("B");
        this.connectSocket = connectSocket;
        clientAddr = connectSocket.getInetAddress();
        
        
    }
    
    


    public void run()
    {
        System.out.println("F");
        try (
                // Create server socket with the given port number
                PrintWriter out =
                        new PrintWriter(connectSocket.getOutputStream(), true);
                // Stream reader from the connection socket
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(connectSocket.getInputStream()));
        )
        {

            IP = InetAddress.getLocalHost().getHostAddress();
            System.out.println("A");
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
            connectSocket.close();

        } catch (IOException e)
        {
            System.out.println("Exception occurred when trying to communicate with the client " + clientAddr.getHostAddress());
            System.out.println(e.getMessage());
        }
    }
    
    public String getIP(){
        return IP;
    }
}


