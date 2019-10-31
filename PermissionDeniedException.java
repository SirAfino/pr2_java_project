public class PermissionDeniedException extends Exception
{
	private static final long serialVersionUID = 1L;

	public PermissionDeniedException(String errorMessage)
	{
		super(errorMessage);
	}
}
