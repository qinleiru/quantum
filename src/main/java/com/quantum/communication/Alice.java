package com.quantum.communication;

import com.quantum.gate.QuantumGate;
import com.quantum.measure.ProjectiveMeasure;
import com.quantum.oparate.QuantumOperation;
import com.quantum.state.*;

import java.util.ArrayList;

public class Alice{
    public static MultiState systemState;
    private DoubleState doubleState;
    private ClusterState clusterState1;
    private ClusterState clusterState2;
    private int highParticle;   //协议中的权限高的代理者个数
    private int lowParticle;    //协议中的权限低的代理者个数
    private int resultX;
    private int resultY;

    public Alice(int highParticle,int lowParticle){
        this.highParticle=highParticle;
        this.lowParticle=lowParticle;
    }

    public void process(){
        secret();
        prepareState();
        measure();
    }
    /**
     * 发送者准备秘密态用于代理者们的共享n
     */
    //todo：传送固定的两量子比特
//    @Override
    public void secret() {
        double [] doubles=new double[]{0.5,0.5,0.5,0.5};
        doubleState=new DoubleState(doubles);
        //设置两量子比特的名字
        doubleState.setParticlesName(1,"x");
        doubleState.setParticlesName(2,"y");
        /**
         * 用于测试的代码
         */
        System.out.println("Alice准备的秘密量子态为"+doubleState.showBinaryState());
    }

    /**
     * 对手中的粒子进行Bell态测量，并公布测量结果
     */
//    @Override
    public void measure() {
        //整个系统的初始状态为
        systemState=QuantumOperation.quantumTensor(QuantumOperation.quantumTensor(doubleState,clusterState1),clusterState2);
        //Alice对粒子x和粒子1，进行Bell态测量
        resultX=ProjectiveMeasure.measureBeseBell(systemState,"x","1");
        //Alice对粒子y和粒子5，进行Bell态测量
        resultY=ProjectiveMeasure.measureBeseBell(systemState,"y",(highParticle+lowParticle+1)+"");
    }
    /**
     * 准备两个多粒子纠缠簇态，用于构建量子信道
     */
    //todo:对于非最大纠缠态的制备还未实现
    public void prepareState(){
        //准备固定的两粒子簇态
        double []states=new double[]{0.5,0.5,0.5,-0.5};
        DoubleState prepareState=new DoubleState(states);
        //控制粒子1的粒子名
        prepareState.setParticlesName(1,"1");
        //控制粒子2的粒子名
        prepareState.setParticlesName(2,""+(highParticle+2));
        //准备初始态为0的两粒子纠缠态
        SingleState singleState= CommonState.Zero_State;
        singleState.setParticlesName(1,"2");
        MultiState multiState=QuantumOperation.quantumTensor(prepareState,singleState);
        //初始化权限高的代理者需要粒子
        for(int i=2;i<=highParticle;i++){
            singleState.setParticlesName(1,""+(i+1));
            multiState=QuantumOperation.quantumTensor(multiState,singleState);
        }
        //初始化权限低的代理者需要粒子
        for (int i=2;i<=lowParticle;i++){
            singleState.setParticlesName(1,""+(highParticle+1+i));
            multiState= QuantumOperation.quantumTensor(multiState,singleState);

        }
        //进行CNOT操作,高等级的用粒子1做控制门，低等级用粒子2做控制门
        for(int i=1;i<=highParticle;i++){
            QuantumOperation.quantumDoublePerform(multiState,"1",""+(i+1),QuantumGate.Operator_CNOT);
        }
        for (int i=2;i<=lowParticle;i++){
            QuantumOperation.quantumDoublePerform(multiState,""+(highParticle+2),""+(highParticle+1+i),QuantumGate.Operator_CNOT);

        }clusterState1=new ClusterState(multiState.getState(),multiState.getParticles());
        ArrayList<String> arrayList=multiState.getParticlesName();
        for(int i=0;i<arrayList.size();i++){
            clusterState1.setParticlesName(i+1,arrayList.get(i));
        }
        clusterState2=new ClusterState(clusterState1.getState(),clusterState1.getParticles());
        arrayList=clusterState1.getParticlesName();
        for(int i=0;i<arrayList.size();i++){
            clusterState2.setParticlesName(i+1,(Integer.parseInt(arrayList.get(i))+clusterState1.getParticles())+"");
        }
        /**
         * 测试
         */
        System.out.println("准备的第一个簇态为\n"+clusterState1.showBinaryState());
        clusterState1.showParticleName();
        System.out.println();

        System.out.println("准备的第二个簇态为\n"+clusterState2.showBinaryState());
        clusterState2.showParticleName();
        System.out.println();
    }

    public void send(Receiver receiver, String particle){
        receiver.receive(particle);
    }

    /**
     * 获取对X粒子进行Bell态测量的结果,提供访问方法
     * @return
     */
    public int getResultX() {
        return resultX;
    }

    /**
     * 获取对Y粒子进行Bell态测量的结果，提供访问方法
     * @return
     */
    public int getResultY() {
        return resultY;
    }
}
