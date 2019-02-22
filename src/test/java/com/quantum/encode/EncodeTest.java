package com.quantum.encode;

import java.io.UnsupportedEncodingException;

public class EncodeTest {
    public static void main(String [] args) throws UnsupportedEncodingException {
        String s = "abc我";
        byte [] nn = String.valueOf(s).getBytes("GBK");
        System.out.println(nn.length);
        for (int i=0; i< nn.length; i++) {
            byte ss = nn[i];
            System.out.print(Integer.toBinaryString((Integer)(ss & 0xFF)));
            System.out.print(" ");
        }
        System.out.println();

        String answer=new String(nn,"GBK");
        System.out.println(answer);
        byte test=1;
        if (test==1){
            System.out.println("相等");
        }
        else{
            System.out.println("不相等");
        }
    }
}
