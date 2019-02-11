import java.io.*;
import java.net.*; //This package defines Scoeket() and ServerSocket() classes

public class TCPClient{

    public static void main (Stirng args[]){
        String sentence;
        Stirng modifiedSentence;
        BufferReader inFromUser=new BufferReader (new InputStreamReader(System.in)/* System will hold remeber data by bufferreader*/);
        Socket clientSocket = new Socket("hostname",6789);
        DataOutStream outToServer = new DataOutputStream (clientSocket.getOutputStream());
        BufferReader inFromServer=new BufferReader(new InputStreamReader(clientSocket.getInputStream()));
        sentence=inFromUser.readLine();
        outToServer.writeBytes(sentence+"\n");
        modifiedSentence=inFromServer.readLine();
        System.out.println("FROM SERVER: "+modifiedSentence);
        clientSocket.close();
    }
}
public class TCPServer{
    public static void main (String args[]){
        String clientSentence;
        Stirng capitalizedSentence;
        ServerSocket welcomeSocket=new ServerSocket(6789);
        while(true){
            Socket connectionSocket=welcomeSocket.accept();
            BufferReader inFromClient= new BufferReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream (connectionSocket.getOutputStream());
            clientSentence=inFromClient.readLine();
            capitalizedSentence=clientSentence.toUpperCase();//Finish this by understanding

        }
        
    }
}