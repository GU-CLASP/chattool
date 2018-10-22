# Getting started

This repository is a netbeans project. If opened in netbeans it should run "out of the box"

If the jar file is run without any command line parameters it will show a GUI that lets you choose whether to run the server or the client.


## Starting the server

To start the server, use ```java -jar "chattool.jar SERVER``` 

The server will listen for incoming connections on port 20000

To change this, use ```java -jar "chattool.jar SERVER  %PORTNUMBER%```  where %PORTNUMBER% is the portnumber


## Starting the client

To start a client from the commandline you need to specify the IP address and port number to connect to

```java -jar "chattool.jar" CLIENT localhost 20000```

It is also possible to start clients (locally) from the GUI by selecting the button "Additional Client" 



## Speeding up testing cycle of conversation controller objects

The process of starting the server and clients and then logging in is quite tedious when developing. To get round this you can start Conversation Controller objects, along with the required number of clients by using the following syntax:

```
javac -jar "chatool.jar" nogui_ccname_autologin CONVERSATIONCONTROLLERNAME
```

For example:

```java
javac -jar "chatool.jar" nogui_ccname_autologin DyadicTurnByTurnInterface
```

This will automatically start the chattool, load the ConversationController object, initialize the clients and log them in.

The default number of clients to start is 2 (which is specified in ```Configuration.defaultGroupSize```

These can be saved in netbeans project configurations


# Programming the chat-tool

## Workflow

The standard approach to creating a new experimental intervention is to:

1. Create a subclass of ```DefaultConversationController```
2. make sure that ```showCCOnGUI()``` returns true (this will
3. Customize the conversation controller object, e.g. 
..* modify ```participantJoinedConversation(...)``` to specify what happens when a conversation logs in
..* modify ```processChatText(....)```to 
..* ..* Modify


The standard approach to creating a new experimental intervention is to create a subclass of DefaultConversationController. This is the central "switchboard" through which all messages pass. This is also where you write the code (if necessary) that responds to users logging in (e.g. assigning to particular conditions). To make the 





