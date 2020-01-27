package util;

import java.io.IOException;
import java.net.ConnectException;
import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class MailAddressServiceTest {

   @Test
    void getAddresses() {

      assertThrows(MalformedURLException.class, () -> {
         new MailAddressService(("invalid"));
      }, "Should throw MalformedURLException on invalid URL");

      assertThrows(Exception.class, () -> {
         MailAddressService service = new MailAddressService(("http://jtpajt2415125awta.com"));
         service.getAddresses();
      }, "Should throw ConnectException on unreachable host");

   }

}