package Pack;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Paint;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Random;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

public class Chart {
	
	 public ChartPanel createChart(Color color) {
		 JFreeChart lineChart = ChartFactory.createLineChart("22.05.2018", "czas","wartoœæ", createDataset(), PlotOrientation.VERTICAL, true,true,false);
	     Font font = new Font("Dialog", Font.BOLD, 8); 
	     CategoryPlot plot = lineChart.getCategoryPlot(); 
	     ValueAxis valueAxis = plot.getRangeAxis();
	     CategoryAxis categoryAxis = plot.getDomainAxis();
	     
	     LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
	     renderer.setBaseShapesVisible(true);
	     renderer.setSeriesShape(0, new Ellipse2D.Double(-3d, -3d, 6d, 6d));
	     renderer.setSeriesFillPaint(0, color);
	        
	     categoryAxis.setTickLabelFont(font);
	     categoryAxis.setLowerMargin(0);
         categoryAxis.setUpperMargin(0);
         
	     ChartPanel chartPanel = new ChartPanel(lineChart);
	     chartPanel.setPreferredSize(new Dimension(840, 300));
	     
	     return chartPanel;	             
	 }
	
	 public DefaultCategoryDataset createDataset( ) {
	    	ArrayList<Double> list = new ArrayList<>();
	    	Random generator = new Random();

	        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );

	        for(int i = 0; i < 24; i++) {
	    		Double j = (double) generator.nextInt(100);
	    		if(i % 5 == 0)
	    			list.add(-1.0);
	    		else
	    			list.add(j);
	    		
	    	}
	        
	        for(int i = 0; i < 24; i++) {
	        	if(list.get(i) == -1.0)
	        		dataset.addValue( null , "PM 10" , i + ":" + "00" );
	        	else
	        		dataset.addValue( list.get(i) , "PM 10" , i + ":" + "00" );
	        }
	        return dataset;
	     }
	 
	 public DefaultCategoryDataset createDataset2(ArrayList<Double> data ) {
	    	ArrayList<Double> list = new ArrayList<>();
	    	Random generator = new Random();

	        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
	        
	        for(int i = 0; i < data.size(); i++) {
	        	if(list.get(i) == -1.0)
	        		dataset.addValue( null , "" , i + ":" + "00" );
	        	else
	        		dataset.addValue( list.get(i) , "" , i + ":" + "00" );
	        }
	        return dataset;
	     }

}
