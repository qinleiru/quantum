package com.quantum.measure;

import com.quantum.oparate.MathOperation;
import com.quantum.state.DoubleState;
import com.quantum.state.MultiState;
import org.junit.Test;
import org.ujmp.core.DenseMatrix;
import org.ujmp.core.Matrix;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class POVMMeasureTest {
    /**
     * 两量子比特的POVM测量
     */
    @Test
    public void measurePOVMTest(){
        System.out.println("测试测试测试测试");
        /*
           添加要区分的两粒子态,
         */
        //用于构建量子信道的簇态的系数
        double a=0.125;
        double b=Math.sqrt(0.5-Math.pow(a,2));
        double c=b;
        double d=a;
        if(Math.pow(a,2)+Math.pow(b,2)!=0.5){
            return;
        }
        ArrayList<DoubleState> doubleStates=new ArrayList<>();
        double[] state=new double[]{a*c,a*d,b*c,b*d};
        DoubleState doubleState=new DoubleState(state);
        doubleState.setParticlesName(1,"m");
        doubleState.setParticlesName(2,"n");
        doubleStates.add(doubleState);
        state=new double[]{a*c,a*d,-b*c,-b*d};
        doubleState=new DoubleState(state);
        doubleState.setParticlesName(1,"m");
        doubleState.setParticlesName(2,"n");
        doubleStates.add(doubleState);
        state=new double[]{a*c,-a*d,b*c,-b*d};
        doubleState=new DoubleState(state);
        doubleState.setParticlesName(1,"m");
        doubleState.setParticlesName(2,"n");
        doubleStates.add(doubleState);
        state=new double[]{a*c,-a*d,-b*c,b*d};
        doubleState=new DoubleState(state);
        doubleState.setParticlesName(1,"m");
        doubleState.setParticlesName(2,"n");
        doubleStates.add(doubleState);
        /*
           用于Bell态测量的测量算子,取值Omega为最小值，epsilon为固定值1/(4(abcd)^2)
         */
        double omega=getOmega(a,b,c,d);                 //49/16.0;
        double epsilon=1/(4*Math.pow(a*b*c*d,2));
        ArrayList<Matrix> oparators=new ArrayList<>();
        //算子1
        Matrix vector1 = DenseMatrix.Factory.zeros(4, 1);
        vector1.setAsDouble(1/(a*c), 0, 0);
        vector1.setAsDouble(1/(a*d), 1, 0);
        vector1.setAsDouble(1/(b*c), 2, 0);
        vector1.setAsDouble(1/(b*d), 3, 0);
        Matrix opera1=vector1.mtimes(vector1.transpose());
        opera1=opera1.times(1/(omega*epsilon));
        oparators.add(opera1);
        //算子2
        Matrix vector2 = DenseMatrix.Factory.zeros(4, 1);
        vector2.setAsDouble(1/(a*c), 0, 0);
        vector2.setAsDouble(1/(a*d), 1, 0);
        vector2.setAsDouble(-1/(b*c), 2, 0);
        vector2.setAsDouble(-1/(b*d), 3, 0);
        Matrix opera2=vector2.mtimes(vector2.transpose());
        opera2=opera2.times(1/(omega*epsilon));
        oparators.add(opera2);
        //算子3
        Matrix vector3 = DenseMatrix.Factory.zeros(4, 1);
        vector3.setAsDouble(1/(a*c), 0, 0);
        vector3.setAsDouble(-1/(a*d), 1, 0);
        vector3.setAsDouble(1/(b*c), 2, 0);
        vector3.setAsDouble(-1/(b*d), 3, 0);
        Matrix opera3=vector3.mtimes(vector3.transpose());
        opera3=opera3.times(1/(omega*epsilon));
        oparators.add(opera3);
        //算子4
        Matrix vector4 = DenseMatrix.Factory.zeros(4, 1);
        vector4.setAsDouble(1/(a*c), 0, 0);
        vector4.setAsDouble(-1/(a*d), 1, 0);
        vector4.setAsDouble(-1/(b*c), 2, 0);
        vector4.setAsDouble(1/(b*d), 3, 0);
        Matrix opera4=vector4.mtimes(vector4.transpose());
        opera4=opera4.times(1/(omega*epsilon));
        oparators.add(opera4);
        /*
          调用方法
         */
        double[] multi=new double[]{0.25*a*c,0,0,0,0,0.25*a*d,0,0,0,0,0.25*b*c,0,0,0,0,0.25*b*d};
        MultiState multiState=new MultiState(multi,4);
        multiState.setParticlesName(1,"1");
        multiState.setParticlesName(2,"2");
        multiState.setParticlesName(3,"m");
        multiState.setParticlesName(4,"n");
        POVMMeasure.measurePOVMDouble(multiState,oparators,doubleStates);
        System.out.println("测试测试测试测试");

    }

    public double getOmega(double a,double b,double c,double d){
        double max=Math.pow(b*d,2);
        max=Math.pow(a*c,2)>max?Math.pow(a*c,2):max;
        max=Math.pow(b*c,2)>max?Math.pow(b*c,2):max;
        max=Math.pow(a*d,2)>max?Math.pow(a*d,2):max;
        return 16*max;
    }
}