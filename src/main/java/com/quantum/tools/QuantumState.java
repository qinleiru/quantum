package com.quantum.tools;

public interface QuantumState {
    double[] getState();
    void setState(double[] state);
    int getParticles();
    void displayState();
    String showState();
}