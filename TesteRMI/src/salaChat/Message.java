package salaChat;

import java.io.Serializable;

public class Message implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String message = "";
	
	private String senderName = "";
	
	private String directReceiver = null;
	
	public Message(String newSender, String newMessage)
	{
		this.message = newMessage;
		this.senderName = newSender;
	}
	
	public Message(String newSender, String newMessage, String newReceiver)
	{
		this.message = newMessage;
		this.directReceiver = newReceiver;
		this.senderName = newSender;
	}
	
	public String getSender()
	{
		return this.senderName;
	}
	
	public String getMessage()
	{
		return this.message;
	}
	
	public String getDirectReceiver()
	{
		return this.directReceiver;
	}
}
