package com.simulation.view;

import javax.swing.*;

/**
 * 获取选择部分，并添加响应事件
 */
public class RadioComponent {
    //进行单选的内容
    private static String[] channelType = {"理想信道", "噪声信道"};
    private static JRadioButton[] jrbs = null;
    //下拉组合框
    private static ButtonGroup buttonGroup;

    //获取单选按钮
    public static ButtonGroup getButtonGroup() {
        jrbs = new JRadioButton[channelType.length];
        //设置单选按钮的内容，并将第一个按钮设置为默认选项
        for (int i = 0; i < jrbs.length; i++) {
            boolean flag = ( i == 0 );
            jrbs[i] = new JRadioButton(channelType[i], flag);
            buttonGroup.add(jrbs[i]);
        }
        return buttonGroup;
    }

    //获取选择的内容
    public static String getComboBoxSelection() {
        String select = "";

        //获取单选框选择的Item
        for (JRadioButton jrb : jrbs) {
            if (jrb.isSelected()) {
                select = jrb.getText();
            }
        }
        return select;
    }
}
