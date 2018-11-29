package com.quantum.oparate;

import com.quantum.gate.QuantumGate;
import com.quantum.state.ClusterState;
import com.quantum.state.DoubleState;
import org.junit.Test;

import static org.junit.Assert.*;

public class MathOperationTest {

    /**
     * 测试两个矩阵之间的张量积
     */
    @Test
    public void testMatrixTensor(){
        double A[][]=new double[][]{{2,0},{-1,1}};
        double B[][]=new double[][]{{1,2,3},{1,0,1}};
        double expectResult[][]=new double[][]{{2,4,6,0,0,0},{2,0,2,0,0,0},{-1,-2,-3,1,2,3},{-1,0,-1,1,0,1}};
        double actualResult[][]=MathOperation.tensor(A,B);
        for(int i=0;i<actualResult.length;i++){
            assertArrayEquals(expectResult[i],actualResult[i],0);
        }

    }

    /**
     * 测试两个向量之间的张量积
     */
    @Test
    public void testVectorTensor(){
        double [] A=new double[]{1,0};
        double [] B=new double[]{0,1};
        double [] expectResult=new double[]{0,1,0,0};
        assertArrayEquals(expectResult,MathOperation.tensor(A,B),0);
    }

    /**
     * 测试多个向量之间的张量积
     */
    @Test
    public void testMultiVectorTensor(){
        double[][]A=new double[][]{{1,0},{1,0},{1,0}};
        double[] expectResult=new double[]{1,0,0,0,0,0,0,0};
        assertArrayEquals(expectResult,MathOperation.tensor(A),0);
    }
    /**
     * 测试对单个粒子进行幺正操作
     */
    @Test
    public void testSinglePerformOperation(){
        double[] A=new double[]{Math.pow(2,-0.5),0,0,Math.pow(2,-0.5)};
        DoubleState doubleState=new DoubleState(A);
        MathOperation.performOperator(doubleState,2, QuantumGate.Operator_X);
        doubleState.displayState();
    }

    /**
     * 测试对多个粒子进行操作
     */
    @Test
    public void testMulitPerformOperation(){
        double A[]=new double[]{0.5,0,0,0.5,0,0,0,0,0,0,0,0,0.5,0,0,-0.5};//一个四粒子簇态
        ClusterState clusterState=new ClusterState(A,4);
        int[]pos=new int[]{3,4};
        MathOperation.performOperator(clusterState,pos,QuantumGate.Operator_CNOT);
        clusterState.displayState();
    }
}