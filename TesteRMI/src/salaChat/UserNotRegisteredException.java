package salaChat;

public class UserNotRegisteredException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	private String userName = "";
	
	public UserNotRegisteredException(String userName_)
	{
		super("User Not Registered: " + userName_);
		this.userName = userName_;
	}
	
	public String getUserName()
	{
		return this.userName;
	}
	
}
