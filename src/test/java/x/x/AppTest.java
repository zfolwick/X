package x.x;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
extends TestCase
{

	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public AppTest( String testName )
	{
		super( testName );
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite()
	{
		return new TestSuite( AppTest.class );
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp()
	{
		// Create shared resource, and pass this shared resource to a method that shares it across threads.
		MyCounter counter = new MyCounter();
		Thread[] testThreads = createThreads(counter);
		// execute threads on shared resource.  This should then modify the shared resource, which may now be checked.
		runAllThreads(testThreads);
		// assert the end state
		int valueAfter2MillionIterations=2000000;
		Assert.assertEquals("OOPS!", valueAfter2MillionIterations, counter.value());
	}

	/* 
	 * Helper method- This creates the shared resource across all threads.
	 */
	public Thread[] createThreads(MyCounter counter) {
		//Create two threads with shared resource of the CounterIncRunnable(counter) and kick them off
		Thread thread1 = new Thread(new CounterIncRunnable(counter));
		thread1.setName("add thread");

		Thread thread2 = new Thread(new CounterIncRunnable(counter));
		thread2.setName("add thread2");
		Thread[] t;
		t = new Thread[] {thread1, thread2};

		return t;
	}

	// starts and executes all incoming threads.
	public void runAllThreads(Thread[] t) {
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
			}
		}
	}
	
	/*
	 * A runnable wrapper class that exercises the class under test
	 */
	class CounterIncRunnable implements Runnable {
	private MyCounter counter;

	public CounterIncRunnable(MyCounter counter) {
		this.counter = counter;
	}

	public void run() {
		for ( int i=0; i<1000000; i++ ) {
			counter.increment();
		}
	}
}


}
