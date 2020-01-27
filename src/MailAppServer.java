import util.MailAddressService;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MailAppServer {

   private static int port = 5555;

   public static void main(String[] args) {

      if(args.length == 1){
         port = Integer.parseInt(args[0]);
      }

      try (
              //Create socket
              ServerSocket serverSocket = new ServerSocket(port);
              Socket clientSocket = serverSocket.accept();

              //Open an inputstream and output stream to the socket
              PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
              BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      ) {
         System.out.println("Client " + clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " connected");

         String inputLine;
         while((inputLine = in.readLine()) != null){
            System.out.println("Request: {" + inputLine + "}");

            try {
               MailAddressService mailAddressService = new MailAddressService(inputLine);
               String addresses = mailAddressService.getAddresses();

               if (addresses != null) {
                  out.println(0);
                  out.println(addresses);
                  out.close();

               } else {
                  out.println(1);
               }
            } catch(IOException e){
               out.println(2);
            }
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
   }



}
