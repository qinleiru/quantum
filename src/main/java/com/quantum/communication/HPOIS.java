package com.quantum.communication;

import com.quantum.gate.QuantumGate;
import com.quantum.measure.Measures;
import com.quantum.state.DoubleState;

import java.util.ArrayList;

//下面是分层量子信息拆分协议，
//todo：构成量子信道的为最大纠缠态，用于传送的秘密量子信息为固定的
public class HPOIS {
    public static void main(String[] args){
        //下面以三个代理者为例
        int high=1;
        int low=2;
        Alice alice=new Alice(high,low);
        ArrayList<Proxy> highProxy=new ArrayList<Proxy>();   //用于存储权限高的代理者
        ArrayList<Proxy> lowProxy=new ArrayList<Proxy>();    //用于存储权限低的代理者
        for(int i=1;i<=high;i++){
            highProxy.add(new Proxy());
        }
        for(int i=1;i<=low;i++){
            lowProxy.add(new Proxy());
        }
        //Alice的相关操作
        alice.process();  //进行了操作
        //下面Alice开始发送粒子
        //发送权限高的代理者需要的粒子,注意每个代理者会发送两个粒子
        for(int i=2;i<=high+1;i++){
            alice.send(highProxy.get(i-2),i+"");
            alice.send(highProxy.get(i-2),(i+4)+"");

        }
        //发送权限低的代理者需要的粒子
        for(int i=1;i<=low;i++){
            alice.send(lowProxy.get(i-1),(i+high+1)+"");
            alice.send(lowProxy.get(i-1),(i+high+5)+"");
        }
        int resultX=alice.getResultX();
        int resultY=alice.getResultY();
        System.out.println("对X粒子进行联合的Bell基的测量结果"+resultX);
        System.out.println("对Y粒子进行联合的Bell基的测量结果"+resultY);
        DoubleState doubleState=new DoubleState();
        //权限高的代理者恢复秘密消息
        System.out.println("权限高的代理者恢复秘密消息");
        //todo：专门为三个代理者做
        //恢复秘密消息的代理者
        Proxy secretProxy=highProxy.get(0);
        //权限低的代理者进行测量
        //需要剩余的权限低的代理者进行单
        Proxy measureProxy=lowProxy.get(0);
        int resultCharlie3=measureProxy.measure("3", Measures.Z);
        int resultCharlie7=measureProxy.measure("7", Measures.Z);
        System.out.println("对3粒子进行Z基的测量结果"+resultCharlie3);
        System.out.println("对7粒子进行Z基的测量结果"+resultCharlie7);
        //对应表格Bell态测量结果的第一种
        if(resultX==1&&resultY==1){
            if (resultCharlie3==0&&resultCharlie7==0){
                secretProxy.perform("3",QuantumGate.Operator_I);
                secretProxy.perform("7",QuantumGate.Operator_I);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==1&&resultCharlie7==0){
                secretProxy.perform("3", QuantumGate.Operator_Z);
                secretProxy.perform("7",QuantumGate.Operator_I);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==0&&resultCharlie7==1){
                secretProxy.perform("3", QuantumGate.Operator_I);
                secretProxy.perform("7",QuantumGate.Operator_Z);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==1&&resultCharlie7==1){
                secretProxy.perform("3", QuantumGate.Operator_Z);
                secretProxy.perform("7",QuantumGate.Operator_Z);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }

        }
        if(resultX==1&&resultY==3){
            if (resultCharlie3==0&&resultCharlie7==0){
                secretProxy.perform("3",QuantumGate.Operator_I);
                secretProxy.perform("7",QuantumGate.Operator_Z);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==1&&resultCharlie7==0){
                secretProxy.perform("3", QuantumGate.Operator_Z);
                secretProxy.perform("7",QuantumGate.Operator_Z);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==0&&resultCharlie7==1){
                secretProxy.perform("3", QuantumGate.Operator_I);
                secretProxy.perform("7",QuantumGate.Operator_I);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==1&&resultCharlie7==1){
                secretProxy.perform("3", QuantumGate.Operator_Z);
                secretProxy.perform("7",QuantumGate.Operator_I);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }

        }
        if(resultX==1&&resultY==2){
            if (resultCharlie3==0&&resultCharlie7==0){
                secretProxy.perform("3",QuantumGate.Operator_I);
                secretProxy.perform("7",QuantumGate.Operator_I);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==1&&resultCharlie7==0){
                secretProxy.perform("3", QuantumGate.Operator_Z);
                secretProxy.perform("7",QuantumGate.Operator_I);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==0&&resultCharlie7==1){
                secretProxy.perform("3", QuantumGate.Operator_I);
                secretProxy.perform("7",QuantumGate.Operator_Z);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==1&&resultCharlie7==1){
                secretProxy.perform("3", QuantumGate.Operator_Z);
                secretProxy.perform("7",QuantumGate.Operator_Z);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }

        }
        if(resultX==1&&resultY==4){
            if (resultCharlie3==0&&resultCharlie7==0){
                secretProxy.perform("3",QuantumGate.Operator_I);
                secretProxy.perform("7",QuantumGate.Operator_Z);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==1&&resultCharlie7==0){
                secretProxy.perform("3", QuantumGate.Operator_Z);
                secretProxy.perform("7",QuantumGate.Operator_Z);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==0&&resultCharlie7==1){
                secretProxy.perform("3", QuantumGate.Operator_I);
                secretProxy.perform("7",QuantumGate.Operator_I);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==1&&resultCharlie7==1){
                secretProxy.perform("3", QuantumGate.Operator_Z);
                secretProxy.perform("7",QuantumGate.Operator_I);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }

        }
        if(resultX==3&&resultY==1){
            if (resultCharlie3==0&&resultCharlie7==0){
                secretProxy.perform("3",QuantumGate.Operator_Z);
                secretProxy.perform("7",QuantumGate.Operator_I);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==1&&resultCharlie7==0){
                secretProxy.perform("3", QuantumGate.Operator_I);
                secretProxy.perform("7",QuantumGate.Operator_I);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==0&&resultCharlie7==1){
                secretProxy.perform("3", QuantumGate.Operator_Z);
                secretProxy.perform("7",QuantumGate.Operator_Z);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==1&&resultCharlie7==1){
                secretProxy.perform("3", QuantumGate.Operator_I);
                secretProxy.perform("7",QuantumGate.Operator_Z);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
        }
        if(resultX==3&&resultY==3){
            if (resultCharlie3==0&&resultCharlie7==0){
                secretProxy.perform("3",QuantumGate.Operator_Z);
                secretProxy.perform("7",QuantumGate.Operator_Z);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==1&&resultCharlie7==0){
                secretProxy.perform("3", QuantumGate.Operator_I);
                secretProxy.perform("7",QuantumGate.Operator_Z);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==0&&resultCharlie7==1){
                secretProxy.perform("3", QuantumGate.Operator_Z);
                secretProxy.perform("7",QuantumGate.Operator_I);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==1&&resultCharlie7==1){
                secretProxy.perform("3", QuantumGate.Operator_I);
                secretProxy.perform("7",QuantumGate.Operator_I);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }

        }
        if(resultX==3&&resultY==2){
            if (resultCharlie3==0&&resultCharlie7==0){
                secretProxy.perform("3",QuantumGate.Operator_Z);
                secretProxy.perform("7",QuantumGate.Operator_I);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==1&&resultCharlie7==0){
                secretProxy.perform("3", QuantumGate.Operator_I);
                secretProxy.perform("7",QuantumGate.Operator_I);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==0&&resultCharlie7==1){
                secretProxy.perform("3", QuantumGate.Operator_Z);
                secretProxy.perform("7",QuantumGate.Operator_Z);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==1&&resultCharlie7==1){
                secretProxy.perform("3", QuantumGate.Operator_I);
                secretProxy.perform("7",QuantumGate.Operator_Z);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
        }
        if(resultX==3&&resultY==4){
            if (resultCharlie3==0&&resultCharlie7==0){
                secretProxy.perform("3",QuantumGate.Operator_Z);
                secretProxy.perform("7",QuantumGate.Operator_Z);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==1&&resultCharlie7==0){
                secretProxy.perform("3", QuantumGate.Operator_I);
                secretProxy.perform("7",QuantumGate.Operator_Z);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==0&&resultCharlie7==1){
                secretProxy.perform("3", QuantumGate.Operator_Z);
                secretProxy.perform("7",QuantumGate.Operator_I);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==1&&resultCharlie7==1){
                secretProxy.perform("3", QuantumGate.Operator_I);
                secretProxy.perform("7",QuantumGate.Operator_I);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
        }
        if(resultX==2&&resultY==1){
            if (resultCharlie3==0&&resultCharlie7==0){
                secretProxy.perform("3",QuantumGate.Operator_I);
                secretProxy.perform("7",QuantumGate.Operator_I);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==1&&resultCharlie7==0){
                secretProxy.perform("3", QuantumGate.Operator_Z);
                secretProxy.perform("7",QuantumGate.Operator_I);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==0&&resultCharlie7==1){
                secretProxy.perform("3", QuantumGate.Operator_I);
                secretProxy.perform("7",QuantumGate.Operator_Z);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==1&&resultCharlie7==1){
                secretProxy.perform("3", QuantumGate.Operator_Z);
                secretProxy.perform("7",QuantumGate.Operator_Z);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
        }
        if(resultX==2&&resultY==3){
            if (resultCharlie3==0&&resultCharlie7==0){
                secretProxy.perform("3",QuantumGate.Operator_I);
                secretProxy.perform("7",QuantumGate.Operator_Z);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==1&&resultCharlie7==0){
                secretProxy.perform("3", QuantumGate.Operator_Z);
                secretProxy.perform("7",QuantumGate.Operator_Z);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==0&&resultCharlie7==1){
                secretProxy.perform("3", QuantumGate.Operator_I);
                secretProxy.perform("7",QuantumGate.Operator_I);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==1&&resultCharlie7==1){
                secretProxy.perform("3", QuantumGate.Operator_Z);
                secretProxy.perform("7",QuantumGate.Operator_I);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
        }
        if(resultX==2&&resultY==2){
            if (resultCharlie3==0&&resultCharlie7==0){
                secretProxy.perform("3",QuantumGate.Operator_I);
                secretProxy.perform("7",QuantumGate.Operator_I);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==1&&resultCharlie7==0){
                secretProxy.perform("3", QuantumGate.Operator_Z);
                secretProxy.perform("7",QuantumGate.Operator_I);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==0&&resultCharlie7==1){
                secretProxy.perform("3", QuantumGate.Operator_I);
                secretProxy.perform("7",QuantumGate.Operator_Z);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==1&&resultCharlie7==1){
                secretProxy.perform("3", QuantumGate.Operator_Z);
                secretProxy.perform("7",QuantumGate.Operator_Z);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
        }
        if(resultX==2&&resultY==4){
            if (resultCharlie3==0&&resultCharlie7==0){
                secretProxy.perform("3",QuantumGate.Operator_I);
                secretProxy.perform("7",QuantumGate.Operator_Z);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==1&&resultCharlie7==0){
                secretProxy.perform("3", QuantumGate.Operator_Z);
                secretProxy.perform("7",QuantumGate.Operator_Z);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==0&&resultCharlie7==1){
                secretProxy.perform("3", QuantumGate.Operator_I);
                secretProxy.perform("7",QuantumGate.Operator_I);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==1&&resultCharlie7==1){
                secretProxy.perform("3", QuantumGate.Operator_Z);
                secretProxy.perform("7",QuantumGate.Operator_I);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
        }
        if(resultX==4&&resultY==1){
            if (resultCharlie3==0&&resultCharlie7==0){
                secretProxy.perform("3",QuantumGate.Operator_Z);
                secretProxy.perform("7",QuantumGate.Operator_I);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==1&&resultCharlie7==0){
                secretProxy.perform("3", QuantumGate.Operator_I);
                secretProxy.perform("7",QuantumGate.Operator_I);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==0&&resultCharlie7==1){
                secretProxy.perform("3", QuantumGate.Operator_Z);
                secretProxy.perform("7",QuantumGate.Operator_Z);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==1&&resultCharlie7==1){
                secretProxy.perform("3", QuantumGate.Operator_I);
                secretProxy.perform("7",QuantumGate.Operator_Z);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
        }
        if(resultX==4&&resultY==3){
            if (resultCharlie3==0&&resultCharlie7==0){
                secretProxy.perform("3",QuantumGate.Operator_Z);
                secretProxy.perform("7",QuantumGate.Operator_Z);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==1&&resultCharlie7==0){
                secretProxy.perform("3", QuantumGate.Operator_I);
                secretProxy.perform("7",QuantumGate.Operator_Z);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==0&&resultCharlie7==1){
                secretProxy.perform("3", QuantumGate.Operator_Z);
                secretProxy.perform("7",QuantumGate.Operator_I);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==1&&resultCharlie7==1){
                secretProxy.perform("3", QuantumGate.Operator_I);
                secretProxy.perform("7",QuantumGate.Operator_I);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
        }
        if(resultX==4&&resultY==2){
            if (resultCharlie3==0&&resultCharlie7==0){
                secretProxy.perform("3",QuantumGate.Operator_Z);
                secretProxy.perform("7",QuantumGate.Operator_I);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==1&&resultCharlie7==0){
                secretProxy.perform("3", QuantumGate.Operator_I);
                secretProxy.perform("7",QuantumGate.Operator_I);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==0&&resultCharlie7==1){
                secretProxy.perform("3", QuantumGate.Operator_Z);
                secretProxy.perform("7",QuantumGate.Operator_Z);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==1&&resultCharlie7==1){
                secretProxy.perform("3", QuantumGate.Operator_I);
                secretProxy.perform("7",QuantumGate.Operator_Z);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
        }
        if(resultX==4&&resultY==4){
            if (resultCharlie3==0&&resultCharlie7==0){
                secretProxy.perform("3",QuantumGate.Operator_Z);
                secretProxy.perform("7",QuantumGate.Operator_Z);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==1&&resultCharlie7==0){
                secretProxy.perform("3", QuantumGate.Operator_I);
                secretProxy.perform("7",QuantumGate.Operator_Z);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==0&&resultCharlie7==1){
                secretProxy.perform("3", QuantumGate.Operator_Z);
                secretProxy.perform("7",QuantumGate.Operator_I);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
            if (resultCharlie3==1&&resultCharlie7==1){
                secretProxy.perform("3", QuantumGate.Operator_I);
                secretProxy.perform("7",QuantumGate.Operator_I);
                doubleState=secretProxy.getOwnState(Alice.systemState);
            }
        }
        System.out.println("得到的量子态为"+doubleState.showBinaryState());
        doubleState.showParticleName();
        System.out.println();
    }
}
