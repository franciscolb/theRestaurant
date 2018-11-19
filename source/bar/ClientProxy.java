package bar;

import genclass.GenericIO;
import comInfo.Message;
import comInfo.MessageException;

public class ClientProxy extends Thread
{
	/**
	 *  Threads counter
	 *
	 *    @serialField nProxy
	 */

	private static int nProxy = 0;

	/**
	 *  Communication channel
	 *
	 *    @serialField sconi
	 */

	private ServerCom sconi;

	/**
	 *  Bar Interface
	 *
	 *    @serialField barInter
	 */

	private BarInterface barInter;

	/**
	 *	The barInterface is connected to the bar.
	 *
	 *  @param sconi communication channel
	 *  @param barInter bar interface
	 */

	public ClientProxy (ServerCom sconi, BarInterface barInter)
	{
		super ("Proxy_" + ClientProxy.getProxyId ());

		this.sconi = sconi;
		this.barInter = barInter;
	}

	/**
	 * Life cycle of the server thread, in this case, Bar. 
	 */

	@Override
	public void run ()
	{
		Message inMessage = null,
				outMessage = null;
		String inString,                                               // string XML de entrada
        	   outString;                                              // string XML de saída

		inString = (String) sconi.readObject ();                       // ler pedido do cliente em XML
	    inMessage = new Message (inString);                            // transformá-lo para o formato de mensagem
		try { 
			outMessage = barInter.processAndReply (inMessage);
		}
		catch (MessageException e){ 
			GenericIO.writelnString ("Thread " + getName () + ": " + e.getMessage () + "!");
			GenericIO.writelnString (e.getMessageVal ().toString ());
			System.exit (1);
		}
		 outString = outMessage.toXMLString ();                         // converter resposta para XML
	     sconi.writeObject (outString);                                 // enviar resposta ao cliente
		sconi.close ();
	}

	/**
	 *  Instance identifier generator
	 *
	 *    @return instance id
	 */

	private static int getProxyId ()
	{
		Class<?> cl = null;
		
		int proxyId;

		try { 
			cl = Class.forName ("bar.ClientProxy");
		}
		catch (ClassNotFoundException e) { 
			GenericIO.writelnString ("O tipo de dados ClientProxy não foi encontrado!");
			e.printStackTrace ();
			System.exit (1);
		}

		synchronized (cl) { 
			proxyId = nProxy;
			nProxy += 1;
		}

		return proxyId;
	}

	/**
	 *  Getter for the communication channel.
	 *
	 *    @return communication channel
	 */

	public ServerCom getScon ()
	{
		return sconi;
	}
}
