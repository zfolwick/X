package x.x;

import ThreadTester.ThreadObjects;
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
		Thread[] testThreads = ThreadObjects.createThreads(new RunnableWrapper(counter), new RunnableWrapper(counter));
		// execute threads on shared resource.  This should then modify the shared resource, which may now be checked.
		ThreadObjects.runAllThreads(testThreads);
		// assert the end state
		int valueAfter2MillionIterations=2000000;
		Assert.assertEquals("OOPS!", valueAfter2MillionIterations, counter.value());
	}
	
	public void testIncrementWhileDecrement()
	{
		// Create shared resource, and pass this shared resource to a method that shares it across threads.
		MyCounter counter = new MyCounter();
		Thread[] testThreads = ThreadObjects.createThreads(new IncrementWrapper(counter), new DecrementWrapper(counter));

		// execute threads on shared resource.  This should then modify the shared resource, which may now be checked.
		ThreadObjects.runAllThreads(testThreads);

		// assert the end state
		int valueAfter2MillionIterations = 0;
		Assert.assertEquals("OOPS!", valueAfter2MillionIterations, counter.value());
	}

	public class IncrementWrapper implements Runnable {
		private MyCounter cnt;
		public IncrementWrapper(MyCounter counter) {
			cnt = counter;
		}

		public void run() {
			for ( int i=0; i<1000000; i++ ) {
				cnt.increment();
			}	
		}
	}
	
	public class DecrementWrapper implements Runnable {
		private MyCounter cnt;

		public DecrementWrapper(MyCounter counter) {
			cnt = counter;
		}

		public void run() {
			for ( int i=0; i<1000000; i++ ) {
				cnt.decrement();
			}	
		}
	}


	/*
	 * A runnable wrapper class that exercises the class under test.  This include private instances of the shared resource under test
	 * and the run function begins the actual exercising of the object under test.  A new wrapper class can easily be created for each api you
	 * wish to test.
	 * Usage:
	 *    Thread t1 = new Thread(new GETApiCallWrapper(apiUnderTest));
	 *    t2.setName("add thread");
	 *    
	 *    Thread t2 = new Thread(new PUTApiCallWrapper(apiUnderTest));
	 *    t2.setName("add thread2");
	 *    
	 * where GETApiCallWrapper and PUTApiCallWrapper take as their constructor the context.
	 * E.g.,
	 * public IContent apiContent = RestApiGet.getDSContent();
	 *  <some code>
	 * Thread t1 = new Thread(new GETApiCallWrapper(apiContent));
	 * 
	 * and GETApiCallWrapper sets a private variable to the constructor parameter, and because it implements Runnable,
	 * it has a run function, which calls the api:
	 * 
	 * run() {
	 *    List<Content> myList = apiContent.getContentList(param1, param2, param3, param4);
	 * }
	 * 
	 * The PUTApiCallWrapper is set up the same way, except that it modifies the data inside its run function.
	 * 
	 * These threads, when started at the same time, may show race conditions.
	 */
	class RunnableWrapper implements Runnable {
		private MyCounter counter;

		public RunnableWrapper(MyCounter counter) {
			this.counter = counter;
		}

		public void run() {
			for ( int i=0; i<1000000; i++ ) {
				counter.increment();
			}
		}
	}
}
