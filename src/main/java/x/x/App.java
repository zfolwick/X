package x.x;

public class App 
{
	public static void main( String[] args )
	{
	}
}

// A shared resource for multiple threads to begin using.
class MyCounter {
	private volatile int c = 0;

	//Adding a "synchronized" keyword here will cause the unit test to pass.
	public void increment() {
		c++;
	}

	public   void decrement() {
		c--;
	}

	public  int value() {
		return c;
	}    
}