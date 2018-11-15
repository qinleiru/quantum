package com.quantum.state;

import com.quantum.tools.QuantumState;

public class ClusterState implements QuantumState {
    private int particles;
    private double[] state;

    @Override
    public double[] getState() {
        return new double[0];
    }

    @Override
    public void setState(double[] state) {
        this.state=state;
    }

    @Override
    public int getPartitles() {
        return 0;
    }

    @Override
    public void setParticles(int particles) {
        this.particles=particles;
    }
}
