package chef;
/**
 * States of the Chef. It allows the characterization of the interaction.
 */
public enum ChefStates {
	/**
	 * Blocking state (initial state). The chef is waken up by the operation handTheNoteToTheChef of the waiter.
	 */
	WAITING_FOR_AN_ORDER,
	
	/**
	 * Transition state.
	 */
	PREPARING_THE_COURSE,
	
	/**
	 * Transition state.
	 */
	DISHING_THE_PORTIONS,
	
	/**
	 * Blocking state. The chef is waken up by the operation collectPortion of the waiter.
	 */
	DELIVERING_THE_PORTIONS,
	
	/**
	 * Final state.
	 */
	CLOSING_SERVICE
}
