package com.quantum.oparate;

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

    //归一化操作+第一位的正定操作
    public static void normalization(double[] states){
        double sum=0;
        for (int i=0;i<states.length;i++){
            sum+=Math.pow(states[i],2);
        }
        for (int i=0;i<states.length;i++){
            states[i]=states[i]/Math.pow(sum,0.5);
        }
        boolean tag=false;
        for (int i=0;i<states.length;i++){
            if (states[i]<0&&tag==false){
                for(int j=0;j<states.length;j++){
                    states[j]*=-1;
                }
                break;
            }
            if (states[i]>0){
                tag=true;
            }
        }
    }
}

