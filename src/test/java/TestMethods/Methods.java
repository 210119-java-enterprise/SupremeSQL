package TestMethods;


import Models.Account;
import Models.AppUsers;
import entity.Entity;
import entity.EntityDao;
import org.junit.*;

import java.util.List;

public class Methods {

    AppUsers user1;
    AppUsers user2;
    AppUsers user3;
    EntityDao entityDao;
    Entity<?> users;
    Entity<?> account;
    Account account1;

    @Before
    public void beforeTest() {
        entityDao = new EntityDao();
        users = Entity.EntityOf(AppUsers.class);
        user1 = new AppUsers("Calvin","Zheng","calvin123","calvin123");
        user2 = new AppUsers("Calvin","Zheng","1234","1234");
        account1 = new Account(1,0,1);
//        user3 = new Users("user3","INSERT");

    }

    @After
    public void afterTest() {
        user1 = null;
        user2 = null;
//        user3 = null;
        entityDao = null;
        users = null;
    }

    @Test
    public void Test_SelectAll(){
        List<?> list = entityDao.SelectALL(users,user1);

        Assert.assertEquals(3,list.size());

    }

    @Test
    public void Test_SelectFrom(){
        List<?> list = entityDao.SelectFROM(users,user1,"firstname");
        Assert.assertEquals(3,list.size());
    }

    @Test
    public void Test_Insert(){
        int count =0;

        count = entityDao.Insert(users,user2);

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

    @Test
    public void Test_DeleteAccount(){
        int count =0;
        entityDao.Insert(users,user2);
        count = entityDao.Delete(users,user2);
        Assert.assertEquals(1, count);
    }
}
