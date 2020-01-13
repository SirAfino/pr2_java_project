import java.util.Vector;

public interface SecureDataInterface<E>
{
	//OVERVIEW: tipo modificabile che rappresenta un dato generico con un utente proprietario
	//			ed un insieme di utenti che hanno accesso al dato
	//TYPICAL ELEMENT: (dato, proprietario, <utente1, utente2, utente3, ...>)
	//				   dove dato è l'informazione di tipo generico
	//				   proprietario rappresenta l'utente che possiede l'informazione
	//				   e utente1...utenteN rappresentano gli utenti a cui l'informazione è stata condivisa
	
	public E getData();
	//RETURNS: dato
	
	public void setData(E data) throws NullPointerException;
	//REQUIRES: data != null
	//THROWS: se data == null lancia NullPointerException
	//MODIFIES: this
	//EFFECTS: setta il dato a data
	
	public User getOwner();
	//RETURNS: l'utente proprietario del dato
	
	public void setOwner(User owner) throws NullPointerException;
	//REQUIRES: owner != null
	//THROWS: se owner == null lancia NullPointerException
	//MODIFIES: this
	//EFFECTS: setta il proprietario a owner
	
	public Vector<User> getShared();
	//RETURNS: la lista di utenti che hanno accesso al dato
	
	public void setShared(Vector<User> shared) throws NullPointerException;
	//REQUIRES: shared != null
	//THROWS: se shared == null lancia NullPointerException
	//MODIFIES: this
	//EFFECTS: setta la lista di utenti che hanno accesso al dato a shared
	
	public void addShared(User user)throws NullPointerException;
	//REQUIRES: user != null
	//THROWS: se user == null lancia NullPointerException
	//MODIFIES: this
	//EFFECTS: aggiunge alla lista di utenti che hanno accesso al dato l'utente user
	
	public boolean isOwner(User owner)throws NullPointerException;
	//REQUIRES: owner != null
	//THROWS: se owner == null lancia NullPointerException
	//RETURNS: true se owner è il proprietario del dato, false altrimenti
	
	public boolean hasAccess(User user)throws NullPointerException;
	//REQUIRES: user != null
	//THROWS: se user == null lancia NullPointerException
	//RETURNS: true se user ha accesso al dato, false altrimenti
}
