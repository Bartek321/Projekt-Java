package Pack;

import java.awt.EventQueue;
import java.sql.SQLException;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            	ConnectionDataBase conn=new ConnectionDataBase();
            	try {
					conn.getData(2745, "2018-06-04");
					ArrayList<Double> wyniki =new ArrayList<Double>();
					ArrayList<String> czasy =new ArrayList<String>();
					wyniki=conn.getPomiary();
					czasy=conn.getCzasy();
					//conn.disconect();
	            	System.out.println("Próba testowa");

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	//proba zapisania powiadomienia
            	conn.connect();
            	conn.SetNotification(2745, "Tutaj trzeba typ", 533.31 , "jakiś@mail.com");
            	//proba odczytu
            	ArrayList<Double> Wartosci =new ArrayList<Double>();
				ArrayList<String> TypyPowiadomien =new ArrayList<String>();
				ArrayList<Integer> IdSensorów =new ArrayList<Integer>();
            	try {
					conn.GetNotification("jakiś@mail.com");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	Wartosci =conn.getValues();
            	TypyPowiadomien =conn.getTypes();
            	IdSensorów =  conn.getIdSensors();
            	System.out.println("Próba testowa2");
            	conn.disconect();

              new MyFrame();
            }
        });
    }
}