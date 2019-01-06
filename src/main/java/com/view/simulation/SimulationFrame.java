package com.view.simulation;

import javax.swing.*;

public class SimulationFrame extends JFrame {
    public  SimulationFrame(){
        //设置外观
        selectUI();
        //设置内容面板
        SimulationTabPanel simulationPanel=new SimulationTabPanel();
        setContentPane(simulationPanel);
        setBounds(200,20,600,710);
    }
    /**
     * 设置外观：获取当前系统的默认外观
     */
    private static void selectUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(IllegalAccessException | InstantiationException |UnsupportedLookAndFeelException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
