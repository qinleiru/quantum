package com.view.component;

import javax.swing.*;
import java.awt.*;

/**
 * 获取文本框组件并添加事件
 */
//todo：下方用于可视化的通信过程
public class TextComponent {
    //定义文本框
    public static JTextArea commText;

    //定义文本属性
    private static final int TEXT_ROWS = 28;
    private static final int TEXT_COLUMNS = 90;
    private static final Font songFont = new Font("宋体", Font.PLAIN, 12);

    //JText文本框 属性初始化
    public TextComponent() {
    }

    //设置通信显示内容——线程安全
    public static void setCommText(final String text) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                commText.append(text);
                commText.append("\r\n");
            }
        });
    }


    //获取通信文本框-滚动窗格
    public static JScrollPane getCommTextPanel() {
        commText = setText(commText);
        setCommText("等待通信........");
        return new JScrollPane(commText);
    }

    //设置文本框属性
    public static JTextArea setText(JTextArea textArea) {
        //初始化文本框
        textArea = new JTextArea(TEXT_ROWS, TEXT_COLUMNS);

        //设置字体格式
        textArea.setFont(songFont);

        //设置自动换行
        textArea.setLineWrap(true);

        return textArea;
    }
}