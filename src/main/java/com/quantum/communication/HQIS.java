package com.quantum.communication;

import com.quantum.gate.QuantumGate;
import com.quantum.measure.ProjectiveMeasure;
import com.quantum.oparate.MathOperation;
import com.quantum.oparate.QuantumOperation;
import com.quantum.state.*;
import com.quantum.tools.QuantumState;

import java.util.Scanner;

//此部分实现多方分层协议2010年Wang等人提出的,此协议有三个代理者，一个发送者.
//有三个代理者，一个发送者,发送者Alice，代理者Bob，Charlie，David，其中David的权限高的代理者
public class HQIS {
    public static void run(){
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
        clusterState.setParticlesName(1,"A");
        clusterState.setParticlesName(2,"B");
        clusterState.setParticlesName(3,"C");
        clusterState.setParticlesName(4,"D");
        //量子信道需要的四粒子簇态为
//        System.out.println("构成量子信道的四粒子簇态为"+clusterState.showBinaryState());
//        clusterState.showParticleName();
//        System.out.println();
        //随机产生一个要传送的单量子比特
        SingleState singleState=new SingleState();
        singleState.setParticlesName(1,"S");
        System.out.println("要传送的秘密量子比特为"+singleState.showBinaryState());
        singleState.showParticleName();
        System.out.println();
        //整个系统的量子态为
        MultiState systemState= QuantumOperation.quantumTensor(singleState,clusterState);
//        System.out.println("Bell态测量之前系统的态为"+systemState.showBinaryState());
//        systemState.showParticleName();
//        System.out.println();
        //对发送者Alice手中的两个粒子进行Bell态的测量
        int resultAlice=ProjectiveMeasure.measureBeseBell(systemState,"S","A");
        //现在模拟代理者David来恢复秘密消息，Bob对手中的粒子进行测量
        System.out.println("Alice的Bell态测量结果为"+resultAlice);
//        System.out.println("Bell态测量完成之后系统的态为"+systemState.showBinaryState());
//        systemState.showParticleName();
//        System.out.println();
//        System.out.println("请输入要选择权限高的代理者恢复秘密消息输入，权限低的代理者恢复秘密消息输入2");
//        Scanner scanner=new Scanner(System.in);
        int select=2;
        if(select==1) {
            //下面权限低的代理者Bob或Alice进行单粒子测量
            int resultBob = ProjectiveMeasure.measureBaseX(systemState, "B");
            System.out.println("Bob的测量结果为" + resultBob);
            System.out.println("所有的测量之后系统此时的态为" + systemState.showBinaryState());
            systemState.showParticleName();
            System.out.println();
            //根据测量结果David对手中的粒子进行相应的操作
            if (resultAlice == 2) {
                if (resultBob == 0) {
                    QuantumOperation.quantumSinglePerform(systemState, "D", QuantumGate.Operator_H);
                    System.out.println("经过H态的测量之后系统的态为" + systemState.showBinaryState());
                    systemState.showParticleName();
                    System.out.println();
                    QuantumOperation.quantumSinglePerform(systemState, "D", QuantumGate.Operator_I);
                }
                if (resultBob == 1) {
                    QuantumOperation.quantumSinglePerform(systemState, "D", QuantumGate.Operator_H);
                    System.out.println("经过H态的测量之后系统的态为" + systemState.showBinaryState());
                    systemState.showParticleName();
                    System.out.println();
                    QuantumOperation.quantumSinglePerform(systemState, "D", QuantumGate.Operator_iY);
                }
            }
            if (resultAlice == 1) {
                if (resultBob == 0) {
                    QuantumOperation.quantumSinglePerform(systemState, "D", QuantumGate.Operator_H);
                    System.out.println("经过H态的测量之后系统的态为" + systemState.showBinaryState());
                    systemState.showParticleName();
                    System.out.println();
                    QuantumOperation.quantumSinglePerform(systemState, "D", QuantumGate.Operator_X);
                }
                if (resultBob == 1) {
                    QuantumOperation.quantumSinglePerform(systemState, "D", QuantumGate.Operator_H);
                    System.out.println("经过H态的测量之后系统的态为" + systemState.showBinaryState());
                    systemState.showParticleName();
                    System.out.println();
                    QuantumOperation.quantumSinglePerform(systemState, "D", QuantumGate.Operator_Z);
                }
            }
            if (resultAlice == 4) {
                if (resultBob == 0) {
                    QuantumOperation.quantumSinglePerform(systemState, "D", QuantumGate.Operator_H);
                    System.out.println("经过H态的测量之后系统的态为" + systemState.showBinaryState());
                    systemState.showParticleName();
                    System.out.println();
                    QuantumOperation.quantumSinglePerform(systemState, "D", QuantumGate.Operator_Z);
                }
                if (resultBob == 1) {
                    QuantumOperation.quantumSinglePerform(systemState, "D", QuantumGate.Operator_H);
                    System.out.println("经过H态的测量之后系统的态为" + systemState.showBinaryState());
                    systemState.showParticleName();
                    System.out.println();
                    QuantumOperation.quantumSinglePerform(systemState, "D", QuantumGate.Operator_X);
                }
            }
            if (resultAlice == 3) {
                if (resultBob == 0) {
                    QuantumOperation.quantumSinglePerform(systemState, "D", QuantumGate.Operator_H);
                    System.out.println("经过H态的测量之后系统的态为" + systemState.showBinaryState());
                    systemState.showParticleName();
                    System.out.println();
                    QuantumOperation.quantumSinglePerform(systemState, "D", QuantumGate.Operator_iY);
                }
                if (resultBob == 1) {
                    QuantumOperation.quantumSinglePerform(systemState, "D", QuantumGate.Operator_H);
                    System.out.println("经过H态的测量之后系统的态为" + systemState.showBinaryState());
                    systemState.showParticleName();
                    System.out.println();
                    QuantumOperation.quantumSinglePerform(systemState, "D", QuantumGate.Operator_I);
                }
            }

            System.out.println("所有的测量以及操作都结束之后系统此时的态为" + systemState.showBinaryState());
            systemState.showParticleName();
            System.out.println();
            QuantumOperation.quantumSinglePerform(systemState, "C", QuantumGate.Operator_H);
            System.out.println("临时用于测试" + systemState.showBinaryState());
            systemState.showParticleName();
            System.out.println();
            double[] secret = getOwnState(systemState, "D");
            MathOperation.normalization(secret);
            SingleState secretState = new SingleState(secret);
            secretState.setParticlesName(1, "D");
            System.out.println("得到的量子秘密态为" + secretState.showBinaryState());
        }
        if(select==2){
            //权限低的代理者来恢复秘密消息，假设权限低的代理者Bob来恢复秘密消息
            int resultCharlie=ProjectiveMeasure.measureBaseZ(systemState,"C");
            System.out.println("Charlie的测量结果为" + resultCharlie);
            System.out.println("所有的测量之后系统此时的态为" + systemState.showBinaryState());
            systemState.showParticleName();
            System.out.println();
            int resultDavid=ProjectiveMeasure.measureBaseZ(systemState,"D");
            System.out.println("David的测量结果为" + resultDavid);
            System.out.println("所有的测量之后系统此时的态为" + systemState.showBinaryState());
            systemState.showParticleName();
            System.out.println();
            if(resultAlice==2){
                if (resultCharlie==0&&resultDavid==0){
                    QuantumOperation.quantumSinglePerform(systemState,"B",QuantumGate.Operator_X);
                }
                if (resultCharlie==0&&resultDavid==1){
                    QuantumOperation.quantumSinglePerform(systemState,"B",QuantumGate.Operator_Z);
                }
                if (resultCharlie==1&&resultDavid==0){
                    QuantumOperation.quantumSinglePerform(systemState,"B",QuantumGate.Operator_I);
                }
                if (resultCharlie==1&&resultDavid==1){
                    QuantumOperation.quantumSinglePerform(systemState,"B",QuantumGate.Operator_iY);

                }
            }
            if(resultAlice==1){
                if (resultCharlie==0&&resultDavid==0){
                    QuantumOperation.quantumSinglePerform(systemState,"B",QuantumGate.Operator_I);
                }
                if (resultCharlie==0&&resultDavid==1){
                    QuantumOperation.quantumSinglePerform(systemState,"B",QuantumGate.Operator_iY);
                }
                if (resultCharlie==1&&resultDavid==0){
                    QuantumOperation.quantumSinglePerform(systemState,"B",QuantumGate.Operator_X);
                }
                if (resultCharlie==1&&resultDavid==1){
                    QuantumOperation.quantumSinglePerform(systemState,"B",QuantumGate.Operator_Z);

                }
            }
            if(resultAlice==4){
                if (resultCharlie==0&&resultDavid==0){
                    QuantumOperation.quantumSinglePerform(systemState,"B",QuantumGate.Operator_iY);
                }
                if (resultCharlie==0&&resultDavid==1){
                    QuantumOperation.quantumSinglePerform(systemState,"B",QuantumGate.Operator_I);
                }
                if (resultCharlie==1&&resultDavid==0){
                    QuantumOperation.quantumSinglePerform(systemState,"B",QuantumGate.Operator_Z);
                }
                if (resultCharlie==1&&resultDavid==1){
                    QuantumOperation.quantumSinglePerform(systemState,"B",QuantumGate.Operator_X);

                }
            }
            if(resultAlice==3){
                if (resultCharlie==0&&resultDavid==0){
                    QuantumOperation.quantumSinglePerform(systemState,"B",QuantumGate.Operator_Z);
                }
                if (resultCharlie==0&&resultDavid==1){
                    QuantumOperation.quantumSinglePerform(systemState,"B",QuantumGate.Operator_X);
                }
                if (resultCharlie==1&&resultDavid==0){
                    QuantumOperation.quantumSinglePerform(systemState,"B",QuantumGate.Operator_iY);
                }
                if (resultCharlie==1&&resultDavid==1){
                    QuantumOperation.quantumSinglePerform(systemState,"B",QuantumGate.Operator_I);

                }
            }
            System.out.println("所有的测量以及操作执行之后系统此时的态为" + systemState.showBinaryState());
            systemState.showParticleName();
            System.out.println();
            double[] secret = getOwnState(systemState, "B");
            MathOperation.normalization(secret);
            SingleState secretState = new SingleState(secret);
            secretState.setParticlesName(1, "B");
            System.out.println("得到的量子秘密态为" + secretState.showBinaryState());
        }
    }
    public static double[] getOwnState(QuantumState quantumState, String particle){
        int index=quantumState.getParticlesName().indexOf(particle);
        double[] result=new double[2];
        int num=quantumState.getParticles();
        for (int i=0;i<Math.pow(2,num);i++){
            if (isBitZero(i,index+1,num)){
                result[0]+=quantumState.getState()[i];
            }
            else
                result[1]+=quantumState.getState()[i];
        }
        return result;
    }
    public static boolean isBitZero(int decNum,int pos,int particles){
        String binStr = "";
        for(int i= particles-1;i>=0;i--) {
            binStr +=(decNum>>i)&1;
        }
        return binStr.charAt(pos-1)=='0';
    }
//    public static void main(String [] args){
//        run();
//    }
}
