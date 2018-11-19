package bar;
/**
 * This class represents the place where the {@link Waiter} is waiting by orders given by the Chef or the Students.
 * So that it's the place where are instantiated the semaphores responsible by the flow of Waiter.
 * It's important to say that the pauses on flow of this entity are, actually, manipulated by the entities
 * in order to portray the exchange of messages and requests between them. 
 * This class has one private variable that are used along the methods defined in class.
 * Beside that, this class contains three objects of the class {@link Semaphore}:
 * 'looking' ({@link Semaphore} that is used to control when the Waiter is waiting by a order).
 * 'payment' ({@link Semaphore} that controls the payment receiving)
 * 'mutex' ({@link Semaphore} that controls the access to this entity)
 */

public class Bar {
	private Semaphore looking = new Semaphore();
	private Semaphore mutex = new Semaphore();
	private Semaphore payment = new Semaphore();
	private String task;
	
	/**
	 * The constructor for class Bar. In constructor, the control semaphore (mutex) is up to allow access to other entities. 
	 */
	public Bar() {
		mutex.up();
	}
	
	/**
	 * This method is used, directly, by entity Waiter. 
	 * It returns an {@link WaiterStates} that will be used by Waiter in order to know which methods
	 * or operations he should do. 
	 * @return returns a state of Waiter which is selected by the value of the variable 'task'.
	 */
	public WaiterStates lookAround() {
		 
		looking.down();
		switch(task) {
			case "studentArrived": return WaiterStates.PRESENTING_THE_MENU;
			case "getPad" : return WaiterStates.TAKING_THE_ORDER;
			case "foodReady" : return WaiterStates.WAITING_FOR_PORTION; 
			case "mealsServed": return WaiterStates.PROCESSING_THE_BILL;
		}
		return WaiterStates.APPRAISING_SITUATION;
	}
	
	
	/** 
	 * This method is involved on the process of entering on the restaurant, by the students.
	 * When a student arrive the restaurant he signals the Waiter with this method. 
	 * This method changes the task value to "studentArrived" in order to Waiter know
	 * what to do when leaving the wait condition on the 'looking.up()' operation.
	 * Also the control semaphore 'mutex' is used to protect the change of task variable.
	 */
	public void enterRestaurant() {
		mutex.down();
		task="studentArrived";
		looking.up();
		mutex.up();
	}
	
	/**
	 * This method is called, indirectly, by the first Student to transmit the order to Waiter. 
	 * After the student describe the order the waiter stop waiting and go to {@link Kitchen} to deliver the order.
	 * This method changes the task value to "getPad" in order to Waiter know what to do when leaving the wait 
	 * condition on the 'looking.up()' operation.
	 * Also the control semaphore 'mutex' is used to protect the change of task variable.
	 */
	public void describeOrder() {
		mutex.down();
		task="getPad";
		looking.up();
		mutex.up();
	}
	
	/**
	 * This method belongs to bar and it's used by entity Chef. 
	 * When this method is called the task variable changes to "foodReady" in
	 * order to Waiter know that he have to go to Kitchen pick up food for students.
	 * Also the control semaphore 'mutex' is used to protect the change of task variable.
	 */
	public void alertWaiter() {
		mutex.down();
		task="foodReady";
		looking.up();
		mutex.up();
	}
	
	/**
	 * This method is involved on the process of asking bill.
	 * The variable 'task' take the value of "mealsServed". This value will be used, by Waiter, to know what to do.
	 * In this method the Student wakes up the Waiter (throw the use of one {@link Semaphore}) and ask him about 
	 * the bill.
	 */
	public void askBill() {
		mutex.down();
		task="mealsServed";
		mutex.up();
		looking.up();
	}
	
	/**
	 * This method is used by the {@link Waiter} to process the bill when he is on state "RECEIVING_PAYMENT".
	 * The idea behind this method is to block the Waiter while he is waiting by the operation "paymentDone"
	 * of the {@link Student}.
	 */
	public void waitPayment() {
		payment.down();
	}
	
	/** 
	 * This method is used by the {@link Student} and is involved on the process of paying the bill.
	 * This method is used by last student to finish the payment.
	 */
	public void paymentDone() {
		payment.up();
	}
}
