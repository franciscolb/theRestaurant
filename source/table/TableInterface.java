package table;

import genclass.GenericIO;

import java.net.SocketTimeoutException;

import comInfo.Message;
import comInfo.MessageException;

/**
 * This class defines an interface to the Bar, using the client-server model that replicates the server with static
 * threads launching.
 */

public class TableInterface {

	/**
	 * Instance of the class Table.
	 * @serialField table
	 */
	private Table table;
	
	/**
	 * The constructor for class TableInterface. In constructor, the barInterface is connected to the table.
	 * @param table class table 
	 */
	public TableInterface(Table table) {
		this.table = table;
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
	        case Message.sSITTING:  
	        						int msg = table.sittingStudent(inMessage.getStudentId());
	        						outMessage = new Message (msg);
	                               break;
	        case Message.wSALUTE : table.saluteClient();
	        						outMessage = new Message (Message.ACK);
	        						break;
	        						
	        case Message.sLASTSTUDENT : table.lastStudent();
	        							outMessage = new Message (Message.ACK);
	        							break;
	        case Message.sFIRSTSTUDENT : table.firstStudent();
	        							 outMessage = new Message (Message.ACK);
	        							 break;
	        case Message.sCHATING : table.chatting();
	        						outMessage = new Message (Message.ACK);
	        						break;
	        case Message.wFOODONTABLE: table.foodOnTable();
		        					   outMessage = new Message (Message.ACK);
	        						   break;
	        case Message.sFINISHEATING: table.finishEating();
			        					outMessage = new Message (Message.ACK);
			        					break;
	        case Message.FINISHCOURSE: int answer = table.getAlreadyFinished();
	        						   outMessage = new Message (Message.FINISHEATTRUE,answer);
	        						   break;
	        case Message.sMEALCOMPLETED : table.mealCompleted();
	        							  outMessage = new Message (Message.ACK);
	        							  break;
	        case Message.sALLFINISHED : table.allFinished();
										outMessage = new Message (Message.ACK);
										break;
	        case Message.sWAITOTHERS : table.waitOthers();
	        						   outMessage = new Message (Message.ACK);
	        						   break;
	        case Message.sWAITFORPAYMENT : table.waitPayment();
	        							   outMessage = new Message (Message.ACK);
	        							   break;
	        case Message.sREQUESTBILL : table.requestBill();
			   							outMessage = new Message (Message.ACK);
			   							break;

	        case Message.wRECEIVEPAYMENT : table.recievePayment();
	        							   outMessage = new Message (Message.ACK);
				   						   break;
			   							
	        case Message.sGOHOME : table.goHome();
	        					   outMessage = new Message (Message.ACK);
	        					   break;
	        case Message.SHUT: TableServer.waitConnection=false;
			   (((ClientProxy) (Thread.currentThread())).getScon()).setTimeout(10);
			   outMessage = new Message(Message.ACK);
			   break;				
	      }

	     return (outMessage);
	}

}
