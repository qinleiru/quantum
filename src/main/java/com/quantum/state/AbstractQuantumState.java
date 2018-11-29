package com.quantum.state;

import com.quantum.tools.QuantumState;

public abstract class AbstractQuantumState implements QuantumState {
    private int particles;
    private double [] state;
    public String showState(){
        String result=null;
        for(int i=0;i<Math.pow(2,particles);i++){
            String str=Integer.toBinaryString(i);
            if(str.length()<particles){
                str="0"+str;
            }
            result+=state[i]+"|"+str+">";
        }
        return result;
    }
}
