package lolChatClientPackage;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

/**
 * 
 * Creates the GUI and handles connecting to the server, sending
 * messages to the server, creating a message log, and saving the 
 * the contacts list.
 * 
 * @author Luis Gonzalez
 * @author Clayton Walker
 *
 */

public class LoLChatsClient {
	
	//logIn fields
	JFrame logInFrame;
	JPanel logInPanel;
	JLabel usernameLabel, passwordLabel;
	JTextField usernameField, passwordField;
	String username, password;
	JButton logInButton, logInTerminateButton;
	
	//main fields
	JFrame mainFrame;
	JPanel mainPanel;
	JTextArea incoming;
	JTextArea contactArea;
	JTextField outgoing;
	JButton sendButton, registerContactButton, terminateButton;
	BufferedReader reader;
	PrintWriter writer;
	Socket sock;
	ArrayList<Contact> myContactList = new ArrayList <Contact>();
	
	//logInError Fields
	JFrame logInErrorFrame;
	JPanel logInErrorPanel;
	public int failedLogInAttempts = 0;
	
	//logOut fields
	JButton logOutButton;
	
	//addContact fields
	JFrame newContactFrame;
	JPanel newContactPanel;
	JLabel firstNameLabel, lastNameLabel, typeLabel, yearLabel, programLabel, PIDLabel;
	JTextField firstNameField, lastNameField, typeField, yearField, programField, PIDField;
	String firstName, lastName, type, year, program, PID;	
	JButton addContactButton;
	
	/**
	 * Generates the GUI, connects to the server, and starts 
	 * the parallel thread.
	 */
	public void go() {
		
		//Declaring Frames and Panel
		logInFrame = new JFrame("LoLChats Log In");
		logInPanel = new JPanel();
		
		//Declaring frameLogIn Buttons and Labels
				
		//usernameLabel
		usernameLabel = new JLabel("Username:");
		usernameField = new JTextField(20);
				
		//passwordLabel
		passwordLabel = new JLabel("Password:");
		passwordField = new JTextField(20);
		
		//log in button
		logInButton = new JButton("Log In");
		logInButton.addActionListener(new logInButtonListener());
		
		//Terminate Button Log In
		logInTerminateButton = new JButton("Terminate");
		logInTerminateButton.addActionListener(new logInTerminateButtonListener());

		//add your stuff to the login panel
		logInPanel.add(usernameLabel);
		logInPanel.add(usernameField);
		logInPanel.add(passwordLabel);
		logInPanel.add(passwordField);
		logInPanel.add(logInButton);
		logInPanel.add(logInTerminateButton);
		
		//setting up frameLogIn
		logInFrame.getContentPane().add(BorderLayout.CENTER, logInPanel);
		logInFrame.setSize(400,500);
		logInFrame.setVisible(true);					
		
		setUpNetworking();
		Thread readerThread = new Thread(new IncomingReader());
		readerThread.start();
	} 
	
	//logInButton 
	/**
	 * 
	 * Retrieves the contact list upon logging in and launches 
	 * the messaging client.
	 *
	 */
	public class logInButtonListener implements ActionListener {
		
		@SuppressWarnings("unchecked")
		public void actionPerformed(ActionEvent ev) {
			try {
				//username and password fields. 
				username = usernameField.getText();
				password = passwordField.getText();
				
				//Only successful log in if username and password are valid
				if ((username.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin")) || (username.equalsIgnoreCase("guest") && password.equalsIgnoreCase("guest"))) {
		        						
					//After your password is verified
					mainFrame = new JFrame("Welcome "+ username + "!");
					mainPanel = new JPanel();
					
					//incoming text area (messages text area)
					incoming = new JTextArea(15,50);
		    		incoming.setLineWrap(true);
		    		incoming.setWrapStyleWord(true);
		    		incoming.setEditable(false);
		    		JScrollPane qScroller = new JScrollPane(incoming);
		    		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		    		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		    		
		    		//contactArea space and initialization
		    		contactArea = new JTextArea(20,15);
		    		contactArea.setLineWrap(true);
		    		contactArea.setWrapStyleWord(true);
		    		contactArea.setEditable(false);
		    		JScrollPane contactScroller = new JScrollPane(contactArea);
		    		contactScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		    		contactScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		    		
		    		//populate the contact area based on the saved contact list file
		    		try {
						//calling back serialized contactList
						FileInputStream fileStream = new FileInputStream ("ContactList.ser");
						ObjectInputStream objin = new ObjectInputStream(fileStream);
						myContactList = (ArrayList<Contact>) objin.readObject();
						for(Contact c : myContactList){
							contactArea.append(c.getFirstName() + " " + c.getLastName() + " (" + c.getType() + ") \n");
						}
					} 
					catch(Exception ex) {
						 ex.printStackTrace();
					}
		    		
		    		
		    		//outgoing text field (message text field) and sendButton
		    		outgoing = new JTextField(20);
		    		sendButton = new JButton("Send");
		    		sendButton.addActionListener(new SendButtonListener());
		    		
		    		//registerContactButton
		    		registerContactButton = new JButton("Add Contact");
		    		registerContactButton.addActionListener(new registerContactButtonListener());
		    		
		    		//log out button
		    		logOutButton = new JButton("Log Out");
		    		logOutButton.addActionListener(new logOutButtonListener());

					//terminateButton
		    		terminateButton = new JButton("Terminate");
					terminateButton.addActionListener(new terminateButtonListener());
		        	
			        //add your stuff to the main panel
					mainPanel.add(qScroller);				
					mainPanel.add(contactScroller);
					mainPanel.add(outgoing);
					mainPanel.add(sendButton);
					mainPanel.add(registerContactButton);
					mainPanel.add(logOutButton);
					mainPanel.add(terminateButton);
					
					//Open mainFrame
					mainFrame.getContentPane().add(BorderLayout.CENTER, mainPanel);
					mainFrame.setSize(400,500);
					mainFrame.setVisible(true);
					
					//Close the logInFrame
					if(failedLogInAttempts == 0){
						logInFrame.dispose();
						logInFrame.setVisible(false);
					}
					else if(failedLogInAttempts > 0){
						logInErrorFrame.dispose();
						logInErrorFrame.setVisible(false);
					}
				}
				//Else, announce invalid user and clear fields
				else {
					++failedLogInAttempts;
					
					logInErrorFrame = new JFrame("Invalid User/Password!");
					logInErrorPanel = new JPanel();
					
					//logInErrorPanel labels, fields, and buttons
					logInErrorPanel.add(usernameLabel);
					logInErrorPanel.add(usernameField);
					logInErrorPanel.add(passwordLabel);
					logInErrorPanel.add(passwordField);
					logInErrorPanel.add(logInButton);
					logInErrorPanel.add(logInTerminateButton);
					
					//Clear the fields for retry of log in
					usernameField.setText("Invalid username/password");
					passwordField.setText("Try Again");
					
					//Setup log in error frame
					logInErrorFrame.getContentPane().add(BorderLayout.CENTER, logInErrorPanel);
					logInErrorFrame.setSize(400,500);
					logInErrorFrame.setVisible(true);
					
					//dispose of invalid log in frame
					logInFrame.dispose();
					logInFrame.setVisible(false);
				}
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}

	} //end of trying log in button
	
	/**
	 * 
	 * Terminates the button upon button press.
	 *
	 */
	//trying logInTerminateButtonListener
	public class logInTerminateButtonListener implements ActionListener {
	
		public void actionPerformed(ActionEvent ev) {
			try {
				//terminates the program upon button press
				System.exit(0);
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	} //end of trying logInTerminateButton
	
	/**
	 * 
	 * Opens up a new window that allows the user to add
	 * a new contact.
	 *
	 */
	//registerContactButtonListener
	public class registerContactButtonListener implements ActionListener {
			
		public void actionPerformed(ActionEvent e) {
			try {
				//add a new contact
				newContactFrame = new JFrame("New Contact");
				newContactPanel = new JPanel();
				
				//Declaring Buttons and Labels
				
				//new userAccount fields
				firstNameLabel = new JLabel("First Name:");
				firstNameField = new JTextField(20);
				
				lastNameLabel = new JLabel("Last Name:");
				lastNameField = new JTextField(20);
				
				typeLabel = new JLabel("Account Type (student, faculty, staff):");
				typeField = new JTextField(20);
				
				yearLabel = new JLabel("Year (Freshmen, Sophomore, Junior, Senior, Instructor):");
				yearField = new JTextField(20);
				
				programLabel = new JLabel("Program (CS, IT, CpE, EE):");
				programField = new JTextField(20);
				
				PIDLabel = new JLabel("PID:");
				PIDField = new JTextField(20);
				
				//addContactButton
				addContactButton = new JButton("Add New Contact");
				addContactButton.addActionListener(new addContactButtonListener());
				
		        //add your stuff to the new contact panel				
				newContactPanel.add(firstNameLabel);
				newContactPanel.add(firstNameField);
				newContactPanel.add(lastNameLabel);
				newContactPanel.add(lastNameField);
				newContactPanel.add(typeLabel);
				newContactPanel.add(typeField);
				newContactPanel.add(yearLabel);
				newContactPanel.add(yearField);
				newContactPanel.add(programLabel);
				newContactPanel.add(programField);
				newContactPanel.add(PIDLabel);
				newContactPanel.add(PIDField);
				newContactPanel.add(addContactButton);
				
				//Open newContactFrame
				newContactFrame.getContentPane().add(BorderLayout.CENTER, newContactPanel);
				newContactFrame.setSize(400,500);
				newContactFrame.setVisible(true);
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * Adds a contact to the contact array list upon button press
	 *
	 */
	//addContactButtonListener
	public class addContactButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			try {
				//New fields for adding a contact
				firstName = firstNameField.getText();
				lastName = lastNameField.getText();
				type = typeField.getText();
				year = yearField.getText();
				program = programField.getText();
				PID = PIDField.getText();
				
				/*
				//add to array list
				myContactList.add(newUsername);
				contactArea.append(newUsername + "\n");
				System.out.println("Contact list is: " + myContactList);*/
				
				//add Contact to array list
				Contact newAddContact = new Contact(PID, firstName, lastName, year, program, type);
				myContactList.add(newAddContact);
				contactArea.append(newAddContact.getFirstName() + " " + newAddContact.getLastName() + " (" + newAddContact.getType() + ")\n");
				//System.out.println("Contact list is: " + myContactList.toString());
				
				newContactFrame.dispose();
				newContactFrame.setVisible(false);
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	} //end of addContactButtonListener
	
	/**
	 * 
	 * Kills the current main window session and restarts the
	 * program with a new log in window
	 *
	 */
	//logOutButton 
	public class logOutButtonListener implements ActionListener {
			
		public void actionPerformed(ActionEvent ev) {
			try {
				//this kills the current main window session, and essentially
				//re-starts the program with a new log in window
				mainFrame.dispose();
				mainFrame.setVisible(false);
				LoLChatsClient client2 = new LoLChatsClient();
				client2.go();
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	} //end of trying logOutButton
	
	/**
	 * 
	 * Saves the contact list to a file through serializable then
	 * terminates the application completely.
	 *
	 */
	//trying out terminate button 
	public class terminateButtonListener implements ActionListener {
								
		public void actionPerformed(ActionEvent ev) {
			try {
				//this saves the contact list to a file through serializable
				//when the terminate button is pressed
				//and then the program exits
				FileOutputStream fs = new FileOutputStream("ContactList.ser");
				ObjectOutputStream os = new ObjectOutputStream (fs);
				os.writeObject(myContactList);
				os.close();
				System.exit(0);
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	} //end of trying terminate button
	
	/**
	 * 
	 * Reads the message typed in to the application sending to the
	 * server and appending to the message log file.
	 *
	 */
	public class IncomingReader implements Runnable {

		public void run() {
				
			String message;
			try {
				//this reads the message, and says what the client read
				//this also sends that message to the writeToFile method
				//which saves the message to a logFile
				while ((message = reader.readLine()) != null) {
					System.out.println("Client read " + message);
					incoming.append(message + "\n");
					//messageLog
					//writeToFile(message);
				} 
			} 
			catch(Exception ex) {
				ex.printStackTrace();
			}
		} 
	}//end of incoming reader
	
	/**
	 *
	 *Sends a message typed in by the user to the server on
	 *button press with who sent it and time stamp.
	 *
	 */
	public class SendButtonListener implements ActionListener {
		
		public void actionPerformed(ActionEvent ev) {
			GregorianCalendar gc = new GregorianCalendar();
			 String timestamp = gc.get(Calendar.HOUR_OF_DAY) + ":" + gc.get(Calendar.MINUTE) + "-" 
			 + (gc.get(Calendar.MONTH)+1) + "/" + gc.get(Calendar.DAY_OF_MONTH) + "/" + gc.get(Calendar.YEAR);
			//this sends the message to the server when the 
			//send button is pressed
			try {
				writer.println(username + ": " + outgoing.getText() + "		" + timestamp + "\n");
				writer.flush();
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
			outgoing.setText("");
			outgoing.requestFocus();
		}
	}//end of send button listener
	
	/**
	 * Takes in the message typed in by the user and saves it to
	 * a message log file (if the file is not present then it is
	 * created by FileWriter).
	 * 
	 * @param message
	 * @throws FileNotFoundException
	 */
	//Takes in the message and saves it to a message log file called
	//logFile.txt
	public static void writeToFile(String message) throws FileNotFoundException {
		 		 
		 boolean append = true;
		 
		 try {
			 FileWriter writer = new FileWriter("logFile.txt" , append);
			 writer.write(message + "\n");
			 writer.close();
		 } 
		 catch (IOException e) {
			 e.printStackTrace();
		 }
	}//end of write to file
	
	/**
	 * Connects to the socket established by VerySimpleChatServer
	 * and readies the application for sending and receiving messages.
	 */
	private void setUpNetworking(){
		//Sets up the networking with VerySimpleChatServer
		//and sets up the server IP and port.
		try {
			sock = new Socket("127.0.0.1", 5000);
			InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
			reader = new BufferedReader(streamReader);
			writer = new PrintWriter(sock.getOutputStream());
			System.out.println("Client connected to Server");
			System.out.println("networking established");
		} 
		catch(IOException ex) {
			ex.printStackTrace();
		}
	}//end set up networking
	
	public static void main(String[] args) {
		//main program just calls go, which connects to the 
		//server, and then brings up the log in frame window
		LoLChatsClient client = new LoLChatsClient();
		client.go();
	}//close main
	
}//close outer class


