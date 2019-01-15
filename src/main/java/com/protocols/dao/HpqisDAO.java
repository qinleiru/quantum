package com.protocols.dao;

import com.protocols.mysql.MysqlSession;
import com.protocols.pojo.Hpqis;
import org.apache.ibatis.session.SqlSession;

public class HpqisDAO {
    private SqlSession sqlSession= MysqlSession.getSession();
    /*
        添加结果
     */
    public void addResult(Hpqis hpqis){
        if(sqlSession==null) {
            sqlSession=MysqlSession.getSession();
        }
        sqlSession.insert("addResult",hpqis);
        sqlSession.commit();
        //关闭session
        sqlSession.close();
    }
//    public static void main(String[] args){
//        HpqisDAO hpqisDAO=new HpqisDAO();
//        Hpqis hpqis=new Hpqis();
//        hpqis.setValue_of_a(0.5);
//        hpqis.setValue_of_b(0.5);
//        hpqis.setValue_of_c(0.5);
//        hpqis.setValue_of_d(0.5);
//        hpqis.setValue_of_omega(1);
//        hpqis.setAuthority(1);
//        hpqis.setResult(1);
//        hpqisDAO.addResult(hpqis);
//    }
}
