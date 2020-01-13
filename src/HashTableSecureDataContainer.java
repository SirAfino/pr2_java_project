import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

public class HashTableSecureDataContainer<E> implements SecureDataContainer<E>
{
	private Hashtable<User, Vector<SecureData<E>>> hashtable;
	
	/*		INVARIANTE DI RAPPRESENTAZIONE (ht = hashtable)
	 * I(c):HASHTABLE NON NULLA
	 *		c.ht != null
	 *
	 *		ID UTENTE UNICI
	 *		for each k,j in c.ht.keys() . k != j => (!k.getId().equals(j.getId())))
	 *
	 *		UTENTI NON NULLI
	 *		for each k in c.ht.keys() => k != null
	 *
	 *		VECTOR DI DATI NON NULLI
	 *		for each k in c.ht.keys() => c.ht.get(k) != null
	 *
	 *		DATI NON NULLI
	 *		for each k in c.ht.keys() => c.ht.get(k).get(i) != null
	 *			0 <= i < c.ht.get(k).size()
	 *
	 *		DATI UNICI PER OGNI UTENTE
	 *		for each k in c.ht.keys() => !c.ht.get(k).get(i).equals(c.ht.get(k).getj))
	 *			0 <= i < j < c.ht.get(k).size()
	 *		
	 *		UTENTI CONDIVISI ESISTENTI
	 *		for each k in c.ht.keys() => (c.ht.keys().contains(c.ht.get(k).get(i).getShared().get(j)))
	 *			0 <= i < c.ht.get(k).size()
	 *			0 <= j < c.ht.get(k).get(i).getShared().size()
	 */
	
	/*		FUNZIONE DI ASTRAZIONE
	 *A(c): <(k, <c.ht.get(k).get(j) for(int j : [0,c.ht.get(k).size()])>) for(User k : c.ht.keys())>
	 */
	
	public HashTableSecureDataContainer()
	{
		hashtable = new Hashtable<User, Vector<SecureData<E>>>(0,1);
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
		Enumeration<User> users = hashtable.keys();
		while(users.hasMoreElements())
		{
			if(users.nextElement().getId().equals(id))
			{
				throw new UnavailableUsernameException("Username is already in use!");
			}
		}
		hashtable.put(new User(id, password), new Vector<SecureData<E>>(0,1));
	}

	public int getSize(String owner, String password) throws NullPointerException, InvalidLoginException
	{
		if(owner == null)
		{
			throw new NullPointerException();
		}
		if(password == null)
		{
			throw new NullPointerException();
		}
		if(!hashtable.containsKey(new User(owner, password)))
		{
			throw new InvalidLoginException("Invalid id or password!");
		}
		return hashtable.get(new User(owner, password)).size();
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
			throw new NullPointerException("Data cannot be null");
		}
		if(!hashtable.containsKey(new User(owner, password)))
		{
			throw new InvalidLoginException("Invalid id or password!");
		}
		Vector<SecureData<E>> elements = hashtable.get(new User(owner, password));
		if(!elements.contains(new SecureData<E>(data)))
		{
			elements.add(new SecureData<E>(data));
			hashtable.put(new User(owner, password), elements);
			return true;
		}
		return false;
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
			throw new NullPointerException("Data cannot be null");
		}
		if(!hashtable.containsKey(new User(owner, password)))
		{
			throw new InvalidLoginException("Invalid id or password!");
		}
		boolean present = false;
		int index = hashtable.get(new User(owner, password)).indexOf(new SecureData<E>(data));
		if(index != -1)
		{
			return hashtable.get(new User(owner, password)).get(index).getData();
		}
		Enumeration<User> keys = hashtable.keys();
		while(keys.hasMoreElements())
		{
			User user = keys.nextElement();
			index = hashtable.get(new User(owner, password)).indexOf(new SecureData<E>(data));
			if(index != -1)
			{
				present = true;
				if(hashtable.get(new User(owner, password)).get(index).hasAccess(user))
				{
					return hashtable.get(new User(owner, password)).get(index).getData();
				}
			}
		}
		if(!present)
		{
			throw new IllegalArgumentException("Data is not present!");
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
			throw new NullPointerException("Data cannot be null!");
		}
		if(!hashtable.containsKey(new User(owner, password)))
		{
			throw new InvalidLoginException("Invalid id or password!");
		}
		int index = hashtable.get(new User(owner, password)).indexOf(new SecureData<E>(data));
		if(index != -1)
		{
			return hashtable.get(new User(owner, password)).remove(index).getData();
		}
		throw new IllegalArgumentException("Data is not present!");
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
		if(!hashtable.containsKey(new User(owner, password)))
		{
			throw new InvalidLoginException("Invalid id or password!");
		}
		boolean present = false;
		Enumeration<User> keys = hashtable.keys();
		while(keys.hasMoreElements())
		{
			User user = keys.nextElement();
			int index = hashtable.get(user).indexOf(new SecureData<E>(data));
			if(index != -1)
			{
				present = true;
				if(hashtable.get(user).get(index).hasAccess(new User(owner, password)))
				{
					put(owner, password, data);
					return;
				}
			}
		}
		if(!present)
		{
			throw new IllegalArgumentException("Data is not present!");
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
		if(!hashtable.containsKey(new User(owner, password)))
		{
			throw new InvalidLoginException("Invalid id or password!");
		}
		User otherUser = null;
		Enumeration<User> keys = hashtable.keys();
		while(keys.hasMoreElements() && otherUser == null)
		{
			User user = keys.nextElement();
			if(user.getId().equals(other))
			{
				otherUser = user;
			}
		}
		if(otherUser == null)
		{
			throw new IllegalArgumentException("Other user cannot be found!");
		}
		Vector<SecureData<E>> elements = hashtable.get(new User(owner, password));
		int index = elements.indexOf(new SecureData<E>(data));
		if(index != -1)
		{
			elements.get(index).addShared(otherUser);
			return;
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
		if(!hashtable.containsKey(new User(owner, password)))
		{
			throw new InvalidLoginException("Invalid id or password!");
		}
		Vector<SecureData<E>> elements = hashtable.get(new User(owner, password));
		Vector<E> items = new Vector<E>(0,1);
		for(SecureData<E> d : elements)
		{
			items.add(d.getData());
		}
		return new SecureDataIterator<E>(items);
	}
}