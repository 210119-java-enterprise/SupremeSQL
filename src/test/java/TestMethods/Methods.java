package TestMethods;


import Models.Users;
import entity.Entity;
import entity.EntityDao;
import org.junit.*;
import session.Session;
import session.SessionManager;
import session.SessionStartup;

import java.util.List;

public class Methods {

    Users user1;
    Users user2;
    Users user3;
    EntityDao entityDao;
    Entity<?> users;

    @Before
    public void beforeTest() {
        entityDao = new EntityDao();
        users = Entity.EntityOf(Users.class);
        user1 = new Users("user1","INSERT");
        user2 = new Users("user2","INSERT");
        user3 = new Users("user3","INSERT");

    }

    @After
    public void afterTest() {
        user1 = null;
        user2 = null;
        user3 = null;
        entityDao = null;
        users = null;
    }

    @Test
    public void Test_SelectAll(){
        List<?> list = entityDao.SelectALL(users,user1);

        Assert.assertEquals(11,list.size());

    }

    @Test
    public void Test_SelectFrom(){
        List<?> list = entityDao.SelectFROM(users,user1,"firstname");
        Assert.assertEquals(11,list.size());
    }

    @Test
    public void Test_Insert(){
        int count =0;

        count = entityDao.Insert(users,user1);

        Assert.assertEquals(1, count);
    }

    @Test
    public void Test_Update(){
        int count =0;

        count = entityDao.Update(users,user1,user2);

        Assert.assertEquals(1, count);
    }

    @Test
    public void Test_Delete(){
        int count =0;
        count = entityDao.Delete(users,user1);
        Assert.assertEquals(1, count);
    }
}
