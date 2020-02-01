import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MailAppMTServer {

   public static void main(String[] args) {

      int port;
      port = args.length == 0 ? 5555 : Integer.parseInt(args[0]);

      try (
              ServerSocket serverSocket = new ServerSocket(port);
      ) {
         System.out.println("Server listening on port " + port);

         while (true) {
               ClientHandler clientHandler = new ClientHandler(serverSocket.accept());
               clientHandler.start();
         }

      } catch (IOException e) {
         e.printStackTrace();
      }


   }
}
