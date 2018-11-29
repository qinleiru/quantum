package com.simulation.view;

import javax.swing.*;
import java.awt.*;

public class SimulationPanel extends JPanel {
    private static JPanel jpanelMenu;
    private static JPanel jpanelSort;
    private static JPanel jpanelResult;

    private static JLabel jlableSort;
    private static JLabel jlabelResult;

    private static JScrollPane jtextSort;
    private static JScrollPane jtextResult;

    private static JComboBox jcomboSelect;

    private static JButton jbuttonSort;

    private static JProgressBar jprogressSort;
    public SimulationPanel(){
        // 按钮初始化
        jbuttonSort = ButtonComponent.getCommButton();

        // 选择项初始化
        jcomboSelect = SelectComponent.getComboBox();

        // 标签初始化
        jlableSort = new JLabel("通信进程：");
        jlabelResult = new JLabel("通信进程：");

        // 文本框初始化
        jtextSort = TextComponent.getCommTextPanel();
        jtextResult = TextComponent.getCartoonTextPanel();

        // 进度条初始化
        jprogressSort = ProgressComponent.getProgressBar();

        // 设置面板
        init_panel();
        init_frame();
    }
    //JPanel面板初始化
    private static void init_panel() {
        jpanelMenu = new JPanel();
        jpanelSort = new JPanel();
        jpanelResult = new JPanel();

        //设置面板边框
        jpanelSort.setBorder(BorderFactory.createEtchedBorder());
        jpanelResult.setBorder(BorderFactory.createEtchedBorder());
        jpanelMenu.setBorder(BorderFactory.createEtchedBorder());
    }

    //frame初始化
    private void init_frame() {
        //添加布局
        setLayout(new GridBagLayout());
        add(jpanelMenu, setConstraints(0, 0, 1, 3, 1, 1));
        add(jpanelSort, setConstraints(0, 1, 2, 3, 1, 1));
        add(jpanelResult, setConstraints(0, 3, 2, 3, 1, 1));
        //面板布局
        //菜单面板
        jpanelMenu.setLayout(new GridBagLayout());
        jpanelMenu.add(jcomboSelect, setConstraints(0, 0, 1, 1, 1, 1));
        jpanelMenu.add(jbuttonSort, setConstraints(1, 0, 1, 1, 1, 1));
        jpanelMenu.add(jprogressSort, setConstraints(0, 2, 1, 3, 1, 1));
        //排序面板
        jpanelSort.setLayout(new GridBagLayout());
        jpanelSort.add(jlableSort, setConstraints(0, 0, 1, 2, 1, 1));
        jpanelSort.add(jtextSort, setConstraints(0, 2, 2, 4, 0, 2));
        //结果面板
        jpanelResult.setLayout(new GridBagLayout());
        jpanelResult.add(jlabelResult, setConstraints(0, 0, 1, 2, 1, 1));
        jpanelResult.add(jtextResult, setConstraints(0, 2, 2, 4, 0, 2));
    }

    //设置布局管理器
    private static GridBagConstraints setConstraints(int gx, int gy, int gh, int gw, int wx, int wy) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = gx;
        constraints.gridy = gy;
        constraints.gridheight = gh;
        constraints.gridwidth = gw;
        constraints.weightx = wx;
        constraints.weighty = wy;
        constraints.fill = GridBagConstraints.BOTH;
        return constraints;
    }
}
