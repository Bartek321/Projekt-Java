package Pack;

import java.awt.EventQueue;

public class Main {

    public static void main(String[] args) {
    	Downloader d = new Downloader();
    	ThreatPoll tp = new ThreatPoll();
    	tp.go();
    	String s = "2018-06-21 13:00:00";
    	String[] values = s.split("\\s+");
    	String ss =  values[1].substring(0, 2);
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Chart("aa", "bb");
            }
        });
    }
}
    	
		//ThreatPoll tp = new ThreatPoll();
		//tp.run(6);
		
		//Mail mail = new Mail();
		//mail.send("tenloginjestprosty@wp.pl", "wiadomoœæ");
		
		/*Verify v= new Verify();
		System.out.println(v.checkMax("PM10", 10, 5));
		System.out.println(v.checkMin("PM2.5", 5, 6));
		System.out.println(v.checkRise("PM1", 11, 33, 0.01));
		System.out.println(v.checkFall("S", 11, 33, 0.01));*/
		
		
