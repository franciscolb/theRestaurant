package chef;


/**
 * This class implements the model client-server with server replication, using static threads launching.
 * 
 *	Communications are based in the exchange messages by sockets with TCP protocol
 */

public class ChefStart {
	
	/**
	 * Main program
	 */
	
	public static void main(String[] args) {
		int nrStudents = Integer.parseInt(args[0]);
		int nrCourses = Integer.parseInt(args[1]);
		String hostnameKit = args[2];
		String hostnameBar = args[3];
		String hostnameSharedMem = args[4];
		int portKit = Integer.parseInt(args[5]);
		int portBar = Integer.parseInt(args[6]);
		int portSharedMem = Integer.parseInt(args[7]);
		
		/* Instantiation of the kitchen, bar and sharedMemory stubs*/
		
		KitchenStub kitStub = new KitchenStub(hostnameKit,portKit);
		BarStub barStub = new BarStub(hostnameBar, portBar);
		SharedMemStub sharedMemStub = new SharedMemStub(hostnameSharedMem, portSharedMem);
		
		/* Instantiation of the Chef */
		
		Chef chef = new Chef(nrCourses, nrStudents, kitStub, barStub, sharedMemStub);
		
		/* Chef Thread starting  */
		
		chef.start();
		
		/* Waiting the end of the thread */
		
		try {
			chef.join();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		/* Signal the kitchen server to shutdown  */
		kitStub.shutdown();
		System.out.println("Chef shutdown");
	}

}
