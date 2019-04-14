import java.io.*;
import java.net.*;
import java.util.*;

public final class WebServer {
    public static void main(String argv[]) throws Exception {
        // Set the port number
        int port = 6789;
        // Establish the listen socket
        ServerSocket welcomeSocket = new ServerSocket(port);
        // Process HTTP service requests in an infinite loop
        while (true) {
            Socket connectingSocket = welcomeSocket.accept();
            HttpRequest request = new HttpRequest(connectingSocket);
            Thread thread = new Thread(request);
            thread.start();
        }
    }
}

final class HttpRequest implements Runnable {
    final static String CRLF = "\r\n";
    Socket socket;

    public HttpRequest(Socket each_socket) throws Exception {
        socket = each_socket;
    }

    public void run() {
        try {
            processRequest();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void processRequest() throws Exception {
        InputStream is = socket.getInputStream();
        BufferedReader inFromClient = new BufferedReader(new InputStreamReader(is));
        DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
        String requestLine = inFromClient.readLine();
        System.out.println();
        System.out.println(requestLine);
        String headerLine = null;
        while ((headerLine = inFromClient.readLine()).length() != 0) {
            System.out.println(headerLine);
        }
        StringTokenizer tokens = new StringTokenizer(requestLine);
        tokens.nextToken();
        String fileName = tokens.nextToken();
        fileName = "." + fileName;
        FileInputStream fis = null;
        boolean fileExists = true;
        try {
            fis = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            fileExists = false;
        }
        String statusLine = null;
        String contentTypeLine = null;
        String entityBody = null;
        if (fileExists) {
            statusLine = "HTTP/1.0 200 OK" + CRLF;
            contentTypeLine = "Content-type: " + contentType(fileName) + CRLF;
        } else {
            statusLine = "HRRP/1.0 404 Not Found" + CRLF;
            contentTypeLine = "no comments" + CRLF;
            entityBody = "<HTML>" + "<HEAD><TITLE>Not Found</TITLE></HEAD>" + fileName
                    + "<BODY>Not Found</BODY><?HTML>";
        }
        outToClient.writeBytes(statusLine);
        outToClient.writeBytes(contentTypeLine);
        outToClient.writeBytes(CRLF);
        if (fileExists) {
            sendBytes(fis, outToClient);
            fis.close();
        } else {
            outToClient.writeBytes(entityBody);
        }
        outToClient.close();
        inFromClient.close();
        socket.close();
    }

    private void sendBytes(FileInputStream fis, DataOutputStream os) throws Exception {
        // construct a 1k buffer to hold bytes on their way to the socket
        byte[] buffer = new byte[1024];
        int bytes = 0;

        // copy requested file into the sockets output stream
        while ((bytes = fis.read(buffer)) != -1) {
            os.write(buffer, 0, bytes);
        }

    }

    private String contentType(String fileName) {
        if (fileName.endsWith(".htm") || fileName.endsWith(".html")) {
            return "text/html";
        }
        if (fileName.endsWith(".gif") || fileName.endsWith(".GIF")) {
            return "image/gif";
        }

        if (fileName.endsWith(".jpeg") || fileName.endsWith(".jpg")) {
            return "image/jpeg";
        }
        return "application/octet-stream";
    }

}