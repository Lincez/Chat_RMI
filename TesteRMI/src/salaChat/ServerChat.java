package salaChat;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerChat extends UnicastRemoteObject implements IF_Server
{
	private static final long serialVersionUID = 1L;

	HashMap<String, Integer> clientUserList = new HashMap<String, Integer>();
	
	ArrayList<Message> messageList	= new ArrayList<Message>();

	@Override
	public boolean registerUser(String userName) throws Exception
	{
		if (this.clientUserList.containsKey(userName))
		{
			throw new UserAlreadyRegisteredException(userName);			
		}
		
		String invalidCharacter = "@ \\";
		
		for (int i = 0; i < invalidCharacter.length(); i++)
		{
			if (userName.indexOf(invalidCharacter.charAt(i)) != -1)
			{
				throw new InvalidCharacterException(invalidCharacter.charAt(i));
			}			
		}
		
		this.clientUserList.put(userName, messageList.size() + 1);
		//this.clientUserList.put(userName, 0);
		
		System.out.println(this.getUsersList());
		
		this.sendMessage("Server", userName + " is online");
		
		return this.clientUserList.containsKey(userName);
	}
	
	@Override
	public void unregisterUser(String userName) throws Exception
	{
		this.sendMessage("Server", userName + " is offline");
		this.clientUserList.remove(userName);
		System.out.println(this.getUsersList());
	}

	@Override
	public void sendMessage(String userName, String message) throws Exception
	{
		if (!userName.equals("Server"))
			this.checkForUser(userName);
		
		this.messageList.add(new Message(userName, message));
	}
	
	@Override
	public void sendDirectMessage(String userName, String receiverName, String message) throws Exception
	{
		this.checkForUser(userName);
		
		this.checkForUser(receiverName);
		
		this.messageList.add(new Message(userName, message, receiverName));
	}

	@Override
	public Message requestMessage(String userName) throws Exception
	{
		//System.out.println("Requesting Message for " + userName);
		
		this.checkForUser(userName);
		
		int index = this.clientUserList.get(userName);
		
		// Se não houver mensagens novas
		if (index == this.messageList.size()) return null;
		
		Message message = this.messageList.get(index);
		this.clientUserList.put(userName, index+1);
		
		// Se foi enviada por userName
		if (message.getSender().equals(userName)) return null;
		
		// Se for Mensagem Direta e userName não for o destinatário
		if (message.getDirectReceiver() != null)
			if (!message.getDirectReceiver().equals(userName)) return null;
		
		System.out.println("Returning message for " + userName);
		
		return message;
	}
	
	public String getUsersList() throws Exception
	{
		return this.clientUserList.keySet().toString();
	}
	
	public void checkForUser(String userName) throws UserNotRegisteredException
	{
		if (!this.clientUserList.containsKey(userName))
		{
			System.out.println(userName + " not registered");
			throw new UserNotRegisteredException(userName);
		}		
	}

	public static void main(String[] args)
	{
		// Start the server
		
		try
		{
			System.out.println("Abrindo Servidor");
			
			ServerChat server = new ServerChat();
			Registry registry = LocateRegistry.createRegistry(1020);
			registry.rebind("ServerChat", server);
			
			System.out.println("Ligado ao registro");
		}
		catch (Exception e)
		{
			System.out.println("Erro:");
			System.out.println(e);
		}

	}
	
	private ServerChat() throws Exception
	{
	}

}
