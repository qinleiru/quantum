package com.protocols.HDQIS;

import com.quantum.measure.ProjectiveMeasure;
import com.quantum.oparate.MathOperation;
import com.quantum.oparate.QuantumOperation;
import com.quantum.state.*;
import com.quantum.tools.QuantumTools;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static com.protocols.HDQIS.HDQIS.systemState;

public class Sender implements com.protocols.role.Sender {
    private SingleState singleState;
    private ClusterState clusterState;
    private ArrayList<HighAgent> highAgents;
    private ArrayList<LowAgent> lowAgents;
    public  String printMessage="";

    public Sender(ArrayList<HighAgent> highAgents, ArrayList<LowAgent> lowAgents){
        this.highAgents=highAgents;
        this.lowAgents=lowAgents;
    }

    public void execute(){
        secret();
        prepareState();
        send();
        measure();
    }
    /**
     * 发送者准备秘密态用于代理者们的共享
     */
    public void secret() {
        singleState=new SingleState();
        singleState.setParticlesName(1,"S");
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        printMessage+=df.format(System.currentTimeMillis())+" "+ "Alice准备秘密量子态为"+ QuantumTools.showBinaryState(singleState)+"\n";
    }

    /**
     * 构造四粒子纠缠态，用于构建量子信道
     */
    public void prepareState(){
        //首先构造使用的四粒子纠缠态，根据论文中的公式
        double[]state0=CommonState.Zero_State.getState();
        double[]state1=CommonState.One_State.getState();
        double[] base1= MathOperation.tensor(new double[][]{state0,state0,state0,state0});
        double[] base2=MathOperation.multiple(MathOperation.tensor(new double[][]{state0,state0,state1,state1}),-1);
        double[] base3=MathOperation.multiple(MathOperation.tensor(new  double[][]{state0,state1,state0,state1}),-1);
        double[] base4=MathOperation.tensor(new double[][]{state0,state1,state1,state0});
        double[] base5=MathOperation.tensor(new double[][]{state1,state0,state0,state1});
        double[] base6=MathOperation.tensor(new double[][]{state1,state0,state1,state0});
        double[] base7=MathOperation.tensor(new double[][]{state1,state1,state0,state0});
        double[] base8=MathOperation.tensor(new double[][]{state1,state1,state1,state1});
        double[] state=MathOperation.add(new double[][]{base1,base2,base3,base4,base5,base6,base7,base8});
        state=MathOperation.multiple(state,0.5*Math.pow(2,-0.5));
        clusterState=new ClusterState(state,4);
        clusterState.setParticlesName(1,"A");
        clusterState.setParticlesName(2,"B");
        clusterState.setParticlesName(3,"C");
        clusterState.setParticlesName(4,"D");
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        printMessage+=df.format(System.currentTimeMillis())+" "+ "Alice准备量子信道需要的纠缠态\n";
    }

    /**
     * 给三个代理者发送需要的粒子
     */
    public void send(){
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        //发送粒子给高权限的代理者
        ArrayList<String> particlesName1=new ArrayList<>();
        particlesName1.add("D");
        highAgents.get(0).recieveParticles(particlesName1);
        printMessage+=df.format(System.currentTimeMillis())+" "+ "Alice将粒子D发送给权限高的代理者Diana\n";
        //发送粒子给权限低的代理者
        ArrayList<String> particlesName2=new ArrayList<>();
        particlesName2.add("B");
        lowAgents.get(0).recieveParticles(particlesName2);
        printMessage+=df.format(System.currentTimeMillis())+" "+ "Alice将粒子B发送给权限低的代理者Bob\n";
        ArrayList<String> particlesName3=new ArrayList<>();
        particlesName3.clear();
        particlesName3.add("C");
        lowAgents.get(1).recieveParticles(particlesName3);
        printMessage+=df.format(System.currentTimeMillis())+" "+ "Alice将粒子C发送给权限低的代理者Charlie\n";
    }

    /**
     * 对手中的粒子进行Bell态测量，并公布测量结果
     */
    public void measure() {
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        int highParticle=this.highAgents.size();
        int lowParticle=this.lowAgents.size();
        //整个系统的初始状态为
        systemState=QuantumOperation.quantumTensor(singleState,clusterState);
        /*
           测试代码
         */
//        System.out.println("初始状态系统的态为");
//        System.out.println(systemState.showBinaryState());
//        systemState.showParticleName();
        //Alice对粒子x和粒子A，进行Bell态测量，并公布测量结果
        HDQIS.resultSA= ProjectiveMeasure.measureBeseBell(systemState,"S","A");
        printMessage+=df.format(System.currentTimeMillis())+" "+ "Alice对粒子S、粒子A进行Bell态测量，并公布测量结果";
        /*
           此部分的代码是测试的内容
         */
//        System.out.println("发送者测量完成之后系统的态为");
//        System.out.println(systemState.showBinaryState());
//        systemState.showParticleName();
    }
}
