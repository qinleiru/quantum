package com.quantum.gate;


public class QuantumGate {
    //以下是pauli操作
    //单量子比特门，I门
    public static final double[][] Operator_I = new double[][]{{1, 0}, {0, 1}};
    //单量子比特门，X门
    public static final double[][] Operator_X = new double[][]{{0, 1}, {1, 0}};
    //单量子比特门，Y门
    public static final double[][] Operator_iY = new double[][]{{0, 1}, {-1, 0}};
    //单量子比特门，Z门
    public static final double[][] Operator_Z = new double[][]{{1, 0}, {0, -1}};
    //单量子比特门，H门
    public static final double[][] Operator_H = new double[][]{{Math.pow(2, -0.5), Math.pow(2, -0.5)}, {Math.pow(2, -0.5), -Math.pow(2, -0.5)}};

    //两量子比特门，CNOT门
    public static final double[][] Operator_CNOT = new double[][]{{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 0, 1}, {0, 0, 1, 0}};
    //两量子比特门，U操作，Bell态转换，经过U操作，|Ф>+态对应|00>
    public static final double[][] Operator_U=new double[][]{{Math.pow(2, -0.5),0,0,Math.pow(2, -0.5)},{0,Math.pow(2, -0.5),Math.pow(2, -0.5),0},{Math.pow(2, -0.5),0,0,-Math.pow(2, -0.5)},{0,Math.pow(2, -0.5),-Math.pow(2, -0.5),0}};

}
