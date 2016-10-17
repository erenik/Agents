
import java.util.ArrayList;
import java.lang.Thread;
import java.lang.Runnable;

class Worker extends Thread
{
	boolean workIsDone = false;
	int seed;
	static int seedEnumerator = 1;
	static int workersStarted = 0;
	static int workersStopped = 0;
	
	
	Worker()
	{
		seed = seedEnumerator + 1;
	}
	public static void main(String[] args)
	{
		Worker w = new Worker();
//		w.DoWork(); // 39% cpu consumed, 10.5 MB,
		w.DoALotOfWork(5); // 50~90% CPU consumed, 12 MB,
		while(true)
			;
	}
	// Starts some threads for arbitrary calculations to consume CPU power.
	public void DoWork()
	{
		Thread t = new Thread(this);
		t.start();
	};
	public void DoALotOfWork(int numThreads)
	{
		for (int i = 0; i < numThreads; ++i)
		{
			Thread t = new Thread(this);
			t.start();
		}
	}
	public void ConsumeMemory()
	{
		// If needed, implement.
	}
	// Pretty much just generate some weird number.
	public void run()
	{
		workersStarted++;
		System.out.println("Workers started: "+workersStarted);
		float sum = 1;
		int lastInt = 0;
		for (int i = seedEnumerator; i < 100000000; ++i)
		{
			sum += (float) i / sum;
			sum *= Math.sqrt(i) / 100.0;
			sum /= i;
//			System.out.println("sum: "+sum);
			lastInt = i;
		}
		PrimeNumbers();
		System.out.println("Work done, result: "+sum+" iterations: "+lastInt);
		workersStarted--;
		System.out.println("Workers stopped: "+workersStopped);
	}
	
	void PrimeNumbers()
	{
		ArrayList<Long> primes = new ArrayList<Long>();
		long maxSearch = Long.MAX_VALUE;
		for (long i = 2; i < maxSearch; ++i)
		{
			// Check division with previous primes.
			boolean dividableByAny = false;
			for (int j = 0; j < primes.size(); ++j)
			{
				float num = (float)i, prime = (float)primes.get(j);
				float result = num / prime;
				int intResult = (int)result;
				float remainder = result - intResult;
//				System.out.println("result: "+result+" intResult "+intResult+"num/prime: "+num+"/"+prime+"Remainder: "+remainder);
	//			try {Thread.sleep(500);} catch (Exception e){}
				if (remainder == 0)
				{
					dividableByAny = true;
				}
			}
			if (dividableByAny)
				continue;
			primes.add(i);
			if (primes.size() % 1000 == 0)
				System.out.println("Found "+primes.size()+"th prime number: "+i);
		}
		System.out.println("Found "+primes.size()+" out of "+maxSearch);
	}
};




