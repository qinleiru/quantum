package com.view.component;

import javax.swing.*;

/**
 * 获取选择部分，并添加响应事件
 */
public class SelectComponent {
    //下拉组合框
    private static JComboBox comboBox;

    //获取JComboBox组合框
    public static JComboBox getComboBox(String[] items) {
        comboBox = new JComboBox();
        comboBox.setEditable(true);

        //为组合框添加Item
        for (int i = 0; i < items.length; i++) {
            comboBox.addItem(items[i]);
        }
        return comboBox;
    }

    //获取comboBox选择-单选
    public static String getComboBoxSelection() {
        String select = new String();
        //获取单选框选择的Item
        return select = (String) comboBox.getSelectedItem();
    }

}
