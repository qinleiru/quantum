package com.view.component;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

public class ChartComponent {

    private static ChartPanel chartPanel;

    //获取ChartPanel
    public static ChartPanel getChartPanel(String chartTitle) {
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                chartTitle ,
                "欧米伽的取值" ,
                "成功恢复的概率" ,
                createDataset() ,
                PlotOrientation.VERTICAL ,
                true , true , false);

        chartPanel = new ChartPanel( xylineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        final XYPlot plot = xylineChart.getXYPlot( );
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
        renderer.setSeriesPaint( 0 , Color.RED );
        renderer.setSeriesPaint( 1 , Color.GREEN );
        renderer.setSeriesPaint( 2 , Color.YELLOW );
        renderer.setSeriesStroke( 0 , new BasicStroke( 4.0f ) );
        renderer.setSeriesStroke( 1 , new BasicStroke( 3.0f ) );
        renderer.setSeriesStroke( 2 , new BasicStroke( 2.0f ) );
        plot.setRenderer( renderer );
        return chartPanel;
    }
    private static XYDataset createDataset( )
    {
        //红色线
        final XYSeries curveOne = new XYSeries( "a^2=c^2=1/4" );
        curveOne.add( 1.0 , 1.0 );
        curveOne.add( 2.0 , 4.0 );
        curveOne.add( 3.0 , 3.0 );
        //绿色线
        final XYSeries curveTwo = new XYSeries( "a^2=1/3,c^2=1/4" );
        curveTwo.add( 1.0 , 4.0 );
        curveTwo.add( 2.0 , 5.0 );
        curveTwo.add( 3.0 , 6.0 );
        //黄色线
        final XYSeries curveThree = new XYSeries( "a^2=c^2=1/3" );
        curveThree.add( 3.0 , 4.0 );
        curveThree.add( 4.0 , 5.0 );
        curveThree.add( 5.0 , 4.0 );
        final XYSeriesCollection dataset = new XYSeriesCollection( );
        dataset.addSeries( curveOne );
        dataset.addSeries( curveTwo );
        dataset.addSeries( curveThree );
        return dataset;
    }

}
