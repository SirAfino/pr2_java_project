import java.util.Iterator;

public class HashTableMain
{
	public static void main(String[] args)
	{
		//TEST FUNZIONANTE
		test1();
		//TEST RICHIESTA CON UTENTE NON VALIDO
		test2();
		//TEST DOPPIA INSERZIONE DELLO STESSO DATO CON LO STESSO UTENTE
		test3();
		//TEST RICHIESTA DI UN DATO A CUI NON SI HA L'ACCESSO
		test4();
		//TEST RIMOZIONE DI UN DATO NON PRESENTE
		test5();
		//TEST CREAZIONE DI DUE UTENTI CON LO STESSO ID
		test6();
	}
	
	//Metodo ausiliario per la stampa della collezione
	public static void printData(String owner, String password, HashTableSecureDataContainer<?> container)
	{
		if(owner == null)
		{
			throw new NullPointerException();
		}
		if(password == null)
		{
			throw new NullPointerException();
		}
		if(container == null)
		{
			throw new NullPointerException();
		}
		try
		{
			System.out.println("Dati di " + owner + " (" + container.getSize(owner, password) + ")");
			int i=1;
			Iterator<?> iterator = container.getIterator(owner, password);
			while(iterator.hasNext())
			{
				System.out.println("  Dato #" + i + ": " + iterator.next());
				i++;
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public static void test1()
	{
		System.out.println("----------------TEST1----------------");
		
		Integer d1 = new Integer(47);
		Integer d2 = new Integer(100);
		Integer d3 = new Integer(999);
		
		HashTableSecureDataContainer<Integer> container = new HashTableSecureDataContainer<Integer>();
		try
		{
			container.createUser("Gabriele", "123");
			container.createUser("Francesco", "456");
			container.createUser("Federico", "789");
			
			container.put("Gabriele", "123", d1);
			container.put("Francesco", "456", d2);
			container.put("Gabriele", "123", d3);
			container.share("Gabriele", "123", "Francesco", d1);
			container.share("Gabriele", "123", "Federico", d3);
			container.copy("Francesco", "456", d1);
			container.copy("Federico", "789", d3);
			container.remove("Gabriele", "123", d1);
			
			printData("Gabriele", "123", container);
			printData("Francesco", "456", container);
			printData("Federico", "789", container);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		System.out.println("-------------------------------------\n");
	}
	
	public static void test2()
	{
		System.out.println("----------------TEST2----------------");
		
		Integer d1 = new Integer(47);
		
		HashTableSecureDataContainer<Integer> container = new HashTableSecureDataContainer<Integer>();
		try
		{
			container.createUser("Gabriele", "123");
			container.put("Gabriele", "000", d1);
			printData("Gabriele", "123", container);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		System.out.println("-------------------------------------\n");
	}
	
	public static void test3()
	{
		System.out.println("----------------TEST3----------------");
		
		Integer d1 = new Integer(47);
		
		HashTableSecureDataContainer<Integer> container = new HashTableSecureDataContainer<Integer>();
		try
		{
			container.createUser("Gabriele", "123");
			container.put("Gabriele", "123", d1);
			container.put("Gabriele", "123", d1);
			printData("Gabriele", "123", container);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		System.out.println("-------------------------------------\n");
	}
	
	public static void test4()
	{
		System.out.println("----------------TEST4----------------");
		
		Integer d1 = new Integer(47);
		
		HashTableSecureDataContainer<Integer> container = new HashTableSecureDataContainer<Integer>();
		try
		{
			container.createUser("Gabriele", "123");
			container.createUser("Francesco", "456");
			
			container.put("Gabriele", "123", d1);
			container.get("Francesco", "456", d1);
			
			printData("Gabriele", "123", container);
			printData("Francesco", "456", container);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		System.out.println("-------------------------------------\n");
	}
	
	public static void test5()
	{
		System.out.println("----------------TEST5----------------");
		
		Integer d1 = new Integer(47);
		
		HashTableSecureDataContainer<Integer> container = new HashTableSecureDataContainer<Integer>();
		try
		{
			container.createUser("Gabriele", "123");
			container.remove("Gabriele", "123", d1);
			
			printData("Gabriele", "123", container);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		System.out.println("-------------------------------------\n");
	}

	public static void test6()
	{
		System.out.println("----------------TEST6----------------");
		
		HashTableSecureDataContainer<Integer> container = new HashTableSecureDataContainer<Integer>();
		try
		{
			container.createUser("Gabriele", "123");
			container.createUser("Gabriele", "456");
			
			printData("Gabriele", "123", container);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		System.out.println("-------------------------------------\n");
	}
}