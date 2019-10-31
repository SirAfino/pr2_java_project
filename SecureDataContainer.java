import java.util.Iterator;

public interface SecureDataContainer<E>
{
	//OVERVIEW: insieme modificabile di utenti e dati, ogni dato ha un utente proprietario e 
	//			può essere condiviso con altri utenti
	//TYPICAL ELEMENT: <(utente1,<sdata1_1,sdata1_2,sdata1_3, ...>), (utente2,<sdata2_1,sdata2_2,sdata2_3, ...>), ...>
	//                 dove sdata è la tripla (info, u_proprietario, <u_condiviso1, u_condiviso2, ...>)
	//                 e u_proprietario, u_condiviso e utente sono costituiti dalla coppia (id, password)
	
	public void createUser(String id, String password) throws NullPointerException, UnavailableUsernameException;
	//REQUIRES: id != null && password != null
	//			&& this non contiene l'utente (id,*)
	//THROWS: se id == null lancia NullPointerException
	//		  se password == null lancia NullPointerException
	//        se this contiene l'utente (id, *) lancia UnavailableUsernameException
	//MODIFIES: this
	//EFFECTS: crea l'utente (id, password) e lo aggiunge alla lista degli utenti
	
	public int getSize(String owner, String password) throws NullPointerException, InvalidLoginException;
	//REQUIRES: owner != null && password != null
	//			&& this contiene l'utente (owner, password)
	//THROWS: se owner == null lancia NullPointerException
	//		  se password == null lancia NullPointerException
	//		  se this non contiene l'utente (owner, password) lancia InvalidLoginException
	//RETURNS: il numero di elementi presenti nella collezione di cui
	//		   l'utente (owner, password) è proprietario
	
	public boolean put(String owner, String password, E data) throws NullPointerException, InvalidLoginException;
	//REQUIRES: owner != null && password != null && data != null
	//			&& this contiene l'utente (owner, password)
	//THROWS: se owner == null lancia NullPointerException
	//		  se password == null lancia NullPointerException
	//		  se this non contiene l'utente (owner, password) lancia InvalidLoginException
	//MODIFIES: this
	//EFFECTS: se this non contiene l'elemento data con proprietario l'utente (owner, password)
	//         viene aggiunto alla collezione l'elemento data e gli viene settato
	//         come proprietario l'utente (owner, password)
	//RETURNS: true se l'elemento viene inserito nella collezione, false altrimenti 

	public E get(String owner, String password, E data) throws NullPointerException, IllegalArgumentException, InvalidLoginException, PermissionDeniedException;
	//REQUIRES: owner != null && password != null && data != null
	//			&& this contiene l'utente (owner, password)
	//          && this contiene un elemento data
	//          && l'utente (owner, password) ha accesso ad almeno un elemento data
	//THROWS: se owner == null lancia NullPointerException
	//		  se password == null lancia NullPointerException
	//        se data == null lancia NullPointerException
	//		  se this non contiene l'utente (owner, password) lancia InvalidLoginException
	//        se this non contiene l'elemento data lancia IllegalArgumentException
	//        se l'utente (owner, password) non ha accesso a nessun elemento data lancia PermissionDeniedException
	//RETURNS: una copia dell'elemento data
	
	public E remove(String owner, String password, E data) throws NullPointerException, IllegalArgumentException, InvalidLoginException, PermissionDeniedException;
	//REQUIRES: owner != null && password != null && data != null
	//			&& this contiene l'utente (owner, password)
	//          && this contiene un elemento data
	//          && l'utente (owner, password) è proprietario dell'elemento data
	//THROWS: se owner == null lancia NullPointerException
	//		  se password == null lancia NullPointerException
	//        se data == null lancia NullPointerException
	//        se this non contiene l'utente (owner, password) lancia InvalidLoginException
	//        se this non contiene l'elemento data lancia IllegalArgumentException
	//        se l'utente (owner, password) non è proprietario dell'elemento data lancia PermissionDeniedException
	//MODIFIES: this
	//EFFECTS: rimuove l'elemento data che ha come proprietario l'utente (owner, password)
	//RETURNS: l'elemento data rimosso
	
	public void copy(String owner, String password, E data) throws NullPointerException, IllegalArgumentException, InvalidLoginException, PermissionDeniedException;
	//REQUIRES: owner != null && password != null && data != null
	//			&& this contiene l'utente (owner, password)
	//          && this contiene un elemento data
	//          && l'utente (owner, password) ha accesso ad almeno un elemento data
	//THROWS: se owner == null lancia NullPointerException
	//		  se password == null lancia NullPointerException
	//        se data == null lancia NullPointerException
	//        se this non contiene l'utente (owner, password) lancia InvalidLoginException
	//        se this non contiene l'elemento data lancia IllegalArgumentException
	//        se l'utente (owner, password) non ha accesso a nessun elemento data lancia PermissionDeniedException
	//MODIFIES: this
	//EFFECTS: se l'utente (owner, password) non possiede già l'elemento data, viene creata
	//         una copia nella collezione dell'elemento data, il cui proprietario è l'utente (owner, password)
	
	public void share(String owner, String password, String other, E data) throws NullPointerException, IllegalArgumentException, InvalidLoginException, PermissionDeniedException;
	//REQUIRES: owner != null && password != null && data != null && other != null
	//          && this contiene l'utente (other, *)
	//			&& this contiene l'utente (owner, password)
	//          && this contiene un elemento data
	//          && l'utente (owner, password) è proprietario dell'elemento data
	//THROWS: se owner == null lancia NullPointerException
	//		  se password == null lancia NullPointerException
	//        se data == null lancia NullPointerException
	//        se other == null lancia NullPointerException
	//        se this non contiene l'utente (owner, password) lancia InvalidLoginException
	//        se this non contiene l'elemento data lancia IllegalArgumentException
	//  	  se this non contiene l'utente (other, *) lancia IllegalArgumentException
	//        se l'utente (owner, password) non è proprietario dell'elemento data lancia PermissionDeniedException
	//MODIFIES: this
	//EFFECTS: condivide l'elemento data con l'utente (other, *), che avrà accesso all'elemento in futuro
	
	public Iterator<E> getIterator(String owner, String password) throws NullPointerException, InvalidLoginException;
	//REQUIRES: owner != null && password != null
	//		    && this contiene l'utente (owner, password)
	//THROWS: se owner == null lancia NullPointerException
	//		  se password == null lancia NullPointerException
	//        se this non contiene l'utente (owner, password) lancia InvalidLoginException
	//RETURNS: l'iteratore che itera arbitrariamente tra tutti gli elementi data che
	//         hanno come proprietario l'utente (owner, password)
}