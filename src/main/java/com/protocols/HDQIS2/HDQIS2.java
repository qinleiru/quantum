package com.protocols.HDQIS2;

import com.quantum.measure.Measures;
import com.quantum.oparate.QuantumOperation;
import com.quantum.state.DoubleState;
import com.quantum.state.MultiState;
import com.quantum.state.QuantumState;
import com.quantum.tools.QuantumTools;
import com.quantum.tools.Tools;

import java.util.ArrayList;

//下面仿真Xu等人提出的分层量子信息拆分协议
//todo：目前只实现了1个权限高的代理者2个权限低的代理者
public class HDQIS2 {
    /**
     * 定义变量用于存储测量结果
     */
    public static MultiState systemState;  //记录系统的态
    public static int resultX;  //对粒子x、粒子1进行Bell态的测量结果
    public static int resultY;  //对粒子y、粒子5进行Bell态的测量结果
    public static void run() {
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
        boolean highAuthor=false;       //此变量用于标志哪一种权限的代理者来恢复秘密消息
        if(highAuthor){
            //协议中的权限高的代理者来恢复秘密量子比特
            System.out.println("权限高的代理者Bob恢复秘密量子比特");
            int random=Tools.randInt(1,2);
            if(random==1){
                System.out.println("权限低的代理者Charlie对手中的粒子进行Z基测量");
                Charlie.measure(Measures.Z);  //对手中的粒子进行测量
                System.out.println("Charlie将测量结果发送给Bob");
                Charlie.sendResult(Bob);
                /*
                    用于测试的代码
                 */
//                System.out.println("此时系统的态为");
//                System.out.println(systemState.showBinaryState());
//                systemState.showParticleName();
            }
            else{
                System.out.println("权限低的代理者David对手中的粒子进行Z基测量");
                David.measure(Measures.Z);    //对手中的粒子进行测量
                System.out.println("David将测量结果发送给Bob");
                David.sendResult(Bob);
                /*
                    用于测试的代码
                 */
//                System.out.println("此时系统的态为");
//                System.out.println(systemState.showBinaryState());
//                systemState.showParticleName();
            }
            System.out.println("Bob收到代理者测量结果");
            System.out.println("Bob对手中的粒子进行操作，恢复秘密量子比特");
            Bob.restore();
            System.out.println("此时Bob手中的粒子态为");
            System.out.println(QuantumTools.showBinaryState(getOwnSate(systemState,Bob.particleName)));
        }
        else{
            //协议中权限低的代理者来恢复秘密量子比特
            int random=Tools.randInt(1,2);
            if(random==1){
                System.out.println("权限低的代理者Charlie来恢复秘密量子比特");
                System.out.println("Bob对手中的粒子进行X基测量");
                Bob.measure(Measures.X);
                System.out.println("Bob将测量结果发送给Charlie");
                Bob.sendResult(Charlie);
                System.out.println("David对手中的粒子进行X基测量");
                David.measure(Measures.X);
                System.out.println("David将测量结果发送给Charlie");
                David.sendResult(Charlie);
                System.out.println("Charlie收到代理者测量结果");
                System.out.println("Charlie对手中的粒子进行操作，恢复秘密量子比特");
                Charlie.restore();
                System.out.println("此时Charlie手中的粒子态为");
                System.out.println(QuantumTools.showBinaryState(getOwnSate(systemState,Charlie.particleName)));
            }
            else{
                System.out.println("权限低的代理者David来恢复秘密量子比特");
                System.out.println("Bob对手中的粒子进行X基测量");
                Bob.measure(Measures.X);
                System.out.println("Bob将测量结果发送给David");
                Bob.sendResult(David);
                System.out.println("Charlie对手中的粒子进行X基测量");
                Charlie.measure(Measures.X);
                System.out.println("Charlie将测量结果发送给David");
                Charlie.sendResult(David);
                System.out.println("David收到代理者测量结果");
                System.out.println("David对手中的粒子进行操作，恢复秘密量子比特");
                David.restore();
                System.out.println("此时David手中的粒子态为");
                System.out.println(QuantumTools.showBinaryState(getOwnSate(systemState,David.particleName)));

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
//    public static void main(String[] args){
//        for (int i=0;i<10;i++) {
//            run();
//        }
//    }
}
