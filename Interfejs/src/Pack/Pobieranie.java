package Pack;
import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

import javax.lang.model.element.Element;
import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;
import org.json.simple.parser.JSONParser;
import org.omg.CORBA.portable.InputStream;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Pobieranie {
	
	public static String getData(String url) {
		URL u;
		String data = new String();
		try {
			u = new URL(url);
    	URLConnection conn;
			conn = u.openConnection();

    	BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
    	data = reader.lines().collect(Collectors.joining("\n")); 	
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}
    	
		return data;
	}
	
	public static ArrayList getStationsIds() {
		ArrayList<Integer> idList = new ArrayList<Integer>();
		try {
        	JSONArray Jarray = new JSONArray(getData("http://api.gios.gov.pl/pjp-api/rest/station/findAll"));

	    	JSONObject Jobject = new JSONObject();
	    	JSONObject Jobject1 = new JSONObject();
	    	JSONObject Jobject2 = new JSONObject();
	    	
	    	for(int i = 0; i < Jarray.length(); i++) {
	    		Jobject = (JSONObject) Jarray.get(i);
	    		Jobject1 = (JSONObject) Jobject.get("city");
	    		Jobject2 = (JSONObject) Jobject1.get("commune");
	    		if(Jobject2.get("communeName").toString().equals("Kraków"))
	    			idList.add(Integer.parseInt(Jobject.get("id").toString()));
	    	}    	

    	} catch(JSONException e) { 		
    	}
		
		return idList;
	}
	
	public static ArrayList getSensorsIds(Integer index) {
		ArrayList<Integer> idList = new ArrayList<Integer>();
		try {
        	JSONArray Jarray = new JSONArray(getData("http://api.gios.gov.pl/pjp-api/rest/station/sensors/" + index.toString()));
        	   	
	    	JSONObject Jobject = new JSONObject();
	    	
	    	for(int i = 0; i < Jarray.length(); i++) {
	    		Jobject = (JSONObject) Jarray.get(i);
	    		idList.add(Integer.parseInt(Jobject.get("id").toString()));    		
	    	} 
	    	
	     	
    	} catch(JSONException e) { 		
    	}
		return idList;
	}
	
	public static ArrayList getSensorsParams(Integer index) {
		ArrayList<String> paramList = new ArrayList<String>();
		try {
        	JSONArray Jarray = new JSONArray(getData("http://api.gios.gov.pl/pjp-api/rest/station/sensors/" + index.toString()));
	    	
	    	JSONObject Jobject = new JSONObject();
	    	JSONObject Jobject1 = new JSONObject();
	    	
	    	for(int i = 0; i < Jarray.length(); i++) {
	    		Jobject = (JSONObject) Jarray.get(i);
	    		Jobject1 = (JSONObject) Jobject.get("param");
	    		paramList.add(Jobject1.get("paramCode").toString());	    		
	    	} 
	     	
    	} catch(JSONException e) { 		
    	}
		return paramList;
	}
	
	public static ArrayList getValues(Integer index) {
		ArrayList<String> paramList = new ArrayList<String>();
		try {
        	JSONObject Jobject = new JSONObject(getData("http://api.gios.gov.pl/pjp-api/rest/data/getData/" + index.toString()));
        	JSONArray Jarray = (JSONArray) Jobject.get("values");
        	
	    	JSONObject Jobject1 = new JSONObject();
	    	
	    	for(int i = 0; i < Jarray.length(); i++) {
	    		Jobject1 = (JSONObject) Jarray.get(i);
	    		paramList.add(Jobject1.get("date").toString());   
	    		paramList.add((Jobject1.get("value")).toString());
	    	} 
	    	
    	} catch(JSONException e) { 		
    	}
		return paramList;
	}
	public static void saveToDataBase(ArrayList<String> data,int j){
		String url = "jdbc:mysql://mysql.agh.edu.pl:3306/mors2?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String username = "mors2";
		String password = "haslojava";
		//System.out.println("Connecting database...");

		try (Connection connection = DriverManager.getConnection(url, username, password)) {
		   // System.out.println("Database connected!");
		    
		    for(int i=0 ;i+1<data.size();i+=2){
		    if(data.get(i+1)!="null"&&data.get(i)!="null") {
		    String query = "insert ignore into Results ( IDSensor, Date, Result)" + " values ( ?, ?, ?)";
		    PreparedStatement preparedStmt = connection.prepareStatement(query);
		    preparedStmt.setInt(1, j);
		    preparedStmt.setString(2, data.get(i));
		    preparedStmt.setDouble(3, Double.parseDouble(data.get(i+1)));
		    
		    preparedStmt.execute();
		    }
		    else{
		    //pustych danych nie dodajemy
		    //String query = "insert into Results (IDResult, IDSensor, Date, Result)" + " values (null , ?, null, ?)";
		    //PreparedStatement preparedStmt = connection.prepareStatement(query);
		    }	
		    }
		    connection.close();
		} catch (SQLException e) {
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
		
	}
	public static void getAllData() {
		ArrayList<Integer> stationsIds = getStationsIds();
		ArrayList<Integer> sensorsIds = new ArrayList<>();
		ArrayList<String> typeOfData = new ArrayList<>();
		ArrayList<String> data = new ArrayList<>();

		
		for(int i = 0; i < stationsIds.size(); i++) {
			sensorsIds = getSensorsIds(stationsIds.get(i));
			typeOfData=getSensorsParams(stationsIds.get(i));
			System.out.println(getSensorsParams(stationsIds.get(i)));
			for(int j = 0; j < sensorsIds.size(); j++) {
				data = getValues(sensorsIds.get(j));
				saveToDataBase(data,sensorsIds.get(j));
				
				//System.out.println(getValues(sensorsIds.get(j)));
			}
			
		}
	    

	}
	
	public static ArrayList getWeather() {
		ArrayList<String> list = new ArrayList<>();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder builder = factory.newDocumentBuilder();
		    InputSource is = new InputSource(new StringReader(getData("http://meteo.ftj.agh.edu.pl/meteo/meteo.xml")));
		    org.w3c.dom.Document d = builder.parse(is);

		    d.getDocumentElement().normalize();
		    
		    NodeList nList = d.getElementsByTagName("dane_aktualne");
		    NodeList nList1 = (NodeList) nList.item(0);
    
            for (int i = 0; i < nList1.getLength(); i++) {
            	if(i == 1 || i == 3 || i == 13 || i == 17)
            		list.add(nList1.item(i).getTextContent());
            }
		    
	    } catch (FileNotFoundException e) {
		} catch (IOException e) {
		} catch (ParserConfigurationException e) {
		} catch (SAXException e) {
		}
		return list;
	}
	public static void initiliase(){
		String url = "jdbc:mysql://mysql.agh.edu.pl:3306/mors2?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String username = "mors2";
		String password = "haslojava";
		System.out.println("Connecting database...");

		try (Connection connection = DriverManager.getConnection(url, username, password)) {
		    System.out.println("Database connected!");
		    String query = "CREATE TABLE IF NOT EXISTS `mors2`.`Results` ( `IDSensor` INT NOT NULL,`Date` DATETIME NOT NULL,`Result` DOUBLE )" ;
		      PreparedStatement preparedStmt = connection.prepareStatement(query);
		      preparedStmt.execute();
		      query="ALTER TABLE Results ADD CONSTRAINT Results_unique UNIQUE(IDSensor,Date,Result)";
		      preparedStmt = connection.prepareStatement(query);
		      preparedStmt.execute();

		      connection.close();
		} catch (SQLException e) {
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
		
	}
    public static void main(String[] args) {
    	
    	//initiliase();
    	getAllData();
    	
    //	System.out.println(Double.parseDouble("null"));
    }
}

