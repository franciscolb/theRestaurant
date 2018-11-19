package sharedMem;
/**
 * States of the Students. It allows the characterization of the
 * interaction.
 */
public enum StudentStates {
	/**
	 * Transition state with random time (initial state).
	 */
	GOING_TO_THE_RESTAURANT,
	
	/**
	 * Blocking state. The student is waken up by the operation 
	 * saluteTheClient of the waiter
	 */
	TAKING_A_SEAT_AT_THE_TABLE,
	
	/**
	 * Transition state.
	 */
	SELECTING_THE_COURSES,
	
	/**
	* Blocking state. The student is waken up by the operation 
	* informCompanion of another student and, when all students 
	* are already at the table, by the operation getThePad of 
	* the waiter
	*/
	ORGANIZING_THE_ORDER,
	
	/**
	 * Blocking state with transition the student blocks while 
	 * waiting for a course to be served and when he/she has 
	 * finished eating it; transition occurs when the last course 
	 * has been served and eaten
	 */
	CHATTING_WITH_COMPANIONS,
	
	/**
	 * Transition state.
	 */
	ENJOYING_THE_MEAL,
	
	/**
	 * Blocking state. The student is waken up by the operation
	 * presentTheBill of the waiter
	 */
	PAYING_THE_MEAL,
	
	/**
	 * Final state.
	 */
	GOING_HOME
}
