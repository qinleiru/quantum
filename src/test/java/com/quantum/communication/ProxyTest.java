package com.quantum.communication;

import com.quantum.state.ClusterState;
import com.quantum.state.DoubleState;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProxyTest {
    @Test
    public void getOwnState(){
        double []states=new double[]{0.5,0,0,0.5,0,0,0,0,0,0,0,0,0.5,0,0,-0.5};
        //制备1个四粒子簇态
        ClusterState clusterState=new ClusterState(states,4);
        //设置不同粒子对应的名字
        clusterState.setParticlesName(1,"1");
        clusterState.setParticlesName(2,"2");
        clusterState.setParticlesName(3,"x");
        clusterState.setParticlesName(4,"y");
        System.out.println(clusterState.showBinaryState());
        clusterState.showParticleName();
        System.out.println();
        Proxy proxy=new Proxy();
        proxy.receive("x");
        proxy.receive("2");
        DoubleState doubleState=proxy.getOwnState(clusterState);
        System.out.println(doubleState.showBinaryState());
        doubleState.showParticleName();
        double expectResult[]=new double[]{0.5,0.5,0.5,-0.5};
        assertArrayEquals(expectResult,proxy.getOwnState(clusterState).getState(),0);
    }
}