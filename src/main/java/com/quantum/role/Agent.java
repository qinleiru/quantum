package com.quantum.role;

import com.quantum.measure.Measures;

import java.util.ArrayList;
import java.util.HashMap;


//代理者角色
//todo:代理者对手中的粒子进行的操作都是一致的
public interface Agent {
    /**
     * 代理者对手中的所有粒子进行单粒子测量
     */
    void measure(Measures measures);

    /**
     * 将测量结果发送给要恢复秘密消息的代理者
     */
    void sendResult(Agent agent);


    /**
     * 接收来自其他代理者的测量结果
     */
    void recieveResult(HashMap<String, Integer> measureResult);


    /**
     * 接收发送来的粒子
     */
    void recieveParticles(ArrayList<String> particleName);

    /**
     *  代理者恢复秘密消息,不同权限的代理者具体不同
     */
    void  restore();
}
