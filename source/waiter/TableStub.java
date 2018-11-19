package waiter;

import comInfo.Message;
import genclass.GenericIO;
import waiter.ClientCom;

/**
 * This class define a stub to the Table using the model client-server with server replication, 
 * launching static threads.
 *
 */
public class TableStub {
	/**
	 *  server name where the Bar server is hosted.
	 *    @serialField serverHostName
	 */

	private String serverHostName = null;

	/**
	 *  Server listening port number
	 *    @serialField serverPortNumb
	 */

	private int serverPortNumb;

	/**
	 *  Instantiation of the the stub to the Table- 
	 *
	 *    @param hostName server name where the kitchen server is hosted
	 *    @param port server listening port number
	 */
	
	public TableStub (String hostName, int port, int nrStudents)
	{
		serverHostName = hostName;
		serverPortNumb = port;
	}
	
	/**
	 * Waiter salutes the client when he arrives 
	 */
	public void saluteClient() {
		ClientCom con = new ClientCom (serverHostName, serverPortNumb);
		Message inMessage, outMessage;
	    String inString, outString;


		while (!con.open ())                                     // aguarda ligação
		{ try
		{ Thread.currentThread ().sleep ((long) (10));
		}
		catch (InterruptedException e) {}
		}
		outMessage = new Message (Message.wSALUTE);
		outString = outMessage.toXMLString ();                         // converte resposta para XML
	    con.writeObject (outString);                                   // envia resposta ao servidor
	    inString = (String) con.readObject ();                         // ler resposta do servidor em XML
	    inMessage = new Message (inString);   
		con.close ();
	}
	
	/**
	 * Waiter notifies students that the food is on the table
	 */
	public void foodOnTable() {
		ClientCom con = new ClientCom (serverHostName, serverPortNumb);
		Message inMessage, outMessage;
	    String inString, outString;


		while (!con.open ())                                     // aguarda ligação
		{ try
		{ Thread.currentThread ().sleep ((long) (10));
		}
		catch (InterruptedException e) {}
		}
		outMessage = new Message (Message.wFOODONTABLE);   
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
	 * Method used by Waiter to notify the Last Student that he had already receivec the payment
	 */
	public void receivePayment() {
		ClientCom con = new ClientCom (serverHostName, serverPortNumb);
		Message inMessage, outMessage;
	    String inString, outString;

		while (!con.open ())                                     // aguarda ligação
		{ try
		{ Thread.currentThread ().sleep ((long) (10));
		}
		catch (InterruptedException e) {}
		}
		outMessage = new Message (Message.wRECEIVEPAYMENT);     
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
	 * Last Student notifies First Student that he can ask the food
	 * @param id
	 */
	public void lastStudent(int id) {
		ClientCom con = new ClientCom (serverHostName, serverPortNumb);
		Message inMessage, outMessage;
	    String inString, outString;

		while (!con.open ())                                     
		{ try
		{ Thread.currentThread ().sleep ((long) (10));
		}
		catch (InterruptedException e) {}
		}
		outMessage = new Message (Message.sLASTSTUDENT);     
		outString = outMessage.toXMLString ();                         // converte resposta para XML
	    con.writeObject (outString);                                   // envia resposta ao servidor
	    inString = (String) con.readObject ();                         // ler resposta do servidor em XML
	    inMessage = new Message (inString);
		con.close ();
	}
	
	/**
	 * Method used by First Student to let him organize the order
	 */
	public void firstStudent() {
		ClientCom con = new ClientCom (serverHostName, serverPortNumb);
		Message inMessage, outMessage;
	    String inString, outString;

		while (!con.open ())                                     // aguarda ligação
		{ try
		{ Thread.currentThread ().sleep ((long) (10));
		}
		catch (InterruptedException e) {}
		}
		outMessage = new Message (Message.sFIRSTSTUDENT);     
		outString = outMessage.toXMLString ();                         // converte resposta para XML
	    con.writeObject (outString);                                   // envia resposta ao servidor
	    inString = (String) con.readObject ();                         // ler resposta do servidor em XML
	    inMessage = new Message (inString);
		con.close ();
	}
	
	/**
	 * Students chatting
	 */
	public void chatting ()
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
		outMessage = new Message (Message.sCHATING);     
		outString = outMessage.toXMLString ();                         // converte resposta para XML
	    con.writeObject (outString);                                   // envia resposta ao servidor
	    inString = (String) con.readObject ();                         // ler resposta do servidor em XML
	    inMessage = new Message (inString);
		con.close ();
	}
	
	/**
	 * Method used to alert that all the students had finished the course
	 */
	public void allFinished ()
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
		outMessage = new Message (Message.sALLFINISHED); 
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
	 * Method used to alert that all the students had finished the meal
	 */
	public void mealCompleted ()
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
		outMessage = new Message (Message.sMEALCOMPLETED); 
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
	 * Method used to put the students waiting by the others
	 */
	public void waitOthers ()
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
		outMessage = new Message (Message.sWAITOTHERS); 
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
	 * Method used to put the students waiting by the payment done by Last Student  
	 */
	public void waitPayment ()
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
		outMessage = new Message (Message.sWAITFORPAYMENT); 
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
	 * Method used by Last Student to call the Waiter so that he can request the bill 
	 */
	public void requestBill ()
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
		outMessage = new Message (Message.sREQUESTBILL); 
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
	 * Method used by Last Student to alert all students that he had already paid the meal
	 */
	public void goHome ()
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
		outMessage = new Message (Message.sGOHOME); 
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
	 * Method used to ShutDown Table Server
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
