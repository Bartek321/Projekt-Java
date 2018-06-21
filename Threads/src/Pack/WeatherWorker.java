package Pack;

import java.util.ArrayList;

public class WeatherWorker implements Runnable {
    
    public WeatherWorker(){
    }

    @Override
    public void run() {
    	Downloader downloader = new Downloader();					
    	ArrayList <Double> list = downloader.getWeather();
    	System.out.println(list);
    	// SEND TO DATABASE
    }

}