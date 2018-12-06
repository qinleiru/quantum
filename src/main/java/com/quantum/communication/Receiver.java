package com.quantum.communication;

import com.quantum.measure.Measures;

public interface Receiver {
    public void receive(String particle);
    public int measure(String particle, Measures measures);
}
