package sharedMem;

/**
 * This class saves the states of the entities that are involved in this system. Whenever a state is changed, the changes
 * are written to the log file. This class has four attributes. The chefState, waiterState, studentsStates that is an array
 * with all the students. Finally have  a log to save the flow of the program.
 */
public class SharedMemory {
	
	private String chefState;
	private String waiterState;
	private String studentsStates[];
	private Logger log;
	private Semaphore mutex = new Semaphore();
	
	
	/**
	 * This method is the constructor of this class and it's used to create an object of this type.
	 * It receives a {@link Bar}, {@link Kitchen} and {@link Table} both of them initialized on the {@link Main}.
	 * Also receive the log class that is used to create a log file. The last parameter is the number of students that 
	 * will eat at the restaurant.
	 * @param kit, object of type {@link Kitchen}
	 * @param bar, object of type {@link Bar}
	 * @param table, object of type {@link Table}
	 * @param log, object of type {@link Logger}
	 * @param nrStudents, integer number of students
	 */
	public SharedMemory (Logger log, int nrStudents) {
		chefState = ChefStates.WAITING_FOR_AN_ORDER.toString();
		waiterState = WaiterStates.APPRAISING_SITUATION.toString();
		studentsStates = new String[nrStudents];
		for(int i = 0; i < nrStudents ; i++)
			studentsStates[i] = StudentStates.GOING_TO_THE_RESTAURANT.toString();
		this.log = log;
		mutex.up();
	}
	/**
	 * In this method the state of the Chef is updated and logged to the log file. This method is called when chef
	 * changes his state.
	 * @param chefState, the new state of the chef.
	 */
	public void setChefState(String chefState){
		mutex.down();
		this.chefState = chefState;
		this.log.log(chefState, waiterState, studentsStates);
		mutex.up();
	}
	
	/**
	 * In this method the state of the Waiter is updated and logged to the log file. This method is called when waiter
	 * changes his state.
	 * @param waiterState, the new state of the waiter.
	 */
	public void setWaiterState(String waiterState){
		mutex.down();
		this.waiterState = waiterState;
		this.log.log(chefState, waiterState, studentsStates);
		mutex.up();
	}
	
	/**
	 * In this method the state of the Student is updated and logged to the log file. This method is called when student
	 * changes his state.
	 * @param studentState, the new state of the student.
	 * @param id, the id of the student that is changing his state.
	 */
	public void setStudentState (String studentState, int id) {
		mutex.down();
		this.studentsStates[id] = studentState;
		this.log.log(chefState, waiterState, studentsStates);
		mutex.up();
	}
}
