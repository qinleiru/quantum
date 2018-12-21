package com.quantum.state;

import com.quantum.oparate.MathOperation;

//todo:可以使用单例模式进行设计
public class CommonState {
    //常见的单粒子态
    //|0>态
    public  static final SingleState Zero_State=new SingleState(new double[]{1,0});
    //|1>态
    public  static final SingleState One_State=new SingleState(new double[]{0,1});
    //|+>态
    public  static final SingleState Plus_State=new SingleState(new double[]{Math.pow(2,-0.5),Math.pow(2,-0.5)});
    //|->态
    public  static final SingleState Minus_State=new SingleState(new double[]{Math.pow(2,-0.5),-Math.pow(2,-0.5)});

    //常见的两粒子态，Bell态oi
    //|𝝭>+态
    public static final DoubleState PsiPlus_State=new DoubleState(MathOperation.multiple(MathOperation.add(MathOperation.tensor(Zero_State.getState(),One_State.getState()),MathOperation.tensor(One_State.getState(),Zero_State.getState())),Math.pow(2,-0.5)));
    //|𝝭>-态
    public static final DoubleState PsiMinus_State=new DoubleState(MathOperation.multiple(MathOperation.subtract(MathOperation.tensor(Zero_State.getState(),One_State.getState()),MathOperation.tensor(One_State.getState(),Zero_State.getState())),Math.pow(2,-0.5)));
    //|Ф>+态
    public static final DoubleState PhiPlus_State= new DoubleState(MathOperation.multiple(MathOperation.add(MathOperation.tensor(Zero_State.getState(),Zero_State.getState()),MathOperation.tensor(One_State.getState(),One_State.getState())),Math.pow(2,-0.5)));
    //|Ф>-态
    public static final DoubleState PhiMinus_State=new DoubleState(MathOperation.multiple(MathOperation.subtract(MathOperation.tensor(Zero_State.getState(),Zero_State.getState()),MathOperation.tensor(One_State.getState(),One_State.getState())),Math.pow(2,-0.5)));
}
