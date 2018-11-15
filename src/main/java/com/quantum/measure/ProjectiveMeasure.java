//package com.quantum.measure;
//
//import com.quantum.tools.QuantumState;
//import com.sun.org.apache.xalan.internal.xsltc.runtime.Operators;
//import com.sun.org.apache.xpath.internal.operations.Operation;
//
//public class ProjectiveMeasure {
//    //Z基的单粒子测量
//    public static int measureBaseZ(QuantumState state,int pos){
//        double[] states=state.getState();
//        double zeroProb=0.0;
//        double oneProb=0.0;
//        for(int i=0;i<states.length;i++){
//            if(isBitZero(i,pos,state.getPartitles())){
//                zeroProb+=Math.pow(states[i],2);
//            }
//        }
//        double random=Math.random();
//        int result=0;
//        if(random<zeroProb)
//            result=0;
//        else
//            result=1;
//        if (result==0){
//            for(int i=0;i<states.length;i++){
//                if(isBitOne(i,pos,state.getPartitles())){
//                    states[i]=0;
//                }
//            }
//        }else{
//            for(int i=0;i<states.length;i++){
//                if(isBitZero(i,pos,state.getPartitles())){
//                    states[i]=0;
//                }
//            }
//        }
//        Operation.normalization(states);
//        return result;
//    }
//    //对量子态进行Bell基测量
//    public static int measureBeseBell(QuantumState state){
//        double[][] temp= Operators.Operator_I;
//        temp= Operation.operatorTensor(temp,Operators.Operator_U);
//        double[] st=state.getState();
//        temp=Operation.innerProduct(temp,OPeration.transposition(st));
//        state.setState(Operation.vecToArray(temp));
//        int p3=measureBaseZ(state,3);
//        int p4=measureBaseZ(state,4);
//        int result=0;
//        if(p3==0){
//            if(p4==0)
//                result=1;
//            else
//                result=2;
//        }else{
//            if(p4==0)
//                result=3;
//            else
//                result=4;
//        }
//        return result;
//    }
//}
