package bar;

import comInfo.Message;
import comInfo.MessageException;

/**
 * This class defines an interface to the Bar, using the client-server model that replicates the server with static
 * threads launching.
 */

public class BarInterface {

	/**
	 * Instance of the class Bar.
	 * @serialField bar
	 */
	private Bar bar;
	
	/**
	 * The constructor for class BarInterface. In constructor, the barInterface is connected to the bar.
	 * @param bar class bar 
	 */
	public BarInterface(Bar bar) {
		this.bar = bar;
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

	      {	case Message.wLOOKAROUND : int msg = Message.wLOOKAROUND;
	    	  							switch(bar.lookAround()) {
	      									case PRESENTING_THE_MENU: 
	      															  msg = Message.sENTERRESTAURANT; 
	      															  break;
	      									case TAKING_THE_ORDER : msg = Message.sDESCRIBEORDER; 
	      															break;
	      									case WAITING_FOR_PORTION: msg = Message.cALERTWAITER;
	      															  break;
	      									case PROCESSING_THE_BILL : msg = Message.sASKBILL;
	      															   break;
	      								}
	    	  							outMessage = new Message(msg);
	    	  							break;
	      
	        case Message.sENTERRESTAURANT: bar.enterRestaurant();
	        							   outMessage = new Message (Message.ACK);         
	        							   break;
	                               
	        case Message.sDESCRIBEORDER : bar.describeOrder(); 
	        							  outMessage = new Message(Message.wHANDNOTE);
	        							  break;
	        case Message.cALERTWAITER:	bar.alertWaiter();
	        							outMessage = new Message(Message.ACK);
	        							break;
	        case Message.sALERTWAITER:	bar.alertWaiter();
	        							outMessage = new Message(Message.ACK);
	        							break;
	        case Message.sASKBILL: bar.askBill();
	        					   outMessage = new Message (Message.ACK);
	        					   break;
	        case Message.sPAYMENTDONE : bar.paymentDone();
	        							outMessage = new Message (Message.ACK);
	        							break;
	        case Message.wWAITINGFORPAYMENT : bar.waitPayment();
	        							   outMessage = new Message (Message.ACK);
	        							   break;
	        case Message.SHUT: BarServer.waitConnection=false;
			   				   (((ClientProxy) (Thread.currentThread())).getScon()).setTimeout(10);
			   				   outMessage = new Message(Message.ACK);
			   				   break;
	      }

	     return (outMessage);
	}
}
