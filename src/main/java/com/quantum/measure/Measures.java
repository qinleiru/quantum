package com.quantum.measure;

public enum Measures {
    X("X基测量",1),Z("Z基测量",2),BELL("Bell基测量",3);
    //成员变量
    private String measure;
    private int index;
    //构造方法
    private Measures(String measure, int index) {
        this.measure=measure;
        this.index=index;
    }
    //普通方法
    public  static String getMeasure(int index){
        for (Measures m:Measures.values()){
            if(m.getIndex()==index){
                return m.measure;
            }
        }
        return null;
    }
    //get set方法
    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
