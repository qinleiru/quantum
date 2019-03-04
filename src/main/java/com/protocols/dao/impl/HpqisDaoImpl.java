package com.protocols.dao.impl;

import com.protocols.dao.HpqisDao;
import com.protocols.mysql.MysqlSession;
import com.protocols.pojo.Hpqis;
import org.apache.ibatis.session.SqlSession;

public class HpqisDaoImpl implements HpqisDao {
    /*
        添加结果
     */
    public void addResult(Hpqis hpqis){
        SqlSession sqlSession= MysqlSession.getSession();
        sqlSession.insert("addResult",hpqis);
        sqlSession.commit();
        //关闭session
        sqlSession.close();
    }
    /*
        查询结果的值
     */
    public  int findResultAmount(Hpqis hpqis){
        SqlSession sqlSession= MysqlSession.getSession();
        int amount=sqlSession.selectOne("findResultAmount",hpqis);
        //关闭session
        sqlSession.close();
        return amount;
    }
//    /*
//        查询总的结果
//     */
//    @Override
//    public int findAllResultByValue(Hpqis hpqis){
//        if(sqlSession==null) {
//            sqlSession=MysqlSession.getSession();
//        }
//        int amount=sqlSession.selectOne("findAllResultByValue",hpqis);
//        //关闭session
//        sqlSession.close();
//        return amount;
//    }


    /*
       测试代码
     */
    public static void main(String[] args){
        Hpqis hpqis=new Hpqis();
        hpqis.setValue_of_a(0.5);
        hpqis.setValue_of_c(0.5);
        hpqis.setValue_of_omega(1);
        hpqis.setResult(1);
        hpqis.setAuthority(1);
        HpqisDaoImpl hpqisDaoImpl=new HpqisDaoImpl();
        System.out.println("结果是"+hpqisDaoImpl.findResultAmount(hpqis));
    }

}
