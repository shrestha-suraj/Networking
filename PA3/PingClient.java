import java.io.*;
import java.net.*;
import java.util.*;

public class PingClient {

   private static final int MAX_TIMEOUT = 1000;
   private static int serverPort = 1044;

   public static void main(String[] args) throws Exception {
      // Check if command line argument satisfies required data input
      if (args.length != 2) {
         System.out.println("Argument Pass Error. Syntax: hostname port");
         return;
      }
      InetAddress hostAddress = InetAddress.getByName(args[0]);
      int port = Integer.parseInt(args[1]);
      // Datagram socket for receiving and sending UDP packets through the port specified on the command line.
      DatagramSocket clientSocket = new DatagramSocket(port);
      // Loop for packet processing
      for (int sequence=0;sequence<10;sequence++) {
         // Timestamp in ms when we send it
         Date date = new Date();
         long time = date.getTime();
         // Create string to send, and transfer i to a Byte Array
         String msg = "Ping " + sequence + "\r " + time + " CRLF\n";
         byte[] byteholder = new byte[1024];
         byteholder = msg.getBytes();
         // Sending the Ping datagram to the server.
         clientSocket.send(new DatagramPacket(byteholder, byteholder.length, hostAddress, serverPort));
         try {
            // Set up the timeout 1000 ms= 1 sec.. Time to decide packet lost
            clientSocket.setSoTimeout(MAX_TIMEOUT);
            //UPD packet for recieving
            DatagramPacket receiver=new DatagramPacket(new byte[1024], 1024);
            // Try to receive the response from the ping
            clientSocket.receive(receiver);
            // timestamp for when we received the packet
            date = new Date();
            long timeReceived = date.getTime();
            // Print the packet and the delay
            printData(receiver, timeReceived - time);
         } catch (IOException e) {
            System.out.println("Packet Timeout: " + sequence);
         }
      }
      clientSocket.close();
   }

   /*
    * Print ping data to the standard output stream.
    */
   private static void printData(DatagramPacket request, long delayTime) throws Exception {
      // Obtain references to the packet's array of bytes.
      byte[] buf = request.getData();

      // Wrap the bytes in a byte array input stream, so that you can read the data as
      // a stream of bytes.
      ByteArrayInputStream bais = new ByteArrayInputStream(buf);

      // Wrap the byte array output stream in an input stream reader,
      // so you can read the data as a stream of characters.
      InputStreamReader isr = new InputStreamReader(bais);

      // Wrap the input stream reader in a bufferred reader,
      // so you can read the character data a line at a time.
      // (A line is a sequence of chars terminated by any combination of \r and \n.)
      BufferedReader br = new BufferedReader(isr);

      // The message data is contained in a single line, so read this line.
      String line = br.readLine();

      // Print host address and data received from it.
      System.out.println("Received from " + request.getAddress().getHostAddress() + ": " + new String(line) + " Delay: "
            + delayTime);
   }
}