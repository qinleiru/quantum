package com.quantum.communication;

import org.junit.Test;

public class AliceTest {
    @Test
    public void testPrepareState(){
        Alice alice=new Alice(1,2);
        alice.prepareState();

        Alice alice1=new Alice(2,3);
        alice1.prepareState();
    }
}
