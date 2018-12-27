package com.quantum.measure;

import com.quantum.oparate.MathOperation;
import com.quantum.oparate.QuantumOperation;
import com.quantum.state.DoubleState;
import com.quantum.tools.QuantumState;
import com.quantum.tools.Range;
import org.ujmp.core.Matrix;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class POVMMeasure {
    /**
     *
     * @param state      系统的态
     * @param oparators   POVM 采用的算子
     * @param doubleStates    要区分的量子态，区分的量子态包含了粒子的名称
     * @return
     */
    //todo:需要整理
    public static boolean measurePOVMDouble(QuantumState state, ArrayList<Matrix> oparators, ArrayList<DoubleState> doubleStates){
        //计算进行量子门操作的算子，用于模拟坍缩
        double[][] quantumGate={doubleStates.get(0).getState(),doubleStates.get(1).getState(),doubleStates.get(2).getState(),doubleStates.get(3).getState()};
        LinkedHashMap<DoubleState, Range> probabilities=new LinkedHashMap<>();  //存取得到的不同的态的测量的概率
        //计算坍缩到不同量子态的概率,使用公式进行计算考虑到精确度的原因使用BigDecimal
        BigDecimal start=BigDecimal.ZERO;
        for(int i=0;i<doubleStates.size();i++){
            for(int j=0;j<oparators.size();j++){
                double[] state1=doubleStates.get(i).getState();
                Matrix temp=Matrix.Factory.importFromArray(state1).transpose();
                System.out.println("列向量的值为\n"+temp);
                Matrix condition=oparators.get(j).mtimes(temp);
                System.out.println("算子与列向量的乘积为\n"+condition);
                if(!isZeroMatrix(condition)){
                    System.out.println("i的值为"+i);
                    System.out.println("j的值为"+j);
                    Matrix linshi=Matrix.Factory.importFromArray(doubleStates.get(i).getState());
                    System.out.println("行向量为"+linshi);
                    Matrix probability=linshi.mtimes(condition);
                    System.out.println("最终求得的概率值为"+probability);
                    System.out.println("测的概率为\n"+BigDecimal.valueOf(probability.getAsDouble(0,0)));
                    Range range;
                    if(i==0) {
                        range=new Range(start,BigDecimal.valueOf(probability.doubleValue()));
                        probabilities.put(doubleStates.get(i), range);
                        start=start.add(BigDecimal.valueOf(probability.doubleValue()));
                    }
                    else{
                        range=new Range(start,start.add(BigDecimal.valueOf(probability.doubleValue())));
                        probabilities.put(doubleStates.get(i),range);
                        start=start.add(BigDecimal.valueOf(probability.doubleValue()));                    }
                }
            }
        }
        //进行转换，要区别的量子态doubleState[0]对应|00>,doubleState[1]对应为|01>,doubleState[2]对应为|10>,doubleState[1]对应为|11>
        QuantumOperation.quantumDoublePerform(state,doubleStates.get(0).getParticlesName().get(0),doubleStates.get(0).getParticlesName().get(1),quantumGate);
        //生成随机数，模拟测量结果
        BigDecimal random=BigDecimal.valueOf(Math.random());
        int index=0;   //索引值+测量结果
        for (index=0;index<probabilities.size();index++){
            BigDecimal borderStart=probabilities.get(doubleStates.get(index)).getStart();
            BigDecimal borderEnd=probabilities.get(doubleStates.get(index)).getStart();
            //生成的随机数在此范围内
            if((random.compareTo(borderStart)==-1||random.compareTo(borderStart)==0)&&random.compareTo(borderEnd)==1){
                 break;
            }
        }
        if(index==4)  return false;   //测量失败返回
        int size=state.getParticlesName().size();
        //将测量的两个粒子后置，便于进行坍缩的实现
        QuantumOperation.quantumSwap(state,state.getParticlesName().get(size-2),doubleStates.get(0).getParticlesName().get(0));
        QuantumOperation.quantumSwap(state,state.getParticlesName().get(size-1),doubleStates.get(0).getParticlesName().get(1));
        switch(index){
            case 0:
                for(int i=0;i<state.getState().length;i++) {
                    if (!ProjectiveMeasure.isBitZero(i, size-1, state.getParticles())&&ProjectiveMeasure.isBitZero(i,size,state.getParticles())) {
                        state.getState()[i]=0;
                    }
                }
                    break;
            case 1:
                for(int i=0;i<state.getState().length;i++) {
                    if (!ProjectiveMeasure.isBitZero(i, size-1, state.getParticles())&&ProjectiveMeasure.isBitOne(i,size,state.getParticles())) {
                        state.getState()[i]=0;
                    }
                }
                break;
            case 2:
                for(int i=0;i<state.getState().length;i++) {
                    if (!ProjectiveMeasure.isBitOne(i, size-1, state.getParticles())&&ProjectiveMeasure.isBitZero(i,size,state.getParticles())) {
                        state.getState()[i]=0;
                    }
                }
                break;
            case 3:
                for(int i=0;i<state.getState().length;i++) {
                    if (!ProjectiveMeasure.isBitOne(i, size-1, state.getParticles())&&ProjectiveMeasure.isBitOne(i,size,state.getParticles())) {
                        state.getState()[i]=0;
                    }
                }
                break;
        }
        System.out.println("经过POVM测量之后系统的态为");
        System.out.println(state.showBinaryState());
        state.showParticleName();
        return true;
    }

    public static boolean isZeroMatrix(Matrix matrix){
        for(int i=0;i<matrix.getRowCount();i++){
            for(int j=0;j<matrix.getColumnCount();j++){
                double result = (double)Math.round(matrix.getAsDouble(i,j)*10000)/10000;  //保留小数点后四位
                if(result!=0){
                    return false;
                }
            }
        }
        return true;
    }
}
