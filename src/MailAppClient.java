import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MailAppClient {

   private static void requestURL(){
   }

   public static void main(String[] args) {

      //Default connection
      int serverPort = 5555;
      String hostURI = "127.0.0.1";

      if(args.length == 1){
         hostURI = args[0];
      }

      try(
         //1 Open a socket
         Socket socket = new Socket(hostURI, serverPort);

         //2 Open an input stream and output stream to the socket
         PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
         BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

         BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
      ){
         System.out.println("Clientsocket created with local port: " + socket.getLocalPort());

         //3.1 Write to the stream according to server protocol
         System.out.print("Please input URL to extract email adresses: ");
         String userInput = stdIn.readLine();
         out.println(userInput);
         System.out.println("Waiting for server... \n");

         //3.2 Read from the stream according to the server protocol
         String responseLine = in.readLine();

         switch (responseLine){
            case "0": {
               System.out.println("Found the following addresses:");
               while((responseLine = in.readLine() )!= null){
               System.out.println(responseLine);
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
         }
      } catch (IOException e) {
         e.printStackTrace();
      }

   }
}
