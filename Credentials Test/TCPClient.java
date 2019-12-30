import java.io.*; 
import java.net.*; 
import java.util.Scanner;
class TCPClient {
    public static void main(String argv[]) throws Exception 
    { 
        System.out.println("Welcome to Suraj Shrestha FTP Protocol Demo");
        System.out.println("**********You must login first**************");
        String sentence; 
        String modifiedSentence;
        Scanner inFromUser=new Scanner(System.in); 
        //BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        Socket clientSocket = new Socket("localhost", 6789); 
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        System.out.print("Login Id: "); 
        sentence = inFromUser.nextLine();
        System.out.print("Password: ");
        sentence =sentence+","+inFromUser.nextLine();
        outToServer.writeBytes(sentence + '\n'); 
        modifiedSentence = inFromServer.readLine();
        System.out.println("FROM SERVER: " + modifiedSentence); 
        clientSocket.close();           
    } 
}  


