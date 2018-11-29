package com.simulation.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * 获取按钮组件，并添加响应事件
 */
public class ButtonComponent {
    private static JButton commButton;

    private static String sortFunction = "Default";

    //对文本框的操作
    private static TextComponent textArea = new TextComponent();

    //初始化
    public ButtonComponent() {
    }

    /**
     * 获取"通信"按钮 并 添加响应事件
     */
    public static JButton getCommButton() {
        commButton = new JButton("开始通信");
        commButton.addActionListener(e -> {
            //开启一个通信线程
            Runnable r = () -> {
                try {
                    new SimulationExecutors();
                    Thread.sleep(500);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            };
            Thread thread = new Thread(r);
            thread.start();
        });
        return commButton;
    }
}
