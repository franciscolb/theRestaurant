package table;

import comInfo.Message;

/**
 * This class represents the place where the {@link Student} will eat.
 * Because of that it's the place where are instantiated the semaphores responsible by the flow of the {@link Student}.
 * It's important to say that the pauses on flow of this entity are, actually, manipulated by the entities
 * in order to portray the exchange of messages and requests between them.
 * 
 * This class contains seven objects of the class {@link Semaphore}:
 * 'mutex' ({@link Semaphore} that control the access of critical zone),
 * 'makeOrder' ({@link Semaphore} that controls when the order is communicate to {@link Waiter}),
 * 'readMenu' ({@link Semaphore} that controls when and who the read the menu),
 * 'eat' ({@link Semaphore} that controls when the students are eating),
 * 'waitPayment' ({@link Semaphore} that put the Students waiting until the last Student finish the payment),
 * 'requestPayment' ({@link Semaphore} that controls the payment of the last {@link Student}), 
 * 'waitForOthers ({@link Semaphore} that allows all students wait each others)
 * 
 * Also, this class contains four others variables of type int:
 * 'finishedEating' (number of the students that had already finished the course),
 * 'atTable' (number of the students that are on the table) and
 * 'nrStudents' (number of students that will participate on dinner).
 */

public class Table {
	
	
	
	private int atTable;
	private int nrStudents;
	private int finishedEating;
	private Semaphore mutex = new Semaphore();
	private Semaphore makeOrder = new Semaphore();
	private Semaphore readMenu = new Semaphore();
	private Semaphore eat = new Semaphore();
	private Semaphore waitForOthers = new Semaphore();
	private Semaphore waitPayment = new Semaphore();
	private Semaphore requestPayment = new Semaphore();
	private Fifo<Integer> fifo;

	
	/**
	 * Constructor of this class. It allows the creation of new objects of this class.
	 */
	public Table(int nrStudents) {
		mutex.up();
		atTable = 0;
		this.nrStudents = nrStudents;
		finishedEating = 0;
		fifo = new Fifo<>(nrStudents);
	}
	
	/**
	 * This method belongs to a method called "enterRestaurant", declared on {@link SharedMemory},that is 
	 * involved on the process of entering on the restaurant.
	 * The idea behind this method is to determine the ID of the first {@link Student} that arrives on the 
	 * restaurant and increment the number of the students what arrives. 
	 * At same time, students are placed to read the menu by using {@link Semaphore} called 'readMenu'.
	 * @param id of the thread that is running this method. It's important to highlight that each student 
	 * is represented by a thread. 
	 */
	public int sittingStudent(int id) {
		try {
			fifo.push(id);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		mutex.down();
		int order = Message.OTHER;
		if(this.atTable == 0) {
			order = Message.FIRST;
		} else if (this.atTable==nrStudents-1) {
			order = Message.LAST;
		}
		atTable++;
		mutex.up();
		readMenu.down();
		return order;
	}
	
	/**
	 * This method is used, indirectly, by the {@link Waiter}.
	 * It portrays the read of the menu by Students. At the truth, when the Waiter calls this method allow the 
	 * student to continue his flow.
	 */
	public void saluteClient() { 
		try {
			fifo.pop();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		readMenu.up();
	}
	
	/**
	 * This method is used, directly, by the last Student.
	 * The idea is to discover the ID of the last Student and save that ID on the variable 'idLast' so that it can be 
	 * used along the program.
	 * To allow this operation this method receive an parameter with the ID of the thread that is running this 
	 * method.
	 * Beside that operation, this method allow that the first student can make the order once all students 
	 * are already at the table. This is done by using the 'up' of the {@link Semaphore} 'makeOrder'. 
	 * @param id of the thread that is running this method.
	 */
	public void lastStudent() {
		makeOrder.up();
	}
	
	/**
	 * This method is used, directly, by the {@link Student}.
	 * The idea behind this method is to put the first student on hold until all the students arrive the restaurant,
	 * being that the first student could make the order of the food. 
	 */
	public void firstStudent() {
		makeOrder.down();
	}
	
	/** 
	 * This method is used, directly, by the {@link Student}. 
	 * When the meal start all the students start to chat until the food arrive, so the idea behind this method is 
	 * to put the students to chat and this is made throw the use of the {@link Semaphore} 'eat'.
	 */
	public void chatting() {
		eat.down();
	}
	
	/** 
	 * This method is used, indirectly, by the {@link Waiter}. 
	 * When the food is on the table all the students start to chat until the food arrive, so the idea behind 
	 * this method is to put the students to eat and this is made throw the use of the {@link Semaphore} 'eat'.
	 */
	public void foodOnTable()  		{ mutex.down(); eat.up(); mutex.up();		         }
	
	/**
	 * This method is used, directly, by the last {@link Student} to finish eating all the courses.
	 * When this event occurs is made a 'up' on {@link Semaphore} 'waitForOthers' what will allow the payment 
	 * of the meal by the last student that arrived on the restaurant.
	 */
	public void allFinished() 		{ mutex.down(); waitForOthers.up(); mutex.up();   }
	
	/**
	 * This method is used, directly, by all the {@link Student}.
	 * Whenever a student has just eaten this method is called in order to discovery how many students have 
	 * already finished, being that this value it will be used along the method 'run' of the {@link Student}.
	 */
	public void finishEating() {
		mutex.down();
		finishedEating++;
		mutex.up();	
	}
	
	/**
	 * This method is used, directly, by the {@link Student}.
	 * The idea behind this method is to return the number of Students that had already finished eating.
	 * This value it will be used along the program by the method 'run' of the {@link Student}.
	 * @return The number of the students that had finished eating.
	 */
	public int getAlreadyFinished() { 
		mutex.down();
		int finishedEat = this.finishedEating;
		mutex.up();
		return finishedEat;  
	}
	
	/**
	 * This method belongs to a method called "studentsFinishEating", declared on {@link SharedMemory},that is 
	 * involved on the process of ending the meal. 
	 * The idea behind this method is to set the variable 'finishedEating' to zero. It's important to say that 
	 * this method is called when the student finish the course.
	 */
	public void mealCompleted()		{ mutex.down(); finishedEating=0; mutex.up();	 }
	

	/**
	 * This method is used, directly, by the last {@link Student} that arrives on the restaurant.
	 * The idea behind this method is to compel last student to wait until all the students had completed the meal. 
	 * It's important to say that this operation is made using a {@link Semaphore} called 'waitForOthers'.
	 */
	public void waitOthers() 		{ waitForOthers.down();  }
	

	/**
	 * This method is used, directly, by the {@link Student}.
	 * Actually the only student that doesn't call this method is the last student to arrive at the restaurant.
	 * The idea behind this method is to put all the students on hold until the last student finish his meal so 
	 * that he can pay.
	 */
	public void waitPayment() 		{ waitPayment.down();	 }
	
	/**
	 * This method is used, directly, by all the {@link Student} and occurs after the payment is completed. 
	 * This operation is done by doing 'up' to the {@link Semaphore} 'waitPayment', so that the students can 
	 * continue their flow.
	 */
	public void goHome() {
		for(int i = 0; i<6 ; i++)
			waitPayment.up();
	}	
	
	/**
	 * This method belongs to a method called "requestBill", declared on {@link SharedMemory},that is involved 
	 * on the process of asking for the bill.
	 * The idea behind this method is to request the bill and wait while the payment is done, and to do that 
	 * this method uses a {@link Semaphore} that will just suffer a 'up' when the payment is received.
	 */
	public void requestBill() 		{ requestPayment.down(); }
	
	/**
	 * This method belongs to a method called "processBill", declared on {@link SharedMemory}, that is used by 
	 * the {@link Waiter} to process the bill when he is on state "RECEIVING_PAYMENT".
	 * The idea behind this method is to wake up the {@link Student} when the payment already in an advanced 
	 * state.
	 */
	public void recievePayment() 	{ requestPayment.up();	 }
}
