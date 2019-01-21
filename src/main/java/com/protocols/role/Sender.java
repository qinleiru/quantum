package com.protocols.role;

public interface Sender {

    /**
     * 准备要传送的秘密量子比特
     */
    void secret();

    /**
     * 构建量子信道所用的纠缠态
     */
    void prepareState();

    /**
     * 代理者对手中的所有粒子进行单粒子测量并在信道中公布
     */
    void measure();

    /**
     * 将信道中粒子发送到代理者手中
     */
    void send();

}
