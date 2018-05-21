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