
import com.protocols.pojo.Hpqis;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;

//todo:目前更新、删除的部分不可用
public class HpqisTest {

    /**
     * 查询结果
     */
    @Test
    public void findResultByIdTest(){
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
        Hpqis hpqis=session.selectOne("findResultById",6);
        //输出结果
        System.out.println(hpqis.getId());
        System.out.println(hpqis.getValue_of_a());
        System.out.println(hpqis.getValue_of_b());
        System.out.println(hpqis.getValue_of_c());
        System.out.println(hpqis.getValue_of_d());
        System.out.println(hpqis.getResult());
        System.out.println(hpqis.getAuthority());
        //关闭session
        session.close();
    }

    /**
     * 添加结果
     */
    @Test
    public void addResultTest(){
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
        Hpqis hpqis=new Hpqis();
        hpqis.setValue_of_a(0.5);
        hpqis.setValue_of_b(0.5);
        hpqis.setValue_of_c(0.5);
        hpqis.setValue_of_d(0.5);
        hpqis.setValue_of_omega(1);
        hpqis.setAuthority(1);
        hpqis.setResult(1);
        session.insert("addResult",hpqis);
        session.commit();
        //关闭session
        session.close();
    }

    /**
     * 更新结果
     */
    @Test
    public void updateResultTest(){
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
        Hpqis hpqis=session.selectOne("findResultById",6);
        if(hpqis.getAuthority()==1){
            hpqis.setAuthority(0);
        }
        else{
            hpqis.setAuthority(1);
        }
        session.update("updateResult",hpqis);
        session.commit();
        //关闭session
        session.close();
    }

    /**
     * 删除结果
     */
    @Test
    public void deleteResultTest(){
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
        Hpqis hpqis=session.selectOne("findResultById",6);
        session.delete("deleteResult",6);
        session.commit();
        //关闭session
        session.close();
    }
}