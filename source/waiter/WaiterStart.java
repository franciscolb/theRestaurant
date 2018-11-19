package waiter;

public class WaiterStart {

	public static void main(String[] args) {
		final int nrStudents = Integer.parseInt(args[0]);
		final String hostnameKit = args[1];
		final String hostnameBar = args[2];
		final String hostnameTable = args[3];
		final String hostnameSharedMem = args[4];

		final int portKit = Integer.parseInt(args[5]);
		final int portBar = Integer.parseInt(args[6]);
		final int portTable = Integer.parseInt(args[7]);
		final int portSharedMem = Integer.parseInt(args[8]);

		/* Instantiation of the kitchen, bar and sharedMemory stubs*/
		
		KitchenStub kitStub = new KitchenStub(hostnameKit,portKit);
		BarStub barStub = new BarStub(hostnameBar, portBar);
		TableStub tableStub = new TableStub(hostnameTable, portTable, nrStudents);
		SharedMemStub sharedMemStub = new SharedMemStub(hostnameSharedMem, portSharedMem);

		Waiter waiter = new Waiter(kitStub, barStub, tableStub, nrStudents, sharedMemStub);
		
		
		waiter.start();
		
		try {
			waiter.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		barStub.shutdown();
		System.out.println("Waiter shutdown");
	}

}
