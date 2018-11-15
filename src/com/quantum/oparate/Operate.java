package com.quantum.oparate;

import com.quantum.tools.QuantumState;

public class Operate {
    //张量积的运算,矩阵之间的张量积，因为会量子门的操作也会使用张量积
    public static double[][] operatorTensor(double[][] A, double[][] B) {

        final int m = A.length;
        final int n = A[0].length;
        final int p = B.length;
        final int q = B[0].length;
        double[][] result = new double[m * n][p * q];
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
    //对量子态进行幺正操作
//    public static void performOperator(QuantumState state,int pos,double[][] operator){
//        double [][] targetState
//    }

    //向量之间的张量积运算用于计算量子系统的状态
    public static double[]operatorTensor(double[]A, double[]B) {

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


    public static void main(String[] args){
        //向量张量积运算的例子
        double []op1=new double[]{1,0};
        double []op2=new double[]{0,1};
        double [] result=Operate.operatorTensor(op1,op2);
        for (int i=0;i<result.length;i++){
                System.out.print(result[i]+"        ");
        }
        System.out.println("矩阵张量积");
        System.out.println("");

        //矩阵之间张量积运算的例子
        double [][]op3=new double[][]{{1,0},{0,1}};
        double [][]op4=new double[][]{{0,1},{1,0}};
        double [][]result1=Operate.operatorTensor(op3,op4);
        for (int i=0;i<result1.length;i++){
            for(int j=0;j<result1[0].length;j++){
                System.out.print(result1[i][j]+"        ");
            }
            System.out.println("");
        }
    }
}

