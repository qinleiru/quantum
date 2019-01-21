package com.quantum.measure;

import com.quantum.oparate.MathOperation;
import com.quantum.oparate.QuantumOperation;
import com.quantum.state.DoubleState;
import com.quantum.state.QuantumState;
import com.quantum.tools.QuantumTools;
import org.ujmp.core.Matrix;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

public class POVMMeasure {
    /**
     *
     * @param state      系统的态
     * @param oparators   POVM 采用的算子
     * @param doubleStates    要区分的量子态，区分的量子态包含了粒子的名称
     * @return      返回值为int类型，int的值与对应的要区分的量子态一一对应，超过doubleStates的长度，代表测量失败
     */
    //todo:需要整理
    public static int measurePOVMDouble(QuantumState state, ArrayList<Matrix> oparators, ArrayList<DoubleState> doubleStates){
        //计算坍缩到不同量子态的概率
        double[] probabilities=new double[doubleStates.size()];
        for(int i=0;i<doubleStates.size();i++){
            for(int j=0;j<oparators.size();j++){
                double[] arrayState=doubleStates.get(i).getState();
                //求对应每个态的概率
                Matrix condition=Matrix.Factory.importFromArray(arrayState).mtimes(oparators.get(j).mtimes(Matrix.Factory.importFromArray(arrayState).transpose()));
                BigDecimal bigDecimal=BigDecimal.valueOf(condition.getAsDouble(0,0));
                double value=bigDecimal.setScale(10,BigDecimal.ROUND_HALF_UP).doubleValue();
                if(value!=0){
                    probabilities[i]=value;
                }
            }
        }
        //生成随机数，模拟测量结果
        double random=new Random().nextDouble();
        System.out.println("随机数的值为"+random);
        int index=0;   //索引值+测量结果
        double sum=0;
        for (;index<probabilities.length;index++) {
            if (index == 0) {
                if (random >= 0 && random < probabilities[index])
                    break;
            } else {
                if (random >= sum && random < ( probabilities[index] + sum )) {
                    break;
                }
            }
            sum += probabilities[index];
        }
        if(index==4){return 4;}
        //todo:接下来模拟塌缩的过程有待验证
        //计算进行量子门操作的算子，用于模拟坍缩
        double[][] quantumOperation=new double[4][4];
        for (int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                quantumOperation[i][j]=1/(4*doubleStates.get(i).getState()[j]);
            }
        }
        /**
          用于测试的代码，输出量子门
         */
        System.out.println("输出用于转换的算子");
        for (int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                System.out.print(quantumOperation[i][j]+"   ");
            }
            System.out.println();
        }
        System.out.println("对应的index的值为"+index);
        //进行转换，要区别的量子态doubleState[0]对应|00>,doubleState[1]对应为|01>,doubleState[2]对应为|10>,doubleState[1]对应为|11>
        String particle1=doubleStates.get(0).getParticlesName().get(0);
        String particle2=doubleStates.get(1).getParticlesName().get(1);
        System.out.println("量子塌缩前对应的量子态");
        System.out.println(QuantumTools.showBinaryState(state));
        System.out.println();
        QuantumOperation.quantumDoublePerform(state,particle1,particle2,quantumOperation);
        collapseSate(state,index);
        MathOperation.normalization(state.getState());
        System.out.println("经过POVM测量之后系统的态为");
        System.out.println(QuantumTools.showBinaryState(state));
        System.out.println();
        return index;
    }

    public static void collapseSate(QuantumState state,int result){
        int size=state.getParticles();
        switch(result){
            case 0:
                for(int i=0;i<state.getState().length;i++) {
                    if (!(ProjectiveMeasure.isBitZero(i, size-1, state.getParticles())&&(ProjectiveMeasure.isBitZero(i,size,state.getParticles())))) {
                        state.getState()[i]=0;
                    }
                }
                break;
            case 1:
                for(int i=0;i<state.getState().length;i++) {
                    if (!(ProjectiveMeasure.isBitZero(i, size-1, state.getParticles())&&ProjectiveMeasure.isBitOne(i,size,state.getParticles()))) {
                        state.getState()[i]=0;
                    }
                }
                break;
            case 2:
                for(int i=0;i<state.getState().length;i++) {
                    if (!(ProjectiveMeasure.isBitOne(i, size-1, state.getParticles())&&ProjectiveMeasure.isBitZero(i,size,state.getParticles()))) {
                        state.getState()[i]=0;
                    }
                }
                break;
            case 3:
                for(int i=0;i<state.getState().length;i++) {
                    if (!(ProjectiveMeasure.isBitOne(i, size-1, state.getParticles())&&ProjectiveMeasure.isBitOne(i,size,state.getParticles()))) {
                        state.getState()[i]=0;
                    }
                }
                break;
        }

    }

}
