package salaChat;

public class ReceiverThread extends Thread
{
	private IF_Server server;
	private String userName; 
	
	public ReceiverThread(IF_Server server_, String userName_)
	{
		this.server = server_;
		this.userName = userName_;
	}
	
	public synchronized void run()
	{
		try
		{
			boolean chave = true;
			while (chave)
			{
				Message message = server.requestMessage(userName);
				if (message != null)
				{
					if (message.getDirectReceiver() != null)
						print("[DM]" + message.getSender() + ": " + message.getMessage());
					else
						print(message.getSender() + ": " + message.getMessage());
				}
				this.wait(500); // Pra não causar overhead desnecessauro
			}
		}
		catch (UserNotRegisteredException e)
		{
			print("Logged out!");
			//print(e.toString());
		}
		catch (Exception e)
		{
			print("Error:");
			print(e.toString());
		}
	}
	
	public void print(String message)
	{
		System.out.println(message);
	}

}
