package chef;

/**
 * This class represents the entity Chef and implements it's life cycle according to the statement.
 */
public class Chef extends Thread {

	/**
	 * {@link ChefStates} that will be used to save the current state of the {@link Chef}
	 *    @serialField chefState
	 */
	private ChefStates chefState;
	
	/**
	 * Variable that will save the number of courses
	 *    @serialField nrCourses
	 */
	private final int nrCourses;
	
	/**
	 * variable that will save the number of students
	 *    @serialField nrStudents
	 */
	private final int nrStudents;
	
	/**
	 * Stub that is representing the {@link kitchen}
	 */
	KitchenStub kitStub;
	
	/**
	 * Stub that is representing the {@link Bar}
	 */
	BarStub barStub;
	
	/**
	 * Stub that is representing the {@link SharedMem}
	 */
	SharedMemStub sharedMemStub;
	
	
	/**
	 * This method is the constructor of this class. It receives some parameters that allow this class to have access
	 * to different classes and so share information between them.
	 * @param kitStub represents the space that this entity could manipulate and use.
	 * @param barStub represents the space that this entity could manipulate and use.
	 * @param log represents the logger that will be used to describe the evolution of the internal
	 *  state of the problem.
	 * @param nrCourses represents the number of courses
	 * @param nrStudents represents the number of students 
	 */
	public Chef (int nrCourses, int nrStudents, KitchenStub kitStub, BarStub barStub, SharedMemStub sharedMemStub) {
		this.nrCourses = nrCourses;
		this.nrStudents = nrStudents;
		this.kitStub = kitStub;
		this.barStub = barStub;
		this.sharedMemStub = sharedMemStub;
	}
	
	/**
	 * This method represents the life cycle of the entity Chef.
	 */
	@Override
	public void run() {
		//Update status to WAITING_FOR_AN_ORDER and set it on log
		chefState=ChefStates.WAITING_FOR_AN_ORDER;
		sharedMemStub.setChefState(chefState.toString());
			
		//Semaphore cooking down with this instruction waiting for order
		kitStub.watchNews();
		
		//Update status to PREPARING_THE_COURSE and set it on log
		chefState=ChefStates.PREPARING_THE_COURSE;
		sharedMemStub.setChefState(chefState.toString());
		
		//Start cooking
		kitStub.startCooking();
		
		for(int courses = 0 ; courses < nrCourses ; courses++) {
			
			for(int student = 0 ; student < nrStudents ; student ++) {
				
				//Update status to DISHING_THE_PORTIONS and set it on log:
				chefState = ChefStates.DISHING_THE_PORTIONS;
				sharedMemStub.setChefState(chefState.toString());
								
				
				//Update status to DELIVERING_THE_PORTIONS and set it on log:
				chefState = ChefStates.DELIVERING_THE_PORTIONS;
				sharedMemStub.setChefState(chefState.toString());
				
				//If it is the first time start cooking the method alertWaiter is called. 
				//Else the method portionReady is called. This separate the cooking between courses and the first course.
				if (student == 0 && courses == 0) {
					barStub.alertWaiter();
					kitStub.portionReady();
				}
				else {
					kitStub.portionReady();
				}
				
			}
			//If is not the last course, continue preparation
			if(courses<nrCourses-1) {
				kitStub.continuePreparation();
			}
		}
		
		//Update status to DELIVERING_THE_PORTIONS and set it on log:
		chefState=ChefStates.CLOSING_SERVICE;
		sharedMemStub.setChefState(chefState.toString());
	}
}
