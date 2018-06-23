package Pack;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JTextField;

public class ConnectionDataBase {
	private  Connection connected;
	private ArrayList<String> Czas;
	private ArrayList<Double> Pomiary;
	private ArrayList<String> Types;
	private ArrayList<Integer> IdSensors;
	private ArrayList<Double> Values;
	public ConnectionDataBase()
	{
		 Czas= new ArrayList<String>();
		 Pomiary= new ArrayList<Double>();
		 Types= new ArrayList<String>();
		 IdSensors= new ArrayList<Integer>();
		 Values= new ArrayList<Double>();
		 connect();

		
	}
	public  void connect(){
		String url = "jdbc:mysql://mysql.agh.edu.pl:3306/mors2?useUnicode=true&characterEncoding=utf8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String username = "mors2";
		String password = "haslojava";
			try {
			connected = DriverManager.getConnection(url, username, password);
		}catch  (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public  void disconect()
	{
		try {
			connected.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean login(String nick, String hasło){
		
	      String query = "SELECT * FROM Users WHERE nick='"+nick+"'";
	      PreparedStatement preparedStmt;
		try {
			preparedStmt = connected.prepareStatement(query);
			ResultSet rs;
			rs = preparedStmt.executeQuery(query);
			if(rs.isBeforeFirst())
		      {
			  rs.next();
		      String haslo =rs.getString("haslo");
		      if(haslo.equals(hasło))
		    	  return true;
		   
		      }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false ;	      
	      }
	      public void SetNotification(int IdSensor, String Type,Double Value, String Mail )	
	      {
	    	  String query = "Insert ignore into Notification (IdSensor ,Type, Value, Mail) "+"VALUES (?,?,?,?)";
	    	  PreparedStatement preparedStmt;
	  		try {
	  			preparedStmt = connected.prepareStatement(query);
	  			 preparedStmt.setInt(1, IdSensor);
	  			 preparedStmt.setString(2, Type);
	  			 preparedStmt.setDouble(3, Value);
	  			 preparedStmt.setString(4, Mail);
	  			 preparedStmt.execute();
	  		} catch (SQLException e) {
	  			// TODO Auto-generated catch block
	  			e.printStackTrace();
	  		}
	      }    
	public void GetNotification(String Mail) throws SQLException
	{

	      String query = "SELECT * FROM Notification WHERE Mail='"+Mail+"'";
	      PreparedStatement preparedStmt = connected.prepareStatement(query);
	      ResultSet rs = preparedStmt.executeQuery(query);
	      if(rs.isBeforeFirst())
	      {
	    	  for(; ;)
	    		 {
	    		  if (rs.next())
	    		  {
	    		  Types.add(rs.getString("Type"));
	    		  IdSensors.add(rs.getInt("IdSensor"));
	    		  Values.add(rs.getDouble("Value"));

	    		  }
	    		 else
	    			 break;
	    		 }
	      }
	      connected.close();
	}
	public void  registration(String mail, String haslo, String nick){
		 String query = "Insert ignore into Users (mail,haslo, nick)"+" VALUES (?,?,?)";
		 PreparedStatement preparedStmt;
		try {
			preparedStmt = connected.prepareStatement(query);
			 preparedStmt.setString(1, mail);
			 preparedStmt.setString(2, haslo);
			 preparedStmt.setString(3, nick);

			 preparedStmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		    
	
	
		
	}
	
	public  void getData( int IDSensor, String Data) throws SQLException
	{
		
		
		 String query = "SELECT Result, Date FROM `Results` WHERE IDSensor = "+Integer.toString(IDSensor)+" AND Date  LIKE '"+Data+ "%' Order BY Date";
	      PreparedStatement preparedStmt = connected.prepareStatement(query);
	      ResultSet rs = preparedStmt.executeQuery(query);
	      if(rs.isBeforeFirst())
	      {
	    	  for(; ;)
	    		 {
	    		  if (rs.next())
	    		  {
	    		  Pomiary.add(rs.getDouble("Result"));
	    		  Czas.add(rs.getString("Date"));
	    		  }
	    		 else
	    			 break;
	    		 }
	      }
	      connected.close();	
	}
	public String getMail(String nick){
		
		 String query = "SELECT mail FROM Users WHERE nick='"+nick+"'";
	      PreparedStatement preparedStmt;
		try {
			preparedStmt = connected.prepareStatement(query);
			ResultSet rs;
			rs = preparedStmt.executeQuery(query);
			if(rs.isBeforeFirst())
		      {
			  rs.next();
		      String mail =rs.getString("mail");
		      return mail;
		      }
		     
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nick;	      
		
}
		
		public ArrayList <Double> getPomiary(){
			
			return Pomiary;
		}
		public ArrayList <String> getCzasy(){
			
			return Czas;
		}
		public ArrayList <Double> getValues(){
			
			return Values;
		}
		public ArrayList <String> getTypes(){
			
			return Types;
		}
		public ArrayList <Integer> getIdSensors(){
			
			return IdSensors;	}
		
}


