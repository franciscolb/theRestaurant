package student;
/**
 * States used to characterize the student by order of arrival to the restaurant. 
 * 
 */
public enum StudentOrder {
	/**
	 * State used to identify the first student to arrive to restaurant.
	 */
	FIRST, 
	
	/**
	 * State used to identify the last student to arrive to restaurant.
	 */
	LAST,
	
	/**
	 * State used to identify a student that it is neither the first nor the last to arrive to restaurant.
	 */
	OTHER
}
