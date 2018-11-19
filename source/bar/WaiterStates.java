package bar;
/**
 * States of the Student. It allows the characterization of the
 * interaction.
 */
public enum WaiterStates {
	/**
	 * Blocking state with transition (initial / final state)
	 * the waiter is waken up by one of the following operations: 
	 * alertThe-Student of the chef, enter and exit of all the 
	 * students, callTheWaiter of the first student to sit at 
	 * the table, signalTheWaiter of the last student to finish 
	 * a course and shouldHaveArrivedEarlier of the last student 
	 * to sit at the table; transition occurs when the last student 
	 * has left the restaurant.
	 */
	APPRAISING_SITUATION,
	
	/**
	 * Blocking state. The waiter is waken up by the operation 
	 * readTheMenu of the student.
	 */
	PRESENTING_THE_MENU,
	
	/**
	 * Blocking state. The waiter is waken up by the operation 
	 * describeTheOrder of the student.
	 */
	TAKING_THE_ORDER,
	
	/**
	 * Blocking state. The waiter is waken up by the operation 
	 * startPreparation of the chef.
	 */
	PLACING_THE_ORDER,
	
	/**
	 * Blocking state. The waiter is waken up by the operation 
	 * haveAllPortionsBeenDelivered of the chef.
	 */
	WAITING_FOR_PORTION,
	
	/**
	 * Transition state.
	 */
	PROCESSING_THE_BILL,
	
	/**
	 * Blocking state.The waiter is waken up by the operation 
	 * honorTheBill of the student.
	 */
	RECEIVING_PAYMENT
	
}
