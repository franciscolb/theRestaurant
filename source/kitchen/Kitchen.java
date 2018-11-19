package kitchen;

/**
 * This class represents the place where the {@link Chef} is waiting by orders and requests given by the Waiter.
 * So that it's the place where are instantiated the semaphores responsible by the flow of Chef.
 * It's important to say that the pauses on flow of this entity are, actually, manipulated by the entities
 * in order to portray the exchange of messages and requests between them.
 * 
 * Beside that, this class contains five objects of the class {@link Semaphore}:
 * 'mutex' ({@link Semaphore} that control the access of critical zone),
 * 'cooking' ({@link Semaphore} that control when the Chef is cooking), 
 * 'transmitingOrder' ({@link Semaphore} that is used by Waiter and Chef to let the Waiter
 * hold until the Chef Start Cooking),
 * 'takingPortion' ({@link Semaphore} that control when the Waiter is taking a portion from the kitchen),
 * 'dishingPortion' ({@link Semaphore} that control when the Chef is dishing the portion) 
 */

public class Kitchen {
	private Semaphore cooking = new Semaphore();
	private Semaphore mutex = new Semaphore();
	private Semaphore transmitingOrder = new Semaphore();
	private Semaphore dishingPortion = new Semaphore();
	private Semaphore takingPortion = new Semaphore();

	
	/**
	 * Contructor of the class Kitchen. In constructor, the control semaphore (mutex) is up to allow access to other entities. 
	 */
	public Kitchen() {
		mutex.up();
	}
	

	/**
	 * This method is used, directly, by {@link Chef}. 
	 * This method set {@link Chef} in a standby state, which will be terminated when the 
	 * {@link Waiter} notify him.
	 */
	public void watchNews() {
		cooking.down();
	}
	
	/**
	 * This method is used, indirectly, by {@link Waiter}.
	 * As the name of the method say in this method the waiter hands the note to the Chef.
	 * In this method the {@link Waiter} puts an end to the wait of the {@link Chef} so that he can be able to start 
	 * cooking.
	 * At the same time the flow of {@link Waiter} is paused, while the {@link Chef} is receiving the order.
	 */
	public void handNoteToTheChef() {
		mutex.down();
		cooking.up();
		mutex.up();
		transmitingOrder.down();
	}
	
	/**
	 * This method is used, directly, by {@link Chef}.
	 * This method put a end on the pause flow of the {@link Waiter}. This means that the chef will start 
	 * cooking and the waiter should return to bar waiting for orders.
	 */
	public void startCooking() { 
		mutex.down(); 
		transmitingOrder.up(); 
		mutex.up(); 
	}
	
	/**
	 * This method is used, indirectly, by {@link Waiter}.
	 * In this method several operations are performed: the wake up of the {@link Chef} so that he can be able 
	 * to dish and deliver the portion and the pause in the stream
	 * of the {@link Waiter} so that he waits for the next portion. 
	 */
	public void collectPortion() {
		mutex.down();
		cooking.up();
		dishingPortion.up();
		mutex.up();
		takingPortion.down();
	}
	
	/**
	 * This method is used, directly, by {@link Chef}.
	 * The idea behind this method is to wake up the {@link Waiter} so that he can be able to collect the portion
	 * and notify the {@link Chef} that he can prepare the next portion.
	 */
	public void portionReady() {
		mutex.down();
		takingPortion.up();
		mutex.up();
		dishingPortion.down();
	}
	
	/**
	 * This method is used, directly, by {@link Chef}.
	 * The idea behind this method is to wait for all students to finish eating before continue the preparation of the
	 * next course.
	 */
	public void continuePreparation() { cooking.down(); }
	
	/**
	 * This method is involved on the process of ending the meal. 
	 * The idea behind this method is to let the Preparation continue or, in others words, ask to {@link Chef} to prepare
	 * the next course.
	 */
	public void finishedEating() { 
		mutex.down(); 
		cooking.up(); 
		mutex.up();          
	}
}
