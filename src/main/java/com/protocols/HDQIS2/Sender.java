package com.protocols.HDQIS2;
import com.protocols.role.AbstractSender;
import com.quantum.gate.QuantumGate;
import com.quantum.measure.ProjectiveMeasure;
import com.quantum.oparate.QuantumOperation;
import com.quantum.state.*;
import com.quantum.tools.QuantumTools;

import java.util.ArrayList;

import static com.protocols.HDQIS2.HDQIS2.systemState;


//概率型分层量子信息拆分协议
//发送者的角色
public class Sender extends AbstractSender {
    private DoubleState doubleState;
    private ClusterState clusterState1;
    private ClusterState clusterState2;
    private ArrayList<HighAgent> highAgents;
    private ArrayList<LowAgent> lowAgents;

    public Sender(ArrayList<HighAgent> highAgents,ArrayList<LowAgent> lowAgents){
        this.highAgents=highAgents;
        this.lowAgents=lowAgents;
    }

    /**
     * 发送者准备秘密态用于代理者们的共享n
     */
    public void secret() {
        doubleState=new DoubleState();
        //设置两量子比特的名字
        doubleState.setParticlesName(1,"x");
        doubleState.setParticlesName(2,"y");
        /**
         * 用于测试的代码
         */
        System.out.println("Alice准备的秘密量子态为"+ QuantumTools.showBinaryState(doubleState));
    }

    /**
     * 准备两个多粒子纠缠簇态，用于构建量子信道
     */
    public void prepareState(){
        /**
         * 根据论文指定的方法生成用于构建量子信道的两个簇态
         */
        int highParticle=this.highAgents.size();
        int lowParticle=this.lowAgents.size();
        //准备固定的两粒子簇态
        double []states=new double[]{0.5,0.5,0.5,-0.5};
        DoubleState prepareState=new DoubleState(states);
        //控制粒子1的粒子名
        prepareState.setParticlesName(1,"1");
        //控制粒子2的粒子名
        prepareState.setParticlesName(2,""+(highParticle+2));
        //准备初始态为0的两粒子纠缠态
        SingleState singleState= CommonState.Zero_State;

        singleState.setParticlesName(1,"2");
        MultiState multiState=QuantumOperation.quantumTensor(prepareState,singleState);
        //初始化权限高的代理者需要粒子
        for(int i=2;i<=highParticle;i++){
            singleState.setParticlesName(1,""+(i+1));
            multiState=QuantumOperation.quantumTensor(multiState,singleState);
        }
        //初始化权限低的代理者需要粒子
        for (int i=2;i<=lowParticle;i++){
            singleState.setParticlesName(1,""+(highParticle+1+i));
            multiState= QuantumOperation.quantumTensor(multiState,singleState);

        }
        //进行CNOT操作,高等级的用粒子1做控制门，低等级用粒子2做控制门
        for(int i=1;i<=highParticle;i++){
            QuantumOperation.quantumDoublePerform(multiState,"1",""+(i+1),QuantumGate.Operator_CNOT);
        }
        for (int i=2;i<=lowParticle;i++){
            QuantumOperation.quantumDoublePerform(multiState,""+(highParticle+2),""+(highParticle+1+i),QuantumGate.Operator_CNOT);
        }
        clusterState1=new ClusterState(multiState.getState(),multiState.getParticles());
        ArrayList<String> arrayList=multiState.getParticlesName();
        for(int i=0;i<arrayList.size();i++){
            clusterState1.setParticlesName(i+1,arrayList.get(i));
        }
        clusterState2=new ClusterState(clusterState1.getState(),clusterState1.getParticles());
        arrayList=clusterState1.getParticlesName();
        for(int i=0;i<arrayList.size();i++){
            clusterState2.setParticlesName(i+1,(Integer.parseInt(arrayList.get(i))+clusterState1.getParticles())+"");
        }
        /**
         * 测试
         */
//        System.out.println("准备的第一个簇态为\n"+clusterState1.showBinaryState());
//        clusterState1.showParticleName();
//        System.out.println();
//
//        System.out.println("准备的第二个簇态为\n"+clusterState2.showBinaryState());
//        clusterState2.showParticleName();
//        System.out.println();
    }

    /**
     * 给相应的代理者发送需要的粒子
     */
    public void send(){
        int highParticle=this.highAgents.size();
        int particle=this.highAgents.size()+this.lowAgents.size()+1;
        //发送粒子给高权限的代理者
        for(int i=0;i<highAgents.size();i++){
            ArrayList<String> particlesName=new ArrayList<>();
            System.out.println("权限高的代理者"+(i+1)+"发送粒子"+(i+2));
            //簇态1中的粒子
            particlesName.add(""+(i+2));
            System.out.println("权限高的代理者"+(i+1)+"发送粒子"+(particle+(i+2)));
            //簇态2中的粒子
            particlesName.add(""+(particle+(i+2)));
            highAgents.get(i).recieveParticles(particlesName);
        }
        //发送粒子给低权限的代理者
        for(int i=0;i<lowAgents.size();i++){
            ArrayList<String> particlesName=new ArrayList<>();
            System.out.println("权限低的代理者"+(i+1)+"发送粒子"+(highParticle+i+2));
            //簇态1中的粒子
            particlesName.add(""+(highParticle+i+2));
            System.out.println("权限低的代理者"+(i+1)+"发送粒子"+(particle+highParticle+(i+2)));
            //簇态2中的粒子
            particlesName.add(""+(particle+highParticle+(i+2)));
            lowAgents.get(i).recieveParticles(particlesName);
        }
    }

    /**
     * 对手中的粒子进行Bell态测量，并公布测量结果
     */
    public void measure() {
        int highParticle=this.highAgents.size();
        int lowParticle=this.lowAgents.size();
        //整个系统的初始状态为
        systemState=QuantumOperation.quantumTensor(QuantumOperation.quantumTensor(doubleState,clusterState1),clusterState2);
        /*
           测试代码
         */
//        System.out.println("初始状态系统的态为");
//        System.out.println(systemState.showBinaryState());
//        systemState.showParticleName();
        //Alice对粒子x和粒子1，进行Bell态测量
        int resultX=ProjectiveMeasure.measureBeseBell(systemState,"x","1");
        //公布测量结果
        HDQIS2.resultX =resultX;
        //Alice对粒子y和粒子5，进行Bell态测量
        int resultY=ProjectiveMeasure.measureBeseBell(systemState,"y",(highParticle+lowParticle+2)+"");
        //公布测量结果
        HDQIS2.resultY =resultY;
        /*
           此部分的代码是测试的内容
         */
//        System.out.println("发送者测量完成之后系统的态为");
//        System.out.println(systemState.showBinaryState());
//        systemState.showParticleName();
    }
}
