package com.quantum.communication;

import com.quantum.measure.Measures;
import com.quantum.measure.ProjectiveMeasure;
import com.quantum.oparate.QuantumOperation;
import com.quantum.state.DoubleState;
import com.quantum.tools.QuantumState;

import java.util.ArrayList;

//todo:此协议中的每个代理者手中有两个粒子
public class Proxy implements Receiver {
    //接受者接收者收到的粒子
    private ArrayList<String> particleName=new ArrayList<String>();
    private DoubleState doubleState;
    /**
     * 代理者接收发送者发送过来的粒子
     * @param particle
     */
    @Override
    public void receive(String particle) {
        particleName.add(particle);
    }

    /**
     * 代理者对手中的粒子进行测量
     * @param particle
     * @param measures
     */
    //todo：协议中只单粒子的测量
    @Override
    public int measure(String particle, Measures measures) {
        int index=measures.getIndex();
        int result=0;
        switch (index){
            case 1:
                result=ProjectiveMeasure.measureBaseX(Alice.systemState,particle);
                break;
            case 2:
                result=ProjectiveMeasure.measureBaseZ(Alice.systemState,particle);
                break;
        }
        return result;
    }
    //对手中的粒子进行操作
    public  void perform(String particle,double operator[][]){
        QuantumOperation.quantumSinglePerform(Alice.systemState,particle,operator);
    }

    //获取手中粒子的态
    public DoubleState getOwnState(QuantumState quantumState){
        //代理者拥有的粒子1 的名称
        String particle1=particleName.get(0);
        //代理者拥有的粒子2的名称
        String particle2=particleName.get(1);
        int particle=quantumState.getParticles();//获取系统态的粒子数
        //交换两个粒子在系统态的位置
        QuantumOperation.quantumSwap(quantumState,particle1,quantumState.getParticlesName().get(particle-2));
        QuantumOperation.quantumSwap(quantumState,particle2,quantumState.getParticlesName().get(particle-1));
        //得到代理者手中的粒子态
        double ownState[]=new double[4];
        for(int i=0;i<4;i++){
            for(int j=i;j<Math.pow(2,particle);j+=4){
                ownState[i]+=quantumState.getState()[j];
            }
        }
        doubleState=new DoubleState(ownState);
        doubleState.setParticlesName(1,particle1);
        doubleState.setParticlesName(2,particle2);
        return doubleState;
    }

}
