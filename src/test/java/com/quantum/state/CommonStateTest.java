package com.quantum.state;

import org.junit.Test;

import static org.junit.Assert.*;

public class CommonStateTest {
    public static final double [] phiPlus_State=new double[]{Math.pow(2,-0.5),0,0,Math.pow(2,-0.5)};
    public static final double [] phiMinus_State=new double[]{Math.pow(2,-0.5),0,0,-Math.pow(2,-0.5)};
    public static final double [] psiPlus_State=new double[]{0,Math.pow(2,-0.5),Math.pow(2,-0.5),0};
    public static final double [] psiMinus_State=new double[]{0,Math.pow(2,-0.5),-Math.pow(2,-0.5),0};
    //用于验证Bell态的正确性
    @Test
    public void testBellState(){
        assertArrayEquals(phiPlus_State,CommonState.PhiPlus_State.getState(),0);
        assertArrayEquals(phiMinus_State,CommonState.PhiMinus_State.getState(),0);
        assertArrayEquals(psiPlus_State,CommonState.PsiPlus_State.getState(),0);
        assertArrayEquals(psiMinus_State,CommonState.PsiMinus_State.getState(),0);
    }

}