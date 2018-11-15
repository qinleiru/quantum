package com.quantum.state;

import com.quantum.tools.QuantumState;

public class OneState implements QuantumState {
    private int particles;
    private double[] state;
    //生成指定的单量子比特
    public OneState(double[] state){
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
    public int getPartitles() {
        return this.particles;
    }

    @Override
    public void setParticles(int num) {
        this.particles=num;
    }
}
