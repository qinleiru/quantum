package com.quantum.measure;

import com.quantum.gate.QuantumGate;
import com.quantum.oparate.MathOperation;
import com.quantum.oparate.QuantumOperation;
import com.quantum.state.QuantumState;

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

    /**
     * Z基的单粒子测量
     * @param state
     * @param particle
     * @return    0表示测量结果为|0>,1表示测量结果为|1>态
     */
    public static int measureBaseZ(QuantumState state, String particle){
        int pos=state.getParticlesName().indexOf(particle)+1;
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
     * @param particle
     * @return 0代表测量结果为|+>  1代表测量结果为|->
     */
    public static int measureBaseX(QuantumState state, String particle){
//        int pos=state.getParticlesName().indexOf(particle)+1;
//        int particles=state.getParticles();
        QuantumOperation.quantumSinglePerform(state,particle, QuantumGate.Operator_H);
        int result=measureBaseZ(state,particle);
        return result;
    }

    /**
     * 对两个粒子进行的Bell态的测量，包括两个粒子不相邻的情况
     * @param state
     * @param particle1
     * @param particle2
     * @return  返回结果是1表示测量结果|Ф>+态  返回结果是2表示测量结果|𝝭>+态   返回结果是3表示测量结果|Ф>-态   返回结果是4表示测量结果|𝝭>-态
     */
    public static int measureBeseBell(QuantumState state, String particle1,String particle2){
//        int index1=state.getParticlesName().indexOf(particle1);
//        int index2=state.getParticlesName().indexOf(particle2);
//        if (Math.abs(index1-index2)!=1){
//            //意味着两个粒子是不相邻的
//            if (index1<index2){
//                String swapParticle=state.getParticlesName().get(index1+1);
//                QuantumOperation.quantumSwap(state,swapParticle,particle2);
//            }
//            else {
//                String swapParticle=state.getParticlesName().get(index2+1);
//                QuantumOperation.quantumSwap(state,swapParticle,particle1);
//            }
//        }
//        int min=index1<index2?index1:index2;
//        int[]pos=new int[]{min+1,min+2};
        QuantumOperation.quantumDoublePerform(state,particle1,particle2,QuantumGate.Operator_U);
        double[] st=state.getState();
        int result1=measureBaseZ(state,particle1);
        int result2=measureBaseZ(state,particle2);
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
