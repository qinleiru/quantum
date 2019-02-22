package com.view.simulation;

import com.view.component.ExGridBagConstraints;
import com.view.component.RadioComponent;
import com.view.component.TextComponent;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * 对应通信模式中的通信面板
 */
public class CommPanel extends JPanel {
    /*
        通信模式下对应的组件
     */
    private static JPanel panelController;       //控制区
    private static JPanel panelCommunication;    //通信过程

    private static JLabel labelController;     //控制区
    private static JLabel labelCommunication;  //通信过程
    private static JLabel labelProtocol;      //协议类型
    private static JLabel labelAttack;         //攻击类型
    private static JLabel labelAgent;          //要恢复秘密消息的代理者
    private static JLabel labelSenderSecret;         //发送者发送的秘密消息
    private static JLabel labelAgentSecret;          //代理者恢复的秘密消息

    private static JComboBox<String> comboProtocols;    //协议
    private static JComboBox<String> comboAttacks;       //攻击
    private static JComboBox<String> comboAgents;        //代理者
    private static JRadioButton radioButtonIdeal;   //理想信道
    private static JRadioButton radioButtonNoise;   //噪声信道
    private static ButtonGroup radioChannel;    //信道

    private static JTextField textFieldSendSecret;   //发送者发送的秘密消息
    private static JTextField textFieldAgentSecret;  //恢复者收到的秘密消息
    private static JButton buttonStart;         //开始按钮

    private static JScrollPane textCommunication;  //通信过程
    /*
       初始化组件，相应组件添加事件
     */
    public CommPanel(){
        /*
           标签进行初始化
         */
        labelController=new JLabel("控制区：");
        labelCommunication = new JLabel("通信过程：");
        labelProtocol=new JLabel("协议类型：");
        labelAttack=new JLabel("攻击类型：");
        labelAgent=new JLabel("恢复秘密：");
        labelSenderSecret=new JLabel("发送的秘密消息：");
        labelAgentSecret=new JLabel("恢复的秘密消息：");
        textFieldAgentSecret=new JTextField();
        textFieldSendSecret=new JTextField();
        radioButtonIdeal=new JRadioButton("理想信道",true);
        radioButtonNoise=new JRadioButton("噪声信道",false);
        ArrayList<JRadioButton> jRadioButtons=new ArrayList<>();
        jRadioButtons.add(radioButtonNoise);
        jRadioButtons.add(radioButtonIdeal);
        radioChannel= RadioComponent.getButtonGroup(jRadioButtons);

        // 协议初始化
        String[] protocols=new String[]{"概率型分层量子信息拆分协议","确定型分层量子信息拆分协议"};
        comboProtocols=new JComboBox<String>(protocols);

        //攻击初始化
        String[] attacks=new String[]{"无攻击","纠缠测量攻击","修改攻击","截获重发攻击"};
        comboAttacks = new JComboBox<String>(attacks);;

        //代理者初始化
        String[] agents=new String[]{"Bob","Charlie","David"};
        comboAgents =new JComboBox<String>(agents);

        // 开始按钮初始化,并添加事件
        buttonStart =new JButton("开始");
        buttonStart.addActionListener(e -> {
            //开启一个通信线程
            Runnable r = () -> {
                try {
                    //进行执行
                    textFieldAgentSecret.setText("");
                    DoSimulation simulation=new DoSimulation((String)comboProtocols.getSelectedItem(),(String)comboAttacks.getSelectedItem(),(String)comboAgents.getSelectedItem(),textFieldSendSecret.getText());
                    textFieldAgentSecret.setText(simulation.recieveSercet);
                    Thread.sleep(500);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            };
            Thread thread = new Thread(r);
            thread.start();
        });

        // 滚动窗口初始化
        textCommunication = TextComponent.getCommTextPanel();

        // 设置面板
        init_panel();
        init_frame();
    }
    //JPanel面板初始化
    private static void init_panel() {
        panelController = new JPanel();
        panelCommunication = new JPanel();

        //设置面板边框
        panelController.setBorder(BorderFactory.createEtchedBorder());
        panelCommunication.setBorder(BorderFactory.createEtchedBorder());
    }

    //frame初始化
    private void init_frame() {
        //通信模式下的布局
        this.setLayout(new GridBagLayout());
        this.add(labelController,new ExGridBagConstraints(0,0,2,1).setFill(ExGridBagConstraints.BOTH).setIpad(200,10).setWeight(100,0));
        this.add(panelController,new ExGridBagConstraints(0,1,2,1).setFill(ExGridBagConstraints.BOTH).setIpad(200,10).setWeight(100, 0));
        this.add(labelCommunication,new ExGridBagConstraints(0,2,2,1).setFill(ExGridBagConstraints.BOTH).setIpad(200,10).setWeight(100, 0));
        this.add(panelCommunication,new ExGridBagConstraints(0,3,2,1).setFill(ExGridBagConstraints.BOTH).setIpad(200,400).setWeight(100, 0));
        //控制区面板的布局
        panelController.setLayout(new GridBagLayout());
        panelController.add(labelProtocol,new ExGridBagConstraints(0,0).setFill(ExGridBagConstraints.BOTH).setWeight(100,0).setInsets(5,10,5,10));
        panelController.add(labelAttack,new ExGridBagConstraints(0,1).setFill(ExGridBagConstraints.BOTH).setInsets(5,10,5,10));
        panelController.add(labelSenderSecret,new ExGridBagConstraints(0,2).setFill(ExGridBagConstraints.BOTH).setWeight(100,0).setInsets(5,10,5,10));
        panelController.add(labelAgentSecret,new ExGridBagConstraints(0,3).setFill(ExGridBagConstraints.BOTH).setWeight(100,0).setInsets(5,10,5,10));
        panelController.add(comboProtocols,new ExGridBagConstraints(1,0,2,1).setFill(ExGridBagConstraints.BOTH).setWeight(100,0).setInsets(5,10,5,10));
        panelController.add(comboAttacks,new ExGridBagConstraints(1,1).setFill(ExGridBagConstraints.BOTH).setInsets(5,10,5,10));
        panelController.add(textFieldSendSecret,new ExGridBagConstraints(1,2,2,1).setFill(ExGridBagConstraints.BOTH).setInsets(5,10,5,10));
        panelController.add(textFieldAgentSecret,new ExGridBagConstraints(1,3,2,1).setFill(ExGridBagConstraints.BOTH).setInsets(5,10,5,10));
        //todo:单选按钮
        int i=3;
//        while(radioChannel.getElements().hasMoreElements()){
//            panelController.add(radioChannel.getElements().nextElement(),new ExGridBagConstraints(i++,0).setFill(ExGridBagConstraints.BOTH).setInsets(5,10,5,10));
//        }
        panelController.add(radioButtonIdeal,new ExGridBagConstraints(3,0).setFill(ExGridBagConstraints.BOTH).setInsets(5,10,5,10));
        panelController.add(radioButtonNoise,new ExGridBagConstraints(4,0).setFill(ExGridBagConstraints.BOTH).setInsets(5,10,5,10));
        panelController.add(labelAgent,new ExGridBagConstraints(3,2).setFill(ExGridBagConstraints.BOTH).setInsets(5,10,5,10));
        panelController.add(comboAgents,new ExGridBagConstraints(4,2,1,1).setFill(ExGridBagConstraints.BOTH).setInsets(5,10,5,10));
        panelController.add(buttonStart,new ExGridBagConstraints(3,3,2,1).setFill(ExGridBagConstraints.BOTH).setInsets(5,50,5,50));
        //通信过程面板的布局
        panelCommunication.add(textCommunication,new ExGridBagConstraints(0,0,1,1).setFill(ExGridBagConstraints.BOTH).setInsets(10,10,10,10));


//        panelController.add(comboProtocols,new ExGridBagConstraints(0,1,3,1).setFill(ExGridBagConstraints.BOTH));
//        panelController.add(comboAttacks,new ExGridBagConstraints(1,1,3,1).setFill(ExGridBagConstraints.BOTH));
//        panelController.add(textFieldSendSecret,new ExGridBagConstraints(2,1).setFill(ExGridBagConstraints.BOTH));
//        panelController.add(labelAgent,new ExGridBagConstraints(2,2).setFill(ExGridBagConstraints.BOTH));
//        panelController.add(comboAgents,new ExGridBagConstraints(2,3).setFill(ExGridBagConstraints.BOTH));
//        panelController.add(textFieldAgentSecret,new ExGridBagConstraints(3,1).setFill(ExGridBagConstraints.BOTH));
//        panelController.add(buttonStart,new ExGridBagConstraints(3,2,2,1).setFill(ExGridBagConstraints.BOTH));




//        this.add()
//        GridBagConstraints s= new GridBagConstraints();//定义一个GridBagConstraints
//        s.fill=GridBagConstraints.BOTH;
//        s.gridx=0;
//        s.gridy=0;
//        s.gridwidth=0;
//        s.gridheight=1;
//        s.weightx=1;
//        s.gridheight=1;
//        add(jlabelController,s);
//        s.gridx=0;
//        s.gridy=1;
//        add(jpanelCommunication,s);
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

    //添加事件
}
