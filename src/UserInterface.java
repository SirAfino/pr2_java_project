public interface UserInterface
{
	//OVERVIEW: oggetto modificabile che rappresenta un singolo utente con id e password
	//TYPICAL ELEMENT: <id, password>
	
	public String getId();
	//RETURNS: id dell'utente
	
	public String getPassword();
	//RETURNS: password dell'utente
	
	public void setPassword(String password)throws NullPointerException;
	//REQUIRES: password != null
	//THROWS: se password == null lancia NullPointerException
	//MODIFIES: this
	//EFFECTS: imposta la password dell'utente a password
	
	public boolean equals(Object other) throws NullPointerException;
	//REQUIRES: other != null && other istanceOf(UserInterface)
	//THROWS: se other == null lancia NullPointerException 
	//RETURNS: true se (this.id == other.id) && (this.password == other.password), false altrimenti
	
	public int hashCode();
	//RETURNS: hashcode dell'oggetto
}