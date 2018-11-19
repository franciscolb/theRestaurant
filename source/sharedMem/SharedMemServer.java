package sharedMem;

import java.net.SocketTimeoutException;

import genclass.GenericIO;

public class SharedMemServer {
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
	 * Main of the SharedMemServer
	 * @param args
	 */
	public static void main(String[] args) {
		SharedMemServer.portNumb = Integer.parseInt(args[0]);
		SharedMemory sharedMem;
		SharedMemInterface sharedMemInter;
		ServerCom scon, sconi;
		ClientProxy cliProxy;
		Logger log;
		int nrStudents = Integer.parseInt(args[1]);
		String filename = "log.txt";

		// Starting service 

		scon = new ServerCom (portNumb);
		scon.start ();
		log = new Logger(filename, nrStudents);
		sharedMem = new SharedMemory (log, nrStudents);
		
		sharedMemInter = new SharedMemInterface (sharedMem);
		GenericIO.writelnString ("O serviço foi estabelecido!");
		GenericIO.writelnString ("O servidor esta em escuta.");

		// Processing requests

		waitConnection = true;
		while (waitConnection)
			try { 
				sconi = scon.accept ();
				cliProxy = new ClientProxy (sconi, sharedMemInter);
				cliProxy.start ();
			}
		catch (SocketTimeoutException e) {
		}
		scon.end ();
		GenericIO.writelnString ("O servidor foi desactivado.");


	}

}
