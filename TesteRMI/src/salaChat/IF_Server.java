package salaChat;


public interface IF_Server extends java.rmi.Remote
{
	public boolean registerUser(String userName) throws Exception;
	
	public void unregisterUser(String userName) throws Exception;
	
	public void sendMessage(String userName, String message) throws Exception;
	
	public void sendDirectMessage(String userName, String receiverName, String message) throws Exception;
	
	public Message requestMessage(String userName) throws Exception;
	
	public String getUsersList() throws Exception;

}
