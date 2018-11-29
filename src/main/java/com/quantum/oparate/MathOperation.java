package com.quantum.oparate;

import com.quantum.gate.QuantumGate;
import com.quantum.tools.QuantumState;
import org.ujmp.core.Matrix;

public class MathOperation {
    //张量积的运算,矩阵之间的张量积，因为会量子门的操作也会使用张量积
    public static double[][] tensor(double[][] A, double[][] B) {

        final int m = A.length;
        final int n = A[0].length;
        final int p = B.length;
        final int q = B[0].length;
        double[][] result = new double[m * p][n * q];
        for (int i = 0; i < m; i++) {

            final int iOffset = i * p;
            for (int j = 0; j < n; j++) {

                final int jOffset = j * q;
                final double aij = A[i][j];

                for (int k = 0; k < p; k++) {

                    for (int l = 0; l < q; l++) {

                        result[iOffset + k][jOffset + l] = aij * B[k][l];

                    }

                }


            }

        }
        return result;
    }

    //计算两个向量之间的张量积运算用于计算量子系统的状态
    public static double[] tensor(double[]A, double[]B) {

        final int m = A.length;
        final int p = B.length;
        double[]result = new double[m * p];
        int k=0;
        for (int i = 0; i < m; i++) {

            final int iOffset = i * p;
            for (int j = 0; j < p; j++) {
                result[k]=A[i]*B[j];
                k++;

            }

        }
        return result;
    }

    //计算多个向量的张量积，向量的个数未知，在此定义为二位数组的长度，每行即为每个向量
    public static double[] tensor(double[][] A) {
        double [] result=tensor(A[0],A[1]);
        for (int i=2;i<A.length;i++){
            result=tensor(result,A[i]);
        }
        return result;
    }

    //向量的数乘用于计算量子系统的状态
    public static double[] multiple(double[]A,double num){
        double [] result=new double[A.length];
        for(int i=0;i<result.length;i++){
            result[i]=A[i]*num;
        }
        return result;
    }
    //向量之间的的加法用于计算量子系统的状态
    public static double[] add(double[] A,double[]B){
        double []result=new double[A.length];
        for(int i=0;i<result.length;i++){
            result[i]=A[i]+B[i];
        }
        return result;
    }
    //多个向量之间的加法
    public static double[] add(double[][] A){
        double []result=add(A[0],A[1]);
        for(int i=2;i<A.length;i++){
            result=add(result,A[i]);
        }
        return result;
    }
    //向量之间的减法用于计算量子系统的状态
    public  static double[] subtract(double[] A,double[] B) {
        double[] result = new double[A.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = A[i] - B[i];
        }
        return result;
    }
    /**
     *@Description:方法更通用，适合对多个量子比特进行操作
     *@Param: [state, pos, operator]  state要进行操作的量子态，pos进行操作的粒子的位置，支持一个或者多个粒子操作，operator操作算子
     *@return:
     *@Author:leiru
     *@date:2018/11/22
     */
    //todo：默认对多个粒子操作这几个粒子是相邻的
    public static void performOperator(QuantumState state,int[] pos,double[][] operator){
        double[] targetState=state.getState();
        double[][] temp=null;
        int i=1;
        if(isExist(1,pos)){
            temp=operator;
            i+=pos.length;
        }
        else{
            temp= QuantumGate.Operator_I;
            i++;
        }
        for(int j=i;j<=state.getParticles();){
            if (!isExist(j,pos)){
                temp=tensor(temp,QuantumGate.Operator_I);
                j++;
            }
            else {
                temp = tensor(temp, operator);
                j+=pos.length;
            }

        }
        Matrix result= (Matrix.Factory.importFromArray(temp)).mtimes((Matrix.Factory.importFromArray(targetState).transpose()));
        state.setState(vecToArray(result.toDoubleArray()));
    }

    //对单量子比特进行操作
    public static void performOperator(QuantumState state,int pos,double[][] operator){
        double[] targetState=state.getState();
        double[][] temp=null;
        if(pos==1){
            temp=operator;
        }
        else
            temp= QuantumGate.Operator_I;
        for(int i=2;i<=state.getParticles();i++){
            if (i!=pos){
                temp=tensor(temp,QuantumGate.Operator_I);
            }
            else
                temp=tensor(temp,operator);
        }
        Matrix result= (Matrix.Factory.importFromArray(temp)).mtimes((Matrix.Factory.importFromArray(targetState).transpose()));
        state.setState(vecToArray(result.toDoubleArray()));
    }

    //用于将列向量转换为数组
    public static double[] vecToArray(double[][] vector){
        double[] result=new double[vector.length];
        for(int i=0;i<result.length;i++){
            result[i]=vector[i][0];
        }
        return result;
    }
    //判断一个整数是否在整数数组之内,整数数组中的元素不重复
    public static  boolean isExist(int num,int[] array){
        for(int i=0;i<array.length;i++){
            if(num==array[i])
                return true;
        }
        return false;
    }

    //归一化操作
    public static void normalization(double[] states){
        double sum=0;
        for (int i=0;i<states.length;i++){
            sum+=Math.pow(states[i],2);
        }
        for (int i=0;i<states.length;i++){
            states[i]=states[i]/Math.pow(sum,0.5);
        }
    }
}

