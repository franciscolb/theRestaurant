package table;

public class Fifo<T> {
    private T[] fifo;
    private int in, out;
    private Semaphore semPro;
    private Semaphore semCon;
    private Semaphore mutex;
    
	@SuppressWarnings("unchecked")
	public Fifo (int size){
		if (size > 0) {
			fifo = (T[]) new Object [size];
			in = out = 0;
			semPro = new Semaphore();
			semCon = new Semaphore();
			mutex = new Semaphore();
			semPro.up();
			mutex.up();
		}
    }

    public void  push (T elemento) throws InterruptedException{
    	semPro.down();
    	mutex.down();
		fifo[in] = elemento;
		in = (in + 1) % fifo.length;
		semCon.up();
    	mutex.up();
    }
    
    public T pop () throws InterruptedException{
    	T val = null;
    	semCon.down();
    	mutex.down();
		val = fifo[out];
		out = (out + 1) % fifo.length;
		semPro.up();
		mutex.up();
    	return val;
    }
}
