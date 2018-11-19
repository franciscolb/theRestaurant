package sharedMem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class Logger {
	private String filename;
	private Semaphore mutex = new Semaphore();
	private int nrStudents;
	private PrintWriter writer;
	
	/**
	 * Constructor of the class Logger.
	 * Responsible by create a file to write, writing the header and initialize the status of the entities writing them.
	 * @param filename
	 */
	public Logger(String filename, int nrStudents) {
		this.filename = filename;
		this.nrStudents = nrStudents;
		try {
			writer = new PrintWriter(new File(filename));
			writer.printf("%-28s %-28s ", "Chef", "Waiter");
			for(int i = 1 ; i <= nrStudents; i++)
				writer.printf("%-28s ", "Student " + i);
			writer.printf("\n");
			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println("Erro ao criar ficheiro.");		
		}
		mutex.up();
	}
	
	/**
	 * This method is used to log a file. Whenever an entity changes his state the log file is updated. This method is 
	 * called on SharedMemory that saves the states of the entities.
	 * @param chefState, the state of the chef that is saved in sharedMemory
	 * @param waiterState, the state of the waiter that is saved in sharedMemory
	 * @param studentsStates, the states of the students that are saved in sharedMemory
	 */
	public void log(String chefState, String waiterState, String[] studentsStates) {
		mutex.down();
		try {
			writer = new PrintWriter(new FileOutputStream(new File(filename),true));
		} catch (FileNotFoundException e) {
			System.out.println("Erro ao abrir ficheiro.");		
		}
		writer.printf("%-28s %-28s ", chefState, waiterState);
		for(int i = 0 ; i < nrStudents; i++)
			writer.printf("%-28s ", studentsStates[i]);
		writer.printf("\n");
		writer.close();
		mutex.up();
	}
}
