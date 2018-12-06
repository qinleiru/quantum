package com.quantum.tools;

import javax.swing.text.html.HTMLDocument;
import java.util.ArrayList;
import java.util.Iterator;

//todo:在写粒子态的过程中，假设粒子态的名称不重复
public abstract class QuantumState {

    public abstract double[] getState();
    public abstract void setState(double[] state);
    public abstract int getParticles();
    public abstract void setParticles(int particles);
    public abstract ArrayList<String> getParticlesName();
    public abstract void setParticlesName(int pos,String name);

    /**
     * 以二进制形式输出量子态
     * @return
     */
    //todo:默认第一个参数是正的
    public String showBinaryState() {
        int particles=getParticles();
        double []states=getState();
        String result="";
        for(int i=0;i<Math.pow(2,particles);i++){
            String str=Tools.toFixedLenBinary(i,particles);
            if(states[i]>0&&result.length()!=0) {
                result =result+"+"+ states[i] + "|" + str + ">";
            }
            if (states[i]>0&&result.length()==0){
                result =result+ states[i] + "|" + str + ">";
            }
            else if(states[i]<0){
                result =result+ states[i] + "|" + str + ">";
            }
        }
        return result;
    }

    public void showParticleName(){
        ArrayList<String> arrayList=getParticlesName();
        Iterator iterator=arrayList.iterator();
        while(iterator.hasNext()){
            System.out.print(iterator.next()+"  ");
        }
    }

}
