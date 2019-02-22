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
    /*
        查询结果的值
     */
    public  int findResultAmount(double a,double c,double omega,int result,int authority){
        if(sqlSession==null) {
            sqlSession=MysqlSession.getSession();
        }
        sqlSession.insert("findResultAmount");
        //关闭session
        sqlSession.close();
        return 0;
    }

}
