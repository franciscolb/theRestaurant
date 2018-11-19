package kitchen;

import comInfo.Message;
import comInfo.MessageException;

/**
 * This class defines an interface to the Kitchen, using the client-server model that replicates the server with static
 * threads launching.
 */
public class KitchenInterface {

	/**
	 * Instance of the class Kitchen.
	 * @serialField bar
	 */
	private Kitchen kit;
	
	/**
	 * The constructor for class KitchenInterface. In constructor, the KitchenInterface is connected to the kitchen.
	 * @param kit class Kitchen 
	 */
	public KitchenInterface(Kitchen kit) {
		this.kit = kit;
	}
	
	/**
	 * This method is used to process the messages and execute the corresponding task. 
	 * Also generates a reply message.
	 * @param inMessage the message received
	 * @return the reply message
	 * @throws MessageException if the message received is not valid this method throws a {link @MessageException}
	 */
	public Message processAndReply (Message inMessage) throws MessageException {
		Message outMessage = null;	
		//Processing
	      
	      switch (inMessage.getType ())

	      { 
	        case Message.cWATCHNEWS:  kit.watchNews ();      
	                                  outMessage = new Message (Message.wHANDNOTE);     
	                                  break;
	        case Message.wHANDNOTE : //System.out.println("hand note");
	        						 kit.handNoteToTheChef();
	                                 outMessage = new Message (Message.cSTARTCOOK);        
	        						 break;
	        case Message.cSTARTCOOK : kit.startCooking();
	                                  outMessage = new Message (Message.ACK);         
	        						  break;
	        case Message.wCOLLECTPORTION: kit.collectPortion();
		                                  outMessage = new Message (Message.cSTARTCOOK);
		                                  break;
	        case Message.cPORTIONREADY:	kit.portionReady();
	        							outMessage = new Message (Message.ACK);
	        							break;
	        case Message.cCONTINUEPREPARATION:kit.continuePreparation();
	        								   outMessage = new Message(Message.ACK);
	        								   break;
	        case Message.SHUT: KitchenServer.waitConnection=false;
	        				   (((ClientProxy) (Thread.currentThread())).getScon()).setTimeout(10);
	        				   outMessage = new Message(Message.ACK);
	        				   break;			  
	      }

	     return (outMessage);
	}
}
