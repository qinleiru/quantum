package com.simulation.view;

import javax.swing.*;

/**
 * 获取选择部分，并添加响应事件
 */
//todo：目前支持单选
public class SelectComponent {
    //量子通信协议
    private static String[] sortType = {"量子隐形传态协议","分层量子信息拆分协议"};
    //下拉组合框
    private static JComboBox comboBox;

    //获取JComboBox组合框
    public static JComboBox getComboBox() {
        comboBox = new JComboBox();
        comboBox.setEditable(true);

        //为组合框添加Item
        for (int i = 0; i < sortType.length; i++) {
            comboBox.addItem(sortType[i]);
        }
        return comboBox;
    }


    //获取comboBox选择-单选
    public static String getComboBoxSelection() {
        String select = new String();

        //获取单选框选择的Item
        return select = (String) comboBox.getSelectedItem();
    }

    //获取通信协议的名称
    public static String[] getSortType() {
        return sortType;
    }
}
