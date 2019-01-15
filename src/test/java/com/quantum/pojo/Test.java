//package com.quantum.pojo;
//
//import org.apache.ibatis.io.Resources;
//import org.apache.ibatis.session.SqlSession;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.apache.ibatis.session.SqlSessionFactoryBuilder;
//
//import java.io.Reader;
//
//public class Test {
//    private static SqlSessionFactory sqlSessionFactory;
//    private static Reader reader;
//
//    static {
//        try {
//            reader = Resources.getResourceAsReader("mybatis-config.xml");
//            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static SqlSessionFactory getSession() {
//        return sqlSessionFactory;
//    }
//
//    public void findResultById(long id) {
//        SqlSession session = sqlSessionFactory.openSession();
//        try {
//            IHpqisOperation hpqisOperation = session.getMapper(IHpqisOperation.class);
//            Hpqis hpqis = hpqisOperation.findResultById(id);
//            if (hpqis != null) {
//                System.out.println(hpqis.getId() + ":" + hpqis.getValue_of_a()
//                        + ":" + hpqis.getValue_of_b());
//            }
//
//        } finally {
//            session.close();
//        }
//    }
//
//    /**
//     * 增加后要commit
//     */
//    public void addResult(){
//        Hpqis hpqis = new Hpqis();
//        hpqis.setValue_of_a(0.5);
//        hpqis.setValue_of_b(0.5);
//        hpqis.setValue_of_c(0.5);
//        hpqis.setValue_of_d(0.5);
//        hpqis.setValue_of_omega(1);
//        hpqis.setResult(1);
//        hpqis.setAuthority(1);
//        SqlSession session = sqlSessionFactory.openSession();
//        try {
//            IHpqisOperation hpqisOperation = session
//                    .getMapper(IHpqisOperation.class);
//            hpqisOperation.addReault(hpqis);
//            session.commit();
//            System.out.println("新增结果：" + hpqis.getId());
//        } finally {
//            session.close();
//        }
//    }
//
//    /**
//     * 修改后要commit
//     */
//    public void updateResult() {
//        SqlSession session = sqlSessionFactory.openSession();
//        try {
//            IHpqisOperation hpqisOperation = session
//                    .getMapper(IHpqisOperation.class);
//            Hpqis hpqis = hpqisOperation.findResultById(6);
//            if (hpqis != null) {
//                hpqis.setAuthority(0);
//                hpqisOperation.updateResult(hpqis);
//                session.commit();
//            }
//        } finally {
//            session.close();
//        }
//    }
//
//    /**
//     * 删除后要commit.
//     *
//     * @param id
//     */
//    public void deleteResult(int id) {
//        SqlSession session = sqlSessionFactory.openSession();
//        try {
//            IHpqisOperation hpqisOperation = session
//                    .getMapper(IHpqisOperation.class);
//            hpqisOperation.deleteUser(id);
//            session.commit();
//        } finally {
//            session.close();
//        }
//    }
//
//    public static void main(String[] args) {
//        try {
//            Test test = new Test();
//            test.findResultById(6);
//            // test.getUserList("test1");
//            // test.addUser();
//            // test.updateUser();
//            // test.deleteUser(6);
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
//}
