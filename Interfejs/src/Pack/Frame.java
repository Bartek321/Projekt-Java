package Pack;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.json.JSONException;
import org.json.JSONObject;

public class Frame extends JFrame implements ActionListener {
	
	List<Component> components;
	JCheckBox[][] cb;
	JTextField[][] ct;
	List<ArrayList<String>> paramList;
	List<ArrayList<Integer>> sensorIdList;
	List<Integer> idList;
	List<String> stationsNamesList;
	JPanel cards;
	HashMap<String, Integer> map;
	
    public Frame() {	
    	super("Program");
    	
    	setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 720);
		setLocation(200, 100);
		
		this.getContentPane().setLayout(new BorderLayout());
		
		JPanel sidePanel = new JPanel();
		JPanel lowSidePanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		JPanel checkPanel = new JPanel();
		JPanel comboPanel = new JPanel();
		JPanel optionsPanel = new JPanel();
		JPanel mainPanel = new JPanel();
		JPanel upPanel = new JPanel();
		JPanel downPanel = new JPanel();
		
		buttonPanel.setMaximumSize(new Dimension(300, 40));
		buttonPanel.setBackground(new Color(0, 255,0));
		
		checkPanel.setMaximumSize(new Dimension(300, 80));
		checkPanel.setLayout(new GridLayout(3,2));
		
		comboPanel.setLayout(new GridLayout(0, 1));
		comboPanel.setMaximumSize(new Dimension(300, 44));
		
		optionsPanel.setBackground(new Color(222,222,222));
		optionsPanel.setPreferredSize(new Dimension(300, 500));
		optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.PAGE_AXIS));
		
		mainPanel.setBackground(new Color(211,211,211));

		idList = new ArrayList<Integer>();
		paramList = new ArrayList<ArrayList<String>>();
		sensorIdList = new ArrayList<ArrayList<Integer>>();
		
		stationsNamesList = new ArrayList<String>();
		
		map = new HashMap<String, Integer>();
		Downloader dl = new Downloader();
		idList = dl.getStationsIds();
		stationsNamesList = dl.getStationsNames();
		for(int i = 0; i < idList.size(); i++) {
			paramList.add(dl.getSensorsParams(idList.get(i)));
			sensorIdList.add(dl.getSensorsIds(idList.get(i)));
			map.put(stationsNamesList.get(i), i);
		}
		
		Object[] stringsA = stationsNamesList.toArray();		
		JComboBox list = new JComboBox(stringsA);
		list.setName("list");
		list.setPreferredSize(new Dimension(200, 30));
		
		String[] strings1 = { "Przekroczenie góra", "Przekroczenie dół", "Wzrost", "Spadek" };
		JComboBox list1 = new JComboBox(strings1);
		list1.setName("list1");
		list1.setPreferredSize(new Dimension(200, 30));
			
		ArrayList<String> listB = new ArrayList<>();
		
		cb = new JCheckBox[7][6];
		ct = new JTextField[7][6];
		
		list.addActionListener(this);	
		
		cards = new JPanel(new CardLayout());		
		
		for(int i = 0; i < idList.size(); i++) {
			JPanel ck = new JPanel();
			JPanel fieldPane = new JPanel();
			JPanel sidePane = new JPanel();
			
			sidePane.setLayout(new GridLayout(0, 2));
			sidePane.add(new JLabel("Typ:"));
			sidePane.add(new JLabel("Wzrost"));	
			
			ck.setMaximumSize(new Dimension(300, 80));
			ck.setMinimumSize(new Dimension(300, 80));
			ck.setLayout(new GridLayout(3,2));
			
			fieldPane.setMinimumSize(new Dimension(300, 80));
	
			for(int j = 0; j < paramList.get(i).size(); j++) {
				cb[i][j] = new JCheckBox(paramList.get(i).get(j).toString());
				cb[i][j].setName(paramList.get(i).get(j).toString() + stationsNamesList.get(i).toString() + "cb");
				ck.add(cb[i][j]);
				
				ct[i][j] = new JTextField();
				ct[i][j].setName(paramList.get(i).get(j).toString() + stationsNamesList.get(i).toString());
				ct[i][j].setPreferredSize(new Dimension(70, 20));
				
				fieldPane.add(new JLabel(paramList.get(i).get(j).toString()));
				fieldPane.add(ct[i][j]);
				
				sidePane.add(new JLabel(paramList.get(i).get(j).toString()));
				sidePane.add(new JLabel("100"));
				
			}
			JPanel p = new JPanel();
			JPanel XPane = new JPanel();
			JPanel f = new JPanel();
			
			GridLayout gg = new GridLayout(0, 4);
			gg.setHgap(5);
			fieldPane.setLayout(gg);
			fieldPane.setMaximumSize(new Dimension(300, listB.size()*15));
			
			f.add(fieldPane);
			f.setPreferredSize(new Dimension(300, 70));
			
			XPane.add(f);
			XPane.setBackground(new Color(1,222,222));
			XPane.setMaximumSize(new Dimension(300,100));
			XPane.setMinimumSize(new Dimension(300,100));
			
			p.add(ck);
			p.add(XPane);
			p.add(sidePane);
			p.setPreferredSize(new Dimension(500, 700));
			p.setBackground(new Color(255,0,0));
			
			cards.add(p, stationsNamesList.get(i).toString());
		}

		Chart chart = new Chart();
        ChartPanel chartPanel1 = chart.createChart(new Color(255, 0, 0));
        chartPanel1.setName("paramChart");
        ChartPanel chartPanel11 = chart.createChart(new Color(0, 0, 255));
        chartPanel11.setName("weatherChart");
             
        upPanel.setPreferredSize(new Dimension(2000, 34));
        //upPanel.setBackground(new Color(255,0,0));
        	
		JComboBox idList = new JComboBox(stringsA);
		idList.setName("idList");
		idList.setPreferredSize(new Dimension(200, 24));
		
		JComboBox idList1 = new JComboBox();
		idList1.setName("idList1");
		idList1.setPreferredSize(new Dimension(100, 24));
		
		for(String i: paramList.get(0)) {
    		idList1.addItem(i);
    	}
		
		idList.addActionListener(this);	
		
		int year = Calendar.getInstance().get(Calendar.YEAR);
		
		JComboBox listb1 = new JComboBox();
		listb1.setName("day");
		listb1.setPreferredSize(new Dimension(100, 24));
		for(int i = 1; i < 32; i++) {
			listb1.addItem(i);
		}
		
		String[] s = {"Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec", "Lipiec", "Sierpień", "Wrzesień", "Październik", "Listopad", "Grudzień"};
		JComboBox listb2 = new JComboBox(s);
		listb2.setName("month");
		listb2.setPreferredSize(new Dimension(100, 24));
		
		listb2.addActionListener(this);	
		
		JComboBox listb3 = new JComboBox();
		listb3.setName("year");
		listb3.setPreferredSize(new Dimension(100, 24));
		System.out.println(year + "SDfdd");
		for(int i = 0; i < year - 2017; i++) {
			listb3.addItem(Integer.toString(year + i));
			System.out.println(year + i);
		}
		
		JButton buttonD = new JButton("Wyświetl");
		buttonD.addActionListener(this);	
		
		upPanel.add(idList);
		upPanel.add(idList1);  
		upPanel.add(listb1);
		upPanel.add(listb2);
		upPanel.add(listb3);
		upPanel.add(buttonD);
		
		String[] ss = {"Temperatura", "Wilgotność", "Siła wiatru", "Ciśnienie"};
		JComboBox paramList1 = new JComboBox(ss);
		paramList1.setName("type");
		paramList1.setPreferredSize(new Dimension(100, 24));
		
		JComboBox paramList2 = new JComboBox();
		paramList2.setName("day1");
		paramList2.setPreferredSize(new Dimension(100, 24));
		for(int i = 1; i < 32; i++) {
			paramList2.addItem(i);
		}
		
		JComboBox paramList3 = new JComboBox(s);
		paramList3.setName("month1");
		paramList3.setPreferredSize(new Dimension(100, 24));
		
		paramList3.addActionListener(this);	
		
		JComboBox paramList4 = new JComboBox();
		paramList4.setName("year1");
		paramList4.setPreferredSize(new Dimension(100, 24));
		System.out.println(year + "SDfdd");
		for(int i = 0; i < year - 2017; i++) {
			paramList4.addItem(Integer.toString(year + i));
			System.out.println(year + i);
		}
		
		JButton buttonE = new JButton("Wyświetl ");
		buttonE.addActionListener(this);
		
		downPanel.add(paramList1);
		downPanel.add(paramList2);  
		downPanel.add(paramList3);
		downPanel.add(paramList4);
		downPanel.add(buttonE);
             
		downPanel.setPreferredSize(new Dimension(2000, 38));
		
		JButton button = new JButton("Dodaj");
		button.addActionListener(this);	
		
		JButton button1 = new JButton("Reset");
		button1.addActionListener(this);		
		
		comboPanel.add(list);
		comboPanel.add(list1);

		buttonPanel.add(button);
		buttonPanel.add(button1);
		
		optionsPanel.add(comboPanel);
		optionsPanel.add(cards);
		optionsPanel.add(buttonPanel);

		sidePanel.add(optionsPanel);
		sidePanel.add(lowSidePanel);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		JPanel chartsPanel = new JPanel();
		chartsPanel.add(chartPanel1);
		chartsPanel.add(chartPanel11);
		chartsPanel.setPreferredSize(new Dimension(860, (int) screenSize.getHeight() - 150));
		chartsPanel.setBackground(new Color(255, 255, 0));
		
		mainPanel.add(upPanel);
		mainPanel.add(chartsPanel);
		mainPanel.add(downPanel);
		
		this.getContentPane().add(sidePanel, BorderLayout.LINE_END);
		this.getContentPane().add(mainPanel, BorderLayout.CENTER);
		components = Util.getAllComponents(this.getContentPane());
		
    }
    
    public void actionPerformed(ActionEvent e) {
    	System.out.println(((Component) e.getSource()).getName());
    	Object selected = ((JComboBox) Util.getComponent("list", components)).getSelectedItem();
    	if(e.getActionCommand() == "Dodaj") {	
	    	
	    	Map<String, String> map = new TreeMap<>();
	
	    	JSONObject Jobject = new JSONObject();
	    	try {
	    		Jobject.put("station", (((JComboBox)Util.getComponent("list", components)).getSelectedItem()));
	    		Jobject.put("type", (((JComboBox)Util.getComponent("list1", components)).getSelectedItem()));
	    		
	    		Integer x = new Integer(0);
	    		for(int i = 0; i < idList.size(); i++) {
	    			if(stationsNamesList.get(i).toString().equals( ((JComboBox) Util.getComponent("list", components)).getSelectedItem().toString()) )
						x = i;
	    			System.out.println(x);
	    			System.out.println(idList.get(i).toString());
	    			System.out.println(((JComboBox) Util.getComponent("list", components)).getSelectedItem().toString());
	    		}
	    		for(int i = 0; i < paramList.get(x).size(); i++) {
	        		if(((JCheckBox) Util.getComponent( paramList.get(x).get(i)  +  ((JComboBox) Util.getComponent("list", components)).getSelectedItem().toString() + "cb"   , components)).isSelected())
	        			map.put(((JCheckBox)Util.getComponent(  paramList.get(x).get(i)  +  ((JComboBox) Util.getComponent("list", components)).getSelectedItem().toString() + "cb"   , components)).getText(), ((JTextField) Util.getComponent(paramList.get(x).get(i)  +  ((JComboBox) Util.getComponent("list", components)).getSelectedItem().toString(), components)).getText());
	        	}
	        	Jobject.put("values", map);      	
	    	}
	    	catch (JSONException E) {
	    		
	    	}
	    	System.out.println(Jobject.toString());
    	} else if (e.getActionCommand() == "Reset") {
    		JSONObject Jobject = new JSONObject();
	    	try {
	    		Jobject.put("info", "reset");
	    	}
	    	catch (JSONException E) {
	    		
	    	}
    	} else if (e.getActionCommand() == "Wyświetl") { 
    		JFreeChart chart = ((ChartPanel) Util.getComponent("paramChart", components)).getChart();
    		CategoryPlot plot = chart.getCategoryPlot();
    		plot.setDataset(Chart.createDataset(((JComboBox)Util.getComponent("idList1", components)).getSelectedItem().toString()));
    	} else if (e.getActionCommand() == "Wyświetl ") { 
    		JFreeChart chart = ((ChartPanel) Util.getComponent("weatherChart", components)).getChart();
    		CategoryPlot plot = chart.getCategoryPlot(); 		
    		plot.setDataset(Chart.createDataset(((JComboBox)Util.getComponent("type", components)).getSelectedItem().toString()));
    	} else if (((Component) e.getSource()).getName().equals("month")) {
    		YearMonth yearMonthObject = YearMonth.of(Integer.parseInt(((JComboBox)Util.getComponent("year", components)).getSelectedItem().toString()), ((JComboBox)Util.getComponent("month", components)).getSelectedIndex() + 1);
        	int daysInMonth = yearMonthObject.lengthOfMonth();
    		((JComboBox)Util.getComponent("day", components)).removeAllItems();
        	for(int i = 1; i < daysInMonth + 1; i++) {
        		((JComboBox)Util.getComponent("day", components)).addItem(i);
        	}
    	} else if (((Component) e.getSource()).getName().equals("month1")) {
    		YearMonth yearMonthObject1 = YearMonth.of(Integer.parseInt(((JComboBox)Util.getComponent("year1", components)).getSelectedItem().toString()), ((JComboBox)Util.getComponent("month1", components)).getSelectedIndex() + 1);
        	int daysInMonth1 = yearMonthObject1.lengthOfMonth();

        	((JComboBox)Util.getComponent("day1", components)).removeAllItems();
        	for(int i = 1; i < daysInMonth1 + 1; i++) {
        		((JComboBox)Util.getComponent("day1", components)).addItem(i);
        	}	
    	} else if (((Component) e.getSource()).getName().equals("idList")) {
    		((JComboBox)Util.getComponent("idList1", components)).removeAllItems();
        	for(int i = 0; i < (paramList.get( map.get( ( (JComboBox) Util.getComponent("idList", components) ).getSelectedItem().toString())).size()); i++) {
        		((JComboBox)Util.getComponent("idList1", components)).addItem((paramList.get( map.get( ( (JComboBox) Util.getComponent("idList", components) ).getSelectedItem().toString())).get(i)));
        	}
    		
    	} else if (((Component) e.getSource()).getName().equals("list")) {
    		CardLayout cardLayout = (CardLayout) cards.getLayout();
        	cardLayout.show(cards, (((JComboBox)Util.getComponent("list", components)).getSelectedItem()).toString());
    	}

    	for(int i = 0; i < paramList.size(); i++) {
	    	if (selected.toString().equals(sensorIdList.get(i).toString())) {
	    	}
    	}
    }
}
