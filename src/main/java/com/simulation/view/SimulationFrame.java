package com.simulation.view;

import javax.swing.*;

public class SimulationFrame extends JFrame {
    private  static JMenuBar menuBar;
    public  SimulationFrame(){
        //设置外观
        selectUI();
        //添加菜单
        initMenu();
        setJMenuBar(menuBar);
        //设置内容面板
        SimulationPanel simulationPanel=new SimulationPanel();
        setContentPane(simulationPanel);
        setBounds(200,200,200,200);
        pack();
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
    /**
     * 初始化菜单栏
     */
    private static void initMenu() {
        menuBar = new JMenuBar();

        // 一级菜单
        JMenu filemenu = new JMenu("File");
        JMenu editmenu = new JMenu("Edit");
        JMenu aboutmenu = new JMenu("About");

        // 二级菜单
        JMenuItem newitem = new JMenuItem("New...");
        JMenuItem closeitem = new JMenuItem("Close...");
        JMenuItem setitem = new JMenuItem("Setting...");

        // 添加二级菜单
        filemenu.add(newitem);
        filemenu.addSeparator();
        filemenu.add(closeitem);
        filemenu.addSeparator();
        editmenu.add(setitem);
        editmenu.addSeparator();

        // 添加一级菜单
        menuBar.add(filemenu);
        menuBar.add(editmenu);
        menuBar.add(aboutmenu);
    }
}
