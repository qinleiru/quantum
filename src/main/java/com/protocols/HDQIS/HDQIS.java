package com.protocols.HDQIS;

import com.protocols.HDQIS.HighAgent;
import com.protocols.HDQIS.LowAgent;
import com.quantum.gate.QuantumGate;
import com.quantum.measure.Measures;
import com.quantum.measure.ProjectiveMeasure;
import com.quantum.oparate.MathOperation;
import com.quantum.oparate.QuantumOperation;
import com.quantum.state.*;
import com.quantum.tools.QuantumState;
import com.quantum.tools.Tools;

import java.util.ArrayList;

/*
    此协议有三个代理者，一个发送者,发送者Alice，代理者Bob，Charlie，David，其中David的权限高的代理者
 */
//下面仿真了2010年Wang等人提出的确定型分层量子信息通信过程
public class HDQIS {
    public static MultiState systemState;  //记录系统的态
    public static int resultSA;            //记录对粒子S、粒子A进行Bell态的测量结果
    public static void run(){
        ArrayList<HighAgent> highAgents = new ArrayList<>();    //用于存储权限高的代理者
        HighAgent Diana=new HighAgent();
        highAgents.add(Diana);
        ArrayList<LowAgent> lowAgents=new ArrayList<>();        //用于存储权限低的代理者
        LowAgent Bob=new LowAgent();
        LowAgent Charlie=new LowAgent();
        lowAgents.add(Bob);
        lowAgents.add(Charlie);
        Sender Alice=new Sender(highAgents,lowAgents);
        //对发送者Alice手中的两个粒子进行Bell态的测量
        Alice.execute();
        boolean highAuthor=false;       //此变量用于标志哪一种权限的代理者来恢复秘密消息
        if(highAuthor){
            //协议中的权限高的代理者来恢复秘密量子比特
            System.out.println("权限高的代理者Diana恢复秘密量子比特");
            int random= Tools.randInt(1,2);
            if(random==1){
                System.out.println("权限低的代理者Bob对手中的粒子进行X基测量");
                Bob.measure(Measures.X);  //对手中的粒子进行测量
                System.out.println("Bob将测量结果发送给Diana");
                Bob.sendResult(Diana);
                QuantumOperation.quantumSinglePerform(systemState, "C", QuantumGate.Operator_H);
                /*
                    用于测试的代码
                 */
//                System.out.println("此时系统的态为");
//                System.out.println(systemState.showBinaryState());
//                systemState.showParticleName();
            }
            else{
                System.out.println("权限低的代理者Charlie对手中的粒子进行X基测量");
                Charlie.measure(Measures.X);    //对手中的粒子进行测量
                System.out.println("Charlie将测量结果发送给Diana");
                Charlie.sendResult(Diana);
                QuantumOperation.quantumSinglePerform(systemState, "B", QuantumGate.Operator_H);
                /*
                    用于测试的代码
                 */
//                System.out.println("此时系统的态为");
//                System.out.println(systemState.showBinaryState());
//                systemState.showParticleName();
            }
            System.out.println("Diana收到代理者测量结果");
            System.out.println("Diana对手中的粒子进行操作，恢复秘密量子比特");
            Diana.restore();
            System.out.println("此时Diana手中的粒子态为");
            System.out.println(getOwnSate(systemState,Diana.getParticleName()).showBinaryState());
        }
        else{
            //协议中权限低的代理者来恢复秘密量子比特
            int random=Tools.randInt(1,2);
            if(random==1){
                System.out.println("权限低的代理者Bob来恢复秘密量子比特");
                System.out.println("Charlie对手中的粒子进行Z基测量");
                Charlie.measure(Measures.Z);
                System.out.println("Charlie将测量结果发送给Bob");
                Charlie.sendResult(Bob);
                System.out.println("Diana对手中的粒子进行X基测量");
                Diana.measure(Measures.Z);
                System.out.println("Diana将测量结果发送给Bob");
                Diana.sendResult(Bob);
                System.out.println("Bob收到代理者测量结果");
                System.out.println("Bob对手中的粒子进行操作，恢复秘密量子比特");
                Bob.restore();
                System.out.println("此时Bob手中的粒子态为");
                System.out.println(getOwnSate(systemState,Bob.getParticleName()).showBinaryState());
            }
            else{
                System.out.println("权限低的代理者Charlie来恢复秘密量子比特");
                System.out.println("Bob对手中的粒子进行Z基测量");
                Bob.measure(Measures.Z);
                System.out.println("Bob将测量结果发送给Charlie");
                Bob.sendResult(Charlie);
                System.out.println("Diana对手中的粒子进行Z基测量");
                Diana.measure(Measures.Z);
                System.out.println("Diana将测量结果发送给Charlie");
                Diana.sendResult(Charlie);
                System.out.println("Charlie收到代理者测量结果");
                System.out.println("Charlie对手中的粒子进行操作，恢复秘密量子比特");
                Charlie.restore();
                System.out.println("此时Charlie手中的粒子态为");
                System.out.println(getOwnSate(systemState,Charlie.getParticleName()).showBinaryState());
            }
        }
        System.out.println("----------------------------------------------------");
    }
    public static SingleState getOwnSate(QuantumState quantumState,ArrayList<String> particleNames){
        SingleState state=new SingleState();
        //交换位置
        QuantumOperation.quantumSwap(quantumState,particleNames.get(0),systemState.getParticlesName().get(systemState.getParticlesName().size()-1));
        double[] ownState=new double[2];
        try {
            for (int i = 0, j = 0; i < Math.pow(2, quantumState.getParticles()); i++) {
                if (quantumState.getState()[i] != 0) {
                    ownState[j++] = quantumState.getState()[i];
                }
            }
        }
        catch(ArrayIndexOutOfBoundsException e){
            System.out.println("恢复秘密消息失败");
            return null;
        }
        state.setState(ownState);
        return state;
    }

    public static void main(String[] args){
        for(int i=0;i<16;i++) {
            run();
        }
    }
}
