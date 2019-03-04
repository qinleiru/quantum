package com.view.component;

import com.protocols.HPQIS.HpqisService;
import com.protocols.pojo.DataPoint;
import com.quantum.oparate.MathOperation;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
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
    public static ChartPanel getChartPanel(String chartTitle,int authority) {
        //创建主题样式
        StandardChartTheme standardChartTheme=new StandardChartTheme("CN");
//设置标题字体
        standardChartTheme.setExtraLargeFont(new Font("隶书",Font.BOLD,20));
//设置图例的字体
        standardChartTheme.setRegularFont(new Font("宋书",Font.PLAIN,15));
//设置轴向的字体
        standardChartTheme.setLargeFont(new Font("宋书",Font.PLAIN,15));
//应用主题样式
        ChartFactory.setChartTheme(standardChartTheme);
        String descY="成功恢复的概率";
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                chartTitle ,
                "ω的取值" ,
                "成功恢复的概率" ,
                createDataset(authority) ,
                PlotOrientation.VERTICAL ,
                true , true , false);

        chartPanel = new ChartPanel( xylineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        final XYPlot plot = xylineChart.getXYPlot( );
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
        renderer.setSeriesPaint( 0 , Color.RED );
        renderer.setSeriesPaint( 1 , Color.GREEN );
        renderer.setSeriesPaint( 2 , Color.YELLOW );
        renderer.setSeriesStroke( 0 , new BasicStroke( 2.0f ) );
        renderer.setSeriesStroke( 1 , new BasicStroke( 2.0f ) );
        renderer.setSeriesStroke( 2 , new BasicStroke( 2.0f ) );
        plot.setRenderer( renderer );
        return chartPanel;
    }
    private static XYDataset createDataset(int authority )
    {
        //红色线
        final XYSeries curveOne = new XYSeries( "a^2=c^2=1/4" );
        DataPoint dataPoint;
        dataPoint= HpqisService.getPointData(0.5,0.5,1,authority);
        curveOne.add( dataPoint.getX() , dataPoint.getY() );
        dataPoint= HpqisService.getPointData(0.5,0.5,1.5,authority);
        curveOne.add( dataPoint.getX(),dataPoint.getY());
        dataPoint= HpqisService.getPointData(0.5,0.5,2,authority);
        curveOne.add( dataPoint.getX(),dataPoint.getY());
        dataPoint= HpqisService.getPointData(0.5,0.5,2.5,authority);
        curveOne.add( dataPoint.getX(),dataPoint.getY());
        dataPoint= HpqisService.getPointData(0.5,0.5,3,authority);
        curveOne.add( dataPoint.getX(),dataPoint.getY());
        //绿色线
        final XYSeries curveTwo = new XYSeries( "a^2=1/3,c^2=1/4" );
        double a=Math.pow(3,-0.5);
        double b=Math.sqrt(0.5-Math.pow(a,2));
        double clusterState1[]=new double[]{a,a,b,b};
        MathOperation.normalization(clusterState1);
        a=clusterState1[0];
        dataPoint= HpqisService.getPointData(a,0.5,1.5,authority);
        curveTwo.add( dataPoint.getX() , dataPoint.getY() );
        dataPoint= HpqisService.getPointData(a,0.5,2,authority);
        curveTwo.add( dataPoint.getX() , dataPoint.getY() );
        dataPoint= HpqisService.getPointData(a,0.5,2.5,authority);
        curveTwo.add( dataPoint.getX() , dataPoint.getY() );
        dataPoint= HpqisService.getPointData(a,0.5,3,authority);
        curveTwo.add( dataPoint.getX() , dataPoint.getY() );
        //黄色线
        final XYSeries curveThree = new XYSeries( "a^2=c^2=1/3" );
        double c=Math.pow(3,-0.5);
        double d=Math.sqrt(0.5-Math.pow(a,2));
        double clusterState2[]=new double[]{c,c,d,d};
        MathOperation.normalization(clusterState2);
        c=clusterState1[0];
        dataPoint= HpqisService.getPointData(a,c,2,authority);
        curveThree.add( dataPoint.getX() , dataPoint.getY() );
        dataPoint= HpqisService.getPointData(a,c,2.5,authority);
        curveThree.add( dataPoint.getX() , dataPoint.getY() );
        dataPoint= HpqisService.getPointData(a,c,3,authority);
        curveThree.add( dataPoint.getX() , dataPoint.getY() );
        final XYSeriesCollection dataset = new XYSeriesCollection( );
        dataset.addSeries( curveOne );
        dataset.addSeries( curveTwo );
        dataset.addSeries( curveThree );
        return dataset;
    }

}
