package com.quantum.oparate;

import com.quantum.gate.QuantumGate;
import com.quantum.state.MultiState;
import com.quantum.state.QuantumState;
import com.quantum.tools.Tools;
import org.ujmp.core.Matrix;

import java.util.ArrayList;

public class QuantumOperation {

    /**
     * 对量子进行单量子门操作
     * @param state
     * @param particle
     * @param operator
     */
    public  static void quantumSinglePerform(QuantumState state,String particle,double[][]operator){
        double[] targetState=state.getState();
        double[][] temp=null;
        int pos=state.getParticlesName().indexOf(particle)+1;
        if(pos==1){
            temp=operator;
        }
        else
            temp= QuantumGate.Operator_I;
        for(int i=2;i<=state.getParticles();i++){
            if (i!=pos){
                temp=MathOperation.tensor(temp,QuantumGate.Operator_I);
            }
            else
                temp=MathOperation.tensor(temp,operator);
        }
        Matrix result= (Matrix.Factory.importFromArray(temp)).mtimes((Matrix.Factory.importFromArray(targetState).transpose()));
        state.setState(MathOperation.vecToArray(result.toDoubleArray()));
    }


    /**
     * 对量子态进行两量子比特门的操作
     * @param state
     * @param particle1
     * @param particle2
     * @param operator
     */
    //FIXME:参数粒子1在前，参数粒子2在后，默认粒子1在粒子2的前面，
    public static void quantumDoublePerform(QuantumState state, String particle1,String particle2, double[][] operator) {
        int size=state.getParticles();
        String changeParticle1=state.getParticlesName().get(size-2);
        String changeParticle2=state.getParticlesName().get(size-1);
        QuantumOperation.quantumSwap(state, particle1, changeParticle1);
        QuantumOperation.quantumSwap(state, particle2, changeParticle2);
        int pos = state.getParticlesName().indexOf(particle1) + 1;
        double[]targetState=state.getState();
        double[][] temp = QuantumGate.Operator_I;
        for(int j=2;j<=state.getParticles();){
            if (j!=pos){
                temp=MathOperation.tensor(temp,QuantumGate.Operator_I);
                j++;
            }
            else {
                temp = MathOperation.tensor(temp, operator);
                j+=2;
            }

        }
        Matrix result= (Matrix.Factory.importFromArray(temp)).mtimes((Matrix.Factory.importFromArray(targetState).transpose()));
        state.setState(MathOperation.vecToArray(result.toDoubleArray()));
    }
    /**
     * 量子态的直积
     * @param state1
     * @param state2
     * @return
     */
    public static MultiState quantumTensor(QuantumState state1, QuantumState state2){
        //各个可能的量子态的概率辐
        double result[]= MathOperation.tensor(state1.getState(),state2.getState());
        //量子态张量积之后的粒子数
        int particles=state1.getParticles()+state2.getParticles();
        MultiState resultState=new MultiState(result,particles);

        //设置每个粒子对应的粒子名称
        int pos=1;
        for (int i=0;i<state1.getParticles();i++){
            resultState.setParticlesName(pos,state1.getParticlesName().get(i));
            pos++;
        }
        for (int i=0;i<state2.getParticles();i++){
            resultState.setParticlesName(pos,state2.getParticlesName().get(i));
            pos++;
        }
        return resultState;
    }

    /**
     * 量子态粒子之间的交换
     * @param quantumState
     * @param a    粒子的名称
     * @param b    粒子的名称
     */
    //todo:从部分假设的是ArrayList中的内容都是不重复的
    public static void quantumSwap(QuantumState quantumState, String a, String b){
        if(a==b) return;
        int length= (int)Math.pow(2,quantumState.getParticles());
        ArrayList<String> arrayList= quantumState.getParticlesName();
        int index1=arrayList.indexOf(a);
        int index2=arrayList.indexOf(b);
        double [] result=new double[length];
        double [] origin= quantumState.getState();
        //生成交换后的量子态的复数表示法
        for (int i=0;i<origin.length;i++){
            if(origin[i]!=0.0){
                StringBuffer str= new StringBuffer(Tools.toFixedLenBinary(i, quantumState.getParticles()));
                char temp=str.charAt(index1);
                str.setCharAt(index1,str.charAt(index2));
                str.setCharAt(index2,temp);
                result[Integer.parseInt(str.toString(),2)]=origin[i];
            }
        }
        quantumState.setState(result);
        //生成交换后的粒子对应的名字
        quantumState.setParticlesName(index1+1,b);
        quantumState.setParticlesName(index2+1,a);
    }
}
