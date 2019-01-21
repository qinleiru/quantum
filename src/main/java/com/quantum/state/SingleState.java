package com.quantum.state;

import java.util.ArrayList;

/**
 * 单量子态
 */
public class SingleState implements QuantumState {

    private final int particles=1;
    private double[] state;
    private ArrayList<String> particlesName;

    //随机生成一个单量子比特,初始化粒子的名称为数字
    public SingleState(){
        double a=Math.random();
        this.state=new double[]{a,Math.sqrt(1-Math.pow(a,2))};
        setParticles(1);
        initParticlesName();
    }

    //生成指定的单量子比特，初始化粒子的名称为数字
    public SingleState(double[] state){
        this.state=state;
        setParticles(1);
        initParticlesName();
    }

    @Override
    public double[] getState() {
        return this.state;
    }

    @Override
    public void setState(double[] state) {
        this.state=state;
    }

    @Override
    public int getParticles() {
        return this.particles;
    }

    @Override
    public void setParticles(int particles) {
    }

    @Override
    public ArrayList<String> getParticlesName() {
        return this.particlesName;
    }

    /**
     * 初始化量子态粒子的名字为数字
     */
    public void initParticlesName(){
        particlesName =new ArrayList<>();
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
