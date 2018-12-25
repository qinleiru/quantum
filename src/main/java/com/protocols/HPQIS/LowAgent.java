package com.protocols.HPQIS;

import com.protocols.HPQIS.HPQIS;
import com.quantum.gate.QuantumGate;
import com.quantum.measure.Measures;
import com.quantum.measure.ProjectiveMeasure;
import com.quantum.oparate.MathOperation;
import com.quantum.oparate.QuantumOperation;
import com.quantum.role.Agent;

import java.util.ArrayList;
import java.util.HashMap;

import static com.protocols.HPQIS.HPQIS.systemState;

public class LowAgent implements Agent {
    private ArrayList<String> particleName=new ArrayList<String>(); //代理者手中的粒子
    private HashMap<String,Integer> measureResult=new HashMap<String,Integer>();  //不同粒子的测量结果
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
     * 秘密量子比特的恢复
     */
    @Override
    public void  restore(){
        int resultAliceX = HPQIS.resultX;   //粒子x、粒子1的Bell态的测量结果
        int resultAliceY = HPQIS.resultY;   //粒子y、粒子5的Bell态的测量结果
        int lowAgentParticle1;   //权限低的clusterState1态中的粒子的测量结果
        int lowAgentParticle2;   //权限低的clusterState2态中的粒子的测量结果
        int highAgentParticle1;  //权限高的clusterState1态中的粒子的测量结果
        int highAgentParticle2;  //权限高的cluaterState2态中的粒子的测量结果
        String particle1Name;    //要操作的粒子1的名字
        String particle2Name;    //要操作的粒子2的名字
        if (measureResult.keySet().contains("3")) {
            lowAgentParticle1 = measureResult.get("3");
            lowAgentParticle2 = measureResult.get("7");

        } else {
            lowAgentParticle1 = measureResult.get("4");
            lowAgentParticle2 = measureResult.get("8");
        }
        highAgentParticle1=measureResult.get("2");
        highAgentParticle2=measureResult.get("6");
        if(Integer.parseInt(particleName.get(0))<Integer.parseInt(particleName.get(1))){
            particle1Name=particleName.get(0);
            particle2Name=particleName.get(1);
        }
        else{
            particle1Name=particleName.get(1);
            particle2Name=particleName.get(0);
        }
        System.out.println("++++++++粒子X、粒子1的Bell态的测量结果为"+resultAliceX);
        System.out.println("++++++++粒子Y、粒子5的Bell态的测量结果为"+resultAliceY);
        System.out.println("++++++++权限低的代理者的第一个粒子测量结果为"+lowAgentParticle1+" 第二个粒子的测量结果为"+lowAgentParticle2);
        System.out.println("++++++++权限高的代理者的第一个粒子测量结果为"+highAgentParticle1+" 第二个粒子的测量结果为"+highAgentParticle2);
        //无论测量结果是恢复秘密消息的代理者都会对手中的粒子先进行H门的操作
        QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_H);
        QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_H);
        if (resultAliceX==1&&resultAliceY==1) {
            if (highAgentParticle1 == 0 && highAgentParticle2 == 0) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
            }
            if (highAgentParticle1 == 0 && highAgentParticle2 == 1) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 0) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 1) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
            }
        }
        if (resultAliceX==1&&resultAliceY==3) {
            if (highAgentParticle1 == 0 && highAgentParticle2 == 0) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
            }
            if (highAgentParticle1 == 0 && highAgentParticle2 == 1) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 0) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 1) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
            }
        }
        if (resultAliceX==1&&resultAliceY==2) {
            if (highAgentParticle1 == 0 && highAgentParticle2 == 0) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
            }
            if (highAgentParticle1 == 0 && highAgentParticle2 == 1) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 0) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 1) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
            }
        }
        if (resultAliceX==1&&resultAliceY==4) {
            if (highAgentParticle1 == 0 && highAgentParticle2 == 0) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
            }
            if (highAgentParticle1 == 0 && highAgentParticle2 == 1) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 0) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 1) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
            }
        }
        if (resultAliceX==3&&resultAliceY==1) {
            if (highAgentParticle1 == 0 && highAgentParticle2 == 0) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
            }
            if (highAgentParticle1 == 0 && highAgentParticle2 == 1) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 0) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 1) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
            }
        }
        if (resultAliceX==3&&resultAliceY==3) {
            if (highAgentParticle1 == 0 && highAgentParticle2 == 0) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
            }
            if (highAgentParticle1 == 0 && highAgentParticle2 == 1) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 0) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 1) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
            }
        }
        if (resultAliceX==3&&resultAliceY==2) {
            if (highAgentParticle1 == 0 && highAgentParticle2 == 0) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
            }
            if (highAgentParticle1 == 0 && highAgentParticle2 == 1) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 0) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 1) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
            }
        }
        if (resultAliceX==3&&resultAliceY==4) {
            if (highAgentParticle1 == 0 && highAgentParticle2 == 0) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
            }
            if (highAgentParticle1 == 0 && highAgentParticle2 == 1) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 0) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 1) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
            }
        }
        if (resultAliceX==2&&resultAliceY==1) {
            if (highAgentParticle1 == 0 && highAgentParticle2 == 0) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
            }
            if (highAgentParticle1 == 0 && highAgentParticle2 == 1) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 0) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 1) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
            }
        }
        if (resultAliceX==2&&resultAliceY==3) {
            if (highAgentParticle1 == 0 && highAgentParticle2 == 0) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
            }
            if (highAgentParticle1 == 0 && highAgentParticle2 == 1) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 0) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 1) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
            }
        }
        if (resultAliceX==2&&resultAliceY==2) {
            if (highAgentParticle1 == 0 && highAgentParticle2 == 0) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
            }
            if (highAgentParticle1 == 0 && highAgentParticle2 == 1) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 0) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 1) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
            }
        }
        if (resultAliceX==2&&resultAliceY==4) {
            if (highAgentParticle1 == 0 && highAgentParticle2 == 0) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
            }
            if (highAgentParticle1 == 0 && highAgentParticle2 == 1) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 0) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 1) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
            }
        }
        if (resultAliceX==4&&resultAliceY==1) {
            if (highAgentParticle1 == 0 && highAgentParticle2 == 0) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
            }
            if (highAgentParticle1 == 0 && highAgentParticle2 == 1) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 0) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 1) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
            }
        }
        if (resultAliceX==4&&resultAliceY==3) {
            if (highAgentParticle1 == 0 && highAgentParticle2 == 0) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
            }
            if (highAgentParticle1 == 0 && highAgentParticle2 == 1) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 0) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 1) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
            }
        }
        if (resultAliceX==4&&resultAliceY==2) {
            if (highAgentParticle1 == 0 && highAgentParticle2 == 0) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
            }
            if (highAgentParticle1 == 0 && highAgentParticle2 == 1) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 0) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 1) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
            }
        }
        if (resultAliceX==4&&resultAliceY==4) {
            if (highAgentParticle1 == 0 && highAgentParticle2 == 0) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
            }
            if (highAgentParticle1 == 0 && highAgentParticle2 == 1) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 0) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_iY);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
                }
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 1) {
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_X);
                }
                if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
                }
            }
        }
        MathOperation.normalization(systemState.getState());
    }
}