package com.quantum;

import org.junit.Test;
import org.ujmp.core.DenseMatrix;
import org.ujmp.core.Matrix;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Random;

public class MainTest {
    /**
     * 随机生成指定范围内固定位数的小数
     * @param args
     */
    public static void main(String[] args) {
        Random random=new Random();
        for(int i=0;i<30;i++){
            System.out.println(random.nextDouble());
        }
    }

    public static double randomDecimal(double min,double max,int scl){
        int pow = (int) Math.pow(10, scl);//指定小数位
        double one = Math.floor((Math.random() * (max - min) + min) * pow) / pow;
        return one;
    }
}
