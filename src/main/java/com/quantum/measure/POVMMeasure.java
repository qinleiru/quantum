package com.quantum.measure;

import com.quantum.gate.QuantumGate;
import com.quantum.oparate.QuantumOperation;
import com.quantum.state.DoubleState;
import com.quantum.tools.QuantumState;
import com.quantum.tools.Range;
import org.ujmp.core.DenseMatrix;
import org.ujmp.core.Matrix;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;

public class POVMMeasure {
    /**
     *
     * @param state      系统的态
     * @param oparators   POVM 采用的算子
     * @param doubleStates    要区分的量子态
     * @return
     */
    public static boolean measurePOVM(QuantumState state, ArrayList<Matrix> oparators, ArrayList<DoubleState> doubleStates){
        Matrix matrix= DenseMatrix.Factory.zeros(4, 1);
        LinkedHashMap<DoubleState, Range> probabilities=new LinkedHashMap<>();
        //计算坍缩到不同量子态的概率
        for(int i=0;i<doubleStates.size();i++){
            for(int j=0;j<oparators.size();j++){
                Matrix temp=Matrix.Factory.importFromArray(doubleStates.get(i).getState()).transpose();
                if(!oparators.get(j).mtimes(temp).equals(matrix)){
                    Matrix probability=Matrix.Factory.importFromArray(doubleStates.get(i).getState()).mtimes(temp);
                    Range range;
                    if(i==0) {
                        range=new Range(0,probability.doubleValue());
                        probabilities.put(doubleStates.get(i), range);
                    }
                    else{
                        range=new Range(probabilities.get(i-1).getEnd(),probabilities.get(i-1).getEnd()+probability.doubleValue());
                        probabilities.put(doubleStates.get(i),range);
                    }
                }
            }
        }
        double random= new Random().nextDouble();
        //进行量子门操作，模拟坍缩
        double[][] quantumGate={doubleStates.get(0).getState(),doubleStates.get(1).getState(),doubleStates.get(2).getState(),doubleStates.get(3).getState()};
        QuantumOperation.quantumDoublePerform(state,doubleStates.get(0).getParticlesName().get(0),doubleStates.get(0).getParticlesName().get(1),quantumGate);
        int result=0;
        for (int i=0;i<probabilities.size();i++){
            if(random>=probabilities.get(i).getStart()&&random<probabilities.get(i).getEnd()){
                 result=i;
            }
            else if(random>=probabilities.get(i).getStart()&&random<probabilities.get(i).getEnd()){
                result=i;
            }
            else if(random>=probabilities.get(i).getStart()&&random<probabilities.get(i).getEnd()){
                result=i;
            }
            else if(random>=probabilities.get(i).getStart()&&random<probabilities.get(i).getEnd()){
                result=i;
            }
            else{
                return false;
            }
        }
        int size=state.getParticlesName().size();
        QuantumOperation.quantumSwap(state,state.getParticlesName().get(size-2),doubleStates.get(0).getParticlesName().get(0));
        QuantumOperation.quantumSwap(state,state.getParticlesName().get(size-1),doubleStates.get(0).getParticlesName().get(1));
        switch(result){
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
        return true;
    }
}
