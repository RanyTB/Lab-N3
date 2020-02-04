import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;

public class MailAppUDPClient {

   private static void requestURL(){
   }

   public static void main(String[] args) {
      byte[] buf = new byte[2048];

      //Default connection
      int clientPort = 5556;
      int serverPort = 5555;
      String hostURI = "127.0.0.1";

      if(args.length == 1){
         hostURI = args[0];
      }

      try(
              //1 Open a socket
              DatagramSocket socket = new DatagramSocket();

              //2 Open an inputstream from console
              BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
      ){
         System.out.println("Clientsocket created with local port: " + socket.getLocalPort());


         //3.1 Read user input
         System.out.print("Please input URL to extract email adresses: ");
         String userInput = stdIn.readLine();

         //3.2 Send packet to the stream according to servers application protocol
         buf = userInput.getBytes();
         DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(hostURI), serverPort);
         socket.send(packet);

         //3.2 Wait for response
         System.out.println("Waiting for server response...");
         buf = new byte[2048];
         packet = new DatagramPacket(buf, buf.length);
         socket.receive(packet);

         String response = new String(buf);
         String[] responseArray = response.split("\\R");

         switch (responseArray[0]){
            case "0": {
               System.out.println("Found the following addresses:");
               for(int i = 1; i < responseArray.length; i++){
                  System.out.println(responseArray[i]);
               }
               break;
            }
            case "1": {
               System.out.println("!!!No email address found on the page!!!");
               break;
            }
            case "2": {
               System.out.println("!!!Server couldn’t find the web page!!!");
               break;
            }
            default: {
               System.out.println("Unexpected response!");
            }
         }
      } catch (IOException e) {
         e.printStackTrace();
      }

   }
}
