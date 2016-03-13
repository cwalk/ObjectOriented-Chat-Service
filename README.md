## Introduction

This project simulates a very basic chat service using Object Oriented Java.

The code is based on a class project from my Object Oriented Programming (Java) class when I was in college.

See Project Description.pdf for more information.

## Usage

Copy the project using `git clone  https://github.com/cwalk/ObjectOriented-Chat-Service`

Import the project folder into your Eclipse workspace. This project was built to run as a Java Project in Eclipse.

Once the source is imported, simply run the VerySimpleChatServer as an application. Then you can run the LoLChatsClient application for each person wanting to connect to the chat room.

This program only has 2 default accounts, and are explained in the user manual below.

##User Manual

LoLChats is a messaging application that allows users connected to the server
to chat in a group. 

**How to launch the client:**

The user will have to launch the the VerySimpleChatServer prior to launching the LoLChatsClient application. Once the server is
running, launch the LoLChatsClient application.



**Log In window:**

The Log In window will prompt the user for a Username and Password.

There are two default Accounts. They are:

*-Username: "admin" and Password: "admin"*

*-Username: "guest" and Password: "guest"*

Click the Log In button to proceed.
	
**Terminate Button:**

Upon click, the client will be terminated and the socket to the server will be closed.



**Message window:**

The Message window have 3 main features. The main text area displays all 
the messages sent to the server. The contact list text area to the right 
of the main text area displays a list of the added contacts. The smallest 
text area is where the user will input the message to be sent. Upon hitting 
send the client will send the message to the server to be handled by the 
server. The server will then distribute the message to all users connected
to the server. Each message sent and received will be time-stamped and stored
in a message log locally in the LoLChatClient file folder named "logFile.txt".
	
**Add Contact Button and Window:**

Upon click, the client will open a new window that will allow the user
to add a new contact to a list that is saved to a file locally named 
"ContactList.ser" The contects of this file can be completely deleted
but not modified. This file is saved once the Terminate button is clicked in the Message window.
	
**Log Out Button and Window:**

Upon click, the client will close the current message window and open a 
new Log In window for a new user to Log In to the chat client.
	
**Terminate Button:**

Upon click, the client will be terminated and the socket to the server will be closed.
