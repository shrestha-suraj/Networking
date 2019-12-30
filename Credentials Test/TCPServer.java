import java.io.*;
import java.net.*;
import java.util.Scanner;

class TCPServer {
    public static void main(String argv[]) throws Exception { 
        String clientSentence; 
        String capitalizedSentence; 
        ServerSocket welcomeSocket = new ServerSocket(6789);
        Socket connectionSocket = welcomeSocket.accept(); 
        BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream())); 
        DataOutputStream  outToClient = new DataOutputStream(connectionSocket.getOutputStream()); 
        clientSentence = inFromClient.readLine(); 
        clientSentence = checkAccount(clientSentence);
        capitalizedSentence = clientSentence.toUpperCase()+ '\n'; 
        outToClient.writeBytes(capitalizedSentence); 
     }
     public static String checkAccount(String credential) throws Exception{
         Scanner scan=new Scanner(new File("user_db.txt"));
         while (scan.hasNextLine()){
             if (scan.nextLine().equals(credential))
                return "User belongs to the system";
         }
         return "User doesnot belong to the system";
     } 
 } 
 

