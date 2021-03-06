import java.io.*;
import java.net.*;
import java.util.Scanner;

public class FTPServer {
    public static void main(String args[]) throws Exception {
        ServerSocket welcomeSocket = new ServerSocket(6789);
        Socket socket = welcomeSocket.accept();
        FileInputStream fis = new FileInputStream("D:/server/server4.mp3");
        InputStreamReader irs = new InputStreamReader(fis);
        byte[] inCode = new byte[500000];
        fis.read(inCode, 0, inCode.length);
        OutputStream dos = socket.getOutputStream();
        dos.write(inCode, 0, inCode.length);
        irs.close();
        fis.close();
        welcomeSocket.close();
    }
}