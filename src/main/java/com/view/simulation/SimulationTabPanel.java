package com.view.simulation;

import javax.swing.*;
import java.awt.*;

public class SimulationTabPanel extends JPanel {
    private JTabbedPane jTabbedPane=new JTabbedPane();   //存放选项卡的组件
    private String[] tabNames={"通信模式","分析模式"};
    public SimulationTabPanel(){
        layoutComponents();
    }
    private void layoutComponents(){
        int i = 0;
        // 第一个标签下的JPanel
        jTabbedPane.addTab(tabNames[i++],new CommPanel());

        // 第二个标签下的JPanel
        jTabbedPane.addTab(tabNames[i++], new AnalyPanel());//加入第一个页面
        setLayout(new GridLayout(1, 1));
        add(jTabbedPane);
    }
}
