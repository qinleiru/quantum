package com.protocols.HPQIS;

import com.protocols.role.AbstractAgent;
import com.quantum.gate.QuantumGate;
import com.quantum.measure.Measures;
import com.quantum.measure.POVMMeasure;
import com.quantum.measure.ProjectiveMeasure;
import com.quantum.oparate.QuantumOperation;
import com.quantum.state.CommonState;
import com.quantum.state.DoubleState;
import com.quantum.state.SingleState;
import org.ujmp.core.DenseMatrix;
import org.ujmp.core.Matrix;

import java.util.ArrayList;
import java.util.HashMap;

import static com.protocols.HPQIS.HPQIS.OMEGA;
import static com.protocols.HPQIS.HPQIS.coefficients;
import static com.protocols.HPQIS.HPQIS.systemState;

public class LowAgent extends AbstractAgent {
    public boolean POVMResult=true;
    /**
     * 代理者对手中的所有粒子进行单粒子测量
     * @param measures
     * @return
     */
    @Override
    public void measure(Measures measures) {
        int index=measures.getIndex();
        switch (index){
            case 1:
                for (int i=0;i<particleName.size();i++){
                    measureResult.put(particleName.get(i), ProjectiveMeasure.measureBaseX(systemState,particleName.get(i)));
                }
                break;
            case 2:
                for (int i=0;i<particleName.size();i++){
                    measureResult.put(particleName.get(i), ProjectiveMeasure.measureBaseZ(systemState,particleName.get(i)));
                }
                break;
        }
    }

    /**
     * 秘密量子比特的恢复
     */
    @Override
    public void restore(){
        int resultAliceX = HPQIS.resultX;   //粒子x、粒子1的Bell态的测量结果
        int resultAliceY = HPQIS.resultY;   //粒子y、粒子5的Bell态的测量结果
        int lowAgentParticle1;   //权限低的clusterState1态中的粒子的测量结果
        int lowAgentParticle2;   //权限低的clusterState2态中的粒子的测量结果
        int highAgentParticle1;  //权限高的clusterState1态中的粒子的测量结果
        int highAgentParticle2;  //权限高的cluaterState2态中的粒子的测量结果
        String particle1Name;    //要操作的粒子1的名字
        String particle2Name;    //要操作的粒子2的名字
        if (measureResult.keySet().contains("3")) {
            lowAgentParticle1 = measureResult.get("3");
            lowAgentParticle2 = measureResult.get("7");

        } else {
            lowAgentParticle1 = measureResult.get("4");
            lowAgentParticle2 = measureResult.get("8");
        }
        highAgentParticle1=measureResult.get("2");
        highAgentParticle2=measureResult.get("6");
        if(Integer.parseInt(particleName.get(0))<Integer.parseInt(particleName.get(1))){
            particle1Name=particleName.get(0);
            particle2Name=particleName.get(1);
        }
        else{
            particle1Name=particleName.get(1);
            particle2Name=particleName.get(0);
        }
        System.out.println("++++++++粒子X、粒子1的Bell态的测量结果为"+resultAliceX);
        System.out.println("++++++++粒子Y、粒子5的Bell态的测量结果为"+resultAliceY);
        System.out.println("++++++++权限低的代理者的第一个粒子测量结果为"+lowAgentParticle1+" 第二个粒子的测量结果为"+lowAgentParticle2);
        System.out.println("++++++++权限高的代理者的第一个粒子测量结果为"+highAgentParticle1+" 第二个粒子的测量结果为"+highAgentParticle2);
        //无论测量结果是恢复秘密消息的代理者都会对手中的粒子先进行H门的操作
        QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_H);
        QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_H);
        int result=0;
        double a=coefficients[0];
        double b=coefficients[1];
        double c=coefficients[2];
        double d=coefficients[3];
        if (resultAliceX==1&&resultAliceY==1) {
            if (highAgentParticle1 == 0 && highAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState, particle1Name, QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState, particle2Name, QuantumGate.Operator_I);
            }
            if (highAgentParticle1 == 0 && highAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState, particle1Name, QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState, particle2Name, QuantumGate.Operator_Z);
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState, particle1Name, QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState, particle2Name, QuantumGate.Operator_I);
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState, particle1Name, QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState, particle2Name, QuantumGate.Operator_Z);
            }
            if(lowAgentParticle1==0&&lowAgentParticle2==0){
                result=performPOVM(a*c,a*d,b*c,b*d);
                performOperation(1,result);
            }
            if(lowAgentParticle1==0&&lowAgentParticle2==1){
                result=performPOVM(a*d,a*c,b*d,b*c);
                performOperation(2,result);
            }
            if (lowAgentParticle1==1&&lowAgentParticle2==0){
                result=performPOVM(b*c,b*d,a*c,a*d);
                performOperation(3,result);
            }
            if(lowAgentParticle1==1&&lowAgentParticle2==1){
                result=performPOVM(b*d,b*c,a*d,a*c);
                performOperation(4,result);
            }
        }
        if (resultAliceX==1&&resultAliceY==3) {
            if (highAgentParticle1 == 0 && highAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
            }
            if (highAgentParticle1 == 0 && highAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
            }
            if(lowAgentParticle1==0&&lowAgentParticle2==0){
                result=performPOVM(a*c,a*d,b*c,b*d);
                performOperation(1,result);
            }
            if(lowAgentParticle1==0&&lowAgentParticle2==1){
                result=performPOVM(a*d,a*c,b*d,b*c);
                performOperation(2,result);
            }
            if (lowAgentParticle1==1&&lowAgentParticle2==0){
                result=performPOVM(b*c,b*d,a*c,a*d);
                performOperation(3,result);
            }
            if(lowAgentParticle1==1&&lowAgentParticle2==1){
                result=performPOVM(b*d,b*c,a*d,a*c);
                performOperation(4,result);
            }
        }
        if (resultAliceX==1&&resultAliceY==2) {
            if (highAgentParticle1 == 0 && highAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
            }
            if (highAgentParticle1 == 0 && highAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
            }
            if(lowAgentParticle1==0&&lowAgentParticle2==0){
                result=performPOVM(a*c,a*d,b*c,b*d);
                performOperation(2,result);
            }
            if(lowAgentParticle1==0&&lowAgentParticle2==1){
                result=performPOVM(a*d,a*c,b*d,b*c);
                performOperation(1,result);
            }
            if (lowAgentParticle1==1&&lowAgentParticle2==0){
                result=performPOVM(b*c,b*d,a*c,a*d);
                performOperation(4,result);
            }
            if(lowAgentParticle1==1&&lowAgentParticle2==1){
                result=performPOVM(b*d,b*c,a*d,a*c);
                performOperation(3,result);
            }
        }
        if (resultAliceX==1&&resultAliceY==4) {
            if (highAgentParticle1 == 0 && highAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
            }
            if (highAgentParticle1 == 0 && highAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
            }
            if(lowAgentParticle1==0&&lowAgentParticle2==0){
                result=performPOVM(a*c,a*d,b*c,b*d);
                performOperation(2,result);
            }
            if(lowAgentParticle1==0&&lowAgentParticle2==1){
                result=performPOVM(a*d,a*c,b*d,b*c);
                performOperation(1,result);
            }
            if (lowAgentParticle1==1&&lowAgentParticle2==0){
                result=performPOVM(b*c,b*d,a*c,a*d);
                performOperation(4,result);
            }
            if(lowAgentParticle1==1&&lowAgentParticle2==1){
                result=performPOVM(b*d,b*c,a*d,a*c);
                performOperation(3,result);
            }
        }
        if (resultAliceX==3&&resultAliceY==1) {
            if (highAgentParticle1 == 0 && highAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
            }
            if (highAgentParticle1 == 0 && highAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
            }
            if(lowAgentParticle1==0&&lowAgentParticle2==0){
                result=performPOVM(a*c,a*d,b*c,b*d);
                performOperation(1,result);
            }
            if(lowAgentParticle1==0&&lowAgentParticle2==1){
                result=performPOVM(a*d,a*c,b*d,b*c);
                performOperation(2,result);
            }
            if (lowAgentParticle1==1&&lowAgentParticle2==0){
                result=performPOVM(b*c,b*d,a*c,a*d);
                performOperation(3,result);
            }
            if(lowAgentParticle1==1&&lowAgentParticle2==1){
                result=performPOVM(b*d,b*c,a*d,a*c);
                performOperation(4,result);
            }
        }
        if (resultAliceX==3&&resultAliceY==3) {
            if (highAgentParticle1 == 0 && highAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
            }
            if (highAgentParticle1 == 0 && highAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
            }
            if(lowAgentParticle1==0&&lowAgentParticle2==0){
                result=performPOVM(a*c,a*d,b*c,b*d);
                performOperation(1,result);
            }
            if(lowAgentParticle1==0&&lowAgentParticle2==1){
                result=performPOVM(a*d,a*c,b*d,b*c);
                performOperation(2,result);
            }
            if (lowAgentParticle1==1&&lowAgentParticle2==0){
                result=performPOVM(b*c,b*d,a*c,a*d);
                performOperation(3,result);
            }
            if(lowAgentParticle1==1&&lowAgentParticle2==1){
                result=performPOVM(b*d,b*c,a*d,a*c);
                performOperation(4,result);
            }
        }
        if (resultAliceX==3&&resultAliceY==2) {
            if (highAgentParticle1 == 0 && highAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
            }
            if (highAgentParticle1 == 0 && highAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
            }
            if(lowAgentParticle1==0&&lowAgentParticle2==0){
                result=performPOVM(a*c,a*d,b*c,b*d);
                performOperation(2,result);
            }
            if(lowAgentParticle1==0&&lowAgentParticle2==1){
                result=performPOVM(a*d,a*c,b*d,b*c);
                performOperation(1,result);
            }
            if (lowAgentParticle1==1&&lowAgentParticle2==0){
                result=performPOVM(b*c,b*d,a*c,a*d);
                performOperation(4,result);
            }
            if(lowAgentParticle1==1&&lowAgentParticle2==1){
                result=performPOVM(b*d,b*c,a*d,a*c);
                performOperation(3,result);
            }
        }
        if (resultAliceX==3&&resultAliceY==4) {
            if (highAgentParticle1 == 0 && highAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
            }
            if (highAgentParticle1 == 0 && highAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
            }
            if(lowAgentParticle1==0&&lowAgentParticle2==0){
                result=performPOVM(a*c,a*d,b*c,b*d);
                performOperation(2,result);
            }
            if(lowAgentParticle1==0&&lowAgentParticle2==1){
                result=performPOVM(a*d,a*c,b*d,b*c);
                performOperation(1,result);
            }
            if (lowAgentParticle1==1&&lowAgentParticle2==0){
                result=performPOVM(b*c,b*d,a*c,a*d);
                performOperation(4,result);
            }
            if(lowAgentParticle1==1&&lowAgentParticle2==1){
                result=performPOVM(b*d,b*c,a*d,a*c);
                performOperation(3,result);
            }
        }
        if (resultAliceX==2&&resultAliceY==1) {
            if (highAgentParticle1 == 0 && highAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
            }
            if (highAgentParticle1 == 0 && highAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
            }
            if(lowAgentParticle1==0&&lowAgentParticle2==0){
                result=performPOVM(a*c,a*d,b*c,b*d);
                performOperation(3,result);
            }
            if(lowAgentParticle1==0&&lowAgentParticle2==1){
                result=performPOVM(a*d,a*c,b*d,b*c);
                performOperation(4,result);
            }
            if (lowAgentParticle1==1&&lowAgentParticle2==0){
                result=performPOVM(b*c,b*d,a*c,a*d);
                performOperation(1,result);
            }
            if(lowAgentParticle1==1&&lowAgentParticle2==1){
                result=performPOVM(b*d,b*c,a*d,a*c);
                performOperation(2,result);
            }
        }
        if (resultAliceX==2&&resultAliceY==3) {
            if (highAgentParticle1 == 0 && highAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
            }
            if (highAgentParticle1 == 0 && highAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
            }
            if(lowAgentParticle1==0&&lowAgentParticle2==0){
                result=performPOVM(a*c,a*d,b*c,b*d);
                performOperation(3,result);
            }
            if(lowAgentParticle1==0&&lowAgentParticle2==1){
                result=performPOVM(a*d,a*c,b*d,b*c);
                performOperation(4,result);
            }
            if (lowAgentParticle1==1&&lowAgentParticle2==0){
                result=performPOVM(b*c,b*d,a*c,a*d);
                performOperation(1,result);
            }
            if(lowAgentParticle1==1&&lowAgentParticle2==1){
                result=performPOVM(b*d,b*c,a*d,a*c);
                performOperation(2,result);
            }
        }
        if (resultAliceX==2&&resultAliceY==2) {
            if (highAgentParticle1 == 0 && highAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
            }
            if (highAgentParticle1 == 0 && highAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
            }
            if(lowAgentParticle1==0&&lowAgentParticle2==0){
                result=performPOVM(a*c,a*d,b*c,b*d);
                performOperation(4,result);
            }
            if(lowAgentParticle1==0&&lowAgentParticle2==1){
                result=performPOVM(a*d,a*c,b*d,b*c);
                performOperation(3,result);
            }
            if (lowAgentParticle1==1&&lowAgentParticle2==0){
                result=performPOVM(b*c,b*d,a*c,a*d);
                performOperation(2,result);
            }
            if(lowAgentParticle1==1&&lowAgentParticle2==1){
                result=performPOVM(b*d,b*c,a*d,a*c);
                performOperation(1,result);
            }
        }
        if (resultAliceX==2&&resultAliceY==4) {
            if (highAgentParticle1 == 0 && highAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
            }
            if (highAgentParticle1 == 0 && highAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
            }
            if(lowAgentParticle1==0&&lowAgentParticle2==0){
                result=performPOVM(a*c,a*d,b*c,b*d);
                performOperation(4,result);
            }
            if(lowAgentParticle1==0&&lowAgentParticle2==1){
                result=performPOVM(a*d,a*c,b*d,b*c);
                performOperation(3,result);
            }
            if (lowAgentParticle1==1&&lowAgentParticle2==0){
                result=performPOVM(b*c,b*d,a*c,a*d);
                performOperation(2,result);
            }
            if(lowAgentParticle1==1&&lowAgentParticle2==1){
                result=performPOVM(b*d,b*c,a*d,a*c);
                performOperation(1,result);
            }
        }
        if (resultAliceX==4&&resultAliceY==1) {
            if (highAgentParticle1 == 0 && highAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
            }
            if (highAgentParticle1 == 0 && highAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
            }
            if(lowAgentParticle1==0&&lowAgentParticle2==0){
                result=performPOVM(a*c,a*d,b*c,b*d);
                performOperation(3,result);
            }
            if(lowAgentParticle1==0&&lowAgentParticle2==1){
                result=performPOVM(a*d,a*c,b*d,b*c);
                performOperation(4,result);
            }
            if (lowAgentParticle1==1&&lowAgentParticle2==0){
                result=performPOVM(b*c,b*d,a*c,a*d);
                performOperation(1,result);
            }
            if(lowAgentParticle1==1&&lowAgentParticle2==1){
                result=performPOVM(b*d,b*c,a*d,a*c);
                performOperation(2,result);
            }
        }
        if (resultAliceX==4&&resultAliceY==3) {
            if (highAgentParticle1 == 0 && highAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
            }
            if (highAgentParticle1 == 0 && highAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
            }
            if(lowAgentParticle1==0&&lowAgentParticle2==0){
                result=performPOVM(a*c,a*d,b*c,b*d);
                performOperation(3,result);
            }
            if(lowAgentParticle1==0&&lowAgentParticle2==1){
                result=performPOVM(a*d,a*c,b*d,b*c);
                performOperation(4,result);
            }
            if (lowAgentParticle1==1&&lowAgentParticle2==0){
                result=performPOVM(b*c,b*d,a*c,a*d);
                performOperation(1,result);
            }
            if(lowAgentParticle1==1&&lowAgentParticle2==1){
                result=performPOVM(b*d,b*c,a*d,a*c);
                performOperation(2,result);
            }
        }
        if (resultAliceX==4&&resultAliceY==2) {
            if (highAgentParticle1 == 0 && highAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
            }
            if (highAgentParticle1 == 0 && highAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
            }
            if(lowAgentParticle1==0&&lowAgentParticle2==0){
                result=performPOVM(a*c,a*d,b*c,b*d);
                performOperation(4,result);
            }
            if(lowAgentParticle1==0&&lowAgentParticle2==1){
                result=performPOVM(a*d,a*c,b*d,b*c);
                performOperation(3,result);
            }
            if (lowAgentParticle1==1&&lowAgentParticle2==0){
                result=performPOVM(b*c,b*d,a*c,a*d);
                performOperation(2,result);
            }
            if(lowAgentParticle1==1&&lowAgentParticle2==1){
                result=performPOVM(b*d,b*c,a*d,a*c);
                performOperation(1,result);
            }
        }
        if (resultAliceX==4&&resultAliceY==4) {
            if (highAgentParticle1 == 0 && highAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
            }
            if (highAgentParticle1 == 0 && highAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 0) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_Z);
            }
            if (highAgentParticle1 == 1 && highAgentParticle2 == 1) {
                QuantumOperation.quantumSinglePerform(systemState,particle1Name,QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState,particle2Name,QuantumGate.Operator_I);
            }
            if(lowAgentParticle1==0&&lowAgentParticle2==0){
                result=performPOVM(a*c,a*d,b*c,b*d);
                performOperation(4,result);
            }
            if(lowAgentParticle1==0&&lowAgentParticle2==1){
                result=performPOVM(a*d,a*c,b*d,b*c);
                performOperation(3,result);
            }
            if (lowAgentParticle1==1&&lowAgentParticle2==0){
                result=performPOVM(b*c,b*d,a*c,a*d);
                performOperation(2,result);
            }
            if(lowAgentParticle1==1&&lowAgentParticle2==1){
                result=performPOVM(b*d,b*c,a*d,a*c);
                performOperation(1,result);
            }
        }

    }

    public ArrayList<DoubleState>getDiscrimStates(double argu1,double argu2,double argu3,double argu4){
        //构造要区分的量子比特
        ArrayList<DoubleState> doubleStates=new ArrayList<>();
        double[] state=new double[]{argu1,argu2,argu3,argu4};
        DoubleState doubleState=new DoubleState(state);
        doubleState.setParticlesName(1,"m");
        doubleState.setParticlesName(2,"n");
        doubleStates.add(doubleState);
        state=new double[]{argu1,argu2,-argu3,-argu4};
        doubleState=new DoubleState(state);
        doubleState.setParticlesName(1,"m");
        doubleState.setParticlesName(2,"n");
        doubleStates.add(doubleState);
        state=new double[]{argu1,-argu2,argu3,-argu4};
        doubleState=new DoubleState(state);
        doubleState.setParticlesName(1,"m");
        doubleState.setParticlesName(2,"n");
        doubleStates.add(doubleState);
        state=new double[]{argu1,-argu2,-argu3,argu4};
        doubleState=new DoubleState(state);
        doubleState.setParticlesName(1,"m");
        doubleState.setParticlesName(2,"n");
        doubleStates.add(doubleState);
        return doubleStates;
    }

    public  ArrayList<Matrix> getOparators(double argu1,double argu2,double argu3,double argu4){
//        double omega=getOmega(argu1,argu2,argu3,argu4);
        double omega=OMEGA;
        double epsilon=1/(4*Math.pow(coefficients[0]*coefficients[1]*coefficients[2]*coefficients[3],2));
        ArrayList<Matrix> oparators=new ArrayList<>();
        //算子1
        Matrix vector1 = DenseMatrix.Factory.zeros(4, 1);
        vector1.setAsDouble(1/argu1, 0, 0);
        vector1.setAsDouble(1/argu2, 1, 0);
        vector1.setAsDouble(1/argu3, 2, 0);
        vector1.setAsDouble(1/argu4, 3, 0);
        Matrix opera1=vector1.mtimes(vector1.transpose());
        opera1=opera1.times(1/(omega*epsilon));
        oparators.add(opera1);
        //算子2
        Matrix vector2 = DenseMatrix.Factory.zeros(4, 1);
        vector2.setAsDouble(1/argu1, 0, 0);
        vector2.setAsDouble(1/argu2, 1, 0);
        vector2.setAsDouble(-1/argu3, 2, 0);
        vector2.setAsDouble(-1/argu4, 3, 0);
        Matrix opera2=vector2.mtimes(vector2.transpose());
        opera2=opera2.times(1/(omega*epsilon));
        oparators.add(opera2);
        //算子3
        Matrix vector3 = DenseMatrix.Factory.zeros(4, 1);
        vector3.setAsDouble(1/argu1, 0, 0);
        vector3.setAsDouble(-1/argu2, 1, 0);
        vector3.setAsDouble(1/argu3, 2, 0);
        vector3.setAsDouble(-1/argu4, 3, 0);
        Matrix opera3=vector3.mtimes(vector3.transpose());
        opera3=opera3.times(1/(omega*epsilon));
        oparators.add(opera3);
        //算子4
        Matrix vector4 = DenseMatrix.Factory.zeros(4, 1);
        vector4.setAsDouble(1/argu1, 0, 0);
        vector4.setAsDouble(-1/argu2, 1, 0);
        vector4.setAsDouble(-1/argu3, 2, 0);
        vector4.setAsDouble(1/argu4, 3, 0);
        Matrix opera4=vector4.mtimes(vector4.transpose());
        opera4=opera4.times(1/(omega*epsilon));
        oparators.add(opera4);
        return oparators;
    }

    public int performPOVM(double argu1,double argu2,double argu3,double argu4){
        //引入辅助粒子m、辅助粒子n
        SingleState singleStateM= CommonState.Zero_State;
        singleStateM.setParticlesName(1,"m");
        systemState=QuantumOperation.quantumTensor(systemState,singleStateM);
        SingleState singleStateN=CommonState.Zero_State;
        singleStateN.setParticlesName(1,"n");
        systemState=QuantumOperation.quantumTensor(systemState,singleStateN);
        //进行CNOT操作
        QuantumOperation.quantumDoublePerform(systemState,particleName.get(0),"m",QuantumGate.Operator_CNOT);
        QuantumOperation.quantumDoublePerform(systemState,particleName.get(1),"n",QuantumGate.Operator_CNOT);
        //构造要区分的量子比特
        ArrayList<DoubleState> doubleStates=getDiscrimStates(argu1,argu2,argu3,argu4);
        //构造使用的算子
        ArrayList<Matrix> oparators=getOparators(argu1,argu2,argu3,argu4);
        return POVMMeasure.measurePOVMDouble(systemState, oparators, doubleStates);
    }

    public void performOperation(int situation,int result){
        if(result==4){
            POVMResult=false;
            System.out.println("((((((((((((POVM测量失败)))))))))))))))");
            return;
        }
        if(situation==1){
            if (result==0){
                QuantumOperation.quantumSinglePerform(systemState, particleName.get(0), QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState, particleName.get(1), QuantumGate.Operator_I);
            }
            if(result==1){
                QuantumOperation.quantumSinglePerform(systemState, particleName.get(0), QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState, particleName.get(1), QuantumGate.Operator_I);
            }
            if(result==2){
                QuantumOperation.quantumSinglePerform(systemState, particleName.get(0), QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState, particleName.get(1), QuantumGate.Operator_Z);
            }
            if (result==3){
                QuantumOperation.quantumSinglePerform(systemState, particleName.get(0), QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState, particleName.get(1), QuantumGate.Operator_Z);
            }
        }
        if(situation==2){
            if (result==0){
                QuantumOperation.quantumSinglePerform(systemState, particleName.get(0), QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState, particleName.get(1), QuantumGate.Operator_X);
            }
            if(result==1){
                QuantumOperation.quantumSinglePerform(systemState, particleName.get(0), QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState, particleName.get(1), QuantumGate.Operator_X);
            }
            if(result==2){
                QuantumOperation.quantumSinglePerform(systemState, particleName.get(0), QuantumGate.Operator_I);
                QuantumOperation.quantumSinglePerform(systemState, particleName.get(1), QuantumGate.Operator_iY);
            }
            if (result==3){
                QuantumOperation.quantumSinglePerform(systemState, particleName.get(0), QuantumGate.Operator_Z);
                QuantumOperation.quantumSinglePerform(systemState, particleName.get(1), QuantumGate.Operator_iY);
            }
        }
        if (situation==3){
            if (result==0){
                QuantumOperation.quantumSinglePerform(systemState, particleName.get(0), QuantumGate.Operator_X);
                QuantumOperation.quantumSinglePerform(systemState, particleName.get(1), QuantumGate.Operator_I);
            }
            if(result==1){
                QuantumOperation.quantumSinglePerform(systemState, particleName.get(0), QuantumGate.Operator_iY);
                QuantumOperation.quantumSinglePerform(systemState, particleName.get(1), QuantumGate.Operator_I);
            }
            if(result==2){
                QuantumOperation.quantumSinglePerform(systemState, particleName.get(0), QuantumGate.Operator_X);
                QuantumOperation.quantumSinglePerform(systemState, particleName.get(1), QuantumGate.Operator_Z);
            }
            if (result==3){
                QuantumOperation.quantumSinglePerform(systemState, particleName.get(0), QuantumGate.Operator_iY);
                QuantumOperation.quantumSinglePerform(systemState, particleName.get(1), QuantumGate.Operator_Z);
            }
        }
        if(situation==4){
            if (result==0){
                QuantumOperation.quantumSinglePerform(systemState, particleName.get(0), QuantumGate.Operator_X);
                QuantumOperation.quantumSinglePerform(systemState, particleName.get(1), QuantumGate.Operator_X);
            }
            if(result==1){
                QuantumOperation.quantumSinglePerform(systemState, particleName.get(0), QuantumGate.Operator_iY);
                QuantumOperation.quantumSinglePerform(systemState, particleName.get(1), QuantumGate.Operator_X);
            }
            if(result==2){
                QuantumOperation.quantumSinglePerform(systemState, particleName.get(0), QuantumGate.Operator_X);
                QuantumOperation.quantumSinglePerform(systemState, particleName.get(1), QuantumGate.Operator_iY);
            }
            if (result==3){
                QuantumOperation.quantumSinglePerform(systemState, particleName.get(0), QuantumGate.Operator_iY);
                QuantumOperation.quantumSinglePerform(systemState, particleName.get(1), QuantumGate.Operator_iY);
            }
        }
    }
    //todo：目前Omega的值选用的是最小的值
    //Omega的值>=16*max{(bd)^2,(ac)^2,(bc)^2,(ad)^2}
    public static double  getOmega(double argu1,double argu2,double argu3,double argu4){
        double max=Math.pow(argu1,2);
        max=Math.pow(argu2,2)>max?Math.pow(argu2,2):max;
        max=Math.pow(argu3,2)>max?Math.pow(argu3,2):max;
        max=Math.pow(argu4,2)>max?Math.pow(argu4,2):max;
        return 16*max;
    }
}
