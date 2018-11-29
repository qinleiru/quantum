package com.quantum.communication;

import com.quantum.gate.QuantumGate;
import com.quantum.measure.ProjectiveMeasure;
import com.quantum.oparate.MathOperation;
import com.quantum.state.*;
import com.simulation.view.TextComponent;

import java.awt.*;

//此部分实现多方分层协议2010年Wang等人提出的,此协议有三个代理者，一个发送者.
public class HQIS {
    public static void run(TextComponent textArea){
        //有三个代理者，一个发送者,发送者Alice，代理者Bob，Charlie，David，其中David的权限高的代理者
        //首先构造使用的四粒子纠缠态
        double[]state0=CommonState.Zero_State.getState();
        double[]state1=CommonState.One_State.getState();
        double[] base1= MathOperation.tensor(new double[][]{state0,state0,state0,state0});
        double[] base2=MathOperation.multiple(MathOperation.tensor(new double[][]{state0,state0,state1,state1}),-1);
        double[] base3=MathOperation.multiple(MathOperation.tensor(new  double[][]{state0,state1,state0,state1}),-1);
        double[] base4=MathOperation.tensor(new double[][]{state0,state1,state1,state0});
        double[] base5=MathOperation.tensor(new double[][]{state1,state0,state0,state1});
        double[] base6=MathOperation.tensor(new double[][]{state1,state0,state1,state0});
        double[] base7=MathOperation.tensor(new double[][]{state1,state1,state0,state0});
        double[] base8=MathOperation.tensor(new double[][]{state1,state1,state1,state1});
        double[] state=MathOperation.add(new double[][]{base1,base2,base3,base4,base5,base6,base7,base8});
        state=MathOperation.multiple(state,0.5*Math.pow(2,-0.5));
        ClusterState clusterState=new ClusterState(state,4);
        //随机产生一个要传送的单量子比特
        SingleState singleState=new SingleState(new double[]{Math.pow(2,-0.5),Math.pow(2,-0.5)});
        textArea.setCommText("要传送的单量子比特为");
        singleState.displayState();
        //整个系统的量子态为
        MultiState systemState=new MultiState(MathOperation.tensor(singleState.getState(),clusterState.getState()),singleState.getParticles()+clusterState.getParticles());
//        System.out.println("整个系统的态为");
//        systemState.displayState();
        //对发送者Alice手中的两个粒子进行Bell态的测量
        int[]pos={1,2};
        int resultAlice=ProjectiveMeasure.measureBeseBell(systemState,pos);
        //现在模拟代理者David来恢复秘密消息，Bob对手中的粒子进行测量
//        System.out.println("Bell态测量完成之后系统的态为");
//        systemState.displayState();
        System.out.println("Alice的Bell态测量结果为"+resultAlice);
        if(resultAlice==1){
            //此时代理者手中的粒子坍缩到，代理者们手中的粒子有3个
            double[] proxy=new double[8];
            for (int i=0;i<8;i++){
                proxy[i]=systemState.getState()[i];
            }
            MultiState proxyState=new MultiState(proxy,3);
            System.out.println("代理者手中3个粒子，此时的系统态为");
            proxyState.displayState();
            int resultBob=ProjectiveMeasure.measureBaseX(proxyState,1);
            System.out.println("Bob的测量结果为"+resultBob);
            if (resultBob==0){
                MathOperation.performOperator(proxyState,3, QuantumGate.Operator_H);
                MathOperation.performOperator(proxyState,3,QuantumGate.Operator_I);
                double []result=new double[2];
                for (int i=0;i<2;i++){
                    result[i]=proxyState.getState()[i];
                }
                MathOperation.normalization(result);
                SingleState resultState=new SingleState(result);
                System.out.println("秘密量子比特为");
                resultState.displayState();
            }
            if (resultBob==1){
                //todo:前面的符号可能需要解决
                MathOperation.performOperator(proxyState,3, QuantumGate.Operator_H);
                MathOperation.performOperator(proxyState,3,QuantumGate.Operator_iY);
                double []result=new double[2];
                for (int i=0;i<2;i++){
                    result[i]=proxyState.getState()[i+6];
                }
                MathOperation.normalization(result);
                SingleState resultState=new SingleState(result);
                System.out.println("秘密量子比特为");
                resultState.displayState();
            }
        }
        if(resultAlice==2){
            //此时代理者手中的粒子坍缩到，代理者们手中的粒子有3个
            double[] proxy=new double[8];
            for (int i=0;i<8;i++){
                proxy[i]=systemState.getState()[i+8];
            }
            MultiState proxyState=new MultiState(proxy,3);
            System.out.println("代理者手中3个粒子，此时的系统态为");
            proxyState.displayState();
            int resultBob=ProjectiveMeasure.measureBaseX(proxyState,1);
            System.out.println("Bob的测量结果为"+resultBob);
            if (resultBob==0){
                MathOperation.performOperator(proxyState,3, QuantumGate.Operator_H);
                MathOperation.performOperator(proxyState,3,QuantumGate.Operator_X);
                double []result=new double[2];
                for (int i=0;i<2;i++){
                    result[i]=proxyState.getState()[i];
                }
                MathOperation.normalization(result);
                SingleState resultState=new SingleState(result);
                System.out.println("秘密量子比特为");
                resultState.displayState();
            }
            if (resultBob==1){
                MathOperation.performOperator(proxyState,3, QuantumGate.Operator_H);
                MathOperation.performOperator(proxyState,3,QuantumGate.Operator_Z);
                double []result=new double[2];
                for (int i=0;i<2;i++){
                    result[i]=proxyState.getState()[i+6];
                }
                MathOperation.normalization(result);
                SingleState resultState=new SingleState(result);
                System.out.println("秘密量子比特为");
                resultState.displayState();
            }
        }
        if(resultAlice==3){
            //此时代理者手中的粒子坍缩到，代理者们手中的粒子有3个
            double[] proxy=new double[8];
            for (int i=0;i<8;i++){
                proxy[i]=systemState.getState()[i+16];
            }
            MultiState proxyState=new MultiState(proxy,3);
            System.out.println("代理者手中3个粒子，此时的系统态为");
            proxyState.displayState();
            int resultBob=ProjectiveMeasure.measureBaseX(proxyState,1);
            System.out.println("Bob的测量结果为"+resultBob);
            if (resultBob==0){
                MathOperation.performOperator(proxyState,3, QuantumGate.Operator_H);
                MathOperation.performOperator(proxyState,3,QuantumGate.Operator_Z);
                double []result=new double[2];
                for (int i=0;i<2;i++){
                    result[i]=proxyState.getState()[i];
                }
                MathOperation.normalization(result);
                SingleState resultState=new SingleState(result);
                System.out.println("秘密量子比特为");
                resultState.displayState();
            }
            if (resultBob==1){
                MathOperation.performOperator(proxyState,3, QuantumGate.Operator_H);
                MathOperation.performOperator(proxyState,3,QuantumGate.Operator_X);
                double []result=new double[2];
                for (int i=0;i<2;i++){
                    result[i]=proxyState.getState()[i+6];
                }
                MathOperation.normalization(result);
                SingleState resultState=new SingleState(result);
                System.out.println("秘密量子比特为");
                resultState.displayState();
            }
        }

        if(resultAlice==4){
            //此时代理者手中的粒子坍缩到，代理者们手中的粒子有3个
            double[] proxy=new double[8];
            for (int i=0;i<8;i++){
                proxy[i]=systemState.getState()[i+24];
            }
            MultiState proxyState=new MultiState(proxy,3);
            System.out.println("代理者手中3个粒子，此时的系统态为");
            proxyState.displayState();
            int resultBob=ProjectiveMeasure.measureBaseX(proxyState,1);
            System.out.println("Bob的测量结果为"+resultBob);
            if (resultBob==0){
                MathOperation.performOperator(proxyState,3, QuantumGate.Operator_H);
                MathOperation.performOperator(proxyState,3,QuantumGate.Operator_iY);
                double []result=new double[2];
                for (int i=0;i<2;i++){
                    result[i]=proxyState.getState()[i];
                }
                MathOperation.normalization(result);
                SingleState resultState=new SingleState(result);
                System.out.println("秘密量子比特为");
                resultState.displayState();
            }
            if (resultBob==1){
                MathOperation.performOperator(proxyState,3, QuantumGate.Operator_H);
                MathOperation.performOperator(proxyState,3,QuantumGate.Operator_I);
                double []result=new double[2];
                for (int i=0;i<2;i++){
                    result[i]=proxyState.getState()[i+6];
                }
                MathOperation.normalization(result);
                SingleState resultState=new SingleState(result);
                System.out.println("秘密量子比特为");
                resultState.displayState();
            }
        }
    }
}
