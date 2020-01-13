import java.util.Iterator;
import java.util.Vector;

public class VectorSecureDataContainer<E> implements SecureDataContainer<E>
{
	private Vector<SecureData<E>> data;
	private Vector<User> users;
	
	/*		INVARIANTE DI RAPPRESENTAZIONE
	 *I(c): VECTOR DATI NON NULLO
	 *		c.data != null
	 *		
	 *		VECTOR UTENTI NON NULLO
	 *		c.users != null
	 *
	 *		DATI NON NULLI
	 *		c.data.get(i) != null
	 *			0 <= i < c.data.size()
	 *
	 *		UTENTI NON NULLI
	 *		c.users.get(i) != null
	 *			0 <= i < c.users.size()
	 *
	 *		DATI UNICI PER OGNI UTENTE
	 *		!c.data.get(i).getData().equals(c.data.get(j).getData()) || !c.data.get(i).getOwner().equals(c.data.get(j).getOwner())
	 *			0 <= i < j < c.data.size()
	 *
	 *		PROPRIETARI ESISTENTI
	 *		c.users.contains(c.data.get(i).getOwner())
	 *			0 <= i < c.data.size()
	 *
	 *		ID UTENTE UNICI
	 *		!c.users.get(i).getId().equals(c.users.get(j).getId())
	 *			0 <= i < j < c.users.size()
	 *		
	 *		UTENTI CONDIVISI ESISTENTI
	 *	    c.users.contains(c.data.get(i).getShared().get(j))
	 *		0 <= i < c.data.size()
	 *			0 <= j < c.data.get(i).getShared().size()
	 */
	
	/*		FUNZIONE DI ASTRAZIONE
	 * A(c): <(u, c.data.get(j) for(int j : [0,c.data.size()]) . c.data.get(j).isOwner(u))  for(User u : c.users)>
	 */
	
	public VectorSecureDataContainer()
	{
		data = new Vector<SecureData<E>>(0,1);
		users = new Vector<User>(0,1);
	}
	
	private boolean login(String id, String password) throws NullPointerException
	//REQUIRES: id != null && password != null
	//THROWS: se id == null lancia NullPointerException
	//        se password == null lancia NullPointerException
	//RETURNS: true se this contiene l'utente (id, password), false altrimenti
	{
		if(id == null)
		{
			throw new NullPointerException("Id cannot be null!");
		}
		if(password == null)
		{
			throw new NullPointerException("Password cannot be null!");
		}
		for(User u : users)
		{
			if(u.equals(new User(id,password)))
			{
				return true;
			}
		}
		return false;
	}
	
	public void createUser(String id, String password) throws NullPointerException, UnavailableUsernameException
	{
		if(id == null)
		{
			throw new NullPointerException("Id cannot be null!");
		}
		if(password == null)
		{
			throw new NullPointerException("Password cannot be null!");
		}
		for(User u : users)
		{
			if(u.getId().equals(id))
			{
				throw new UnavailableUsernameException("Username is already in use!");
			}
		}
		users.add(new User(id, password));
	}

	public int getSize(String owner, String password) throws NullPointerException, InvalidLoginException
	{
		if(owner == null)
		{
			throw new NullPointerException("Id cannot be null!");
		}
		if(password == null)
		{
			throw new NullPointerException("Password cannot be null!");
		}
		if(!login(owner, password))
		{
			throw new InvalidLoginException("Invalid id or password!");
		}
		int count = 0;
		for(SecureData<E> d : data)
		{
			if(d.isOwner(new User(owner, password)))
			{
				count++;
			}
		}
		return count;
	}

	public boolean put(String owner, String password, E data) throws NullPointerException, InvalidLoginException
	{
		if(owner == null)
		{
			throw new NullPointerException("Id cannot be null!");
		}
		if(password == null)
		{
			throw new NullPointerException("Password cannot be null!");
		}
		if(data == null)
		{
			throw new NullPointerException("Data cannot be null!");
		}
		if(!login(owner, password))
		{
			throw new InvalidLoginException("Invalid id or password!");
		}
		User user = new User(owner, password);
		for(SecureData<E> d : this.data)
		{
			if(d.getData().equals(data) && d.isOwner(user))
			{
				return false;
			}
		}
		this.data.add(new SecureData<E>(data, user));
		return true;
	}

	public E get(String owner, String password, E data) throws NullPointerException, IllegalArgumentException, InvalidLoginException, PermissionDeniedException
	{
		if(owner == null)
		{
			throw new NullPointerException("Id cannot be null!");
		}
		if(password == null)
		{
			throw new NullPointerException("Password cannot be null!");
		}
		if(data == null)
		{
			throw new NullPointerException("Data cannot be null!");
		}
		if(!login(owner, password))
		{
			throw new InvalidLoginException("Invalid id or password!");
		}
		boolean present = false;
		for(SecureData<E> d : this.data)
		{
			if(d.getData().equals(data))
			{
				present = true;
				if(d.hasAccess(new User(owner, password)))
				{
					return d.getData();
				}
			}
		}
		if(!present)
		{
			throw new IllegalArgumentException("Data cannot be found!");
		}
		throw new PermissionDeniedException("Permission denied!");
	}

	public E remove(String owner, String password, E data) throws NullPointerException, IllegalArgumentException, InvalidLoginException, PermissionDeniedException
	{
		if(owner == null)
		{
			throw new NullPointerException("Id cannot be null!");
		}
		if(password == null)
		{
			throw new NullPointerException("Password cannot be null!");
		}
		if(data == null)
		{
			throw new NullPointerException("Data cannot be null");
		}
		if(!login(owner, password))
		{
			throw new InvalidLoginException("Invalid id or password!");
		}
		boolean present = false;
		int index = 0;
		for(SecureData<E> d : this.data)
		{
			if(d.getData().equals(data))
			{
				present = true;
				if(d.isOwner(new User(owner, password)))
				{
					this.data.remove(index);
					return d.getData();
				}
			}
			index++;
		}
		if(!present)
		{
			throw new IllegalArgumentException("Data cannot be found!");
		}
		throw new PermissionDeniedException("Permission denied!");
	}
	
	public void copy(String owner, String password, E data) throws NullPointerException, IllegalArgumentException, InvalidLoginException, PermissionDeniedException
	{
		if(owner == null)
		{
			throw new NullPointerException("Id cannot be null!");
		}
		if(password == null)
		{
			throw new NullPointerException("Password cannot be null!");
		}
		if(data == null)
		{
			throw new NullPointerException("Data cannot be null!");
		}
		if(!login(owner, password))
		{
			throw new InvalidLoginException("Invalid id or password!");
		}
		boolean present = false;
		User user = new User(owner, password);
		for(SecureData<E> d : this.data)
		{
			if(d.getData().equals(data))
			{
				present = true;
				if(d.hasAccess(user))
				{
					put(owner, password, data);
					return;
				}
			}
		}
		if(!present)
		{
			throw new IllegalArgumentException("Data cannot be found!");
		}
		throw new PermissionDeniedException("Permission denied!");
	}

	public void share(String owner, String password, String other, E data) throws NullPointerException, IllegalArgumentException, InvalidLoginException, PermissionDeniedException
	{
		if(owner == null)
		{
			throw new NullPointerException("Id cannot be null!");
		}
		if(password == null)
		{
			throw new NullPointerException("Password cannot be null!");
		}
		if(data == null)
		{
			throw new NullPointerException("Data cannot be null!");
		}
		if(other == null)
		{
			throw new NullPointerException("Id cannot be null!");
		}
		if(!login(owner, password))
		{
			throw new InvalidLoginException("Invalid id or password!");
		}
		User otherUser = null;
		for(User u : users)
		{
			if(u.getId().equals(other))
			{
				otherUser = u;
				break;
			}
		}
		if(otherUser == null)
		{
			throw new IllegalArgumentException("Other user cannot be found!");
		}
		boolean present = false;
		for(SecureData<E> d : this.data)
		{
			if(d.getData().equals(data))
			{
				present = true;
				if(d.isOwner(new User(owner, password)))
				{
					d.addShared(otherUser);
					return;
				}
			}
		}
		if(!present)
		{
			throw new IllegalArgumentException("Data cannot be found!");
		}
		throw new PermissionDeniedException("Permission denied!");
	}

	public Iterator<E> getIterator(String owner, String password) throws NullPointerException, InvalidLoginException
	{
		if(owner == null)
		{
			throw new NullPointerException("Id cannot be null!");
		}
		if(password == null)
		{
			throw new NullPointerException("Password cannot be null!");
		}
		if(!login(owner, password))
		{
			throw new InvalidLoginException("Invalid id or password!");
		}
		Vector<E> items = new Vector<E>(0,1);
		for(SecureData<E> d : data)
		{
			if(d.isOwner(new User(owner, password)))
			{
				items.add(d.getData());
			}
		}
		return new SecureDataIterator<E>(items);
	}
}