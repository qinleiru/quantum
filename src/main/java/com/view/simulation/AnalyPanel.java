package com.view.simulation;

import com.view.component.ChartComponent;
import com.view.component.ExGridBagConstraints;
import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.awt.*;

/**
 * 对应分析模式中分析面板
 */
public class AnalyPanel extends JPanel {
    /**
     * 分析模式下对应的组件
     */
    private static ChartPanel panellLowAgents;       //权限低的
    private static ChartPanel panelHighAgents;    //权限高的
    /*
       初始化组件，相应组件添加事件
     */
    public AnalyPanel() {
        panelHighAgents= ChartComponent.getChartPanel("权限高的代理者的测量参数取值与成功概率关系图",1);
        panellLowAgents=ChartComponent.getChartPanel("权限低的代理者的测量参数取值于成功概率关系图",0);
        //设置面板边框
        panelHighAgents.setBorder(BorderFactory.createEtchedBorder());
        panellLowAgents.setBorder(BorderFactory.createEtchedBorder());
        //进行布局
        this.setLayout(new GridBagLayout());
        this.add(panelHighAgents,new ExGridBagConstraints(0,0,1,1).setFill(ExGridBagConstraints.BOTH).setIpad(150,400).setWeight(100,10));
        this.add(panellLowAgents,new ExGridBagConstraints(0,1,1,1).setFill(ExGridBagConstraints.BOTH).setIpad(150,400).setWeight(100,10));
    }
}
