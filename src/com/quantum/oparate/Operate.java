package com.quantum.oparate;

public class Operate {
    //张量积的运算
    //todo：结果正确与否待验证，没有对单粒子之间的张量积做处理
    public static  double[][] operatorTensor(double[][] operator1,double[][] operator2){
        int row1=operator1.length;
        int row2=operator2.length;
        int col1=operator1[0].length;
        int col2=operator2[0].length;
        int row=row1* row2;
        int col=col1*col2;
        double[][] result=new double[row][col];
        for (int i=0;i<row1;i++){
            for (int j=0;j<col1;j++){
                for (int k=0;k<row2;k++){
                    for(int l=0;l<col2;l++){
                        result[i*row2+k][j*col2+1]=operator1[i][j]*operator2[k][l];
                    }
                }
            }
        }
        return result;
    }
    public static void main(String[] args){
        double [][] op1=new double[][]{{2,0},{-1,1}};
        double [][] op2=new double[][]{{1,2,3},{1,0,1}};
        double [][] result=Operate.operatorTensor(op1,op2);
        for (int i=0;i<result.length;i++){
            for(int j=0;j<result[0].length;j++) {
                System.out.print(result[i][j]+" ");
            }
            System.out.println();
        }
    }
}

