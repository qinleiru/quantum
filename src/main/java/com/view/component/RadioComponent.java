package com.view.component;

import javax.swing.*;
import java.util.ArrayList;

/**
 * 获取选择部分，并添加响应事件
 */
public class RadioComponent {
    //下拉组合框
    private static ButtonGroup buttonGroup;
    private static ArrayList<JRadioButton> jRadioButtons;

    //获取单选按钮
    public static ButtonGroup getButtonGroup(ArrayList<JRadioButton> arrayList) {
        //设置单选按钮的内容，并将第一个按钮设置为默认选项
        buttonGroup=new ButtonGroup();
        jRadioButtons=arrayList;
        for (int i = 0; i < jRadioButtons.size(); i++) {
            buttonGroup.add(jRadioButtons.get(i));
        }
        return buttonGroup;
    }

    //获取选择的内容
    public static String getComboBoxSelection() {
        String select = "";
        //获取单选框选择的Item
        for (JRadioButton jrb : jRadioButtons) {
            if (jrb.isSelected()) {
                select = jrb.getText();
            }
        }
        return select;
    }
}
