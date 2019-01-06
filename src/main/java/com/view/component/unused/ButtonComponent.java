//package com.view.component;
//
//import javax.swing.*;
//
///**
// * 获取按钮组件，并添加响应事件
// */
//public class ButtonComponent {
//    private static JButton commButton;
//    /**
//     * 获取"通信"按钮 并 添加响应事件
//     */
//    public static JButton getCommButton(String button) {
//        commButton = new JButton(button);
//        commButton.addActionListener(e -> {
//            //开启一个通信线程
//            Runnable r = () -> {
//                try {
//                    new DoSimulation();
//                    Thread.sleep(500);
//                } catch (Exception e1) {
//                    e1.printStackTrace();
//                }
//            };
//            Thread thread = new Thread(r);
//            thread.start();
//        });
//        return commButton;
//    }
//}
