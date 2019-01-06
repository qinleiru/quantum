package com.view.simulation;

import com.protocols.HDQIS.HDQIS;
import com.protocols.HPQIS.HPQIS;
import com.view.component.DialogComponent;
import com.view.component.TextComponent;

import java.text.SimpleDateFormat;

/**
 * 进行通信过程的执行器
 */
public class DoSimulation {
    private  String protocolType;
    private  String agents;
    private TextComponent textArea;
    public DoSimulation(String protocols, String attacks, String agents, String secret){
        /*
            此部分代码用于测试
         */
        System.out.println("++++++仿真的通信协议为++++++"+protocols);
        System.out.println("++++++协议的攻击方式为++++++"+attacks);
        System.out.println("++++++恢复秘密消息的代理者++++++"+agents);
        System.out.println("++++++要恢复的秘密消息为++++++"+secret);
        this.protocolType=protocols;
        this.agents=agents;

        //获取通信的协议
        textArea = new TextComponent();
        try {
            //对文本框对象的操作初始化
            textArea = new TextComponent();
            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            textArea.setCommText(df.format(System.currentTimeMillis())+" "+protocolType+ "---------------已经开始---------------");
            this.comm();
            textArea.setCommText(df.format(System.currentTimeMillis())+" "+protocolType+ "---------------通信完成---------------");
            //弹出对话框提示
            new DialogComponent(null, " 通信完成");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void comm(){
        switch(protocolType){
            case "确定型分层量子信息拆分协议":
                HDQIS.run(textArea,agents);
                break;
            case "概率型分层量子信息拆分协议":
                HPQIS.run(textArea,agents);
                break;

        }
    }
}
