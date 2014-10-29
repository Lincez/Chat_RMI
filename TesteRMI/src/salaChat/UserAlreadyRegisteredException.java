package salaChat;

public class UserAlreadyRegisteredException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	private String userName = "";
	
	public UserAlreadyRegisteredException(String userName_)
	{
		super("User Already Registered: " + userName_);
		this.userName = userName_;
	}
	
	public String getUserName()
	{
		return this.userName;
	}
	
}
