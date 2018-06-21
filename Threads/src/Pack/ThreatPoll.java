package Pack;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreatPoll {
	
	public static void go() {
		int i = 0;
		/*try {
			Thread.sleep(getSleepTime());			//sleep
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}*/
		for(;;) {
			i++;
			run(0);
			try {
				Thread.sleep(getSleepTime());  //sleep
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(i == 1)
				break;
		}
	}
	
	private static int getSleepTime() {
		int mins = 0;
		if(LocalDateTime.now().getMinute() > 30)
			mins = 60 - LocalDateTime.now().getMinute() + 30;	
		else 
			mins = 30 - LocalDateTime.now().getMinute();
		return mins;
	}

    public static void run(int index) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        ArrayList<Integer> list = new ArrayList<Integer>();  
        Downloader downloader = new Downloader();
        list = downloader.getStationsIds();
        //Runnable weatherWorker = new WeatherWorker();
        //executor.execute(weatherWorker);
        int x = 0;
        for (int i = 0; i < list.size(); i++) {
        	x++;
        	Runnable worker = new Worker(list.get(i));
            executor.execute(worker);
            if(i > -1)
            	break;
          }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("Finished all threads");
    }
}