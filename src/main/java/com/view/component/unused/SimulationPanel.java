package com.view.component.unused;

import com.view.component.TextComponent;

import javax.swing.*;
import java.awt.*;

public class SimulationPanel extends JPanel {
    private static JPanel jpanelController;
    private static JPanel jpanelCommunication;

    private static JLabel jlabelComm;
    private static JLabel jlabelController;

    private static JScrollPane jtextComm;

    private static JComboBox jcomboSelect;

    private static JButton jbuttonComm;

    public SimulationPanel(){
        // 按钮初始化
//        jbuttonComm = ButtonComponent.getCommButton();

        // 选择项初始化
//        jcomboSelect = SelectComponent.getComboBox();

        // 标签初始化
        jlabelController=new JLabel("控制区");
        jlabelComm = new JLabel("通信过程：");

        // 文本框初始化
        jtextComm = TextComponent.getCommTextPanel();


        // 设置面板
        init_panel();
        init_frame();
    }
    //JPanel面板初始化
    private static void init_panel() {
        jpanelController = new JPanel();
        jpanelCommunication = new JPanel();

        //设置面板边框
        jpanelCommunication.setBorder(BorderFactory.createEtchedBorder());
        jpanelController.setBorder(BorderFactory.createEtchedBorder());
    }

    //frame初始化
    private void init_frame() {
        //添加布局
        setLayout(new GridBagLayout());
        GridBagConstraints s= new GridBagConstraints();//定义一个GridBagConstraints
        s.fill=GridBagConstraints.BOTH;
        s.gridx=0;
        s.gridy=0;
        s.gridwidth=0;
        s.gridheight=1;
        s.weightx=1;
        s.gridheight=1;
        add(jlabelController,s);
        s.gridx=0;
        s.gridy=1;
        add(jpanelCommunication,s);
//        add(jpanelController, setConstraints(0, 0, 1, 3, 1, 1));
//        add(jpanelCommunication, setConstraints(0, 1, 2, 3, 1, 1));
//        //面板布局
//        //菜单面板
//        jpanelController.setLayout(new GridBagLayout());
//        jpanelController.add(jcomboSelect, setConstraints(0, 0, 1, 1, 1, 1));
//        jpanelController.add(jbuttonComm, setConstraints(1, 0, 1, 1, 1, 1));
//        jpanelController.add(jlabelController,setConstraints(0,0,))
//        //排序面板
//        jpanelCommunication.setLayout(new GridBagLayout());
//        jpanelCommunication.add(jlabelComm, setConstraints(0, 0, 1, 2, 1, 1));
//        jpanelCommunication.add(jtextComm, setConstraints(0, 2, 2, 4, 0, 2));
    }


}
