package com.quantum;

import org.junit.Test;
import org.ujmp.core.DenseMatrix;
import org.ujmp.core.Matrix;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

public class MainTest {
    @Test
    public void testParseInt(){
        LinkedHashMap<String,Integer> linkedHashMap=new LinkedHashMap<>();
        linkedHashMap.put("A",1);
        linkedHashMap.put("B",2);
        linkedHashMap.put("C",3);
        double   f   =   31.5585705047920580;
        BigDecimal   b   =   BigDecimal.valueOf(f);
        double   f1   =   b.setScale(6,   BigDecimal.ROUND_HALF_UP).doubleValue();
        double f2=keepDecimalPlaces(f,6);
    }

    public double keepDecimalPlaces(double value, int nums){
        BigDecimal   b   =   BigDecimal.valueOf(value);
        return b.setScale(nums,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Matrix vector1= DenseMatrix.Factory.zeros(1,4);
        vector1.setAsDouble(0.1654,0,0);
        vector1.setAsDouble(0.0625,0,1);
        vector1.setAsDouble(0.4375,0,2);
        vector1.setAsDouble(0.1654,0,3);
        System.out.println(vector1);
        Matrix operator1= DenseMatrix.Factory.zeros(4,4);
        operator1.setAsDouble(0.0357,0,0);
        operator1.setAsDouble(0.0945,0,1);
        operator1.setAsDouble(0.0135,0,2);
        operator1.setAsDouble(0.0357,0,3);
        operator1.setAsDouble(0.0945,1,0);
        operator1.setAsDouble(0.2500,1,1);
        operator1.setAsDouble(0.0357,1,2);
        operator1.setAsDouble(0.0945,1,3);
        operator1.setAsDouble(0.0135,2,0);
        operator1.setAsDouble(0.0357,2,1);
        operator1.setAsDouble(0.0051,2,2);
        operator1.setAsDouble(0.0135,2,3);
        operator1.setAsDouble(0.0357,3,0);
        operator1.setAsDouble(0.0945,3,1);
        operator1.setAsDouble(0.0135,3,2);
        operator1.setAsDouble(0.0357,3,3);
        Matrix result=operator1.mtimes(vector1.transpose());
        System.out.println("计算之后的结果是\n"+result);
    }


}
