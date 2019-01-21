package com.protocols.HPQIS;

import com.protocols.dao.HpqisDAO;
import com.protocols.pojo.Hpqis;
import com.quantum.measure.Measures;
import com.quantum.oparate.MathOperation;
import com.quantum.oparate.QuantumOperation;
import com.quantum.state.DoubleState;
import com.quantum.state.MultiState;
import com.quantum.state.QuantumState;
import com.quantum.tools.QuantumTools;
import com.quantum.tools.Tools;
import com.view.component.TextComponent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

//下面仿真本论文中提出的概率型分层量子信息拆分协议
//todo：目前只实现了1个权限高的代理者2个权限低的代理者
//todo：用于传送的秘密量子信息为固定的
public class HPQIS {
    /**
     * 定义变量用于存储测量结果
     */
    public static MultiState systemState;  //记录系统的态
    public static int resultX;  //对粒子x、粒子1进行Bell态的测量结果
    public static int resultY;  //对粒子y、粒子5进行Bell态的测量结果
    public static final double [] coefficients=getCoefficients();//构建量子信道的两个簇态的系数，分别对应a、b、c、d\
    public static void run(TextComponent textArea,String agents) {
        ArrayList<HighAgent> highAgents = new ArrayList<>();   //用于存储权限高的代理者
        ArrayList<LowAgent> lowAgents = new ArrayList<>();    //用于存储权限低的代理者
        HighAgent Bob=new HighAgent();
        highAgents.add(Bob);
        LowAgent Charlie=new LowAgent();
        LowAgent David=new LowAgent();
        lowAgents.add(Charlie);
        lowAgents.add(David);
        Sender Alice = new Sender(highAgents, lowAgents);
        Alice.execute();  //Alice进行了操作,包括准备秘密量子比特、准备两个簇态、发送粒子以及测量等操作
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        textArea.setCommText(Alice.printMessage);
        boolean highAuthor=false;       //此变量用于标志哪一种权限的代理者来恢复秘密消息
        switch (agents) {
            case "权限高":
                highAuthor=true;
                break;
            case "权限低":
                highAuthor=false;
                break;
        }
        if(highAuthor){
            //协议中的权限高的代理者来恢复秘密量子比特
            textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "权限高的代理者Bob恢复秘密量子比特");
            int random=Tools.randInt(1,2);
            if(random==1){
                textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Charlie对手中的粒子进行Z基测");
                Charlie.measure(Measures.Z);  //对手中的粒子进行测量
                textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Charlie将测量结果发送给Bob");
                Charlie.sendResult(Bob);
                /*
                    用于测试的代码
                 */
//                System.out.println("此时系统的态为");
//                System.out.println(systemState.showBinaryState());
//                systemState.showParticleName();
            }
            else{
                textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "David对手中的粒子进行Z基测量");
                David.measure(Measures.Z);    //对手中的粒子进行测量
                textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "David将测量结果发送给Bob");
                David.sendResult(Bob);
                /*
                    用于测试的代码
                 */
//                System.out.println("此时系统的态为");
//                System.out.println(systemState.showBinaryState());
//                systemState.showParticleName();
            }
            textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Bob收到代理者测量结果");
            textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Bob对手中的粒子进行操作，恢复秘密量子比特");
            Bob.restore();
            textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Bob引入辅助粒子m、辅助粒子n执行POVM测量并进行操作恢复秘密量子比特");
           if(Bob.POVMResult==false){
               textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Bob进行POVM测量失败");
               Hpqis hpqis=new Hpqis(coefficients[0],coefficients[1],coefficients[2],coefficients[3],1.5,0,1);
               HpqisDAO hpqisDAO=new HpqisDAO();
               hpqisDAO.addResult(hpqis);
               return;
           }
            Hpqis hpqis=new Hpqis(coefficients[0],coefficients[1],coefficients[2],coefficients[3],1.5,1,1);
            HpqisDAO hpqisDAO=new HpqisDAO();
            hpqisDAO.addResult(hpqis);
            textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Bob手中的粒子态为"+QuantumTools.showBinaryState(getOwnSate(systemState,Bob.getParticleName())));
        }
        else{
            //协议中权限低的代理者来恢复秘密量子比特
            int random=Tools.randInt(1,2);
            if(random==1){
                textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Charlie来恢复秘密量子比特");
                textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Bob对手中的粒子进行X基测量");
                Bob.measure(Measures.X);
                textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Bob将测量结果发送给Charlie");
                Bob.sendResult(Charlie);
                textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "David对手中的粒子进行X基测量");
                David.measure(Measures.X);
                textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "David将测量结果发送给Charlie");
                David.sendResult(Charlie);
                textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Charlie收到代理者测量结果");
                textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Charlie对手中的粒子进行操作，恢复秘密量子比特");
                Charlie.restore();
                textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Charlie引入辅助粒子m、辅助粒子n执行POVM测量并进行操作恢复秘密量子比特");
                if(Charlie.POVMResult==false){
                    textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Charlie进行POVM测量失败");
                    Hpqis hpqis=new Hpqis(coefficients[0],coefficients[1],coefficients[2],coefficients[3],1.5,0,0);
                    HpqisDAO hpqisDAO=new HpqisDAO();
                    hpqisDAO.addResult(hpqis);
                    return;
                }
                Hpqis hpqis=new Hpqis(coefficients[0],coefficients[1],coefficients[2],coefficients[3],1.5,1,0);
                HpqisDAO hpqisDAO=new HpqisDAO();
                hpqisDAO.addResult(hpqis);
                textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Charlie手中的粒子态为"+ QuantumTools.showBinaryState(getOwnSate(systemState,Charlie.getParticleName())));
            }
            else{
                textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "David来恢复秘密量子比特");
                textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Bob对手中的粒子进行X基测量");
                Bob.measure(Measures.X);
                textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Bob将测量结果发送给David");
                Bob.sendResult(David);
                textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Charlie对手中的粒子进行X基测量");
                Charlie.measure(Measures.X);
                textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "Charlie将测量结果发送给David");
                Charlie.sendResult(David);
                textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "David收到代理者测量结果");
                textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "David对手中的粒子进行操作，恢复秘密量子比特");
                David.restore();
                textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "David引入辅助粒子m、辅助粒子n执行POVM测量并进行操作恢复秘密量子比特");
                if(David.POVMResult==false){
                    textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "David进行POVM测量失败");
                    Hpqis hpqis=new Hpqis(coefficients[0],coefficients[1],coefficients[2],coefficients[3],1.5,0,0);
                    HpqisDAO hpqisDAO=new HpqisDAO();
                    hpqisDAO.addResult(hpqis);
                    return;
                }
                Hpqis hpqis=new Hpqis(coefficients[0],coefficients[1],coefficients[2],coefficients[3],1.5,1,0);
                HpqisDAO hpqisDAO=new HpqisDAO();
                hpqisDAO.addResult(hpqis);
                textArea.setCommText(df.format(System.currentTimeMillis())+" "+ "David手中的粒子态为"+QuantumTools.showBinaryState(getOwnSate(systemState,David.getParticleName())));
            }
        }
        System.out.println("----------------------------------------------------");

    }
    public static DoubleState getOwnSate(QuantumState quantumState,ArrayList<String> particleNames){
        DoubleState state=new DoubleState();
        for(String particle:particleNames){
            if(Integer.parseInt(particle)<=(quantumState.getParticles()-2)/2){
                QuantumOperation.quantumSwap(quantumState,particle,systemState.getParticlesName().get(systemState.getParticlesName().size()-2));
                state.setParticlesName(1,particle);
            }
            else{
                QuantumOperation.quantumSwap(quantumState,particle,systemState.getParticlesName().get(systemState.getParticlesName().size()-1));
                state.setParticlesName(2,particle);
            }
        }
        double[] ownState=new double[4];
        try {
            for (int i = 0, j = 0; i < Math.pow(2, quantumState.getParticles()); i++) {
                if (quantumState.getState()[i] != 0) {
                    ownState[j++] = quantumState.getState()[i];
                }
            }
        }
        catch(ArrayIndexOutOfBoundsException e){
            System.out.println("恢复秘密消息失败");
            return null;
        }
        state.setState(ownState);
        return state;
    }

    /**
     * 此方法用于返回构建信道的两个非最大纠缠态使用的系数
     * @return
     */
    public static double[] getCoefficients(){
//        double a=Math.random();
//        double b=Math.random();
        double a=0.5;
        double b=0.5;
        double c=0.5;
        double d=0.5;
        double clusterState1[]=new double[]{a,a,b,b};
        MathOperation.normalization(clusterState1);
        a=clusterState1[0];
        b=clusterState1[2];
//        double c=Math.random();
//        double d=Math.random();
        double clusterState2[]=new double[]{c,c,d,d};
        MathOperation.normalization(clusterState2);
        c=clusterState1[0];
        d=clusterState1[2];
        return new double[]{a,b,c,d};
    }
    public static void main(String[] args){
        TextComponent textComponent=new TextComponent();
        for (int i=0;i<1000;i++) {
            run(textComponent,"权限低");
        }
    }
}
