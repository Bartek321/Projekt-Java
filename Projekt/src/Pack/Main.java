package Pack;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;
import org.json.simple.parser.JSONParser;

public class Main {
    public static void main(String[] args) {
    	System.out.println("dfg");
    	
    	/*String fromFile = "http://api.gios.gov.pl/pjp-api/rest/data/getData/92";
        String toFile = "D:\\file.json";

        try {

            URL website = new URL(fromFile);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(toFile);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
            rbc.close();

        } catch (IOException e) {
            e.printStackTrace();
        }*/
        
    	JSONParser parser = new JSONParser();
    	//JSONArray a = (JSONArray) parser.parse(new FileReader("http://api.gios.gov.pl/pjp-api/rest/data/getData/92"));
    /*	try {
	    	File file = new File("D:\\file.json");
	        String content = FileUtils.readFileToString(file, "utf-8");
	        
	        // Convert JSON string to JSONObject
	        JSONObject o = new JSONObject(content); 
	        JSONArray favorite_foods = o.getJSONArray("values");
	        for (int i = 0; i < favorite_foods.length(); i++) {
	            String food = (String) favorite_foods.getJSONObject(i).get("value").toString();
	            System.out.println(food);
	        }
	        System.out.println((String)(favorite_foods.toString()));
	        //JSONArray dg = favorite_foods.;
	        //System.out.println((String)(favorite_foods1.toString()));
    	}
    	catch(IOException e) {  		
    	}
    	catch(JSONException e) {
    	}*/
    	
    	try {
	    	URI uri = new URI("http://api.gios.gov.pl/pjp-api/rest/data/getData/92");
	    	JSONTokener tokener = new JSONTokener(uri.toURL().openStream());
	    	JSONObject Jobject = new JSONObject(tokener);
	    	JSONArray Jarray = Jobject.getJSONArray("values");
	        for (int i = 0; i < Jarray.length(); i++) {
	            String value = (String) Jarray.getJSONObject(i).get("value").toString();
	            System.out.println(value);
	            String date = (String) Jarray.getJSONObject(i).get("date").toString();
	            System.out.println(date);
	        }
    	}
    	catch(URISyntaxException e) {
    		
    	}
    	catch(JSONException e) {
    		
    	}
    	catch(MalformedURLException e) {
    		
    	}
    	catch(IOException e) {  		
    	}
    	
    	
    	
    }
}