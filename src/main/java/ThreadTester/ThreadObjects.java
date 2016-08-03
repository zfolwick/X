package ThreadTester;

import junit.framework.Assert;

public class ThreadObjects {
	/* 
	 * These methods create the shared resource across multiple threads.  This is a customized helper of the test method.
	 * 
	 * which is called via:
	 *     createThreads(new Object1(sharedresource), new Object2(sharedresource));
	 *     
	 * For any number of arguments, go ahead and populate an array of threads with a thread for each argument and set it's name.
	 */
	public static Thread[] createThreads(Runnable ...runnables ) {
		Thread[] threads = new Thread[runnables.length];

		int i = 0;
		for ( Runnable t : runnables ) {
			threads[i] = new Thread(t);
			threads[i].setName("add thread" + i++);
		}
		return threads;
	}
	
	// starts and executes all incoming threads.
	public static void runAllThreads(Thread[] t) {
		int i=0;
		int len = t.length - 1;
		for (; i < len ; i++) {
			t[i].start();
			t[i+1].start();
			try {
				t[i].join();
				t[i+1].join();
			}catch (InterruptedException e) {
				e.printStackTrace();
				Assert.fail("Thread got interrupted.");
			}
		}
	}
}
