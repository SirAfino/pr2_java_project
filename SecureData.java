import java.util.Vector;

public class SecureData<E> implements SecureDataInterface<E>
{
	private E data;
	private User owner;
	private Vector<User> shared;
	
	/*	INVARIANTE DI RAPPRESENTAZIONE
	 *I(c): c.data != null
	 *		c.owner != null
	 *		c.shared != null
	 *		c.shared.get(i) != null		0 <= i < c.shared.size()
	 *		!c.shared.get(i).equals(c.shared.get(j))	0 <= i < j < c.shared.size()
	 */
	
	/*	FUNZIONE DI ASTRAZIONE
	 * A(c): (c.data, c.owner, <k for(User k : shared)>)
	 */
	
	public SecureData(E data, User owner) throws NullPointerException
	{
		if(data == null)
		{
			throw new NullPointerException();
		}
		if(owner == null)
		{
			throw new NullPointerException();
		}
		this.data = data;
		this.owner = owner;
		shared = new Vector<User>(0,1);
	}
	
	public SecureData(E data) throws NullPointerException
	{
		if(data == null)
		{
			throw new NullPointerException();
		}
		this.data = data;
		shared = new Vector<User>(0,1);
	}
	
	public SecureData(E data, User owner, Vector<User> shared) throws NullPointerException
	{
		if(data == null)
		{
			throw new NullPointerException();
		}
		if(owner == null)
		{
			throw new NullPointerException();
		}
		if(shared == null)
		{
			throw new NullPointerException();
		}
		this.data = data;
		this.owner = owner;
		this.shared = shared;
	}
	
	public E getData()
	{
		return data;
	}

	public void setData(E data) throws NullPointerException
	{
		if(data == null)
		{
			throw new NullPointerException();
		}
		this.data = data;
	}

	public User getOwner()
	{
		return owner;
	}

	public void setOwner(User owner) throws NullPointerException
	{
		if(owner == null)
		{
			throw new NullPointerException();
		}
		this.owner = owner;
	}

	public Vector<User> getShared()
	{
		return shared;
	}

	public void setShared(Vector<User> shared) throws NullPointerException
	{
		if(shared == null)
		{
			throw new NullPointerException();
		}
		this.shared = shared;
	}

	public void addShared(User user) throws NullPointerException
	{
		if(user == null)
		{
			throw new NullPointerException();
		}
		if(shared.indexOf(user) == -1)
		{
			shared.add(user);
		}
	}

	public boolean isOwner(User owner) throws NullPointerException
	{
		if(owner == null)
		{
			throw new NullPointerException();
		}
		if(this.owner == null)
		{
			return false;
		}
		return this.owner.equals(owner);
	}

	public boolean hasAccess(User user) throws NullPointerException
	{
		if(user == null)
		{
			throw new NullPointerException();
		}
		if(isOwner(user))
		{
			return true;
		}
		for(User u : shared)
		{
			if(u.equals(user))
			{
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public boolean equals(Object other)
	{
		return this.data.equals(((SecureData<E>) other).getData());
	}
}
