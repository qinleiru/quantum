package com.protocols.pojo;

/**
 * 对应数据库中的表
 */
public class Hpqis {
    private  long id;
    private double value_of_a;
    private double value_of_b;
    private double value_of_c;
    private double value_of_d;
    private double value_of_omega;
    private int result;    //1表示成功，0表示失败
    private int authority;   //1表示权限高，0表示权限低
    public  Hpqis(){

    }
    public  Hpqis(double value_of_a,double value_of_b,double value_of_c,double value_of_d,double value_of_omega,int result,int authority){
        this.value_of_a=value_of_a;
        this.value_of_b=value_of_b;
        this.value_of_c=value_of_c;
        this.value_of_d=value_of_d;
        this.value_of_omega=value_of_omega;
        this.result=result;
        this.authority=authority;
    }

    public int getAuthority() {
        return authority;
    }

    public void setAuthority(int authority) {
        this.authority = authority;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getValue_of_a() {
        return value_of_a;
    }

    public void setValue_of_a(double value_of_a) {
        this.value_of_a = value_of_a;
    }

    public double getValue_of_b() {
        return value_of_b;
    }

    public void setValue_of_b(double value_of_b) {
        this.value_of_b = value_of_b;
    }

    public double getValue_of_c() {
        return value_of_c;
    }

    public void setValue_of_c(double value_of_c) {
        this.value_of_c = value_of_c;
    }

    public double getValue_of_d() {
        return value_of_d;
    }

    public void setValue_of_d(double value_of_d) {
        this.value_of_d = value_of_d;
    }

    public double getValue_of_omega() {
        return value_of_omega;
    }

    public void setValue_of_omega(double value_of_omega) {
        this.value_of_omega = value_of_omega;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
    @Override
    public  String toString(){
        return this.value_of_a+" "+this.value_of_b+" "+this.value_of_c+" "+this.value_of_d+" "+this.value_of_omega+" "+this.result+" "+this.authority;
    }

}
