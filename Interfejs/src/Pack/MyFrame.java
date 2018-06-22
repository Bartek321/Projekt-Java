package Pack;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyFrame extends JFrame implements ActionListener {
	List<Component> components;
    public MyFrame() {	
    	super("login");
    	
    	setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(250, 150);
		setLocation(200, 100);

		JPanel mainPanel = new JPanel();
		JPanel labelPanel = new JPanel();
		JPanel fieldPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		
		fieldPanel.setLayout(new GridLayout(0,2));
		
		JLabel name = new JLabel("Name:");
		JLabel password = new JLabel("Password:");
		JLabel label = new JLabel("Logowanie");
		
		JTextField nameField = new JTextField();
		nameField.setName("name");
		JPasswordField passField = new JPasswordField();
		passField.setName("pass");
		
		JButton loginButton = new JButton("Loguj");
		loginButton.addActionListener(this);
		JButton registerButton = new JButton("Rejestruj");
		registerButton.addActionListener(this);
				
		labelPanel.add(label);
		
		fieldPanel.add(name);
		fieldPanel.add(nameField);
		fieldPanel.add(password);
		fieldPanel.add(passField);
		
		buttonPanel.add(loginButton);
		buttonPanel.add(registerButton);
		
		mainPanel.add(labelPanel);
		mainPanel.add(fieldPanel);
		mainPanel.add(buttonPanel);
		
		this.add(mainPanel);
		components = Util.getAllComponents(this.getContentPane());
    }
    
    public void actionPerformed(ActionEvent e) {  	
    	
    	System.out.println(((JTextField)Util.getComponent("name", components)).getText());
    	System.out.println(((JPasswordField)Util.getComponent("pass", components)).getText());
    	if(e.getActionCommand() == "Loguj") {
    	 	String url = "jdbc:mysql://mysql.agh.edu.pl:3306/mors2?useUnicode=true&characterEncoding=utf8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    		String username = "mors2";
    		String password = "haslojava";
    		try (Connection connection = DriverManager.getConnection(url, username, password) ) {
			    String query = "SELECT * FROM Users WHERE nick='"+ ((JTextField) Util.getComponent("name", components)).getText()+"'";
			    PreparedStatement preparedStmt = connection.prepareStatement(query);
			    ResultSet rs = preparedStmt.executeQuery(query);
			    if(rs.isBeforeFirst()) {
			    	rs.next();
			        String haslo = rs.getString("haslo");
			        String haslologin =((JTextField) Util.getComponent("pass", components)).getText();
			      	if(haslo.equals(haslologin)) {
			      		 //System.out.format("%s, %s, \n",haslologin,haslo ); 
						connection.close();	
						new Frame();
				    	this.dispose();
			      	}
			    }
					connection.close();	
					System.out.println("z³e logowanie");
					
			} catch (SQLException e1) {
				throw new IllegalStateException("Cannot connect the database!", e1);
			}
    		
    	} else if (e.getActionCommand() == "Rejestruj") {
			new RegisterFrame();
		}
    	
    }
}