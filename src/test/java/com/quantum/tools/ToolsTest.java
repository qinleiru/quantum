package com.quantum.tools;

import org.junit.Test;

import static org.junit.Assert.*;

public class ToolsTest {
    /**
     * 测试返回指定范围的随机整数
     */
    @Test
    public void testRandInt(){
        assertEquals(1,Tools.randInt(1,1));
    }

}