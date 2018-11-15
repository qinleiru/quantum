package com.quantum.tools;

public interface QuantumState {
    double[] getState();
    void setState(double[] state);
    int getPartitles();
    void setParticles(int num);
}