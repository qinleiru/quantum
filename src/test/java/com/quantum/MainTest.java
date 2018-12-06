package com.quantum;

import org.junit.Test;

public class MainTest {
    @Test
    public void testParseInt(){
        String str="1000";
        System.out.println(Integer.parseInt(str.toString(),2));
    }
}
