import java.util.Iterator;
import java.util.Vector;

public class SecureDataIterator<E> implements Iterator<E>
{
	Vector<E> items;
	int index;
	
	/*		INVARIANTE DI RAPPRESENTAZIONE
	 * I(c):c.items != null
	 *		c.index >= 0
	 *		c.index <= items.size()
	 *		c.items.get(i) != null		0 <= i < c.items.size()
	 */		
	
	/*		FUNZIONE DI ASTRAZIONE
	 * A(c): (c.index, <k for(E k : c.items)>)
	 */
	
	public SecureDataIterator(Vector<E> items)
	{
		this.items = items;
		index = 0;
	}
	
	public boolean hasNext()
	{
		if(index < items.size())
		{
			return true;
		}
		return false;
	}

	public E next()
	{
		if(hasNext())
		{
			return items.get(index++);
		}
		return null;
	}
}
