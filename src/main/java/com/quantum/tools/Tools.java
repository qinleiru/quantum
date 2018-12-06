package com.quantum.tools;

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
}
