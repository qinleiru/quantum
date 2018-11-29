package com.quantum.communication;

import com.quantum.state.ClusterState;
import com.quantum.state.DoubleState;

public  class Alice implements Sender {
    private DoubleState doubleState;
    private ClusterState clusterState1;
    private ClusterState clusterState2;
    public Alice(){

    }
    /**
     * 发送者准备秘密态用于代理者们的共享
     */
    @Override
    public void secret() {
        double [] doubles=new double[]{0.5,0.5,0.5,0.5};
        this.doubleState=new DoubleState(doubles);
    }

    /**
     * 发送构成量子信道的粒子给代理者
     */
    @Override
    public void send(Reciever reciever){
        prepareState();
//        doSend(reciever);
    }

    /**
     * 对手中的粒子进行Bell态测量
     */
    @Override
    public void measure() {

    }
    /**
     * 准备四粒子簇态
     */
    public void prepareState(){
        double []states=new double[]{0.5,0,0,0.5,0,0,0,0,0,0,0,0,0.5,0,0,-0.5};
        clusterState1=new ClusterState(states,4);
        clusterState2=new ClusterState(states,4);
    }
}
