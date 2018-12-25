package com.quantum.measure;

import com.quantum.state.DoubleState;
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
        /*
           添加要区分的两粒子态
         */
        double a=0.25;
        double b=Math.sqrt(0.5-Math.pow(a,2));
        double c=b;
        double d=a;
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
        double omega=49/16.0;
        double epsilon=1/(4*Math.pow(a*b*c*d,2));
        ArrayList<Matrix> oparators=new ArrayList<>();
        Matrix vector1 = DenseMatrix.Factory.zeros(4, 1);
        vector1.setAsDouble(1/a*c, 0, 0);
        vector1.setAsDouble(1/a*d, 1, 0);
        vector1.setAsDouble(1/b*c, 2, 0);
        vector1.setAsDouble(1/b*d, 3, 0);
        Matrix opera11=opera1=opera1




    }

}