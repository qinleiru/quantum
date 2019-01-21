package com.quantum.tools;

import java.util.Random;

public class Tools {

    /**
     * 转换为定长的字符串
     * @param value
     * @param length
     * @return
     */
    public static String toFixedLenBinary(int value,int length){
        String str=Integer.toBinaryString(value);
        while(str.length()<length){
            str="0"+str;
        }
        return str;
    }

    /**
     * 返回指定范围内的随机整数
     * @param min
     * @param max
     * @return
     */
    public static int randInt(int min,int max){
        Random rand=new Random();
        int randomNum=rand.nextInt((max-min)+1)+min;
        return  randomNum;
    }


}
