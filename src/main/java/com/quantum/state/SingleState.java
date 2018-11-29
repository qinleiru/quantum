package com.quantum.state;

import com.quantum.tools.QuantumState;

public class SingleState implements QuantumState {
    public final int particles=1;
    private double[] state;
    //随机生成一个单量子比特
    public SingleState(){
        double a=Math.random();
        state=new double[]{a,Math.sqrt(1-Math.pow(a,2))};
    }
    //生成指定的单量子比特
    public SingleState(double[] state){
        this.state=state;
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

    @Override
    public void displayState() {
        for(int i=0;i<Math.pow(2,particles);i++){
            System.out.println(state[i]+" ");
        }
        System.out.println();
    }

    @Override
    public String showState() {
        String result="";
        for(int i=0;i<Math.pow(2,particles);i++){
            String str=Integer.toBinaryString(i);
            if(str.length()<particles){
                str="0"+str;
            }
            if(state[i]>0&&i!=0) {
                result =result+"+"+ state[i] + "|" + str + ">";
            }
            else{
                result =result+ state[i] + "|" + str + ">";
            }
        }
        return result;
    }
}
