package root;

/*
 * Examples of game loops, one simple, the other complex
 * 
 * Author: Elijah K. 
 * Date:   4/1/2021
 * 
 */

public class Main implements Runnable{

	public static void main(String[] args){
		
		Main app = new Main();      // creates new instance of the Main class to run as a new thread
		Thread t = new Thread(app); // creates new thread passing the new instance of the main method to use
		
		t.start(); // starts the new thread, program execution continues in run() method

	}

	@Override
	public void run() 
	{
		System.out.println("Running...");
		
		// loops: uncomment one to run
		
		loopComplex();
		// loopSimple();
	}
	
	/*
	 * ============================================= Complex Loop =============================================
	 */
	
	// Better uses the resources of the computer without a fixed framerate or character update rate. 
	// Tickrate can be used for environment and other things
	private void loopComplex() { 
		boolean running = true; // keeps track of the state of the program. Program terminates when running = false
		long tickLength = 50;   // desired length of one cycle in milliseconds
		
		int allowableIterations = 5; // keeps track of how many "frames" can run per tick
		
		// actual loop
		while(running) 
		{
			long time = System.currentTimeMillis(); // gets the time at the start of the loop
			
			// frame loop
			for(int i = 0; i < allowableIterations; i++) 
			{
				
				// update and render functions not implemented, but this is when they should be ran
				// update();
				
				// render();
				
				// *********** Required sleep time ********** //
				// for stability and consistency, it is reccomended to keep this code in
				try {
					Thread.sleep(1L);
				} 
				catch (InterruptedException e) // standard catch block
				{
					e.printStackTrace();
				}
				// ****************************************** //
				
				// check if the the program is running over the allotted time for one tick, 
				// if so, immediatly decrement the allowable iterations and exit the loop 
				if((System.currentTimeMillis() - time) > tickLength) 
				{
					allowableIterations = allowableIterations == 1? 1 : allowableIterations --; // only decrement iteration count when able to do so while still rendering and updating
					continue; // exits the loop
				}
			}
			System.out.println(allowableIterations); // output how many allowable iterations per tick
			System.out.println("FPS " + allowableIterations * (1000 / tickLength)); // outputs approximate fps
			
			// update tick dependent events if needed
			
			long timeElapsed = System.currentTimeMillis() - time; // gets the time taken from the time the loop started to the current moment
			long excess = tickLength - timeElapsed;  // calculate how long the program has to go before the end of the tick
			
			if(excess > timeElapsed / allowableIterations)  // computes average time per iteration and if the excess time is greater than that, add one iteration per tick
			{
				allowableIterations++; // increment allowable iterations by 1
			}
			
			Thread.sleep(excess); // for stability, the thread pauses for the remainder of the tick length
		}
	}

	/*
	 * ===================================== Simple Loop ============================================
	 */
	
	public static void loopSimple() // renders and updates in one loop, thread sleeps while not in use and framerate and update rate is fixed
	{
		
		boolean running = true; // keeps track of the state of the program. Program terminates when running = false
		long tickLength = 50;   // desired length of one cycle in milliseconds
		
		// int x = 0; // test variable for test output
		
		while(running) 
		{
			long time = System.currentTimeMillis(); // gets the time at the start of the loop
			
			// update();
			
			// render();
			
			// test output
			/*
			x++;
			System.out.println(x);
			*/
			
			try 
			{
				// calculates time needed to wait in order to meet the tickLength requirement
				
				long timeElapsed = System.currentTimeMillis() - time; // gets the time taken from the time the loop started to the current moment
				long timeToWait = tickLength - timeElapsed; // calculates the time to wait in order to meet the ticks per second requirement
				
				timeToWait = timeToWait >= 0 ? timeToWait : 0; // makes sure the timeToWait variable is always non-negative
				
				Thread.sleep(timeToWait); // pauses the thread for the required number of milliseconds
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}
}
