package com.quantum.state;

import com.quantum.tools.QuantumState;

public class MultiState implements QuantumState {
    private int particles;
    private double [] state;
    public MultiState(double[] state,int particles){
        this.state=state;
        this.particles=particles;
    }
    @Override
    public double[] getState() {
        return this.state;
    }

    @Override
    public void setState(double[] state) {
        this.state=state;
    }

    @Override
    public int getParticles() {
        return this.particles;
    }

    public void setParticle(int particles){
        this.particles=particles;
    }
    @Override
    public void displayState() {
        for(int i=0;i<Math.pow(2,particles);i++){
            System.out.println(state[i]+" ");
        }
        System.out.println();
    }

    @Override
    public String showState() {
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
