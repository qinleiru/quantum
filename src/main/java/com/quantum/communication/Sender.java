package com.quantum.communication;

public interface Sender {
    public void secret();
    public void send(Receiver receiver, String particles);
    public void measure();
}
