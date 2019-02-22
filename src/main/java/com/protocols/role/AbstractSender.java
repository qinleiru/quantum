package com.protocols.role;


public abstract class AbstractSender {

    public final void execute(){
        secret();
        prepareState();
        send();
        measure();
    }
    /**
     * 准备要传送的秘密量子比特
     */
    public abstract void secret();

    /**
     * 构建量子信道所用的纠缠态
     */
    public abstract void prepareState();

    /**
     * 代理者对手中的所有粒子进行单粒子测量并在信道中公布
     */
    public abstract void measure();

    /**
     * 将信道中粒子发送到代理者手中
     */
    public  abstract void send();

}
