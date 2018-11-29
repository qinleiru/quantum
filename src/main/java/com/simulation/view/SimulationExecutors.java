package com.simulation.view;

public class SimulationExecutors {
    private  String protocolType;
    private  DoComm newCommThread;

    private  TextComponent textArea;
    public SimulationExecutors(){
        //获取通信的协议
        protocolType = SelectComponent.getComboBoxSelection();
        textArea = new TextComponent();
        try {
            System.out.println("选择的通信方式为：" + protocolType);

            //将文本框切换到排序显示模式
            TextComponent.setCommText("--------开始通信--------");
            TextComponent.setCartoonText("--------开始通信--------");

            newCommThread = new DoComm(protocolType);

            //将结果保存在SortFile中

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
