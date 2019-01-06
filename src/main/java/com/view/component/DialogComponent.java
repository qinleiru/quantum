package com.view.component;

import javax.swing.*;
import java.awt.*;

//自定义弹出对话框
public class DialogComponent extends JDialog {
    //第一个参数为拥有者，第二个参数为显示的String内容
    public DialogComponent(JFrame owner, String content) {
        super(owner, "提示", true);
        add(new JLabel(content), BorderLayout.CENTER);
        JPanel panel = new JPanel();
        JButton ok = new JButton("OK");
        //点击ok时，关闭对话框
        ok.addActionListener(e -> setVisible(false));
        panel.add(ok);
        add(panel, BorderLayout.SOUTH);
        setSize(150, 100);
        setLocation(400, 400);
        setVisible(true);
    }
}