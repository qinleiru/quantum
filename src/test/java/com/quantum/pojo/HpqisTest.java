package com.quantum.pojo;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;

public class HpqisTest {

    @Test
    public void hpqisFindByIdTest(){
        //定义读取文件名
        String resources = "mybatis-config.xml";
        //创建流
        Reader reader=null;
        try {
            //读取mybatis-config.xml文件到reader对象中
            reader= Resources.getResourceAsReader(resources);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //初始化mybatis,创建SqlSessionFactory类的实例
        SqlSessionFactory sqlMapper=new SqlSessionFactoryBuilder().build(reader);
        //创建session实例
        SqlSession session=sqlMapper.openSession();
        //传入参数查询，返回结果
        Hpqis hpqis=session.selectOne("findById",1);
        //输出结果
        System.out.println(hpqis.getId());
        System.out.println(hpqis.getValue_of_a());
        System.out.println(hpqis.getValue_of_b());
        System.out.println(hpqis.getValue_of_c());
        System.out.println(hpqis.getValue_of_d());
        System.out.println(hpqis.getResult());
        //关闭session
        session.close();
    }
}