package Pack;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Worker implements Runnable {
  
    private Integer Id;
    
    public Worker(Integer Id){
        this.Id=Id;
    }

    @Override
    public void run() {
    	Mail mail = new Mail();
    	Downloader downloader = new Downloader();						//download
    	ArrayList<Integer> idList = new ArrayList<Integer>();
    	ArrayList<String> paramList = new ArrayList<String>();
    	ArrayList<ArrayList<String>> valuesList = new ArrayList<ArrayList<String>>();
    	ArrayList<ArrayList<Double>> valuesList1 = new ArrayList<ArrayList<Double>>();
    	HashMap<String, Double> map = new HashMap<String, Double>();
    	
    	idList = downloader.getSensorsIds(Id);
    	paramList = downloader.getSensorsParams(Id);
    	
    	for(int i = 0; i < idList.size(); i++) {
    		valuesList.add(downloader.getValues(idList.get(i)));
    		System.out.println(valuesList.get(i));
    		valuesList1.add(test(valuesList.get(i)));
    		System.out.println(valuesList1.get(i));
    		map.put(paramList.get(i) + "1", valuesList1.get(i).get(0));
    		map.put(paramList.get(i) + "2", valuesList1.get(i).get(1));
    	}
    	System.out.println(paramList);
    	System.out.println("LOLOL");
    	System.out.println(map);
    	
    	//POBRANIE INFO O USTAWIENAICH POWIADMIEN 
    	ArrayList <User> user = new ArrayList<User>();
    	ArrayList<ArrayList<User>> userList = new ArrayList<ArrayList<User>>();
    	Random generator = new Random();
    	user.add(new User("CO", "min", "aaa@22.22", 2000.0));
		user.add(new User("NO2", "max", "aaa@22.22", 40.0));
		user.add(new User("PM10", "rise", "aaa@22.22", 0.01));
		user.add(new User("PM2.5", "fall", "aaa@22.22", 0.01));
		user.add(new User("C6H6", "max", "aaa@22.22", (double) generator.nextInt(100)));
    	for(int i = 0; i < 20; i++) {
    		if(i < 10 && i > 4)
    			user.add(new User("PM10", "max", "aafgdfdfa@22.22", (double) generator.nextInt(100)));
    		if(i < 15 && i > 9)
    			user.add(new User("PM10", "max", "aasdsa@22.22", (double) generator.nextInt(100)));
    		if(i >= 15)
    			user.add(new User("PM10", "max", "aafsssgfa@22.22", (double) generator.nextInt(100)));
    	}
    	
    	for(int i = 0, j = 0; i < user.size(); i++, j++) {
    		userList.add(new ArrayList<User>());
    		userList.get(j).add(user.get(i));
    		while(user.get(i).getAdress().equals(user.get(i + 1).getAdress())) {
    			i++;
    			userList.get(j).add(user.get(i));
    			if(i == user.size() - 1)
    				break;
    		}
    	}
    	
    	String msg = new String();
    	Verify verify = new Verify();
    	for(int i = 0; i < userList.size(); i++) {
    		for(int j = 0; j < userList.get(i).size(); j++) {
    			msg += verify.getMessage(userList.get(i).get(j).getType(), userList.get(i).get(j).getParam(), userList.get(i).get(j).getValue(), map.get(userList.get(i).get(j).getParam() + "1"), map.get(userList.get(i).get(j).getParam() + "2"));
    		}
    		System.out.println(msg);
    		msg = "";
    		//mail.send(userList.get(i).get(j).getAdress(), msg);  //send mail
    	}

    							//put in database
    	System.out.println(Id);
    }
    
    static public ArrayList<Double> test(ArrayList<String> values) {
    	ArrayList<Double> valueList = new ArrayList<Double>();
    	int j = 9;
    	for(int i = 0; i < j; i += 2) {
    		String[] value = values.get(i).split("\\s+");
        	if((value[1].substring(0, 2).equals(String.valueOf(LocalDateTime.now().getHour() )) && !values.get(i + 1).equals("null")) || (value[1].substring(0, 2).equals(String.valueOf(LocalDateTime.now().getHour() - 1)) && !values.get(i + 1).equals("null")) || (value[1].substring(0, 2).equals(String.valueOf(LocalDateTime.now().getHour() - 2)) && !values.get(i + 1).equals("null"))) {
        		if(valueList.size() == 2)
        			break;
        		valueList.add(Double.valueOf(values.get(i + 1)));
        	} else if ((value[1].substring(0, 2).equals(String.valueOf(LocalDateTime.now().getHour() )) && values.get(i + 1).equals("null")) || (value[1].substring(0, 2).equals(String.valueOf(LocalDateTime.now().getHour() - 1)) && values.get(i + 1).equals("null"))) {
        		valueList.add(0.0);
        	} 			
    	}
    	
    	return valueList;
    }

}