package Pack;

import java.awt.BorderLayout;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.json.JSONException;
import org.json.JSONObject;

public class Frame extends JFrame implements ActionListener {
	
	JCheckBox[] cb;
	
    public Frame() {	
    	super("Program");
    	
    	setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLocation(200, 100);
		
		this.getContentPane().setLayout(new BorderLayout());
		
		JPanel sidePanel = new JPanel();
		JPanel lowSidePanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		JPanel checkPanel = new JPanel();
		JPanel comboPanel = new JPanel();
		comboPanel.setLayout(new GridLayout(0, 1));
		JPanel fieldPanel = new JPanel();
		JPanel fieldPanel1 = new JPanel();
		
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(222,222,222));
		panel.setPreferredSize(new Dimension(300, 250));
		JPanel panel1 = new JPanel();
		panel1.setBackground(new Color(211,211,211));
		
		String[] strings = { "stacja 1", "stacja 2", "stacja 3" };
		JComboBox list = new JComboBox(strings);
		list.setName("list");
		list.setPreferredSize(new Dimension(200, 30));
		String[] strings1 = { "Przekroczenie góra", "Przekroczenie dó³", "Wzrost", "Spadek" };
		JComboBox list1 = new JComboBox(strings1);
		list1.setName("list1");
		list1.setPreferredSize(new Dimension(200, 30));
		

		JCheckBox check = new JCheckBox("PM10");
		check.setSelected(true);
		check.setName("check");
		JCheckBox check1 = new JCheckBox("PM2.5");
		check1.setSelected(false);
		check1.setName("check1");
		
		cb = new JCheckBox[2];
		cb[0] = check;
		cb[1] = check1;
		
		JLabel label = new JLabel("Wartoœæ:");
		JTextField field = new JTextField();
		field.setName("PM10");
		field.setPreferredSize(new Dimension(120, 20));
		
		JLabel label1 = new JLabel("Wartoœæ1:");
		JTextField field1 = new JTextField();
		field1.setName("PM2.5");
		field1.setPreferredSize(new Dimension(120, 20));
		
		JButton button = new JButton("Dodaj");
		button.addActionListener(this);	
		
		JButton button1 = new JButton("Reset");
		button1.addActionListener(this);		
		
		comboPanel.add(list);
		comboPanel.add(list1);
		checkPanel.add(check);
		checkPanel.add(check1);
		fieldPanel.add(label);
		fieldPanel.add(field);
		fieldPanel1.add(label1);
		fieldPanel1.add(field1);
		buttonPanel.add(button);
		buttonPanel.add(button1);
		
		panel.add(comboPanel);
		panel.add(checkPanel);
		panel.add(fieldPanel);
		panel.add(fieldPanel1);
		panel.add(buttonPanel);
		
		sidePanel.add(panel);
		sidePanel.add(lowSidePanel);
		
		this.getContentPane().add(sidePanel, BorderLayout.LINE_END);
		this.getContentPane().add(panel1, BorderLayout.CENTER);
    }
    
    public static List<Component> getAllComponents(final Container c) {
        Component[] comps = c.getComponents();
        List<Component> compList = new ArrayList<Component>();
        for (Component comp : comps) {
            compList.add(comp);
            if (comp instanceof Container) {
                compList.addAll(getAllComponents((Container) comp));
            }
        }
        return compList;
      }
    
    public Component getComponent(String name, List<Component> list) {
        for (Component i : list) {
        	if(i.getName() == name)
        		return i;
        }
        	return null;
    }
    
    public void actionPerformed(ActionEvent e) {
    	if(e.getActionCommand() == "Dodaj") {
	    	List<Component> components = new ArrayList<Component>();
	    	components = getAllComponents(this.getContentPane());
	    	
	    	Map<String, String> map = new TreeMap<>();
	
	    	JSONObject Jobject = new JSONObject();
	    	try {
	    		Jobject.put("station", (((JComboBox)getComponent("list", components)).getSelectedItem()));
	    		Jobject.put("type", (((JComboBox)getComponent("list1", components)).getSelectedItem()));
	    		for(int i = 0; i < cb.length; i++) {
	        		if(cb[i].isSelected())
	        			map.put(cb[i].getText(), (((JTextField)getComponent(cb[i].getText(), components)).getText()));
	        	}
	        	Jobject.put("values", map);      	
	    	}
	    	catch (JSONException E) {
	    		
	    	}
	    	System.out.println(Jobject.toString());
    	} else {
    		JSONObject Jobject = new JSONObject();
	    	try {
	    		Jobject.put("info", "reset");
	    	}
	    	catch (JSONException E) {
	    		
	    	}
	    	System.out.println(Jobject.toString());
    	}
    }
}