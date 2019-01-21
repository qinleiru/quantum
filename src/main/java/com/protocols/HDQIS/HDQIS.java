package com.protocols.HDQIS;

import com.quantum.gate.QuantumGate;
import com.quantum.measure.Measures;
import com.quantum.oparate.QuantumOperation;
import com.quantum.state.*;
import com.quantum.state.QuantumState;
import com.quantum.tools.Tools;
import com.view.component.TextComponent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/*
    此协议有三个代理者，一个发送者,发送者Alice，代理者Bob，Charlie，David，其中David的权限高的代理者
 */
//下面仿真了2010年Wang等人提出的确定型分层量子信息通信过程
public class HDQIS {
    public static MultiState systemState;  //记录系统的态
    public static int resultSA;            //记录对粒子S、粒子A进行Bell态的测量结果
    public static void run(TextComponent textArea,String agents){
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
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Alice.execute();
        textArea.setCommText(Alice.printMessage);
        boolean highAuthor=false;       //此变量用于标志哪一种权限的代理者来恢复秘密消息
        switch ( agents) {
            case "权限高":
                highAuthor=true;
                break;
            case "权限低":
                highAuthor=false;
                break;
        }
        if(highAuthor){
            //协议中的权限高的代理者来恢复秘密量子比特
            textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Diana恢复秘密量子比特");
            int random= Tools.randInt(1,2);
            if(random==1){
                textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Bob对手中的粒子进行X基测量");
                Bob.measure(Measures.X);  //对手中的粒子进行测量
                textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Bob将测量结果发送给Diana");
                Bob.sendResult(Diana);
                QuantumOperation.quantumSinglePerform(systemState, "C", QuantumGate.Operator_H);
            }
            else{
                textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Charlie（权限低）对手中的粒子进行X基测量");
                Charlie.measure(Measures.X);    //对手中的粒子进行测量
                textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Charlie将测量结果发送给Diana");
                Charlie.sendResult(Diana);
                QuantumOperation.quantumSinglePerform(systemState, "B", QuantumGate.Operator_H);
            }
            textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Diana收到代理者测量结果");
            System.out.println("Diana对手中的粒子进行操作，恢复秘密量子比特");
            Diana.restore();
            textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Diana对手中的粒子进行操作，恢复秘密量子比特");
            textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Diana恢复的秘密量子比特"+getOwnSate(systemState,Diana.getParticleName()).showBinaryState());
        }
        else{
            //协议中权限低的代理者来恢复秘密量子比特
            int random=Tools.randInt(1,2);
            if(random==1){
                textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Bob恢复秘密量子比特");
                textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Charlie对手中的粒子进行Z基测量");
                Charlie.measure(Measures.Z);
                textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Charlie将测量结果发送给Bob");
                Charlie.sendResult(Bob);
                textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Diana对手中的粒子进行X基测量");
                Diana.measure(Measures.Z);
                textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Diana将测量结果发送给Bob");
                Diana.sendResult(Bob);
                textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Bob收到代理者测量结果");
                textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Bob对手中的粒子进行操作，恢复秘密量子比特");
                Bob.restore();
                textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Bob恢复的秘密量子比特"+getOwnSate(systemState,Bob.getParticleName()).showBinaryState());
            }
            else{
                textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Charlie恢复秘密量子比特");
                textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Bob对手中的粒子进行Z基测量");
                Bob.measure(Measures.Z);
                textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Bob将测量结果发送给Charlie");
                Bob.sendResult(Charlie);
                textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Diana对手中的粒子进行Z基测量");
                Diana.measure(Measures.Z);
                textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Diana将测量结果发送给Charlie");
                Diana.sendResult(Charlie);
                textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Charlie收到代理者测量结果");

                System.out.println("Charlie收到代理者测量结果");
                textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Charlie对手中的粒子进行操作，恢复秘密量子比特");
                Charlie.restore();
                textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Charile恢复的秘密量子比特"+getOwnSate(systemState,Charlie.getParticleName()).showBinaryState());
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

//    public static void main(String[] args){
//        for(int i=0;i<16;i++) {
//            run();
//        }
//    }
}
