package com.quantum.measure;

import com.quantum.gate.QuantumGate;
import com.quantum.oparate.MathOperation;
import com.quantum.state.ClusterState;
import com.quantum.tools.QuantumState;

public class ProjectiveMeasure {

    public static boolean isBitZero(int decNum,int pos,int particles){
        String binStr = "";
        for(int i= particles-1;i>=0;i--) {
            binStr +=(decNum>>i)&1;
        }
        return binStr.charAt(pos-1)=='0';
    }
     public  static  boolean isBitOne(int decNum,int pos,int particles){
         String binStr = "";
         for(int i= particles-1;i>=0;i--) {
             binStr +=(decNum>>i)&1;
         }
         return binStr.charAt(pos-1)=='1';
     }
    //Z基的单粒子测量
    public static int measureBaseZ(QuantumState state,int pos){
        double[] states=state.getState();
        double zeroProb=0.0;
        double oneProb=0.0;
        //测得结果为0的概率
        for(int i=0;i<states.length;i++){
            if(isBitZero(i,pos,state.getParticles())){
                zeroProb+=Math.pow(states[i],2);
            }
        }
        oneProb=1-zeroProb;
        double random=Math.random();
        int result=0;
        if(random<zeroProb)
            result=0;
        else
            result=1;

        //发生测量坍缩
        if (result==0){
            for(int i=0;i<states.length;i++){
                if(isBitOne(i,pos,state.getParticles())){
                    states[i]=0;
                }
            }
        }else{
            for(int i=0;i<states.length;i++){
                if(isBitZero(i,pos,state.getParticles())){
                    states[i]=0;
                }
            }
        }
        MathOperation.normalization(states);
        state.setState(states);
        return result;
    }

    /**
     *  X基的单粒子测量
     * @param state
     * @param pos
     * @return 0代表测量结果为+ 1代表测量结果为-
     */
    public static int measureBaseX(QuantumState state,int pos){
        int particles=state.getParticles();
        MathOperation.performOperator(state,pos, QuantumGate.Operator_H);
        int result=measureBaseZ(state,pos);
        return result;
    }
    /**
     * 进行Bell态测量
     * @param state
     * @param pos 代表要测量的粒子，测量的粒子两个相邻
     * @return  测量结果1代表|Ф>+态 2代表|𝝭>+态 3代表|Ф>-态 4代表|𝝭>-态
     */
    public static int measureBeseBell(QuantumState state,int pos[]){
        MathOperation.performOperator(state,pos,QuantumGate.Operator_U);
        double[] st=state.getState();
        int result1=measureBaseZ(state,pos[0]);
        int result2=measureBaseZ(state,pos[1]);
        int result=0;
        if(result1==0){
            if(result2==0)
                result=1;
            else
                result=2;
        }else{
            if(result2==0)
                result=3;
            else
                result=4;
        }
        return result;
    }
}
