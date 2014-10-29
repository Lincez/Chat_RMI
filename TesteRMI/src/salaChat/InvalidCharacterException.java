package salaChat;

public class InvalidCharacterException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	private char c;
	
	public InvalidCharacterException(char c_)
	{
		super("Invalid Character Detected: " + c_);
		this.c = c_;
	}
	
	public char getCharacter()
	{
		return this.c;
	}

}
