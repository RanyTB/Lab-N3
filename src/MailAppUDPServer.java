import util.MailAddressService;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;

public class MailAppUDPServer {

   public static void main(String[] args) {
      byte[] buf;

      try (
         DatagramSocket serverSocket = new DatagramSocket(5555);
      ) {
         System.out.println("Server listening on port: " + serverSocket.getLocalPort());

         while(true){

            buf = new byte[2048];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            serverSocket.receive(packet); //Blocking

            String receivedText = new String(packet.getData(), 0, packet.getLength());
            int clientPort = packet.getPort();
            InetAddress clientIP = packet.getAddress();

               StringBuilder response = new StringBuilder();
            try {
               MailAddressService mailAddressService = new MailAddressService(receivedText);
               String addresses = mailAddressService.getAddresses();
               if (addresses != null) {
                  response.append("0\n");
                  response.append(addresses);
               } else {
                  response.append("1\n");
               }
            } catch (IOException e) {
            response.append("2\n");
         }

            buf = response.toString().getBytes();
            packet = new DatagramPacket(buf, buf.length, clientIP, clientPort);

            serverSocket.send(packet);

         }
      } catch (SocketException e) {
         System.err.println("Could not create server socket");
         e.printStackTrace();
      } catch(IOException e){
         System.err.println("Could not handle packet or something...");
         e.printStackTrace();
      }


   }

}
