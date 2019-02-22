package com.protocols.QuantumTeleportation;

import com.quantum.gate.QuantumGate;
import com.quantum.measure.ProjectiveMeasure;
import com.quantum.oparate.MathOperation;
import com.quantum.oparate.QuantumOperation;
import com.quantum.state.DoubleState;
import com.quantum.state.MultiState;
import com.quantum.state.SingleState;
import com.quantum.state.QuantumState;
import com.quantum.tools.QuantumTools;

/**
 * 实现了量子分层信息拆分协议的基础，量子隐形传态协议
 */
//todo：在最后计算粒子手中自己的状态的过程中可能存在问题
public class QuantumTeleportation {
    public static void run() {
        SingleState oneState = new SingleState();
        oneState.setParticlesName(1, "x");
        System.out.println("发送者要传送粒子x的量子态为：" + QuantumTools.showBinaryState(oneState));
        double[] bell = new double[]{Math.pow(2, -0.5), 0, 0, Math.pow(2, -0.5)};
        DoubleState twoState = new DoubleState(bell);
        twoState.setParticlesName(1,"1");
        twoState.setParticlesName(2,"2");
        System.out.println("发送者准备用于构造量子信道粒子1和粒子2的Bell态为：" + QuantumTools.showBinaryState(twoState));
        //当前系统所处的整个量子态为
        //todo：在计算系统的张量积的过程中可能还会需要归一化
        MultiState systemState = QuantumOperation.quantumTensor(oneState, twoState);
//        System.out.println("此时系统的态为：" + systemState.showBinaryState());
//        System.out.print("粒子的下标为：");
//        systemState.showParticleName();
//        System.out.println();
        System.out.println("发送者将粒子2发送给接收者");
        //对发送者手中的粒子x以及粒子1进行Bell态测量
        System.out.println("发送者将手中的粒子x及粒子1进行Bell态的测量,并公布测量结果");
        int result = ProjectiveMeasure.measureBeseBell(systemState, "x", "1");
        switch(result){
            case 1:
                System.out.println("发送者测量结果|Ф>+态");
                break;
            case 2:
                System.out.println("发送者测量结果|\uD835\uDF6D>+态");
                break;
            case 3:
                System.out.println("发送者测量结果|Ф>-态");
                break;
            case 4:
                System.out.println("发送者测量结果|\uD835\uDF6D>-态");
                break;
        }
        System.out.println("接收者根据发送者的测量结果，接收者对手中的粒子2进行操作");
        if (result == 1) {
            QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_I);
        }
        if (result == 2) {
            QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_X);
        }
        if (result == 3) {
            QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_Z);
        }
        if (result == 4) {
            QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_iY);
        }
        double[] secret=getOwnState(systemState,"2");
        MathOperation.normalization(secret);
        SingleState secretState = new SingleState(secret);
        secretState.setParticlesName(1,"2");
        System.out.println("接收者得到的量子秘密态为" + QuantumTools.showBinaryState(secretState));
    }
    public static double[] getOwnState(QuantumState quantumState,String particle){
        int index=quantumState.getParticlesName().indexOf(particle);
        double[] result=new double[2];
        int num=quantumState.getParticles();
        for (int i=0;i<Math.pow(2,num);i++){
            if (isBitZero(i,index+1,num)){
                result[0]+=quantumState.getState()[i];
            }
            else
                result[1]+=quantumState.getState()[i];
        }
        return result;
    }
    public static boolean isBitZero(int decNum,int pos,int particles){
        String binStr = "";
        for(int i= particles-1;i>=0;i--) {
            binStr +=(decNum>>i)&1;
        }
        return binStr.charAt(pos-1)=='0';
    }
//    public static void main(String[] args){
//        run();
//    }
}
