/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trafficlight;

import java.net.*;
import java.io.*;

/**
 *
 * @author Gretar
 */
public class TCP_Client 
{
  public static void main(String[] args) throws IOException 
  {
    Client_Window w = new Client_Window();
      
    String hostName = "127.0.0.1"; // default localhost
    int portNumber = 5555;
    
    if(args.length > 0)
    {
      hostName = args[0];
      
      if(args.length > 1)
      {
        portNumber = Integer.parseInt(args[1]);
        
        if(args.length > 2)
        {
          System.err.println("Usage: java EchoClient [<host name>] [<port number>]");
          System.exit(1);
        }
      }
    }
    
    System.out.println("Hi, I'm TCP_client");
    
    try
    (
      // create TCP socket
      Socket socket = new Socket(hostName, portNumber);

      // Streamwriter
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

      // Streamreader
      BufferedReader in = new BufferedReader(
      new InputStreamReader(socket.getInputStream()));

      // Keboard input reader
      BufferedReader keyIn = new BufferedReader(
      new InputStreamReader(System.in))
    )
    {    
        System.out.println("Client [" + InetAddress.getLocalHost().getHostAddress() + "]: > ");

        String receivedLight;
        String previousLight = "";

        while((receivedLight = in.readLine()) != null){
          //System.out.println("Server [" + hostName + "]: > " + recievedLight);
          if(!receivedLight.equals(previousLight)){
              w.changeLight(receivedLight);
              previousLight = receivedLight;
          }
      }
      
      /*
      while((userInput = keyIn.readLine()) != null && !userInput.isEmpty())
      {
        // write keyboard input to the socket
        out.println(userInput);
        
        // read from the socket and display
        String receivedText = in.readLine();
        
        System.out.println("Server [" + hostName + "]: > " + receivedText);
        System.out.print("Client [" + InetAddress.getLocalHost().getHostAddress() + "]: > ");
      }
        */
        System.out.println("Client exiting");
    }
    catch(UnknownHostException e)
    {
      System.err.println("Don't know about host " + hostName);
      System.exit(1);
    }
    catch(IOException e)
    {
      System.err.println("Couldn't get I/O for the connection to " + hostName);
      System.exit(1);
    }
  }
}
