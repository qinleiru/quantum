package com.protocols.pojo;

public class DataPoint {
    private double X;
    private double Y;

    public DataPoint(){}

    public DataPoint(double X,double Y){
        this.X=X;
        this.Y=Y;
    }
    public double getX() {
        return X;
    }

    public void setX(double x) {
        X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        Y = y;
    }
}
