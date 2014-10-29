package salaChat;

import java.rmi.Naming;

public class ClientUser
{
	private IF_Server server;
	
	private String userName = "";
	
	ReceiverThread receiverThread = null;
	
	public void connectToServer(String serverIp) throws Exception
	{
		this.server = (IF_Server)Naming.lookup("//" + serverIp + ":1020" + "/ServerChat");
	}
	
	private void sendDirectMessage(String content) throws Exception
	{
		String receiverName = "";
		String message = "";
		String[] contentParts = content.split(" ");
		receiverName = content.substring(1, content.indexOf(' '));
		for (int i = 1; i < contentParts.length; i++)
		{
			message += contentParts[i] + " ";
		}
		try
		{
			server.sendDirectMessage(userName, receiverName, message);
		}
		catch (UserNotRegisteredException e)
		{
			this.receiverThread.print(e.toString());
		}
	}
	
	private void register() throws Exception
	{
		this.userName = "";
		boolean ok = false;
		while (ok == false)
		{
			try
			{
				this.userName = aplicacao.Console.readLine("Digite o nome de usuário: ");
				server.registerUser(userName);
				ok = true;
			}
			catch (UserAlreadyRegisteredException e)
			{
				System.out.println("ERRO: Nome de usuário já cadastrado!");
			}
			catch (InvalidCharacterException e)
			{
				System.out.println("ERRO: Caracter inválido: '" + e.getCharacter() + "'");
			}
		}
	}
	
	public void run() throws Exception
	{
		this.register();
		
		System.out.println("Users Online: " + server.getUsersList());
		
		this.receiverThread = new ReceiverThread(this.server, this.userName);
		this.receiverThread.start();
		
		try
		{
			boolean chave = true;
			while (chave)
			{
				String message = aplicacao.Console.readLine();
				if (message.equals("\\sair")) chave = false;
				else if (message.equals("\\users"))
				{
					System.out.println("Users Online: " + server.getUsersList());
				}
				else if (message.startsWith("@"))
				{
					this.sendDirectMessage(message);
				}
				else server.sendMessage(userName, message);
			}
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		
		server.unregisterUser(userName);
	}
	
	public static void main(String[] args)
	{
		try
		{
			System.out.println("Connecting...");
			ClientUser client = new ClientUser();
			client.connectToServer("127.0.0.1");
			System.out.println("Connected!");
			client.run();
		}
		catch (Exception e)
		{
			System.out.println("Client Error:");
			System.out.println(e);
		}
		
	}

}
