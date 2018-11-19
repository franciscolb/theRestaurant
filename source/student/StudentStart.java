package student;
	
public class StudentStart {

	public static void main(String[] args) {
		final int nrStudents = Integer.parseInt(args[0]);
		final int nrMeals = Integer.parseInt(args[1]);
		final String hostnameBar = args[2];
		final String hostnameTable = args[3];
		final String hostnameSharedMem = args[4];
		final int portBar = Integer.parseInt(args[5]);
		final int portTable = Integer.parseInt(args[6]);
		final int portSharedMem = Integer.parseInt(args[7]);
		
		/* Instantiation of the table, bar and sharedMemory stubs*/
		
		BarStub barStub = new BarStub(hostnameBar, portBar);
		TableStub tableStub = new TableStub(hostnameTable, portTable, nrStudents);
		SharedMemStub sharedMemStub = new SharedMemStub(hostnameSharedMem, portSharedMem);


		Student[] students = new Student[nrStudents];
		
		for(int i = 0 ; i < nrStudents ; i++) {
			students[i] = new Student(barStub, tableStub, i, nrMeals, nrStudents, sharedMemStub);
		}
	
		for(int i = 0 ; i < nrStudents ; i++) {
			students[i].start();
		}
		
		
		for(int i = 0 ; i < nrStudents ; i++) {
			try {
				students[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
		tableStub.shutdown();
		sharedMemStub.shutdown();
		System.out.println("Student shutdown");
	}

}
