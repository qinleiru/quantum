package com.quantum.communication;

import com.quantum.gate.QuantumGate;
import com.quantum.oparate.Operate;
import com.quantum.tools.QuantumState;
import org.ujmp.core.*;

//下面的实例尝试实现量子隐形传态协议
public class QuantumTeleportation {
    public static void main(String[] args) {
        //要传送的位置的量子态
        double[] state = new double[]{Math.pow(0.5, -0.5), Math.pow(0.5, -0.5)};
        //构建量子信道需要的量子纠缠态
        double[] bellState = new double[]{Math.pow(0.5, -0.5), 0, 0, Math.pow(0.5, -0.5)};
        //当前系统所处的整个量子态为
        double[] systemState = Operate.operatorTensor(state, bellState);
        //对发送者手中的粒子x以及粒子A进行Bell态测量
        //首先进行矩阵的张量积
        double[][] u = new double[][]{{Math.pow(0.5, -0.5), 0, 0, Math.pow(0.5, -0.5)}, {0, Math.pow(0.5, -0.5), Math.pow(0.5, -0.5), 0}, {Math.pow(0.5, -0.5), 0, 0, -Math.pow(0.5, -0.5)}, {0, Math.pow(0.5, -0.5), -Math.pow(0.5, -0.5), 0}};
        double[][] I = QuantumGate.Operator_I;
        double operate[][] = Operate.operatorTensor(u, I);
        //测量
        Matrix temp = Matrix.Factory.importFromArray(operate);
        Matrix system=Matrix.Factory.importFromArray(systemState);
        Matrix result=temp.mtimes(system.transpose());
        System.out.println("矩阵的结果是");
        System.out.print(result.transpose());
        double [][] changeResult=result.transpose().toDoubleArray();
        for(int i=0;i<changeResult.length;i++){
            for(int j=0;j<changeResult[0].length;j++){
                System.out.print(changeResult[i][j]+" ");
            }
            System.out.println("");
        }
        //产生坍缩
    }
}
