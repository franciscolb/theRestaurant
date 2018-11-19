package waiter;
import genclass.*;
import comInfo.*;

/**
 * This class define a stub to the Kitchen using the model client-server with server replication, 
 * launching static threads.
 */

public class KitchenStub {
	/**
	 *  server name where the Kitchen server is hosted.
	 *    @serialField serverHostName
	 */

	private String serverHostName = null;

	/**
	 *  Server listening port number
	 *    @serialField serverPortNumb
	 */

	private int serverPortNumb;

	/**
	 *  Instantiation of the the stub to the kitchen- 
	 *
	 *    @param hostName server name where the kitchen server is hosted
	 *    @param port server listening port number
	 */

	public KitchenStub (String hostName, int port)
	{
		serverHostName = hostName;
		serverPortNumb = port;
	}

	/**
	 * Chef watching news (service solicitation )
	 * 
	 */	
	public void watchNews ()
	{
		ClientCom con = new ClientCom (serverHostName, serverPortNumb);
		Message inMessage, outMessage;
	    String inString, outString;

		while (!con.open ())                                     // aguarda ligação
		{ try
		{ Thread.currentThread ().sleep ((long) (10));
		}
		catch (InterruptedException e) {}
		}
		outMessage = new Message (Message.cWATCHNEWS);
		outString = outMessage.toXMLString ();                         // converte resposta para XML
	    con.writeObject (outString);                                   // envia resposta ao servidor
	    inString = (String) con.readObject ();                         // ler resposta do servidor em XML
	    inMessage = new Message (inString);   
		if ((inMessage.getType () != Message.wHANDNOTE))
		{ GenericIO.writelnString ("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
		GenericIO.writelnString (inMessage.toString ());
		System.exit (1);
		}
		con.close ();

	}
	
	/**
	 * Waiter notifies chef to let him start cooking
	 */
	public void handNoteToTheChef ()
	{
		ClientCom con = new ClientCom (serverHostName, serverPortNumb);
		Message inMessage, outMessage;
	    String inString, outString;


		while (!con.open ())                                     // aguarda ligação
		{ try
		{ Thread.currentThread ().sleep ((long) (10));
		}
		catch (InterruptedException e) {}
		}
		outMessage = new Message (Message.wHANDNOTE);     // o chef vai dormir
		outString = outMessage.toXMLString ();                         // converte resposta para XML
	    con.writeObject (outString);                                   // envia resposta ao servidor
	    inString = (String) con.readObject ();                         // ler resposta do servidor em XML
	    inMessage = new Message (inString);   
		if ((inMessage.getType () != Message.cSTARTCOOK))
		{ GenericIO.writelnString ("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
		GenericIO.writelnString (inMessage.toString ());
		System.exit (1);
		}
		con.close ();

	}
	
	/**
	 *  Waiter notifies Chef that the portion was collected
	 */
	public void collectPortion ()
	{
		ClientCom con = new ClientCom (serverHostName, serverPortNumb);
		Message inMessage, outMessage;
	    String inString, outString;


		while (!con.open ())                                     // aguarda ligação
		{ try
		{ Thread.currentThread ().sleep ((long) (10));
		}
		catch (InterruptedException e) {}
		}
		outMessage = new Message (Message.wCOLLECTPORTION);     // o chef vai dormir
		outString = outMessage.toXMLString ();                         // converte resposta para XML
	    con.writeObject (outString);                                   // envia resposta ao servidor
	    inString = (String) con.readObject ();                         // ler resposta do servidor em XML
	    inMessage = new Message (inString);   
		if ((inMessage.getType () != Message.cSTARTCOOK))	// ?? 
		{ GenericIO.writelnString ("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
		GenericIO.writelnString (inMessage.toString ());
		System.exit (1);
		}
		con.close ();

	
	}
	
	/**
	 * Chef notifies Waiter that has started cooking
	 */
	public void startCooking ()
	{
		ClientCom con = new ClientCom (serverHostName, serverPortNumb);
		Message inMessage, outMessage;
	    String inString, outString;


		while (!con.open ())                                     // aguarda ligação
		{ try
		{ Thread.currentThread ().sleep ((long) (10));
		}
		catch (InterruptedException e) {}
		}
		outMessage = new Message (Message.cSTARTCOOK);     // o chef vai cozinhar
		outString = outMessage.toXMLString ();                         // converte resposta para XML
	    con.writeObject (outString);                                   // envia resposta ao servidor
	    inString = (String) con.readObject ();                         // ler resposta do servidor em XML
	    inMessage = new Message (inString);   
		if ((inMessage.getType () != Message.ACK))
		{ GenericIO.writelnString ("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
		GenericIO.writelnString (inMessage.toString ());
		System.exit (1);
		}
		con.close ();
	}

	/**
	 * Chef notifies Waiter that the portion is ready
	 */
	public void portionReady ()
	{
		ClientCom con = new ClientCom (serverHostName, serverPortNumb);
		Message inMessage, outMessage;
	    String inString, outString;


		while (!con.open ())                                     // aguarda ligação
		{ try
		{ Thread.currentThread ().sleep ((long) (10));
		}
		catch (InterruptedException e) {}
		}
		outMessage = new Message (Message.cPORTIONREADY);     // o chef vai cozinhar
		outString = outMessage.toXMLString ();                         // converte resposta para XML
	    con.writeObject (outString);                                   // envia resposta ao servidor
	    inString = (String) con.readObject ();                         // ler resposta do servidor em XML
	    inMessage = new Message (inString); 
		if ((inMessage.getType () != Message.ACK))
		{ GenericIO.writelnString ("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
		GenericIO.writelnString (inMessage.toString ());
		System.exit (1);
		}
		con.close ();
	}

	/**
	 * Chef notifies that he will continue the preparation
	 */
	public void continuePreparation ()
	{
		ClientCom con = new ClientCom (serverHostName, serverPortNumb);
		Message inMessage, outMessage;
	    String inString, outString;


		while (!con.open ())                                     // aguarda ligação
		{ try
		{ Thread.currentThread ().sleep ((long) (10));
		}
		catch (InterruptedException e) {}
		}
		outMessage = new Message (Message.cCONTINUEPREPARATION);     // o chef vai cozinhar
		outString = outMessage.toXMLString ();                         // converte resposta para XML
	    con.writeObject (outString);                                   // envia resposta ao servidor
	    inString = (String) con.readObject ();                         // ler resposta do servidor em XML
	    inMessage = new Message (inString); 
		if ((inMessage.getType () != Message.ACK))
		{ GenericIO.writelnString ("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
		GenericIO.writelnString (inMessage.toString ());
		System.exit (1);
		}
		con.close ();
	}

	/**
	 * Method used to ShutDown Kitchen Server
	 */
	public void shutdown ()
	{
		ClientCom con = new ClientCom (serverHostName, serverPortNumb);
		Message inMessage, outMessage;
	    String inString, outString;

		while (!con.open ())                                                // aguarda ligação
		{ try
		{ Thread.currentThread ().sleep ((long) (10));
		}
		catch (InterruptedException e) {}
		}
		outMessage = new Message (Message.SHUT);
		outString = outMessage.toXMLString ();                         // converte resposta para XML
	    con.writeObject (outString);                                   // envia resposta ao servidor
	    inString = (String) con.readObject ();                         // ler resposta do servidor em XML
	    inMessage = new Message (inString);   
		if (inMessage.getType () != Message.ACK)
		{ GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Tipo inválido!");
		GenericIO.writelnString (inMessage.toString ());
		System.exit (1);
		}
		con.close ();
	}

}
