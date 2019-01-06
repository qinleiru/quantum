package com.view.simulation;

import javax.swing.*;
import java.awt.*;

public class QuantumSimulation {
    public static void main(String[] args){
        EventQueue.invokeLater(() -> {
            JFrame frame = new SimulationFrame();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setTitle("分层量子信息拆分协议仿真");
            frame.setVisible(true);
        });
    }
}
