package student;

/**
 * This class represents the entity Chef and implements it's life cycle according to the statement.
 */

public class Student extends Thread {
	
	/**
	 * variable that will save the ID of the thread.
	 *    @serialField nrStudents
	 */
	private int id;
	
	/**
	 * {@link ChefStates} that will be used to save the current state of the {@link Student}
	 *    @serialField studentState
	 */
	private StudentStates studentState;
	
	/**
	 * Variable that will save the number of courses
	 *    @serialField nrMeals
	 */
	private int nrMeals;
	
	/**
	 * variable that will save the number of students
	 *    @serialField nrStudents
	 */
	private int nrStudents;
	
	/**
	 * {@link StudentOrder} that will be used to save the current state of the {@link Student}
	 *    @serialField studentState
	 */
	private StudentOrder order;
	
	/**
	 * Stub that is representing the {@link Bar}
	 */
	private BarStub barStub;
	
	/**
	 * Stub that is representing the {@link Table}
	 */
	private TableStub tableStub;
	
	/**
	 * Stub that is representing the {@link sharedMem}
	 */
	private SharedMemStub sharedMemStub;
	
	private Semaphore mutex = new Semaphore();
	
	private static int studentsFinished;
	
	/**
	 * This method is the constructor of this class. It receives some parameters that allow this class to have access
	 * to different classes and so shared information between them.
	 * @param tableStub represents the space that this entity could manipulate and use.
	 * @param barStub represents the space that this entity could manipulate and use.
	 * @param log represents the logger that will be used to describe the evolution of the internal
	 *  state of the problem.
	 * @param nrMeals represents the number of courses
	 * @param nrStudents represents the number of students
	 * @param ID of the thread.
	 */
	public Student (BarStub barStub, TableStub tableStub, int id, int nrMeals, int nrStudents, SharedMemStub sharedMemStub) {
		this.id = id;
		this.nrMeals = nrMeals;
		this.nrStudents = nrStudents;
		this.barStub = barStub;
		this.tableStub = tableStub;
		this.sharedMemStub = sharedMemStub;
		mutex.up();
	}
	
	/**
	 * This method represents the life cycle of the entity Student.
	 */
	@Override
	public void run() {
		
		
		try {
			sleep((long)(Math.random()*1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//Update status to TAKING_A_SEAT_AT_THE_TABLE and set it on log:
		studentState = StudentStates.TAKING_A_SEAT_AT_THE_TABLE;
		sharedMemStub.setStudentState(studentState.toString(), this.id);
		
		barStub.enterRestaurant();
		order = tableStub.sittingStudent(this.id);

		//Update status to SELECTING_THE_COURSES and set it on log:
		this.studentState = StudentStates.SELECTING_THE_COURSES;
		sharedMemStub.setStudentState(studentState.toString(), this.id);
		
		
		if(order == StudentOrder.OTHER) {
			//Update status to CHATTING_WITH_COMPANIONS and set it on log:
			this.studentState = StudentStates.CHATTING_WITH_COMPANIONS;
			sharedMemStub.setStudentState(studentState.toString(), this.id);
		}
		else if (order == StudentOrder.LAST) {
			tableStub.lastStudent(this.id);
		}
		else if(order == StudentOrder.FIRST) {
						
			//Update status to ORGANIZING_THE_ORDER and set it on log:
			this.studentState = StudentStates.ORGANIZING_THE_ORDER;
			sharedMemStub.setStudentState(studentState.toString(), this.id);
			tableStub.firstStudent();
			barStub.describeOrder();
			
			//Update status to CHATTING_WITH_COMPANIONS and set it on log:
			this.studentState = StudentStates.CHATTING_WITH_COMPANIONS;
			sharedMemStub.setStudentState(studentState.toString(), this.id);
		}
		
		for(int meals = 0; meals < nrMeals ; meals ++) {
			
			tableStub.chatting();
			
			//Update status to ENJOYING_THE_MEAL and set it on log:
			this.studentState = StudentStates.ENJOYING_THE_MEAL;
			sharedMemStub.setStudentState(studentState.toString(), this.id);
	
			
			try {
				sleep((long)(Math.random()*1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//Update status to CHATTING_WITH_COMPANIONS and set it on log:
			this.studentState = StudentStates.CHATTING_WITH_COMPANIONS;
			sharedMemStub.setStudentState(studentState.toString(), this.id);
			
			mutex.down();
			Student.studentsFinished++;
			
			if(Student.studentsFinished == nrStudents ) {
				
				tableStub.mealCompleted();
				if(meals==nrMeals-1) {
					tableStub.allFinished();
				}
					
				else {
					barStub.alertWaiter();
					Student.studentsFinished=0;
				}
			}
			mutex.up();
		}
		
		if(order == StudentOrder.LAST) {
			tableStub.waitOthers();
			
			barStub.askBill();
			tableStub.requestBill();
			barStub.paymentDone();
			
			//Update status to PAYING_THE_MEAL and set it on log:
			this.studentState = StudentStates.PAYING_THE_MEAL;
			sharedMemStub.setStudentState(studentState.toString(), this.id);
			
			//Update status to GOING_HOME and set it on log:
			this.studentState = StudentStates.GOING_HOME;
			sharedMemStub.setStudentState(studentState.toString(), this.id);
			
			
			tableStub.goHome();
		} else {
			tableStub.waitPayment();
			//Update status to GOING_HOME and set it on log:
			this.studentState = StudentStates.GOING_HOME;
			sharedMemStub.setStudentState(studentState.toString(), this.id);
		}
		
		
	}
}
