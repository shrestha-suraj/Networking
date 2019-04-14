//Name: Suraj Shrestha
//Project 2

import java.io.*;
import java.net.*;
import java.util.*;

public final class WebServer {
	public static void main(String argv[]) throws Exception{
		int port = 6789;
		ServerSocket welcomeSocket = new ServerSocket(port);
		while (true) {
			Socket connectingSocket = welcomeSocket.accept();
			HttpRequest request = new HttpRequest(connectingSocket);
			Thread thread = new Thread(request);
			thread.start();
		}
	}
}

final class HttpRequest implements Runnable{
	final static String CRLF ="\r\n";
	Socket socket;
	public HttpRequest(Socket incomingSocket) throws Exception{
		socket=incomingSocket;
	}
	public void run() {
		try {
			processRequest();
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}

	private void processRequest() throws Exception{
		DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
		BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String requestLine = inFromClient.readLine();
		System.out.println();
		System.out.println(requestLine);
		String headerLine = null;
		while((headerLine=inFromClient.readLine()).length()!=0) {
			System.out.println(headerLine);
		}
		StringTokenizer tokens = new StringTokenizer(requestLine);
		tokens.nextToken();
		String fileName = tokens.nextToken();
		fileName = "." + fileName;
		FileInputStream fileIn = null;
		boolean fileExists = true;
		try {
			fileIn = new FileInputStream(fileName);
		}
		catch(FileNotFoundException e) {
			fileExists = false;
		}
		String statusLine = null;
		String contentTypeLine = null;
		String entityBody = null;
		if (fileExists) {
			statusLine = "HTTP/1.0 200 OK" + CRLF;
			contentTypeLine = "Content-type: " + contentType(fileName) + CRLF;
		}
		else {
			statusLine = "HTTP/1.0 404 Not Found" + CRLF;
			contentTypeLine = "no contents" + CRLF;
			entityBody = "<HTML>" + "<HEAD><TITLE>NotFound</TITLE></HEAD>" + fileName + "<BODY>Not Found</BODY></HTML>";
		}
		outToClient.writeBytes(statusLine);
		outToClient.writeBytes(contentTypeLine);
		outToClient.writeBytes(CRLF);
		if (fileExists) {
			sendBytes(fileIn,outToClient);
			fileIn.close();
		}
		else {
		outToClient.writeBytes(entityBody);
		}
		outToClient.close();
		inFromClient.close();
		socket.close();
	}

	private void sendBytes(FileInputStream fileIn, DataOutputStream outToClient) throws Exception{
		byte[] buffer = new byte[1024];
		int bytes = 0;
		while((bytes=fileIn.read(buffer))!=-1) {
			outToClient.write(buffer,0,bytes);
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
