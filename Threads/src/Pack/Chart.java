package Pack;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.CategoryAnnotation;
import org.jfree.chart.annotations.XYShapeAnnotation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.util.Rotation;


public class Chart extends JFrame {
	
    public Chart(String applicationTitle, String chartTitle) {
        super(applicationTitle);
        
        setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLocation(200, 100);

        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                "Years","Number of Schools",
                createDataset2(),
                PlotOrientation.VERTICAL,
                true,true,false);
        Font font3 = new Font("Dialog", Font.BOLD, 10); 
        CategoryPlot p = lineChart.getCategoryPlot(); 
        ValueAxis axis = p.getRangeAxis();
        CategoryAxis axis1 = p.getDomainAxis();
        
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) p.getRenderer();
        renderer.setBaseShapesVisible(true);
        renderer.setSeriesShape(0, new Ellipse2D.Double(-3d, -3d, 6d, 6d));
        
        axis1.setTickLabelFont(font3);
        axis1.setCategoryMargin(0.00001);
        axis1.setLowerMargin(0);
        axis1.setUpperMargin(0);
             ChartPanel chartPanel1 = new ChartPanel( lineChart );
             this.add(chartPanel1);
        
        

    }

    
    private DefaultCategoryDataset createDataset2( ) {
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
     

}