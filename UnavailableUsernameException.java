public class UnavailableUsernameException extends Exception
{
	private static final long serialVersionUID = 1L;

	public UnavailableUsernameException(String errorMessage)
	{
		super(errorMessage);
	}

}
