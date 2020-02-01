package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailAddressService {

   private URL url;
   private Pattern emailPattern = Pattern.compile("\\s*([\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4})\\s*");

   /**
    * @param URI Host URI. Protocol must be included
    * @throws MalformedURLException if URL is not correctly formatted
    */
   public MailAddressService(String URI) throws MalformedURLException {
      if(!URI.startsWith("http")) URI = "http://" + URI;
      this.url = new URL(URI);
   }

   /**
    * @return a string of email addresses if found. Null if no email addresses was found.
    * @throws IOException if connection is invalid
    */
   public String getAddresses() throws IOException {
      StringBuilder builder = new StringBuilder();

      try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {

         String line;
         while ((line = in.readLine()) != null) {
            Matcher matcher = emailPattern.matcher(line);
            while (matcher.find()) {
               builder.append(matcher.group(1)).append("\n");
            }
         }
      }

      if (builder.toString().length() > 0) {
         return builder.toString();
      } else return null;
   }
}
