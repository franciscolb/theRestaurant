 package student;

import comInfo.Message;
import genclass.GenericIO;
import student.ClientCom;

public class SharedMemStub {

	/**
	 *  server name where the SharedMem server is hosted.
	 *    @serialField serverHostName
	 */

	private String serverHostName = null;

	/**
	 *  Server listening port number
	 *    @serialField serverPortNumb
	 */

	private int serverPortNumb;

	/**
	 *  Instantiation of the the stub to the SharedMem- 
	 *
	 *    @param hostName server name where the SharedMem server is hosted
	 *    @param port server listening port number
	 */

	public SharedMemStub (String hostName, int port)
	{
		serverHostName = hostName;
		serverPortNumb = port;
	}

	
	/**
	 * Update Chef State
	 */
	public void setChefState (String state)
	{
		ClientCom con = new ClientCom (serverHostName, serverPortNumb);
		Message inMessage, outMessage;
	    String inString, outString;

		while (!con.open ()) { 
			try { 
				Thread.currentThread ().sleep ((long) (10));
			}
			catch (InterruptedException e) {}
		}
		outMessage = new Message (Message.CHEFUPDATE,state); 
		outString = outMessage.toXMLString ();                         // converte resposta para XML
	    con.writeObject (outString);                                   // envia resposta ao servidor
	    inString = (String) con.readObject ();                         // ler resposta do servidor em XML
	    inMessage = new Message (inString); 
		if ((inMessage.getType () != Message.ACK)) { 
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		con.close ();

	}

	/**
	 * Update Student State
	 */
	public void setStudentState (String state, int id)
	{
		ClientCom con = new ClientCom (serverHostName, serverPortNumb);
		Message inMessage, outMessage;
	    String inString, outString;

		while (!con.open ()) { 
			try { 
				Thread.currentThread ().sleep ((long) (10));
			}
			catch (InterruptedException e) {}
		}
		outMessage = new Message (Message.STUDENTUPDATE,id,state); 
		outString = outMessage.toXMLString ();                         // converte resposta para XML
	    con.writeObject (outString);                                   // envia resposta ao servidor
	    inString = (String) con.readObject ();                         // ler resposta do servidor em XML
	    inMessage = new Message (inString); 
		if ((inMessage.getType () != Message.ACK)) { 
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		con.close ();
	}

	/**
	 * Update Chef State
	 */
	public void setWaiterState (String state)
	{
		ClientCom con = new ClientCom (serverHostName, serverPortNumb);
		Message inMessage, outMessage;
	    String inString, outString;


		while (!con.open ()) { 
			try { 
				Thread.currentThread ().sleep ((long) (10));
			}
			catch (InterruptedException e) {}
		}
		outMessage = new Message (Message.WAITERUPDATE,state); 
		outString = outMessage.toXMLString ();                         // converte resposta para XML
	    con.writeObject (outString);                                   // envia resposta ao servidor
	    inString = (String) con.readObject ();                         // ler resposta do servidor em XML
	    inMessage = new Message (inString); 
		if ((inMessage.getType () != Message.ACK)) { 
			GenericIO.writelnString ("Thread " + Thread.currentThread ().getName ()+ ": Tipo inválido!");
			GenericIO.writelnString (inMessage.toString ());
			System.exit (1);
		}
		con.close ();
	}

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
