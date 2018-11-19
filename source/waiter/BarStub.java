package waiter;

import comInfo.Message;
import genclass.GenericIO;
/**
 * This class define a stub to the Bar using the model client-server with server replication, 
 * launching static threads.
 *
 */
public class BarStub {
		
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
	 *  Instantiation of the the stub to the Bar- 
	 *
	 *    @param hostName server name where the kitchen server is hosted
	 *    @param port server listening port number
	 */

	public BarStub (String hostName, int port)
	{
		serverHostName = hostName;
		serverPortNumb = port;
	}

	/**
	 * Chef sends a message to Bar to call the waiter when food is ready
	 */
	public void alertWaiter ()
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
		outMessage = new Message (Message.cALERTWAITER);     
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
	 * Method used by Waiter to allow him to know what he should do
	 * @return WaiterStates
	 */
	public WaiterStates lookAround ()
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
		outMessage = new Message (Message.wLOOKAROUND);     
		outString = outMessage.toXMLString ();                         // converte resposta para XML
	    con.writeObject (outString);                                   // envia resposta ao servidor
	    inString = (String) con.readObject ();                         // ler resposta do servidor em XML
	    inMessage = new Message (inString);
		if ((inMessage.getType () != Message.sENTERRESTAURANT) && (inMessage.getType() != Message.sDESCRIBEORDER) && (inMessage.getType() != Message.cALERTWAITER) && (inMessage.getType() != Message.sASKBILL))
		{ GenericIO.writelnString ("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
		GenericIO.writelnString (inMessage.toString ());
		System.exit (1);
		}
		int in = inMessage.getType();
		con.close ();
		if(in == Message.cALERTWAITER) {
			return WaiterStates.WAITING_FOR_PORTION;
		} else if (in == Message.sENTERRESTAURANT) {
			return WaiterStates.PRESENTING_THE_MENU;
		} else if (in == Message.sDESCRIBEORDER) {
			return WaiterStates.TAKING_THE_ORDER;
		} else if (in == Message.sASKBILL) {
			return WaiterStates.PROCESSING_THE_BILL;
		} else return WaiterStates.APPRAISING_SITUATION;
	}
	
	/**
	 * Method used by Waiter to notify Students that he's waiting the payment
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
		outMessage = new Message (Message.wWAITINGFORPAYMENT);     
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
	 * Student notifies the Waiter that he's arrived
	 */
	public void enterRestaurant ()
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
		outMessage = new Message (Message.sENTERRESTAURANT);     
		outString = outMessage.toXMLString ();                         // converte resposta para XML
	    con.writeObject (outString);                                   // envia resposta ao servidor
	    inString = (String) con.readObject ();                         // ler resposta do servidor em XML
	    inMessage = new Message (inString);
		if ((inMessage.getType() != Message.ACK))
		{ GenericIO.writelnString ("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
		GenericIO.writelnString (inMessage.toString ());
		System.exit (1);
		}
		con.close ();
		
	}
	
	/**
	 * First Student notifies Waiter about the group choices 
	 */
	public void describeOrder ()
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
		outMessage = new Message (Message.sDESCRIBEORDER);    	
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
	 * Last Student notifies Waiter so that he can pay the meal
	 */
	public void askBill ()
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
		outMessage = new Message (Message.sASKBILL); 
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
	 * Last Student notifies Waiter so that the payment is done
	 */
	public void paymentDone ()
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
		outMessage = new Message (Message.sPAYMENTDONE); 
		con.writeObject (outMessage);
		inMessage = (Message) con.readObject ();outString = outMessage.toXMLString ();                         // converte resposta para XML
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
	 * Method used to ShutDown Bar Server
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
