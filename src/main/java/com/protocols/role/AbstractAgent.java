package com.protocols.role;

import com.quantum.measure.Measures;

import java.util.ArrayList;
import java.util.HashMap;



//代理者角色
//todo:代理者对手中的粒子进行的操作都是一致的
public abstract class AbstractAgent {
    public ArrayList<String> particleName=new ArrayList<String>(); //代理者手中的粒子
    public HashMap<String,Integer> measureResult=new HashMap<String,Integer>();  //不同粒子的测量结果

    /**
     * 接收发送来的粒子
     */
    public  void recieveParticles(ArrayList<String> particleName){
        this.particleName = particleName;
    }

    /**
     * 帮助代理者恢复秘密消息
     * @param measures
     * @param agent
     */
    public final void helpRestore(Measures measures,AbstractAgent agent){
        measure(measures);
        sendResult(agent);
    }

    /**
     * 代理者对手中的所有粒子进行单粒子测量
     */
    public abstract void measure(Measures measures);

    /**
     * 将测量结果发送给要恢复秘密消息的代理者
     */
    public void sendResult(AbstractAgent agent){
        agent.recieveResult(measureResult);
    };


    /**
     * 接收来自其他代理者的测量结果
     */
    public   void recieveResult(HashMap<String, Integer> measureResult){
        for(String key:measureResult.keySet()){
            this.measureResult.put(key,measureResult.get(key));
        }
    };

    /**
     *  代理者恢复秘密消息,不同权限的代理者具体不同
     */
    public abstract void restore();

}
