import util.MailAddressService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {

   private Socket clientSocket;


   public ClientHandler(Socket clintSocket) {
      this.clientSocket = clintSocket;
   }

   public void run() {

      try(
              PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
              BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
      ){
         System.out.println("Client " + clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " connected");

         String inputLine;
         while ((inputLine = in.readLine()) != null) {
            System.out.println("Request from: " + clientSocket.getInetAddress() + ": {\"" + inputLine + "\"}");

            try {
               MailAddressService mailAddressService = new MailAddressService(inputLine);
               String addresses = mailAddressService.getAddresses();

               if (addresses != null) {
                  out.println(0);
                  out.println(addresses);
               } else {
                  out.println(1);
               }
            } catch (IOException e) {
               out.println(2);
            }
            clientSocket.shutdownOutput();
         }
      } catch (IOException e){
         e.printStackTrace();
      }
   }
}
