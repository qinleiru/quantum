package com.quantum.state;

import java.util.ArrayList;

public class ClusterState implements QuantumState {
    private int particles;
    private double[] state;
    private ArrayList<String> particlesName;

    public ClusterState(double[] state,int particles){
        this.particles=particles;
        this.state=state;
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
