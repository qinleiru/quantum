package com.quantum.oparate;

import com.quantum.gate.QuantumGate;
import com.quantum.state.*;
import com.quantum.tools.QuantumTools;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class QuantumOperationTest {
    /**
     * 测试两个量子态之间的直积之后，粒子的名称
     */
    @Test
    public void testQuantumTensor(){
        /**
         * 一个四粒子簇态
         */
        double []states=new double[]{0.5,0,0,0.5,0,0,0,0,0,0,0,0,0.5,0,0,-0.5};
        //制备1个四粒子簇态
        ClusterState clusterState=new ClusterState(states,4);
        //设置不同粒子对应的名字
        clusterState.setParticlesName(1,"1");
        clusterState.setParticlesName(2,"2");
        clusterState.setParticlesName(3,"3");
        clusterState.setParticlesName(4,"4");
        /**
         * 一个两量子比特
         */
        double [] doubles=new double[]{0.5,0.5,0.5,0.5};
        //制备一个两量子比特
        DoubleState doubleState=new DoubleState(doubles);
        //设置两量子比特的名字
        doubleState.setParticlesName(1,"x");
        doubleState.setParticlesName(2,"y");
        /**
         * 两个量子态的张量积来表示系统的量子态
         */
        MultiState multiState=QuantumOperation.quantumTensor(clusterState,doubleState);
        //向量之间的张量积已经测试完成，主要测试粒子的名称
        ArrayList<String> arrayList=multiState.getParticlesName();
        assertTrue(arrayList.get(0)=="1");
        assertTrue(arrayList.get(1)=="2");
        assertTrue(arrayList.get(2)=="3");
        assertTrue(arrayList.get(3)=="4");
        assertTrue(arrayList.get(4)=="x");
        assertTrue(arrayList.get(5)=="y");
    }

    /**
     * 用于测试粒子交换之后的复数表示法以及粒子名称
     */
    @Test
    public void testQuantumSwap(){
        /**
         * 一个四粒子簇态
         */
        double []states=new double[]{0.5,0,0,0.5,0,0,0,0,0,0,0,0,0.5,0,0,-0.5};
        //制备1个四粒子簇态
        ClusterState clusterState=new ClusterState(states,4);
        //设置不同粒子对应的名字
        clusterState.setParticlesName(1,"1");
        clusterState.setParticlesName(2,"2");
        clusterState.setParticlesName(3,"x");
        clusterState.setParticlesName(4,"y");
        QuantumOperation.quantumSwap(clusterState,"1","x");
        ArrayList<String> arrayList=clusterState.getParticlesName();
        assertTrue(arrayList.get(0)=="x");
        assertTrue(arrayList.get(1)=="2");
        assertTrue(arrayList.get(2)=="1");
        assertTrue(arrayList.get(3)=="y");
        double [] resultState=new double[]{0.5,0,0,0,0,0,0.5,0,0,0.5,0,0,0,0,0,-0.5};
        assertArrayEquals(resultState,clusterState.getState(),0);
    }

    /**
     * 对量子态的单量子比特门操作,执行I操作
     */
    @Test
    public void testQuantumSinglePerformI(){
        /**
         * 一个四粒子簇态
         */
        double []states=new double[]{0.5,0,0,0.5,0,0,0,0,0,0,0,0,0.5,0,0,-0.5};
        //制备1个四粒子簇态
        ClusterState clusterState=new ClusterState(states,4);
        //设置不同粒子对应的名字
        clusterState.setParticlesName(1,"1");
        clusterState.setParticlesName(2,"2");
        clusterState.setParticlesName(3,"x");
        clusterState.setParticlesName(4,"y");
        QuantumOperation.quantumSinglePerform(clusterState,"1", QuantumGate.Operator_I);
        System.out.println(QuantumTools.showBinaryState(clusterState));
        assertArrayEquals(states,clusterState.getState(),0);
    }

    /**
     * 对量子态的单量子比特门操作,执行X操作
     */
    @Test
    public void testQuantumSinglePerformX(){
        /**
         * 一个四粒子簇态
         */
        double []states=new double[]{0.5,0,0,0.5,0,0,0,0,0,0,0,0,0.5,0,0,-0.5};
        //制备1个四粒子簇态
        ClusterState clusterState=new ClusterState(states,4);
        //设置不同粒子对应的名字
        clusterState.setParticlesName(1,"1");
        clusterState.setParticlesName(2,"2");
        clusterState.setParticlesName(3,"x");
        clusterState.setParticlesName(4,"y");
        QuantumOperation.quantumSinglePerform(clusterState,"2", QuantumGate.Operator_X);
        System.out.println(QuantumTools.showBinaryState(clusterState));
//        assertArrayEquals(states,clusterState.getState(),0);
    }

    @Test
    public void testQuantumSinglePerformIY(){
        /**
         * 一个四粒子簇态
         */
        double []states=new double[]{0.5,0,0,0.5,0,0,0,0,0,0,0,0,0.5,0,0,-0.5};
        //制备1个四粒子簇态
        ClusterState clusterState=new ClusterState(states,4);
        //设置不同粒子对应的名字
        clusterState.setParticlesName(1,"1");
        clusterState.setParticlesName(2,"2");
        clusterState.setParticlesName(3,"x");
        clusterState.setParticlesName(4,"y");
        QuantumOperation.quantumSinglePerform(clusterState,"2", QuantumGate.Operator_iY);
        System.out.println(QuantumTools.showBinaryState(clusterState));
//        assertArrayEquals(states,clusterState.getState(),0);
    }
    @Test
    public void testQuantumSinglePerformH(){
        /**
         * 一个四粒子簇态
         */
        double []states=new double[]{1,0,0,0};
        DoubleState doubleState=new DoubleState(states);
        QuantumOperation.quantumSinglePerform(doubleState,"2", QuantumGate.Operator_H);
        double [] expectResult=new double[]{Math.pow(2,-0.5),Math.pow(2,-0.5),0,0};
        assertArrayEquals(expectResult,doubleState.getState(),0.000001);
    }


    /**
     * 对量子态的两量子比特操作
     */
    @Test
    public void testQuantumDoublePerform(){
        /**
         * 一个四粒子簇态
         */
        double []states=new double[]{0.5,0,0,0.5,0,0,0,0,0,0,0,0,0.5,0,0,-0.5};
        //制备1个四粒子簇态
        ClusterState clusterState=new ClusterState(states,4);
        //设置不同粒子对应的名字
        clusterState.setParticlesName(1,"1");
        clusterState.setParticlesName(2,"2");
        clusterState.setParticlesName(3,"x");
        clusterState.setParticlesName(4,"y");
        System.out.println(QuantumTools.showBinaryState(clusterState));
        System.out.println();
        QuantumOperation.quantumDoublePerform(clusterState,"2","y",QuantumGate.Operator_CNOT);
        System.out.println(QuantumTools.showBinaryState(clusterState));
        double []resultStates=new double[]{0.5,0,0,0,0,0.5,0,0,0,0,0,0.5,0,0,-0.5,0};
        assertArrayEquals(resultStates,clusterState.getState(),0);
    }
}