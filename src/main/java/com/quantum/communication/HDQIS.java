package com.quantum.communication;

import com.quantum.gate.QuantumGate;
import com.quantum.measure.ProjectiveMeasure;
import com.quantum.oparate.MathOperation;
import com.quantum.oparate.QuantumOperation;
import com.quantum.state.*;
import com.quantum.tools.QuantumState;

//Xu等人提出的确定性的分层量子信息拆分协议，下面是逻辑的实现。其中有一个权限高的代理者，两个权限低的代理者
public class HDQIS {
    public static void run() {
        //准备要传送的秘密两量子比特
        double[] secret = new double[]{0.5, 0.5, 0.5, 0.5};
        DoubleState doubleState = new DoubleState(secret);
        doubleState.setParticlesName(1, "x");
        doubleState.setParticlesName(2, "y");
        System.out.println("要传送的秘密两量子比特为" + doubleState.showBinaryState());
        doubleState.showParticleName();
        System.out.println();
        //准备用于构造量子信道的两个纠缠簇态
        double[] state1 = new double[]{0.5, 0, 0, 0.5, 0, 0, 0, 0, 0, 0, 0, 0, 0.5, 0, 0, -0.5};
        ClusterState clusterState1 = new ClusterState(state1, 4);
        clusterState1.setParticlesName(1, "1");
        clusterState1.setParticlesName(2, "2");
        clusterState1.setParticlesName(3, "3");
        clusterState1.setParticlesName(4, "4");
        System.out.println("构成量子信道的四粒子簇态为" + clusterState1.showBinaryState());
        clusterState1.showParticleName();
        System.out.println();
        ClusterState clusterState2 = new ClusterState(state1, 4);
        clusterState2.setParticlesName(1, "5");
        clusterState2.setParticlesName(2, "6");
        clusterState2.setParticlesName(3, "7");
        clusterState2.setParticlesName(4, "8");
        System.out.println("构成量子信道的四粒子簇态为" + clusterState2.showBinaryState());
        clusterState2.showParticleName();
        System.out.println();
        //整个系统的量子态为
        MultiState systemState = QuantumOperation.quantumTensor(QuantumOperation.quantumTensor(doubleState, clusterState1), clusterState2);
        System.out.println("Bell态测量之前系统的态为" + systemState.showBinaryState());
        systemState.showParticleName();
        System.out.println();
        //对发送者Alice手中的两个粒子(x,1)与(y,5)进行Bell态的测量,测量完成之后秘密量子比特在三个代理者之间共享。
        int resultAliceX = ProjectiveMeasure.measureBeseBell(systemState, "x", "1");
        int resultAliceY = ProjectiveMeasure.measureBeseBell(systemState, "y", "5");
        System.out.println("Alice对手中的粒子进行两次Bell态的测量系统的态为：" + systemState.showBinaryState());
        systemState.showParticleName();
        System.out.println();
        int select = 2;
        //权限高的代理者恢复秘密量子比特，这里Bob是权限高的代理者
        if (select == 1) {
            int resultCharlie3 = ProjectiveMeasure.measureBaseZ(systemState, "3");
            int resultCharlie7 = ProjectiveMeasure.measureBaseZ(systemState, "7");
            System.out.println("Charlie对手中的两个单粒子进行Z基测量之后系统的态为：" + systemState.showBinaryState());
            systemState.showParticleName();
            System.out.println();
            if (resultAliceX == 1 && resultAliceY == 1) {
                if (resultCharlie3 == 0 && resultCharlie7 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_I);
                }
                if (resultCharlie3 == 0 && resultCharlie7 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_Z);
                }
                if (resultCharlie3 == 1 && resultCharlie7 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_I);
                }
                if (resultCharlie3 == 1 && resultCharlie7 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_Z);
                }
            }
            if (resultAliceX == 1 && resultAliceY == 3) {
                if (resultCharlie3 == 0 && resultCharlie7 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_Z);
                }
                if (resultCharlie3 == 0 && resultCharlie7 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_I);
                }
                if (resultCharlie3 == 1 && resultCharlie7 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_Z);
                }
                if (resultCharlie3 == 1 && resultCharlie7 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_I);
                }
            }
            if (resultAliceX == 1 && resultAliceY == 2) {
                if (resultCharlie3 == 0 && resultCharlie7 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_X);
                }
                if (resultCharlie3 == 0 && resultCharlie7 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_iY);
                }
                if (resultCharlie3 == 1 && resultCharlie7 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_X);
                }
                if (resultCharlie3 == 1 && resultCharlie7 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_iY);
                }
            }
            if (resultAliceX == 1 && resultAliceY == 4) {
                if (resultCharlie3 == 0 && resultCharlie7 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_iY);
                }
                if (resultCharlie3 == 0 && resultCharlie7 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_X);
                }
                if (resultCharlie3 == 1 && resultCharlie7 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_iY);
                }
                if (resultCharlie3 == 1 && resultCharlie7 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_X);
                }
            }
            if (resultAliceX == 3 && resultAliceY == 1) {
                if (resultCharlie3 == 0 && resultCharlie7 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_I);
                }
                if (resultCharlie3 == 0 && resultCharlie7 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_Z);
                }
                if (resultCharlie3 == 1 && resultCharlie7 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_I);
                }
                if (resultCharlie3 == 1 && resultCharlie7 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_Z);
                }
            }
            if (resultAliceX == 3 && resultAliceY == 3) {
                if (resultCharlie3 == 0 && resultCharlie7 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_Z);
                }
                if (resultCharlie3 == 0 && resultCharlie7 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_I);
                }
                if (resultCharlie3 == 1 && resultCharlie7 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_Z);
                }
                if (resultCharlie3 == 1 && resultCharlie7 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_I);
                }

            }
            if (resultAliceX == 3 && resultAliceY == 2) {
                if (resultCharlie3 == 0 && resultCharlie7 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_X);
                }
                if (resultCharlie3 == 0 && resultCharlie7 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_iY);
                }
                if (resultCharlie3 == 1 && resultCharlie7 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_X);
                }
                if (resultCharlie3 == 1 && resultCharlie7 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_iY);
                }
            }
            if (resultAliceX == 3 && resultAliceY == 4) {
                if (resultCharlie3 == 0 && resultCharlie7 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_iY);
                }
                if (resultCharlie3 == 0 && resultCharlie7 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_Z);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_X);
                }
                if (resultCharlie3 == 1 && resultCharlie7 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_iY);
                }
                if (resultCharlie3 == 1 && resultCharlie7 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_I);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_X);
                }
            }
            if (resultAliceX == 2 && resultAliceY == 1) {
                if (resultCharlie3 == 0 && resultCharlie7 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_I);
                }
                if (resultCharlie3 == 0 && resultCharlie7 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_Z);
                }
                if (resultCharlie3 == 1 && resultCharlie7 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_I);
                }
                if (resultCharlie3 == 1 && resultCharlie7 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_Z);
                }
            }
            if (resultAliceX == 2 && resultAliceY == 3) {
                if (resultCharlie3 == 0 && resultCharlie7 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_Z);
                }
                if (resultCharlie3 == 0 && resultCharlie7 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_I);
                }
                if (resultCharlie3 == 1 && resultCharlie7 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_Z);
                }
                if (resultCharlie3 == 1 && resultCharlie7 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_I);
                }
            }
            if (resultAliceX == 2 && resultAliceY == 2) {
                if (resultCharlie3 == 0 && resultCharlie7 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_X);
                }
                if (resultCharlie3 == 0 && resultCharlie7 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_iY);
                }
                if (resultCharlie3 == 1 && resultCharlie7 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_X);
                }
                if (resultCharlie3 == 1 && resultCharlie7 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_iY);
                }
            }
            if (resultAliceX == 2 && resultAliceY == 4) {
                if (resultCharlie3 == 0 && resultCharlie7 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_iY);
                }
                if (resultCharlie3 == 0 && resultCharlie7 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_X);
                }
                if (resultCharlie3 == 1 && resultCharlie7 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_iY);
                }
                if (resultCharlie3 == 1 && resultCharlie7 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_X);
                }
            }
            if (resultAliceX == 4 && resultAliceY == 1) {
                if (resultCharlie3 == 0 && resultCharlie7 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_I);
                }
                if (resultCharlie3 == 0 && resultCharlie7 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_Z);
                }
                if (resultCharlie3 == 1 && resultCharlie7 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_I);
                }
                if (resultCharlie3 == 1 && resultCharlie7 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_Z);
                }

            }
            if (resultAliceX == 4 && resultAliceY == 3) {
                if (resultCharlie3 == 0 && resultCharlie7 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_Z);
                }
                if (resultCharlie3 == 0 && resultCharlie7 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_I);
                }
                if (resultCharlie3 == 1 && resultCharlie7 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_Z);
                }
                if (resultCharlie3 == 1 && resultCharlie7 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_I);
                }
            }
            if (resultAliceX == 4 && resultAliceY == 2) {
                if (resultCharlie3 == 0 && resultCharlie7 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_X);
                }
                if (resultCharlie3 == 0 && resultCharlie7 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_iY);
                }
                if (resultCharlie3 == 1 && resultCharlie7 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_X);
                }
                if (resultCharlie3 == 1 && resultCharlie7 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_iY);
                }
            }
            if (resultAliceX == 4 && resultAliceY == 4) {
                if (resultCharlie3 == 0 && resultCharlie7 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_iY);
                }
                if (resultCharlie3 == 0 && resultCharlie7 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_iY);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_X);
                }
                if (resultCharlie3 == 1 && resultCharlie7 == 0) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_iY);
                }
                if (resultCharlie3 == 1 && resultCharlie7 == 1) {
                    QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_X);
                    QuantumOperation.quantumSinglePerform(systemState, "6", QuantumGate.Operator_X);
                }
            }
            System.out.println("经历过所有的测量与操作之后系统的态为" + systemState.showBinaryState());
            systemState.showParticleName();
            System.out.println();
            DoubleState doubleState1=new DoubleState(getOwnState(systemState,"2","6"));
            doubleState1.setParticlesName(1,"2");
            doubleState1.setParticlesName(2,"6");
            System.out.println("权限高的粒子的态为" + doubleState1.showBinaryState());
            doubleState1.showParticleName();
            System.out.println();
        }
        if (select == 2) {
            //权限低的代理者恢复秘密量子比特，这里假设Charlie要恢复秘密消息，Charlie手中有粒子3、粒子7
            int resultBob2=ProjectiveMeasure.measureBaseX(systemState,"2");
            int resultBob6=ProjectiveMeasure.measureBaseX(systemState,"6");
            int resultDavid4=ProjectiveMeasure.measureBaseX(systemState,"4");
            int resultDavid8=ProjectiveMeasure.measureBaseX(systemState,"8");
            //无论测量结果是恢复秘密消息的代理者都会对手中的粒子先进行H门的操作
            QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_H);
            QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_H);
            if (resultAliceX==1&&resultAliceY==1) {
                if (resultBob2 == 0 && resultBob6 == 0) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                }
                if (resultBob2 == 0 && resultBob6 == 1) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                }
                if (resultBob2 == 1 && resultBob6 == 0) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                }
                if (resultBob2 == 1 && resultBob6 == 1) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                }
            }
            if (resultAliceX==1&&resultAliceY==3) {
                if (resultBob2 == 0 && resultBob6 == 0) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                }
                if (resultBob2 == 0 && resultBob6 == 1) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                }
                if (resultBob2 == 1 && resultBob6 == 0) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                }
                if (resultBob2 == 1 && resultBob6 == 1) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                }
            }
            if (resultAliceX==1&&resultAliceY==2) {
                if (resultBob2 == 0 && resultBob6 == 0) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                }
                if (resultBob2 == 0 && resultBob6 == 1) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                }
                if (resultBob2 == 1 && resultBob6 == 0) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                }
                if (resultBob2 == 1 && resultBob6 == 1) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                }
            }
            if (resultAliceX==1&&resultAliceY==4) {
                if (resultBob2 == 0 && resultBob6 == 0) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                }
                if (resultBob2 == 0 && resultBob6 == 1) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                }
                if (resultBob2 == 1 && resultBob6 == 0) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                }
                if (resultBob2 == 1 && resultBob6 == 1) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                }
            }
            if (resultAliceX==3&&resultAliceY==1) {
                if (resultBob2 == 0 && resultBob6 == 0) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                }
                if (resultBob2 == 0 && resultBob6 == 1) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                }
                if (resultBob2 == 1 && resultBob6 == 0) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                }
                if (resultBob2 == 1 && resultBob6 == 1) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                }
            }
            if (resultAliceX==3&&resultAliceY==3) {
                if (resultBob2 == 0 && resultBob6 == 0) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                }
                if (resultBob2 == 0 && resultBob6 == 1) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                }
                if (resultBob2 == 1 && resultBob6 == 0) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                }
                if (resultBob2 == 1 && resultBob6 == 1) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                }
            }
            if (resultAliceX==3&&resultAliceY==2) {
                if (resultBob2 == 0 && resultBob6 == 0) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                }
                if (resultBob2 == 0 && resultBob6 == 1) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                }
                if (resultBob2 == 1 && resultBob6 == 0) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                }
                if (resultBob2 == 1 && resultBob6 == 1) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                }
            }
            if (resultAliceX==3&&resultAliceY==4) {
                if (resultBob2 == 0 && resultBob6 == 0) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                }
                if (resultBob2 == 0 && resultBob6 == 1) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                }
                if (resultBob2 == 1 && resultBob6 == 0) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                }
                if (resultBob2 == 1 && resultBob6 == 1) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                }
            }
            if (resultAliceX==2&&resultAliceY==1) {
                if (resultBob2 == 0 && resultBob6 == 0) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                }
                if (resultBob2 == 0 && resultBob6 == 1) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                }
                if (resultBob2 == 1 && resultBob6 == 0) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                }
                if (resultBob2 == 1 && resultBob6 == 1) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                }
            }
            if (resultAliceX==2&&resultAliceY==3) {
                if (resultBob2 == 0 && resultBob6 == 0) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                }
                if (resultBob2 == 0 && resultBob6 == 1) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                }
                if (resultBob2 == 1 && resultBob6 == 0) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                }
                if (resultBob2 == 1 && resultBob6 == 1) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                }
            }
            if (resultAliceX==2&&resultAliceY==2) {
                if (resultBob2 == 0 && resultBob6 == 0) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                }
                if (resultBob2 == 0 && resultBob6 == 1) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                }
                if (resultBob2 == 1 && resultBob6 == 0) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                }
                if (resultBob2 == 1 && resultBob6 == 1) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                }
            }
            if (resultAliceX==2&&resultAliceY==4) {
                if (resultBob2 == 0 && resultBob6 == 0) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                }
                if (resultBob2 == 0 && resultBob6 == 1) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                }
                if (resultBob2 == 1 && resultBob6 == 0) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                }
                if (resultBob2 == 1 && resultBob6 == 1) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                }
            }
            if (resultAliceX==4&&resultAliceY==1) {
                if (resultBob2 == 0 && resultBob6 == 0) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                }
                if (resultBob2 == 0 && resultBob6 == 1) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                }
                if (resultBob2 == 1 && resultBob6 == 0) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                }
                if (resultBob2 == 1 && resultBob6 == 1) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                }
            }
            if (resultAliceX==4&&resultAliceY==3) {
                if (resultBob2 == 0 && resultBob6 == 0) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                }
                if (resultBob2 == 0 && resultBob6 == 1) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                }
                if (resultBob2 == 1 && resultBob6 == 0) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                }
                if (resultBob2 == 1 && resultBob6 == 1) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                }
            }
            if (resultAliceX==4&&resultAliceY==2) {
                if (resultBob2 == 0 && resultBob6 == 0) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                }
                if (resultBob2 == 0 && resultBob6 == 1) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                }
                if (resultBob2 == 1 && resultBob6 == 0) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                }
                if (resultBob2 == 1 && resultBob6 == 1) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                }
            }
            if (resultAliceX==4&&resultAliceY==4) {
                if (resultBob2 == 0 && resultBob6 == 0) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                }
                if (resultBob2 == 0 && resultBob6 == 1) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_iY);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_Z);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                }
                if (resultBob2 == 1 && resultBob6 == 0) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_iY);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_Z);
                    }
                }
                if (resultBob2 == 1 && resultBob6 == 1) {
                    if (resultDavid4 == 0 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 0 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_X);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 0) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_X);
                    }
                    if (resultDavid4 == 1 && resultDavid8 == 1) {
                        QuantumOperation.quantumSinglePerform(systemState,"3",QuantumGate.Operator_I);
                        QuantumOperation.quantumSinglePerform(systemState,"7",QuantumGate.Operator_I);
                    }
                }
            }
            DoubleState doubleState1=new DoubleState(getOwnState(systemState,"3","7"));
            doubleState1.setParticlesName(1,"3");
            doubleState1.setParticlesName(2,"7");
            System.out.println("权限低的粒子的态为" + doubleState1.showBinaryState());
            doubleState1.showParticleName();
            System.out.println();
        }
    }
    public static double[] getOwnState(QuantumState quantumState, String particle1,String particle2){
        int index1=quantumState.getParticlesName().indexOf(particle1);
        int index2=quantumState.getParticlesName().indexOf(particle2);
        double[] result=new double[4];
        int num=quantumState.getParticles();
        for (int i=0;i<Math.pow(2,num);i++){
            if (isBitZero(i,index1+1,num)&&isBitZero(i,index2+1,num)){
                result[0]+=quantumState.getState()[i];
            }
            if (isBitZero(i,index1+1,num)&&!isBitZero(i,index2+1,num)){
                result[1]+=quantumState.getState()[i];
            }
            if (!isBitZero(i,index1+1,num)&&isBitZero(i,index2+1,num)){
                result[2]+=quantumState.getState()[i];
            }
            if (!isBitZero(i,index1+1,num)&&!isBitZero(i,index2+1,num)){
                result[3]+=quantumState.getState()[i];
            }
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
    public static void main(String [] args){
        run();
    }
}
