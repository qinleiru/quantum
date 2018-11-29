package com.quantum.communication;

import com.quantum.gate.QuantumGate;
import com.quantum.measure.ProjectiveMeasure;
import com.quantum.oparate.MathOperation;
import com.quantum.state.DoubleState;
import com.quantum.state.MultiState;
import com.quantum.state.SingleState;
import com.simulation.view.TextComponent;

import java.text.SimpleDateFormat;

//下面的实例尝试实现量子隐形传态协议,信道中已知的量子比特已经进行过归一化处理
public class QuantumTeleportation {
    public static void run(TextComponent textArea){
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SingleState oneState=new SingleState();
        textArea.setCommText(df.format(System.currentTimeMillis())+" 发送者Alice共享的单量子比特为:"+oneState.showState());
        oneState.displayState();
        textArea.setCommText(df.format(System.currentTimeMillis())+" 发送者Alice准备Bell态构造量子信道");
        double[] bell = new double[]{Math.pow(2, -0.5), 0, 0, Math.pow(2, -0.5)};
        DoubleState twoState=new DoubleState(bell);
        textArea.setCommText(df.format(System.currentTimeMillis())+" 完成构造量子信道，将Bell态其中的一个粒子发送给接收者");
        //当前系统所处的整个量子态为
        //todo：在计算系统的张量积的过程中可能还会需要归一化
        double[] system = MathOperation.tensor(oneState.getState(), twoState.getState());
        MultiState systemState=new MultiState(system,3);
        //对发送者手中的粒子x以及粒子A进行Bell态测量
        textArea.setCommText(df.format(System.currentTimeMillis())+" 发送者Alice对手中的两个粒子进行测量");
        int pos[]=new int[]{1,2};
        int result=ProjectiveMeasure.measureBeseBell(systemState,pos);
        if (result==1) {
            System.out.println("Bell态测的结果为1");
            MathOperation.performOperator(systemState, 3, QuantumGate.Operator_I);
            double[] secret = new double[2];
            for (int i = 0; i < 2; i++) {
                secret[i] = systemState.getState()[i];
            }
            MathOperation.normalization(secret);
            SingleState secretState=new SingleState(secret);
            System.out.println("得到的量子秘密态为");
            secretState.displayState();
        }
        if (result==2) {
            System.out.println("Bell态测的结果为2");
            MathOperation.performOperator(systemState, 3, QuantumGate.Operator_X);
            double[] secret = new double[2];
            for (int i = 0; i < 2; i++) {
                secret[i] = systemState.getState()[i+2];
            }
            MathOperation.normalization(secret);
            SingleState secretState=new SingleState(secret);
            System.out.println("得到的量子秘密态为");
            secretState.displayState();
        }
        if (result==3) {
            System.out.println("Bell态测的结果为3");
            MathOperation.performOperator(systemState, 3, QuantumGate.Operator_Z);
            double[] secret = new double[2];
            for (int i = 0; i < 2; i++) {
                secret[i] = systemState.getState()[i+4];
            }
            MathOperation.normalization(secret);
            SingleState secretState=new SingleState(secret);
            System.out.println("得到的量子秘密态为");
            secretState.displayState();
        }
        if (result==4) {
            System.out.println("Bell态测的结果为4");
            MathOperation.performOperator(systemState, 3, QuantumGate.Operator_iY);
            double[] secret = new double[2];
            for (int i = 0; i < 2; i++) {
                secret[i] = systemState.getState()[i+6];
            }
            MathOperation.normalization(secret);
            SingleState secretState=new SingleState(secret);
            System.out.println("得到的量子秘密态为");
            secretState.displayState();
        }
    }
}
