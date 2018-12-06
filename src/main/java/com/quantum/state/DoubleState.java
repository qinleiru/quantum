package com.quantum.state;

import com.quantum.oparate.MathOperation;
import com.quantum.tools.QuantumState;

import java.util.ArrayList;

/**
 * 两粒子纠缠态
 */
public class DoubleState extends QuantumState {
    private int particles;
    private double[] state;
    private ArrayList<String> particlesName;

    /**
     * 随机生成任意的两量子比特
     */
    public DoubleState(){
        state=new double[4];
        for (int i=0;i<4;i++){
            state[i]=Math.random();
        }
        //进行归一化操作
        MathOperation.normalization(state);
        particles=2;
        initParticlesName();
    }

    /**
     * 生成指定的两量子比特
     * @param state
     */
    public DoubleState(double[] state){
        this.state=state;
        particles=2;
        initParticlesName();
    }

    @Override
    public double[] getState() {
        return this.state;
    }

    @Override
    public void setState(double[] state) {
        this.state = state;
    }

    @Override
    public int getParticles() {
        return this.particles;
    }

    @Override
    public void setParticles(int particles) {
        this.particles=particles;
    }

    @Override
    public ArrayList<String> getParticlesName() {
        return this.particlesName;
    }

    /**
     * 初始化量子态粒子的名字为数字
     */
    public void initParticlesName(){
        particlesName=new ArrayList<>();
        for(int i=1;i<=particles;i++){
            particlesName.add(""+i);
        }
    }

    /**
     * 设置粒子的名字
     * @param pos
     * @param name
     */
    public void setParticlesName(int pos, String name) {
        getParticlesName().set(pos-1,name);
    }
}
