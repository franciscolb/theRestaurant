package waiter;

/**
 * This class represents the entity Chef and implements it's life cycle according to the statement.
 */

public class Waiter extends Thread {
	
	/**
	 * {@link WaiterStates} that will be used to save the current state of the {@link Waiter}
	 *    @serialField chefState
	 */
	private WaiterStates waiterState;
	
	/**
	 * Variable that will save the number of courses
	 *    @serialField nrCourses
	 */
	private int nrStudents;
	
	/**
	 * Stub that is representing the {@link Bar}
	 */
	private BarStub barStub;
	
	/**
	 * Stub that is representing the {@link kitchen}
	 */
	private KitchenStub kitStub;
	
	/**
	 * Stub that is representing the {@link Bar}
	 */
	private TableStub tableStub;
	
	
	/**
	 * Stub that is representing the {@link sharedMem}
	 */
	private SharedMemStub sharedMemStub;

	/**
	 * This method is the constructor of this class. It receives some parameters that allow this class to have access
	 * to different classes and so shared information between them.
	 * @param kitStub represents the space that this entity could manipulate and use.
	 * @param barStub represents the space that this entity could manipulate and use.
	 * @param tableStub represents the space that this entity could manipulate and use.
	 * @param log represents the logger that will be used to describe the evolution of the internal
	 *  state of the problem.
	 * @param nrStudents represents the number of students 
	 */
	public Waiter (KitchenStub kitStub, BarStub barStub, TableStub tableStub, int nrStudents, SharedMemStub sharedMemStub) {
		this.nrStudents = nrStudents;
		this.kitStub = kitStub;
		this.barStub = barStub;
		this.tableStub = tableStub;
		this.sharedMemStub = sharedMemStub;
		//sharedMem.setInitialWaiterState(WaiterStates.APPRAISING_SITUATION);
	}

	/**
	 * This method represents the life cycle of the entity Student.
	 */
	@Override
	public void run() {
		boolean continueWork = true;
		WaiterStates task;
		
		while(continueWork) {
			//Update status to APPRAISING_SITUATION and set it on log:
			waiterState = WaiterStates.APPRAISING_SITUATION;
			sharedMemStub.setWaiterState(waiterState.toString());
			
			task = barStub.lookAround();
			switch(task) {
				case PRESENTING_THE_MENU : 
					//Update status to PRESENTING_THE_MENU and set it on log:
					this.waiterState = WaiterStates.PRESENTING_THE_MENU;
					sharedMemStub.setWaiterState(waiterState.toString());
					tableStub.saluteClient();
					//bar.returnBar();
					break;
				
				case TAKING_THE_ORDER : 
					//Update status to TAKING_THE_ORDER and set it on log:
					this.waiterState=WaiterStates.TAKING_THE_ORDER;
					sharedMemStub.setWaiterState(waiterState.toString());
					//Update status to PLACING_THE_ORDER and set it on log:
					this.waiterState = WaiterStates.PLACING_THE_ORDER;
					sharedMemStub.setWaiterState(waiterState.toString());
					
					kitStub.handNoteToTheChef();
					break;
				
				case WAITING_FOR_PORTION:
					for (int student = 0; student<nrStudents; student++) {
						kitStub.collectPortion();
						
						//Update status to WAITING_FOR_PORTION and set it on log:
						this.waiterState=WaiterStates.WAITING_FOR_PORTION;
						sharedMemStub.setWaiterState(waiterState.toString());
					}
					
					for(int i = 0 ; i < nrStudents ; i++)
						tableStub.foodOnTable();
					
					
					break;
				
				case PROCESSING_THE_BILL:
					//Update status to PROCESSING_THE_BILL and set it on log:
					this.waiterState=WaiterStates.PROCESSING_THE_BILL;
					sharedMemStub.setWaiterState(waiterState.toString());
					
					//Update status to RECEIVING_PAYMENT and set it on log:
					this.waiterState=WaiterStates.RECEIVING_PAYMENT;
					sharedMemStub.setWaiterState(waiterState.toString());
					
					tableStub.receivePayment();
					barStub.waitPayment();
					
					//Update status to APPRAISING_SITUATION and set it on log:
					this.waiterState=WaiterStates.APPRAISING_SITUATION;
					sharedMemStub.setWaiterState(waiterState.toString());
					continueWork=false;
					
					
					break;
					
				default:
					//Update status to RECEIVING_PAYMENT and set it on log:
					this.waiterState=WaiterStates.APPRAISING_SITUATION;
					sharedMemStub.setWaiterState(waiterState.toString());
					
					break;
			}
		}
	}
}
