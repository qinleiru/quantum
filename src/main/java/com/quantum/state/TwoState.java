package com.quantum.state;

import com.quantum.tools.QuantumState;

/**
 * 两粒子纠缠态
 */
public class TwoState implements QuantumState {
    private int particles;
    private double[] state;

    @Override
    public double[] getState() {
        return this.state;
    }

    @Override
    public void setState(double[] state) {
        this.state = state;
    }

    @Override
    public int getPartitles() {
        return this.particles;
    }

    @Override
    public void setParticles(int particles) {
        this.particles = particles;
    }
}
