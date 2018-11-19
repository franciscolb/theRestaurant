package table;

import java.net.SocketTimeoutException;

import genclass.GenericIO;

/**
 * This class defines a Server, in this case the bar, using the client-server model that replicates the server with static
 * threads launching. The communication used is based on passing messages by sockets with TCP protocol.
 */
public class TableServer {

	/**
	 * Number of the listening port for the service.
	 * @serialField portNumb
	 */
	private static int portNumb;
	
	/**
	 * This variable is used to maintain the server listening.
	 * @serialField waitConnection
	 */
	public static boolean waitConnection;

	/**
	 * Main program 
	 */
	public static void main(String[] args) {
		TableServer.portNumb = Integer.parseInt(args[0]);
		Table table;                                    
		TableInterface tableInter;                     
		ServerCom scon, sconi;                              
		ClientProxy cliProxy;                              
		final int nrStudents = Integer.parseInt(args[1]);

		// Starting service 

		scon = new ServerCom (portNumb);                    
		scon.start ();                                      
		table = new Table (nrStudents);                    
		tableInter = new TableInterface (table);        
		GenericIO.writelnString ("O serviço foi estabelecido!");
		GenericIO.writelnString ("O servidor esta em escuta.");

		// Processing requests

		waitConnection = true;
		while (waitConnection)
			try
		{ sconi = scon.accept ();                      
		cliProxy = new ClientProxy (sconi, tableInter);  
		cliProxy.start ();
		}
		catch (SocketTimeoutException e)
		{
		}
		scon.end ();                                         
		GenericIO.writelnString ("O servidor foi desactivado.");

	}
}
