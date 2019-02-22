package com.quantum.tools;

import com.quantum.state.QuantumState;

import java.util.ArrayList;
import java.util.Iterator;

public class QuantumTools {
    /**
     * 以二进制形式输出量子态，第一个参数符号为正好
     * @return
     */
    public static String showBinaryState(QuantumState quantumState) {
        int particles=quantumState.getParticles();
        double []states=quantumState.getState();
        String result="量子态为";
        for(int i=0;i<Math.pow(2,particles);i++){
            String str= Tools.toFixedLenBinary(i,particles);
            if(states[i]>0&&result.length()!=0) {
                result =result+"+"+ states[i] + "|" + str + ">";
            }
            if (states[i]>0&&result.length()==0){
                result =result+ states[i] + "|" + str + ">";
            }
            else if(states[i]<0){
                result =result+ states[i] + "|" + str + ">";
            }
        }

        ArrayList<String> arrayList=quantumState.getParticlesName();
        Iterator iterator=arrayList.iterator();
        result+="  对应的粒子为";
        while(iterator.hasNext()){
            result+=iterator.next()+"  ";
        }
        return result;
    }
}
