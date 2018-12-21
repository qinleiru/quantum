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
     * 测试向量的乘法运算
     */
    @Test
    public void testMultiple(){
        double A[]=new double[]{1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,-1};
        double expectResult[]=new double[]{-1,0,0,-1,0,0,0,0,0,0,0,0,-1,0,0,1};
        assertArrayEquals(expectResult,MathOperation.multiple(A,-1),0);
    }

    /**
     * 对向量进行归一化操作
     */
    @Test
    public void testNormalization(){
        double A[]=new double[]{1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,-1};
        MathOperation.normalization(A);
        double expectResult[]=new double[]{0.5,0,0,0.5,0,0,0,0,0,0,0,0,0.5,0,0,-0.5};
        assertArrayEquals(expectResult,A,0);
    }

    /**
     * 对向量进行归一化操作，并改变相应的符号
     */
    @Test
    public void testNormalizationNegative(){
        double A[]=new double[]{0,0,-1,-1,0,0,0,0,0,0,0,0,-1,0,0,1};
        MathOperation.normalization(A);
        double expectResult[]=new double[]{0,0,0.5,0.5,0,0,0,0,0,0,0,0,0.5,0,0,-0.5};
        assertArrayEquals(expectResult,A,0);
    }

}