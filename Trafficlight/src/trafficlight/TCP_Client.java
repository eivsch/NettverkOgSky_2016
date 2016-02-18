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

public class TCP_Client 
{
  public static void main(String[] args) throws IOException 
  {
    Client_Window w = new Client_Window();
      
    String hostName = "127.0.0.1"; // default localhost
    int portNumber = 5555;
    
    try
    (
      // create TCP socket
      Socket socket = new Socket(hostName, portNumber);

      // Streamreader
      BufferedReader in = new BufferedReader(
              new InputStreamReader(socket.getInputStream()));
    )
    {   
        // message received from server
        String receivedLight;

        while((receivedLight = in.readLine()) != null){
            // change to corresponding light when message is received  
            w.changeLight(receivedLight);
        }
        
        in.close();
        socket.close();
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
