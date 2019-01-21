package com.protocols.HPQIS;

import com.quantum.gate.QuantumGate;
import com.quantum.measure.ProjectiveMeasure;
import com.quantum.oparate.QuantumOperation;
import com.quantum.state.*;
import com.quantum.tools.QuantumTools;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static com.protocols.HPQIS.HPQIS.coefficients;
import static com.protocols.HPQIS.HPQIS.systemState;

//概率型分层量子信息拆分协议
//发送者的角色
public class Sender implements com.protocols.role.Sender {
    private DoubleState doubleState;
    private ClusterState clusterState1;
    private ClusterState clusterState2;
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
     * 发送者准备秘密态用于代理者们的共享n
     */
    //todo：传送固定的两量子比特
    public void secret() {
        double [] secret=new double[]{0.5,0.5,0.5,0.5};
        doubleState=new DoubleState(secret);
        //设置两量子比特的名字
        doubleState.setParticlesName(1,"x");
        doubleState.setParticlesName(2,"y");
        /**
         * 用于测试的代码
         */
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        printMessage+=df.format(System.currentTimeMillis())+" "+ "Alice准备秘密量子态为"+QuantumTools.showBinaryState(doubleState)+"\n";
    }

    /**
     * 准备两个非最大纠缠态多粒子纠缠簇态，用于构建量子信道
     */
    public void prepareState(){
        /**
         * 根据论文指定的方法生成用于构建量子信道的两个簇态
         */
        int highParticle=this.highAgents.size();
        int lowParticle=this.lowAgents.size();
        //所用的系数为
        double a=coefficients[0];
        double b=coefficients[1];
        double c=coefficients[2];
        double d=coefficients[3];
        /*
          测试测试
         */
        System.out.println("a的值为"+a);
        System.out.println("b的值为"+b);
        System.out.println("c的值为"+c);
        System.out.println("d的值为"+d);
        //准备固定的两粒子簇态
        DoubleState prepareState1=new DoubleState(new double[]{a,a,b,-b});
        DoubleState prepareState2=new DoubleState(new double[]{c,c,d,-d});
        //簇态1中控制粒子1的粒子名
        prepareState1.setParticlesName(1,"1");
        //簇态1中控制粒子2的粒子名
        prepareState1.setParticlesName(2,""+(highParticle+2));
        //簇态2中控制粒子1的粒子名
        prepareState2.setParticlesName(1,""+(2+highParticle+lowParticle));
        //簇态2中控制粒子2的粒子名
        prepareState2.setParticlesName(2,""+(highParticle*2+lowParticle+3));

        //准备初始态为0的两粒子纠缠态
        SingleState singleState1= CommonState.Zero_State;
        singleState1.setParticlesName(1,"2");
        MultiState multiState1=QuantumOperation.quantumTensor(prepareState1,singleState1);
        SingleState singleState2=CommonState.Zero_State;
        singleState2.setParticlesName(1,""+(3+highParticle+lowParticle));
        MultiState multiState2=QuantumOperation.quantumTensor(prepareState2,singleState2);

        //初始化权限高的代理者需要粒子
        for(int i=2;i<=highParticle;i++){
            singleState1.setParticlesName(1,""+(i+1));
            multiState1=QuantumOperation.quantumTensor(multiState1,singleState1);
            singleState2.setParticlesName(1,""+(i+1+highParticle+lowParticle));
            multiState2=QuantumOperation.quantumTensor(multiState2,singleState2);
        }
        //初始化权限低的代理者需要粒子
        for (int i=2;i<=lowParticle;i++){
            singleState1.setParticlesName(1,""+(highParticle+1+i));
            multiState1= QuantumOperation.quantumTensor(multiState1,singleState1);
            singleState2.setParticlesName(1,""+(highParticle*2+lowParticle+2+i));
            multiState2=QuantumOperation.quantumTensor(multiState2,singleState2);
        }

        //进行CNOT操作,高等级的用粒子1做控制门，低等级用粒子2做控制门
        for(int i=1;i<=highParticle;i++){
            QuantumOperation.quantumDoublePerform(multiState1,"1",""+(i+1),QuantumGate.Operator_CNOT);
            QuantumOperation.quantumDoublePerform(multiState2,""+(highParticle+lowParticle+2),""+(highParticle+lowParticle+2+i),QuantumGate.Operator_CNOT);
        }
        for (int i=2;i<=lowParticle;i++){
            QuantumOperation.quantumDoublePerform(multiState1,""+(highParticle+2),""+(highParticle+1+i),QuantumGate.Operator_CNOT);
            QuantumOperation.quantumDoublePerform(multiState2,""+(highParticle*2+lowParticle+3),""+(highParticle*2+lowParticle+2+i),QuantumGate.Operator_CNOT);
        }
        clusterState1=new ClusterState(multiState1.getState(),multiState1.getParticles());
        ArrayList<String> arrayList=multiState1.getParticlesName();
        for(int i=0;i<arrayList.size();i++){
            clusterState1.setParticlesName(i+1,arrayList.get(i));
        }

        clusterState2=new ClusterState(multiState2.getState(),multiState2.getParticles());
        arrayList=multiState2.getParticlesName();
        for(int i=0;i<arrayList.size();i++){
            clusterState2.setParticlesName(i+1,arrayList.get(i));
        }
        /**
         * 测试
         */
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        printMessage+=df.format(System.currentTimeMillis())+" "+ "Alice准备量子信道需要的两个簇态，第一个簇态为"+ QuantumTools.showBinaryState(clusterState1)+"第二个簇态为"+QuantumTools.showBinaryState(clusterState2)+"\n";
    }

    /**
     * 给相应的代理者发送需要的粒子
     */
    public void send(){
        int highParticle=this.highAgents.size();
        int particle=this.highAgents.size()+this.lowAgents.size()+1;
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        //发送粒子给高权限的代理者
        for(int i=0;i<highAgents.size();i++){
            ArrayList<String> particlesName=new ArrayList<>();
//            System.out.println("权限高的代理者"+(i+1)+"发送粒子"+(i+2));
            //簇态1中的粒子
            particlesName.add(""+(i+2));
//            System.out.println("权限高的代理者"+(i+1)+"发送粒子"+(particle+(i+2)));
            //簇态2中的粒子
            particlesName.add(""+(particle+(i+2)));
            highAgents.get(i).recieveParticles(particlesName);
            printMessage+=df.format(System.currentTimeMillis())+" "+ "Alice发送粒子"+(i+2)+"、粒子"+(particle+(i+2))+"给权限高的代理者Bob\n";
        }
        //发送粒子给低权限的代理者
        for(int i=0;i<lowAgents.size();i++){
            ArrayList<String> particlesName=new ArrayList<>();
//            System.out.println("权限低的代理者"+(i+1)+"发送粒子"+(highParticle+i+2));
            //簇态1中的粒子
            particlesName.add(""+(highParticle+i+2));
//            System.out.println("权限低的代理者"+(i+1)+"发送粒子"+(particle+highParticle+(i+2)));
            //簇态2中的粒子
            particlesName.add(""+(particle+highParticle+(i+2)));
            lowAgents.get(i).recieveParticles(particlesName);
        }
        printMessage+=df.format(System.currentTimeMillis())+" "+ "Alice发送粒子3、粒子7给权限低的代理者Charlie\n";
        printMessage+=df.format(System.currentTimeMillis())+" "+ "Alice发送粒子4、粒子8给权限低的代理者David\n";

    }

    /**
     * 对手中的粒子进行Bell态测量，并公布测量结果
     */
    public void measure() {
        int highParticle=this.highAgents.size();
        int lowParticle=this.lowAgents.size();
        //整个系统的初始状态为
        systemState=QuantumOperation.quantumTensor(QuantumOperation.quantumTensor(doubleState,clusterState1),clusterState2);
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        /*
           测试代码
         */
//        System.out.println("初始状态系统的态为");
//        System.out.println(systemState.showBinaryState());
//        systemState.showParticleName();
        //Alice对粒子x和粒子1，进行Bell态测量
        int resultX=ProjectiveMeasure.measureBeseBell(systemState,"x","1");
        //公布测量结果
        HPQIS.resultX =resultX;
        //Alice对粒子y和粒子5，进行Bell态测量
        int resultY=ProjectiveMeasure.measureBeseBell(systemState,"y",(highParticle+lowParticle+2)+"");
        //公布测量结果
        HPQIS.resultY =resultY;
        /*
           此部分的代码是测试的内容
         */
//        System.out.println("发送者测量完成之后系统的态为");
//        System.out.println(systemState.showBinaryState());
//        systemState.showParticleName();
        printMessage+=df.format(System.currentTimeMillis())+" "+ "Alice对手中的粒子x、粒子1以及粒子y、粒子5分别进行Bell态测量，并公布测量结果";

    }
}
