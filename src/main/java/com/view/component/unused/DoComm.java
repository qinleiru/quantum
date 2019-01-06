package com.view.component.unused;

import com.view.component.DialogComponent;
import com.view.component.TextComponent;

import java.text.SimpleDateFormat;

public class DoComm {
    //对应的量子仿真的协议
    private String protocolType;
    //文本框对象的操作
    private TextComponent textArea;
    public DoComm(String protocolType){
        this.protocolType=protocolType;
        //对文本框对象的操作初始化
        textArea = new TextComponent();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        textArea.setCommText(df.format(System.currentTimeMillis())+" "+protocolType+ "---------------已经开始---------------");
        this.comm();
        textArea.setCommText(df.format(System.currentTimeMillis())+" "+protocolType+ "---------------通信完成---------------");
        //弹出对话框提示
        new DialogComponent(null, protocolType + " 通信完成");
    }
    private void comm(){
        switch(protocolType){
            case "量子隐形传态协议":
//                QuantumTeleportation.run(textArea);
                break;
            case "分层量子信息拆分协议":
//                HQIS.run(textArea);
                break;

        }
    }
}
