package lolChatClientPackage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;


/**
 * Simple Chat Server that uses socket connection to the client and upon
 * receiving a message, returns the message to all clients connected to the server.
 * 
 * @author Luis Gonzalez
 * @author Clayton Walker
 * 
 * 
 *  
 */
public class VerySimpleChatServer extends LoLChatsClient {
	

	ArrayList <Object> clientOutputStreams;
	
	/**
	 * 
	 * Handles the socketing and input stream from the client.
	 * 
	 */
	public class ClientHandler implements Runnable {
		BufferedReader reader;
		Socket sock;
		
		
		/**
		 * Creates the input stream for the buffered reader to read from.
		 * 
		 * @param clientSocket  
		 * 
		 */
		public ClientHandler(Socket clientSocket) {
			try {
				sock = clientSocket;
				InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
				reader = new BufferedReader(isReader);
			} catch(Exception ex) {ex.printStackTrace();}
		}
	
		
		/**
		 * Runs parallel to main and outputs a message client side once received.
		 */
		public void run() {
			String message;
			try {
				while ((message = reader.readLine()) != null) {
					System.out.println("Server read "  + message);
					tellEveryone(message);
					writeToFile(message);
				} 
			} catch(Exception ex) {ex.printStackTrace();}
		} 
	}
	
	public static void main (String[] args) {
		new VerySimpleChatServer().go();
	}
	
	/**
	 * Connects to the client locally.
	 */
	public void go() {
		clientOutputStreams = new ArrayList<Object>();
		try {
			ServerSocket serverSock = new ServerSocket(5000);
			while(true) {
				Socket clientSocket = serverSock.accept();
				PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
				clientOutputStreams.add(writer);
				Thread t = new Thread(new ClientHandler(clientSocket));
				t.start();
				System.out.println("got a connection");
				System.out.println("Server connected to Client");
			}
		} 
		catch(Exception ex) {
			ex.printStackTrace();
		}
	} // close go
	
		/**
		 * Sends the message to all users on the server using the client.
		 * 
		 * @param message
		 * 
		 */
		public void tellEveryone(String message) {
			Iterator<Object> it = clientOutputStreams.iterator();
			while(it.hasNext()) {
				try {
					PrintWriter writer = (PrintWriter) it.next();
					writer.println(message);
					writer.flush();
				}
				catch(Exception ex) {
					ex.printStackTrace();
				}
			}//end while
		}// close tellEveryone
}



