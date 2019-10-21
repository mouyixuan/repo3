package com.uppower.test;

import com.uppower.dao.IUserDao;
import com.uppower.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class SecondLevelCatchTest {
    private InputStream inputStream;
    private SqlSessionFactory factory;


    @Before
    public void init() throws IOException {
        inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        factory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @After
    public void destory() throws Exception{
        inputStream.close();
    }

    @Test
    public void testFindOne() {
        SqlSession session = factory.openSession();
        IUserDao userDao = session.getMapper(IUserDao.class);
        User user = userDao.findUserById(45);
        System.out.println(user);
        session.close();//释放一级缓存

        SqlSession session1 = factory.openSession();//再次打开session
        IUserDao userDao1 = session1.getMapper(IUserDao.class);
        User user1 = userDao1.findUserById(45);
        System.out.println(user1);
        session1.close();
    }
}
