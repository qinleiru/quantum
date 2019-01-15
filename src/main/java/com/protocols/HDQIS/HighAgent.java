package com.protocols.HDQIS;

import com.quantum.gate.QuantumGate;
import com.quantum.measure.Measures;
import com.quantum.measure.ProjectiveMeasure;
import com.quantum.oparate.MathOperation;
import com.quantum.oparate.QuantumOperation;
import com.protocols.role.Agent;

import java.util.ArrayList;
import java.util.HashMap;

import static com.protocols.HDQIS.HDQIS.systemState;

public class HighAgent implements Agent {
    private ArrayList<String> particleName = new ArrayList<String>(); //代理者手中的粒子
    private HashMap<String, Integer> measureResult = new HashMap<String, Integer>();  //不同粒子的测量结果

    /**
     * 代理者对手中的所有粒子进行单粒子测量
     * @param measures
     * @return
     */
    @Override
    public void measure(Measures measures) {
        int index=measures.getIndex();
        switch (index){
            case 1:
                for (int i=0;i<particleName.size();i++){
                    measureResult.put(particleName.get(i), ProjectiveMeasure.measureBaseX(systemState,particleName.get(i)));
                }
                break;
            case 2:
                for (int i=0;i<particleName.size();i++){
                    measureResult.put(particleName.get(i), ProjectiveMeasure.measureBaseZ(systemState,particleName.get(i)));
                }
                break;
        }
    }

    /**
     * 将测量结果发送给要恢复秘密消息的代理者
     */
    @Override
    public void sendResult(Agent agent){
        agent.recieveResult(measureResult);
    }

    /**
     * 接收来自其他代理者的测量结果
     */
    @Override
    public void recieveResult(HashMap<String, Integer> measureResult){
        for(String key:measureResult.keySet()){
            this.measureResult.put(key,measureResult.get(key));
        }
    }

    /**
     * 接收发送来的粒子
     */
    @Override
    public void recieveParticles(ArrayList<String> particleName) {
        this.particleName = particleName;
    }

    /**
     * 手中的粒子
     */
    public ArrayList<String> getParticleName() {
        return particleName;
    }

    /**
     * 实现父类的抽象方法，权限高的代理者与权限低的代理者的实现是不同的,权限高的代理者进行恢复只需要一个权限低的代理者进行测量即可
     */
    @Override
    public void restore() {
        int resultAliceSA=HDQIS.resultSA;   //对粒子S、粒子A进行
        int lowAgentParticle1;   //权限低的粒子的测量结果
        if (measureResult.keySet().contains("B")) {
            lowAgentParticle1 = measureResult.get("B");
        } else {
            lowAgentParticle1 = measureResult.get("C");
        }
        /**
         * 根据发送者的测量结果以及权限低的代理者的测量结果，对自己手中的秘密量子比特进行操作
         */
        /*
           用于测试的代码
         */
        System.out.println("++++++++Bell态的测量结果为"+resultAliceSA);
        System.out.println("++++++++权限低的代理者的测量结果为"+lowAgentParticle1);
        if (resultAliceSA == 2) {
            if (lowAgentParticle1 == 0) {
                QuantumOperation.quantumSinglePerform(systemState, "D", QuantumGate.Operator_H);
//                System.out.println("经过H态的测量之后系统的态为" + systemState.showBinaryState());
//                systemState.showParticleName();
//                System.out.println();
                QuantumOperation.quantumSinglePerform(systemState, "D", QuantumGate.Operator_I);
            }
            if (lowAgentParticle1 == 1) {
                QuantumOperation.quantumSinglePerform(systemState, "D", QuantumGate.Operator_H);
//                System.out.println("经过H态的测量之后系统的态为" + systemState.showBinaryState());
//                systemState.showParticleName();
//                System.out.println();
                QuantumOperation.quantumSinglePerform(systemState, "D", QuantumGate.Operator_iY);
            }
        }
        if (resultAliceSA == 1) {
            if (lowAgentParticle1 == 0) {
                QuantumOperation.quantumSinglePerform(systemState, "D", QuantumGate.Operator_H);
//                System.out.println("经过H态的测量之后系统的态为" + systemState.showBinaryState());
//                systemState.showParticleName();
//                System.out.println();
                QuantumOperation.quantumSinglePerform(systemState, "D", QuantumGate.Operator_X);
            }
            if (lowAgentParticle1 == 1) {
                QuantumOperation.quantumSinglePerform(systemState, "D", QuantumGate.Operator_H);
//                System.out.println("经过H态的测量之后系统的态为" + systemState.showBinaryState());
//                systemState.showParticleName();
//                System.out.println();
                QuantumOperation.quantumSinglePerform(systemState, "D", QuantumGate.Operator_Z);
            }
        }
        if (resultAliceSA == 4) {
            if (lowAgentParticle1 == 0) {
                QuantumOperation.quantumSinglePerform(systemState, "D", QuantumGate.Operator_H);
//                System.out.println("经过H态的测量之后系统的态为" + systemState.showBinaryState());
//                systemState.showParticleName();
//                System.out.println();
                QuantumOperation.quantumSinglePerform(systemState, "D", QuantumGate.Operator_Z);
            }
            if (lowAgentParticle1 == 1) {
                QuantumOperation.quantumSinglePerform(systemState, "D", QuantumGate.Operator_H);
//                System.out.println("经过H态的测量之后系统的态为" + systemState.showBinaryState());
//                systemState.showParticleName();
//                System.out.println();
                QuantumOperation.quantumSinglePerform(systemState, "D", QuantumGate.Operator_X);
            }
        }
        if (resultAliceSA == 3) {
            if (lowAgentParticle1 == 0) {
                QuantumOperation.quantumSinglePerform(systemState, "D", QuantumGate.Operator_H);
//                System.out.println("经过H态的测量之后系统的态为" + systemState.showBinaryState());
//                systemState.showParticleName();
//                System.out.println();
                QuantumOperation.quantumSinglePerform(systemState, "D", QuantumGate.Operator_iY);
            }
            if (lowAgentParticle1 == 1) {
                QuantumOperation.quantumSinglePerform(systemState, "D", QuantumGate.Operator_H);
//                System.out.println("经过H态的测量之后系统的态为" + systemState.showBinaryState());
//                systemState.showParticleName();
//                System.out.println();
                QuantumOperation.quantumSinglePerform(systemState, "D", QuantumGate.Operator_I);
            }
        }
        MathOperation.normalization(systemState.getState());
    }
}
