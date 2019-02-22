package com.protocols.HDQIS2;

import com.protocols.role.AbstractAgent;
import com.quantum.gate.QuantumGate;
import com.quantum.measure.Measures;
import com.quantum.measure.ProjectiveMeasure;
import com.quantum.oparate.QuantumOperation;

import java.util.ArrayList;
import java.util.HashMap;

import static com.protocols.HDQIS2.HDQIS2.systemState;


public class HighAgent extends AbstractAgent {

    /**
     * 代理者对手中的所有粒子进行单粒子测量
     * @param measures
     * @return
     */
    @Override
    public void measure(Measures measures) {
        int index=measures.getIndex();
        int result=0;
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
     * 实现父类的抽象方法，权限高的代理者与权限低的代理者的实现是不同的,权限高的代理者进行恢复只需要一个权限低的代理者进行测量即可
     */
    @Override
    public void restore() {
        int resultAliceX = HDQIS2.resultX;   //粒子x、粒子1的Bell态的测量结果
        int resultAliceY = HDQIS2.resultY;   //粒子y、粒子5的Bell态的测量结果
        int lowAgentParticle1;   //权限低的clusterState1态中的粒子的测量结果
        int lowAgentParticle2;   //权限低的clusterState2态中的粒子的测量结果
        if (measureResult.keySet().contains("3")) {
            lowAgentParticle1 = measureResult.get("3");
        } else {
            lowAgentParticle1 = measureResult.get("4");
        }
        if (measureResult.keySet().contains("7")) {
            lowAgentParticle2 = measureResult.get("7");
        } else {
            lowAgentParticle2 = measureResult.get("8");
        }
        /**
         * 根据发送者的测量结果以及权限低的代理者的测量结果，对自己手中的秘密量子比特进行操作
         */
        System.out.println("++++++++粒子X、粒子1的Bell态的测量结果为"+resultAliceX);
        System.out.println("++++++++粒子Y、粒子5的Bell态的测量结果为"+resultAliceY);
        System.out.println("++++++++权限低的代理者的第一个粒子测量结果为"+lowAgentParticle1+" 第二个粒子的测量结果为"+lowAgentParticle2);
        if (resultAliceX == 1 && resultAliceY == 1) {
            if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_I);
            }
            if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_Z);
            }
            if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_I);
            }
            if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_Z);
            }
        }
        if (resultAliceX == 1 && resultAliceY == 3) {
            if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_Z);
            }
            if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_I);
            }
            if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_Z);
            }
            if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_I);
            }
        }
        if (resultAliceX == 1 && resultAliceY == 2) {
            if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_X);
            }
            if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_iY);
            }
            if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_X);
            }
            if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_iY);
            }
        }
        if (resultAliceX == 1 && resultAliceY == 4) {
            if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_iY);
            }
            if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_X);
            }
            if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_iY);
            }
            if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_X);
            }
        }
        if (resultAliceX == 3 && resultAliceY == 1) {
            if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_I);
            }
            if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_Z);
            }
            if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_I);
            }
            if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_Z);
            }
        }
        if (resultAliceX == 3 && resultAliceY == 3) {
            if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_Z);
            }
            if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_I);
            }
            if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_Z);
            }
            if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_I);
            }

        }
        if (resultAliceX == 3 && resultAliceY == 2) {
            if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_X);
            }
            if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_iY);
            }
            if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_X);
            }
            if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_iY);
            }
        }
        if (resultAliceX == 3 && resultAliceY == 4) {
            if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_iY);
            }
            if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_X);
            }
            if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_iY);
            }
            if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_X);
            }
        }
        if (resultAliceX == 2 && resultAliceY == 1) {
            if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_X);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_I);
            }
            if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_X);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_Z);
            }
            if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_iY);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_I);
            }
            if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_iY);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_Z);
            }
        }
        if (resultAliceX == 2 && resultAliceY == 3) {
            if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_X);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_Z);
            }
            if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_X);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_I);
            }
            if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_iY);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_Z);
            }
            if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_iY);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_I);
            }
        }
        if (resultAliceX == 2 && resultAliceY == 2) {
            if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_X);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_X);
            }
            if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_X);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_iY);
            }
            if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_iY);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_X);
            }
            if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_iY);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_iY);
            }
        }
        if (resultAliceX == 2 && resultAliceY == 4) {
            if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_X);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_iY);
            }
            if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_X);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_X);
            }
            if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_iY);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_iY);
            }
            if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_iY);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_X);
            }
        }
        if (resultAliceX == 4 && resultAliceY == 1) {
            if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_iY);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_I);
            }
            if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_iY);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_Z);
            }
            if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_X);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_I);
            }
            if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_X);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_Z);
            }

        }
        if (resultAliceX == 4 && resultAliceY == 3) {
            if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_iY);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_Z);
            }
            if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_iY);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_I);
            }
            if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_X);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_Z);
            }
            if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_X);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_I);
            }
        }
        if (resultAliceX == 4 && resultAliceY == 2) {
            if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_iY);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_X);
            }
            if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_iY);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_iY);
            }
            if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_X);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_X);
            }
            if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_X);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_iY);
            }
        }
        if (resultAliceX == 4 && resultAliceY == 4) {
            if (lowAgentParticle1 == 0 && lowAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_iY);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_iY);
            }
            if (lowAgentParticle1 == 0 && lowAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_iY);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_X);
            }
            if (lowAgentParticle1 == 1 && lowAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_X);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_iY);
            }
            if (lowAgentParticle1 == 1 && lowAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_X);
                QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_X);
            }
        }
//        MathOperation.normalization(systemState.getState());
    }
}
