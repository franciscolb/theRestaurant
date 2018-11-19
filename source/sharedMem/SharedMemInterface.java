package sharedMem;

import comInfo.Message;
import comInfo.MessageException;

public class SharedMemInterface {

	/**
	 * Instance of the class Bar.
	 * @serialField bar
	 */
	private SharedMemory sharedMem;
	
	/**
	 * The constructor for class BarInterface. In constructor, the barInterface is connected to the bar.
	 * @param bar class bar 
	 */
	public SharedMemInterface( SharedMemory  sharedMem) {
		this.sharedMem =  sharedMem;
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
	      
	      switch (inMessage.getType ()) {
	      
	      case Message.CHEFUPDATE : sharedMem.setChefState(inMessage.getState());
	      							outMessage = new Message(Message.ACK);
	      							break;
	      case Message.WAITERUPDATE : sharedMem.setWaiterState(inMessage.getState());
									  outMessage = new Message(Message.ACK);
									  break;
	      case Message.STUDENTUPDATE : sharedMem.setStudentState(inMessage.getState(),inMessage.getStudentId());
									outMessage = new Message(Message.ACK);
									break;
	      case Message.SHUT: SharedMemServer.waitConnection=false;
		   					 (((ClientProxy) (Thread.currentThread())).getScon()).setTimeout(10);
		   					 outMessage = new Message(Message.ACK);
		   					 break;			
	      }

	     return (outMessage);
	}
}
