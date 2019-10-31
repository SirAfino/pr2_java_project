public class User implements UserInterface
{
	private String id;
	private String password;
	
	/*	INVARIANTE DI RAPPRESENTAZIONE
	 *I(c): c.id != null
	 * 		c.password != null
	 */		
	
	/*	FUNZIONE DI ASTRAZIONE
	 * A(c): <c.getId(), c.getPassword()>
	 */
	
	public User(String id, String password) throws NullPointerException
	{
		if(id == null)
		{
			throw new NullPointerException("Id cannot be null!");
		}
		if(password == null)
		{
			throw new NullPointerException("Password cannot be null!");
		}
		this.id = id;
		this.password = password;
	}
	
	public String getId()
	{
		return id;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password) throws NullPointerException
	{
		if(password == null)
		{
			throw new NullPointerException("Password cannot be null!");
		}
		this.password = password;
	}
	
	public boolean equals(Object other) throws NullPointerException
	{
		if(other == null)
		{
			throw new NullPointerException("Other object cannot be null!");
		}
		return (this.id.equals(((User)other).getId()) && this.password.equals(((User)other).getPassword()));
	}
	
	public int hashCode()
	{
	    return id.hashCode() * 31 + password.hashCode();
	}
}