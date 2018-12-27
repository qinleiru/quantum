package com.quantum.measure;

import com.quantum.oparate.MathOperation;
import com.quantum.state.ClusterState;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProjectiveMeasureTest {

    /**
     * Z基对单粒子进行测量
     */
    @Test
    public void measureBaseZ() {
        System.out.println("测试测试测试测试");
        //一个四粒子簇态，簇态的形式为：1/2（|0000>+|0011>+|1100>-|1111>）
        double A[]=new double[]{0.5,0,0,0.5,0,0,0,0,0,0,0,0,0.5,0,0,-0.5};
        ClusterState clusterState=new ClusterState(A,4);
        int result=ProjectiveMeasure.measureBaseZ(clusterState,"3");
        if(result==1){
            double[] expectResult=new double[]{0,0,0,Math.pow(2,-0.5),0,0,0,0,0,0,0,0,0,0,0,-Math.pow(2,-0.5)};
            assertArrayEquals(expectResult,clusterState.getState(),0.00001);
        }
        else{
            double[] expectResult=new double[]{Math.pow(2,-0.5),0,0,0,0,0,0,0,0,0,0,0,Math.pow(2,-0.5),0,0,0};
            assertArrayEquals(expectResult,clusterState.getState(),0.00001);
        }
        System.out.println("测试测试测试测试");

    }

    /**
     * X基对单粒子进行测量
     */
    @Test
    public void measureBaseX() {
        //一个四粒子簇态，簇态的形式为：1/2（|0000>+|0011>+|1100>-|1111>）
        double A[]=new double[]{0.5,0,0,0.5,0,0,0,0,0,0,0,0,0.5,0,0,-0.5};
        ClusterState clusterState=new ClusterState(A,4);
        int result=ProjectiveMeasure.measureBaseX(clusterState,"3");
        if(result==1){
            double[] expectResult=new double[]{0,0,0.5,-0.5,0,0,0,0,0,0,0,0,0,0,0.5,0.5};
            assertArrayEquals(expectResult,clusterState.getState(),0.00001);
        }
        else{
            double[] expectResult=new double[]{0.5,0.5,0,0,0,0,0,0,0,0,0,0,0.5,-0.5,0,0};
            assertArrayEquals(expectResult,clusterState.getState(),0.00001);
        }
    }

    /**
     * Bell基对两量子比特进行测量
     */
    @Test
    public void measureBeseBell() {
        //一个四粒子簇态，簇态的形式为：1/2（|0000>+|0011>+|1100>-|1111>）
        double []states=new double[]{0.5,0,0,0,0,0.5,0,0,0,0,0.5,0,0,0,0,-0.5};
        //制备1个四粒子簇态
        ClusterState clusterState=new ClusterState(states,4);
        //设置不同粒子对应的名字
        clusterState.setParticlesName(1,"1");
        clusterState.setParticlesName(2,"x");
        clusterState.setParticlesName(3,"2");
        clusterState.setParticlesName(4,"y");
        int result=ProjectiveMeasure.measureBeseBell(clusterState,"x","y");
        if(result==1){
            double[] expectResult=new double[]{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
            assertArrayEquals(expectResult,clusterState.getState(),0.00001);
        }
        if(result==3){
            double[] expectResult=new double[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0};
            assertArrayEquals(expectResult,clusterState.getState(),0.00001);
        }
    }
}