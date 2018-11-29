package com.quantum.communication;

import com.quantum.tools.QuantumState;

public interface Sender {
    public void secret();
    public void send(Reciever reciever);
    public void measure();
}
