package com;

import org.ujmp.core.DenseMatrix;
import org.ujmp.core.Matrix;

public class Main {
    //用于测试POVM测量之后系统态的形式
    public static void main(String[] args){
        //定义POVM的测量算子为
        //测量算子为O1
//        Matrix matrix= DenseMatrix.Factory.zeros(4, 4);
//        for (int i = 0; i < matrix.getRowCount(); ++i){
//            for (int j = 0 ; j < matrix.getColumnCount(); ++j){
//                //可以使用setXXX来进行矩阵的赋值，其中第一个参数是值，第二个参数是行，第三个参数是列
//                matrix.setAsDouble(16, i , j);
//            }
//        }
        //测量算子为O2
        Matrix matrix= DenseMatrix.Factory.zeros(4, 4);
        matrix.setAsDouble(16,0,0);
        matrix.setAsDouble(16,0,1);
        matrix.setAsDouble(-16,0,2);
        matrix.setAsDouble(-16,0,3);
        matrix.setAsDouble(16,1,0);
        matrix.setAsDouble(16,1,1);
        matrix.setAsDouble(-16,1,2);
        matrix.setAsDouble(-16,1,3);
        matrix.setAsDouble(-16,2,0);
        matrix.setAsDouble(-16,2,1);
        matrix.setAsDouble(16,2,2);
        matrix.setAsDouble(16,2,3);
        matrix.setAsDouble(-16,3,0);
        matrix.setAsDouble(-16,3,1);
        matrix.setAsDouble(16,3,2);
        matrix.setAsDouble(16,3,3);
        Matrix measure = matrix.times(1.0/64);
        System.out.println("measure的值是");
        System.out.println(measure);
//        System.out.println("dense2的值是");
//        System.out.println(dense2);
        //那么作用在量子态上的量子操作为
        double [][]operator=measure.toDoubleArray();
//        operator=MathOperation.tensor(QuantumGate.Operator_I,MathOperation.tensor(QuantumGate.Operator_I,operator));
        //要测量的两粒子的态为
//        double[] state=new double[]{0.0625,0,0,0,0,0.0625,0,0,0,0,0.0625,0,0,0,0,0.0625};
        //要测量的两粒子态为
        double[] state=new double[]{0.0625,0.0625,0.0625,0.0625};
        Matrix vector=Matrix.Factory.importFromArray(state).transpose();
        //将二维数组转换为列向量
        System.out.println("--------------");
        System.out.println(measure);
        Matrix fenZi=measure.mtimes(vector);  //将表示数组的态先转换为列向量并进行运算
        System.out.println("分子的值是");
        System.out.println(fenZi);
        Matrix suanzi=measure.mtimes(measure.transpose());
        System.out.println("算子的值是");
        System.out.println(suanzi);
        Matrix temp=(Matrix.Factory.importFromArray(state)).mtimes(suanzi.mtimes(Matrix.Factory.importFromArray(state).transpose()));
        System.out.println("分母的值是");
        System.out.println(temp);
        Matrix result=fenZi.times(Math.sqrt(temp.getAsDouble(0,0)));
        System.out.println("测量完成后量子谈坍缩到");
        System.out.println(result);

    }
}
