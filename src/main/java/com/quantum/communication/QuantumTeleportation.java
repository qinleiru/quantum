package com.quantum.communication;

import com.quantum.gate.QuantumGate;
import com.quantum.measure.ProjectiveMeasure;
import com.quantum.oparate.MathOperation;
import com.quantum.oparate.QuantumOperation;
import com.quantum.state.DoubleState;
import com.quantum.state.MultiState;
import com.quantum.state.SingleState;
import com.quantum.tools.QuantumState;

/**
 * 实现了量子分层信息拆分协议的基础，量子隐形传态协议
 */
//todo：在最后计算粒子手中自己的状态的过程中可能存在问题
public class QuantumTeleportation {
    public static void run() {
        SingleState oneState = new SingleState();
        oneState.setParticlesName(1, "x");
        System.out.println("要传送的秘密单量子比特为：" + oneState.showBinaryState());
        oneState.showParticleName();
        System.out.println();
        double[] bell = new double[]{Math.pow(2, -0.5), 0, 0, Math.pow(2, -0.5)};
        DoubleState twoState = new DoubleState(bell);
        twoState.setParticlesName(1,"1");
        twoState.setParticlesName(2,"2");
//        System.out.println("用于构成量子信道的Bell态为：" + twoState.showBinaryState());
//        twoState.showParticleName();
//        System.out.println();
        //当前系统所处的整个量子态为
        //todo：在计算系统的张量积的过程中可能还会需要归一化
        MultiState systemState = QuantumOperation.quantumTensor(oneState, twoState);
//        System.out.println("此时系统的态为：" + systemState.showBinaryState());
//        systemState.showParticleName();
//        System.out.println();
        //对发送者手中的粒子x以及粒子1进行Bell态测量
        int result = ProjectiveMeasure.measureBeseBell(systemState, "x", "1");
        System.out.println("测量完成后系统的态为" + systemState.showBinaryState());
        systemState.showParticleName();
        System.out.println();
        if (result == 1) {
            System.out.println("Bell态测的结果为1");
            QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_I);
        }
        if (result == 2) {
            System.out.println("Bell态测的结果为2");
            QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_X);
        }
        if (result == 3) {
            System.out.println("Bell态测的结果为3");
            QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_Z);
        }
        if (result == 4) {
            System.out.println("Bell态测的结果为4");
            QuantumOperation.quantumSinglePerform(systemState, "2", QuantumGate.Operator_iY);
        }
        double[] secret=getOwnState(systemState,"2");
        MathOperation.normalization(secret);
        SingleState secretState = new SingleState(secret);
        secretState.setParticlesName(1,"2");
        System.out.println("得到的量子秘密态为" + secretState.showBinaryState());
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
    public  static void main(String[] args){
        run();
    }
}
