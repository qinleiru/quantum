package com.quantum.role;

public interface Sender {

    /**
     * 准备要传送的秘密量子比特
     */
    void secret();

    /**
     *
     */
    void prepareState();

    /**
     * 代理者对手中的所有粒子进行单粒子测量
     */
    void measure();

    /**
     * 将粒子发送到代理者手中
     */
    void send();

}
