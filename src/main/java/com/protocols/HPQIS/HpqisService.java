package com.protocols.HPQIS;

import com.protocols.dao.impl.HpqisDaoImpl;
import com.protocols.pojo.DataPoint;
import com.protocols.pojo.Hpqis;

import java.util.ArrayList;

public class HpqisService {
    public static DataPoint getPointData(double a,double c,double omega,int authority){
        DataPoint dataPoint=new DataPoint();
        Hpqis hpqis=new Hpqis();
        hpqis.setValue_of_a(a);
        hpqis.setValue_of_c(c);
        hpqis.setValue_of_omega(omega);
        hpqis.setResult(1);
        hpqis.setAuthority(authority);
        HpqisDaoImpl hpqisDaoImpl=new HpqisDaoImpl();
        double success=hpqisDaoImpl.findResultAmount(hpqis);
        System.out.println(success);
        hpqis.setResult(0);
        double fail=hpqisDaoImpl.findResultAmount(hpqis);
        dataPoint.setX(omega);
        double Y=success/(fail+success);
        System.out.println(Y);
        dataPoint.setY(Y);
        return dataPoint;
    }
    /**
     * a的值与c的值对应图像中的线
     * @param a
     * @param c
     * @param authority
     * @return
     */
    public static ArrayList getLineData(double a,double c,int authority){
        ArrayList<DataPoint> arrayList=new ArrayList<>();
        arrayList.add(getPointData(a,c,1,authority));
        arrayList.add(getPointData(a,c,1.5,authority));
        arrayList.add(getPointData(a,c,2,authority));
        arrayList.add(getPointData(a,c,2.5,authority));
        arrayList.add(getPointData(a,c,3,authority));
        return arrayList;
    }
//    public static void main(String[] args){
//        ArrayList<DataPoint> arrayList=HpqisService.getLineData(0.5,0.5,1);
//        for(DataPoint dataPoint:arrayList){
//            System.out.println("X的值是"+dataPoint.getX()+" Y的值是"+dataPoint.getY());
//        }
//    }
}

