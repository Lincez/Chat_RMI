Chat_RMI
========

College exercise in which I did a "chat" using Java RMI

There's a RMI Server that collects messages from its Clients. 
Each Client is responsible for autenticating itself with the server, sending messages and querying messages sent from others Clients.

To run it open the server by running the class salaChat.ServerChat;
Each user runs its Client by modifying the server IP in the main function of the class salaChat.ClientUser, then running it.
